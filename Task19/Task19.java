package ru.itis.kpfu.darZam.BattleRoyal.Task19;

import org.apache.http.conn.HttpHostConnectException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task19 {

    //main
    private JFrame mainFrame;
    private Container mainPanel;
    //menu
    private JMenuBar menuBar;
    private JMenu file;
    private JMenuItem exit;
    private JMenuItem about;
    //info
    private JButton checkInfo;
    private JButton color;
    private JButton center;
    private JPanel infoPanel;
    //status
    private JPanel statusPanel;
    private JLabel statusLabel;
    //form
    private JPanel formPanel;
    private JTextField email;
    private JPasswordField password;
    private JLabel emailLabel;
    private JLabel passwordLabel;
    private JButton register;



    public void run(){
        mainFrame = new JFrame("Hello World");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainPanel =mainFrame.getContentPane();
        mainPanel.setLayout(new BorderLayout());


        menuBar = new JMenuBar();
        file = new JMenu("File");
        exit = new JMenuItem("exit");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        file.add(exit);
        about = new JMenuItem("about");

        about.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(mainFrame,"modal");
            }
        });
        menuBar.add(file);
        menuBar.add(about);

        mainPanel.add(menuBar, BorderLayout.NORTH);

        //info
        infoPanel = new JPanel();
        infoPanel.setOpaque(false);
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

        checkInfo = new JButton("Check Info");
        checkInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String emailCheck = email.getText();
                String passwordCheck = new String(password.getPassword());

                Pattern emailPat = Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
                Matcher matcher = emailPat.matcher(emailCheck);

                if (matcher.find()){
                    email.setBorder(BorderFactory.createLineBorder(Color.green));
                }else {
                    email.setBorder(BorderFactory.createLineBorder(Color.red));
                }

                if(passwordCheck.length()>5){
                    password.setBorder(BorderFactory.createLineBorder(Color.green));
                }else {
                    password.setBorder(BorderFactory.createLineBorder(Color.red));
                }
            }
        });
        color = new JButton("color");
        color.addActionListener(new ColorListener());
        center = new JButton("center");


        center.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.setLocationRelativeTo(null);
            }
        });

        infoPanel.add(checkInfo);
        infoPanel.add(color);
        infoPanel.add(center);

        mainPanel.add(infoPanel, BorderLayout.EAST);

        //status

        statusPanel = new JPanel();
        statusPanel.setOpaque(false);
        statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));
        statusLabel = new JLabel("Status");
        statusLabel.setHorizontalAlignment(SwingConstants.LEFT);
        statusPanel.add(statusLabel);
        mainPanel.add(statusPanel, BorderLayout.SOUTH);

        //form
        formPanel = new JPanel();
        formPanel.setOpaque(false);
        email = new JTextField();
        password = new JPasswordField();
        emailLabel = new JLabel("email");
        passwordLabel = new JLabel("password");
        emailLabel.setLabelFor(email);
        passwordLabel.setLabelFor(password);
        formPanel.setLayout(new GridBagLayout());

        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.anchor = GridBagConstraints.BASELINE_LEADING;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new Insets(4, 4, 4, 4);

        gridBagConstraints.gridwidth = GridBagConstraints.RELATIVE;
        formPanel.add(emailLabel, gridBagConstraints);
        gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
        gridBagConstraints.weightx = 1;
        formPanel.add(email, gridBagConstraints);

        gridBagConstraints.gridwidth = GridBagConstraints.RELATIVE;
        gridBagConstraints.weightx = 0;
        formPanel.add(passwordLabel, gridBagConstraints);
        gridBagConstraints.weightx = 1;
        gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
        formPanel.add(password, gridBagConstraints);
        gridBagConstraints.gridwidth = GridBagConstraints.RELATIVE;
        gridBagConstraints.weightx = 0;
        register = new JButton("register");
        register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String emailText = email.getText();
                String passwordText =new String(password.getPassword());
                System.out.println(emailText + passwordText);
                try {
                    int respCode = HttpSignInRequest.sendRequest(emailText, passwordText);
                    if (respCode == 200){
                        JOptionPane.showMessageDialog(mainFrame,"you are in System");
                    }else {
                        JOptionPane.showMessageDialog(mainFrame,"incorrect data");
                    }
                } catch (URISyntaxException e1) {
                    e1.printStackTrace();
                }catch (HttpHostConnectException e1){
                    Logger.getLogger(this.getClass().getName()).log(Level.FINE, "Server is not accepted");
                    JOptionPane.showMessageDialog(mainFrame,"server is not accepted");
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        formPanel.add(register, gridBagConstraints);


        //set status info
        center.addMouseListener(new Info("This is center button"));
        color.addMouseListener(new Info("This color chandge color"));
        checkInfo.addMouseListener(new Info("check your date"));
        email.addMouseListener(new Info("write your email"));
        password.addMouseListener(new Info("write your password"));
        exit.addMouseListener(new Info("exit from app"));
        register.addMouseListener(new Info("register"));

        mainFrame.pack();
        mainFrame.setMinimumSize(new Dimension(300, 300));
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainFrame.setVisible(true);

    }

    public static void main(String[] args) {
        Task19 frame = new Task19();
        frame.run();
    }

    private class ColorListener implements ActionListener{

        private boolean isChange;
        @Override
        public void actionPerformed(ActionEvent e) {
            if (isChange){
                isChange = false;
                mainPanel.setBackground((new JPanel()).getBackground());
            }else {
                isChange = true;
                mainPanel.setBackground(Color.blue);
            }
        }
    }

    private class Info implements MouseListener  {

        private String info;

        private Info(String info){
            this.info = info;
        }
        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {
            statusLabel.setText(info);
        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

}
