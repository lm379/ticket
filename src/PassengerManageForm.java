import javax.swing.table.DefaultTableModel;
import org.json.JSONArray;
import org.json.JSONObject;
import javax.swing.*;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class PassengerManageForm extends JFrame {
    private JComboBox<String> passengerTypeComboBox;
    private JTextField passengerInfoTextField;
    private JTextField passengerNameTextField;
    private JComboBox<String> passengerSexComboBox;
    private JComboBox<String> passengerIdTyperComboBox;
    private JTextField passengerIdTextField;
    private JTextField passengerBirthTextField;
    private JTextField passengerTeleTextField;
    private JTextField passengerTicketNumberTextField;
    private Map<String, String> conditionMap;
    private String apiAddress = "http://localhost:15379/";

    class QueryInfoForm extends JFrame {
        public QueryInfoForm() {
            super("查询乘客信息");
            setSize(500, 300);
            setLayout(new GridLayout(3, 2));
            String[] searchTypes = { "姓名", "身份证号" };
            passengerTypeComboBox = new JComboBox<>(searchTypes);
            passengerTypeComboBox.setSelectedIndex(0);
            passengerInfoTextField = new JTextField(20);
            passengerInfoTextField.setToolTipText("请在此输入乘客信息");
            JButton queryInfoButton = new JButton("点击查询乘客信息");

            queryInfoButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String selectedSearchType = (String) passengerTypeComboBox.getSelectedItem();
                    // 获取输入框的内容
                    String inputText = passengerInfoTextField.getText();
                    if (inputText.isEmpty()) {
                        JOptionPane.showMessageDialog(QueryInfoForm.this, "输入不能为空", "错误",
                                JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                    // 根据选择调用相应的接口
                    if ("姓名".equals(selectedSearchType)) {
                        String apiEndpoint = "queryTickets?PName="
                                + URLEncoder.encode(inputText, StandardCharsets.UTF_8);
                        queryTicketInfo(apiEndpoint);
                    } else if ("身份证号".equals(selectedSearchType)) {
                        String apiEndpoint = "queryTicketsById?PassengerIdentity="
                                + URLEncoder.encode(inputText, StandardCharsets.UTF_8);
                        queryTicketInfo(apiEndpoint);
                    }
                }
            });
            JLabel l1 = new JLabel("查询方式：");
            JLabel l2 = new JLabel("乘客信息：");

            l1.setHorizontalAlignment(JLabel.CENTER);
            l2.setHorizontalAlignment(JLabel.CENTER);
            add(l1);
            add(passengerTypeComboBox);
            add(l2);
            add(passengerInfoTextField);
            add(new JLabel(""));
            add(queryInfoButton);
            // add(formPanel);
            // setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
            // pack();
            setLocationRelativeTo(null);
        }

        private void queryTicketInfo(String apiEndpoint) {
            // 在后台线程中执行网络请求，避免阻塞事件调度线程
            SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {
                    // 构建请求URL
                    URI uri = new URI(apiAddress + apiEndpoint);
                    URL url = uri.toURL();
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");

                    int responseCode = conn.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        String inputLine;
                        StringBuilder response = new StringBuilder();

                        while ((inputLine = in.readLine()) != null) {
                            response.append(inputLine);
                        }
                        in.close();

                        // 解析JSON数据并更新表格模型
                        String jsonString = response.toString();
                        // System.out.print(jsonString);
                        SwingUtilities.invokeLater(() -> {
                            try {
                                JSONArray jsonArray = new JSONArray(response.toString());
                                showResults(jsonArray);
                            } catch (Exception e) {
                                // tableModel.setRowCount(0); // 清空表格数据
                                JOptionPane.showMessageDialog(PassengerManageForm.this, jsonString, "错误",
                                        JOptionPane.INFORMATION_MESSAGE);
                            }
                        });
                    } else {
                        // 如果请求失败，显示错误信息
                        SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(PassengerManageForm.this,
                                "查询失败，响应码：" + responseCode, "错误", JOptionPane.ERROR_MESSAGE));
                    }
                    return null;
                }
            };
            worker.execute();
        }

        private void showResults(JSONArray jsonArray) {
            // 创建一个包含表格的滚动面板
            String[] columnNames = { "姓名", "证件类型", "证件号码", "手机号", "票号", "航班号", "始发城市", "目的城市", "航行里程(Km)", "起飞时间" };
            DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
            JTable frameTable = new JTable(tableModel);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                tableModel.addRow(new Object[] {
                        obj.getString("PName"),
                        obj.getString("IdentityStyle"),
                        obj.getString("PassengerIdentity"),
                        obj.getString("PTele"),
                        obj.getInt("PTicketNumber"),
                        obj.getString("FlightNumber"),
                        obj.getString("fromcity"),
                        obj.getString("tocity"),
                        obj.getInt("mileAge") + "km",
                        obj.getString("departureTime")
                });
            }

            JScrollPane scrollPane = new JScrollPane(frameTable);
            scrollPane.setPreferredSize(new Dimension(1200, 300));

            // 显示包含滚动面板的对话框
            JFrame frame = new JFrame("查询结果");
            frame.getContentPane().add(scrollPane);
            frame.pack();
            frame.setLocation(getMousePosition());
            frame.setVisible(true);
        }
    }

    public class BookTicketForm extends JFrame {
        public BookTicketForm() {
            super("预定机票");
            setSize(600, 400);
            setLayout(new GridLayout(8, 2));
            String[] sexTypes = { "男", "女" };
            String[] idTypes = { "大陆居民身份证", "护照", "港澳通行证" };

            passengerNameTextField = new JTextField(20);
            passengerNameTextField.setToolTipText("请输入乘客姓名");
            passengerIdTextField = new JTextField(18);
            passengerIdTextField.setToolTipText("请输入乘客身份证号");
            passengerSexComboBox = new JComboBox<>(sexTypes);
            passengerSexComboBox.setSelectedIndex(0);
            passengerIdTyperComboBox = new JComboBox<>(idTypes);
            passengerIdTyperComboBox.setSelectedIndex(0);
            passengerBirthTextField = new JTextField(20);
            passengerBirthTextField.setToolTipText("请输入乘客出生日期");
            passengerTeleTextField = new JTextField(20);
            passengerTeleTextField.setToolTipText("请输入乘客电话号码");
            passengerTicketNumberTextField = new JTextField();
            passengerTicketNumberTextField.setToolTipText("请输入乘客机票号");
            JButton BookTicketButton = new JButton("预定机票");
            JLabel l1 = new JLabel("姓名");
            JLabel l2 = new JLabel("性别");
            JLabel l3 = new JLabel("证件类型");
            JLabel l4 = new JLabel("证件号");
            JLabel l5 = new JLabel("出生日期");
            JLabel l6 = new JLabel("电话");
            JLabel l7 = new JLabel("机票号");
            l1.setHorizontalAlignment(JLabel.CENTER);
            l2.setHorizontalAlignment(JLabel.CENTER);
            l3.setHorizontalAlignment(JLabel.CENTER);
            l4.setHorizontalAlignment(JLabel.CENTER);
            l5.setHorizontalAlignment(JLabel.CENTER);
            l6.setHorizontalAlignment(JLabel.CENTER);
            l7.setHorizontalAlignment(JLabel.CENTER);

            add(l1);
            add(passengerNameTextField);
            add(l2);
            add(passengerSexComboBox);
            add(l3);
            add(passengerIdTyperComboBox);
            add(l4);
            add(passengerIdTextField);
            add(l5);
            add(passengerBirthTextField);
            add(l6);
            add(passengerTeleTextField);
            add(l7);
            add(passengerTicketNumberTextField);
            add(new JLabel());
            add(BookTicketButton);

            BookTicketButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    handleBookTicket("addPassenger");
                }
            });
            passengerIdTextField.addFocusListener(new FocusAdapter() {
                @Override
                public void focusLost(FocusEvent e) {
                    String idNumber = passengerIdTextField.getText();
                    if (idNumber.length() == 18) {
                        // 提取出生日期
                        String birthDate = idNumber.substring(6, 10) + "-" + idNumber.substring(10, 12) + "-"
                                + idNumber.substring(12, 14);
                        passengerBirthTextField.setText(birthDate);

                        // 提取性别，身份证倒数第二位为奇数表示男，偶数表示女
                        char genderCode = idNumber.charAt(16);
                        int sexIndex = (genderCode % 2 == 0) ? 1 : 0; // 0为男，1为女
                        passengerSexComboBox.setSelectedIndex(sexIndex);
                    } else {
                        JOptionPane.showMessageDialog(null, "身份证号码必须为18位", "输入错误", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLocationRelativeTo(null);
        }

        private void handleBookTicket(String apiEndpoint) {
            // 在后台线程中执行网络请求，避免阻塞事件调度线程
            SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {
                    String PName = passengerNameTextField.getText();
                    String gender = (String) passengerSexComboBox.getSelectedItem();
                    String IdentityStyle = (String) passengerIdTyperComboBox.getSelectedItem();
                    String PassengerIdentity = passengerIdTextField.getText();
                    String birthday = passengerBirthTextField.getText();
                    String PTele = passengerTeleTextField.getText();
                    int PTicketNumber;
                    try {
                        PTicketNumber = Integer.parseInt((passengerTicketNumberTextField.getText()));

                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(PassengerManageForm.this, "票号输入无效", "错误",
                                JOptionPane.INFORMATION_MESSAGE);
                        return null;
                    }

                    if (PName.isEmpty() || gender.isEmpty() || IdentityStyle.isEmpty() || PassengerIdentity.isEmpty()
                            || birthday.isEmpty() || PTele.isEmpty()) {
                        JOptionPane.showMessageDialog(PassengerManageForm.this, "输入不能为空", "错误",
                                JOptionPane.INFORMATION_MESSAGE);
                        return null;
                    }

                    JSONObject json = new JSONObject();
                    json.put("PName", PName);
                    json.put("gender", gender);
                    json.put("IdentityStyle", IdentityStyle);
                    json.put("PassengerIdentity", PassengerIdentity);
                    json.put("birthday", birthday);
                    json.put("PTele", PTele);
                    json.put("PTicketNumber", PTicketNumber);

                    String jsonInputString = json.toString();
                    try {
                        var request = HttpRequest.newBuilder().uri(URI.create(apiAddress + apiEndpoint))
                                .header("Content-Type", "application/json")
                                .POST(HttpRequest.BodyPublishers.ofString(jsonInputString)).build();
                        var client = HttpClient.newHttpClient();
                        var response = client.send(request, HttpResponse.BodyHandlers.ofString());

                        // 读取响应
                        int responseCode = response.statusCode();
                        if (responseCode == HttpURLConnection.HTTP_OK) {
                            JOptionPane.showMessageDialog(null, "乘客信息添加成功");
                        } else {
                            // 读取错误信息
                            SwingUtilities
                                    .invokeLater(() -> JOptionPane.showMessageDialog(null, response.statusCode(),
                                            "添加失败", JOptionPane.ERROR_MESSAGE));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        SwingUtilities.invokeLater(
                                () -> JOptionPane.showMessageDialog(null, e.getMessage(), "添加失败",
                                        JOptionPane.ERROR_MESSAGE));
                    }

                    return null;
                }
            };
            worker.execute();
        }
    }

    class DeletePassengerForm extends JFrame {
        public DeletePassengerForm() {
            super("退票管理");
            setSize(500, 300);
            setLayout(new GridLayout(3, 2));
            String[] searchTypes = { "姓名", "身份证号", "电话号码" };
            conditionMap = new HashMap<>();
            conditionMap.put("姓名", "PName");
            conditionMap.put("身份证号", "PassengerIdentity");
            conditionMap.put("电话号码", "PTele");

            passengerTypeComboBox = new JComboBox<>(searchTypes);
            passengerTypeComboBox.setSelectedIndex(0);
            passengerInfoTextField = new JTextField(20);
            passengerInfoTextField.setToolTipText("请输入乘客信息");
            JButton updateInfButton = new JButton("退款");
            JButton deleteInfoButton = new JButton("删除乘客信息");

            updateInfButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    handleDeletePassenger("updatePaymentState");
                }
            });
            deleteInfoButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    handleDeletePassenger("deletePassenger");
                }
            });

            JLabel l1 = new JLabel("查询方式：");
            JLabel l2 = new JLabel("乘客信息：");
            l1.setHorizontalAlignment(JLabel.CENTER);
            l2.setHorizontalAlignment(JLabel.CENTER);

            add(l1);
            add(passengerTypeComboBox);
            add(l2);
            add(passengerInfoTextField);
            add(updateInfButton);
            add(deleteInfoButton);
            // add(formPanel);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLocationRelativeTo(null);
        }

        private void handleDeletePassenger(String apiEndpoint) {
            SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {
                    String passengerInfo = (String) passengerTypeComboBox.getSelectedItem();
                    String selectField = conditionMap.get(passengerInfo);
                    String Pinfo = passengerInfoTextField.getText();
                    if (Pinfo == null) {
                        JOptionPane.showMessageDialog(PassengerManageForm.this, "输入不能为空", "错误",
                                JOptionPane.INFORMATION_MESSAGE);
                        return null;
                    }
                    JSONObject json = new JSONObject();
                    json.put("selectField", selectField);
                    json.put("Pinfo", Pinfo);

                    String jsonInputString = json.toString();
                    // System.out.println(json.toString());
                    try {
                        var request = HttpRequest.newBuilder().uri(URI.create(apiAddress + apiEndpoint))
                                .header("Content-Type", "application/json")
                                .POST(HttpRequest.BodyPublishers.ofString(jsonInputString)).build();
                        var client = HttpClient.newHttpClient();
                        var response = client.send(request, HttpResponse.BodyHandlers.ofString());

                        // 读取响应
                        int responseCode = response.statusCode();
                        if (responseCode == HttpURLConnection.HTTP_OK) {
                            JOptionPane.showMessageDialog(null, "修改成功");
                        } else {
                            // 读取错误信息
                            SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(null, response.statusCode(),
                                    "修改失败", JOptionPane.ERROR_MESSAGE));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        SwingUtilities.invokeLater(
                                () -> JOptionPane.showMessageDialog(null, e.getMessage(), "添加失败",
                                        JOptionPane.ERROR_MESSAGE));
                    }
                    return null;
                }
            };
            worker.execute();
        }
    }

    class UpdatePassengerForm extends JFrame {
        JTextField oldpassengerInfoTextField;
        JTextField newpassengerInfoTextField;
        JComboBox<String> oldpassengerTypeComboBox;
        JComboBox<String> newpassengerTypeComboBox;

        public UpdatePassengerForm() {
            super("乘客信息修改");
            setSize(450, 250);
            setLayout(new GridLayout(5, 2));
            String[] oldTypes = { "姓名", "证件号" };
            String[] newTypes = { "姓名", "证件号", "电话号码", "票号" };
            conditionMap = new HashMap<>();
            conditionMap.put("姓名", "PName");
            conditionMap.put("证件号", "PassengerIdentity");
            conditionMap.put("电话号码", "PTele");
            conditionMap.put("票号", "PTicketNumber");

            oldpassengerTypeComboBox = new JComboBox<>(oldTypes);
            newpassengerTypeComboBox = new JComboBox<>(newTypes);
            oldpassengerTypeComboBox.setSelectedIndex(0);
            newpassengerTypeComboBox.setSelectedIndex(0);
            oldpassengerInfoTextField = new JTextField();
            oldpassengerInfoTextField.setToolTipText("请输入需要修改的乘客信息");
            newpassengerInfoTextField = new JTextField();
            newpassengerInfoTextField.setToolTipText("请输入修改后的信息");
            JButton updateInfoButton = new JButton("提交修改");

            JLabel l1 = new JLabel("待变更乘客查询方式：");
            JLabel l2 = new JLabel("乘客信息：");
            JLabel l3 = new JLabel("需要修改的部分：");
            JLabel l4 = new JLabel("修改后的信息：");
            // 设置居中
            l1.setHorizontalAlignment(JLabel.CENTER);
            l2.setHorizontalAlignment(JLabel.CENTER);
            l3.setHorizontalAlignment(JLabel.CENTER);
            l4.setHorizontalAlignment(JLabel.CENTER);

            add(l1);
            add(oldpassengerTypeComboBox);
            add(l2);
            add(oldpassengerInfoTextField);
            add(l3);
            add(newpassengerTypeComboBox);
            add(l4);
            add(newpassengerInfoTextField);
            add(new JLabel(""));
            add(updateInfoButton);

            updateInfoButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    handleUpdatepassenger("updatePassenger");
                }
            });
        }

        private void handleUpdatepassenger(String apiEndpoint) {
            SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {
                    String oldpassengerType = (String) oldpassengerTypeComboBox.getSelectedItem();
                    String newpassengerType = (String) newpassengerTypeComboBox.getSelectedItem();
                    String oldSelectField = conditionMap.get(oldpassengerType);
                    String newSelectField = conditionMap.get(newpassengerType);
                    String oldPinfo = oldpassengerInfoTextField.getText();
                    String newPinfo = newpassengerInfoTextField.getText();
                    if (oldPinfo.isEmpty() || newPinfo.isEmpty()) {
                        JOptionPane.showMessageDialog(PassengerManageForm.this, "输入不能为空", "错误",
                                JOptionPane.INFORMATION_MESSAGE);
                        return null;
                    }
                    JSONObject json = new JSONObject();
                    json.put("oldSelectField", oldSelectField);
                    json.put("newSelectField", newSelectField);
                    json.put("oldPinfo", oldPinfo);
                    json.put("newPinfo", newPinfo);

                    String jsonInputString = json.toString();
                    // System.out.println(json.toString());
                    try {
                        var request = HttpRequest.newBuilder().uri(URI.create(apiAddress + apiEndpoint))
                                .header("Content-Type", "application/json")
                                .POST(HttpRequest.BodyPublishers.ofString(jsonInputString)).build();
                        var client = HttpClient.newHttpClient();
                        var response = client.send(request, HttpResponse.BodyHandlers.ofString());

                        // 读取响应
                        int responseCode = response.statusCode();
                        if (responseCode == HttpURLConnection.HTTP_OK) {
                            JOptionPane.showMessageDialog(null, "修改成功");
                        } else {
                            // 读取错误信息
                            SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(null, response.statusCode(),
                                    "修改失败", JOptionPane.ERROR_MESSAGE));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        SwingUtilities.invokeLater(
                                () -> JOptionPane.showMessageDialog(null, e.getMessage(), "添加失败",
                                        JOptionPane.ERROR_MESSAGE));
                    }
                    return null;
                }
            };
            worker.execute();
        }
    }

    public PassengerManageForm() {
        super("乘客信息管理");
        setPreferredSize(new Dimension(400, 400));
        setLayout(new GridLayout(4, 1));
        JButton BookTicketButton = new JButton("预定机票");
        JButton queryInfoButton = new JButton("查询乘客信息");
        JButton deltePassengerButton = new JButton("退票管理");
        JButton updatePassengerButton = new JButton("修改乘客信息");

        queryInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                QueryInfoForm queryInfoForm = new QueryInfoForm();
                queryInfoForm.setVisible(true);
            }
        });

        BookTicketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BookTicketForm bookTicketForm = new BookTicketForm();
                bookTicketForm.setVisible(true);
            }
        });

        deltePassengerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DeletePassengerForm deletePassengerForm = new DeletePassengerForm();
                deletePassengerForm.setVisible(true);
            }
        });

        updatePassengerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UpdatePassengerForm updatePassengerForm = new UpdatePassengerForm();
                updatePassengerForm.setVisible(true);
            }
        });

        // JPanel formPanel = new JPanel();
        add(queryInfoButton);
        add(BookTicketButton);
        add(deltePassengerButton);
        add(updatePassengerButton);

        // setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PassengerManageForm().setVisible(true);
            }
        });
    }
}
