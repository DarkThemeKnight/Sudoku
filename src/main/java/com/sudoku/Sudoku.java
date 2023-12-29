package com.sudoku;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.logging.Logger;

public class Sudoku {
    private final Logger log = Logger.getLogger("Sudoku main");
    private int[][] matrix = new int[9][9];
    private static final int EASY = 40;
    private static final int MEDIUM = 30;
    private static final int HARD = 20;
    public void initializeGrid(int difficulty){
        matrix = new int[9][9];
        // generate mappings
        List<Integer> val = new ArrayList<>();
        for (int i = 0; i < 81; i++) {
            val.add(i);
        }
        int[] positions = new int[0];
        Random random = new Random();
        switch (difficulty){
            case 1 -> {
                positions = new int[EASY];
                for (int i = 0; i < positions.length; i++) {
                    positions[i] = val.remove(random.nextInt(val.size()));
                }
            }
            case 2 ->{
                positions = new int[MEDIUM];
                for (int i = 0; i < positions.length; i++) {
                    positions[i] = val.remove(random.nextInt(val.size()));
                }
            }
            case 3 -> {
                positions = new int[HARD];
                for (int i = 0; i < positions.length; i++) {
                    positions[i] = val.remove(random.nextInt(val.size()));
                }
            }
        }
        for (int position : positions) {
            int row = position / 9;
            int col = position % 9;
            int input = random.nextInt(10);
            boolean isValidInput = isValid(input, row, col);
            while (!isValidInput) {
                input = random.nextInt(10);
                isValidInput = isValid(input, row, col);
            }
            matrix[row][col] = input;
        }
    }

    public boolean isValid(int number, int row, int col) {
        ExecutorService executor = Executors.newFixedThreadPool(3); // Creating a thread pool
        List<Callable<Boolean>> tasks = getCallables(number, row, col);
        try {
            // Submit tasks to the executor and get the results
            List<Future<Boolean>> results = executor.invokeAll(tasks);
            // Check the results from each task
            for (Future<Boolean> result : results) {
                if (!result.get()) {
                    executor.shutdown(); // Shutdown the executor
                    return false; // If any task returns false, return false
                }
            }
        } catch (InterruptedException | ExecutionException e) {
            log.severe(e.getMessage());
        }
        executor.shutdown(); // Shutdown the executor
        return true; // If all tasks pass, return true
    }
    private List<Callable<Boolean>> getCallables(int number, int row, int col) {
        Callable<Boolean> checkRows = () -> {
            for (int i = row; i < 9; i++) {
                if (matrix[i][col] == number) {
                    return false;
                }
            }
            return true;
        };

        Callable<Boolean> checkColumns = () -> {
            for (int i = col; i < 9; i++) {
                if (matrix[row][i] == number) {
                    return false;
                }
            }
            return true;
        };

        Callable<Boolean> checkSubgrid = () -> {
            int startRow = 3 * (row / 3);
            int startCol = 3 * (col / 3);
            for (int i = startRow; i < startRow + 3; i++) {
                for (int j = startCol; j < startCol + 3; j++) {
                    if (matrix[i][j] == number) {
                        return false;
                    }
                }
            }
            return true;
        };

        // List of tasks
        return List.of(checkRows, checkColumns, checkSubgrid);
    }
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            if (i % 3 != 0) {
                sb.append("-------------------------------------------------\n");
            }
            else {
                sb.append("***************************************************\n");
            }
            sb.append("* | ");
            for (int j = 0; j < 3; j++) {
                sb.append(matrix[i][j]);
                if (j+1 < 3){
                    sb.append(" | ");
                }
            }



            sb.append(" | * ").append(" | ");
            for (int j = 0; j < 3; j++) {
             sb.append(matrix[i][j+3]);
             if (j+1 < 3){
                 sb.append(" | ");
             }
            }


            sb.append(" | * ").append(" | ");
            for (int j = 0; j < 3; j++) {
                sb.append(matrix[i][j+6]);
                if (j+1 < 3){
                    sb.append(" | ");
                }
            }
            sb.append(" | *\n");

        }
        sb.append("***************************************************\n");
        return sb.toString();
    }


    public static void main(String[] args) {
        Sudoku sudoku = new Sudoku();
//        sudoku.initializeGrid(1);
//        sudoku.initializeGrid(2);
        System.out.println(sudoku);
    }
}