package scheduleGenerator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Random;
import java.util.TreeMap;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Used to store predicted days and generate new days.
 * 
 * @author schneimd. Created Oct 18, 2012.
 */
public class Schedule extends Thread implements Serializable {

	private ArrayList<Worker> workers;
	private ArrayList<Day> days;
	private TreeMap<String, TreeMap<String, Worker>> schedule;
	private GregorianCalendar cal;
	private HashMap<Integer, ArrayList<Worker>> workerIndices;
	private boolean workerForEveryJob = true;

	/**
	 * Used to construct an initial schedule, used if one does not exist.
	 * 
	 * @param daySlots
	 * @param wrks
	 */
	public Schedule(ArrayList<Day> daySlots, ArrayList<Worker> wrks) {
		this.workers = wrks;
		this.days = daySlots;
		this.workerIndices = new HashMap<Integer, ArrayList<Worker>>();
		for (int i = 1; i <= 7; i++) {
			this.workerIndices.put(i, new ArrayList<Worker>());
		}
		this.generateIndices();

		// Key is year/month/day format and item is a hashmap with key nameOfJob
		// and item Worker
		this.schedule = new TreeMap<String, TreeMap<String, Worker>>();

		this.cal = new GregorianCalendar();

		this.calculateNextMonth();
	}

	@Override
	public void run() {
		this.calculateNextMonth();
	}

	/**
	 * returns workers in schedule.
	 * 
	 * @return workers
	 */
	public ArrayList<Worker> getWorkers() {
		return this.workers;
	}

	private void generateIndices() {
		for (int i = 0; i < this.workers.size(); i++) {
			for (Day day : this.workers.get(i).getDays()) {
				int numDay = this.numForName(day.getNameOfDay());
				this.workerIndices.get(numDay).add(this.workers.get(i));
			}
		}
	}

	/**
	 * Calculates another month of schedule based on workers availability.
	 * 
	 */
	private synchronized void calculateNextMonth() {

		int initialSize = this.schedule.size();

		if (this.schedule.size() > 0) {
			handleAlreadyGeneratedSchedule();
		}

		// Used to see if month changes
		int currentMonth = this.cal.get(Calendar.MONTH);

		int daysInMonth = 0;
		ArrayList<Integer> numOfJobs = new ArrayList<Integer>();

		// While still in the current month generate a schedule for each day
		while (currentMonth == this.cal.get(Calendar.MONTH)) {
			daysInMonth = scheduleWeek(daysInMonth, numOfJobs);
			this.cal.add(Calendar.DATE, 1);
		}
		HTMLGenerator.makeTable(daysInMonth, numOfJobs);

		calculateAdditionalMonth(initialSize);

		Main.dumpConfigFile();
	}

	// Swap 1 - Team 03 - Quality Change

	private int scheduleWeek(int daysInMonth, ArrayList<Integer> numOfJobs) {
		for (Day day : this.days) {

			if (sameWeekDayAsCalendar(day)) {

				daysInMonth++;
				assignAllJobsForDay(numOfJobs, day);
				break; // Breaks so it doesn't check the other days
			}
		}
		return daysInMonth;
	}

	// Swap 1 - Team 03 - Quality Change

	private void calculateAdditionalMonth(int initialSize) {
		// Calls itself if there aren't many days generated
		// For instance if the date it was created is the last day of the
		// month it would only makes one day of schedule.
		if (this.schedule.size() - initialSize < 2 && !this.workerForEveryJob) {
			this.calculateNextMonth();
		}
	}

	// Swap 1 - Team 03 - Quality Change

	private void assignAllJobsForDay(ArrayList<Integer> numOfJobs, Day day) {
		TreeMap<String, Worker> jobsWithWorker = new TreeMap<String, Worker>();
		ArrayList<String> workersWorking = new ArrayList<String>();

		ArrayList<String> jobsInOrder = day.getJobs();

		// Used for html later

		numOfJobs.add(jobsInOrder.size());

		//

		for (String job : jobsInOrder) {

			ArrayList<Worker> workersForJob = new ArrayList<Worker>();

			getAvailableWorkers(day, workersWorking, job, workersForJob);

			if (workersForJob.size() > 0) {
				assignWorkerToJob(jobsWithWorker, workersWorking, job,
						workersForJob);
			} else {
				displayNoWorkerError(day, jobsWithWorker, job);
				break;
			}

		}
		String date = getFormattedDate();
		this.schedule.put(date, jobsWithWorker);
	}

	// Swap 1 - Team 03 - Quality Change

	private boolean sameWeekDayAsCalendar(Day day) {
		return this.cal.get(Calendar.DAY_OF_WEEK) == this.numForName(day
				.getNameOfDay());
	}

	// Swap 1 - Team 03 - Quality Change

	private String getFormattedDate() {
		return this.cal.get(Calendar.YEAR) + "/"
				+ String.format("%02d", (this.cal.get(Calendar.MONTH) + 1))
				+ "/"
				+ String.format("%02d", this.cal.get(Calendar.DAY_OF_MONTH));
	}

