module com.javacourse.courseprojectfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires mysql.connector.j;
    requires java.sql;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires java.naming;
    requires org.kordamp.bootstrapfx.core;

    opens com.javacourse.courseprojectfx to javafx.fxml;
    exports com.javacourse.courseprojectfx;
    opens com.javacourse.courseprojectfx.fxControllers to javafx.fxml;
    exports com.javacourse.courseprojectfx.fxControllers to javafx.fxml;
    opens com.javacourse.courseprojectfx.fxControllers.tableParameters to javafx.fxml, javafx.base;
    exports com.javacourse.courseprojectfx.fxControllers.tableParameters to javafx.fxml, java.base;
    opens com.javacourse.courseprojectfx.model to javafx.fxml, org.hibernate.orm.core, jakarta.persistence;
    exports com.javacourse.courseprojectfx.model to javafx.fxml, org.hibernate.orm.core, jakarta.persistence;
}