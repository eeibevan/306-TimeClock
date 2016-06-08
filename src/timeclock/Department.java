package timeclock;

/**
 * @author Evan & Liz
 * Interface Class That Holds All Relivent Information For A Department Stored In The Database
 */
public class Department 
{
	/**
	 * The ID Of The Department In The Database
	 */
	private int departmentID;

	/**
	 * The Name Of The Department In The Database
	 */
	private String departmentName;

	/**
	 * Constructor, Stores The Department Name And ID
	 * @param pDepartmentID
	 * 			The Unique ID Of The Department
	 * @param pDepartmentName
	 * 			The Name Of The Department
     */
	public Department(int pDepartmentID, String pDepartmentName)
	{
		departmentID = pDepartmentID;
		departmentName = pDepartmentName;
	}
	public Department(int pDepartmentID)
	{
		departmentID = pDepartmentID;
		departmentName = "";
	}

	/**
	 * Converts The Object To A String (Gives The Name)
	 * @return
	 * 	The Department Name
	 */
	@Override
    public String toString()
    {
        return departmentName;
    }


	/**
	 * Tests If This Department Is The Same As Another Object
	 * @param o
	 * 	The Object To Test To See If We Are Equal To (Should Be A Department Object)
	 * @return
	 * 	True - The Departments Are The Same <br />
	 * 	False - The Departments Are Not The Same
     */
    @Override
    public boolean equals(Object o)
    {
		// Tests By Reference If The Objects Are The Same Object
        if (this == o)
            return true;

		// If It's Not A Department Object, Then It Cannot Be Equal
        if (!(o instanceof Department))
            return false;

		// Setup To Compare Data Members
        Department that = (Department) o;

		// Tests If The Unique IDs Match,
		// If So, Then Everything Else Should Match
        return getDepartmentID() == that.getDepartmentID();

    }



	// ***** Accessors And Mutators *****

    public int getDepartmentID() {return departmentID;}
	public void setDepartmentID(int departmentID) {this.departmentID = departmentID;}
	public String getDepartmentName() {return departmentName;}
	public void setDepartmentName(String departmentName) {this.departmentName = departmentName;}
}
