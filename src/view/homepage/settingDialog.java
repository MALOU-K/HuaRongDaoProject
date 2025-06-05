package view.homepage;

import view.game.GameFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class settingDialog extends JDialog {
    private boolean isBGM = true;
    private boolean isSoundEffect = true;
    private JCheckBox BGM;
    private JCheckBox SoundEffect;
    private JButton exit;
    private JButton returnBtn;
    private JFrame frame;

    public settingDialog(MapChoice mapChoice){
        super(mapChoice,"设置",true);
        this.frame = mapChoice;
        this.setSize(200,180);
        this.setLayout(null);

        this.BGM = new JCheckBox("开启背景音乐",isBGM);
        this.BGM.setBounds(50,10,100,30);

        this.add(BGM);

        this.SoundEffect = new JCheckBox("开启音效",isSoundEffect);
        this.SoundEffect.setBounds(50,50,100,30);
        this.add(SoundEffect);

        exit = new JButton("退出游戏");
        this.exit.setBounds(50,90,100,30);
        this.add(exit);

        exit.addActionListener(e -> {
            System.exit(0);
        });

        BGM.addActionListener(e -> {
            isBGM = BGM.isSelected();
            if (isBGM){
                mapChoice.playBGM("Music/BGM.wav","BGM");
            }else {
                mapChoice.stopBGM("BGM");
            }
        });

        SoundEffect.addActionListener(e -> {
            isSoundEffect = SoundEffect.isSelected();
        });

        this.setLocationRelativeTo(null);

    }

    public settingDialog(GameFrame gameFrame){
        super(gameFrame,"设置",true);
        this.frame = gameFrame;

        this.setSize(220,180);
        this.setLayout(null);

        this.BGM = new JCheckBox("开启背景音乐",isBGM);
        this.BGM.setBounds(50,10,100,30);
        this.add(BGM);

        this.SoundEffect = new JCheckBox("开启音效",isSoundEffect);
        this.SoundEffect.setBounds(50,50,100,30);
        this.add(SoundEffect);

        exit = new JButton("退出游戏");
        exit.setBounds(5,90,90,30);
        this.add(exit);
        returnBtn = new JButton("返回菜单");
        returnBtn.setBounds(105,90,90,30);
        this.add(returnBtn);

        exit.addActionListener(e -> {
            System.exit(0);
        });
        returnBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameFrame.setVisible(false);
                gameFrame.getUpper().setVisible(true);
            }
        });

        BGM.addActionListener(e -> {
            isBGM = BGM.isSelected();
            if (isBGM){
                gameFrame.getUpper().playBGM("Music/BGM.wav","BGM");
            }else {
                gameFrame.getUpper().stopBGM("BGM");
            }
        });

        SoundEffect.addActionListener(e -> {
            isSoundEffect = SoundEffect.isSelected();
        });
        this.setLocationRelativeTo(this);
    }
}
