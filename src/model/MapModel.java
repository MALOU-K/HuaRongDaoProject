package model;

import java.util.stream.IntStream;

/**
 * This class is to record the map of one game. For example:
 */
public class MapModel {
    int[][] matrix;
    private int[][] originalMatrix;


    public MapModel(int[][] matrix) {
        this.matrix = matrix;
        this.originalMatrix = copyMatrix(matrix);
    }

    public void restart(){
        matrix = copyMatrix(originalMatrix);
    }

    public int[][] copyMatrix(int[][] matrix){
        int[][] copy = new int[matrix.length][matrix[0].length];
        IntStream.range(0, matrix.length).forEach(i -> System.arraycopy(matrix[i], 0, copy[i], 0, matrix[i].length));
        return copy;
    }

    public int getWidth() {
        return this.matrix[0].length;
    }

    public int getHeight() {
        return this.matrix.length;
    }

    public int getId(int row, int col) {
        return matrix[row][col];
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public boolean checkInWidthSize(int col) {
        return col >= 0 && col < matrix[0].length;
    }

    public boolean checkInHeightSize(int row) {
        return row >= 0 && row < matrix.length;
    }

    public boolean equalTo(MapModel model){
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (model.getId(i,j) != this.getId(i,j)){
                    return false;
                }
            }
        }
        return true;
    }
}
