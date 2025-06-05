import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;
import java.util.prefs.Preferences;
import java.util.Base64;
import java.io.*;

import model.MapModel;
import view.game.GameFrame;
import view.homepage.MapChoice;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;

public class HuarongLoginSystem extends JFrame {
    private JLabel welcomeLabel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JCheckBox showPasswordCheck;
    private JCheckBox rememberMeCheck;
    private JRadioButton normalUserRadio;
    private JRadioButton guestUserRadio;
    private JButton registerLink;

    // 用户数据库
    private Map<String, User> userDatabase = new HashMap<>();
    private Map<String, String> saltDatabase = new HashMap<>();

    // 用户数据库文件
    private static final String USER_DB_FILE = "users.dat";
    private static final String SALT_DB_FILE = "salts.dat";

    // 登录成功监听器
    private LoginSuccessListener loginSuccessListener;

    public interface LoginSuccessListener {
        void onLoginSuccess(String username);
    }

    public void setLoginSuccessListener(LoginSuccessListener listener) {
        this.loginSuccessListener = listener;
    }

    public HuarongLoginSystem() {
        initializeUI();
        loadUserData();
        setupRememberMe();
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int confirm = JOptionPane.showConfirmDialog(
                        HuarongLoginSystem.this,
                        "确定要退出游戏吗？",
                        "退出确认",
                        JOptionPane.YES_NO_OPTION
                );
                if (confirm == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
    }

    private void initializeUI() {
        setTitle("三国华容道 登录系统");
        setSize(450, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(240, 248, 255));

        // 使用GridBagLayout布局
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 5, 15);
        gbc.anchor = GridBagConstraints.WEST;

        // 标题
        JLabel titleLabel = new JLabel("三国华容道 登录系统", SwingConstants.CENTER);
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 24));
        titleLabel.setForeground(new Color(0, 100, 200));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(titleLabel, gbc);

        // 用户名输入
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(new JLabel("用户名:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        usernameField = new JTextField(20);
        add(usernameField, gbc);

        // 密码输入
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        add(new JLabel("密码:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        passwordField = new JPasswordField(20);
        add(passwordField, gbc);

        // 显示密码复选框
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        showPasswordCheck = new JCheckBox("显示密码");
        showPasswordCheck.addItemListener(e -> {
            passwordField.setEchoChar(showPasswordCheck.isSelected() ? (char) 0 : '*');
        });
        add(showPasswordCheck, gbc);

        // 登录方式单选按钮
        gbc.gridy = 4;
        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        radioPanel.setOpaque(false);

        ButtonGroup group = new ButtonGroup();
        normalUserRadio = new JRadioButton("普通用户", true);
        guestUserRadio = new JRadioButton("游客登录");

        normalUserRadio.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        guestUserRadio.setFont(new Font("微软雅黑", Font.PLAIN, 14));

        group.add(normalUserRadio);
        group.add(guestUserRadio);
        radioPanel.add(normalUserRadio);
        radioPanel.add(guestUserRadio);
        add(radioPanel, gbc);

        // 添加单选按钮监听器
        guestUserRadio.addActionListener(e -> {
            usernameField.setEnabled(false);
            passwordField.setEnabled(false);
            usernameField.setText("");
            passwordField.setText("");
        });

        normalUserRadio.addActionListener(e -> {
            usernameField.setEnabled(true);
            passwordField.setEnabled(true);
            usernameField.requestFocus();
        });

        // 记住我复选框
        gbc.gridy = 5;
        rememberMeCheck = new JCheckBox("记住我");
        rememberMeCheck.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        add(rememberMeCheck, gbc);

        // 登录按钮
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        loginButton = new JButton("登录");
        loginButton.setFont(new Font("微软雅黑", Font.BOLD, 16));
        loginButton.setBackground(new Color(50, 205, 50));
        loginButton.setForeground(Color.WHITE);
        loginButton.setPreferredSize(new Dimension(120, 40));
        loginButton.addActionListener(e -> handleLogin());
        add(loginButton, gbc);

        // 注册链接
        gbc.gridy = 7;
        registerLink = new JButton("没有账号？立即注册");
        registerLink.setBorderPainted(false);
        registerLink.setContentAreaFilled(false);
        registerLink.setForeground(new Color(0, 100, 200));
        registerLink.setFont(new Font("微软雅黑", Font.PLAIN, 13));
        registerLink.addActionListener(e -> showRegistrationDialog());
        registerLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        add(registerLink, gbc);

        // 窗口打开时自动获取焦点
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                if (normalUserRadio.isSelected()) {
                    usernameField.requestFocus();
                }
            }
        });
    }

    private void handleLogin() {
        if (guestUserRadio.isSelected()) {
            // 游客登录
            openGameScreen("游客");
        } else {
            // 普通用户登录
            String username = usernameField.getText().trim();
            char[] password = passwordField.getPassword();

            if (username.isEmpty()) {
                showError("请输入用户名");
                return;
            }

            if (password.length == 0) {
                showError("请输入密码");
                return;
            }

            if (!userDatabase.containsKey(username)) {
                showError("用户名不存在");
                return;
            }

            User user = userDatabase.get(username);
            String salt = saltDatabase.get(username);
            String hashedInput = hashPassword(new String(password), salt);

            if (hashedInput.equals(user.passwordHash)) {
                // 登录成功
                if (rememberMeCheck.isSelected()) {
                    saveCredentials(username, password);
                } else {
                    clearSavedCredentials();
                }

                openGameScreen(username);
            } else {
                showError("密码错误");
            }

            // 清理密码内存
            Arrays.fill(password, '0');
        }
    }

    private void showRegistrationDialog() {
        JDialog dialog = new JDialog(this, "用户注册", true);
        dialog.setSize(350, 250);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel userLabel = new JLabel("用户名:");
        JTextField regUsername = new JTextField(15);

        JLabel passLabel = new JLabel("密码:");
        JPasswordField regPassword = new JPasswordField(15);

        JLabel confirmLabel = new JLabel("确认密码:");
        JPasswordField regConfirmPassword = new JPasswordField(15);

        JCheckBox showRegPassword = new JCheckBox("显示密码");
        showRegPassword.addItemListener(e -> {
            char echoChar = showRegPassword.isSelected() ? (char) 0 : '*';
            regPassword.setEchoChar(echoChar);
            regConfirmPassword.setEchoChar(echoChar);
        });

        JButton registerBtn = new JButton("注册");
        registerBtn.setBackground(new Color(30, 144, 255));
        registerBtn.setForeground(Color.WHITE);
        registerBtn.addActionListener(e -> {
            String username = regUsername.getText().trim();
            char[] password = regPassword.getPassword();
            char[] confirmPassword = regConfirmPassword.getPassword();

            if (username.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "用户名不能为空", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (password.length < 6) {
                JOptionPane.showMessageDialog(dialog, "密码长度至少为6位", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!Arrays.equals(password, confirmPassword)) {
                JOptionPane.showMessageDialog(dialog, "两次输入的密码不一致", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (userDatabase.containsKey(username)) {
                JOptionPane.showMessageDialog(dialog, "用户名已存在", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 生成盐值
            String salt = generateSalt();
            String hashedPassword = hashPassword(new String(password), salt);

            // 保存用户
            userDatabase.put(username, new User(username, hashedPassword));
            saltDatabase.put(username, salt);

            // 保存到文件
            saveToFile();

            JOptionPane.showMessageDialog(dialog, "注册成功！", "成功", JOptionPane.INFORMATION_MESSAGE);
            dialog.dispose();

            // 清理密码内存
            Arrays.fill(password, '0');
            Arrays.fill(confirmPassword, '0');
        });

        panel.add(userLabel);
        panel.add(regUsername);
        panel.add(passLabel);
        panel.add(regPassword);
        panel.add(confirmLabel);
        panel.add(regConfirmPassword);
        panel.add(new JLabel());
        panel.add(showRegPassword);
        panel.add(new JLabel());
        panel.add(registerBtn);

        dialog.add(panel);
        dialog.setVisible(true);
    }

    private void openGameScreen(String username) {
        // 创建欢迎界面
        GameScreen gameScreen = new GameScreen(username);
        gameScreen.setWelcomeMessage("欢迎, " + username + "!");
        gameScreen.setTitle("三国华容道 - " + username);
        gameScreen.setLocationRelativeTo(null);
        gameScreen.setVisible(true);

        // 关闭登录窗口
        this.dispose();

        // 触发登录成功事件
        if (loginSuccessListener != null) {
            loginSuccessListener.onLoginSuccess(username);
        }
    }

    private void setupRememberMe() {
        Preferences prefs = Preferences.userNodeForPackage(HuarongLoginSystem.class);
        String savedUsername = prefs.get("savedUsername", "");
        String savedPassword = prefs.get("savedPassword", "");
        if (!savedUsername.isEmpty()) {
            usernameField.setText(savedUsername);
            if (!savedPassword.isEmpty()) {
                try {
                    byte[] decodedBytes = Base64.getDecoder().decode(savedPassword);
                    passwordField.setText(new String(decodedBytes));
                } catch (IllegalArgumentException e) {
                    System.err.println("Saved password decoding failed");
                }
            }
            rememberMeCheck.setSelected(true);
        }
    }

    private void saveCredentials(String username, char[] password) {
        Preferences prefs = Preferences.userNodeForPackage(HuarongLoginSystem.class);
        prefs.put("savedUsername", username);
        prefs.put("savedPassword", Base64.getEncoder().encodeToString(new String(password).getBytes()));
    }

    private void clearSavedCredentials() {
        Preferences prefs = Preferences.userNodeForPackage(HuarongLoginSystem.class);
        prefs.remove("savedUsername");
        prefs.remove("savedPassword");
    }

    private void loadUserData() {
        // 尝试从文件加载用户数据
        if (!loadFromFile()) {
            // 文件不存在时创建示例用户
            String salt = generateSalt();
            userDatabase.put("admin", new User("admin", hashPassword("admin123", salt)));
            saltDatabase.put("admin", salt);
            saveToFile(); // 保存到文件
        }
    }

    // 从文件加载用户数据
    private boolean loadFromFile() {
        try (ObjectInputStream userIn = new ObjectInputStream(new FileInputStream(USER_DB_FILE));
             ObjectInputStream saltIn = new ObjectInputStream(new FileInputStream(SALT_DB_FILE))) {

            userDatabase = (Map<String, User>) userIn.readObject();
            saltDatabase = (Map<String, String>) saltIn.readObject();
            return true;
        } catch (FileNotFoundException e) {
            System.out.println("用户数据库文件不存在，将创建新数据库");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "加载用户数据失败: " + e.getMessage(),
                    "错误", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    // 保存用户数据到文件
    private void saveToFile() {
        try (ObjectOutputStream userOut = new ObjectOutputStream(new FileOutputStream(USER_DB_FILE));
             ObjectOutputStream saltOut = new ObjectOutputStream(new FileOutputStream(SALT_DB_FILE))) {

            userOut.writeObject(userDatabase);
            saltOut.writeObject(saltDatabase);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "保存用户数据失败: " + e.getMessage(),
                    "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    // 密码哈希方法（SHA-256 + 盐值）
    private String hashPassword(String password, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            String combined = salt + password;
            byte[] hashedBytes = md.digest(combined.getBytes());

            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("密码加密失败", e);
        }
    }

    // 生成随机盐值
    private String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "错误", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HuarongLoginSystem loginSystem = new HuarongLoginSystem();
            loginSystem.setVisible(true);
        });
    }

    // 用户内部类（实现Serializable以支持序列化）
    private static class User implements Serializable {
        String username;
        String passwordHash;

        User(String username, String passwordHash) {
            this.username = username;
            this.passwordHash = passwordHash;
        }
    }

    // 游戏欢迎界面
    private class GameScreen extends JFrame {
        private JLabel welcomeLabel;
        private String username; // 添加用户名字段

        public GameScreen(String username) {
            this.username = username;
            setTitle("三国华容道 - " + username);
            setSize(800, 600);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setLayout(new BorderLayout());
            getContentPane().setBackground(new Color(245, 245, 245));

            // 顶部欢迎消息
            welcomeLabel = new JLabel("", SwingConstants.CENTER);
            welcomeLabel.setFont(new Font("微软雅黑", Font.BOLD, 36));
            welcomeLabel.setForeground(new Color(70, 130, 180));
            welcomeLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
            add(welcomeLabel, BorderLayout.NORTH);

            // 主内容面板
            JPanel mainPanel = new JPanel(new BorderLayout());
            mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
            mainPanel.setBackground(Color.WHITE);
            mainPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(200, 200, 220), 2),
                    BorderFactory.createEmptyBorder(20, 20, 20, 20)
            ));

            // 游戏标题
            JLabel title = new JLabel("三国华容道", SwingConstants.CENTER);
            title.setFont(new Font("微软雅黑", Font.BOLD, 48));
            title.setForeground(new Color(178, 34, 34));
            title.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
            mainPanel.add(title, BorderLayout.NORTH);

            // 游戏说明
            JTextArea desc = new JTextArea();
            desc.setText("游戏规则说明：\n\n"
                    + "1. 移动棋子使曹操从底部出口逃脱\n"
                    + "2. 不同棋子移动方式不同\n"
                    + "3. 曹操需要两个空格才能移动\n"
                    + "4. 所有棋子不能重叠或移出棋盘\n"
                    + "5. 只能水平或垂直移动棋子");
            desc.setFont(new Font("宋体", Font.PLAIN, 22));
            desc.setEditable(false);
            desc.setLineWrap(true);
            desc.setWrapStyleWord(true);
            desc.setBackground(Color.WHITE);
            desc.setMargin(new Insets(10, 20, 20, 20));
            mainPanel.add(desc, BorderLayout.CENTER);

            // 按钮面板
            JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 20, 20));
            buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
            buttonPanel.setBackground(Color.WHITE);

            // 继续游戏按钮（新增）
            JButton continueBtn = new JButton("继续游戏");
            styleButton(continueBtn, new Color(30, 144, 255)); // 蓝色按钮
            continueBtn.addActionListener(e -> {
                // 检查保存文件是否存在
                File saveFile = new File("saves/" + username + ".sav");
                if (!saveFile.exists()) {
                    JOptionPane.showMessageDialog(GameScreen.this,
                            "没有找到保存的游戏", "提示", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                // 使用默认地图创建游戏框架（加载时会覆盖）
                MapModel defaultMap = new MapModel(new int[][]{
                        {4, 3, 3, 5},
                        {4, 3, 3, 5},
                        {6, 2, 2, 7},
                        {6, 1, 1, 7},
                        {1, 0, 0, 1}
                });
                MapChoice mapChoice = new MapChoice(600, 500, username);
                GameFrame gameFrame = new GameFrame(600, 500, defaultMap, mapChoice, username);

                gameFrame.loadGame(); // 加载保存的游戏
                gameFrame.setVisible(true);
                dispose(); // 关闭欢迎界面
            });

            // 开始新游戏按钮
            JButton startBtn = new JButton("开始新游戏");
            styleButton(startBtn, new Color(50, 205, 50)); // 绿色按钮
            startBtn.addActionListener(e -> {
                // 创建游戏地图模型
                MapChoice mapChoice = new MapChoice(600, 500, username);
                mapChoice.setVisible(true);
                dispose(); // 关闭当前窗口
            });

            buttonPanel.add(continueBtn);
            buttonPanel.add(startBtn);
            mainPanel.add(buttonPanel, BorderLayout.SOUTH);

            add(mainPanel, BorderLayout.CENTER);
        }

        // 设置欢迎消息
        public void setWelcomeMessage(String message) {
            welcomeLabel.setText(message);
        }

        // 按钮样式统一方法
        private void styleButton(JButton button, Color bgColor) {
            button.setFont(new Font("微软雅黑", Font.BOLD, 24));
            button.setBackground(bgColor);
            button.setForeground(Color.WHITE);
            button.setFocusPainted(false);
            button.setBorder(BorderFactory.createRaisedBevelBorder());
            button.setPreferredSize(new Dimension(200, 50));
        }
    }
}