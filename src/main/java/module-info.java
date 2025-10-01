module com.tugalsan.api.servlet.charset {
    requires jakarta.servlet;
    requires com.tugalsan.api.charset;
    requires com.tugalsan.api.file;
    requires com.tugalsan.api.function;
    requires com.tugalsan.api.log;    
    exports com.tugalsan.api.servlet.charset.server;
}
