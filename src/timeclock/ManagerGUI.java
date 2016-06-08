package timeclock;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.sql.SQLException;
import javax.imageio.ImageIO;
import javax.swing.*;


/**
 * @author Evan Black and Elizabeth Stanton
 * GUI for the Main Manager Page.
 */

public class ManagerGUI extends JFrame
{	
	//Height and width for buttons
	private static final int BTN_WIDTH = 150;
	private static final int BTN_HEIGHT = 150;
	
	//Label, button, and image for Schedule 
	private JLabel lblSchedule;
	private JButton btnSchedule;
	private BufferedImage scheduleImage;
	
	//Label, button, and image for Employees
	private JLabel lblEmployees;
	private JButton btnEmployees;
	private BufferedImage employeesImage;
	
	//Label, button, and image for Hour Log
	private JLabel lblHourLog;
	private JButton btnHourLog;
	private BufferedImage hourLogImage;
	
	//Label, button, and image for Report
	private JLabel lblReport;
	private JButton btnReport;
	private BufferedImage reportImage;


    /**
     * Provides Database IO
     */
	private DatabaseInteraction dbo;
	
	//Constructor
	public ManagerGUI() throws SQLException
    {
		/* Frame Properties*/
		this.setTitle("Admin Manager"); //Titles the frame 
		this.setSize(330,380); //Sets size of frame
				
		/* Layout */
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout()); //Sets layout grid bag
	    GridBagConstraints bag = new GridBagConstraints();
	    bag.fill = GridBagConstraints.NORTHEAST;
	    bag.weightx = 1;
	    

        // Initializes Connection To Database
        //  Exceptions Thrown Handled By Main Class
        dbo = new DatabaseInteraction();

	    
		/* **************************************** *
		 *    Employee Schedule Button and Label    *
		 * **************************************** */
	    //Label
	    lblSchedule = new JLabel("Schedule");
	    bag.gridx = 0; //Column 
		bag.gridy = 0; //Row 
	    panel.add(lblSchedule, bag); //Adds to panel
	    
	    //Button
	    btnSchedule = new JButton();
		scheduleDesign(); //Calls method with the button design
		btnSchedule.addActionListener(new BtnListener());
		bag.gridx = 0; //Column 
		bag.gridy = 1; //Row 
		bag.gridwidth = 1;
		panel.add(btnSchedule, bag); //Adds to panel
		
		
		
		/* ******************************************* *
		 *    Employee Information Button and Label    *
		 * ******************************************* */
		//Label
		lblEmployees = new JLabel("Employee Information");
	    bag.gridx = 1; //Column 
		bag.gridy = 0; //Row 
	    panel.add(lblEmployees, bag); //Adds to panel
	    
	    //Button
		btnEmployees = new JButton();
		employeesDesign(); //Calls method with the button design
		btnEmployees.addActionListener(new BtnListener());
		bag.gridx = 1; //Column 
		bag.gridy = 1; //Row 
		bag.gridwidth = 1;		
		panel.add(btnEmployees, bag); //Adds to panel
		
		
		
		
		/* **************************************** *
		 *    Employee Hour Log Button and Label    *
		 * **************************************** */
		//Label
		lblHourLog = new JLabel("Hour Log");
	    bag.gridx = 0; //Column 
		bag.gridy = 3; //Row 
	    panel.add(lblHourLog, bag); //Adds to panel
	    
	    //Button
		btnHourLog = new JButton();
		hourLogDesign(); //Calls method with the button design
		btnHourLog.addActionListener(new BtnListener());
		bag.gridx = 0; //Column 
		bag.gridy = 4; //Row 
		bag.gridwidth = 1;
		panel.add(btnHourLog, bag); //Adds to panel
		
		
		
		/* ***************************** *
		 *    Report Button and Label    *
		 * ***************************** */
		//Label
		lblReport = new JLabel("Report");
	    bag.gridx = 1; //Column 
		bag.gridy = 3; //Row 
	    panel.add(lblReport, bag); //Adds to panel
	    
