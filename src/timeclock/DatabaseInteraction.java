package timeclock;

import java.io.FileInputStream;
import java.io.IOException;
import javax.sql.DataSource;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Vector;


/**
 * @author Evan
 * Class For Interfacing With The Database
 *
 */
public class DatabaseInteraction
{
    /**
     * Destination For All Connections
     */
    private DataSource dataSource;

    /**
     * Connection Statements Built From
     */
    private Connection connection;

    /**
     * Default Constructor,
     * Sets Up The Data Source, And Opens Our Connection
     */
    DatabaseInteraction()
    {
        dataSource = getMySQLDataSource();
        establishConnectionSilent();
    }


    /**
     * Configures Server Info Needed Before Making A Connection
     * @return
     *      A Datasource Set Up With Properties From The Properties File
     */
    private static DataSource getMySQLDataSource()
    {
        // Parses For Properties
        Properties props = new Properties();

        // To Bring In The Properties From The Filesystem
        FileInputStream fis = null;

        // Datasource To Be Setup And Returned
        MysqlDataSource mysqlDS = null;

        try
        {
            // Opens And Reads Properties File
            fis = new FileInputStream("db.properties");
            props.load(fis);
            mysqlDS = new MysqlDataSource();
            mysqlDS.setURL(props.getProperty("MYSQL_DB_URL"));
            mysqlDS.setUser(props.getProperty("MYSQL_DB_USERNAME"));
            mysqlDS.setPassword(props.getProperty("MYSQL_DB_PASSWORD"));
        }
        catch (IOException e) //TODO: Don't Swallow This Exception
        {
            e.printStackTrace();
        }

        // Returns Completed Datasource
        return mysqlDS;
    }

    /**
     * Attempts To Connect To The Server, And Set The connection Data Member
     * @throws SQLException
     *          Connection To The Server Fails
     */
    private void establishConnection() throws SQLException
    {
        connection = dataSource.getConnection();
    }

    /**
     * Attempts To Connect To The Server, And Set The connection Data Member
     *  Throws No Exceptions, Even If Connection Fails
     */
    private void establishConnectionSilent()
    {
        try
        {
            connection = dataSource.getConnection();
        }
        catch (SQLException e)
        {
            // Ignored
        }
    }


    /**
     * Tests If The Connection Is Closed, Or Not Set Up
     * @return
     *  True - The Connection To The Database Is Up <br />
     *  False - Connection To The Database Was Not Up
     */
    private boolean isConnectionAlive()
    {
        // If Not Setup, May Be Null
        if (connection == null)
            return false;


        try
        {
            // May Be Closed, If Not Closed And Alive
            return connection.isClosed();
        }
        catch (SQLException ex)
        {
            return false;
        }
    }

    /**
     * Makes Sure The Connection To The Database Is Up,
     *  And Established One If It Is Not
     * @return
     *      True - The Connection To The Database Is Up <br />
     *      False - Connection To The Database Was Not Up, ANd Could Not Be Established.
     */
    public boolean assureConnection()
    {

        // If Connection Is Okay, No Need To Do Anything
        if (!isConnectionAlive())
        {
            try
            {
                establishConnection();
            }
            catch (SQLException e)
            {
                return false;
            }
        }

        return true;
    }


    /**
     * Retrieves A Set Of All Employees From The Database
     * @return
     *  A ResultSet of ALl Employees
     * @throws SQLException
     *  Connection To The Server Failed, Or The Database Was Not Found
     */
    ResultSet getAllEmployees() throws SQLException
    {
        Statement statement;
        statement = connection.createStatement();

        return statement.executeQuery("SELECT * FROM timeclock.employee");
    }


    /**
     * Retrieves A Set Of All Hours From The Database
     *
     * @return
     *  A ResultSet of All Hours
     * @throws SQLException
     *  Connection To The Server Failed, Or The Database Was Not Found
     */
    ResultSet getAllHours() throws SQLException
    {
        Statement statement;
        statement = connection.createStatement();
        return statement.executeQuery("SELECT * FROM timeclock.employeeHourLog");
    }

