package view.game;

import controller.GameController;
import model.GameState;
import model.MapModel;
import view.AIFrame.AIFrame;
import view.FrameUtil;
import view.Sound;
import view.homepage.HomeFrame;
import view.homepage.MapChoice;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.zip.CRC32;

import model.MapModel;


public class GameFrame extends JFrame {

    private GameController controller;
    private JButton restartBtn;
    private JButton loadBtn;
    private String currentUser;
    private JButton saveBtn;
    private JLabel stepLabel;
    private GamePanel gamePanel;
    private JButton UPBtn, DOWNBtn, LEFTBtn, RIGHTBtn;
    private JButton AIBtn;

    private AIFrame aiFrame;

    private JButton setting;
    private boolean isBGM ;
    private boolean isSoundEffect ;
    private JPanel contentPanel;

    private Timer autoSaveTimer;
    private MapChoice upper;

    private Sound sound ;


    public GameFrame(int width, int height, MapModel mapModel, MapChoice upper,String username) {
        this.currentUser = username;
        this.setTitle("欢迎,"+username);
        this.setLayout(null);
        this.setSize(width, height);
        gamePanel = new GamePanel(mapModel, this);
        gamePanel.setLocation(30, height / 2 - gamePanel.getHeight() / 2);

        this.controller = new GameController(gamePanel, mapModel);
        this.upper = upper;
      //  this.initialMapModel = new MapModel(mapModel.getInitialMatrix());



        this.sound = upper.getSound();
        this.isBGM = upper.isBGM();
        this.isSoundEffect = upper.isSoundEffect();

        try {
            // 加载背景图片
            BufferedImage backgroundImage = ImageIO.read(new File("Image/背景.jpg"));

            JPanel backgroundPanel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
                }
            };

            // 设置 JPanel 为透明
            backgroundPanel.setOpaque(false);

            // 将 JPanel 添加到 LayeredPane 的最底层
            getLayeredPane().add(backgroundPanel,JLayeredPane.DEFAULT_LAYER);
            backgroundPanel.setBounds(0, 0, getWidth(), getHeight());

            // 确保 ContentPane 是透明的
            JPanel ContentPane = (JPanel) this.getContentPane();
            ContentPane.setOpaque(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.contentPanel = new JPanel();
        this.contentPanel.setLayout(null);
        this.contentPanel.setBounds(0,0,this.getWidth(),this.getHeight());
        this.contentPanel.setOpaque(false);
        getLayeredPane().add(contentPanel,JLayeredPane.MODAL_LAYER);
        contentPanel.add(gamePanel);


        this.restartBtn = FrameUtil.createButton(contentPanel, "Restart", new Point(gamePanel.getWidth() + 80, 120), 80, 50);
        this.loadBtn = FrameUtil.createButton(contentPanel, "Load", new Point(gamePanel.getWidth() + 80, 210), 80, 50);
        this.stepLabel = FrameUtil.createJLabel(contentPanel, "Start", new Font("serif", Font.ITALIC, 22), new Point(30 + gamePanel.getWidth() / 2 - 30, 30), 180, 50);
        this.restartBtn = FrameUtil.createButton(this, "Restart", new Point(gamePanel.getWidth() + 80, 120), 80, 50);
        this.loadBtn = FrameUtil.createButton(this, "Load", new Point(gamePanel.getWidth() + 80, 210), 80, 50);
        this.loadBtn.addActionListener(e -> loadGame());
        this.saveBtn = FrameUtil.createButton(this, "Save", new Point(gamePanel.getWidth() + 80, 300), 80, 50);
        this.saveBtn.addActionListener(e -> saveGame());
        this.stepLabel = FrameUtil.createJLabel(this, "Start", new Font("serif", Font.ITALIC, 22), new Point(30 + gamePanel.getWidth()/2 - 30, 30), 180, 50);
        gamePanel.setStepLabel(stepLabel);

        this.UPBtn = FrameUtil.createImageButton(contentPanel, "Image/箭头UP.png", new Point(gamePanel.getWidth() + 200, 250), 75, 45);
        this.DOWNBtn = FrameUtil.createImageButton(contentPanel, "Image/箭头DOWN.png", new Point(gamePanel.getWidth() + 200, 370), 75, 45);
        this.LEFTBtn = FrameUtil.createImageButton(contentPanel, "Image/箭头LEFT.png", new Point(gamePanel.getWidth() + 155, 295), 45, 75);
        this.RIGHTBtn = FrameUtil.createImageButton(contentPanel, "Image/箭头RIGHT.png", new Point(gamePanel.getWidth() + 275, 295), 45, 75);

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

        this.setting = FrameUtil.createImageButton(contentPanel, "Image/设置.png", new Point(this.getWidth() - 90, 5), 50, 50);


        this.AIBtn = FrameUtil.createButton(contentPanel, "AI提示", new Point(this.getWidth() - 120, 60), 80, 30);
        AIBtn.addActionListener(e -> {
            upper.playSound(0, "Music/按钮.wav", "按钮");
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
            upper.playSound(0, "Music/按钮.wav", "按钮");

            controller.restartGame();
            gamePanel.requestFocusInWindow();
        });
        this.loadBtn.addActionListener(e -> {
            upper.playSound(0, "Music/按钮.wav", "按钮");
            String string = JOptionPane.showInputDialog(this, "Input path:");
            System.out.println(string);
            gamePanel.requestFocusInWindow();//enable key listener
        });






        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        setupAutoSave();


    }

