package scheduleGenerator;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Day is used to store jobs for a given day.
 * 
 * @author schneimd. Created Oct 15, 2012.
 */
public class Day implements Serializable {

	private String dayOfWeek;
	private ArrayList<String> jobs = new ArrayList<String>();

	/**
	 * Construct a day with a name and jobs.
	 * 
	 * @param name
	 * 
	 * @param jobs
	 */
	public Day(String name, ArrayList<Object> jobs) {
		this.dayOfWeek = name;
		for (Object i : jobs) {
			this.jobs.add((String) i);
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

	
	//Swap 1 - Team 03 - QUALITY CHANGE
	// Removes checks to map day of the week strings to ints
	
	public int getIntOfDay() {
		String[] daysList = new String[] { "sunday", "monday", "tuesday",
				"wednesday", "thursday", "friday", "saturday" };
		for (int i = 0; i < daysList.length; i++) {
			if (daysList[i].equals(this.dayOfWeek.toLowerCase())) {
				return i;
			}
		}
		return -1;

	}
}
