package scheduleGenerator;

import java.io.Serializable;
import java.text.DateFormatSymbols;
import java.util.ArrayList;

/**
 * Day is used to store jobs for a given day.
 *
 * @author schneimd.
 *         Created Oct 15, 2012.
 */
public class Day implements Serializable{
	//SWAP 1, Team 10
	//SMELL: Data Class - this class consists entirely of accessors and mutators.  
	//It would be advantageous to make it so this object kept track of requirements about what is allowed on days 
	//and more conveniently could go from the id version of days to the named version
	
	// SWAP 2, Team 1 
	// REFACTORING FOR ENHANCEMENT FROM BAD SMELL #6
	// Using the Move Method to move the numForName() method into this class give the Day class more functionality.
	// Enhancement: Adds functionality, and is no longer simply a data class.
	// Refactor successful. This could be rafctored further by implementing what was suggested in swap 1,
	//   and moved the functionality of keeping track of what is allowed on days to this class.
	
	private String dayOfWeek;
	private ArrayList<String> jobs = new ArrayList<String>();
	
	/**
	 * Construct a day with a name and jobs.
	 * 
	 * @param name 
	 *
	 * @param jobs
	 */
	public Day(String name, ArrayList<Object> jobs)
	{
		this.dayOfWeek = name;
		for(Object i:jobs)
		{
			this.jobs.add((String)i);
		}
	}
	
	/**
	 * Add one jobName.
	 *
	 * @param jobName
	 */
	public void addJob(String jobName) {
		this.jobs.add(jobName);
	}
	
	/**
	 * Set jobs to new jobs.
	 *
	 * @param jobNames
	 */
	public void setJobs(ArrayList<String> jobNames) {
		this.jobs = jobNames;
	}
	
	/**
	 * return current jobs.
	 *
	 * @return jobs
	 */
	public ArrayList<String> getJobs() {
		return this.jobs;
	}
	
	/**
	 * Gives the name of this day.
	 *
	 * @return day of week
	 */
	public String getNameOfDay() {
		return this.dayOfWeek;
	}
	
	// SWAP 2, Team 1 
	// REFACTORING FOR ENHANCEMENT FROM BAD SMELL #1
	// Used the Move Method to move the numForName() method into the day class. 
	protected int numForName() {
		//SWAP 1, Team 10
		//SMELL: Switch-Statement - this is essentially just a long case statement for each of the different days.  
		//By using some built in java functionality with days, you could do this automatically.
		
		// SWAP 2, Team 1 
		// REFACTORING FOR ENHANCEMENT FROM BAD SMELL #2
		// Used the Extract Method to remove the switch statement and replace it with built in java functionality.
		// Enhancement: Removed duplicated code, improved functionality.
		//   Can lead to more refactoring on the rest of the Day class to use Java's built-in date format library.
		// Refactoring was very successful. Switch statement completely removed and replaced with much cleaner code.
		DateFormatSymbols objDay = new DateFormatSymbols();  
		String DayNames[] = objDay.getWeekdays();  
        for (int dayNum = 0; dayNum < DayNames.length; dayNum++)   
        {  
        	if(dayOfWeek.equalsIgnoreCase(DayNames[dayNum]))  
            {  
            	return dayNum;  
            }  
        }  
        return 0; 
	}
}
