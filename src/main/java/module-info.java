module com.example.envirofx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.net.http;
    requires org.json;
    requires org.apache.httpcomponents.httpcore;
    requires org.apache.httpcomponents.httpclient;
    requires org.apache.commons.codec;
    requires com.fasterxml.jackson.databind;

    opens com.example.envirofx to javafx.fxml;
    exports com.example.envirofx;
}