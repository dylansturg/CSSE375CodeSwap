package scheduleGenerator;

import java.io.Serializable;
import java.util.Calendar;

/*
 * Swap 1 - Team 03 - BONUS FEATURE
 * 
 * Adding new class to support calendars
 */

public class Event implements Serializable {
	private Calendar startDate;
	private Calendar endDate;
	
	public Event(Calendar start, Calendar end){
		this.startDate = start;
		this.endDate = end;
	}
	
	public boolean checkConflict(Calendar date){
		return date.after(this.startDate) && date.before(this.endDate);
	}
}
