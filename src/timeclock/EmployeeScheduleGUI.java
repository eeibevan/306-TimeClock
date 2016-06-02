package timeclock;

import javax.swing.JFrame;

/**
 * @author Evan Black & Elizabeth Stanton
 *  Tracks Expected Hours of Employees
 *  Note, Class Not Fully Implemented!
 */
public class EmployeeScheduleGUI extends JFrame
{
	/**
	 * Title Of The Window
	 */
	String title; 
	public EmployeeScheduleGUI()
	{
		title = "Employee Schedule";
		this.setTitle(title); //Titles the frame 
		this.setSize(500,300); //Sets size of frame
		this.setDefaultCloseOperation(EXIT_ON_CLOSE); //Sets exit on close
		
		//Title, department and sort drop boxes 
		TitleandDropBoxes titleandDropBoxes = new TitleandDropBoxes(title);
		this.add(titleandDropBoxes);
		

		////////////////////////////////
		/////Layout needs these two tables to be side by side
		////////////////////////////////
		//Table in the middle
		//EmployeeInformationTable employeeInformationTable = new EmployeeInformationTable(true, this.dbo);
		//this.add(employeeInformationTable);
		
		//EmployeeHourLogTable hourLogTable = new EmployeeHourLogTable(this.dbo);
		//this.add(hourLogTable);
		
		AddEditDeleteButtons addEditDeleteButtons = new AddEditDeleteButtons();
		this.add(addEditDeleteButtons);
		
		this.setLocationRelativeTo(null); //Sets frame to open in the middle of the screen
		this.setVisible(true); //Make the window visible
	}
}
