// import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import javax.swing.*;
import org.json.JSONObject;

public class FlightsDeleteForm extends JFrame {
    private JTextArea textAreaTicketNumber;
    private JButton deleteButton;
    private String apiAddress = "http://localhost:15379/";

    public FlightsDeleteForm() {
        setTitle("删除航班");
        setSize(500, 100);
        // setLayout(new GridLayout(1, 3));

        textAreaTicketNumber = new JTextArea();
        deleteButton = new JButton("删除");

        JPanel formPanel = new JPanel();
        formPanel.add(new JLabel("票号："));
        formPanel.add(textAreaTicketNumber);
        formPanel.add(deleteButton);
        add(formPanel);
        // pack();

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleDeleteFlight("deleteFlight");
            }
        });
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void handleDeleteFlight(String apiEndpoint) {
        String TicketNumber = textAreaTicketNumber.getText();

        // 检查字段是否为空
        if (TicketNumber.isEmpty()) {
            JOptionPane.showMessageDialog(null, "票号不可为空", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JSONObject json = new JSONObject();
        json.put("TicketNumber", TicketNumber);
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
                JOptionPane.showMessageDialog(null, "删除成功");
            } else {
                // 读取错误信息
                SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(null,  response.body(),
                        "删除失败", JOptionPane.ERROR_MESSAGE));
            }
        } catch (Exception e) {
            e.printStackTrace();
            SwingUtilities.invokeLater(
                    () -> JOptionPane.showMessageDialog(null, e.getMessage(), "删除失败", JOptionPane.ERROR_MESSAGE));
        }
    }
}
