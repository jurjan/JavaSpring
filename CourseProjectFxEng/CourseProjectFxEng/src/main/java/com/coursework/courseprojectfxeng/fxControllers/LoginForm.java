package com.coursework.courseprojectfxeng.fxControllers;

import com.coursework.courseprojectfxeng.HelloApplication;
import com.coursework.courseprojectfxeng.hibernate.HibernateShop;
import com.coursework.courseprojectfxeng.model.User;
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
import java.util.Stack;

public class LoginForm implements Initializable {

    @FXML
    public TextField loginField;
    @FXML
    public PasswordField passwordField;
    private EntityManagerFactory entityManagerFactory;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        entityManagerFactory = Persistence.createEntityManagerFactory("Shop");
    }

    public void validateAndLogin() throws IOException {
        HibernateShop hibernateShop = new HibernateShop(entityManagerFactory);
        User user = hibernateShop.getUserByCredentials(loginField.getText(), passwordField.getText());
        //If user exists, open main window form
        if(user !=null){
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main-window.fxml"));
            //Load resources associated with the form
            Parent parent = fxmlLoader.load();
            //Access controller of main window
            MainWindow mainWindow = fxmlLoader.getController();
            mainWindow.setData(entityManagerFactory, user);
            //Every element in the form knows to which scene it belongs and scene knows to which stage (window it belongs)
            Stage stage = (Stage) loginField.getScene().getWindow();
            Scene scene = new Scene(parent);
            stage.setTitle("Flower shop");
            stage.setScene(scene);
            stage.show();
        }else{
            //Something went wrong, generate an alert
            //See here, you can just copy paste - https://code.makery.ch/blog/javafx-dialogs-official/
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("System message");
            alert.setHeaderText("Error during login");
            alert.setContentText("No such user");

            alert.showAndWait();
        }
    }

    public void openRegistrationForm() {
    }
}
