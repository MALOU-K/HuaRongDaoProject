package view.game;

import controller.GameController;
import model.Direction;
import model.MapModel;
import view.AIFrame.AIFrame;
import view.FrameUtil;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

/**
 * It is the subclass of ListenerPanel, so that it should implement those four methods: do move left, up, down ,right.
 * The class contains a grids, which is the corresponding GUI view of the matrix variable in MapMatrix.
 */
public class GamePanel extends ListenerPanel {
    private List<BoxComponent> boxes;
    private MapModel model;
    private GameController controller;
    private JLabel stepLabel;
    private int steps;
    private final int GRID_SIZE = 50;
    private BoxComponent selectedBox;
    private VictoryWindow window;
    private GameFrame gameFrame;
    private AIFrame aiFrame;


    public GamePanel(MapModel model, GameFrame gameFrame) {
        this.gameFrame = gameFrame;
        boxes = new ArrayList<>();
        this.setVisible(true);
        this.setFocusable(true);
        this.setLayout(null);
        this.setSize(model.getWidth() * GRID_SIZE + 4, model.getHeight() * GRID_SIZE + 4);
        this.model = model;
        this.selectedBox = null;

        initialGame();
    }


    public GamePanel(MapModel model, AIFrame aiFrame) {
        this.aiFrame = aiFrame;
        boxes = new ArrayList<>();

        this.setVisible(true);
        this.setFocusable(false);
        this.setLayout(null);
        this.setSize(model.getWidth() * GRID_SIZE + 4, model.getHeight() * GRID_SIZE + 4);
        this.model = model;
        this.selectedBox = null;

        initialGame();
    }


