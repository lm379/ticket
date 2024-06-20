import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.HashMap;
import javax.swing.*;
import org.json.JSONObject;

public class FlightsAddForm extends JFrame {
    private JTextField textFieldTflightNumber;
    private JTextField textFieldTicketNumber;
    private JTextField textFieldPrice;
    private JTextField textFieldDiscount;
    private JButton addButton;
    private JComboBox<String> comboBoxCondition;
    private Map<String, Integer> conditionMap;
    private String apiAddress = "http://localhost:15379/";

    public FlightsAddForm() {
        setTitle("添加航班信息");
        setSize(500, 400);
        setLayout(new GridLayout(6, 2));

        // 初始化下拉选择框和映射
        String[] conditions = { "是", "否" };
        comboBoxCondition = new JComboBox<>(conditions);
        conditionMap = new HashMap<>();
        conditionMap.put("是", 1);
        conditionMap.put("否", 0);

        textFieldTflightNumber = new JTextField();
        textFieldTicketNumber = new JTextField();
        textFieldPrice = new JTextField();
        textFieldDiscount = new JTextField();
        addButton = new JButton("提交");

        JLabel l1 = new JLabel("航班号:");
        JLabel l2 = new JLabel("票号:");
        JLabel l3 = new JLabel("价格:");
        JLabel l4 = new JLabel("预售状态:");
        JLabel l5 = new JLabel("折扣率:");
        l1.setHorizontalAlignment(JLabel.CENTER);
        l2.setHorizontalAlignment(JLabel.CENTER);
        l3.setHorizontalAlignment(JLabel.CENTER);
        l4.setHorizontalAlignment(JLabel.CENTER);
        l5.setHorizontalAlignment(JLabel.CENTER);

        add(l1);
        add(textFieldTflightNumber);
        add(l2);
        add(textFieldTicketNumber);
        add(l3);
        add(textFieldPrice);
        add(l4);
        add(comboBoxCondition);
        add(l5);
        add(textFieldDiscount);
        add(new JLabel());
        add(addButton);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleAddFlight("addFlight");
            }
        });

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void handleAddFlight(String apiEndpoint) {
        String TflightNumber = textFieldTflightNumber.getText();
        String TicketNumber = textFieldTicketNumber.getText();
        String Price = textFieldPrice.getText();
        String selectedCondition = (String) comboBoxCondition.getSelectedItem();
        Integer Condition = conditionMap.get(selectedCondition);
        String discount = textFieldDiscount.getText();

        // 检查所有字段是否已填写
        if (TflightNumber.isEmpty() || TicketNumber.isEmpty() || Price.isEmpty() ||
                discount.isEmpty()) {
            JOptionPane.showMessageDialog(null, "请填写所有必填字段。");
            return;
        }

        JSONObject json = new JSONObject();
        json.put("TflightNumber", TflightNumber);
        json.put("TicketNumber", TicketNumber);
        json.put("Price", Price);
        json.put("Condition", Condition);
        json.put("discount", discount);
        json.put("worker", "1");

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
                JOptionPane.showMessageDialog(null, "航班添加成功");
            } else {
                // 读取错误信息
                SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(null, "状态码:" + response.statusCode(),
                        "添加失败", JOptionPane.ERROR_MESSAGE));
            }
        } catch (Exception e) {
            e.printStackTrace();
            SwingUtilities.invokeLater(
                    () -> JOptionPane.showMessageDialog(null, e.getMessage(), "添加失败", JOptionPane.ERROR_MESSAGE));
        }
    }
}
