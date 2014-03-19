package scheduleGenerator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.property.DateProperty;


/*
 * Swap 1 - Team 03 - BONUS FEATURE
 * 
 * Adding new class to support calendars
 */

public class WorkerCalendar implements Serializable {
	ArrayList<Event> scheduledDays = new ArrayList<Event>();
	
	public WorkerCalendar(File calendarFile){
		if (calendarFile == null)
			return;
		
		FileInputStream fin;
		try {
			fin = new FileInputStream(calendarFile);
			
			CalendarBuilder builder = new CalendarBuilder();

			Calendar calendar = builder.build(fin);
						
			for (Iterator i = calendar.getComponents("VEVENT").iterator(); i.hasNext();){
				Component event = (Component)i.next();
				
				java.util.Calendar eventStart = convertDateToCalendar(((DateProperty)event.getProperty("DTSTART")).getDate());
				java.util.Calendar eventEnd = convertDateToCalendar(((DateProperty)event.getProperty("DTEND")).getDate());
				eventEnd.add(java.util.Calendar.DATE, 1);
				
				scheduledDays.add(new Event(eventStart, eventEnd));
				
			}
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	
	private java.util.Calendar convertDateToCalendar(Date date){
		java.util.Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.set(java.util.Calendar.HOUR_OF_DAY, 0);
		calendar.set(java.util.Calendar.MINUTE, 0);
		calendar.set(java.util.Calendar.SECOND, 0);
		calendar.set(java.util.Calendar.MILLISECOND, 0);
		return calendar;
	}
	
	public boolean checkScheduleConflict(java.util.Calendar date) {
		for (Event e : this.scheduledDays){
			if(e.checkConflict(date)){
				return true;
			}
		}
		
		return false;
	}
	
}
