package timeclock;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.*;

/**
 * @author Evan Black and Elizabeth Stanton
 * Manages Current Employee Information
 */
public class EmployeeInformationAddEditGUI extends JDialog
{
	private JLabel lblFirstName; //Label for first employee name
	private JTextField txtFirstName; //Combo box for first employee name to be entered

	private JLabel lblLastName; //Label for last employee name
	private JTextField txtLastName; //Combo box for last employee name to be entered

	private JLabel lblEmployeeID; //Label for employee ID
	private JTextField txtEmployeeID; //Text box for employee ID to be entered

	private JLabel lblEmail; //Label for employee email
	private JTextField txtEmail; //Text box for employee email to be entered

	private JLabel lblDepartment; //Label for department
	private JComboBox<Department> boxDepartment; //Combo box for department to be entered

	private JButton btnNewDepartment; //Button to indicate creating a new department

	private JLabel lblJobCode; //Label for job code
	private JComboBox<JobCode> boxJobCode; //Combo box for job code to be entered

	private JButton btnNewJobCode; //Button to indicate creating a new department job code

	private JButton btnFinish; //Button to indicate you are finish and close the dialog

    private JPanel panel;


    /**
     * Width Of The Dialogue
     */
    private static final int WIDTH = 600;

    /**
     * Height Of The Dialogue
     */
    private static final int HEIGHT = 500;

    /**
     * Object That Interfaces With The Database
     */
    private DatabaseInteraction dbo;

    /**
     * The Employee We Are Editing / Creating
     */
    private Employee employee;

    /**
     * Tracks If The Employee Is New (True)
     * Or, We Are Editing An Existing Employee (False)
     */
    private boolean isNewEmployee;

    /**
     * Constructor, Takes In An Employee To Edit And A Reference To The Database
     * @param pEmployee
     *  The Employee To Edit
     * @param pDBO
     *  A Reference To The Database That The Employee Is In
     */
    public EmployeeInformationAddEditGUI(Employee pEmployee, DatabaseInteraction pDBO)
    {
        // Stores Data Members
        employee = pEmployee;
        dbo = pDBO;

        // We Were Passed An Employee To Edit, So It Is Not New
        isNewEmployee = false;

        // Adds Graphical Component, Sets Up Listeners
        setUpGUI();
    }


    /**
     * Constructor, Takes In A Reference To The Database,
     * Also Makes This Dialogue Create A New Employee, And Inserts It When We Are Done
     *
     * @param pDBO
     * 	A Reference To The Database To Add The Hour To
     */
    public EmployeeInformationAddEditGUI(DatabaseInteraction pDBO)
    {
        // Stores Data Member
        dbo = pDBO;

        // No Employee Was Passed, So We Are Making A New One
        isNewEmployee = true;

        // Adds Graphical Component, Sets Up Listeners
        setUpGUI();
    }

    /**
     * <b>Method:</b> setWindowTitle() <br />
     * <b>Purpose:</b>  Sets The Window Title Based On If This Is A New Hour, Or We Re Editing One
     */
    private void setWindowTitle()
    {
        if (isNewEmployee)
        {
            this.setTitle("New Employee");
        }
        else
        {
            this.setTitle("Edit " + employee.getFirstName());
        }
    }

    /**
     * <b>Method:</b> setUpGUI() <br />
     * <b>Purpose:</b>  Sets The Graphical Components, Adds Listeners
     */
    private void setUpGUI()
    {
        // Assures That Other Things May Not Be Interacted With While This Is Open
        // Evan: Commented Out (No Modal So We Can Keep Interacting )
        // this.setModal(true);

        panel = new JPanel();
        panel.setLayout(new GridBagLayout()); //Sets layout grid bag

        setWindowTitle();
        this.setSize(WIDTH,HEIGHT); //Sets size of frame
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE); //Sets exit on close


        GridBagConstraints bag = new GridBagConstraints();
        bag.fill = GridBagConstraints.NORTHEAST;

        //First Name
        lblFirstName = new JLabel("First Name:"); //Label for employee name text box
        bag.weighty = 0.5;
        bag.gridx = 0; //Column
        bag.gridy = 0; //Row
        bag.ipady = 8;
        bag.ipadx = 10;
        bag.gridwidth = 1;
        panel.add(lblFirstName, bag); //Add label to the panel

