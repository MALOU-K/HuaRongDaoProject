package model;

import java.util.stream.IntStream;

/**
 * This class is to record the map of one game.
 */
public class MapModel {
    private int[][] currentMatrix;  // 当前游戏状态
    private int[][] originalMatrix; // 初始地图状态（永不修改）

    public MapModel(int[][] matrix) {
        // 创建原始地图的深拷贝
        this.originalMatrix = copyMatrix(matrix);
        // 初始时当前状态与原始状态相同
        this.currentMatrix = copyMatrix(originalMatrix);
    }

    public int[][] getOriginalMatrix() {
        return originalMatrix;
    }
    public int[][] getMatrix() {
        return currentMatrix;
    }

    public void restart() {
        // 重置为原始地图状态（深拷贝）
        this.currentMatrix = copyMatrix(originalMatrix);
    }

    public int[][] copyMatrix(int[][] matrix) {
        int[][] copy = new int[matrix.length][matrix[0].length];
        IntStream.range(0, matrix.length).forEach(i ->
                System.arraycopy(matrix[i], 0, copy[i], 0, matrix[i].length)
        );
        return copy;
    }

    public void setOriginalMatrix(int[][] originalMatrix) {
        this.originalMatrix = originalMatrix;
    }


    public int getWidth() {
        return this.currentMatrix[0].length;
    }

    public int getHeight() {
        return this.currentMatrix.length;
    }

    public int getId(int row, int col) {
        return currentMatrix[row][col];
    }



    public void setMatrix(int[][] matrix) {
        this.currentMatrix = matrix;
    }

    public boolean checkInWidthSize(int col) {
        return col >= 0 && col < currentMatrix[0].length;
    }

    public boolean checkInHeightSize(int row) {
        return row >= 0 && row < currentMatrix.length;
    }

    public boolean equalTo(MapModel model) {
        for (int i = 0; i < currentMatrix.length; i++) {
            for (int j = 0; j < currentMatrix[0].length; j++) {
                if (model.getId(i, j) != this.getId(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isWin() {
        for (int i = 0; i < currentMatrix.length; i++) {
            for (int j = 0; j < currentMatrix[0].length; j++) {
                if (currentMatrix[i][j] == 2) {
                    return false;
                }
            }
        }
        return true;
    }
}