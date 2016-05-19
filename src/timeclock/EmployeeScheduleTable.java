package timeclock;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class EmployeeScheduleTable extends JPanel
{
	private JTable scheduleTable;
	private	JScrollPane scrollPane;
	
	public EmployeeScheduleTable()
	{

		this.setLayout( new BorderLayout() );
		
		// Create columns names
		String columnsHeadings[] = { "Day", "Time In", "Time Out" };

	
		// Create a new table instance
		//scheduleTable = new JTable( rows, columnsHeadings );
		
		// Add the table to a scrolling pane
		scrollPane = new JScrollPane( scheduleTable );
		this.add( scrollPane, BorderLayout.CENTER );
		
	}
}
