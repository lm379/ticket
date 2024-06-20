import javax.swing.*;
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
import org.json.JSONObject;

public class FlightsUpdateForm extends JFrame {
    private JTextArea textFieldTickNumber;
    private JTextArea newTinfo;
    private JButton updateButton;
    private JComboBox<String> comboBoxCondition;
    private Map<String, String> conditionMap;
    private String apiAddress = "http://localhost:15379/";

    public FlightsUpdateForm() {
        setTitle("修改航班信息");
        setSize(500, 300);
        setLayout(new GridLayout(4, 2));

        // 初始化下拉选择框和映射
        String[] conditions = { "价格", "票号", "折扣率", "航班号" };
        comboBoxCondition = new JComboBox<>(conditions);
        conditionMap = new HashMap<>();
        conditionMap.put("价格", "Price");
        conditionMap.put("票号", "TicketNumber");
        conditionMap.put("折扣率", "discount");
        conditionMap.put("航班号", "TflightNumber");

        textFieldTickNumber = new JTextArea();
        newTinfo = new JTextArea();
        updateButton = new JButton("提交");
        comboBoxCondition = new JComboBox<>(new String[] { "价格", "票号", "折扣率", "航班号" });
        JLabel l1 = new JLabel("待修改的票号:");
        JLabel l2 = new JLabel("需要修改的参数:");
        JLabel l3 = new JLabel("修改为:");
        l1.setHorizontalAlignment(JLabel.CENTER);
        l2.setHorizontalAlignment(JLabel.CENTER);
        l3.setHorizontalAlignment(JLabel.CENTER);
        // 设置布局并添加组件
        add(l1);
        add(textFieldTickNumber);
        add(l2);
        add(comboBoxCondition);
        add(l3);
        add(newTinfo);
        add(updateButton);

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleUpdateFlight("updateFlight");
            }
        });

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void handleUpdateFlight(String apiEndpoint) {
        String TicketNumber = textFieldTickNumber.getText();
        String newTinfo = this.newTinfo.getText();
        String condition = (String) conditionMap.get(comboBoxCondition.getSelectedItem());

        if (TicketNumber.isEmpty() || newTinfo.isEmpty()) {
            JOptionPane.showMessageDialog(null, "输入不能为空");
            return;
        }

        JSONObject json = new JSONObject();
        json.put("TicketNumber", TicketNumber);
        json.put("newTinfo", newTinfo);
        json.put("SelectTField", condition);
        // System.out.println(json.toString());
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
                JOptionPane.showMessageDialog(null, "航班修改成功");
            } else {
                // 读取错误信息
                SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(null, response.body(),
                        "航班修改失败", JOptionPane.ERROR_MESSAGE));
            }
        } catch (Exception e) {
            e.printStackTrace();
            SwingUtilities.invokeLater(
                    () -> JOptionPane.showMessageDialog(null, e.getMessage(), "航班修改失败", JOptionPane.ERROR_MESSAGE));
        }
    }
}
