import javax.swing.*;
import javax.swing.plaf.FontUIResource;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

import com.formdev.flatlaf.FlatLightLaf;
// import com.formdev.flatlaf.*;

public class TicketManageSystem extends JFrame {

    public TicketManageSystem() {

        FlatLightLaf.setup();
        setTitle("机票订票管理系统");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 1));
        // setLayout(new FlowLayout());

        // 创建按钮面板
        JButton buttonPassengerManage = new JButton("乘客管理");
        buttonPassengerManage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PassengerManageForm selectForm = new PassengerManageForm();
                selectForm.setVisible(true);
            }
        });

        JButton buttonQueryFlights = new JButton("航班管理");
        buttonQueryFlights.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FlightsQueryForm queryForm = new FlightsQueryForm();
                queryForm.setVisible(true);
            }
        });

        JButton buttonSalesQuery = new JButton("销售统计");
        buttonSalesQuery.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SalesQueryForm salesQueryForm = new SalesQueryForm();
                salesQueryForm.setVisible(true);
            }
        });
        // buttonSalesQuery.setPreferredSize(new Dimension(150, 40));
        // buttonPassengerManage.setPreferredSize(new Dimension(150, 40));
        // buttonQueryFlights.setPreferredSize(new Dimension(150, 40));
        // buttonSalesQuery.setPreferredSize(new Dimension(150, 40));

        ImageIcon originalIcon = new ImageIcon("img/icon.png");
        Image image = originalIcon.getImage(); // 将 ImageIcon 转换为 Image
        // 缩放图片到新的尺寸
        Image newimg = image.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
        ImageIcon newIcon = new ImageIcon(newimg);
        JLabel Jicon = new JLabel(newIcon);
        JLabel Jlabel = new JLabel("欢迎使用机票购票管理系统");

        buttonQueryFlights
                .setFont(new Font(buttonQueryFlights.getFont().getName(), buttonQueryFlights.getFont().getStyle(), 20));
        buttonSalesQuery
                .setFont(new Font(buttonSalesQuery.getFont().getName(), buttonSalesQuery.getFont().getStyle(), 20));
        buttonPassengerManage.setFont(
                new Font(buttonPassengerManage.getFont().getName(), buttonPassengerManage.getFont().getStyle(), 20));
        Jlabel.setFont(new Font(Jlabel.getFont().getName(), Jlabel.getFont().getStyle(), 20));
        JPanel formPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        // formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.X_AXIS));
        JPanel outerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = GridBagConstraints.REMAINDER;
        gbc.gridy = GridBagConstraints.REMAINDER;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.NONE;
        formPanel.add(Box.createVerticalGlue());
        formPanel.add(Jicon);
        formPanel.add(Box.createVerticalGlue());
        formPanel.add(Jlabel);
        // add(formPanel);
        outerPanel.add(formPanel, gbc);
        add(outerPanel);
        add(buttonPassengerManage);
        // add(Box.createRigidArea(new Dimension(0, 10)));
        add(buttonQueryFlights);
        // add(Box.createRigidArea(new Dimension(0, 10)));
        add(buttonSalesQuery);
    }

    public static void main(String[] args) {

        setGlobalFont(new Font("微软雅黑", Font.PLAIN, 18));

        FlatLightLaf.setup();
        SwingUtilities.invokeLater(() -> {
            LoginForm mainFrame = new LoginForm();
            mainFrame.setVisible(true);
            // TicketManageSystem mainFrame = new TicketManageSystem();
            // mainFrame.setVisible(true);
        });
    }

    private static void setGlobalFont(Font font) {
        FontUIResource fontRes = new FontUIResource(font);
        for (@SuppressWarnings("rawtypes")
        Enumeration keys = UIManager.getDefaults().keys(); keys.hasMoreElements();) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource) {
                UIManager.put(key, fontRes);
            }
        }
    }
}