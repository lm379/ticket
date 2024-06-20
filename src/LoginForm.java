import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.HttpURLConnection;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import org.json.JSONObject;
// import com.formdev.flatlaf.FlatLightLaf;

public class LoginForm extends JFrame {
    private JTextField accountField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private String apiAddress = "http://localhost:15379/";

    public LoginForm() {
        super("登录");
        setSize(450, 250);
        // 创建登录面板
        JPanel loginPanel = new JPanel(new GridLayout(3, 2));
        // 添加账户输入框
        loginPanel.add(new JLabel("账户:", JLabel.CENTER));
        accountField = new JTextField(20);
        loginPanel.add(accountField);

        // 添加密码输入框
        loginPanel.add(new JLabel("密码:", JLabel.CENTER));
        passwordField = new JPasswordField(20);
        loginPanel.add(passwordField);

        // 添加登录按钮并添加事件监听器
        registerButton = new JButton("注册");
        loginButton = new JButton("登录");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 获取账户和密码
                String account = accountField.getText();
                String password = new String(passwordField.getPassword());
                // 发送登录请求
                sendLoginRequest(account, password, "login");
            }
        });
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String account = accountField.getText();
                String password = new String(passwordField.getPassword());
                sendRegisterRequest(account, password, "register");
            }
        });
        loginPanel.add(registerButton);
        loginPanel.add(loginButton);

        // 设置窗口属性
        add(loginPanel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void sendRegisterRequest(String account, String password, String apiEndpoint) {
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                if (account.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "账号或密码不能为空");
                    return null;
                }
                // 创建JSON对象并填充账户和密码
                JSONObject jsonInput = new JSONObject();
                jsonInput.put("account", account);
                jsonInput.put("password", password);
                String jsonInputString = jsonInput.toString();
                // 发送JSON数据
                try {
                    var request = HttpRequest.newBuilder().uri(URI.create(apiAddress + apiEndpoint))
                            .header("Content-Type", "application/json")
                            .POST(HttpRequest.BodyPublishers.ofString(jsonInputString)).build();
                    var client = HttpClient.newHttpClient();
                    var response = client.send(request, HttpResponse.BodyHandlers.ofString());

                    // 读取响应
                    int responseCode = response.statusCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        JOptionPane.showMessageDialog(null, "注册成功");
                    } else {
                        // 读取错误信息
                        SwingUtilities
                                .invokeLater(() -> JOptionPane.showMessageDialog(null,
                                        "状态码:" + response.statusCode() + "\n" + response.body(),
                                        "注册失败", JOptionPane.ERROR_MESSAGE));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    SwingUtilities.invokeLater(
                            () -> JOptionPane.showMessageDialog(null, e.getMessage(), "注册失败",
                                    JOptionPane.ERROR_MESSAGE));
                }
                return null;
            }
        };
        worker.execute();
    }

    private void sendLoginRequest(String account, String password, String apiEndpoint) {
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                if (account.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "账号或密码不能为空");
                    return null;
                }
                // 创建JSON对象并填充账户和密码
                JSONObject jsonInput = new JSONObject();
                jsonInput.put("account", account);
                jsonInput.put("password", password);
                String jsonInputString = jsonInput.toString();
                // 发送JSON数据
                try {
                    var request = HttpRequest.newBuilder().uri(URI.create(apiAddress + apiEndpoint))
                            .header("Content-Type", "application/json")
                            .POST(HttpRequest.BodyPublishers.ofString(jsonInputString)).build();
                    var client = HttpClient.newHttpClient();
                    var response = client.send(request, HttpResponse.BodyHandlers.ofString());

                    // 读取响应
                    int responseCode = response.statusCode();
                    String resArray = response.body().toString();
                    JSONObject resObject = new JSONObject(resArray);
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        JOptionPane.showMessageDialog(null, "登录成功");
                        LoginForm.this.setVisible(false); // 隐藏登录窗体
                        if (resObject.getInt("isAdmin") == 0) {
                            PassengerManageForm pmf = new PassengerManageForm();
                            PassengerManageForm.BookTicketForm btf = pmf.new BookTicketForm();
                            btf.setVisible(true);
                        } else if (resObject.getInt("isAdmin") == 1) {
                            new TicketManageSystem().setVisible(true); // 显示PassengerManageForm窗体
                            LoginForm.this.setVisible(false);
                        }
                    } else {
                        // 读取错误信息
                        SwingUtilities
                                .invokeLater(() -> JOptionPane.showMessageDialog(null, "密码错误",
                                        "登录失败", JOptionPane.ERROR_MESSAGE));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    SwingUtilities.invokeLater(
                            () -> JOptionPane.showMessageDialog(null, e.getMessage(), "登录失败",
                                    JOptionPane.ERROR_MESSAGE));
                }
                return null;
            }
        };
        worker.execute();
    }

//     public static void main(String[] args) {
//         FlatLightLaf.setup();
//         SwingUtilities.invokeLater(new Runnable() {
//             @Override
//             public void run() {
//                 new LoginForm().setVisible(true);
//             }
//         });
//     }
}
