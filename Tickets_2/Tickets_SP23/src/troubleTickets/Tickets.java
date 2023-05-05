package troubleTickets;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;

@SuppressWarnings("serial")
public class Tickets extends JFrame implements ActionListener {

    // class level member objects
    Dao dao = new Dao(); // for CRUD operations
    Boolean chkIfAdmin = null;

    // Main menu object items
    private JMenu mnuFile = new JMenu("File");
    private JMenu mnuAdmin = new JMenu("Admin");
    private JMenu mnuTickets = new JMenu("Tickets");

    // Sub menu item objects for all Main menu item objects
    JMenuItem mnuItemExit;
    JMenuItem mnuItemUpdate;
    JMenuItem mnuItemDelete;
    JMenuItem mnuItemOpenTicket;
    JMenuItem mnuItemViewTicket;

    public Tickets(Boolean isAdmin) {

        chkIfAdmin = isAdmin;
        createMenu();
        prepareGUI();

    }

    private void createMenu() {

        // Initialize sub menu items for File main menu
        mnuItemExit = new JMenuItem("Exit");
        mnuFile.add(mnuItemExit);

        // Initialize sub menu items for Tickets main menu
        mnuItemOpenTicket = new JMenuItem("Open Ticket");
        mnuTickets.add(mnuItemOpenTicket);
        mnuItemViewTicket = new JMenuItem("View Ticket");
        mnuTickets.add(mnuItemViewTicket);

        // Add action listeners for each desired menu item
        mnuItemExit.addActionListener(this);
        mnuItemOpenTicket.addActionListener(this);
        mnuItemViewTicket.addActionListener(this);

        // Set font and foreground color for menu items
        Font font = new Font("Arial", Font.PLAIN, 14);
        Color color = new Color(0, 102, 204);
        mnuItemExit.setFont(font);
        mnuItemOpenTicket.setFont(font);
        mnuItemViewTicket.setFont(font);
        mnuItemExit.setForeground(color);
        mnuItemOpenTicket.setForeground(color);
        mnuItemViewTicket.setForeground(color);

        // Only show the Admin menu if the user is an admin
        if (chkIfAdmin != null && chkIfAdmin) {
            // Initialize sub menu items for Admin main menu
            mnuItemUpdate = new JMenuItem("Update Ticket");
            mnuAdmin.add(mnuItemUpdate);
            mnuItemDelete = new JMenuItem("Delete Ticket");
            mnuAdmin.add(mnuItemDelete);

            // Add action listeners for each desired menu item
            mnuItemUpdate.addActionListener(this);
            mnuItemDelete.addActionListener(this);

            // Set font and foreground color for menu items
            mnuItemUpdate.setFont(font);
            mnuItemDelete.setFont(font);
            mnuItemUpdate.setForeground(color);
            mnuItemDelete.setForeground(color);
        }
    }

	private void prepareGUI() {

	    try {
	        // Nimbus look and feel
	        UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
	    } catch (Exception e) {
	        // If Nimbus is not available, set the system look and feel.
	        e.printStackTrace();
	    }

	    // create JMenu bar
	    JMenuBar bar = new JMenuBar();
	    bar.add(mnuFile); // add main menu items in order, to JMenuBar
	    bar.add(mnuAdmin);
	    bar.add(mnuTickets);
	    // add menu bar components to frame
	    setJMenuBar(bar);

	    addWindowListener(new WindowAdapter() {
	        // define a window close operation
	        public void windowClosing(WindowEvent wE) {
	            System.exit(0);
	        }
	    });
	    // set frame options
	    setSize(400, 400);
	    getContentPane().setBackground(Color.LIGHT_GRAY);
	    setLocationRelativeTo(null);
	    setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// implement actions for sub menu items
		if (e.getSource() == mnuItemExit) {
			System.exit(0);
		} else if (e.getSource() == mnuItemOpenTicket) {

			// get ticket information
			String ticketName = JOptionPane.showInputDialog(null, "Enter your name");
			String ticketDesc = JOptionPane.showInputDialog(null, "Enter a ticket description");

			// insert ticket information to database

			int id = dao.insertRecords(ticketName, ticketDesc);

			// display results if successful or not to console / dialog box
			if (id != 0) {
				System.out.println("Ticket ID : " + id + " created successfully!!!");
				JOptionPane.showMessageDialog(null, "Ticket id: " + id + " created");
			} else
				System.out.println("Ticket cannot be created!!!");
		}

		else if (e.getSource() == mnuItemViewTicket) {

			// retrieve all tickets details for viewing in JTable
			try {

				JTable jt = new JTable(ticketsJTable.buildTableModel(dao.readRecords()));
				jt.setBounds(30, 40, 200, 400);
				JScrollPane sp = new JScrollPane(jt);
				add(sp);
				setVisible(true); // refreshes or repaints frame on screen

			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

}
