import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import org.json.JSONArray;
import org.json.JSONObject;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;

public class FlightsSelectForm extends JFrame {
    private JComboBox<String> searchTypeComboBox;
    private JTextField fromCityTextField;
    private JTextField toCityTextField;
    private JTextField flightNumberTextField;
    private JButton submitButton;
    private String apiAddress = "http://localhost:15379/";

    public FlightsSelectForm() {
        super("筛选航班");
        setSize(600, 150);
        searchTypeComboBox = new JComboBox<>(new String[] { "城市", "航班号" });
        fromCityTextField = new JTextField(10);
        toCityTextField = new JTextField(10);
        flightNumberTextField = new JTextField(10);
        submitButton = new JButton("提交");

        JPanel inputPanel = new JPanel();
        inputPanel.add(searchTypeComboBox);
        inputPanel.add(fromCityTextField);
        inputPanel.add(toCityTextField);
        inputPanel.add(flightNumberTextField);
        inputPanel.add(submitButton);

        // 默认显示城市输入框，隐藏航班号输入框
        fromCityTextField.setVisible(true);
        toCityTextField.setVisible(true);
        flightNumberTextField.setVisible(false);

        searchTypeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ("城市".equals(searchTypeComboBox.getSelectedItem())) {
                    fromCityTextField.setVisible(true);
                    toCityTextField.setVisible(true);
                    flightNumberTextField.setVisible(false);
                } else {
                    fromCityTextField.setVisible(false);
                    toCityTextField.setVisible(false);
                    flightNumberTextField.setVisible(true);
                }
                // 重新绘制界面以更新输入框的显示状态
                FlightsSelectForm.this.revalidate();
                FlightsSelectForm.this.repaint();
            }
        });
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String apiEndpoint;
                if ("城市".equals(searchTypeComboBox.getSelectedItem())) {
                    String fromCity = fromCityTextField.getText();
                    String toCity = toCityTextField.getText();
                    apiEndpoint = "queryFlights?fromCity=" + URLEncoder.encode(fromCity, StandardCharsets.UTF_8)
                            + "&toCity=" + URLEncoder.encode(toCity, StandardCharsets.UTF_8);
                } else {
                    String flightNumber = flightNumberTextField.getText();
                    apiEndpoint = "queryFlightByNumber?FlightNumber="
                            + URLEncoder.encode(flightNumber, StandardCharsets.UTF_8);
                }
                queryFlights(apiEndpoint);
            }
        });

        JPanel formPanel = new JPanel();
        formPanel.add(new JLabel("查询方式："));
        formPanel.add(inputPanel);
        add(formPanel);
        setLocationRelativeTo(null);
    }

    public void queryFlights(String apiEndpoint) {
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

                    String jsonString = response.toString();
                    SwingUtilities.invokeLater(() -> {
                        try {
                            JSONArray jsonArray = new JSONArray(response.toString());
                            showResults(jsonArray);
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(FlightsSelectForm.this, jsonString, "错误",
                                    JOptionPane.INFORMATION_MESSAGE);
                        }
                    });
                } else {
                    // 如果请求失败，显示错误信息
                    SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(FlightsSelectForm.this,
                            "查询失败，响应码：" + responseCode, "错误", JOptionPane.ERROR_MESSAGE));
                }
                return null;
            }
        };
        worker.execute();
    }

    public void showResults(JSONArray jsonArray) {
        // System.out.println(jsonArray);
        // 创建一个包含表格的滚动面板
        String[] columnNames = { "票号", "始发城市", "目的城市", "实售价格", "原价", "折扣率", "航班号", "机型", "航行里程", "起飞时间" };
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable frameTable = new JTable(tableModel);
        DecimalFormat df = new DecimalFormat("#.00");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            tableModel.addRow(new Object[] {
                    obj.getInt("TicketNumber"),
                    obj.getString("fromCity"),
                    obj.getString("toCity"),
                    "¥" + df.format((double) obj.getInt("Price") * obj.getDouble("discount")),
                    "¥" + obj.getInt("Price"),
                    obj.getDouble("discount"),
                    obj.getString("FlightNumber"),
                    obj.getString("FlightName"),
                    obj.getInt("mileAge") + "km",
                    obj.getString("departureTime")
            });
        }

        JScrollPane scrollPane = new JScrollPane(frameTable);
        scrollPane.setPreferredSize(new Dimension(1000, 500));

        JFrame frame = new JFrame("查询结果");
        frame.getContentPane().add(scrollPane);
        frame.pack();
        frame.setLocation(getMousePosition());
        frame.setVisible(true);
    }
}