	    //Button
		btnReport = new JButton();
		reportDesign(); //Calls method with the button design
		btnReport.addActionListener(new BtnListener());
		bag.gridx = 1; //Column 
		bag.gridy = 4; //Row 
		bag.gridwidth = 1;
		panel.add(btnReport, bag); //Adds to panel
		
		
		/* Frame Properties*/
		this.setTitle("Admin Manager"); //Titles the frame 
		this.setSize(330,380); //Sets size of frame
		this.add(panel); //Adds panel to frame
		this.setResizable(false); //Set frame so it can't be resized
		this.setLocationRelativeTo(null); //Sets frame to open in the middle of the screen
		this.setVisible(true); //Make the window visible
		this.setDefaultCloseOperation(EXIT_ON_CLOSE); //Sets exit on close
	}
	
	/* **********************
	 * scheduleDesign
	 * ** Design aspects for schedule button
	 * ** Returns void
	 * **********************/
	private void scheduleDesign()
	{
		try
		{
			scheduleImage = ImageIO.read(getClass().getResource("clock.png")); //Gets image
			
			int w = scheduleImage.getWidth(null); //Gets width of image
			int h = scheduleImage.getHeight(null); //Gets height of image
			BufferedImage buff = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
			
			Graphics g = buff.getGraphics();
			g.drawImage(scheduleImage, 0, 0, null);
		}catch(Exception Ex){}
		
		btnSchedule.setPreferredSize(new Dimension(BTN_WIDTH, BTN_HEIGHT)); //Sets height and width of button
		btnSchedule.setIcon(new ImageIcon(scheduleImage)); //Sets button to show image
		btnSchedule.setBorder(BorderFactory.createEtchedBorder()); //Changes border of button
	}
	
	/* **********************
	 * employeesDesign
	 * ** Design aspects for employees button
	 * ** Returns void
	 * **********************/
	private void employeesDesign()
	{
		try
		{
			employeesImage = ImageIO.read(getClass().getResource("people.png")); //Gets image
			
			int w = employeesImage.getWidth(null); //Gets width of image
			int h = employeesImage.getHeight(null); //Gets height of image
			BufferedImage buff = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
			
			Graphics g = buff.getGraphics();
			g.drawImage(employeesImage, 0, 0, null);
		}catch(Exception Ex){}
		
		btnEmployees.setPreferredSize(new Dimension(BTN_WIDTH, BTN_HEIGHT)); //Sets height and width of button
		btnEmployees.setIcon(new ImageIcon(employeesImage)); //Sets button to show image
		btnEmployees.setBorder(BorderFactory.createEtchedBorder()); //Changes border of button
		
	}
	
	/* **********************
	 * hourLogDesign
	 * ** Design aspects for hour log button
	 * ** Returns void
	 * **********************/
	private void hourLogDesign()
	{
		try
		{
			hourLogImage = ImageIO.read(getClass().getResource("clockspin.png")); //Gets image
			
			int w = hourLogImage.getWidth(null); //Gets width of image
			int h = hourLogImage.getHeight(null); //Gets height of image
			BufferedImage buff = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
			
			Graphics g = buff.getGraphics();
			g.drawImage(hourLogImage, 0, 0, null);
		}catch(Exception Ex){}
		
		btnHourLog.setPreferredSize(new Dimension(BTN_WIDTH, BTN_HEIGHT)); //Sets height and width of button
		btnHourLog.setIcon(new ImageIcon(hourLogImage)); //Sets button to show image
		btnHourLog.setBorder(BorderFactory.createEtchedBorder()); //Changes border of button

		
	}
	
	/* **********************
	 * reportDesign
	 * ** Design aspects for report button
	 * ** Returns void
	 * **********************/
	private void reportDesign()
	{
		try
		{
			reportImage = ImageIO.read(getClass().getResource("pen.png")); //Gets image
			
			int w = reportImage.getWidth(null); //Gets width of image
			int h = reportImage.getHeight(null); //Gets height of image
			BufferedImage buff = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
			
			Graphics g = buff.getGraphics();
			g.drawImage(reportImage, 0, 0, null);
		}catch(Exception Ex){}
		
		btnReport.setPreferredSize(new Dimension(BTN_WIDTH, BTN_HEIGHT)); //Sets height and width of button
		btnReport.setIcon(new ImageIcon(reportImage)); //Sets button to show image
		btnReport.setBorder(BorderFactory.createEtchedBorder()); //Changes border of button
	}


	/**
	 * Listener Class To Handle Button Presses
	 */
	private class BtnListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent actionEvent)
		{
			if (actionEvent.getSource() == btnEmployees)
			{
				new EmployeeInformationGUI(dbo);
			}
			else if (actionEvent.getSource() == btnHourLog)
			{
				new EmployeeHourLogGUI(dbo);
			}
			else if (actionEvent.getSource() == btnSchedule)
			{
				JOptionPane.showMessageDialog(null, "Coming Soon");
			}
			else if (actionEvent.getSource() == btnReport)
			{
				JOptionPane.showMessageDialog(null, "Coming Soon");
			}
		}
	}
}