    /**
     * Retrieves ArrayList Of All Hours From The Database
     *
     * @return
     *  ArrayList Of Hours, Each One A Shift In The Database
     */
    public ArrayList<Hour> retrieveAllHours() // TODO: Change To Use Vectors
    {
        if (!assureConnection())
            return null;

        ArrayList<Hour> toReturn = new ArrayList<>();
        ResultSet allHours = null;

        try
        {
            allHours = getAllHours();

            // Adds One Hour Object For Every Row In The ResultSet
            while (allHours.next())
            {
                Hour toAdd = new Hour(allHours.getInt("RecordID"),allHours.getInt("EmployeeID"), allHours.getTimestamp("TimeIn"),allHours.getTimestamp("TimeOut"));
                toReturn.add(toAdd);
            }
        }
        catch (SQLException e)
        {
            return null;
        }
        finally
        {
            closeSilently(allHours);
        }

        return toReturn;
    }

    /**
     * Retrieves ArrayList Of All Employees From The Server
     *
     * @return
     *  ArrayList Of Employees, Each One An Employee In The Database
     */
    public ArrayList<Employee> retrieveAllEmployees()
    {
        if (!assureConnection())
            return null;

        ArrayList<Employee> toReturn = new ArrayList<>();
        ResultSet allEmp = null;


        try
        {
            allEmp = getAllEmployees();

            // Adds One Employee Object For Every Row In The ResultSet
            while (allEmp.next())
            {
                Employee toAdd = new Employee(allEmp.getInt("EmployeeID"), allEmp.getString("FirstName"), allEmp.getString("LastName"), allEmp.getString("Email"), allEmp.getInt("DepartmentID"), allEmp.getInt("JobCode"));
                toReturn.add(toAdd);
            }

        }
        catch (SQLException ex)
        {
            return null;
        }
        finally
        {
            closeSilently(allEmp);
        }

        return toReturn;
    }

    /**
     * Retrieves Vector Of All Departments From The Database
     *
     * @return
     *  Vector Of Departments, Each An Entry In The Department Table
     */
    public Vector<Department> retrieveAllDepartments()
    {
        if (!assureConnection())
            return null;

        Vector<Department> toReturn = new Vector<>();
        ResultSet allDepts = null;


        try
        {
            allDepts = getAllDepartments();

            // Adds One Department Object For Every Row In The ResultSet
            while (allDepts.next())
            {
                Department toAdd = new Department(allDepts.getInt("DepartmentID"), allDepts.getString("DepartmentName"));
                toReturn.add(toAdd);
            }
        }
        catch (SQLException e)
        {
            return null;
        }
        finally
        {
            closeSilently(allDepts);
        }

        return toReturn;
    }

    /**
     * Retrieves Vector Of All JobCodes From The Database (For ComboBoxes)
     * @return
     *  Vector Of JobCodes, Each An Entry In The jobCode Table
     */
    public Vector<JobCode> retrieveAllJobs()
    {
        if (!assureConnection())
            return null;

        Vector<JobCode> toReturn = new Vector<>();
        ResultSet allJobs = null;


        try
        {
            allJobs = getAllJobs();

            // Adds One JobCode Object For Every Row In The ResultSet
            while (allJobs.next())
            {
                JobCode toAdd = new JobCode(allJobs.getInt("JobCode"), allJobs.getInt("DepartmentID"), allJobs.getString("JobName"));
                toReturn.add(toAdd);
            }
        }
        catch (SQLException e)
        {
            return null;
        }
        finally
        {
            closeSilently(allJobs);
        }

        return toReturn;

    }

    /**
     * Retrieves Vector Of JobCodes From The Database Belonging To A Specific Department
     *
     * @param pDepartmentID
     *  The ID Of The Department With Job Codes
     * @return
     *  Vector Of JobCodes, Each An Entry In The jobCode Table Tied To A Specific Department
     */
    public Vector<JobCode> retrieveJobsInDepartment(int pDepartmentID)
    {
        if (!assureConnection())
            return null;

        Vector<JobCode> toReturn = new Vector<>();
        ResultSet someJobs = null;


        try
        {
            someJobs = getJobsByDepartment(pDepartmentID);

            // Adds One JobCode Object For Every Row In The ResultSet
            while (someJobs.next())
            {
                JobCode toAdd = new JobCode(someJobs.getInt("JobCode"), someJobs.getInt("DepartmentID"), someJobs.getString("JobName"));
                toReturn.add(toAdd);
            }
        }
        catch (SQLException e)
        {
            return null;
        }
        finally
        {
            closeSilently(someJobs);
        }

        return toReturn;
    }

