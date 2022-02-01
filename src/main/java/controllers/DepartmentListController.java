package controllers;

import com.test.jfxdb.Application;
import entities.Department;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import services.DepartmentDao;
import util.Utils;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class DepartmentListController implements Initializable {

    @FXML
    private TableView<Department> tableViewDepartment;
    @FXML
    private TableColumn<Department,Integer> tableColumnId;
    @FXML
    private TableColumn<Department,String> tableColumName;

    @FXML
    private Button btNew;
    private DepartmentDao service;
    private ObservableList<Department> obsList;

    @FXML
    public void onBtNewAction(ActionEvent event){
        Stage parentStage = Utils.currentStage(event);
        Department obj = new Department();
        createDialogForm(obj, parentStage,"DepartmentForm.fxml");

    }

    public void setDepartmentService(DepartmentDao service) {
        this.service= service;

    }

    public  void updateTableView(){
        if (service==null){
            throw new IllegalStateException("Service was null");
        }
        List<Department> departmentList = service.findAll();
        obsList = FXCollections.observableArrayList(departmentList);
        tableViewDepartment.setItems(obsList);

    }

    private void createDialogForm(Department obj, Stage parentStage, String absoluteName) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource(absoluteName));
            Pane pane = fxmlLoader.load();

            DepartmentFormController controller = fxmlLoader.getController();
            controller.setEntity(obj);
            controller.updateFormData();
            Stage dialogStage = new Stage();

            dialogStage.setTitle("Enter Department Data");
            dialogStage.setScene(new Scene(pane));
            dialogStage.setResizable(false);
            dialogStage.initOwner(parentStage);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        initializeNodes();


    }

    private void initializeNodes() {
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        Stage stage = (Stage) Application.getScene().getWindow();
        tableViewDepartment.prefHeightProperty().bind(stage.heightProperty());
    }

}
