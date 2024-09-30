import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class CargoManagementSystem extends Frame implements ActionListener {

    // Components for the login page
    TextField usernameField, passwordField;
    Button loginButton;

    // Main dashboard buttons
    Button manageCargoButton, manageFlightsButton, manageBookingsButton, manageCustomersButton;

    // Database connection
    Connection con;

    CargoManagementSystem() {
        // Initialize the database connection
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/airlinecargomanagement", "root", "root");
        } catch (Exception e) {
            System.out.println("Error connecting to the database: " + e);
        }

        // Initialize Login Page with Colorful Layout
        setTitle("Employee Login");
        setLayout(new GridLayout(3, 2));

        // Set the background color for the frame
        //getContentPane().setBackground(new Color(18, 61, 130));

        // Labels with custom colors
        Label usernameLabel = new Label("Username:");
        usernameLabel.setForeground(new Color(0, 102, 204));
        add(usernameLabel);

        // Username TextField with background color
        usernameField = new TextField();
        usernameField.setBackground(new Color(255, 255, 204));
        add(usernameField);

        Label passwordLabel = new Label("Password:");
        passwordLabel.setForeground(new Color(0, 102, 204));
        add(passwordLabel);

        // Password TextField with background color
        passwordField = new TextField();
        passwordField.setBackground(new Color(255, 255, 204));
        passwordField.setEchoChar('*');
        add(passwordField);

        // Login Button with a custom color and font
        loginButton = new Button("Login");
        loginButton.setBackground(new Color(102, 178, 255));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.addActionListener(this);
        add(loginButton);

        // Frame settings
        setSize(400, 200);
        setLocationRelativeTo(null); // Center the frame
        setVisible(true);
    }
    // Login action handler
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String username = usernameField.getText();
            String password = passwordField.getText();

            try {
                String query = "SELECT * FROM Employee WHERE Username=? AND Password=?";
                PreparedStatement ps = con.prepareStatement(query);
                ps.setString(1, username);
                ps.setString(2, password);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    dispose(); // Close the login page
                    showDashboard(); // Show the main dashboard
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid credentials");
                }
            } catch (Exception ex) {
                System.out.println("Login Error: " + ex);
            }
        }
    }

    // Main Dashboard after login
    public void showDashboard() {
        Frame dashboard = new Frame("Cargo Management System");
        dashboard.setLayout(new GridBagLayout()); // Using GridBagLayout for better control
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Add padding around buttons
        gbc.fill = GridBagConstraints.HORIZONTAL; // Stretch buttons horizontally

        // Set the background color of the frame
        dashboard.setBackground(Color.WHITE); // Choose a light gray color for the frame

        // Create buttons
        manageCargoButton = new Button("Manage Cargo");
        manageFlightsButton = new Button("Manage Flights");
        manageBookingsButton = new Button("Manage Bookings");

        // Set the same background and text colors for all buttons
        Color buttonColor = Color.lightGray; // Choose your desired color
        manageCargoButton.setBackground(buttonColor);
        manageCargoButton.setForeground(Color.BLACK);

        manageFlightsButton.setBackground(buttonColor);
        manageFlightsButton.setForeground(Color.BLACK);

        manageBookingsButton.setBackground(buttonColor);
        manageBookingsButton.setForeground(Color.BLACK);

        // Set preferred size for buttons
        Dimension buttonSize = new Dimension(200, 50); // Width: 200, Height: 50
        manageCargoButton.setPreferredSize(buttonSize);
        manageFlightsButton.setPreferredSize(buttonSize);
        manageBookingsButton.setPreferredSize(buttonSize);

        // Add buttons with alignment
        gbc.gridx = 0;
        gbc.gridy = 0;
        dashboard.add(manageCargoButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        dashboard.add(manageFlightsButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        dashboard.add(manageBookingsButton, gbc);

        // Action listeners for buttons
        manageCargoButton.addActionListener(e -> manageCargo());
        manageFlightsButton.addActionListener(e -> manageFlights());
        manageBookingsButton.addActionListener(e -> manageBookings());

        // Handle window closing event
        dashboard.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });

        dashboard.setSize(600, 400);
        dashboard.setVisible(true);
    }

    // Manage Cargo operations
    public void manageCargo() {
        Frame cargoFrame = new Frame("Manage Cargo");
        cargoFrame.setLayout(new GridBagLayout()); // Use GridBagLayout for vertical alignment
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding around buttons
        gbc.fill = GridBagConstraints.HORIZONTAL; // Stretch buttons horizontally
        gbc.weightx = 1.0; // Allow buttons to grow horizontally

        // Create buttons
        Button addCargoButton = new Button("Add Cargo");
        Button removeCargoButton = new Button("Remove Cargo");
        Button updateCargoStatusButton = new Button("Update Cargo Status");

        // Set background colors for buttons
        addCargoButton.setBackground(Color.LIGHT_GRAY);
        removeCargoButton.setBackground(Color.LIGHT_GRAY);
        updateCargoStatusButton.setBackground(Color.LIGHT_GRAY);

        // Set button text colors (foreground)
        addCargoButton.setForeground(Color.BLACK);
        removeCargoButton.setForeground(Color.BLACK);
        updateCargoStatusButton.setForeground(Color.BLACK);

        // Set preferred size for buttons
        Dimension buttonSize = new Dimension(200, 50); // Width: 200, Height: 50
        addCargoButton.setPreferredSize(buttonSize);
        removeCargoButton.setPreferredSize(buttonSize);
        updateCargoStatusButton.setPreferredSize(buttonSize);

        // Add buttons to the frame with vertical alignment
        gbc.gridx = 0;
        gbc.gridy = 0;
        cargoFrame.add(addCargoButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        cargoFrame.add(removeCargoButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        cargoFrame.add(updateCargoStatusButton, gbc);

        // Add action listeners to buttons
        addCargoButton.addActionListener(e -> addCargo());
        removeCargoButton.addActionListener(e -> removeCargo());
        updateCargoStatusButton.addActionListener(e -> updateCargoStatus());

        // Set frame size and visibility
        cargoFrame.setSize(400, 300);
        cargoFrame.setVisible(true);

        // Close operation for the frame
        cargoFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                cargoFrame.dispose();
            }
        });
    }

    // Add Cargo
    public void addCargo() {
        Frame addCargoFrame = new Frame("Add Cargo");
        addCargoFrame.setLayout(new GridLayout(8, 2, 10, 10)); // Adjust rows for additional fields

        // Sender Details
        Label senderNameLabel = new Label("Sender Name:");
        senderNameLabel.setForeground(Color.BLUE);
        TextField senderNameField = new TextField();
        senderNameField.setBackground(Color.LIGHT_GRAY);

        Label senderAddressLabel = new Label("Sender Address:");
        senderAddressLabel.setForeground(Color.BLUE);
        TextField senderAddressField = new TextField();
        senderAddressField.setBackground(Color.LIGHT_GRAY);

        // Receiver Details
        Label receiverNameLabel = new Label("Receiver Name:");
        receiverNameLabel.setForeground(Color.BLUE);
        TextField receiverNameField = new TextField();
        receiverNameField.setBackground(Color.LIGHT_GRAY);

        Label receiverAddressLabel = new Label("Receiver Address:");
        receiverAddressLabel.setForeground(Color.BLUE);
        TextField receiverAddressField = new TextField();
        receiverAddressField.setBackground(Color.LIGHT_GRAY);

        // Cargo Details
        Label cargoTypeLabel = new Label("Cargo Type:");
        cargoTypeLabel.setForeground(Color.BLUE);
        TextField cargoTypeField = new TextField();
        cargoTypeField.setBackground(Color.LIGHT_GRAY);

        Label weightLabel = new Label("Weight (kg):");
        weightLabel.setForeground(Color.BLUE);
        TextField weightField = new TextField();
        weightField.setBackground(Color.LIGHT_GRAY);

        Label chargeLabel = new Label("Shipping Charge (in Rs):");
        chargeLabel.setForeground(Color.BLUE);
        TextField chargeField = new TextField();
        chargeField.setBackground(Color.LIGHT_GRAY);
        chargeField.setEditable(false); // Charge is calculated

        // Add button
        Button addButton = new Button("Add Cargo");
        addButton.setBackground(Color.GREEN);
        addButton.setForeground(Color.WHITE);

        // Add components to frame
        addCargoFrame.add(senderNameLabel);
        addCargoFrame.add(senderNameField);
        addCargoFrame.add(senderAddressLabel);
        addCargoFrame.add(senderAddressField);
        addCargoFrame.add(receiverNameLabel);
        addCargoFrame.add(receiverNameField);
        addCargoFrame.add(receiverAddressLabel);
        addCargoFrame.add(receiverAddressField);
        addCargoFrame.add(cargoTypeLabel);
        addCargoFrame.add(cargoTypeField);
        addCargoFrame.add(weightLabel);
        addCargoFrame.add(weightField);
        addCargoFrame.add(chargeLabel);
        addCargoFrame.add(chargeField);
        addCargoFrame.add(new Label("")); // Empty label for spacing
        addCargoFrame.add(addButton);

        // Set frame size and visibility
        addCargoFrame.setSize(500, 400);
        addCargoFrame.setVisible(true);

        // Calculate shipping charge based on weight
        weightField.addTextListener(e -> {
            try {
                float weight = Float.parseFloat(weightField.getText());
                float charge = calculateShippingCharge(weight);
                chargeField.setText(String.valueOf(charge));
            } catch (NumberFormatException ex) {
                chargeField.setText("Invalid weight");
            }
        });

        // Add action listener to the button
        addButton.addActionListener(e -> {
            String senderName = senderNameField.getText();
            String senderAddress = senderAddressField.getText();
            String receiverName = receiverNameField.getText();
            String receiverAddress = receiverAddressField.getText();
            String cargoType = cargoTypeField.getText();
            String weight = weightField.getText();
            String charge = chargeField.getText();

            try {
                // Generate a unique tracking ID
                String trackingID = generateTrackingID();

                // Prepare the SQL query
                String query = "INSERT INTO AirCargo (TrackingID, SenderName, SenderAddress, ReceiverName, ReceiverAddress, CargoType, Weight, Charge, Status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, 'Pending')";
                PreparedStatement ps = con.prepareStatement(query);
                ps.setString(1, trackingID);
                ps.setString(2, senderName);
                ps.setString(3, senderAddress);
                ps.setString(4, receiverName);
                ps.setString(5, receiverAddress);
                ps.setString(6, cargoType);
                ps.setFloat(7, Float.parseFloat(weight));
                ps.setFloat(8, Float.parseFloat(charge));
                ps.executeUpdate();

                JOptionPane.showMessageDialog(addCargoFrame, "Cargo added successfully with Tracking ID: " + trackingID);
                addCargoFrame.dispose();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(addCargoFrame, "Error adding cargo: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Close operation for the frame
        addCargoFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                addCargoFrame.dispose();
            }
        });
    }

    // Method to calculate shipping charge based on weight
    private float calculateShippingCharge(float weight) {
        final float ratePerKg = 50.0f; // Define the rate per kilogram
        return weight * ratePerKg;
    }

    // Method to generate a unique tracking ID
    private String generateTrackingID() throws SQLException {
        String query = "SELECT COUNT(*) FROM AirCargo"; // Count existing records
        PreparedStatement ps = con.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        rs.next();
        int count = rs.getInt(1);
        return "TRACK-" + (count + 1); // Generate a unique tracking ID
    }


    // Remove Cargo
    public void removeCargo() {
        Frame removeCargoFrame = new Frame("Remove Cargo");
        removeCargoFrame.setLayout(new GridLayout(3, 2, 10, 10)); // Grid layout with spacing

        // Label and TextField for Cargo ID
        Label cargoIdLabel = new Label("Cargo ID:");
        cargoIdLabel.setForeground(Color.BLUE); // Set text color
        TextField cargoIdField = new TextField();
        cargoIdField.setBackground(Color.LIGHT_GRAY); // Set background color

        // Remove button
        Button removeButton = new Button("Remove Cargo");
        removeButton.setBackground(Color.RED); // Set button color
        removeButton.setForeground(Color.WHITE); // Set text color

        // Add components to frame
        removeCargoFrame.add(cargoIdLabel);
        removeCargoFrame.add(cargoIdField);
        removeCargoFrame.add(new Label("")); // Empty label for spacing
        removeCargoFrame.add(removeButton);

        // Set frame size and visibility
        removeCargoFrame.setSize(400, 200);
        removeCargoFrame.setVisible(true);

        // Add action listener to the remove button
        removeButton.addActionListener(e -> {
            String cargoId = cargoIdField.getText();

            // Show confirmation dialog
            int confirm = JOptionPane.showConfirmDialog(removeCargoFrame, "Are you sure you want to remove Cargo ID: " + cargoId + "?", "Confirm Removal", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    String query = "DELETE FROM AirCargo WHERE ID=?"; // Use the correct table name and column
                    PreparedStatement ps = con.prepareStatement(query);
                    ps.setInt(1, Integer.parseInt(cargoId));
                    int rowsAffected = ps.executeUpdate();

                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(removeCargoFrame, "Cargo removed successfully!");
                    } else {
                        JOptionPane.showMessageDialog(removeCargoFrame, "Cargo not found!");
                    }
                    removeCargoFrame.dispose();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(removeCargoFrame, "Error removing cargo: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                } catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(removeCargoFrame, "Invalid Cargo ID format!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Close operation for the frame
        removeCargoFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                removeCargoFrame.dispose();
            }
        });
    }


    public void updateCargoStatus() {
        Frame updateCargoFrame = new Frame("Update Cargo Status");
        updateCargoFrame.setLayout(new GridLayout(4, 2, 10, 10)); // Grid layout with spacing

        // Label and TextField for Cargo ID
        Label cargoIdLabel = new Label("Cargo ID:");
        cargoIdLabel.setForeground(Color.BLUE); // Set text color
        TextField cargoIdField = new TextField();
        cargoIdField.setBackground(Color.LIGHT_GRAY); // Set background color

        // Label and TextField for New Status
        Label statusLabel = new Label("New Status:");
        statusLabel.setForeground(Color.BLUE);
        TextField statusField = new TextField();
        statusField.setBackground(Color.LIGHT_GRAY);

        // Update button
        Button updateButton = new Button("Update Status");
        updateButton.setBackground(Color.YELLOW); // Set button color
        updateButton.setForeground(Color.BLACK); // Set text color

        // Add components to frame
        updateCargoFrame.add(cargoIdLabel);
        updateCargoFrame.add(cargoIdField);
        updateCargoFrame.add(statusLabel);
        updateCargoFrame.add(statusField);
        updateCargoFrame.add(new Label("")); // Empty label for spacing
        updateCargoFrame.add(updateButton);

        // Set frame size and visibility
        updateCargoFrame.setSize(400, 200);
        updateCargoFrame.setVisible(true);

        // Add action listener to the update button
        updateButton.addActionListener(e -> {
            String cargoId = cargoIdField.getText();
            String status = statusField.getText();

            // Show confirmation dialog
            int confirm = JOptionPane.showConfirmDialog(updateCargoFrame, "Are you sure you want to update Cargo ID: " + cargoId + " to status: " + status + "?", "Confirm Update", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    String query = "UPDATE AirCargo SET Status=? WHERE ID=?"; // Use the correct table name and column
                    PreparedStatement ps = con.prepareStatement(query);
                    ps.setString(1, status);
                    ps.setInt(2, Integer.parseInt(cargoId));
                    int rowsAffected = ps.executeUpdate();

                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(updateCargoFrame, "Cargo status updated successfully!");
                    } else {
                        JOptionPane.showMessageDialog(updateCargoFrame, "Cargo not found!");
                    }
                    updateCargoFrame.dispose();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(updateCargoFrame, "Error updating cargo status: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                } catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(updateCargoFrame, "Invalid Cargo ID format!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Close operation for the frame
        updateCargoFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                updateCargoFrame.dispose();
            }
        });
    }

    // Manage Flights operations
    public void manageFlights() {
        Frame flightFrame = new Frame("Manage Flights");
        flightFrame.setLayout(new GridLayout(2, 1));

        Button addFlightButton = new Button("Add Flight");
        Button removeFlightButton = new Button("Remove Flight");

        // Set button colors
        addFlightButton.setBackground(Color.LIGHT_GRAY);
        addFlightButton.setForeground(Color.BLACK);
        removeFlightButton.setBackground(Color.LIGHT_GRAY);
        removeFlightButton.setForeground(Color.BLACK);

        addFlightButton.addActionListener(e -> addFlight());
        removeFlightButton.addActionListener(e -> removeFlight());

        flightFrame.add(addFlightButton);
        flightFrame.add(removeFlightButton);

        flightFrame.setSize(400, 200);
        flightFrame.setVisible(true);
    }

    // Add Flight
    public void addFlight() {
        Frame addFlightFrame = new Frame("Add Flight");

        Label depAirportLabel = new Label("Departure Airport:");
        TextField depAirportField = new TextField();
        Label arrAirportLabel = new Label("Arrival Airport:");
        TextField arrAirportField = new TextField();
        Label depTimeLabel = new Label("Departure Time (yyyy-MM-dd HH:mm:ss):");
        TextField depTimeField = new TextField();
        Label arrTimeLabel = new Label("Arrival Time (yyyy-MM-dd HH:mm:ss):");
        TextField arrTimeField = new TextField();

        Button addButton = new Button("Add Flight");

        // Set button color
        addButton.setBackground(Color.BLUE);
        addButton.setForeground(Color.WHITE);

        addFlightFrame.setLayout(new GridLayout(5, 2));
        addFlightFrame.add(depAirportLabel);
        addFlightFrame.add(depAirportField);
        addFlightFrame.add(arrAirportLabel);
        addFlightFrame.add(arrAirportField);
        addFlightFrame.add(depTimeLabel);
        addFlightFrame.add(depTimeField);
        addFlightFrame.add(arrTimeLabel);
        addFlightFrame.add(arrTimeField);
        addFlightFrame.add(addButton);

        addFlightFrame.setSize(600, 400);
        addFlightFrame.setVisible(true);

        addButton.addActionListener(e -> {
            String depAirport = depAirportField.getText();
            String arrAirport = arrAirportField.getText();
            String depTime = depTimeField.getText();
            String arrTime = arrTimeField.getText();

            try {
                String query = "INSERT INTO Flight (DepartureAirport, ArrivalAirport, DepartureTime, ArrivalTime) VALUES (?, ?, ?, ?)";
                PreparedStatement ps = con.prepareStatement(query);
                ps.setString(1, depAirport);
                ps.setString(2, arrAirport);
                ps.setString(3, depTime);
                ps.setString(4, arrTime);
                ps.executeUpdate();

                // Display confirmation with added details
                JOptionPane.showMessageDialog(addFlightFrame,
                        "Flight added successfully!\n" +
                                "Departure: " + depAirport + "\n" +
                                "Arrival: " + arrAirport + "\n" +
                                "Departure Time: " + depTime + "\n" +
                                "Arrival Time: " + arrTime);
                addFlightFrame.dispose();
            } catch (SQLException ex) {
                System.out.println("Error adding flight: " + ex);
            }
        });
    }

    // Remove Flight
    public void removeFlight() {
        Frame removeFlightFrame = new Frame("Remove Flight");

        Label flightIdLabel = new Label("Flight ID:");
        TextField flightIdField = new TextField();

        Button removeButton = new Button("Remove Flight");

        // Set button color
        removeButton.setBackground(Color.ORANGE);
        removeButton.setForeground(Color.WHITE);

        removeFlightFrame.setLayout(new GridLayout(2, 2));
        removeFlightFrame.add(flightIdLabel);
        removeFlightFrame.add(flightIdField);
        removeFlightFrame.add(removeButton);

        removeFlightFrame.setSize(400, 200);
        removeFlightFrame.setVisible(true);

        removeButton.addActionListener(e -> {
            String flightId = flightIdField.getText();

            try {
                String query = "SELECT * FROM Flight WHERE FlightID=?";
                PreparedStatement ps = con.prepareStatement(query);
                ps.setInt(1, Integer.parseInt(flightId));
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    String depAirport = rs.getString("DepartureAirport");
                    String arrAirport = rs.getString("ArrivalAirport");
                    String depTime = rs.getString("DepartureTime");
                    String arrTime = rs.getString("ArrivalTime");

                    // Now attempt to remove the flight
                    String deleteQuery = "DELETE FROM Flight WHERE FlightID=?";
                    PreparedStatement deletePs = con.prepareStatement(deleteQuery);
                    deletePs.setInt(1, Integer.parseInt(flightId));
                    int rowsAffected = deletePs.executeUpdate();

                    if (rowsAffected > 0) {
                        // Display confirmation with removed details
                        JOptionPane.showMessageDialog(removeFlightFrame,
                                "Flight removed successfully!\n" +
                                        "Flight ID: " + flightId + "\n" +
                                        "Departure: " + depAirport + "\n" +
                                        "Arrival: " + arrAirport + "\n" +
                                        "Departure Time: " + depTime + "\n" +
                                        "Arrival Time: " + arrTime);
                    } else {
                        JOptionPane.showMessageDialog(removeFlightFrame, "Flight not found!");
                    }
                } else {
                    JOptionPane.showMessageDialog(removeFlightFrame, "Flight not found!");
                }
                removeFlightFrame.dispose();
            } catch (SQLException ex) {
                System.out.println("Error removing flight: " + ex);
            }
        });
    }

    // Manage Bookings operations

    public void manageBookings() {
        Frame bookingFrame = new Frame("Manage Bookings");
        bookingFrame.setLayout(new GridLayout(3, 1));

        Button confirmBookingButton = new Button("Confirm Booking");
        Button cancelBookingButton = new Button("Cancel Booking");
        Button displayFlightsButton = new Button("Display Flights");
        Button displayCargoItemsButton = new Button("Display All Cargo Items");

        // Set button colors
        confirmBookingButton.setBackground(Color.lightGray);
        cancelBookingButton.setBackground(Color.lightGray);
        displayFlightsButton.setBackground(Color.lightGray);
        displayCargoItemsButton.setBackground(Color.LIGHT_GRAY);

        confirmBookingButton.addActionListener(e -> confirmBooking());
        cancelBookingButton.addActionListener(e -> cancelBooking());
        displayFlightsButton.addActionListener(e -> displayFlights());
        displayCargoItemsButton.addActionListener(e -> displayCargoItems());

        bookingFrame.add(confirmBookingButton);
        bookingFrame.add(cancelBookingButton);
        bookingFrame.add(displayFlightsButton);
        bookingFrame.add(displayCargoItemsButton);

        bookingFrame.setSize(400, 300);
        bookingFrame.setVisible(true);
    }

    // Confirm Booking
    public void confirmBooking() {
        Frame confirmBookingFrame = new Frame("Confirm Booking");
        confirmBookingFrame.setBackground(Color.gray);
        Label cargoIdLabel = new Label("Cargo ID:");
        TextField cargoIdField = new TextField();
        Label flightIdLabel = new Label("Flight ID:");
        TextField flightIdField = new TextField();

        Button confirmButton = new Button("Confirm Booking");
        confirmButton.setBackground(Color.GREEN);

        confirmBookingFrame.setLayout(new GridLayout(3, 2));
        confirmBookingFrame.add(cargoIdLabel);
        confirmBookingFrame.add(cargoIdField);
        confirmBookingFrame.add(flightIdLabel);
        confirmBookingFrame.add(flightIdField);
        confirmBookingFrame.add(confirmButton);

        confirmBookingFrame.setSize(400, 300);
        confirmBookingFrame.setVisible(true);

        confirmButton.addActionListener(e -> {
            String cargoId = cargoIdField.getText();
            String flightId = flightIdField.getText();

            try {
                String query = "INSERT INTO CargoBooking (CargoID, FlightID) VALUES (?, ?)";
                PreparedStatement ps = con.prepareStatement(query);
                ps.setInt(1, Integer.parseInt(cargoId));
                ps.setInt(2, Integer.parseInt(flightId));
                ps.executeUpdate();

                JOptionPane.showMessageDialog(confirmBookingFrame, "Booking confirmed successfully!");
                confirmBookingFrame.dispose();
            } catch (SQLException ex) {
                System.out.println("Error confirming booking: " + ex);
            }
        });
    }

    // Cancel Booking
    public void cancelBooking() {
        Frame cancelBookingFrame = new Frame("Cancel Booking");

        Label bookingIdLabel = new Label("Booking ID:");
        TextField bookingIdField = new TextField();

        Button cancelButton = new Button("Cancel Booking");
        cancelButton.setBackground(Color.RED);

        cancelBookingFrame.setLayout(new GridLayout(2, 2));
        cancelBookingFrame.add(bookingIdLabel);
        cancelBookingFrame.add(bookingIdField);
        cancelBookingFrame.add(cancelButton);

        cancelBookingFrame.setSize(400, 200);
        cancelBookingFrame.setVisible(true);

        cancelButton.addActionListener(e -> {
            String bookingId = bookingIdField.getText();

            try {
                String query = "DELETE FROM CargoBooking WHERE BookingID=?";
                PreparedStatement ps = con.prepareStatement(query);
                ps.setInt(1, Integer.parseInt(bookingId));
                int rowsAffected = ps.executeUpdate();

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(cancelBookingFrame, "Booking cancelled successfully!");
                } else {
                    JOptionPane.showMessageDialog(cancelBookingFrame, "Booking not found!");
                }
                cancelBookingFrame.dispose();
            } catch (SQLException ex) {
                System.out.println("Error cancelling booking: " + ex);
            }
        });
    }

    public void displayFlights() {
        Frame displayFlightsFrame = new Frame("Available Flights");
        displayFlightsFrame.setLayout(new BorderLayout());

        TextArea flightsArea = new TextArea();
        flightsArea.setEditable(false);

        try {
            String query = "SELECT FlightID, DepartureAirport, ArrivalAirport, DepartureTime, ArrivalTime FROM Flight";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String flightInfo = "Flight ID: " + rs.getInt("FlightID") +
                        " | From: " + rs.getString("DepartureAirport") +
                        " To: " + rs.getString("ArrivalAirport") +
                        " | Departure: " + rs.getTimestamp("DepartureTime") +
                        " | Arrival: " + rs.getTimestamp("ArrivalTime") + "\n";
                flightsArea.append(flightInfo);
            }
        } catch (SQLException ex) {
            System.out.println("Error retrieving flights: " + ex.getMessage());
        }

        displayFlightsFrame.add(flightsArea, BorderLayout.CENTER);
        displayFlightsFrame.setSize(600, 400);
        displayFlightsFrame.setVisible(true);
    }

    public void displayCargoItems() {
        Frame displayCargoItemsFrame = new Frame("All Cargo Items");
        displayCargoItemsFrame.setLayout(new BorderLayout());

        TextArea cargoItemsArea = new TextArea();
        cargoItemsArea.setEditable(false);

        try {
            String query = "SELECT ID, TrackingID, SenderName, ReceiverName, Weight, Status FROM AirCargo";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String cargoInfo = "Cargo ID: " + rs.getInt("ID") +
                        " | Tracking ID: " + rs.getString("TrackingID") +
                        " | Sender: " + rs.getString("SenderName") +
                        " | Receiver: " + rs.getString("ReceiverName") +
                        " | Weight: " + rs.getFloat("Weight") + "kg" +
                        " | Status: " + rs.getString("Status") + "\n";
                cargoItemsArea.append(cargoInfo);
            }
        } catch (SQLException ex) {
            System.out.println("Error retrieving cargo items: " + ex.getMessage());
        }

        displayCargoItemsFrame.add(cargoItemsArea, BorderLayout.CENTER);
        displayCargoItemsFrame.setSize(600, 400);
        displayCargoItemsFrame.setVisible(true);
    }



    public static void main(String[] args) {
        new CargoManagementSystem();
    }
}