        txtFirstName = new JTextField(10); //Text field for employee name to be entered
        bag.gridx = 1; //Column
        bag.gridy = 0; //Row
        bag.gridwidth = 3;
        panel.add(txtFirstName, bag); //Add Text field  to the panel

        //Last Name
        lblLastName = new JLabel("Last Name:"); //Label for employee name text box
        bag.weighty = 0.5;
        bag.gridx = 5; //Column
        bag.gridy = 0; //Row
        bag.ipady = 8;
        bag.ipadx = 10;
        bag.gridwidth = 1;
        panel.add(lblLastName, bag); //Add label to the panel

        txtLastName = new JTextField(10); //Text field  for employee name to be entered
        bag.gridx = 6; //Column
        bag.gridy = 0; //Row
        bag.gridwidth = 3;
        panel.add(txtLastName, bag); //Add Text field  to the panel

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

        //Email
        lblEmail = new JLabel("Email:"); //Label for employee ID text box
        bag.gridx = 0; //Column
        bag.gridy = 2; //Row
        bag.gridwidth = 1;
        panel.add(lblEmail, bag); //Add label to the panel

        txtEmail = new JTextField(15); //Text box for employee ID to be entered or filled
        bag.gridx = 1; //Column
        bag.gridy = 2; //Row
        bag.gridwidth = 3;
        panel.add(txtEmail, bag); //Add text box to the panel

        //Department
        lblDepartment = new JLabel("Department:"); //Label for employee name text box
        bag.weighty = 0.5;
        bag.gridx = 0; //Column
        bag.gridy = 3; //Row
        bag.gridwidth = 1;
        panel.add(lblDepartment, bag); //Add label to the panel

        boxDepartment = new JComboBox<Department>(dbo.retrieveAllDepartments()); //Combo box for employee name to be entered
        boxDepartment.addItemListener(new DepartmentListener());
        bag.gridx = 1; //Column
        bag.gridy = 3; //Row
        bag.gridwidth = 5;
        panel.add(boxDepartment, bag); //Add combo box to the panel

        //New Department
        btnNewDepartment = new JButton("New");
        bag.gridx = 7; //Column
        bag.gridy = 3; //Row
        bag.gridwidth = 1;
        panel.add(btnNewDepartment, bag); //Add button to the panel


        //Job Code
        lblJobCode = new JLabel("Job Code:"); //Label for employee name text box
        bag.weighty = 0.5;
        bag.gridx = 0; //Column
        bag.gridy = 4; //Row
        bag.gridwidth = 1;
        panel.add(lblJobCode, bag); //Add label to the panel


        boxJobCode = new JComboBox<JobCode>(dbo.retrieveJobsInDepartment( ((Department) boxDepartment.getSelectedItem()).getDepartmentID() )); //Combo box for Possible Jobs
        bag.gridx = 1; //Column
        bag.gridy = 4; //Row
        bag.gridwidth = 4;
        panel.add(boxJobCode, bag); //Add combo box to the panel

        //New Job Code
        btnNewJobCode = new JButton("New");
        bag.gridx = 4; //Column
        bag.gridy = 4; //Row
        bag.gridwidth = 1;
        panel.add(btnNewJobCode, bag); //Add button to the panel


        //Done
        btnFinish = new JButton("Finish"); //Button to indicate you are done with the dialog and close it
        btnFinish.addActionListener(new BtnListener());
        bag.gridx = 4; //Column
        bag.gridy = 5; //Row
        bag.gridwidth = 1;
        panel.add(btnFinish, bag); //Add button to the panel




        // If We Are Not Making A New Employee, Then Fill In The Known Info
        if (!isNewEmployee)
        {
            txtFirstName.setText(employee.getFirstName());
            txtLastName.setText(employee.getLastName());
            txtEmployeeID.setText("" + employee.getEmployeeID());
            txtEmail.setText(employee.getEmail());
            boxDepartment.setSelectedItem(employee.getDepartmentID());
        }



        this.add(panel); //Adds panel to the frame
        this.setResizable(false); //Set frame so it can't be resized
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
        // Temporary Storage For A Possibly New ID
        int newEmployeeID;