    public GameController getController() {
        return controller;
    }

    public MapChoice getUpper() {
        return upper;
    }

    private void saveGame() {
        if ("游客".equals(currentUser)) {
            JOptionPane.showMessageDialog(this, "游客无法保存游戏", "提示", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        File saveDir = new File("saves");
        if (!saveDir.exists()) {
            saveDir.mkdir();
        }

        // 创建游戏状态对象
        GameState state = new GameState(
                controller.getModel().getMatrix(),
                controller.getModel().getOriginalMatrix(),
                gamePanel.getSteps(),
                currentUser
        );

        try {
            // 创建字节数组输出流
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            // 使用同一个 ObjectOutputStream 计算校验和和写入文件
            try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
                oos.writeObject(state);

                // 计算校验和
                byte[] data = baos.toByteArray();
                CRC32 crc = new CRC32();
                crc.update(data);
                long checksum = crc.getValue();

                // 写入文件（包含校验和）
                try (ObjectOutputStream fileOos = new ObjectOutputStream(
                        new FileOutputStream("saves/" + currentUser + ".sav"))) {
                    fileOos.writeLong(checksum); // 写入校验和
                    fileOos.write(data); // 写入序列化数据
                    JOptionPane.showMessageDialog(this, "游戏已保存", "成功", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "保存失败: " + ex.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }
        String fullPath = new File("saves/" + currentUser + ".sav").getAbsolutePath();
        System.out.println("存档位置: " + fullPath);
        JOptionPane.showMessageDialog(this, "存档位置: " + fullPath);


    }

    public void loadGame() {

        if ("游客".equals(currentUser)) {
            JOptionPane.showMessageDialog(this, "游客无法加载游戏", "提示", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        File saveFile = new File("saves/" + currentUser + ".sav");
        if (!saveFile.exists()) {
            JOptionPane.showMessageDialog(this, "没有找到保存文件", "提示", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(saveFile))) {

            // 读取校验和
            long savedChecksum = ois.readLong();

            // 读取序列化数据
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = ois.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }
            byte[] data = baos.toByteArray();

            // 验证校验和
            CRC32 crc = new CRC32();
            crc.update(data);
            long calculatedChecksum = crc.getValue();

            if (savedChecksum != calculatedChecksum) {
                throw new IOException("文件校验失败，可能已被篡改");
            }

            // 反序列化游戏状态
            try (ObjectInputStream dataOis = new ObjectInputStream(
                    new ByteArrayInputStream(data))) {
                GameState state = (GameState) dataOis.readObject();

                // 验证用户匹配
                if (!currentUser.equals(state.getUsername())) {
                    throw new IOException("用户不匹配");
                }

                // 验证地图数据
                int[][] mapMatrix = state.getMapMatrix();
                if (mapMatrix == null || mapMatrix.length == 0 || mapMatrix[0].length == 0) {
                    throw new IOException("地图数据损坏");
                }

                // 验证步数
                if (state.getSteps() < 0) {
                    throw new IOException("步数数据损坏");
                }

                // 创建新模型
                MapModel newModel = new MapModel(mapMatrix);
                newModel.setOriginalMatrix(state.getOriginalMatrix());
                gamePanel.setModel(newModel);
                controller = new GameController(gamePanel,newModel);

                // 重置游戏面板
                gamePanel.clearAllBox();
                gamePanel.initialGame();
                gamePanel.setSteps(state.getSteps());
                gamePanel.getStepLabel().setText("Step: " + state.getSteps());

                // 重置选中的棋子
                gamePanel.setSelectedBox(null);

                JOptionPane.showMessageDialog(this, "游戏已加载", "成功", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (IOException | ClassNotFoundException | ClassCastException ex) {
            JOptionPane.showMessageDialog(this, "加载失败: 文件可能已损坏 - " + ex.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            // 删除损坏文件
            if (saveFile.exists()) {
                saveFile.delete();
            }
        }
    }

    private void setupAutoSave() {
        // 如果不是游客，设置自动保存
        if (!"游客".equals(currentUser)) {
            // 定时自动保存（每5分钟）
            autoSaveTimer = new Timer(30000, e -> saveGame());
            autoSaveTimer.start();
        }

        // 退出时保存处理
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int choice = JOptionPane.showConfirmDialog(
                        GameFrame.this,
                        "是否保存游戏进度？",
                        "退出确认",
                        JOptionPane.YES_NO_CANCEL_OPTION
                );

                if (choice == JOptionPane.YES_OPTION) {
                    saveGame();
                    dispose(); // 关闭窗口
                } else if (choice == JOptionPane.NO_OPTION) {
                    dispose(); // 关闭窗口
                }
                // 取消则不做任何操作，窗口保持打开
            }
        });
    }


}
