module com.coursework.courseprojectfxeng {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires mysql.connector.j;
    requires java.sql;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires java.naming;
    requires org.kordamp.bootstrapfx.core;

    opens com.coursework.courseprojectfxeng to javafx.fxml;
    exports com.coursework.courseprojectfxeng;
    opens com.coursework.courseprojectfxeng.fxControllers to javafx.fxml;
    exports com.coursework.courseprojectfxeng.fxControllers to javafx.fxml;
    opens com.coursework.courseprojectfxeng.fxControllers.tableParameters to javafx.fxml, javafx.base;
    exports com.coursework.courseprojectfxeng.fxControllers.tableParameters to javafx.fxml, java.base;
    opens com.coursework.courseprojectfxeng.model to javafx.fxml, org.hibernate.orm.core, jakarta.persistence;
    exports com.coursework.courseprojectfxeng.model to javafx.fxml, org.hibernate.orm.core, jakarta.persistence;
}