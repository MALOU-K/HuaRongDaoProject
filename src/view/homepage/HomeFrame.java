package view.homepage;

import view.FrameUtil;
import view.game.GameFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeFrame extends JFrame {
    private JButton start;
    private JButton exit;
    private JDialog exitDialog;
    private GameFrame gameFrame;

    public HomeFrame(int width, int height) {
        this.setTitle("Home");
        setLayout(null);
        setSize(width, height);
        setVisible(true);
        this.setLocationRelativeTo(null);

        start = FrameUtil.createButton(this, "开始游戏", new Point(20, 40), 100, 60);
        exit = FrameUtil.createButton(this, "退出游戏", new Point(20, 150), 100, 60);

        exitDialog = new JDialog(this, "提示", true);
        exitDialog.setSize(300, 200);
        exitDialog.setLayout(null);
        JLabel label = new JLabel("是否退出游戏");
        label.setBounds(100, 30, 100, 50);
        exitDialog.add(label);

        JButton confirm = new JButton("确认");
        JButton cancel = new JButton("取消");
        confirm.setBounds(100, 130, 60, 30);
        cancel.setBounds(160, 130, 60, 30);
        exitDialog.add(confirm);
        exitDialog.add(cancel);
        exitDialog.setLocationRelativeTo(this);
        confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exitDialog.setVisible(false);
            }
        });

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exitDialog.setVisible(true);
            }
        });
        start.addActionListener(e -> {
            if (gameFrame != null) {
                gameFrame.setVisible(true);
                this.setVisible(false);
            }


        });

    }

    public void setGameFrame(GameFrame gameFrame) {
        this.gameFrame = gameFrame;
    }
}
