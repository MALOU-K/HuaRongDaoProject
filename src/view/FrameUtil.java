package view;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * This class is to create basic JComponent.
 */
public class FrameUtil {
    private static Sound sound;

    public static JLabel createJLabel(JFrame frame, Point location, int width, int height, String text) {
        JLabel jLabel = new JLabel(text);
        jLabel.setSize(width, height);
        jLabel.setLocation(location);
        frame.add(jLabel);
        return jLabel;
    }

    public static JLabel createJLabel(JFrame frame, String name, Font font, Point location, int width, int height) {
        JLabel label = new JLabel(name);
        label.setFont(font);
        label.setSize(width, height);
        label.setLocation(location);

        frame.add(label);
        return label;
    }

    public static JLabel createJLabel(JPanel frame, String name, Font font, Point location, int width, int height) {
        JLabel label = new JLabel(name);
        label.setFont(font);
        label.setSize(width, height);
        label.setLocation(location);
        frame.add(label);
        return label;
    }

    public static JTextField createJTextField(JFrame frame, Point location, int width, int height) {
        JTextField jTextField = new JTextField();
        jTextField.setSize(width, height);
        jTextField.setLocation(location);
        frame.add(jTextField);
        return jTextField;
    }

    public static JButton createButton(JFrame frame, String name, Point location, int width, int height) {
        JButton button = new JButton(name);
        button.setLocation(location);
        button.setSize(width, height);
        frame.add(button);
        return button;
    }

    public static JButton createButton(JPanel panel, String name, Point location, int width, int height) {
        JButton button = new JButton(name);
        button.setLocation(location);
        button.setSize(width, height);
        panel.add(button);
        return button;
    }

    public static JButton createImageButton(JFrame frame, String filename, Point location, int width, int height) {

        /*try (FileInputStream inputStream = new FileInputStream(filename)) {
            BufferedImage image = ImageIO.read(inputStream);
            BufferedImage scaledImage = scaleImage(image, width, height);
            ImageIcon image1 = new ImageIcon(scaledImage);
            JButton button = new JButton(image1);
            button.setLocation(location);
            button.setSize(width,height);
            button.setOpaque(false);
            button.setContentAreaFilled(false);
            button.setBorderPainted(false);
            frame.add(button);
            return button;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;*/
        ImageIcon originalIcon = new ImageIcon(filename);
        Image image = originalIcon.getImage().getScaledInstance(width,height,Image.SCALE_SMOOTH);
        ImageIcon image1 = new ImageIcon(image);

        JButton button = new JButton(image1);
        button.setLocation(location);
        button.setSize(width,height);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        frame.add(button);
        return button;

    }
    public static JButton createImageButton(JPanel frame, String filename, Point location, int width, int height) {
        ImageIcon originalIcon = new ImageIcon(filename);
        Image image = originalIcon.getImage().getScaledInstance(width,height,Image.SCALE_SMOOTH);
        ImageIcon image1 = new ImageIcon(image);

        JButton button = new JButton(image1);
        button.setLocation(location);
        button.setSize(width,height);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);


        frame.add(button);
        return button;


    }

    private static BufferedImage scaleImage(BufferedImage original, int width, int height) {
        BufferedImage resized = new BufferedImage(width, height, original.getType());
        Graphics2D g2d = resized.createGraphics();

        // 设置高质量缩放参数
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        // 绘制缩放后的图像
        g2d.drawImage(original, 0, 0, width, height, null);
        g2d.dispose();

        return resized;
    }





}
