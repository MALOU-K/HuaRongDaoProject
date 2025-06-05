package controller;

import model.Direction;
import model.MapModel;
import view.AIFrame.AIFrame;
import view.game.BoxComponent;
import view.game.GamePanel;

/**
 * It is a bridge to combine GamePanel(view) and MapMatrix(model) in one game.
 * You can design several methods about the game logic in this class.
 */
public class GameController {
    private final GamePanel view;
    private MapModel model;
    private AIFrame aiFrame = null;
    private GamePanel gamePanel;

    public GameController(GamePanel view, MapModel model) {
        this.view = view;
        this.model = model;
        view.setController(this);
        this.gamePanel = view;

    }


    public void restartGame() {
        model.restart();
        view.clearAllBox();
        view.setSteps(0);
        view.getStepLabel().setText(String.format("Step: %d", view.getSteps()));
        if (aiFrame != null) {
            aiFrame.dispose();
            aiFrame = null;
        }
        view.initialGame();
    }

    public boolean doMove(int row, int col, Direction direction) {
        if (model.getId(row, col) == 1) {
            int nextRow = row + direction.getRow();
            int nextCol = col + direction.getCol();
            if (model.checkInHeightSize(nextRow) && model.checkInWidthSize(nextCol)) {
                if (model.getId(nextRow, nextCol) == 0) {
                    model.getMatrix()[row][col] = 0;
                    model.getMatrix()[nextRow][nextCol] = 1;
                    BoxComponent box = view.getSelectedBox();
                    Move.pieceMove(box, view.getGRID_SIZE(), direction);
                    box.setRow(nextRow);
                    box.setCol(nextCol);

                    return true;
                }
            }
        } else if (model.getId(row, col) == 2) {
            int nextRow = row + direction.getRow();
            int nextCol = col + direction.getCol();
            switch (direction) {
                case LEFT:
                    if (model.checkInHeightSize(nextCol)) {
                        if (model.getId(nextRow, nextCol) == 0) {
                            model.getMatrix()[row][col + 1] = 0;
                            model.getMatrix()[nextRow][nextCol] = 2;
                            BoxComponent box = view.getSelectedBox();
                            Move.pieceMove(box, view.getGRID_SIZE(), direction);
                            box.setRow(nextRow);
                            box.setCol(nextCol);
                            return true;
                        }
                    }
                    break;
                case RIGHT:
                    if (model.checkInWidthSize(nextCol + 1)) {
                        if (model.getId(nextRow, nextCol + 1) == 0) {
                            model.getMatrix()[row][col] = 0;
                            model.getMatrix()[nextRow][nextCol + 1] = 2;
                            BoxComponent box = view.getSelectedBox();
                            Move.pieceMove(box, view.getGRID_SIZE(), direction);
                            box.setRow(nextRow);
                            box.setCol(nextCol);
                            return true;
                        }
                    }
                    break;
                case UP, DOWN:
                    if (model.checkInHeightSize(nextRow)) {
                        if (model.getId(nextRow, nextCol) == 0 && model.getId(nextRow, nextCol + 1) == 0) {
                            model.getMatrix()[row][col] = 0;
                            model.getMatrix()[row][col + 1] = 0;
                            model.getMatrix()[nextRow][nextCol] = 2;
                            model.getMatrix()[nextRow][nextCol + 1] = 2;
                            BoxComponent box = view.getSelectedBox();
                            Move.pieceMove(box, view.getGRID_SIZE(), direction);
                            box.setRow(nextRow);
                            box.setCol(nextCol);
                            return true;
                        }
                    }
                    break;

            }


        } else if (model.getId(row, col) > 3) {
            int nextRow = row + direction.getRow();
            int nextCol = col + direction.getCol();
            int type = model.getMatrix()[row][col];
            if (model.checkInHeightSize(nextRow) && model.checkInWidthSize(nextCol) && model.checkInHeightSize(nextRow + 1)) {
                if (direction == Direction.LEFT || direction == Direction.RIGHT) {
                    if (model.getId(nextRow, nextCol) == 0 && model.getId(nextRow + 1, nextCol) == 0) {
                        model.getMatrix()[row][col] = 0;
                        model.getMatrix()[row + 1][col] = 0;
                        model.getMatrix()[nextRow][nextCol] = type;
                        model.getMatrix()[nextRow + 1][nextCol] = type;
                        BoxComponent box = view.getSelectedBox();
                        Move.pieceMove(box, view.getGRID_SIZE(), direction);
                        box.setRow(nextRow);
                        box.setCol(nextCol);
                        return true;
                    }
                } else {
                    if (model.getId(nextRow, nextCol) == 0 || model.getId(nextRow + 1, nextCol) == 0) {
                        model.getMatrix()[row][col] = 0;
                        model.getMatrix()[row + 1][col] = 0;
                        model.getMatrix()[nextRow][nextCol] = type;
                        model.getMatrix()[nextRow + 1][nextCol] = type;
                        BoxComponent box = view.getSelectedBox();
                        Move.pieceMove(box, view.getGRID_SIZE(), direction);
                        box.setRow(nextRow);
                        box.setCol(nextCol);
                        return true;
                    }
                }

            }

        } else if (model.getId(row, col) == 3) {
            int nextRow = row + direction.getRow();
            int nextCol = col + direction.getCol();
            switch (direction) {
                case UP:
                    if (model.checkInHeightSize(nextRow)) {
                        if (model.getId(nextRow, nextCol) == 0 && model.getId(nextRow, nextCol + 1) == 0) {
                            model.getMatrix()[row + 1][col] = 0;
                            model.getMatrix()[row + 1][col + 1] = 0;
                            model.getMatrix()[nextRow][nextCol] = 3;
                            model.getMatrix()[nextRow][nextCol + 1] = 3;
                            BoxComponent box = view.getSelectedBox();
                            Move.pieceMove(box, view.getGRID_SIZE(), direction);
                            box.setRow(nextRow);
                            box.setCol(nextCol);
                            return true;

                        }
                    }
                    break;
                case DOWN:
                    if (model.checkInHeightSize(nextRow + 1)) {
                        if (model.getId(nextRow + 1, nextCol) == 0 && model.getId(nextRow + 1, nextCol + 1) == 0) {
                            model.getMatrix()[row][col] = 0;
                            model.getMatrix()[row][col + 1] = 0;
                            model.getMatrix()[nextRow + 1][nextCol] = 3;
                            model.getMatrix()[nextRow + 1][nextCol + 1] = 3;
                            BoxComponent box = view.getSelectedBox();
                            Move.pieceMove(box, view.getGRID_SIZE(), direction);
                            box.setRow(nextRow);
                            box.setCol(nextCol);
                            return true;
                        }
                    }
                    break;
                case LEFT:
                    if (model.checkInWidthSize(nextCol)) {
                        if (model.getId(nextRow, nextCol) == 0 && model.getId(nextRow + 1, nextCol) == 0) {
                            model.getMatrix()[row][col + 1] = 0;
                            model.getMatrix()[row + 1][col + 1] = 0;
                            model.getMatrix()[nextRow][nextCol] = 3;
                            model.getMatrix()[nextRow + 1][nextCol] = 3;
                            BoxComponent box = view.getSelectedBox();
                            Move.pieceMove(box, view.getGRID_SIZE(), direction);
                            box.setRow(nextRow);
                            box.setCol(nextCol);
                            return true;
                        }
                    }
                    break;
                case RIGHT:
                    if (model.checkInWidthSize(nextCol + 1)) {
                        if (model.getId(nextRow, nextCol + 1) == 0 && model.getId(nextRow + 1, nextCol + 1) == 0) {
                            model.getMatrix()[row][col] = 0;
                            model.getMatrix()[row + 1][col] = 0;
                            model.getMatrix()[nextRow][nextCol + 1] = 3;
                            model.getMatrix()[nextRow + 1][nextCol + 1] = 3;
                            BoxComponent box = view.getSelectedBox();
                            Move.pieceMove(box, view.getGRID_SIZE(), direction);
                            box.setRow(nextRow);
                            box.setCol(nextCol);
                            return true;
                        }
                    }
                    break;
            }
        }


        return false;
    }

    public void setAiFrame(AIFrame aiFrame) {
        this.aiFrame = aiFrame;
    }

    public MapModel getModel() {
        return model;
    }

    public MapModel setModel(MapModel model) {
        return model;
    }


}

