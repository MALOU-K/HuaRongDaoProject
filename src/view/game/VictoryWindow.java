package view.game;

import controller.GameController;
import view.FrameUtil;

import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VictoryWindow extends JWindow {
    private int step;
    private JLabel stepLabel;
    private JButton restartBtn,returnHomeBtn;
    private Clip clip;

    public VictoryWindow( GameFrame gameFrame, int step, GameController controller) {
        super(gameFrame);
        this.step = step;

        this.setLocation(gameFrame.getContentPane().getLocationOnScreen());
        this.setSize(gameFrame.getContentPane().getWidth(),gameFrame.getContentPane().getHeight());
        this.setBackground(new Color(0,0,0,0));
        this.setLayout(null);


        TransparencyPanel panel1 = new TransparencyPanel(0.7f);
        panel1.setBounds(0,0,this.getWidth(),this.getHeight());
        panel1.setLayout(null);
        this.add(panel1);

        BoxComponent image = new BoxComponent("凌驾.png");
        image.setBounds(0,0,this.getWidth(),this.getHeight());
        panel1.add(image);

        String Step = String.format("步数：%d",this.step);
        stepLabel = new JLabel(Step);
        stepLabel.setFont(new Font("宋体",Font.PLAIN,50));
        stepLabel.setForeground(Color.cyan);
        stepLabel.setBounds(getWidth() - 250,200,250,100);
        panel1.add(stepLabel);

        restartBtn = new JButton("再来一次");
        restartBtn.setBounds(getWidth() - 200,300,100,60);
        panel1.add(restartBtn);

        returnHomeBtn = new JButton("返回主页");
        returnHomeBtn.setBounds(getWidth() - 200,380,100,60);
        panel1.add(returnHomeBtn);

        restartBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VictoryWindow.this.setVisible(false);
                gameFrame.playSound(0,"Music/按钮.wav","按钮");
                controller.restartGame();
                gameFrame.stopBGM("凌驾");
            }
        });
        returnHomeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VictoryWindow.this.setVisible(false);
                gameFrame.playSound(0,"Music/按钮.wav","按钮");
                controller.restartGame();
                gameFrame.setVisible(false);
                gameFrame.getUpper().setVisible(true);
                gameFrame.stopBGM("凌驾");
            }
        });

    }


    public static class TransparencyPanel extends JPanel {
        private float transparency;
        public TransparencyPanel(Float transparency) {
            this.transparency = transparency;
            setOpaque(false);
        }
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(new Color(0, 0, 0, (int) (255 * transparency)));
            g.fillRect(0,0,getWidth(),getHeight());
        }

    }

}
