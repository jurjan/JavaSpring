package com.javacourse.courseprojectfx.fxControllers.tableParameters;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class UserTableParameters {

    SimpleIntegerProperty id = new SimpleIntegerProperty();
    SimpleStringProperty login = new SimpleStringProperty();
    SimpleStringProperty name = new SimpleStringProperty();

    //TODO Complete remaining fields that are in User class
    //Do not forget constructor, getters and setters

    public UserTableParameters(SimpleIntegerProperty id, SimpleStringProperty login, SimpleStringProperty name) {
        this.id = id;
        this.login = login;
        this.name = name;
    }

    public UserTableParameters() {
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getLogin() {
        return login.get();
    }
    public SimpleStringProperty loginProperty() {
        return login;
    }

    public void setLogin(String login) {
        this.login.set(login);
    }

    public String getName() {
        return name.get();
    }
    public SimpleStringProperty nameProperty() {
        return name;
    }
    public void setName(String name) {
        this.name.set(name);
    }
}
