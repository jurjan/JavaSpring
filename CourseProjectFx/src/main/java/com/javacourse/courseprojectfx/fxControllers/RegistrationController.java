package com.javacourse.courseprojectfx.fxControllers;

import com.javacourse.courseprojectfx.HelloApplication;
import com.javacourse.courseprojectfx.hibernate.ShopHibernate;
import com.javacourse.courseprojectfx.model.Customer;
import com.javacourse.courseprojectfx.model.User;
import com.javacourse.courseprojectfx.model.Manager;
import jakarta.persistence.EntityManagerFactory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class RegistrationController {

    @FXML
    public TextField loginField;
    @FXML
    public PasswordField passwordField;
    @FXML
    public PasswordField repeatPasswordField;
    @FXML
    public TextField nameField;
    @FXML
    public TextField surnameField;
    @FXML
    public RadioButton customerCheckbox;
    @FXML
    public ToggleGroup userType;
    @FXML
    public RadioButton managerCheckbox;
    @FXML
    public TextField addressField;
    @FXML
    public TextField cardNoField;
    @FXML
    public DatePicker birthDateField;
    @FXML
    public TextField employeeIdField;
    @FXML
    public TextField medCertificateField;
    @FXML
    public DatePicker employmentDateField;
    @FXML
    public CheckBox isAdminCheck;
    @FXML
    public TextField billingAddressField;

    private EntityManagerFactory entityManagerFactory;

    public void setData(EntityManagerFactory entityManagerFactory, boolean showManagerFields) {
        this.entityManagerFactory = entityManagerFactory;
        disableFields(showManagerFields);
    }

    public void setData(EntityManagerFactory entityManagerFactory, Manager manager) {
        this.entityManagerFactory = entityManagerFactory;
        toggleFields(customerCheckbox.isSelected(), manager);
    }

    private void disableFields(boolean showManagerFields) {
        if (!showManagerFields) {
            employeeIdField.setVisible(false);
            medCertificateField.setVisible(false);
            employmentDateField.setVisible(false);
            isAdminCheck.setVisible(false);
            managerCheckbox.setVisible(false);
        }
    }

    private void toggleFields(boolean isManager, Manager manager) {
        if (isManager) {
            addressField.setDisable(true);
            cardNoField.setDisable(true);
            employeeIdField.setDisable(false);
            medCertificateField.setDisable(false);
            employmentDateField.setDisable(false);
            if (manager.isAdmin()) isAdminCheck.setDisable(false);
        } else {
            addressField.setDisable(false);
            cardNoField.setDisable(false);
            employeeIdField.setDisable(true);
            medCertificateField.setDisable(true);
            employmentDateField.setDisable(true);
            isAdminCheck.setDisable(true);
        }
    }


    public void createUser() {
        ShopHibernate shopHibernate = new ShopHibernate(entityManagerFactory);
        if (customerCheckbox.isSelected()) {
            User user = new Customer(nameField.getText(), surnameField.getText(), loginField.getText(), passwordField.getText(), cardNoField.getText(), addressField.getText(), billingAddressField.getText(), birthDateField.getValue());
            shopHibernate.create(user);
        } else {
            //Create Manager
        }
    }

    public void returnToLogin() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-form.fxml"));
        Parent parent = fxmlLoader.load();
        Stage stage = (Stage) loginField.getScene().getWindow();
        Scene scene = new Scene(parent);
        FxUtils.setStageParameters(stage, scene, false);
    }
}
