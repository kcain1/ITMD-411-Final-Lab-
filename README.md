# ITMD-411-Final-Lab-

Kevin Cain

Java class for "TroubleTickets". The application allows users to create and view support tickets, and has options for administrators to update or delete tickets.

The class extends the JFrame class, indicating that it is a window that can be displayed on the screen. It implements the ActionListener interface, which means it can handle events such as button clicks.

The class defines several member objects, including a DAO (Data Access Object) for performing CRUD (Create, Read, Update, Delete) operations on the database. It also defines JMenu and JMenuItem objects for creating a menu bar, with sub-menu items for opening, viewing, updating, and deleting support tickets.

The constructor for the Tickets class takes a boolean value indicating whether the user is an administrator or not, and calls the createMenu() and prepareGUI() methods to initialize the menu bar and window components.

The createMenu() method initializes the sub-menu items for each main menu item, adds action listeners for each menu item, and sets the font and color for the menu items.

The prepareGUI() method sets the look and feel of the application, creates a JMenuBar object, adds the main menu items to the menu bar, sets the window size, background color, and location, and makes the window visible.

The actionPerformed() method handles the actions for each sub-menu item. If the "Open Ticket" item is selected, it prompts the user for their name and a ticket description, inserts the information into the database, and displays a message box indicating success or failure. If the "View Ticket" item is selected, it retrieves all ticket details from the database and displays them in a JTable object.

The Dao class connects to a MySQL database and allows users to perform CRUD operations on the tickets in the system. It also has the ability to create tables in the database, add users to the user table, and close tickets.

The Dao class contains methods for interacting with the database. The getConnection() method sets up the connection with the database. The createTables() method creates the tables in the database. The addUsers() method adds users to the user table. The insertRecords() method inserts a new ticket into the ticket table. The readRecords() method retrieves all tickets from the ticket table. The deleteTicket() method deletes a ticket from the ticket table. The closeTicket() method changes the status of a ticket to closed and adds an end date to the ticket.

The program reads user data from a the CSV file and adds it to the user table in the database. The program also uses prepared statements to prevent SQL injection attacks.

the login class functionality for the gui Troubletickets, login. There is a form with two input fields for username and password, and two buttons for submitting the login credentials or exiting the application. The program uses a database to store user credentials and verify them during the login process. If the user enters valid credentials, the program opens a new window with the help desk application's main interface. If the user enters invalid credentials, the program displays an error message and allows the user to retry up to three times. The program uses the DAO pattern to interact with the database, and it handles SQL exceptions using try-with-resources blocks.

The Java class ticketsJTable provides a method buildTableModel that takes a ResultSet object and returns a DefaultTableModel object.

The buildTableModel method uses the ResultSet metadata to retrieve the column names and number of columns. It then loops through the ResultSet data and retrieves the data for each column. The retrieved data is then stored in a Vector of Vectors, where each Vector represents a row of data. Finally, the DefaultTableModel is created with the Vector of Vectors and the column names and returned.

This method is used for building a JTable object in a Swing GUI application that displays data from a database.
