package timeclock;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JLabel;

/**
 * @author Evan Black & Liz Stanton
 * Class Containing Title And Sort Dropdowns For Primary GUIs
 */
public class TitleandDropBoxes extends JPanel
{
	//Department Drop Down
	private Department[] departments; //Departments employees are in.
	private JComboBox<Department> boxDepartments; //Drop down box for our departments.
	private String currentDepartment; //Will hold the selected department.
	
	//Title
	private JLabel lblTitle;
	private String title;
	
	//Sort By Drop Down	
	private String[] sortBy = {"First Name", "Last Name", "Employee ID"}; //Ways employees can be sorted.
	private JComboBox boxSortBy; //Drop down box for employee sort.
	private String currentSort; //Will hold the selected sort.
	
	//Constructor
	public TitleandDropBoxes(String ptitle)
	{
			
		//Department Drop Down
		//boxDepartments = new JComboBox<Department>(departments);
		//boxDepartments.addActionListener(new ComboBoxListener());
		//this.add(boxDepartments);
		
		////////////////////////////////
		/////Need centered and formated to be bigger
		////////////////////////////////
		//Title
		title = ptitle;
		lblTitle = new JLabel(title);
		this.add(lblTitle);
		
		//Sort By Drop Down
		boxSortBy = new JComboBox(sortBy); 
		boxSortBy.addActionListener(new ComboBoxListener());
		//this.add(boxSortBy);
	}
	
	
	private class ComboBoxListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			if (e.getSource() == boxDepartments)
			{
				currentDepartment = (String)boxDepartments.getSelectedItem();
			}
			else if (e.getSource() == boxSortBy)
			{
				currentSort = (String)boxSortBy.getSelectedItem();
			}				
		}		
	}
}