    public void initialGame() {
        this.steps = 0;
        //copy a map
        int[][] map = new int[model.getHeight()][model.getWidth()];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                map[i][j] = model.getId(i, j);
            }
        }
        //build Component
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                BoxComponent box = null;
                if (map[i][j] == 1) {
                    box = new BoxComponent("小兵.jpg", i, j);
                    box.setSize(GRID_SIZE, GRID_SIZE);
                    map[i][j] = 0;
                } else if (map[i][j] == 2) {
                    box = new BoxComponent("关羽.jpg", i, j);
                    box.setSize(GRID_SIZE * 2, GRID_SIZE);
                    map[i][j] = 0;
                    map[i][j + 1] = 0;
                } else if (map[i][j] > 3) {
                    switch (map[i][j] % 4) {
                        case 0:
                            box = new BoxComponent("马超.jpg", i, j);
                            break;
                        case 1:
                            box = new BoxComponent("赵云.jpg", i, j);
                            break;
                        case 2:
                            box = new BoxComponent("张飞.jpg", i, j);
                            break;
                        case 3:
                            box = new BoxComponent("黄忠.jpg", i, j);
                            break;
                    }
                    box.setSize(GRID_SIZE, GRID_SIZE * 2);
                    map[i][j] = 0;
                    map[i + 1][j] = 0;
                } else if (map[i][j] == 3) {
                    box = new BoxComponent("曹操.jpg", i, j);
                    box.setSize(GRID_SIZE * 2, GRID_SIZE * 2);
                    map[i][j] = 0;
                    map[i + 1][j] = 0;
                    map[i][j + 1] = 0;
                    map[i + 1][j + 1] = 0;
                }
                if (box != null) {
                    box.setLocation(j * GRID_SIZE + 2, i * GRID_SIZE + 2);
                    boxes.add(box);
                    box.setVisible(true);
                    this.add(box);
                }
            }
        }
        this.repaint();
    }

    public void clearAllBox() {
        for (BoxComponent box : boxes) {
            removeBox(box);
        }
        boxes.clear();
        this.repaint();

    }

    public void removeBox(BoxComponent box) {
        this.remove(box);
        this.revalidate();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            // 加载背景图片
            BufferedImage backgroundImage = ImageIO.read(new File("Image/棋盘.jpg"));

            g.drawImage(backgroundImage,0,0,this.getWidth(),this.getHeight(),null);

        } catch (IOException e) {
            e.printStackTrace();
        }

        Border border = BorderFactory.createLineBorder(Color.DARK_GRAY, 4);
        this.setBorder(border);
    }

    @Override
    public void doMouseClick(Point point) {
        gameFrame.getUpper().playSound(0, "Music/棋子.wav", "棋子");
        Component component = this.getComponentAt(point);
        if (component instanceof BoxComponent clickedComponent) {
            if (selectedBox == null) {
                selectedBox = clickedComponent;
                selectedBox.setSelected(true);
            } else if (selectedBox != clickedComponent) {
                selectedBox.setSelected(false);
                clickedComponent.setSelected(true);
                selectedBox = clickedComponent;
            } else {
                clickedComponent.setSelected(false);
                selectedBox = null;
            }
        }
    }

    @Override
    public void doMoveRight() {
        gameFrame.getUpper().playSound(0, "Music/按钮.wav", "按钮");
        System.out.println("Click VK_RIGHT");

        if (selectedBox != null) {
            if (controller.doMove(selectedBox.getRow(), selectedBox.getCol(), Direction.RIGHT)) {
                afterMove();
            }
        }
    }

    @Override
    public void doMoveLeft() {
        gameFrame.getUpper().playSound(0, "Music/按钮.wav", "按钮");
        System.out.println("Click VK_LEFT");
        if (selectedBox != null) {
            if (controller.doMove(selectedBox.getRow(), selectedBox.getCol(), Direction.LEFT)) {
                afterMove();
            }
        }
    }

    @Override
    public void doMoveUp() {
        gameFrame.getUpper().playSound(0, "Music/按钮.wav", "按钮");
        System.out.println("Click VK_Up");
        if (selectedBox != null) {
            if (controller.doMove(selectedBox.getRow(), selectedBox.getCol(), Direction.UP)) {
                afterMove();
            }
        }
    }

    @Override
    public void doMoveDown() {
        gameFrame.getUpper().playSound(0, "Music/按钮.wav", "按钮");
        System.out.println("Click VK_DOWN");
        if (selectedBox != null) {
            if (controller.doMove(selectedBox.getRow(), selectedBox.getCol(), Direction.DOWN)) {
                afterMove();
            }
        }
    }

    public void afterMove() {
        this.steps++;
        this.stepLabel.setText(String.format("Step: %d", this.steps));
        if (isWin()) {
            System.out.println("win");

            window = new VictoryWindow(gameFrame, this.getSteps(), gameFrame.getController());
            gameFrame.getUpper().playSound(1, "Music/起航.wav", "启航");
            window.setVisible(true);
            gameFrame.getUpper().playBGM("Music/凌驾.wav", "凌驾");

            gameFrame.addComponentListener(new ComponentAdapter() {
                @Override
                public void componentMoved(ComponentEvent e) {
                    if (window != null && window.isVisible()) {
                        window.setLocation(gameFrame.getContentPane().getLocationOnScreen());
                    }
                }

                @Override
                public void componentResized(ComponentEvent e) {
                    if (window != null && window.isVisible()) {
                        window.setSize(gameFrame.getContentPane().getWidth(), gameFrame.getContentPane().getHeight());

                    }
                }
            });
        }
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public boolean isWin() {
        return model.getId(3, 1) == 3 && model.getId(3, 2) == 3 && model.getId(4, 1) == 3 && model.getId(4, 2) == 3;
    }

    public void setStepLabel(JLabel stepLabel) {
        this.stepLabel = stepLabel;
    }

    public JLabel getStepLabel() {
        return stepLabel;
    }

    public void setController(GameController controller) {
        this.controller = controller;
    }

    public BoxComponent getSelectedBox() {
        return selectedBox;
    }

    public void setSelectedBox(BoxComponent box) {
        // 如果之前有选中的box，先取消其选中状态
        if (this.selectedBox != null) {
            this.selectedBox.setSelected(false);
        }

        // 设置新的选中box
        this.selectedBox = box;

        // 如果新box不为null，设置其选中状态
        if (this.selectedBox != null) {
            this.selectedBox.setSelected(true);
        }

        // 重绘面板以更新显示
        this.repaint();
    }

    public int getGRID_SIZE() {
        return GRID_SIZE;
    }

    public int getSteps() {
        return steps;
    }

    public MapModel getModel() {
        return model;
    }

    public void setModel(MapModel model) {
        this.model = model;
    }

    public void resetGameState() {
        clearAllBox();
        initialGame();
        setSteps(0);
        getStepLabel().setText("Step: 0");
        setSelectedBox(null);
        repaint();
    }

}