    /**
     * Retrieves Vector Of JobCodes From The Server Belonging To A Specific Department (For ComboBoxes)
     * @param department
     *  A Department Object With An ID Set
     * @return
     *  Vector Of JobCodes, Each An Entry In The jobCode Table Tied To A Specific Department
     */
    public Vector<JobCode> retrieveJobsInDepartment(Department department)
    {
        return this.retrieveJobsInDepartment(department.getDepartmentID());
    }

    /**
     * Retrieves A Specific Employee (Or None If pEmployeeID Does Not Match Anything)
     *
     * @param employeeID
     *  The ID Of The Employee To Look For
     * @return
     *  A Result Set Of One Or Zero Employees
     * @throws SQLException
     *  Connection To The Server Failed, Or The Database Was Not Found
     */
    ResultSet getEmployeeByID(int employeeID) throws SQLException
    {
        String sql =
                "SELECT * FROM timeclock.employee " +
                        "WHERE EmployeeID = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, employeeID);


        return preparedStatement.executeQuery();
    }


    /**
     * Retrieves All Entries In Department Table
     *
     * @return
     *  Result Set Of Every Department
     * @throws SQLException
     *  Connection To The Server Failed, Or The Database Was Not Found
     */
    ResultSet getAllDepartments() throws SQLException
    {
        Statement statement;
        statement = connection.createStatement();

        return statement.executeQuery("SELECT * FROM timeclock.department");

    }

    /**
     * Retrieves All Entries In jobCode Table
     *
     * @return
     *  Result Set Of Every jobCode
     * @throws SQLException
     *  Connection To The Server Failed, Or The Database Was Not Found
     */
    ResultSet getAllJobs() throws SQLException
    {
        Statement statement = connection.createStatement();
        return statement.executeQuery("SELECT * FROM timeclock.jobCode");
    }

    /**
     * Retrieves All Jobs In A Specific Department
     *
     * @param pDepartmentID
     *  The ID Of The Department With The Job
     * @return
     *  Result Set Of All Jobs From That Department
     * @throws SQLException
     *  Connection To The Server Failed, Or The Database Was Not Found
     */
    ResultSet getJobsByDepartment(int pDepartmentID) throws SQLException
    {
        String sql =
                "SELECT * FROM jobCode " +
                        "WHERE DepartmentID=?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, pDepartmentID);
        return preparedStatement.executeQuery();
    }

    /**
     * Retrieves A Specific Department (Or None If pDepartmentID Does Not Match Anything)
     *
     * @param pDepartmentID
     *  The ID Of The Department To Search For
     * @return
     *  A Result Set Of One Or Zero Departments
     * @throws SQLException
     *  Connection To The Server Failed, Or The Database Was Not Found
     */
    ResultSet getDepartmentByID(int pDepartmentID) throws SQLException
    {
        String sql =
                "SELECT * FROM timeclock.department " +
                        "WHERE DepartmentID = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, pDepartmentID);
        return preparedStatement.executeQuery();
    }

    /**
     * Attempts To Begin A Shift For An Employee
     *  Not Fully Implemented!
     *
     * @param pEmployeeID
     *  The ID OF The Employee To Start The Shift Of
     * @return
     *  True - The Shift Was Successfully Started <br />
     *  False  - The Shift Could Not Be Started
     */
    boolean clockEmployeeIn(int pEmployeeID) // TODO: Complete Method
    {

        try
        {
            // Makes Sure Connection Is Good
            if (!assureConnection())
                return false;


            // Checks If The Employee Is Already In
            if( !isEmployeeClockedIn(pEmployeeID) )
            {
                insertShift(pEmployeeID);
            }
            else
            {
                return false;
            }
        }
        catch (SQLException ex)
        {
            return false;
        }
        finally
        {
            closeConnectionSilently();
        }


        return true;
    }

