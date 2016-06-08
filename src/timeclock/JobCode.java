package timeclock;

/**
 * @author Evan & Liz
 * Interface Class That Holds All Relivent Information For A Job Stored In The Database
 */
public class JobCode
{
    /**
     * The ID Of The Job In The Database
     */
    private int jobCode;

    /**
     * The ID Of The Department In The Database
     */
    private int departmentID;

    /**
     * The Name Of The Job In The Database
     */
    private String jobName;

    /**
     * Value Signifying An Unset And Meaningless Value
     * Should Be Changed Before Operated On
     */
    private static final int IGNORE_INT = -1;

    /**
     * Constructor
     * @param pJobCode
     *  ID of The Job
     * @param pDepartmentID
     *  ID Of Department That The Employee Belongs To
     * @param pJobName
     *  Name Of This Job
     */
    public JobCode(int pJobCode, int pDepartmentID, String pJobName)
    {
        jobCode = pJobCode;
        departmentID = pDepartmentID;
        jobName = pJobName;
    }

    /**
     * Constructor, Sets Up Empty Job
     */
    public JobCode()
    {
        this(IGNORE_INT,IGNORE_INT,"");
    }

    /**
     * Converts The Object To A String (Gives The Name)
     * @return
     * 	The Job Name
     */
    @Override
    public String toString()
    {
        return jobName;
    }

    /**
     * Tests If This Job Is The Same As Another Object
     * @param o
     * 	The Object To Test To See If We Are Equal To (Should Be A Job Object)
     * @return
     * 	True - The Jobs Are The Same <br />
     * 	False - The Jobs Are Not The Same
     */
    @Override
    public boolean equals(Object o)
    {
        // Tests By Reference If The Objects Are The Same Object
        if (this == o)
            return true;

        // If It's Not A Job Object, Then It Cannot Be Equal
        if (!(o instanceof JobCode))
            return false;

        // Setup To Compare Data Members
        JobCode job = (JobCode) o;

        // Tests If The Unique IDs Match
        if (getJobCode() != job.getJobCode())
            return false;

        // Tests If The Department IDs Match
        return getDepartmentID() == job.getDepartmentID();
    }

    //Accessor and Mutators 
    public int getJobCode() { return jobCode; }
    public void setJobCode(int jobCode) { this.jobCode = jobCode; }
    public int getDepartmentID() { return departmentID; }
    public void setDepartmentID(int departmentID) { this.departmentID = departmentID; }
    public String getJobName() { return jobName; }
    public void setJobName(String jobName) { this.jobName = jobName; }
}
