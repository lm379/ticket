import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import org.json.JSONArray;
import org.json.JSONObject;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.text.DecimalFormat;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URI;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import com.formdev.flatlaf.FlatLightLaf;

public class FlightsQueryForm extends JFrame {
    private String apiAddress = "http://localhost:15379/";
    private JButton addFlightButton;
    private JButton deleteFlightButton;
    private JButton updateFlightButton;

    public FlightsQueryForm() {
        super("航班管理");
        setPreferredSize(new Dimension(750, 400));
        setLayout(new GridLayout(5, 1));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // 初始化组件
        addFlightButton = new JButton("添加航班");
        deleteFlightButton = new JButton("删除航班");
        updateFlightButton = new JButton("修改航班");

        // 设置布局并添加组件
        JButton queryButton = new JButton("查询所有航班");
        JButton selectButton = new JButton("条件筛选航班");

        // 添加事件监听器
        queryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                queryFlights("queryAllFlights");
            }
        });
        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FlightsSelectForm flightSelectForm = new FlightsSelectForm();
                flightSelectForm.setVisible(true);
            }
        });
        addFlightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FlightsAddForm addFlightFrame = new FlightsAddForm();
                addFlightFrame.setVisible(true);
            }
        });
        deleteFlightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FlightsDeleteForm deleteFlightFame = new FlightsDeleteForm();
                deleteFlightFame.setVisible(true);
            }
        });
        updateFlightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FlightsUpdateForm updateFlightFrame = new FlightsUpdateForm();
                updateFlightFrame.setVisible(true);
            }
        });

        // JPanel formPanel = new JPanel();
        add(selectButton);
        add(queryButton);
        add(addFlightButton);
        add(deleteFlightButton);
        add(updateFlightButton);
        // setLayout(new BorderLayout());
        // add(formPanel, BorderLayout.CENTER);
        pack();
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
                            JOptionPane.showMessageDialog(FlightsQueryForm.this, jsonString, "错误",
                                    JOptionPane.INFORMATION_MESSAGE);
                        }
                    });
                } else {
                    // 如果请求失败，显示错误信息
                    SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(FlightsQueryForm.this,
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

    public static void main(String[] args) {
        FlatLightLaf.setup();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FlightsQueryForm().setVisible(true);
            }
        });
    }
}