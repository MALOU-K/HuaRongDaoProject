package model;

import java.io.Serializable;
import java.util.Arrays;

public class GameState implements Serializable {
    private static final long serialVersionUID = 1L;
    private int[][] mapMatrix;
    private int steps;
    private String username;

    public GameState(int[][] mapMatrix, int steps, String username) {
        this.mapMatrix = mapMatrix;
        this.steps = steps;
        this.username = username;
    }

    public int[][] getMapMatrix() {
        return mapMatrix;
    }

    public int getSteps() {
        return steps;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "GameState{" +
                "mapMatrix=" + Arrays.deepToString(mapMatrix) +
                ", steps=" + steps +
                ", username='" + username + '\'' +
                '}';
    }
}