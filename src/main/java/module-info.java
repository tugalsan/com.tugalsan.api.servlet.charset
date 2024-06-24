module com.tugalsan.api.servlet.charset {
    requires jakarta.servlet;
    requires com.tugalsan.api.charset;
    requires com.tugalsan.api.file;
    requires com.tugalsan.api.callable;
    requires com.tugalsan.api.log;
    requires com.tugalsan.api.unsafe;
    exports com.tugalsan.api.servlet.charset.server;
}
