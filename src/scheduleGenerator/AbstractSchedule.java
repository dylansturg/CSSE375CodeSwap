package scheduleGenerator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.TreeMap;

/*
 * BONUS FEATURE
 * This code is virtually the same as what was in Schedule.java, just extracted here.
 */

public abstract class AbstractSchedule extends Thread implements Serializable {
	
    private static final long serialVersionUID = 6595204764383243072L;
    protected ArrayList<Worker> workers;
	protected ArrayList<Day> days;
	protected TreeMap<String, TreeMap<String, Worker>> schedule;
	protected GregorianCalendar cal;
	protected boolean workerForEveryJob = true;

	/**
	 * Returns the schedule.
	 * 
	 * @return HashMap schedule
	 */
	public TreeMap<String, TreeMap<String, Worker>> getSchedule(){
		return schedule;
	}
	
	/**
	 * returns workers in schedule.
	 * 
	 * @return workers
	 */
	public ArrayList<Worker> getWorkers(){
		return workers;
	}
	
	//SWAP 1, Team 10
	//SMELL: Feature Envy - This is functionality that is reused regularly about days but is in what seems to be the wrong class.  
	//It should be moved to the day class
	
	// SWAP 2, Team 1 
	// REFACTORING FOR ENHANCEMENT FROM BAD SMELL #1
	// Used the Move Method to move the numForName() method into the day class.
	// Enhancement: Adds functionality to the Day class and improves the clarity of the purpose of each class.
	// Refactor was successful. Method calls were fixed to reflect the change.
	
	
	/**
	 * Calculates another month of schedule based on workers availability.
	 * 
	 */
	protected abstract void calculateNextMonth();
	
	public void run(){
		calculateNextMonth();
	}
}
