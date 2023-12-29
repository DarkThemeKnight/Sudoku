module Sudoku {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;
    requires java.logging;
    opens  com.sudoku.controller to  javafx.graphics,javafx.fxml,javafx.base,javafx.controls,java.logging;
    opens com.sudoku to  javafx.graphics,javafx.fxml,javafx.base,javafx.controls,java.logging;

    exports com.sudoku.controller;
    exports com.sudoku;
}