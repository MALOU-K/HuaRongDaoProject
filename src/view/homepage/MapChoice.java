package view.homepage;

import model.MapModel;
import view.game.GameFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MapChoice extends JFrame {
  //hahaha我是帅哥
    private JButton level1,level2,level3,level4;
    private JButton exit;

    public MapChoice(int width,int height){
        this.setSize(width,height);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.level1 = new JButton("Level 1");
        this.level1.setSize(180,80);
        this.level1.setLocation(100,100);
        this.add(level1);
        this.level2 = new JButton("Level 2");
        this.level2.setSize(180,80);
        this.level2.setLocation(320,100);
        this.add(level2);
        this.level3 = new JButton("Level 3");
        this.level3.setSize(180,80);
        this.level3.setLocation(100,230);
        this.add(level3);
        this.level4 = new JButton("Level 4");
        this.level4.setSize(180,80);
        this.level4.setLocation(320,230);
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
            GameFrame gameFrame = new GameFrame(600, 500, mapModel,this);
            gameFrame.setTitle("三国华容道 - 游戏进行中");
            gameFrame.setLocationRelativeTo(null);
            gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            gameFrame.setVisible(true);

            // 关闭当前窗口
            dispose();
        });

        level2.addActionListener(e -> {
            this.setVisible(false);
            MapModel mapModel = new MapModel(new int[][]{
                    {4, 3, 3, 7},
                    {4, 3, 3, 7},
                    {5, 2, 2, 6},
                    {5, 0, 0, 6},
                    {1, 0, 0, 1}
            });

            // 创建并显示游戏主窗口
            GameFrame gameFrame = new GameFrame(600, 500, mapModel,this);
            gameFrame.setTitle("三国华容道 - 游戏进行中");
            gameFrame.setLocationRelativeTo(null);
            gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            gameFrame.setVisible(true);

            // 关闭当前窗口
            dispose();
        });

        level3.addActionListener(e -> {
            this.setVisible(false);
            MapModel mapModel = new MapModel(new int[][]{
                    {1, 3, 3, 1},
                    {4, 3, 3, 7},
                    {4, 2, 2, 7},
                    {5, 1, 1, 6},
                    {5, 0, 0, 6}
            });

            // 创建并显示游戏主窗口
            GameFrame gameFrame = new GameFrame(600, 500, mapModel,this);
            gameFrame.setTitle("三国华容道 - 游戏进行中");
            gameFrame.setLocationRelativeTo(null);
            gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            gameFrame.setVisible(true);

            // 关闭当前窗口
            dispose();
        });

        level4.addActionListener(e -> {
            this.setVisible(false);
            MapModel mapModel = new MapModel(new int[][]{
                    {4, 3, 3, 7},
                    {4, 3, 3, 7},
                    {5, 2, 2, 6},
                    {5, 1, 1, 6},
                    {1, 0, 0, 1}
            });

            // 创建并显示游戏主窗口
            GameFrame gameFrame = new GameFrame(600, 500, mapModel,this);
            gameFrame.setTitle("三国华容道 - 游戏进行中");
            gameFrame.setLocationRelativeTo(null);
            gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            gameFrame.setVisible(true);

            // 关闭当前窗口
            dispose();
        });


        this.setVisible(false);


    }
}
