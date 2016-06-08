package timeclock;

import javax.swing.*;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Evan Black & Liz Stanton
 * Panel Containing Add, Edit, And Delete Buttons For Interfacing With Tables
 */
public class AddEditDeleteButtons extends JPanel
{

	private JButton btnAdd;
	private JButton btnEdit;
	private JButton btnDelete;

	/**
	 * Reference To The Table That Will Be Changed By These Buttons
	 */
	private TableFrame tableCaller;

	/**
	 * Default Constructor
	 * Adds Buttons, And Listener, But With NO REFERENCE TO A TABLE TO EDIT
	 */
	public AddEditDeleteButtons()
	{
		JPanel panel = new JPanel(); //Initializes a JPanel for use
	    panel.setLayout(new GridBagLayout()); //Sets layout grid bag

		//Sets Up GridBag Constraints
	    GridBagConstraints bag = new GridBagConstraints();
	    bag.fill = GridBagConstraints.NORTHEAST;
	    
		//Add button
		btnAdd = new JButton("Add");
		bag.gridx = 0; //Column 
		bag.gridy = 0; //Row 
		bag.gridwidth = 1;
		this.add(btnAdd);
		btnAdd.addActionListener(new ButtonListener()); // Adds Listener
		
		//Edit button
		btnEdit = new JButton("Edit");
		bag.gridx = 1; //Column 
		bag.gridy = 0; //Row 
		bag.gridwidth = 1;
		this.add(btnEdit);
		btnEdit.addActionListener(new ButtonListener());// Adds Listener

		//Delete button
		btnDelete = new JButton("Delete");
		bag.gridx = 8; //Column 
		bag.gridy = 0; //Row 
		bag.gridwidth = 1;
		this.add(btnDelete);
		btnDelete.addActionListener(new ButtonListener());// Adds Listener
		
	}

	/**
	 * Overloaded Constructor
	 * Sets Up Buttons, Listeners, And Attaches The Table To Edit
	 * @param tableCaller
	 * 		The Table To Be Edited By The Buttons
     */
	public AddEditDeleteButtons(TableFrame tableCaller)
	{
		// Calls Default Constructor To Set Up GUI Components
		this();

		// Stores Reference To Table To Edit
		this.tableCaller = tableCaller;
	}


	/**
	 * Calls tableCaller's openAddDialog Method,
	 * 						May Be Expanded To Do Other Things
	 */
	private void openAddDialog()
	{
		tableCaller.openAddDialog();
	}

	/**
	 * Calls tableCaller's openEditDialog Method,
	 * 						May Be Expanded To Do Other Things
	 */
	private void openEditDialog()
	{
		tableCaller.openEditDialog();
	}

	/**
	 * Calls tableCaller's deleteItem Method,
	 * 						May Be Expanded To Do Other Things
	 */
	private void deleteItem()
	{
		tableCaller.deleteItem();
	}


	/**
	 * Inner Class To Handle Button Presses
	 * Hands Off Control To Add/Edit/Delete Methods After Clicking
	 */
	private class ButtonListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			if (e.getSource() == btnAdd) // Add Button
			{
				openAddDialog();
			}
			else if (e.getSource() == btnEdit) // Edit Button
			{
				openEditDialog();
			}			
			else if (e.getSource() == btnDelete) // Delete Button
			{
				deleteItem();
			}	
		}		
	}

}
