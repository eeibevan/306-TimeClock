package timeclock;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class EmployeeInformationTable extends JPanel
{
    /**
     * Table Containing Employees
     */
	private JTable employeeInformationTable;

    /**
     * Allows For Scrolling
     */
    private	JScrollPane scrollPane;

    /**
     * Reference To The Database
     */
    private DatabaseInteraction dbo;

    /**
     * If The Table Is A Full Table (False) <br />
     * Or A Slimmed Down Version Meant To Be Displayed With Other Data (True)
     */
    private boolean isAsideTable;

    /**
     * The Model Contining The Catual Data
     */
    private EmployeeModel model;

    /**
     * Constructor
     * @param pIsAsideTable
     *  If The Table Is A Slim Version (True) <br />
     *  Or A Full Version (False)
     */
    public EmployeeInformationTable(boolean pIsAsideTable)
    {
        // Stores Data Member
        isAsideTable = pIsAsideTable;

        // Sets Layout
        this.setLayout(new BorderLayout());

        // Sets Up The Database Reference
        dbo = new DatabaseInteraction();
        dbo.assureConnection();


        if (!isAsideTable) // Set Up Full Table
        {
            model = new EmployeeModel(dbo);
            employeeInformationTable = new JTable(model);
            employeeInformationTable.addMouseListener(new TableMouseListener()); // Only Full Table Currently Has Listener
        }
        else // Set Up Aside Table
        {
            model = new EmployeeModelAside(dbo);
            employeeInformationTable = new JTable(model);
        }

        //employeeInformationTable.addMouseListener(tableListener);
        // Add the table to a scrolling pane
        scrollPane = new JScrollPane(employeeInformationTable);
        this.add(scrollPane, BorderLayout.CENTER);

    }


    public void updateTable()
    {
        model.updateList();
    }


    /* ***** Accessors And Mutators ***** */
    public JTable getEmployeeInformationTable()
    {
        return employeeInformationTable;
    }

    public void setTableListener(MouseAdapter mouseAdapter)
    {
        employeeInformationTable.addMouseListener(mouseAdapter);
    }

    public EmployeeModel getModel()
    {
        return model;
    }

    public void setModel(EmployeeModel model)
    {
        this.model = model;
    }



    /**
     * Inner Class To Handle Double Clicks
     */
    private class TableMouseListener extends MouseAdapter
    {

        @Override
        public void mousePressed(MouseEvent mouseEvent)
        {
            // Assures Double CLick
            if (mouseEvent.getSource() == employeeInformationTable && mouseEvent.getClickCount() == 2)
            {
                // Gets The Clicked Row
                int row = employeeInformationTable.rowAtPoint( mouseEvent.getPoint() );

                if (!isAsideTable) // Assured This A Table That Should Be Edited
                {
                    // Brings Up Add Edit GUI
                    EmployeeInformationAddEditGUI employeeInformationAddEditGUI = new EmployeeInformationAddEditGUI(
                            ( (EmployeeModel) employeeInformationTable.getModel()).getEmployeeAtRow(row),
                            dbo
                    );
                }


            }
        }
    }

}
