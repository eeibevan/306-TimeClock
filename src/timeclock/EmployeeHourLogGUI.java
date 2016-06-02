package timeclock;


import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

/**
 * @author Evan Black and Elizabeth Stanton
 * Track Hours Logged by Employees
 */
public class EmployeeHourLogGUI extends TableFrame
{
	/**
	 * Title Of The Window
	 */
	String title;

	/**
	 * Reference To The Database
	 */
	DatabaseInteraction dbo;

	/**
	 * Width Of The Window
	 */
	private static final int WIDTH = 950;

	/**
	 * Height Of The Window
	 */
	private static final int HEIGHT = 600;


	/**
	 * Handles Info On Employees
	 */
	EmployeeInformationTable employeeInformationTable;

	/**
	 * Holds Editable Hours
	 */
	EmployeeHourLogTable employeeHourLogTable;

	/**
	 * Buttons For Change Operations
	 */
	AddEditDeleteButtons addEditDeleteButtons;


	/**
	 * Constructor <br />
	 * Adds Graphical Components And Sets Up Listeners
	 */
	public EmployeeHourLogGUI(DatabaseInteraction dbo)
	{
		title = "Employee Hour Log";
		this.setTitle(title); //Titles the frame 
		this.setSize(WIDTH,HEIGHT); //Sets size of frame

		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE); //Sets exit on close
		this.setLayout(new GridBagLayout()); //Sets layout grid bag


		// Controls Persistent Data
		this.dbo = dbo;
	    
	    GridBagConstraints bag = new GridBagConstraints();
	    bag.fill = GridBagConstraints.NORTHEAST;
		
		//Title, department and sort drop boxes 
		TitleandDropBoxes titleandDropBoxes = new TitleandDropBoxes(title);
		bag.weighty = 0.5;
		bag.gridx = 0; //Column 
		bag.gridy = 0; //Row 
		bag.ipady = 8; 
	    bag.ipadx=10;
		bag.gridwidth = 3;
		this.add(titleandDropBoxes, bag);
		


		//Table in the middle
		employeeInformationTable = new EmployeeInformationTable(true, this.dbo);
		bag.gridx = 0; //Column 
		bag.gridy = 1; //Row 
		bag.gridwidth = 1;
		this.add(employeeInformationTable, bag);


		// Hours
		employeeHourLogTable = new EmployeeHourLogTable();
		employeeHourLogTable.setTableListener(new EditHourMouse());
		bag.gridx = 1; //Column 
		bag.gridy = 1; //Row
		bag.gridwidth = 2;
		this.add(employeeHourLogTable, bag);

		// Bottom Buttons
		addEditDeleteButtons = new AddEditDeleteButtons(this);
		bag.gridx = 0; //Column 
		bag.gridy = 2; //Row 
		bag.gridwidth = 3;
		this.add(addEditDeleteButtons, bag);
		
		this.setLocationRelativeTo(null); //Sets frame to open in the middle of the screen
		this.setVisible(true); //Make the window visible
	}


	/**
	 * <b>Method:</b> showEditDialogue() <br />
	 * <b>Purpose:</b> Sets Up An Edit Dialogue On A Double Clicked Row
	 */
	private void showEditDialogue()
	{
		JTable table = employeeHourLogTable.getHourLogTable();
		int row = table.getSelectedRow();

		// Assures That The User Clicked A Row
		if (row != TimeClockUtil.NO_SELECTED_ROW)
		{
			showEditDialogue(row);
		}
	}

	/**
	 * <b>Method:</b> showEditDialogue() <br />
	 * <b>Purpose:</b> Sets Up An Edit Dialogue On A Row
	 * @param row
	 * 	The Number Of The Row Containing An Hour (Usually Retrieved From The Table)
     */
	private void showEditDialogue(int row)
	{
		// Assures That The Row Is Valid
		if (row != TimeClockUtil.NO_SELECTED_ROW)
		{
			EmployeeHourLogAddEditGUI employeeHourLogAddEditGUI = new EmployeeHourLogAddEditGUI(
					employeeHourLogTable.getModel().getHourAtRow(row),
					dbo
			);

			// Updates The Info In The Table From The Database
			//	In Case The Info Was Changed
			employeeHourLogTable.updateTable();
		}
	}

	/**
	 * <b>Method:</b> openNewDialogue() <br />
	 * <b>Purpose:</b> Opens A Dialogue To Add A Shift
	 */
	private void openNewDialogue()
	{
		EmployeeHourLogAddEditGUI employeeHourLogAddEditGUI = new EmployeeHourLogAddEditGUI(
				dbo
		);

		// Updates The Info In The Table From The Database
		//	In Case The Info Was Changed
		employeeHourLogTable.updateTable();
	}


	/**
	 * <b>Method:</b> openNewDialogue() <br />
	 * <b>Purpose:</b> Opens A Dialogue To Add A Shift
	 */
	@Override
	public void openAddDialog()
	{
		openNewDialogue();
	}

	/**
	 * <b>Method:</b> showEditDialogue() <br />
	 * <b>Purpose:</b> Sets Up An Edit Dialogue
	 */
	@Override
	public void openEditDialog()
	{
		showEditDialogue();
	}

	@Override
	public void deleteItem()
	{
		// Not Implemented
	}

	/**
	 * Listener For Double Click To Edit Tables
	 */
	private class EditHourMouse extends MouseAdapter
	{
		// Reference To Check Source Of Event
		JTable table = employeeHourLogTable.getHourLogTable();

		@Override
		public void mousePressed(MouseEvent mouseEvent)
		{
			// Check For Double Clicks, and Makes Sure The Event Came From The Table
			if (mouseEvent.getSource() == table && mouseEvent.getClickCount() == 2)
			{
				// Gets The Row They Clicked On
				int row = table.rowAtPoint( mouseEvent.getPoint() );
				showEditDialogue(row);

			}
		}
	}


}
