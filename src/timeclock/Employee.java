package timeclock;

/**
 * @author Evan & Liz
 * Interface Class That Holds All Relivent Information For An Employee Stored In The Database
 */
public class Employee 
{
	/**
	 * The Unique ID Of The Employee In The Database
	 */
	private int employeeID;

	/**
	 * The First Name Of The Employee
	 */
	private String firstName;

	/**
	 * The Last Name Of The Employee
	 */
	private String lastName;

	/**
	 * The Email Address Of The Employee
	 */
	private String email;

	/**
	 * The Unique ID Of The Department That The Employee Is A Part Of
	 * @see timeclock.Department
	 */
	private int departmentID;

	/**
	 * The Unique ID Of The Job That The Employee Performs
	 * @see timeclock.JobCode
	 */
	private int jobCode;

	/**
	 * Value Signifying An Unset And Meaningless Value
	 * Should Be Changed Before Operated On
	 */
	private static final int IGNORE_INT = -1;

	/**
	 * Default Constructor<br />
	 * Sets Up An Empty Employee
	 */
	public Employee()
	{
        this(IGNORE_INT,"","","", IGNORE_INT, IGNORE_INT);
	}

	/**
	 * Constructor, Stores All Relevant Employee Info <br />
	 * @param pEmployeeID
	 * 	The Unique ID Of The Employee
	 * @param pFirstName
	 * 	The First Name Of The Employee
	 * @param pLastName
	 * 	The Last Name Of The Employee
	 * @param pEmail
	 * 	The Email Address Of The Employee
	 * @param pDepartmentID
	 * 	The Unique ID Of The Department That The Employee Is A Part Of
     * @param pJobCode
	 *	The Unique ID Of The Job That The Employee Performs
	 */
	public Employee(int pEmployeeID, String pFirstName, String pLastName, String pEmail, int pDepartmentID, int pJobCode)
	{
		// Stores Passed In Info
		employeeID = pEmployeeID;
		firstName = pFirstName;
		lastName = pLastName;
		email = pEmail;
		departmentID = pDepartmentID;
		jobCode = pJobCode;
	}

	/**
	 * <b>Method:</b> toString() <br />
	 * <b>Purpose:</b>  Converts The Object To A String (Gives First And Last Name)
	 * @return
	 * 	The Employee's First And Last Name Separated By A Space
	 */
	@Override
    public String toString()
    {
        return firstName + " " +lastName;
    }


	/**
	 * <b>Method:</b> equals() <br />
	 * <b>Purpose:</b> Tests If This Employee Is The Same As Another Object
	 * @param o
	 * 	The Object To Test To See If We Are Equal To (Should Be An Employee Object)
	 * @return
	 * 	True - The Employees Are The Same <br />
	 * 	False - The Employees Are Not The Same
	 */
    @Override
    public boolean equals(Object o)
    {
		// Tests By Reference If The Objects Are The Same Object
        if (this == o)
            return true;

		// If It's Not An Employee Object, Then It Cannot Be Equal
        if (!(o instanceof Employee))
            return false;

		// Setup To Compare Data Members
        Employee employee = (Employee) o;

		// Tests If The Unique IDs Match,
		// If So, Then Everything Else Should Match
        return getEmployeeID() == employee.getEmployeeID();

    }



	// ***** Accessors And Mutators *****

	public int getEmployeeID() { return employeeID; }
	public void setEmployeeID(int employeeID) {	this.employeeID = employeeID; }
	public String getFirstName() { return firstName; }
	public void setFirstName(String firstName) { this.firstName = firstName; }
	public String getLastName() { return lastName; }
	public void setLastName(String lastName) { this.lastName = lastName; }
	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }
	public int getDepartmentID() { return departmentID; }
	public void setDepartmentID(int departmentID) { this.departmentID = departmentID; }
	public int getJobCode() { return jobCode; }
	public void setJobCode(int jobCode) { this.jobCode = jobCode; }
}
