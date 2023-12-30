package com.sudoku.controller;

import com.sudoku.Sudoku;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;

public class Gameplay {


    @FXML
    private Label timer;
    @FXML
    private BorderPane bounds;
    private GridPane gridPane;
    Sudoku sudoku = new Sudoku();
    ActionEvent[] events = new ActionEvent[15]; //store 15 events
    int eventPointer = 0;
    List<TextField> fields = new ArrayList<>();
    public void init() {
        bounds.setPadding(new Insets(10)); // Add padding for better visibility
        // Create a GridPane for the Sudoku grid
        gridPane = new GridPane();
        sudoku.initializeGrid(1);
        int[][] sudokuGrid = sudoku.getMatrix();
        System.out.println(sudoku);
        // Populate the grid with the Sudoku values
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                int value = sudokuGrid[row][col];
                TextField cell = getTextField(value, row, col);
                gridPane.add(cell, col, row);
            }
        }
        gridPane.setGridLinesVisible(false);
        bounds.setCenter(gridPane); // Set the Sudoku grid to the center of BorderPane
        fields.forEach(textField -> {
            textField.setOnAction(this::validateInput);
        });
    }

    private TextField getTextField(int value, int row, int col) {
        TextField cell = new TextField(value == 0 ? " " : String.valueOf(value));
        cell.setMinSize(40, 40);
        cell.setStyle("-fx-border-color: black; -fx-border-width: 1px;");
        cell.setAlignment(Pos.CENTER); // Center text within the label
        if (row % 3 == 0 && col % 3 != 0){
            cell.setStyle("-fx-border-color: black; -fx-border-width: 3px 1px 1px 1px;");
        }
        if (col % 3 == 0) {
            if ((row % 3 == 0)) {
                cell.setStyle("-fx-border-color: black; -fx-border-width: 3px 1px 1px 3px;");
            }
            else {
                cell.setStyle("-fx-border-color: black; -fx-border-width: 1px 1px 1px 3px;");
            }
        }
        cell.setId("r"+row+col);
        if (value != 0){
            cell.setDisable(true);
            return cell;
        }
        fields.add(cell);
        return cell;
    }
    @FXML
    public void validateInput(ActionEvent event){
        TextField textField = (TextField) event.getSource();
        String id = textField.getId();
        String strValue = textField.getText();
        int val = 0;
        try {
            val = Integer.parseInt(strValue.trim());
        }catch (NumberFormatException e){
            event.consume();
            textField.setText(" ");
            return;
        }
        int row = Integer.parseInt(id.charAt(1)+"");
        int col = Integer.parseInt(id.charAt(2)+"");
        boolean isValid = sudoku.play(val,row,col);
        if (isValid) {

            textField.setStyle("-fx-border-color: green; -fx-border-width: 2.4px; -fx-text-fill: black;");
        } else {
            textField.setStyle("-fx-border-color: red; -fx-border-width: 2.4px; -fx-text-fill: black;");
        }
    }
    @FXML
    public void undo(){

    }
    @FXML
    public void redo(){

    }
    @FXML
    public void reset(){
        init();
    }


}
