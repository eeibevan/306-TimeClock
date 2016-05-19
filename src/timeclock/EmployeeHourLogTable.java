package timeclock;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 * @author Evan Black & Liz Stanton
 * Frame Containing A Table And Scroll Pane For Holding Employee Hours
 */
public class EmployeeHourLogTable extends JPanel
{
	/**
	 * Table Containing Shifts From The Database
	 */
	private JTable hourLogTable;

	/**
	 * Adds Scrolling Ability If There Are Many Employees
	 */
	private	JScrollPane scrollPane;

	/**
	 * Model That Interfaced With The Database, And Holds Actual Table Info
	 */
	private HourLogModel model;

	/**
	 * Constructor <br />
	 * Adds Graphical Components And Sets Up Table
	 */
	public EmployeeHourLogTable()
	{	
		this.setLayout( new BorderLayout() );

		model = new HourLogModel(new DatabaseInteraction());
		// Create a new table instance
		hourLogTable = new JTable(model);
		
		// Add the table to a scrolling pane
		scrollPane = new JScrollPane( hourLogTable );
		this.add( scrollPane, BorderLayout.CENTER );
	}

	/**
	 * <b>Method:</b> updateTable() <br />
	 * <b>Purpose:</b> Refreshes Table Data With That In The Database
	 */
	public void updateTable()
	{
		model.updateList();
	}


	/* ***** Accessors And Mutators ***** */

	public JTable getHourLogTable()
	{
		return hourLogTable;
	}

	public void setTableListener(MouseAdapter mouseAdapter)
	{
		hourLogTable.addMouseListener(mouseAdapter);
	}


	public HourLogModel getModel()
	{
		return model;
	}


}
