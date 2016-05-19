package timeclock;

/**
 * @author Evan Black and Elizabeth Stanton
 * Simple Model For Slim Employee Table
 */
public class EmployeeModelAside extends EmployeeModel
{
    /**
     * Names Of Columns That Appear
     */
    private static String[] columnNames = {"ID", "First Name", "Last Name"};

    /**
     * Constructor,
     *  Fills The Table With Data From The Database
     * @param dbo
     *  A Reference To The Database With Data To Fill The Table With
     */
    public EmployeeModelAside(DatabaseInteraction dbo)
    {
        super(dbo); // Handled By Parent Constructor
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
        return super.getValueAt(rowIndex, columnIndex);
    }
}
