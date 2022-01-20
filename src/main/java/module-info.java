module cellsociety_app {
    // list all imported class packages since they are dependencies
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.base;
    requires javafx.media;

    // allow other classes to access listed packages in your project
    exports cellsociety;
}
