package view.game;

import controller.GameController;
import model.MapModel;
import view.AIFrame.AIFrame;
import view.FrameUtil;
import view.Sound;
import view.homepage.HomeFrame;
import view.homepage.MapChoice;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class GameFrame extends JFrame {
    private GameController controller;
    private JButton restartBtn;
    private JButton loadBtn;
    private JLabel stepLabel;
    private GamePanel gamePanel;
    private JButton UPBtn, DOWNBtn, LEFTBtn, RIGHTBtn;
    private JButton AIBtn;
    private AIFrame aiFrame;

    private JButton setting;
    private boolean isBGM = true;
    private boolean isSoundEffect = true;

    private MapChoice upper;

    private static Sound sound = new Sound();


    public GameFrame(int width, int height, MapModel mapModel, MapChoice upper) {
        this.setTitle("2025 CS109 Project Demo");
        this.setLayout(null);
        this.setSize(width, height);
        gamePanel = new GamePanel(mapModel, this);
        gamePanel.setLocation(30, height / 2 - gamePanel.getHeight() / 2);
        this.add(gamePanel);
        this.controller = new GameController(gamePanel, mapModel);
        this.upper = upper;

        this.restartBtn = FrameUtil.createButton(this, "Restart", new Point(gamePanel.getWidth() + 80, 120), 80, 50);
        this.loadBtn = FrameUtil.createButton(this, "Load", new Point(gamePanel.getWidth() + 80, 210), 80, 50);
        this.stepLabel = FrameUtil.createJLabel(this, "Start", new Font("serif", Font.ITALIC, 22), new Point(30 + gamePanel.getWidth() / 2 - 30, 30), 180, 50);
        gamePanel.setStepLabel(stepLabel);

        this.UPBtn = FrameUtil.createImageButton(this, "Image/箭头UP.png", new Point(gamePanel.getWidth() + 200, 250), 75, 45);
        this.DOWNBtn = FrameUtil.createImageButton(this, "Image/箭头DOWN.png", new Point(gamePanel.getWidth() + 200, 370), 75, 45);
        this.LEFTBtn = FrameUtil.createImageButton(this, "Image/箭头LEFT.png", new Point(gamePanel.getWidth() + 155, 295), 45, 75);
        this.RIGHTBtn = FrameUtil.createImageButton(this, "Image/箭头RIGHT.png", new Point(gamePanel.getWidth() + 275, 295), 45, 75);

        this.UPBtn.addActionListener(e -> {
            gamePanel.doMoveUp();
            gamePanel.requestFocusInWindow();
        });
        this.DOWNBtn.addActionListener(e -> {
            gamePanel.doMoveDown();
            gamePanel.requestFocusInWindow();
        });
        this.LEFTBtn.addActionListener(e -> {
            gamePanel.doMoveLeft();
            gamePanel.requestFocusInWindow();
        });
        this.RIGHTBtn.addActionListener(e -> {
            gamePanel.doMoveRight();
            gamePanel.requestFocusInWindow();
        });

        this.setting = FrameUtil.createImageButton(this, "Image/设置.png", new Point(this.getWidth() - 90, 5), 50, 50);


        this.AIBtn = FrameUtil.createButton(this, "AI提示", new Point(this.getWidth() - 120, 60), 80, 30);
        AIBtn.addActionListener(e -> {
            playSound(0, "Music/按钮.wav", "按钮");
            if (aiFrame != null) {
                aiFrame.dispose();
                aiFrame = null;
            }
            aiFrame = new AIFrame(this.gamePanel, this);
            aiFrame.setVisible(true);
            controller.setAiFrame(aiFrame);
            gamePanel.requestFocusInWindow();
        });


        this.restartBtn.addActionListener(e -> {
            playSound(0, "Music/按钮.wav", "按钮");
            controller.restartGame();
            gamePanel.requestFocusInWindow();//enable key listener
        });
        this.loadBtn.addActionListener(e -> {
            playSound(0, "Music/按钮.wav", "按钮");
            String string = JOptionPane.showInputDialog(this, "Input path:");
            System.out.println(string);
            gamePanel.requestFocusInWindow();//enable key listener
        });


        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


    }

    public GameController getController() {
        return controller;
    }

    public MapChoice getUpper() {
        return upper;
    }

    public void playSound(int type, String filename, String key) {
        if (type == 0) {
            if (isSoundEffect) {
                try {
                    sound.loadSound(key, filename);
                    sound.playOneTime(key);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } else if (type == 1) {
            if (isBGM) {
                try {
                    sound.loadSound(key, filename);
                    sound.playOneTime(key);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public void playBGM(String filename, String key) {
        if (isBGM) {
            try {
                sound.loadSound(key, filename);
                sound.playOneTime(key);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void stopBGM(String key) {
        sound.stop(key);
    }

}
