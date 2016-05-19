package timeclock;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

/**
 * @author Evan Black & Liz Stanton
 * Model For Hour Log Tables, Holds Data And Interfaces With Database
 *
 */
public class HourLogModel extends AbstractTableModel
{
    /**
     * Names Of Columns That Appear
     */
    private static String[] columnNames = {"Name", "Time In", "Time Out"};

    /**
     * ArrayList Of Data To Display In The Table
     */
    private ArrayList<Hour> hourList;

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
    public HourLogModel(DatabaseInteraction dbo)
    {
        this.dbo = dbo;
        hourList = dbo.retrieveAllHours();
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
        return hourList.size();
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
     *  The Hour At (rowIndex,columnIndex)
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        switch (columnIndex)
        {
            case 0:
                return hourList.get(rowIndex).getEmployeeID();
            case 1:
                return hourList.get(rowIndex).getTimeIn();
            case 2:
                return hourList.get(rowIndex).getTimeOut();
        }
        return null;
    }



    /**
     * <b>Method:</b> getColumnName() <br />
     * <b>Purpose:</b> Returns String Name Of A Column
     * @param column
     *  The Number Of The Column
     * @return
     *  The Column's Name As A String
     */
    @Override
    public String getColumnName(int column)
    {
        return columnNames[column];
    }

    /**
     * <b>Method:</b> updateList() <br />
     * <b>Purpose:</b> Refreshes The Stored Data, And Informs The Table That We Did So
     */
    public void updateList()
    {
        hourList = dbo.retrieveAllHours();
        this.fireTableDataChanged();
    }

    /* ***** Accessors ***** */

    public Hour getHourAtRow(int row)
    {
        return hourList.get(row);
    }

    public ArrayList<Hour> getHourList()
    {
        return hourList;
    }



}