    /**
     * Attempts To Create A New Employee, Will Check To See If Unique Fields Already
     *  Are Taken (ID) Before Creation
     *
     * @param newEmployeeID
     * 	The Unique ID Of The Employee
     * @param firstName
     * 	The First Name Of The Employee
     * @param lastName
     * 	The Last Name Of The Employee
     * @param email
     * 	The Email Address Of The Employee
     * @param departmentID
     * 	The Unique ID Of The Department That The Employee Is A Part Of
     * @param jobCode
     *	The Unique ID Of The Job That The Employee Performs
     *
     * @return
     *  True - The Employee Was Successfully Created <br />
     *  False - The Employee Could Not Be Created, The ID May Be Taken
     *
     *  @throws SQLException
     *      Database Failure
     */
    boolean createNewEmployee (int newEmployeeID, String firstName, String lastName, String email, int departmentID, int jobCode) throws SQLException
    {
        if (!isExistingEmployeeByID(newEmployeeID))
        {
            insertNewEmployee(newEmployeeID, firstName, lastName, email, departmentID, jobCode);
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Attempts To Create A New Employee, Will Check To See If Unique Fields Already
     *  Are Taken (ID) Before Creation
     *
     * @param newEmployee
     *  An Employee Object Containing All Relevant Info
     *
     * @return
     *  True - The Employee Was Successfully Created <br />
     *  False - The Employee Could Not Be Created
     *
     * @throws SQLException
     *  Database Failure
     */
    boolean createNewEmployee (Employee newEmployee) throws SQLException
    {
        return this.createNewEmployee(newEmployee.getEmployeeID(), newEmployee.getFirstName(), newEmployee.getLastName(), newEmployee.getEmail(), newEmployee.getDepartmentID(), newEmployee.getJobCode());
    }

    /**
     * Attempts To Remove An Employee
     *
     * @param employeeToRemove
     *  An Employee Object Containing At Least An ID
     * @throws SQLException
     *  Database Failure
     */
    void removeEmployee(Employee employeeToRemove) throws SQLException
    {
        deleteEmployeeByID(employeeToRemove.getEmployeeID());
    }

    /**
     * Attempts To Remove An Employee
     *
     * @param employeeID
     *  The ID Of The Employee To Remove
     * @throws SQLException
     *  Database Failure
     */
    void removeEmployee(int employeeID) throws SQLException
    {
        deleteEmployeeByID(employeeID);
    }

    /**
     * Attempts Create A New Shift
     *
     * @param employeeID
     *  The ID OF The Employee That Worked The Shift
     * @param timeIn
     *  SQL Time Value For When The Clock In Occurred
     * @param dateIn
     *  Sql Date Value For When The Clock In Occurred
     * @param timeOut
     *  SQL Time Value For When The Clock Out Occurred
     * @param dateOut
     *  Sql Date Value For When The Clock Out Occurred
     *@return
     *  True - The Shift Was Successfully Created <br />
     *  False - The Shift Could Not Be Created
     * @throws SQLException
     *  Database Failure
     */
    boolean createNewShift(int employeeID, Time timeIn, Date dateIn, Time timeOut, Date dateOut) throws SQLException
    {
        insertNewHour(employeeID,timeIn,dateIn,timeOut,dateOut);
        return true;
    }

    /**
     * Attempts Create A New Shift
     *
     * @param newHour
     *  An Hour Containing All Relevant Info
     * @return
     *  True - The Shift Was Successfully Created <br />
     *  False - The Shift Could Not Be Created
     * @throws SQLException
     *  Database Failure
     */
    boolean createNewShift(Hour newHour) throws SQLException
    {
        return createNewShift(newHour.getEmployeeID(), newHour.getTimeIn(), newHour.getDateIn(), newHour.getTimeOut(), newHour.getDateOut());
    }


    /**
     * Tests If The Employee Has An Active Shift
     *
     * @param employeeID
     *  The ID Of The Employee To Check For An Active Shift
     * @return
     *  True - The Employee Is In <br />
     *  False - The Employee Is Not In
     * @throws SQLException
     *  Database Failure
     */
    private boolean isEmployeeClockedIn(int employeeID) throws SQLException
    {
        ResultSet resultSet = null;
        try
        {
            resultSet = getEmployeeInShiftsByID(employeeID);

            // If ResultSet Is Empty (No Active Shifts), Will Return False
            return !resultSet.next();
        }
        finally
        {
            closeSilently(resultSet);
        }

    }

    /**
     * Get All Shifts For A Given Employee
     *
     * @param employeeID
     *  The ID Of An Employee To Get All Shifts Of
     * @return
     *  A ResultSet Of All Shifts Fur An Employee
     * @throws SQLException
     *  Database Failure
     */
    ResultSet getEmployeeInShiftsByID(int employeeID) throws SQLException
    {
        String sql =
                "SELECT * FROM timeclock.employeeHourLog " +
                        "WHERE EmployeeID=? AND ISNULL(TimeOut) ";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, employeeID);


        return preparedStatement.executeQuery();

    }

    /**
     * Inserts A Default Shift For A Given Employee
     *
     * @param employeeID
     *  The ID Of The Employee To Add The Shift For
     * @throws SQLException
     *  Database Failure
     */
    private void insertShift(int employeeID) throws SQLException
    {
        String sql =
                "INSERT INTO timeclock.employeeHourLog (EmployeeID) " +
                        "VALUES (  ?  )";


        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, employeeID);

        preparedStatement.executeUpdate();
        preparedStatement.closeOnCompletion();

    }

