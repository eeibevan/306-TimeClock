package timeclock;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.*;

/**
 * @author Evan Black and Elizabeth Stanton
 * Add or Edit GUI for the Hours Logged by Employees
 */
public class EmployeeHourLogAddEditGUI extends JDialog
{
	private JLabel lblEmployeeName; //Label for employee name 
	private JComboBox boxEmployeeName; //Combo box for employee name to be entered
	
	private JLabel lblEmployeeID; //Label for employee ID 
	private JTextField txtEmployeeID; //Text box for employee ID to be entered
	
	private JLabel lblDay; //Label for day of the week
	private JComboBox boxDay; //Combo box for day of the week
	
	private JLabel lblStartDate; //Label for start date text box
	private JTextField txtStartDate; //Text box for start date
	
	private JLabel lblStartTime; //Label for start date text box
	private JTextField txtStartTime; //Text box for start Time
	
	private JLabel lblEndDate; //Label for end date text box
	private JTextField txtEndDate; //Text box for end date
	
	private JLabel lblEndTime; //Label for end date text box
	private JTextField txtEndTime; //Text box for end Time
	
	private JButton btnFinish; //Button to indicate you are finished and close the dialog

	/**
	 * Width Of The Dialogue
	 */
	private static final int WIDTH = 600;

	/**
	 * Height Of The Dialogue
	 */
	private static final int HEIGHT = 300;


	/**
	 * Object That Interfaces With The Database
	 */
	private DatabaseInteraction dbo;

	/**
	 * The Hour We Are Editing / Creating
	 */
	private Hour hour;

	/**
	 * Tracks If The Hour Is New (True)
	 * Or, We Are Editing An Existing Hour (False)
	 */
	private boolean isNewHour;


	/**
	 * Constructor, Takes In An Hour To Edit And A Reference To The Database
	 * @param hour
	 * 	The Hour To Change
	 * @param dbo
	 * 	A Reference To The Database That The Hour Is In
     */
	public EmployeeHourLogAddEditGUI(Hour hour, DatabaseInteraction dbo)
	{

		// Stores Data Members
		this.dbo = dbo;
		this.hour = hour;

		// We Were Passed An Hour To Edit, So It Is Not New
		isNewHour = false;

		// Adds Graphical Component, Sets Up Listeners
		setUpGUI();
	}

	/**
	 * Constructor, Takes In A Reference To The Database,
	 * Also Makes This Dialogue Create A New Hour, And Inserts It When We Are Done
	 *
	 * @param dbo
	 * 	A Reference To The Database To Add The Hour To
     */
	public EmployeeHourLogAddEditGUI(DatabaseInteraction dbo)
	{
		// Stores Data Member
		this.dbo = dbo;

		// No Hour Was Passed, So We Are Making A New One
		isNewHour = true;

		// Adds Graphical Component, Sets Up Listeners
		setUpGUI();
	}

	/**
	 * <b>Method:</b> setWindowTitle() <br />
	 * <b>Purpose:</b>  Sets The Window Title Based On If This Is A New Hour, Or We Re Editing One
	 */
	private void setWindowTitle()
	{
		if (isNewHour)
		{
			this.setTitle("New Shift");
		}
		else
		{
			this.setTitle("Edit Shift");
		}
	}

	/**
	 * <b>Method:</b> setUpGUI() <br />
	 * <b>Purpose:</b>  Sets The Graphical Components, Adds Listeners
	 */
	private void setUpGUI()
	{
		// Assures That Other Things May Not Be Interacted With While This Is Open
		this.setModal(true);

		JPanel panel = new JPanel(); //Initializes a JPanel for use
		panel.setLayout(new GridBagLayout()); //Sets layout grid bag


		GridBagConstraints bag = new GridBagConstraints();
		bag.fill = GridBagConstraints.NORTHEAST;

		setWindowTitle();
		this.setSize(WIDTH,HEIGHT); //Sets size of frame
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE); //Sets exit on close


		//Name
		lblEmployeeName = new JLabel("Name:"); //Label for employee name text box
		bag.weighty = 0.5;
		bag.gridx = 0; //Column
		bag.gridy = 0; //Row
		bag.ipady = 8;
		bag.ipadx=10;
		bag.gridwidth = 1;
		panel.add(lblEmployeeName, bag); //Add label to the panel

