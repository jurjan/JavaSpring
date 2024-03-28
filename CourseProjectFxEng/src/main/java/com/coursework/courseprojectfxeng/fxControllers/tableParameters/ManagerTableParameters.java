package com.coursework.courseprojectfxeng.fxControllers.tableParameters;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ManagerTableParameters extends UserTableParameters{

    public ManagerTableParameters() {
    }

    public ManagerTableParameters(SimpleIntegerProperty id, SimpleStringProperty login, SimpleStringProperty name) {
        super(id, login, name);
    }
}
