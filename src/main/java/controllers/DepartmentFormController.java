package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class DepartmentFormController implements Initializable {
    @FXML
    private TextField txtID;
    @FXML
    private TextField txtName;
    @FXML
    private Label labelErrorName;
    @FXML
    private Button btSave;
    @FXML
    public  Button btCancel;

    public void onBtSaveAction(){
        System.out.println("Save");
    }
    public void onBtCancelAction(){
        System.out.println("Cancel");
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeNodes();

    }
    public void initializeNodes(){
        txtID.textProperty().addListener((obs, oldValue, newValue) -> {
            if(newValue != null && !newValue.matches("\\d")){txtID.setText(oldValue);}
        });

    }
}
