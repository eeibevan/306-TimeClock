package timeclock;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

/**
 * @author Evan & Liz
 * Interface Class That Holds All Relivent Information For A Shift Stored In The Database
 */
public class Hour
{
    /**
     * The ID Of The Hour In The Database
     */
    private int recordID;

    /**
     * The ID Of The Employee In The Database
     */
    private int employeeID;

    /**
     * SQL Time Value For When The Employee Clocked In
     */
    private Time timeIn;

    /**
     * SQL Date Value For When The Employee Clocked In
     */
    private Date dateIn;

    /**
     * SQL Time Value For When The Employee Clocked Out
     */
    private Time timeOut;

    /**
     * SQL Date Value For When The Employee Clocked Out
     */
    private Date dateOut;

    /**
     * Constant To Signify That The Stored Record ID Is Not Meaningful
     */
    private static final int IGNORE_RECORD_ID = -1;

    /**
     * Constructor, Stores Relevant Data Members
     * @param recordID
     *  The ID To Attach To The Shift
     * @param employeeID
     *  The ID OF The Employee That Worked The Shift
     * @param timeIn
     *  Timestamp Of When The Clock In Occurred
     * @param timeOut
     * Timestamp Of When The Clock Out Occurred
     *
     */
    public Hour(int recordID, int employeeID, Timestamp timeIn, Timestamp timeOut)
    {
        // Stored Data Members
        this.recordID = recordID;
        this.employeeID = employeeID;
        this.timeIn = new Time(timeIn.getTime());
        this.dateIn = new Date(timeIn.getTime());
        this.timeOut = new Time(timeOut.getTime());
        this.dateOut = new Date(timeOut.getTime());
    }

    /**
     * Constructor, Stores Relevant Data Members
     * @param recordID
     *  The ID To Attach To The Shift
     * @param employeeID
     *  The ID OF The Employee That Worked The Shift
     * @param timeIn
     * SQL Time Value For When The Clock In Occurred
     * @param dateIn
     * Sql Date Value For When The Clock In Occurred
     * @param timeOut
     * SQL Time Value For When The Clock Out Occurred
     * @param dateOut
     * Sql Date Value For When The Clock Out Occurred
     */
    public Hour(int recordID, int employeeID, Time timeIn, Date dateIn, Time timeOut, Date dateOut)
    {
        // Stored Data Members
        this.recordID = recordID;
        this.employeeID = employeeID;
        this.timeIn = timeIn;
        this.dateIn = dateIn;
        this.timeOut = timeOut;
        this.dateOut = dateOut;
    }

    /**
     * Constructor, Stores Relevant Data Members For A New Shift
     * @param employeeID
     *  The ID OF The Employee That Worked The Shift
     * @param timeIn
     * SQL Time Value For When The Clock In Occurred
     * @param dateIn
     * Sql Date Value For When The Clock In Occurred
     * @param timeOut
     * SQL Time Value For When The Clock Out Occurred
     * @param dateOut
     * Sql Date Value For When The Clock Out Occurred
     */
    public Hour (int employeeID, Time timeIn, Date dateIn, Time timeOut, Date dateOut)
    {
        this(IGNORE_RECORD_ID,employeeID,timeIn,dateIn,timeOut,dateOut);
    }


    /**
     * <b>Method:</b> equals() <br />
     * <b>Purpose:</b> Tests If This Hour Is The Same As Another Object
     * @param o
     * 	The Object To Test To See If We Are Equal To (Should Be An Hour Object)
     * @return
     * 	True - The Hour Are The Same <br />
     * 	False - The Hour Are Not The Same
     */
    @Override
    public boolean equals(Object o)
    {
        // Tests By Reference If The Objects Are The Same Object
        if (this == o) return true;

        // If It's Not An Hour Object, Then It Cannot Be Equal
        if (!(o instanceof Hour)) return false;

        // Setup To Compare Data Members
        Hour hour = (Hour) o;

        // Tests If The Unique IDs Match
        if (getRecordID() != hour.getRecordID())
            return false;

        // Tests If The Employee IDs Match
        return getEmployeeID() == hour.getEmployeeID();

    }

    /* ***** Accessors And Mutators ***** */

    public Time getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(Time timeOut) {
        this.timeOut = timeOut;
    }

    public Time getTimeIn() {
        return timeIn;
    }

    public void setTimeIn(Time timeIn) {
        this.timeIn = timeIn;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public int getRecordID() {
        return recordID;
    }

    public void setRecordID(int recordID) {
        this.recordID = recordID;
    }

    public Date getDateIn() {
        return dateIn;
    }

    public void setDateIn(Date dateIn) {
        this.dateIn = dateIn;
    }

    public Date getDateOut() {
        return dateOut;
    }

    public void setDateOut(Date dateOut) {
        this.dateOut = dateOut;
    }
}