	// Swap 1 - Team 03 - Quality Change

	private void displayNoWorkerError(Day day,
			TreeMap<String, Worker> jobsWithWorker, String job) {
		jobsWithWorker.put(job, new Worker("Empty", new ArrayList<Day>()));
		JOptionPane.showMessageDialog(
				new JFrame(),
				"No workers are able to work as a(n) " + job + " on "
						+ day.getNameOfDay());
		this.workerForEveryJob = false;
	}

	// Swap 1 - Team 03 - Quality Change

	private void assignWorkerToJob(TreeMap<String, Worker> jobsWithWorker,
			ArrayList<String> workersWorking, String job,
			ArrayList<Worker> workersForJob) {
		Worker workerForJob = workersForJob.get(new Random()
				.nextInt(workersForJob.size()));
		for (Worker w : workersForJob) {
			if (w.numWorkedForJob(job) < workerForJob.numWorkedForJob(job)) {
				workerForJob = w;
			}
		}
		jobsWithWorker.put(job, workerForJob);
		workersWorking.add(workerForJob.getName());
		workerForJob.addWorkedJob(job);
	}

	// Swap 1 - Team 03 - Quality Change

	private void getAvailableWorkers(Day day, ArrayList<String> workersWorking,
			String job, ArrayList<Worker> workersForJob) {
		// Swap 1 - team 03 - BONUS FEATURE
		// refactoring to remove code duplication in getting day's index in the week
		for (Worker worker : this.workerIndices.get(day.getIntOfDay()+1)) {
			
			Day workerDay = worker.getDayWithName(day.getNameOfDay());
			
			if (checkWorkerAvailability(workersWorking, job, worker, workerDay)) {
				workersForJob.add(worker);

			}
		}
	}

	private boolean checkWorkerAvailability(ArrayList<String> workersWorking,
			String job, Worker worker, Day workerDay) {
		this.cal.set(Calendar.HOUR_OF_DAY, 12);
		return workerDay.getJobs().contains(job)
				&& !workersWorking.contains(worker.getName())
				&& !worker.checkScheduleConflict(this.cal);
	}

	// SWAP 1 - Team 03 - Quality Change

	// Extracting this method allows developers to easily change the internal
	// format used for the schedule. The internal format is strictly of the form
	// YY/MM/DD which may make some internationalizzation more challenging.
	// Furthermore, the change allows developers to customize the options
	// regarding already existing calendars: for example, you could add an
	// option to regenerate the existing schedule.

	private synchronized void handleAlreadyGeneratedSchedule() {
		String lastDateMade = this.schedule.lastKey();
		String[] parts = lastDateMade.split("/");
		int year = Integer.parseInt(parts[0]);
		int month = Integer.parseInt(parts[1]) - 1;
		int day = Integer.parseInt(parts[2]);
		this.cal = new GregorianCalendar(year, month, day);
		int tempNum = this.cal.get(Calendar.MONTH);
		while (tempNum == this.cal.get(Calendar.MONTH)) {
			this.cal.add(Calendar.DATE, 1);
		}
	}

	private int numForName(String nameOfDay) {
		int dayNum = 0;
		if (nameOfDay.equals("Sunday")) {
			dayNum = 1;
		} else if (nameOfDay.equals("Monday")) {
			dayNum = 2;
		} else if (nameOfDay.equals("Tuesday")) {
			dayNum = 3;
		} else if (nameOfDay.equals("Wednesday")) {
			dayNum = 4;
		} else if (nameOfDay.equals("Thursday")) {
			dayNum = 5;
		} else if (nameOfDay.equals("Friday")) {
			dayNum = 6;
		} else if (nameOfDay.equals("Saturday")) {
			dayNum = 7;
		}
		return dayNum;
	}

	// /**
	// * Returns the month/day/year of next date with the name of day.
	// *
	// * @param nameOfDay
	// * @return string of year/month/day format
	// */
	// private String getNextDate(String nameOfDay) {
	// int dayNum = numForName(nameOfDay);
	// GregorianCalendar tempCal = (GregorianCalendar) this.cal.clone();
	//
	// tempCal.add(Calendar.DATE, 1);
	// while (tempCal.get(Calendar.DAY_OF_WEEK) != dayNum) {
	// tempCal.add(Calendar.DATE, 1);
	// }
	// return String.valueOf(tempCal.get(Calendar.YEAR)) + "/" +
	// String.valueOf(tempCal.get(Calendar.MONTH)) + "/"
	// + String.valueOf(tempCal.get(Calendar.DAY_OF_MONTH));
	// }

	/**
	 * Returns the schedule.
	 * 
	 * @return HashMap schedule
	 */
	public TreeMap<String, TreeMap<String, Worker>> getSchedule() {
		return this.schedule;
	}

}
