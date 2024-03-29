package timeclock;




// For Reading in Server URL, Account, Database Name, etc
import java.io.FileInputStream;
import java.io.IOException;

import javax.sql.DataSource;
import javax.swing.*;


import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Vector;


/**
 * @author Evan and Liz
 * Class For Interfacing With The Database
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
     * Default Constructor <br />
     * Sets Up The Data Source, And Opens Our Connection
     */
    DatabaseInteraction()
    {
        dataSource = getMySQLDataSource();
        establishConnectionSilent();
    }


    /**
     * <b>Method:</b> getMySQLDataSource() <br />
     * <b>Purpose:</b>  Configures Server Info Needed Before Making A Connection
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
        catch (IOException e)
        {
            e.printStackTrace();
        }

        // Returns Completed Datasource
        return mysqlDS;
    }

    /**
     * <b>Method:</b> establishConnection() <br />
     * <b>Purpose:</b>  Attempts To Connect To The Server, And Set The connection Data Member
     * @throws SQLException
     *          Connection To The Server Fails
     */
    private void establishConnection() throws SQLException
    {
        connection = dataSource.getConnection();
    }

    /**
     * <b>Method:</b> establishConnectionSilent() <br />
     * <b>Purpose:</b>  Attempts To Connect To The Server, And Set The connection Data Member
     *                      Throws No Exceptions, Even IF Connection Fails
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
     * <b>Method:</b> isConnectionAlive() <br />
     * <b>Purpose:</b>  Tests If The Connection Is Closed, Or Not Set Up
     * @return
     *      True - The Connection To The Database Is Up <br />
     *      False - Connection To The Database Was Not Up
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
     * <b>Method:</b> assureConnection() <br />
     * <b>Purpose:</b>  Makes Sure The Connection To The Database Is Up,
     *  And Established One If It Is Not
     * @return
     *      True - The Connection To The Database Is Up <br />
     *      False - Connection To The Database Was Not Up, ANd Could Not Be Established.
     */
    public boolean assureConnection()
    {

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
     * <b>Method:</b> getAllEmployees() <br />
     * <b>Purpose:</b>  Retrieves A Set Of All Employees From The Server
     * @return
     *      A ResultSet of ALl Employees
     * @throws SQLException
     *     Connection To The Server Failed, Or The Database Was Not Found
     */
    ResultSet getAllEmployees() throws SQLException
    {
        Statement statement;
        statement = connection.createStatement();

        return statement.executeQuery("SELECT * FROM timeclock.employee");
    }


    /**
     * <b>Method:</b> getAllHours() <br />
     * <b>Purpose:</b>  Retrieves A Set Of All Hours From The Server
     * @return
     *      A ResultSet of ALl Hours
     * @throws SQLException
     *     Connection To The Server Failed, Or The Database Was Not Found
     */
    ResultSet getAllHours() throws SQLException
    {
        Statement statement;
        statement = connection.createStatement();
        return statement.executeQuery("SELECT * FROM timeclock.employeeHourLog");
    }

    /**
     * <b>Method:</b> retrieveAllHours() <br />
     * <b>Purpose:</b>  Retrieves ArrayList Of All Hours From The Server
     * @return
     *      ArrayList Of Hours, Each One A Shift In The Database
     */
    public ArrayList<Hour> retrieveAllHours()
    {
        if (!assureConnection())
            return null;

        ArrayList<Hour> toReturn = new ArrayList<>();
        ResultSet allHours = null;

        try
        {
            allHours = getAllHours();
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
     * <b>Method:</b> retrieveAllEmployees() <br />
     * <b>Purpose:</b>  Retrieves ArrayList Of All Employees From The Server
     * @return
     *      ArrayList Of Employees, Each One An Employee In The Database
     */
    public ArrayList<Employee> retrieveAllEmployees()
    {
        if (!assureConnection())
            return null;

        ArrayList<Employee> toReturn = new ArrayList<Employee>();
        ResultSet allEmp = null;
        try
        {
            allEmp = getAllEmployees();

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
     * <b>Method:</b> retrieveAllDepartments() <br />
     * <b>Purpose:</b>  Retrieves Vector Of All Departments From The Server (For ComboBoxes)
     * @return
     *      Vector Of Departments, Each An Entry In The Department Table
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
     * <b>Method:</b> retrieveAllJobs() <br />
     * <b>Purpose:</b>  Retrieves Vector Of All JobCodes From The Server (For ComboBoxes)
     * @return
     *      Vector Of JobCodes, Each An Entry In The jobCode Table
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
     * <b>Method:</b> retrieveJobsInDepartment() <br />
     * <b>Purpose:</b>  Retrieves Vector Of JobCodes From The Server Belonging To A Specific Department (For ComboBoxes)
     * @param pDepartmentID
     *      The ID Of The Department With Job Codes
     * @return
     *      Vector Of JobCodes, Each An Entry In The jobCode Table Tied To A Specific Department
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
     * <b>Method:</b> retrieveJobsInDepartment() <br />
     * <b>Purpose:</b>  Retrieves Vector Of JobCodes From The Server Belonging To A Specific Department (For ComboBoxes)
     * @param pDepartment
     *      A Department Object With An ID Set
     * @return
     *      Vector Of JobCodes, Each An Entry In The jobCode Table Tied To A Specific Department
     */
    public Vector<JobCode> retrieveJobsInDepartment(Department pDepartment)
    {
        return this.retrieveJobsInDepartment(pDepartment.getDepartmentID());
    }

    /**
     * <b>Method:</b> getEmployeeByID() <br />
     * <b>Purpose:</b>  Retrieves A Specific Employee (Or None If pEmployeeID Does Not Match Anything)
     * @param pEmployeeID
     *      The ID Of The Employee To Look For
     * @return
     *  A Result Set Of One Or Zero Employees
     * @throws SQLException
     *  Connection To The Server Failed, Or The Database Was Not Found
     */
    ResultSet getEmployeeByID(int pEmployeeID) throws SQLException
    {

        String sql =
                "SELECT * FROM timeclock.employee " +
                        "WHERE EmployeeID = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, pEmployeeID);
        return preparedStatement.executeQuery();
    }


    /**
     * <b>Method:</b> getAllDepartments() <br />
     * <b>Purpose:</b>  Retrieves All Entries In Department Table
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
     * <b>Method:</b> getAllJobs() <br />
     * <b>Purpose:</b>  Retrieves All Entries In jobCode Table
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
     * <b>Method:</b> getJobsByDepartment() <br />
     * <b>Purpose:</b>  Retrieves All Jobs In A Specific Department
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
     * <b>Method:</b> getDepartmentByID() <br />
     * <b>Purpose:</b>  Retrieves A Specific Department (Or None If pDepartmentID Does Not Match Anything)
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
     * <b>Method:</b> clockEmployeeIn() <br />
     * <b>Purpose:</b>  Attempts To Begin A Shift For An Employee
     *  Not Fully Implemented!
     * @param pEmployeeID
     *  The ID OF The Employee To Start The Shift Of
     * @return
     *  True - The Shift Was Successfully Started <br />
     *  False  - The Shift Could Not Be Started
     */
    boolean clockEmployeeIn(int pEmployeeID)
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
     * <b>Method:</b> createNewEmployee() <br />
     * <b>Purpose:</b>  Attempts To Create A New Employee
     * @param pNewEmployeeID
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
     *
     * @return
     *  True - The Employee Was Successfully Created <br />
     *  False - The Employee Could Not Be Created
     *
     *  @throws SQLException
     *      Database Failure
     */
    boolean createNewEmployee (int pNewEmployeeID, String pFirstName, String pLastName, String pEmail, int pDepartmentID, int pJobCode) throws SQLException
    {
        if (!isExistingEmployeeByID(pNewEmployeeID))
        {
            insertNewEmployee(pNewEmployeeID, pFirstName, pLastName, pEmail, pDepartmentID, pJobCode);
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * <b>Method:</b> createNewEmployee() <br />
     * <b>Purpose:</b>  Attempts To Create A New Employee
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
     * <b>Method:</b> removeEmployee() <br />
     * <b>Purpose:</b>  Attempts To Remove An Employee
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
     * <b>Method:</b> removeEmployee() <br />
     * <b>Purpose:</b>  Attempts To Remove An Employee
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
     * <b>Method:</b> createNewShift() <br />
     * <b>Purpose:</b>  Attempts Create A New Shift
     * @param pEmployeeID
     *  The ID OF The Employee That Worked The Shift
     * @param pTimeIn
     * SQL Time Value For When The Clock In Occurred
     * @param pDateIn
     * Sql Date Value For When The Clock In Occurred
     * @param pTimeOut
     * SQL Time Value For When The Clock Out Occurred
     * @param pDateOut
     * Sql Date Value For When The Clock Out Occurred
     *
     *@return
     *  True - The Shift Was Successfully Created <br />
     *  False - The Shift Could Not Be Created
     *
     * @throws SQLException
     *  Database Failure
     */
    boolean createNewShift(int pEmployeeID, Time pTimeIn, Date pDateIn, Time pTimeOut, Date pDateOut) throws SQLException
    {
        insertNewHour(pEmployeeID,pTimeIn,pDateIn,pTimeOut,pDateOut);
        return true;
    }

    /**
     * <b>Method:</b> createNewShift() <br />
     * <b>Purpose:</b>  Attempts Create A New Shift
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
     * <b>Method:</b> isEmployeeClockedIn() <br />
     * <b>Purpose:</b>  Tests If The Employee Has An Active Shift
     * @param pEmployeeID
     *  The ID Of The Employee To Check For An Active Shift
     * @return
     *  True - The Employee Is In <br />
     *  False - The Employee Is Not In
     *
     * @throws SQLException
     *  Database Failure
     */
    private boolean isEmployeeClockedIn(int pEmployeeID) throws SQLException
    {
        ResultSet resultSet = null;
        try
        {
            resultSet = getEmployeeInShiftsByID(pEmployeeID);
            return !resultSet.next();
        }
        finally
        {
            closeSilently(resultSet);
        }

    }

    /**
     * <b>Method:</b> getEmployeeInShiftsByID() <br />
     * <b>Purpose:</b>  Get All Shifts For A Given Employee
     * @param pEmployeeID
     *  The ID Of An Employee To Get All Shifts Of
     * @return
     *  A ResultSet Of All Shifts Fur An Employee
     *
     * @throws SQLException
     *  Database Failure
     */
    ResultSet getEmployeeInShiftsByID(int pEmployeeID) throws SQLException
    {
        String sql =
                "SELECT * FROM timeclock.employeeHourLog " +
                        "WHERE EmployeeID=? AND ISNULL(TimeOut) ";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, pEmployeeID);
        return preparedStatement.executeQuery();

    }

    /**
     * <b>Method:</b> insertShift() <br />
     * <b>Purpose:</b>  Inserts A Default Shift For A Given Employee
     * @param pEmployeeID
     *  The ID Of The Employee To Add The Shift For
     *
     * @throws SQLException
     *  Database Failure
     */
    private void insertShift(int pEmployeeID) throws SQLException
    {
        String sql =
                "INSERT INTO timeclock.employeeHourLog (EmployeeID) " +
                        "VALUES (  ?  )";


        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, pEmployeeID);
        preparedStatement.executeUpdate();

        preparedStatement.closeOnCompletion();

    }

    /**
     * <b>Method:</b> endShift() <br />
     * <b>Purpose:</b>  Ends An Open Shift For A Given Employee
     * @param pEmployeeID
     *  The ID Of The Employee To Close The Shift For
     * @return
     *  True - The Employee's Shift Was Closed <br />
     *  False - The Employee's Shift Could Not Be Closed
     * @throws SQLException
     *  Database Failure
     */
    boolean endShift(int pEmployeeID) throws SQLException
    {
        if (isEmployeeClockedIn(pEmployeeID))
        {
            String sql =
                    "UPDATE timeclock.employeeHourLog " +
                            "SET TimeOut=NOW()"+
                            "WHERE EmployeeID=? AND ISNULL(TimeOut)";


            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, pEmployeeID);
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
     * <b>Method:</b> deleteShiftByID() <br />
     * <b>Purpose:</b>  Removes A Shift From The Database
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
     * <b>Method:</b> deleteEmployeeByID() <br />
     * <b>Purpose:</b>  Removes An Employee From The Database
     * @param pEmployeeID
     *  The ID OF The Employee
     * @throws SQLException
     *  Database Failure
     */
    private void deleteEmployeeByID(int pEmployeeID) throws SQLException
    {
        String sql = "DELETE FROM employee " +
                "WHERE EmployeeID=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1,pEmployeeID);
        preparedStatement.executeUpdate();

        preparedStatement.closeOnCompletion();
    }

    /**
     * <b>Method:</b> updateEmployeeByID() <br />
     * <b>Purpose:</b>  Updated Info Associated With A Given Employee
     * @param pCurrentEmployeeID
     *  The Current Unique ID Of The Employee
     * @param pNewEmployeeID
     * 	The New Unique ID Of The Employee
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
     *
     * @throws SQLException
     *  Database Failure
     */
    public void updateEmployeeByID(int pCurrentEmployeeID, int pNewEmployeeID, String pFirstName, String pLastName, String pEmail, int pDepartmentID, int pJobCode) throws SQLException
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

        preparedStatement.setInt(1, pNewEmployeeID);
        preparedStatement.setInt(2, pDepartmentID);
        preparedStatement.setInt(3, pJobCode);
        preparedStatement.setString(4, pFirstName);
        preparedStatement.setString(5, pLastName);
        preparedStatement.setString(6, pEmail);
        preparedStatement.setInt(7, pCurrentEmployeeID);

        preparedStatement.executeUpdate();
        closeSilently(preparedStatement);
    }

    /**
     * <b>Method:</b> updateShiftByID() <br />
     * <b>Purpose:</b>  Updated Info Associated With A Given Shift
     * @param pRecordID
     *  The Current Unique ID Of The Shift
     * @param pEmployeeID
     *  The New Unique ID Of The Employee
     * @param pTimeIn
     *  The Timestamp Of When The Shift Starts
     * @param pTimeOut
     *  The Timestamp Of When The Shift Ends
     * @throws SQLException
     *   Database Failure
     */
    public void updateShiftByID(int pRecordID, int pEmployeeID, Timestamp pTimeIn, Timestamp pTimeOut) throws SQLException
    {
        String sql =
                "UPDATE employeeHourLog " +
                        "SET EmployeeID=?, " +
                        "TimeIn=?, " +
                        "TimeOut=? " +
                        "WHERE RecordID=? ";


        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        // Values
        preparedStatement.setInt(1,pEmployeeID);
        preparedStatement.setTimestamp(2,pTimeIn);
        preparedStatement.setTimestamp(3,pTimeOut);

        // Key
        preparedStatement.setInt(4,pRecordID);


        preparedStatement.executeUpdate();
        closeSilently(preparedStatement);
    }

    /**
     * Method: isExistingEmployeeByID (int pEmployeeID) <br />
     * Purpose: Method Checks If A Passed In ID Is Already In The Database
     *
     * @param pEmployeeID
     *      The ID To Check For
     *
     * @return
     *      True - The ID Is Already In The Database <br />
     *      False - The ID Is Not In The Database <br />
     *
     * @throws SQLException
     *      If We Lose Connection To The Server
     */
    boolean isExistingEmployeeByID(int pEmployeeID) throws SQLException
    {
        ResultSet resultSet = getEmployeeByID(pEmployeeID);
        try
        {
            return resultSet.next();
        }
        finally
        {
            closeSilently(resultSet);
        }

    }

    /**
     * <b>Method:</b> insertNewEmployee() <br />
     * <b>Purpose:</b> Private Creation Method For Employee
     * @param pNewEmployeeID
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
     *
     *  @throws SQLException
     *      Database Failure
     */
    private void insertNewEmployee(int pNewEmployeeID, String pFirstName, String pLastName, String pEmail, int pDepartmentID, int pJobCode) throws SQLException
    {
        String sql =
                "INSERT INTO timeclock.employee (EmployeeID, FirstName, LastName, Email, DepartmentID, JobCode) " +
                        "VALUES (?,?,?,?,?,?)";


        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setInt(1, pNewEmployeeID);
        preparedStatement.setString(2, pFirstName);
        preparedStatement.setString(3, pLastName);
        preparedStatement.setString(4, pEmail);
        preparedStatement.setInt(5, pDepartmentID);
        preparedStatement.setInt(6, pJobCode);


        preparedStatement.executeUpdate();
        closeSilently(preparedStatement);
    }

    /**
     * <b>Method:</b> insertNewHour() <br />
     * <b>Purpose:</b> Private Creation Method For Shift
     *
     * @param pEmployeeID
     *  The ID OF The Employee That Worked The Shift
     * @param pTimeIn
     * SQL Time Value For When The Clock In Occurred
     * @param pDateIn
     * Sql Date Value For When The Clock In Occurred
     * @param pTimeOut
     * SQL Time Value For When The Clock Out Occurred
     * @param pDateOut
     * Sql Date Value For When The Clock Out Occurred
     *
     *  @throws SQLException
     *      Database Failure
     */
    private void insertNewHour(int pEmployeeID, Time pTimeIn, Date pDateIn, Time pTimeOut, Date pDateOut) throws SQLException
    {

        // Convert Values To Timestamp For Storage In Database
        Timestamp timeIn = new Timestamp(pDateIn.getTime() + pTimeIn.getTime());
        Timestamp timeOut = new Timestamp(pDateOut.getTime() + pTimeOut.getTime());

        String sql =
                "INSERT INTO timeclock.employeeHourLog (EmployeeID, TimeIn, TimeOut) " +
                        "VALUES (?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);


        preparedStatement.setInt(1, pEmployeeID);
        preparedStatement.setTimestamp(2,timeIn);
        preparedStatement.setTimestamp(3,timeOut);

        preparedStatement.executeUpdate();
        closeSilently(preparedStatement);
    }

    /**
     * <b>Method:</b> closeSilently() <br />
     * <b>Purpose:</b> Closes The Passed In Item Silently (No Exception Throwing)
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
     * <b>Method:</b> closeSilently() <br />
     * <b>Purpose:</b> Closes The Passed In Item Silently (No Exception Throwing)
     * @param pPreparedStatement
     *  A PreparedStatement To Close
     */
    private void closeSilently(PreparedStatement pPreparedStatement)
    {
        try
        {
            pPreparedStatement.close();
        }
        catch (SQLException e)
        {
            // Ignored
        }
    }

    /**
     * <b>Method:</b> closeSilently() <br />
     * <b>Purpose:</b> Closes The Passed In Item Silently (No Exception Throwing)
     * @param pStatement
     *  A Statement To Close
     */
    private void closeSilently(Statement pStatement)
    {
        try
        {
            pStatement.close();
        }
        catch (SQLException e)
        {
            // Ignored
        }
    }

    /**
     * <b>Method:</b> closeSilently() <br />
     * <b>Purpose:</b> Closes The Passed In Item Silently (No Exception Throwing)
     * @param pConnection
     *  A Connection To Close
     */
    private void closeSilently(Connection pConnection)
    {
        try
        {
            pConnection.close();
        }
        catch (SQLException e)
        {
            // Ignored
        }
    }

    /**
     * <b>Method:</b> closeConnectionSilently() <br />
     * <b>Purpose:</b> Closes This Object's Connection Silently (No Exception Throwing)s
     */
    private void closeConnectionSilently()
    {
        closeSilently(connection);
    }

}
