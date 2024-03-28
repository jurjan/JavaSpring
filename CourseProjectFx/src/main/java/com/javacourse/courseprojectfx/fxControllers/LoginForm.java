package com.javacourse.courseprojectfx.fxControllers;

import com.javacourse.courseprojectfx.HelloApplication;
import com.javacourse.courseprojectfx.hibernate.ShopHibernate;
import com.javacourse.courseprojectfx.model.User;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginForm implements Initializable {
    @FXML
    public TextField loginField;
    @FXML
    public PasswordField pswField;

    private EntityManagerFactory entityManagerFactory;

    public void validateAndLoadMain() throws IOException {

        ShopHibernate hibernateShop = new ShopHibernate(entityManagerFactory);
        var user = hibernateShop.getUserByCredentials(loginField.getText(), pswField.getText());
        //If user exists, open main window form
        if (user != null) {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main-window.fxml"));
            //Load resources associated with the form. We need this step to access controllers
            Parent parent = fxmlLoader.load();
            //Access controller of main window. Each form has its own controller, so make sure that you make no mistake here
            MainWindow mainWindow = fxmlLoader.getController();
            mainWindow.setData(entityManagerFactory, (User) user);
            //Every element in the form knows to which scene it belongs and scene knows to which stage (window it belongs)
            Stage stage = (Stage) loginField.getScene().getWindow();
            Scene scene = new Scene(parent);
            FxUtils.setStageParameters(stage, scene, false);
        } else {
            //Something went wrong, generate an alert
            FxUtils.generateAlert(Alert.AlertType.INFORMATION, "Login error", "No such user or wrong credentials");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        entityManagerFactory = Persistence.createEntityManagerFactory("Shop");
    }

    public void openRegistration() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("registration.fxml"));
        Parent parent = fxmlLoader.load();
        //Access controller of main window. Each form has its own controller, so make sure that you make no mistake here
        RegistrationController registrationController = fxmlLoader.getController();
        registrationController.setData(entityManagerFactory, false);
        Stage stage = (Stage) loginField.getScene().getWindow();
        Scene scene = new Scene(parent);
        FxUtils.setStageParameters(stage, scene, false);
    }
}
