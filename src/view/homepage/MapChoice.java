package view.homepage;

import model.MapModel;
import view.FrameUtil;
import view.Sound;
import view.game.GameFrame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;



import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MapChoice extends JFrame {
  //hahaha我是帅哥
    private String currentUser;
    private JButton level1,level2,level3,level4;
    private JButton setting;
    private Sound sound = new Sound();
    private JPanel contentPanel;

    private boolean isSoundEffect = true;
    private boolean isBGM = true;

    public MapChoice(int width,int height,String username){
        this.currentUser = username;
        this.setSize(width,height);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.contentPanel = new JPanel();
        this.contentPanel.setLayout(null);
        this.contentPanel.setOpaque(false);
        this.contentPanel.setBounds(0,0,this.getWidth(),this.getWidth());
        getLayeredPane().add(contentPanel,JLayeredPane.MODAL_LAYER);

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

        this.level1 = new JButton("Level 1");
        this.level1.setSize(180,80);
        this.level1.setLocation(100,100);
        contentPanel.add(level1);
        this.level2 = new JButton("Level 2");
        this.level2.setSize(180,80);
        this.level2.setLocation(320,100);
        contentPanel.add(level2);
        this.level3 = new JButton("Level 3");
        this.level3.setSize(180,80);
        this.level3.setLocation(100,230);
        contentPanel.add(level3);
        this.level4 = new JButton("Level 4");
        this.level4.setSize(180,80);
        this.level4.setLocation(320,230);
        contentPanel.add(level4);

        level1.addActionListener(e -> {
            playSound(0, "Music/按钮.wav", "按钮");
            playSound(1,"Music/宝藏.wav","宝藏");
            this.setVisible(false);
            MapModel mapModel = new MapModel(new int[][]{
                    {4, 3, 3, 7},
                    {4, 3, 3, 7},
                    {5, 0, 0, 6},
                    {5, 0, 0, 6},
                    {1, 0, 0, 1}
            });

            // 创建并显示游戏主窗口
            GameFrame gameFrame = new GameFrame(600, 500, mapModel,this);
        this.level4.setSize(180,100);
        this.add(level4);



        level1.addActionListener(e -> {
            this.setVisible(false);
            MapModel mapModel = new MapModel(new int[][]{
                    {4, 3, 3, 7},
                    {4, 3, 3, 7},
                    {5, 0, 0, 6},
                    {5, 0, 0, 6},
                    {1, 0, 0, 1}
            });

            // 创建并显示游戏主窗口
            GameFrame gameFrame = new GameFrame(600, 500, mapModel,this,currentUser);
            gameFrame.setTitle("三国华容道 - 游戏进行中");
            gameFrame.setLocationRelativeTo(null);
            gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            gameFrame.setVisible(true);

            // 关闭当前窗口
            dispose();
        });

        level2.addActionListener(e -> {
            playSound(0, "Music/按钮.wav", "按钮");
            playSound(1,"Music/宝藏.wav","宝藏");
            this.setVisible(false);
            MapModel mapModel = new MapModel(new int[][]{
                    {4, 3, 3, 7},
                    {4, 3, 3, 7},
                    {5, 2, 2, 6},
                    {5, 0, 0, 6},
                    {1, 0, 0, 1}
            });

            // 创建并显示游戏主窗口
            GameFrame gameFrame = new GameFrame(600, 500, mapModel,this,currentUser);
            gameFrame.setTitle("三国华容道 - 游戏进行中");
            gameFrame.setLocationRelativeTo(null);
            gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            gameFrame.setVisible(true);

            // 关闭当前窗口
            dispose();
        });

        level3.addActionListener(e -> {
            playSound(0, "Music/按钮.wav", "按钮");
            playSound(1,"Music/宝藏.wav","宝藏");
            this.setVisible(false);
            MapModel mapModel = new MapModel(new int[][]{
                    {1, 3, 3, 1},
                    {4, 3, 3, 7},
                    {4, 2, 2, 7},
                    {5, 1, 1, 6},
                    {5, 0, 0, 6}
            });

            // 创建并显示游戏主窗口
            GameFrame gameFrame = new GameFrame(600, 500, mapModel,this,currentUser);
            gameFrame.setTitle("三国华容道 - 游戏进行中");
            gameFrame.setLocationRelativeTo(null);
            gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            gameFrame.setVisible(true);

            // 关闭当前窗口
            dispose();
        });

        level4.addActionListener(e -> {
            playSound(0, "Music/按钮.wav", "按钮");
            playSound(1,"Music/宝藏.wav","宝藏");
            this.setVisible(false);
            MapModel mapModel = new MapModel(new int[][]{
                    {4, 3, 3, 7},
                    {4, 3, 3, 7},
                    {5, 2, 2, 6},
                    {5, 1, 1, 6},
                    {1, 0, 0, 1}
            });

            // 创建并显示游戏主窗口
            GameFrame gameFrame = new GameFrame(600, 500, mapModel,this,currentUser);
            gameFrame.setTitle("三国华容道 - 游戏进行中");
            gameFrame.setLocationRelativeTo(null);
            gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            gameFrame.setVisible(true);

            // 关闭当前窗口
            dispose();
        });

        this.setVisible(false);
    }

    public Sound getSound() {
        return sound;
    }

    public boolean isBGM() {
        return isBGM;
    }

    public void setBGM(boolean BGM) {
        isBGM = BGM;
    }

    public boolean isSoundEffect() {
        return isSoundEffect;
    }

    public void setSoundEffect(boolean soundEffect) {
        isSoundEffect = soundEffect;
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
