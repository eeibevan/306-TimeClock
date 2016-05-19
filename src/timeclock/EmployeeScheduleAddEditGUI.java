package timeclock;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPasswordField;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

/**
 * @author Evan Black & Elizabeth Stanton
 *  Add or Edit GUI for the Employee Schedule
 *  Note, Class Not Fully Implemented!
 */
public class EmployeeScheduleAddEditGUI extends JFrame
{
	private JLabel lblEmployeeName; //Label for employee name 
	private JComboBox boxEmployeeName; //Combo box for employee name to be entered
	
	private JLabel lblEmployeeID; //Label for employee ID 
	private JTextField txtEmployeeID; //Text box for employee ID to be entered
	
	private JLabel lblDay; //Label for day of the week
	private JComboBox boxDay; //Combo box for day of the week
	
	private JLabel lblStartTime; //Label for start date text box
	private JTextField txtStartTime; //Text box for start date
	
	private JLabel lblEndTime; //Label for end date text box
	private JTextField txtEndTime; //Text box for end date
	
	private JButton btnFinish; //Button to indicate you are finish and close the dialog

	/**
	 * Width Of The Dialogue
	 */
	private static final int WIDTH = 800;

	/**
	 * Height Of The Dialogue
	 */
	private static final int HEIGHT = 600;


	/**
	 * Constructor, Sets Up GUI Components
	 */
	public EmployeeScheduleAddEditGUI()
	{
		JPanel panel = new JPanel(); //Initializes a JPanel for use
	    panel.setLayout(new GridBagLayout()); //Sets layout grid bag
	    
	    
	    GridBagConstraints bag = new GridBagConstraints();
	    bag.fill = GridBagConstraints.NORTHEAST;
	    
		this.setTitle("Schedule Add Edit"); //Titles the frame
		this.setSize(WIDTH,HEIGHT); //Sets size of frame
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE); //Sets exit on close
	   
		
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
		
		txtEmployeeID = new JTextField(4); //Text box for employee ID to be entered or filled
		bag.gridx = 1; //Column 
		bag.gridy = 1; //Row 
		bag.gridwidth = 2;
		panel.add(txtEmployeeID, bag); //Add text box to the panel
		
		
		//Day
		lblDay = new JLabel("Day:"); //Label for day combo box
		bag.gridx = 0; //Column 
		bag.gridy = 2; //Row 
		bag.gridwidth = 1;
		panel.add(lblDay, bag); //Add label to the panel
		
		boxDay = new JComboBox(); //Combo box for day chose
		bag.gridx = 1; //Column 
		bag.gridy = 2; //Row 
		bag.gridwidth = 3;
		panel.add(boxDay, bag); //Add combo box to the panel
		
		
		//Start Time
		lblStartTime = new JLabel("Start Time:"); //Label for employee hour start time text box
		bag.gridx = 0; //Column
		bag.gridy = 3; //Row 
		bag.gridwidth = 1;
		panel.add(lblStartTime, bag); //Add label to the panel
		
		txtStartTime = new JTextField(10); //Text box for employee hour start time to be entered
		bag.gridx = 1; //Column 
		bag.gridy = 3; //Row 
		bag.gridwidth = 3;
		panel.add(txtStartTime, bag); //Add text box to the panel
		
		//End
		lblEndTime = new JLabel("End Time:"); //Label for employee hour end time text box
		bag.gridx = 5; //Column 
		bag.gridy = 3; //Row 
		bag.gridwidth = 1;
		panel.add(lblEndTime, bag); //Add label to the panel
		
		txtEndTime = new JTextField(10); //Text box for employee hour end time to be entered
		bag.gridx = 6; //Column
		bag.gridy = 3; //Row 
		bag.gridwidth = 3;
		panel.add(txtEndTime, bag); //Add text box to the panel
		
		
		//Done
		btnFinish = new JButton("Finish"); //Button to indicate you are done with the dialog and close it
		bag.gridx = 4; //Column
		bag.gridy = 4; //Row
		bag.gridwidth = 1;
		panel.add(btnFinish, bag); //Add button to the panel
		
		
		this.add(panel); //Adds panel to the frame
		//this.setResizable(false); //Set frame so it can't be resized
		this.setLocationRelativeTo(null); //Sets frame to open in the middle of the screen
		this.setVisible(true); //Make the window visible
	}
}
