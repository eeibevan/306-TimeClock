package timeclock;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @author Evan Black & Liz Stanton
 * Class Containing Utility Functions
 */
public class TimeClockUtil
{

    /**
     * Integer Signifying No Selected Row In A Table,
     * Used For Checking If A Row Is Selected
     */
    public static final int NO_SELECTED_ROW = -1;


    /**
     * <b>Method:</b> isInt() <br />
     * <b>Purpose:</b> Determine If A Passed In String May Be Parsed Into An Int
     * @param possibleInt
     *  A String To Try To Parse
     * @return
     *  True - The String May Be Pared As An Int <br />
     *  False - The String May Not Be Parsed As An Int
     */
    public static boolean isInt(String possibleInt)
    {
        try
        {
            Integer.parseInt(possibleInt);
        }
        catch (Exception ex)
        {
            return false;
        }

        return true;
    }

    /**
     * <b>Method:</b> isValidDate() <br />
     * <b>Purpose:</b> Determine If A Passed In String May Be Parsed Into A SQL Date
     * @param possibleDate
     *  A String To Try To Parse
     * @return
     *  True - The String May Be Pared As A SQL Date <br />
     *  False - The String May Not Be Parsed A SQL Date
     */
    public static boolean isValidDate(String possibleDate)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);

        try
        {
            dateFormat.parse(possibleDate);
        }
        catch (ParseException e)
        {
            return false;
        }

        return true;
    }

    /**
     * <b>Method:</b> isValidTime() <br />
     * <b>Purpose:</b> Determine If A Passed In String May Be Parsed Into A SQL Time
     * @param possibleTime
     *  A String To Try To Parse
     * @return
     *  True - The String May Be Pared As A SQL Time <br />
     *  False - The String May Not Be Parsed A SQL Time
     */
    public static boolean isValidTime(String possibleTime)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        dateFormat.setLenient(false);

        try
        {
            dateFormat.parse(possibleTime);
        }
        catch (ParseException e)
        {
            return false;
        }

        return true;
    }

    /**
     * <b>Method:</b> parseDate() <br />
     * <b>Purpose:</b> Parses Passed In String Into SQL Date
     * @param date
     *  A String To Parse
     * @return
     *  date As A SQL Date
     */
    public static Date parseDate(String date)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);

        try
        {
            java.util.Date uDate = dateFormat.parse(date);
            return new java.sql.Date(uDate.getTime());
        }
        catch (ParseException e)
        {
            return new Date(0);
        }

    }

    /**
     * <b>Method:</b> parseTime() <br />
     * <b>Purpose:</b> Parses Passed In String Into SQL Time
     * @param time
     *  A String To Parse
     * @return
     *  date As A SQL Time
     */
    public static Time parseTime(String time)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        dateFormat.setLenient(false);

        Time ltime = null;
        try
        {
            java.util.Date uDate = dateFormat.parse(time);
            ltime = new java.sql.Time(uDate.getTime());
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

        return ltime;

    }

    /**
     * <b>Method:</b> parseTimestamp() <br />
     * <b>Purpose:</b> Parses Passed In String Into SQL TimeStamp
     * @param toStamp
     *  A String To Parse
     * @return
     *  date As A SQL TimeStamp
     */
    public static Timestamp parseTimestamp(String toStamp)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);

        Timestamp stamp = null;

        try
        {
            java.util.Date uDate = dateFormat.parse(toStamp);
            stamp = new java.sql.Timestamp(uDate.getTime());
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

        return stamp;
    }


}
