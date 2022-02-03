package controllers;

import entities.Department;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import services.DaoFactory;
import services.DepartmentDao;
import util.Utils;

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
    public Button btCancel;
    private Department entity;

    public void setEntity(Department entity) {
        this.entity = entity;
    }

    public void onBtSaveAction() {

        entity = getFormData();
        DepartmentDao dao = DaoFactory.createDepartmentDao();
        dao.saveOrUpdate(entity);


    }

    private Department getFormData() {
        Department department = new Department();
        department.setId(Utils.tryParseToInt(txtID.getText()));
        department.setName(txtName.getText());

        return department;
    }

    public void onBtCancelAction() {
        System.out.println("Cancel");
    }


    public void updateFormData() {
        if (entity == null) {
            throw new IllegalStateException("Entity was Null");
        }
        txtID.setText(String.valueOf(entity.getId()));
        txtName.setText(entity.getName());

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       initializeNodes();

    }

    public void initializeNodes() {
       txtID.textProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null && !newValue.matches("\\d")) {
               txtID.setText(oldValue);
           }
      });

    }
}
