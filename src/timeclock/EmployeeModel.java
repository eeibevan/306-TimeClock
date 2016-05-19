package timeclock;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Vector;

/**
 * @author Evan Black & Liz Stanton
 * Model For Employee Tables, Holds Data And Interfaces With Database
 *
 */
public class EmployeeModel extends AbstractTableModel
{

    /**
     * Names Of Columns That Appear
     */
    private static String[] columnNames = {"ID", "First Name", "Last Name" ,"Email", "Department", "Job"};

    /**
     * ArrayList Of Data To Display In The Table
     */
    private ArrayList<Employee> employeeList;

    /**
     * Reference To The Database
     */
    private DatabaseInteraction dbo;


    /**
     * Constructor,
     *  Fills The Table With Data From The Database
     * @param dbo
     *  A Reference To The Database With Data To Fill The Table With
     */
    public EmployeeModel(DatabaseInteraction dbo)
    {
        this.dbo = dbo;
        updateList();

    }

    /**
     * <b>Method:</b> getRowCount() <br />
     * <b>Purpose:</b> Returns Number Of Rows That Are In The Table
     * @return
     *  Number Of Rows
     */
    @Override
    public int getRowCount()
    {
        return employeeList.size();
    }

    /**
     * <b>Method:</b> getColumnCount() <br />
     * <b>Purpose:</b> Returns Number Of Columns That Are In The Table
     * @return
     *  Number Of Columns
     */
    @Override
    public int getColumnCount()
    {
        return columnNames.length;
    }

    /**
     * <b>Method:</b> getValueAt() <br />
     * <b>Purpose:</b> Retrieve A Value At A Specific Location In The Table
     * @param rowIndex
     *  The Row The Value Is In
     * @param columnIndex
     *  The Column The Value Is In
     * @return
     *  The Employee At (rowIndex,columnIndex)
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        switch (columnIndex)
        {
            case 0:
                return employeeList.get(rowIndex).getEmployeeID();
            case 1:
                return employeeList.get(rowIndex).getFirstName();
            case 2:
                return employeeList.get(rowIndex).getLastName();
            case 3:
                return employeeList.get(rowIndex).getEmail();
            case 4:
                return employeeList.get(rowIndex).getDepartmentID();
            case 5:
                return employeeList.get(rowIndex).getJobCode();
        }
        return null;
    }

    /**
     * <b>Method:</b> updateList() <br />
     * <b>Purpose:</b> Refreshes The Stored Data, And Informs The Table That We Did So
     */
    public void updateList()
    {
        employeeList = dbo.retrieveAllEmployees();
        this.fireTableDataChanged();
    }

    /* ***** Accessors ***** */

    @Override
    public String getColumnName(int column)
    {
        return columnNames[column];
    }

    public Employee getEmployeeAtRow(int row)
    {
        return employeeList.get(row);
    }



}