		boxEmployeeName = new JComboBox(); //Combo box for employee name to be entered
		bag.gridx = 1; //Column
		bag.gridy = 0; //Row
		bag.gridwidth = 6;
		panel.add(boxEmployeeName, bag); //Add combo box to the panel

		//ID
		lblEmployeeID = new JLabel("ID:"); //Label for employee ID text box
		bag.gridx = 0; //Column
		bag.gridy = 1; //Row
		bag.gridwidth = 1;
		panel.add(lblEmployeeID, bag); //Add label to the panel

		txtEmployeeID = new JTextField(5); //Text box for employee ID to be entered or filled
		bag.gridx = 1; //Column
		bag.gridy = 1; //Row
		bag.gridwidth = 2;
		panel.add(txtEmployeeID, bag); //Add text box to the panel

		//Start Date
		lblStartDate = new JLabel("Start Date:"); //Label for employee hour date time text box
		bag.gridx = 0; //Column
		bag.gridy = 3; //Row
		bag.gridwidth = 1;
		panel.add(lblStartDate, bag); //Add label to the panel

		txtStartDate = new JTextField(10); //Text box for employee hour start date to be entered
		bag.gridx = 1; //Column
		bag.gridy = 3; //Row
		bag.gridwidth = 3;
		panel.add(txtStartDate, bag); //Add text box to the panel

		//End Date
		lblEndDate = new JLabel("End Date:"); //Label for employee hour end date text box
		bag.gridx = 5; //Column
		bag.gridy = 3; //Row
		bag.gridwidth = 1;
		panel.add(lblEndDate, bag); //Add label to the panel

		txtEndDate = new JTextField(10); //Text box for employee hour end date to be entered
		bag.gridx = 6; //Column
		bag.gridy = 3; //Row
		bag.gridwidth = 3;
		panel.add(txtEndDate, bag); //Add text box to the panel

		//Start Time
		lblStartTime = new JLabel("Start Time:"); //Label for employee hour start time text box
		bag.gridx = 0; //Column
		bag.gridy = 4; //Row
		bag.gridwidth = 1;
		panel.add(lblStartTime, bag); //Add label to the panel

		txtStartTime = new JTextField(10); //Text box for employee hour start time to be entered
		bag.gridx = 1; //Column
		bag.gridy = 4; //Row
		bag.gridwidth = 3;
		panel.add(txtStartTime, bag); //Add text box to the panel

		//End Time
		lblEndTime = new JLabel("End Time:"); //Label for employee hour end time text box
		bag.gridx = 5; //Column
		bag.gridy = 4; //Row
		bag.gridwidth = 1;
		panel.add(lblEndTime, bag); //Add label to the panel

		txtEndTime = new JTextField(10); //Text box for employee hour end time to be entered
		bag.gridx = 6; //Column
		bag.gridy = 4; //Row
		bag.gridwidth = 3;
		panel.add(txtEndTime, bag); //Add text box to the panel


		//Done
		btnFinish = new JButton("Finish"); //Button to indicate you are done with the dialog and close it
		btnFinish.addActionListener(new BtnListener());
		bag.gridx = 4; //Column
		bag.gridy = 5; //Row
		bag.gridwidth = 1;
		panel.add(btnFinish, bag); //Add button to the panel

		// If This Hour Is Not A New One
		//	Insert Known Values
		if (!isNewHour)
		{
			txtEmployeeID.setText(hour.getEmployeeID() + "");
			txtStartDate.setText(hour.getDateIn() + "");
			txtEndDate.setText(hour.getDateOut() + "");
			txtStartTime.setText(hour.getTimeIn() + "");
			txtEndTime.setText(hour.getTimeOut() + "");
		}

