package AI;

import model.Direction;
import model.MapModel;

import java.util.*;


public class BFS {
    private Queue<Node> queue = new ArrayDeque<>();
    private Set<Long> inq = new HashSet<>();


    /* initialModel: {4, 3, 3, 7},
                     {4, 3, 3, 7},
                     {5, 0, 0, 6},
                     {5, 0, 0, 6},
                     {1, 0, 0, 1}*/
    public Node node(MapModel initialModel) {
        List<Long> initial = new ArrayList<>();
        Long initialHash = modelToHash(initialModel);
        initial.add(initialHash);
        inq.add(initialHash);
        queue.add(new Node(initialModel, initial));
        int[] row = new int[]{-1, 1, 0, 0};
        int[] col = new int[]{0, 0, -1, 1};//上下左右
        int step1 = 0;

        while (!queue.isEmpty()) {
            int count = queue.size();
            for (int i = 0; i < count; i++) {
                Node top = queue.poll();
                if (top.getModel().getMatrix()[3][1] == 3 && top.getModel().getMatrix()[3][2] == 3 && top.getModel().getMatrix()[4][1] == 3 && top.getModel().getMatrix()[4][2] == 3) {
                    return top;
                }
                if (top.getStep() >= 150) {
                    return top;
                }

                List<int[]> zero = getZero(top.getModel());
                for (int[] ints : zero) {
                    for (int j = 0; j < 4; j++) {

                        step1 += 1;
                        System.out.println(step1);
                        if (step1 > 20800000) {
                            return top;
                        }


                        if (isMove(top.getModel(), Direction.getDirection(-row[j], -col[j]), ints[0] + row[j], ints[1] + col[j])) {
                            int[][] copy = new int[top.getModel().getMatrix().length][];
                            //复制矩阵
                            for (int k = 0; k < top.getModel().getMatrix().length; k++) {
                                copy[k] = Arrays.copyOf(top.getModel().getMatrix()[k], top.getModel().getMatrix()[k].length);
                            }
                            MapModel copyModel = new MapModel(copy);
                            Move(copyModel, Direction.getDirection(-row[j], -col[j]), ints[0] + row[j], ints[1] + col[j]);
                            if (inq.add(modelToHash(copyModel))){
                                List<Long> newPath = new ArrayList<>(top.path);
                                newPath.add(modelToHash(copyModel));
                                Node node1 = new Node(copyModel, newPath);
                                node1.setStep(top.getStep() + 1);
                                queue.add(node1);
                            }


                        }
                    }
                }
            }

        }
        return null;
    }

    public int getBlockRow(MapModel model, int row, int col, Direction direction) {
        int id = model.getId(row, col);

        if (id == 1 || id == 2) {
            return row;
        } else if (id > 3 || id == 3) {
            if (direction.getRow() == 0) {
                if (row > 0 && row < 4 && model.getId(row + direction.getCol(), col) == id) {
                    return row + direction.getCol();
                }
            } else {
                if (row > 0 && row < 4 && model.getId(row + direction.getRow(), col) == id) {
                    return row + direction.getRow();
                }
            }
        }

        return row;
    }

    // 获取块的起始列（优化版）
    public int getBlockCol(MapModel model, int row, int col, Direction direction) {
        int id = model.getId(row, col);

        if (id == 1 || id > 3) {
            return col;
        } else if (id == 2 || id == 3) {
            if (direction.getCol() == 0) {
                if (col > 0 && col < 3 && model.getId(row, col + direction.getRow()) == id) {
                    return col + direction.getRow();
                }
            } else {
                if (col > 0 && col < 3 && model.getId(row, col + direction.getCol()) == id) {
                    return col + direction.getCol();
                }
            }
        }
        return col;
    }

    public boolean isMove(MapModel model, Direction direction, int Row, int Col) {
        if (Row > 4 || Row < 0 || Col > 3 || Col < 0) {
            return false;
        }
        int col = getBlockCol(model, Row, Col, direction);
        int row = getBlockRow(model, Row, Col, direction);
        int nextRow = row + direction.getRow();
        int nextCol = col + direction.getCol();

        if (model.checkInHeightSize(nextRow) && model.checkInWidthSize(nextCol)
        ) {
            int type = model.getId(row, col);
            int height = getHeight(type);
            int width = getWidth(type);
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    if (!model.checkInHeightSize(nextRow - j * direction.getCol()) || !model.checkInWidthSize(nextCol - i * direction.getRow())) {
                        return false;
                    }
                    if (model.getId(nextRow - j * direction.getCol(), nextCol - i * direction.getRow()) != 0) {
                        return false;
                    }
                }
            }
            /*for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    model.getMatrix()[nextRow - j * direction.getCol() - j * direction.getRow()][nextCol - i * direction.getRow() - i * direction.getCol()] = type;
                    model.getMatrix()[row - j * direction.getCol() - j * direction.getRow()][col - i * direction.getRow() - i * direction.getCol()] = 0;
                }
            }*/
            return true;
        }

        return false;
    }

    public void Move(MapModel model, Direction direction, int Row, int Col){
        int col = getBlockCol(model, Row, Col, direction);
        int row = getBlockRow(model, Row, Col, direction);
        int nextRow = row + direction.getRow();
        int nextCol = col + direction.getCol();
        int type = model.getId(row, col);
        int height = getHeight(type);
        int width = getWidth(type);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                model.getMatrix()[nextRow - j * direction.getCol() - j * direction.getRow()][nextCol - i * direction.getRow() - i * direction.getCol()] = type;
                model.getMatrix()[row - j * direction.getCol() - j * direction.getRow()][col - i * direction.getRow() - i * direction.getCol()] = 0;
            }
        }
    }

    public int getWidth(int type) {
        if (type == 1 || type > 3) {
            return 1;
        } else if (type == 2 || type == 3) {
            return 2;
        }
        return 0;
    }

    public int getHeight(int type) {
        if (type == 1 || type == 2) {
            return 1;
        } else if (type > 3 || type == 3) {
            return 2;
        }
        return 0;
    }


    public List<int[]> getZero(MapModel model) { //int[]={row,col}
        List<int[]> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 4; j++) {
                if (model.getId(i, j) == 0) {
                    int[] zero = new int[]{i, j};
                    list.add(zero);
                }
            }
        }
        return list;
    }

    public long modelToHash(MapModel model) {
        long hash = 0;
        int[][] matrix = model.getMatrix();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                hash = (hash << 3) | matrix[i][j]; // 每个元素占3位
            }
        }
        return hash;
    }

    public MapModel hashToModel(long hash) {
        int rows = 5;
        int cols = 4;
        int[][] matrix = new int[5][4];
        int totalElements = rows * cols;

        for (int i = totalElements - 1; i >= 0; i--) {
            int row = i / cols;
            int col = i % cols;
            matrix[row][col] = (int) (hash & 7); // 取最后3位
            hash >>>= 3; // 右移3位
        }
        return new MapModel(matrix);
    }

}
