module com.mycompany.gestiondepracticas {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires java.sql;
    requires java.persistence;
    
    opens com.mycompany.gestiondepracticas to javafx.fxml, org.hibernate.orm.core, java.sql;
    opens models;
    exports com.mycompany.gestiondepracticas;
}