        if (!TimeClockUtil.isInt(txtEmployeeID.getText()))
        {
            txtEmployeeID.requestFocusInWindow();
            //JOptionPane.showMessageDialog(this, "Please Enter A Valid Integer For Employee ID");
            JOptionPane.showMessageDialog(this, "Please Enter A Valid Integer For Employee ID", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // ID Is Now Definitely An Integer, So We Parse It
        newEmployeeID = Integer.parseInt(txtEmployeeID.getText());

        // If This Is A New Employee, Make Sure Their ID Is Not Already Used
        if (isNewEmployee)
        {
            boolean isExistingEmployee;

            try
            {
                isExistingEmployee = dbo.isExistingEmployeeByID(newEmployeeID);
            }
            catch (SQLException e)
            {
                JOptionPane.showMessageDialog(this, "SQL Error \n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            if (isExistingEmployee) // Prompts The User IF The ID Was Taken
            {
                JOptionPane.showMessageDialog(this, "Employee ID Already Taken" , "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }


        return true;

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

    /**
     * <b>Method:</b> performAddEdit() <br />
     * <b>Purpose:</b> Validates, Then Commits The Form Data To The Database
     */
    private void performAddEdit()
    {
        // Only Start Adding If Data Valid
        if (verifyTextFields())
        {
            // Gathers Information For Employee
            int employeeID = Integer.parseInt(txtEmployeeID.getText());
            Department department = (Department) boxDepartment.getSelectedItem();
            JobCode job = (JobCode) boxJobCode.getSelectedItem();

            // Make Absolutely Sure The Selected ComboBox Item Is A Department
            if (boxDepartment.getSelectedItem() instanceof Department)
            {
                department = (Department) boxDepartment.getSelectedItem();
            }

            // Make Absolutely Sure The Selected ComboBox Item Is A Job
            if (boxDepartment.getSelectedItem() instanceof JobCode)
            {
                job = (JobCode) boxDepartment.getSelectedItem();
            }


            // If We Are Making An Employee
            if (isNewEmployee)
            {
                // Sets Up An Employee Object With All Of Our Known Info
                Employee toAdd = new Employee();
                toAdd.setEmployeeID(employeeID);
                toAdd.setFirstName(txtFirstName.getText());
                toAdd.setLastName(txtLastName.getText());
                toAdd.setEmail(txtEmail.getText());
                toAdd.setDepartmentID(department.getDepartmentID());
                toAdd.setJobCode(job.getJobCode());


                try // Attempts To Commit Data
                {
                    dbo.createNewEmployee(toAdd);
                    JOptionPane.showMessageDialog(null, "New Employee Created");
                    dispose();
                }
                catch (SQLException e)
                {
                    JOptionPane.showMessageDialog(null, "SQL Error \n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            else // Edit Session
            {
                try // Attempts To Update Existing Info
                {
                    dbo.updateEmployeeByID(employee.getEmployeeID(), employeeID, txtFirstName.getText(), txtLastName.getText(), txtEmail.getText(), department.getDepartmentID(), job.getJobCode());
                    JOptionPane.showMessageDialog(null, "Update Success");
                    dispose();

                }
                catch (SQLException e)
                {
                    JOptionPane.showMessageDialog(null, "SQL Error \n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }


    /**
     * Listener For Department Drop Down That Updates The Possible Job Codes Drop Down
     */
    private class DepartmentListener implements ItemListener
    {
        @Override
        public void itemStateChanged(ItemEvent itemEvent)
        {
            // Assures Event Only Fired Once On State Change
            if (itemEvent.getStateChange() == ItemEvent.SELECTED)
            {
                // Makes Sure That The List Is Only Populated With Existing Job Codes
                boxJobCode.removeAllItems();

                // Temporary Storage For Whatever Department We Are In Now
                Department selectedDept = null;

                // Make Absolutely Sure The Selected ComboBox Item Is A Department
                if (boxDepartment.getSelectedItem() instanceof Department)
                {
                    // Gets The Department From The Other Combo Box
                    selectedDept = ( (Department) boxDepartment.getSelectedItem() );
                }

                // Retries All Known Jobs From The Database
                Vector<JobCode> jobs = dbo.retrieveJobsInDepartment(selectedDept);

                // Adds Jobs In selectedDept To The Combo Box
                for (JobCode aJob : jobs)
                {
                    boxJobCode.addItem(aJob);
                }
            }

        }
    }

}