		this.add(panel); //Adds panel to the frame
		//this.setResizable(false); //Set frame so it can't be resized
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null); //Sets frame to open in the middle of the screen
		this.setVisible(true); //Make the window visible
	}

	/**
	 * <b>Method:</b> verifyTextFields() <br />
	 * <b>Purpose:</b> Assures That The Values Currently In The Text Fields Are Valid,
	 * 	Notifies The User And Returns false If They Are Not
	 *
	 * 	@return
	 * 	True - All Text Field Values Are Valid, And May Be Parsed And Used <br />
	 * 	False - At Least One Text Field Is Not Valid
	 */
	private boolean verifyTextFields()
	{
		// Tests If The ID Is A Valid Format (int)
		if (!TimeClockUtil.isInt(txtEmployeeID.getText()))
		{
			txtEmployeeID.requestFocusInWindow();
			JOptionPane.showMessageDialog(this, "Please Enter A Valid Integer For Employee ID", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		// ID Is Now Certainly An Int, So We Parse It
		int employeeID = Integer.parseInt(txtEmployeeID.getText());

		try
		{
			if (!dbo.isExistingEmployeeByID(employeeID))
			{
				JOptionPane.showMessageDialog(this, "ID: " + employeeID + " Not Found In Database!", "Error", JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}
		catch (SQLException e)
		{
			JOptionPane.showMessageDialog(this, "SQL Error \n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (!TimeClockUtil.isValidDate(txtStartDate.getText()))
		{
			txtStartDate.requestFocusInWindow();
			JOptionPane.showMessageDialog(this, "Please Enter A Valid Date For Date In", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (!TimeClockUtil.isValidDate(txtEndDate.getText()))
		{
			txtEndDate.requestFocusInWindow();
			JOptionPane.showMessageDialog(this, "Please Enter A Valid Date For Date Out", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (!TimeClockUtil.isValidTime(txtStartTime.getText()))
		{
			txtStartTime.requestFocusInWindow();
			JOptionPane.showMessageDialog(this, "Please Enter A Valid Time For Time In", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (!TimeClockUtil.isValidTime(txtEndTime.getText()))
		{
			txtEndTime.requestFocusInWindow();
			JOptionPane.showMessageDialog(this, "Please Enter A Valid Time For Time Out", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		return true;
	}

	/**
	 * <b>Method:</b> performAddEdit() <br />
	 * <b>Purpose:</b> Validates, Then Commits The Form Data To The Database
	 */
	private void performAddEdit()
	{
		// Only Start Adding If Data Valid
		if (verifyTextFields())
        {

            // ID Is Now Certainly An Int, So We Parse It
            int employeeID = Integer.parseInt(txtEmployeeID.getText());

            // Now A Valid Date, So Parse
            Date dateIn = TimeClockUtil.parseDate(txtStartDate.getText());

            // Now A Valid Time, So Parse
            Time timeIn = TimeClockUtil.parseTime(txtStartTime.getText());

            // Now A Valid Date, So Parse
            Date dateOut = TimeClockUtil.parseDate(txtEndDate.getText());

            // Now A Valid Time, So Parse
            Time timeOut = TimeClockUtil.parseTime(txtEndTime.getText());


			// Also Process Timestamps (For Newer Time Implementation)
            Timestamp inTime = TimeClockUtil.parseTimestamp(txtStartDate.getText() + " " + txtStartTime.getText());
            Timestamp outTime = TimeClockUtil.parseTimestamp(txtEndDate.getText() + " " + txtEndTime.getText());

            if (isNewHour) // Is Add Session
            {
				// Create an Hour With All Of Our Known Info
                Hour toAdd = new Hour(employeeID, timeIn, dateIn, timeOut, dateOut);
                try
                {
                    dbo.createNewShift(toAdd);
                }
                catch (SQLException e)
                {
                    JOptionPane.showMessageDialog(null, "SQL Error \n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            else // Is Update Session
            {
                try
                {
					// Updates The Existing Info
                    dbo.updateShiftByID(hour.getRecordID(), employeeID, inTime, outTime);
                }
                catch (SQLException e)
                {
                    JOptionPane.showMessageDialog(null, "SQL Error \n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
	}

	/**
	 * Private Listener Class To Handle The Button Press
	 */
	private class BtnListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent actionEvent)
		{
			if (actionEvent.getSource() == btnFinish)
			{
				performAddEdit();
			}
		}
	}
}
