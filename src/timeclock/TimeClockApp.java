package timeclock;

import javax.swing.*;
import java.sql.SQLException;

/**
 * Class Is Entry Point For Manager
 */
public class TimeClockApp 
{
	/**
	 * main Entry Point, Merely Opens Our Manager GUI
     */
	public static void main(String[] args) 
	{

		try
		{
			new ManagerGUI();
		}
        catch (SQLException e)
        {
            JOptionPane.showMessageDialog(null, "Connection To The Database Failed", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