    /**
     * Ends An Open Shift For A Given Employee
     *
     * @param employeeID
     *  The ID Of The Employee To Close The Shift For
     * @return
     *  True - The Employee's Shift Was Closed <br />
     *  False - The Employee's Shift Could Not Be Closed
     * @throws SQLException
     *  Database Failure
     */
    boolean endShift(int employeeID) throws SQLException
    {
        if (isEmployeeClockedIn(employeeID))
        {
            String sql =
                    "UPDATE timeclock.employeeHourLog " +
                            "SET TimeOut=NOW() "+
                            "WHERE EmployeeID=? AND ISNULL(TimeOut)";


            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, employeeID);


            preparedStatement.executeUpdate();
            preparedStatement.closeOnCompletion();

            return true;
        }
        else
        {
            return false;
        }


    }

    /**
     * Removes A Shift From The Database
     *
     * @param pShiftID
     *  The ID OF The Shift
     * @throws SQLException
     *  Database Failure
     */
    private void deleteShiftByID(int pShiftID) throws SQLException
    {
        String sql = "DELETE FROM employeehourlog " +
                "WHERE EmployeeID=?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1,pShiftID);

        preparedStatement.executeUpdate();
        preparedStatement.closeOnCompletion();
    }

    /**
     * Removes An Employee From The Database
     *
     * @param employeeID
     *  The ID OF The Employee
     * @throws SQLException
     *  Database Failure
     */
    private void deleteEmployeeByID(int employeeID) throws SQLException
    {
        String sql = "DELETE FROM employee " +
                "WHERE EmployeeID=?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1,employeeID);

        preparedStatement.executeUpdate();
        preparedStatement.closeOnCompletion();
    }

    /**
     * Updated Info Associated With A Given Employee
     *
     * @param currentEmployeeID
     *  The Current Unique ID Of The Employee
     * @param newEmployeeID
     * 	The New Unique ID Of The Employee
     * @param firstName
     * 	The First Name Of The Employee
     * @param lastName
     * 	The Last Name Of The Employee
     * @param email
     * 	The Email Address Of The Employee
     * @param departmentID
     * 	The Unique ID Of The Department That The Employee Is A Part Of
     * @param jobCode
     *	The Unique ID Of The Job That The Employee Performs
     * @throws SQLException
     *  Database Failure
     */
    public void updateEmployeeByID(int currentEmployeeID, int newEmployeeID, String firstName, String lastName, String email, int departmentID, int jobCode) throws SQLException
    {
        String sql =
                "UPDATE employee " +
                        "SET EmployeeID=?, " +
                        "DepartmentID=?, " +
                        "JobCode=?, " +
                        "FirstName=?, " +
                        "LastName=?, " +
                        "Email=? " +
                        "WHERE EmployeeID=?;";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setInt(1, newEmployeeID);
        preparedStatement.setInt(2, departmentID);
        preparedStatement.setInt(3, jobCode);
        preparedStatement.setString(4, firstName);
        preparedStatement.setString(5, lastName);
        preparedStatement.setString(6, email);
        preparedStatement.setInt(7, currentEmployeeID);

        preparedStatement.executeUpdate();
        closeSilently(preparedStatement);
    }

    /**
     * Updated Info Associated With A Given Shift
     *
     * @param recordID
     *  The Current Unique ID Of The Shift
     * @param employeeID
     *  The New Unique ID Of The Employee
     * @param timeIn
     *  The Timestamp Of When The Shift Starts
     * @param timeOut
     *  The Timestamp Of When The Shift Ends
     * @throws SQLException
     *   Database Failure
     */
    public void updateShiftByID(int recordID, int employeeID, Timestamp timeIn, Timestamp timeOut) throws SQLException
    {
        String sql =
                "UPDATE employeeHourLog " +
                        "SET EmployeeID=?, " +
                        "TimeIn=?, " +
                        "TimeOut=? " +
                        "WHERE RecordID=? ";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setInt(1,employeeID);
        preparedStatement.setTimestamp(2,timeIn);
        preparedStatement.setTimestamp(3,timeOut);
        preparedStatement.setInt(4,recordID);

        preparedStatement.executeUpdate();
        closeSilently(preparedStatement);
    }

    /**
     * Method Checks If A Passed In ID Is Already In The Database
     *
     * @param employeeID
     *  The ID To Check For
     * @return
     *  True - The ID Is Already In The Database <br />
     *  False - The ID Is Not In The Database
     * @throws SQLException
     *  If We Lose Connection To The Server
     */
    boolean isExistingEmployeeByID(int employeeID) throws SQLException
    {
        // Should Be 0 Or 1 Employees
        ResultSet resultSet = getEmployeeByID(employeeID);
        try
        {
            // If 0, Then Returns False
            return resultSet.next();
        }
        finally
        {
            closeSilently(resultSet);
        }

    }

    /**
     * Private Creation Method For Employee
     *
     * @param newEmployeeID
     * 	The Unique ID Of The Employee
     * @param firstName
     * 	The First Name Of The Employee
     * @param lastName
     * 	The Last Name Of The Employee
     * @param email
     * 	The Email Address Of The Employee
     * @param departmentID
     * 	The Unique ID Of The Department That The Employee Is A Part Of
     * @param jobCode
     *	The Unique ID Of The Job That The Employee Performs
     *
     *  @throws SQLException
     *      Database Failure
     */
    private void insertNewEmployee(int newEmployeeID, String firstName, String lastName, String email, int departmentID, int jobCode) throws SQLException
    {
        String sql =
                "INSERT INTO timeclock.employee (EmployeeID, FirstName, LastName, Email, DepartmentID, JobCode) " +
                        "VALUES (?,?,?,?,?,?)";


        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setInt(1, newEmployeeID);
        preparedStatement.setString(2, firstName);
        preparedStatement.setString(3, lastName);
        preparedStatement.setString(4, email);
        preparedStatement.setInt(5, departmentID);
        preparedStatement.setInt(6, jobCode);


        preparedStatement.executeUpdate();
        closeSilently(preparedStatement);
    }

    /**
     * Private Creation Method For Shift
     *
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
     *  @throws SQLException
     *      Database Failure
     */
    private void insertNewHour(int employeeID, Time timeIn, Date dateIn, Time timeOut, Date dateOut) throws SQLException
    {

        // Convert Values To Timestamp For Storage In Database
        Timestamp timeStampIn = new Timestamp(dateIn.getTime() + timeIn.getTime());
        Timestamp timeStampOut = new Timestamp(dateOut.getTime() + timeOut.getTime());

        String sql =
                "INSERT INTO timeclock.employeeHourLog (EmployeeID, TimeIn, TimeOut) " +
                        "VALUES (?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);


        preparedStatement.setInt(1, employeeID);
        preparedStatement.setTimestamp(2,timeStampIn);
        preparedStatement.setTimestamp(3,timeStampOut);

        preparedStatement.executeUpdate();
        closeSilently(preparedStatement);
    }

    /**
     * Closes The Passed In Item Silently (No Exception Throwing)
     *
     * @param pResultSet
     *  A ResultSet To Close
     */
    private void closeSilently(ResultSet pResultSet)
    {
        try
        {
            pResultSet.close();
        }
        catch (SQLException e)
        {
            // Ignored
        }
    }

    /**
     * Closes The Passed In Item Silently (No Exception Throwing)
     *
     * @param preparedStatement
     *  A PreparedStatement To Close
     */
    private void closeSilently(PreparedStatement preparedStatement)
    {
        try
        {
            preparedStatement.close();
        }
        catch (SQLException e)
        {
            // Ignored
        }
    }

    /**
     * Closes The Passed In Item Silently (No Exception Throwing)
     *
     * @param statement
     *  A Statement To Close
     */
    private void closeSilently(Statement statement)
    {
        try
        {
            statement.close();
        }
        catch (SQLException e)
        {
            // Ignored
        }
    }

    /**
     * Closes The Passed In Item Silently (No Exception Throwing)
     *
     * @param connection
     *  A Connection To Close
     */
    private void closeSilently(Connection connection)
    {
        try
        {
            connection.close();
        }
        catch (SQLException e)
        {
            // Ignored
        }
    }

    /**
     * Closes This Object's Connection Silently (No Exception Throwing)
     */
    private void closeConnectionSilently()
    {
        closeSilently(connection);
    }

}
