package timeclock;

import javax.swing.*;

/**
 * @author Evan Black & Liz Stanton
 * Abstract Class Defining Required Methods For All Secondary GUIs
 */
public abstract class TableFrame extends JFrame
{
    /**
     * Open A Dialogue To Add An Item
     */
    public abstract void openAddDialog();


    /**
     * Open A Dialogue To Edit An Item
     */
    public abstract void openEditDialog();


    /**
     * Deletes An Item
     */
    public abstract void deleteItem();

}
