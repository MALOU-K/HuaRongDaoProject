import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class windowTest {
    public static void main(String[] args) {
        JWindow window = new JWindow();
        window.setSize(400, 500);
        window.setLayout(null);
        window.setLocationRelativeTo(null);
        window.setBackground(new Color(0, 0, 0, 0));


        TransparencyPanel panel1 = new TransparencyPanel(0.5f);
        panel1.setBounds(0,0,window.getWidth(),window.getHeight());
        panel1.setLayout(null);
        window.add(panel1);

        ImageIcon image = new ImageIcon("E:\\小MALOU\\Project\\曹操.jpg");
        JLabel label = new JLabel(image);
        label.setSize(300,300);
        panel1.add(label);

        window.setVisible(true);
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
