package controllers;

import com.test.jfxdb.Application;
import entities.Department;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import services.DaoFactory;
import services.DepartmentDao;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class MainViewController implements Initializable {
    @FXML
    private MenuItem menuItemSeller;
    @FXML
    private MenuItem menuItemDepartment;
    @FXML
    private MenuItem menuItemAbout;

    public void onMenuItemSellerButtonClick() {
    }

    public void onMenuItemDepartmentButtonClick() {

        loadView("DepartmentList.fxml", (DepartmentListController controller) -> {
            controller.setDepartmentService(DaoFactory.createDepartmentDao());
            controller.updateTableView();
        });
    }

    public void onMenuItemAboutButtonClick() {
    }

    private <T> void loadView(String absoluteName, Consumer<T> intializingAction){

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource(absoluteName));
            VBox newVbox = fxmlLoader.load();

            Scene scene = Application.getScene();

            VBox mainVbox = (VBox) ((ScrollPane) scene.getRoot()).getContent();

            Node mainMenu = mainVbox.getChildren().get(0);
            mainVbox.getChildren().clear();
            mainVbox.getChildren().add(mainMenu);
            mainVbox.getChildren().addAll(newVbox.getChildren());

            T controller = fxmlLoader.getController();
            intializingAction.accept(controller);



        } catch (IOException e) {
            e.printStackTrace();
        }




    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }
}
