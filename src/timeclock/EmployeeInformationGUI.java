package timeclock;

import java.awt.*;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.border.Border;

/**
 * @author Evan Black and Elizabeth Stanton
 *  Manages Current Employee Information
 */
public class EmployeeInformationGUI extends TableFrame
{

	/**
	 * Title Of The Window
	 */
	private String title;

	/**
	 * Table With Employee Info
	 */
	private EmployeeInformationTable employeeInformationTable;

	/**
	 * Reference To The Database
	 */
	private DatabaseInteraction dbo;


	/**
	 * Width Of The Window
	 */
	private static final int WIDTH = 700;

	/**
	 * Height Of The Window
	 */
	private static final int HEIGHT = 500;


	public EmployeeInformationGUI(DatabaseInteraction dbo)
	{
		title = "Employee Information";
		this.setTitle(title); //Titles the frame
		this.setSize(WIDTH,HEIGHT); //Sets size of frame
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE); //Sets Dispose on close, So We Don't Close Everything

		this.dbo = dbo;

		//Title, department and sort drop boxes 
		TitleandDropBoxes titleandDropBoxes = new TitleandDropBoxes(title);
		this.add(titleandDropBoxes, BorderLayout.NORTH);
		
		//Table in the middle
		employeeInformationTable = new EmployeeInformationTable(false, this.dbo);
		this.add(employeeInformationTable, BorderLayout.CENTER);
		
		AddEditDeleteButtons addEditDeleteButtons = new AddEditDeleteButtons(this);
		this.add(addEditDeleteButtons, BorderLayout.SOUTH);


		this.setLocationRelativeTo(null); //Sets frame to open in the middle of the screen
		this.setVisible(true); //Make the window visible
	}


	/**
	 * Sets Up An Edit Dialogue On A Double Clicked Row
	 */
	private void showEditDialogue()
	{
		JTable table = employeeInformationTable.getEmployeeInformationTable();
		showEditDialogue(table.getSelectedRow());
	}

	/**
	 * Sets Up An Edit Dialogue On A Row
	 * @param row
	 * 	The Number Of The Row Containing An Employee (Usually Retrieved From The Table)
	 */
	private void showEditDialogue(int row)
	{
		EmployeeInformationAddEditGUI employeeInformationAddEditGUI = new EmployeeInformationAddEditGUI(
				employeeInformationTable.getModel().getEmployeeAtRow(row),
				dbo
		);

		employeeInformationTable.updateTable();

	}

	/**
	 * Opens A Dialogue To Add An Employee
	 */
	private void openNewDialogue()
	{
		EmployeeInformationAddEditGUI employeeInformationAddEditGUI = new EmployeeInformationAddEditGUI(
				dbo
		);

		employeeInformationTable.updateTable();

	}

	/**
	 * Deletes A Selected Employee
	 */
	private void deleteEmployee()
	{
		JTable table = employeeInformationTable.getEmployeeInformationTable();
		int row = table.getSelectedRow();

		// Assures An Employee Is Selected
		if (row != TimeClockUtil.NO_SELECTED_ROW)
		{
			try
			{
				Employee toRemove = employeeInformationTable.getModel().getEmployeeAtRow(table.getSelectedRow());
				dbo.removeEmployee(toRemove);
				employeeInformationTable.updateTable();
			}
			catch (SQLException e)
			{
				JOptionPane.showMessageDialog(null, "SQL Error \n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}



		}
	}


	/**
	 * Opens A Dialogue To Add A Shift
	 */
	@Override
	public void openAddDialog()
	{
		openNewDialogue();
	}

	/**
	 * Sets Up An Edit Dialogue
	 */
	@Override
	public void openEditDialog()
	{
		showEditDialogue();
	}

	/**
	 * Deletes A Selected Employee
	 */
	@Override
	public void deleteItem()
	{
		deleteEmployee();
	}

}
