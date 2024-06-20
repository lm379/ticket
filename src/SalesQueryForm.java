import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import org.json.JSONArray;
import org.json.JSONObject;
import java.awt.BorderLayout;
import java.awt.Dimension;
// import java.awt.event.ActionEvent;
// import java.awt.event.ActionListener;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.net.URI;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class SalesQueryForm extends JFrame {
    public String apiAddress = "http://localhost:15379/";
    private JTable resultsTable;
    private DefaultTableModel tableModel;

    public SalesQueryForm() {
        super("机票销售信息查询");
        setPreferredSize(new Dimension(600, 200));

        // 表格模型
        String[] columnNames = { "航班号", "乘客人数", "机票销售额" };
        tableModel = new DefaultTableModel(columnNames, 0);
        resultsTable = new JTable(tableModel);
        add(new JScrollPane(resultsTable), BorderLayout.CENTER);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        queryAllSales("calculateFlightSales");
    }

    private void queryAllSales(String apiEndpoint) {
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
                    SwingUtilities.invokeLater(() -> {
                        try {
                            JSONArray jsonArray = new JSONArray(response.toString());
                            // 清空表格数据
                            tableModel.setRowCount(0);
                            DecimalFormat df = new DecimalFormat("#.00");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject obj = jsonArray.getJSONObject(i);
                                tableModel.addRow(new Object[] {
                                        obj.getString("FlightNumber"),
                                        obj.getInt("PNum"),
                                        df.format((double)obj.getInt("Sales"))
                                });
                            }
                        } catch (Exception e) {
                            tableModel.setRowCount(0); // 清空表格数据
                            JOptionPane.showMessageDialog(SalesQueryForm.this, jsonString, "错误",
                                    JOptionPane.INFORMATION_MESSAGE);
                        }
                    });
                } else {
                    // 如果请求失败，显示错误信息
                    SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(SalesQueryForm.this,
                            "查询失败，响应码：" + responseCode, "错误", JOptionPane.ERROR_MESSAGE));
                }
                return null;
            }
        };
        worker.execute();
    }

    // public static void main(String[] args) {
    // SwingUtilities.invokeLater(new Runnable() {
    // @Override
    // public void run() {
    // new SalesQueryForm().setVisible(true);
    // }
    // });
    // }
}
