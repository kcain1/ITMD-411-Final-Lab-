package troubleTickets;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class Login extends JFrame {
    Dao conn;

    public Login() {
        super("My Help Desk Application Login");
        conn = new Dao();
        conn.createTables();
        setSize(500, 310);
        setLocationRelativeTo(null); // centers window

        // SET UP CONTROLS
        JLabel lblUsername = new JLabel("Username:", JLabel.RIGHT);
        JLabel lblPassword = new JLabel("Password:", JLabel.RIGHT);
        JLabel lblStatus = new JLabel(" ", JLabel.CENTER);

        JTextField txtUname = new JTextField(10);
        JPasswordField txtPassword = new JPasswordField();
        JButton btnSubmit = new JButton("Login");
        JButton btnExit = new JButton("Exit");

        // SET FONTS
        Font labelFont = new Font(Font.SANS_SERIF, Font.BOLD, 16);
        Font buttonFont = new Font(Font.SANS_SERIF, Font.PLAIN, 14);
        lblUsername.setFont(labelFont);
        lblPassword.setFont(labelFont);
        lblStatus.setFont(labelFont);
        btnSubmit.setFont(buttonFont);
        btnExit.setFont(buttonFont);

        // SET COLORS
        Color bgColor = new Color(180, 180, 180);
        Color textColor = new Color(40, 40, 40);
        lblUsername.setForeground(textColor);
        lblPassword.setForeground(textColor);
        lblStatus.setForeground(textColor);
        getContentPane().setBackground(bgColor);

        // SET BUTTON SIZE
        btnSubmit.setPreferredSize(new java.awt.Dimension(100, 35));
        btnExit.setPreferredSize(new java.awt.Dimension(100, 35));

        // ADD OBJECTS TO FRAME
        setLayout(new GridLayout(4, 2));
        add(lblUsername);  // 1st row filler
        add(txtUname);
        add(lblPassword);  // 2nd row
        add(txtPassword);
        add(btnSubmit);          // 3rd row
        add(btnExit);
        add(lblStatus);    // 4th row

        btnSubmit.addActionListener(new ActionListener() {
            int count = 0; // count agent

            @Override
            public void actionPerformed(ActionEvent e) {
                boolean admin = false;
                count = count + 1;
                // verify credentials of user (MAKE SURE TO CHANGE TO YOUR TABLE NAME BELOW)

                String query = "SELECT * FROM kcain_users WHERE uname = ? and upass = ?;";
                try (PreparedStatement stmt = conn.getConnection().prepareStatement(query)) {
                    stmt.setString(1, txtUname.getText());
                    stmt.setString(2, txtPassword.getText());
                    ResultSet rs = stmt.executeQuery();
                    if (rs.next()) {
                        admin = rs.getBoolean("admin"); // get table column value
                        new Tickets(admin); //open Tickets file / GUI interface
                        setVisible(false); // HIDE THE FRAME
                        dispose(); // CLOSE OUT THE WINDOW
                    } else
                        lblStatus.setText("Try again! " + (3 - count) + " / 3 attempt(s) left");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
		btnExit.addActionListener(e -> System.exit(0));

		setVisible(true); // SHOW THE FRAME
	}

	public static void main(String[] args) {

		new Login();
	}
}
