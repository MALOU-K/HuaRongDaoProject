package view.AIFrame;

import AI.BFS;
import AI.Node;
import model.MapModel;
import view.game.GameFrame;
import view.game.GamePanel;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class AIFrame extends JFrame {
    private BFS bfs;
    private GamePanel gamePanel;
    private GameFrame gameFrame;
    private JButton nextBtn;
    private GamePanel AIGamePanel;


    public AIFrame(GamePanel panel, GameFrame gameFrame) {
        this.gameFrame = gameFrame;
        this.gamePanel = panel;
        this.setSize(60 + gamePanel.getWidth(), this.gameFrame.getHeight());
        this.setLocation(this.gameFrame.getX() + this.gameFrame.getWidth(), this.gameFrame.getY());
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
        this.setLayout(null);
        this.setResizable(false);


        AIGamePanel = new GamePanel(gamePanel.getModel(), this);
        AIGamePanel.setFocusable(false);
        AIGamePanel.setLocation(30, this.getHeight() / 2 - gamePanel.getHeight() / 2);
        this.add(AIGamePanel);

        this.bfs = new BFS();
        Node node = bfs.node(gamePanel.getModel());
        List<Long> path = node.getPath();
        final int[] step = {1};

        nextBtn = new JButton("下一步");
        nextBtn.setBounds(this.getWidth() / 2 - 30, AIGamePanel.getY() + AIGamePanel.getHeight() + 20, 60, 30);
        this.add(nextBtn);

        nextBtn.addActionListener(e -> {
            if (step[0] < path.size()) {
                AIGamePanel.setModel(bfs.hashToModel(path.get(step[0])));
                AIGamePanel.clearAllBox();
                AIGamePanel.initialGame();
                step[0] += 1;
            }else {
                JOptionPane.showMessageDialog(this,"已经走完了");
            }
            panel.requestFocusInWindow();
        });

        panel.requestFocusInWindow();

        //跟随移动和大小
        gameFrame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentMoved(ComponentEvent e) {
                if (AIFrame.this != null && AIFrame.this.isVisible()) {
                    AIFrame.this.setLocation(AIFrame.this.gameFrame.getX() + AIFrame.this.gameFrame.getWidth(), AIFrame.this.gameFrame.getY());
                }
            }

            @Override
            public void componentResized(ComponentEvent e) {
                if (AIFrame.this != null && AIFrame.this.isVisible()) {
                    AIFrame.this.setSize(60 + gamePanel.getWidth(), AIFrame.this.gameFrame.getHeight());
                    AIFrame.this.setLocation(AIFrame.this.gameFrame.getX() + AIFrame.this.gameFrame.getWidth(), AIFrame.this.gameFrame.getY());

                }
            }
        });

        //同步状态
        gameFrame.addWindowStateListener(new WindowAdapter() {
            @Override
            public void windowStateChanged(WindowEvent e) {
                syncWindowState(gameFrame, AIFrame.this);
            }
        });
        this.addWindowStateListener(new WindowAdapter() {
            @Override
            public void windowStateChanged(WindowEvent e) {
                syncWindowState(AIFrame.this, gameFrame);
            }
        });
    }

    private void syncWindowState(JFrame source, JFrame target) {
        int state = source.getExtendedState();
        if (target.getExtendedState() != state) {
            target.setExtendedState(state);
        }
    }

}
