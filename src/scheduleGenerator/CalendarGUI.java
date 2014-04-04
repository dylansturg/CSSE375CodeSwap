package scheduleGenerator;

import java.awt.Component;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/*
 * CODE SMELL - Large Class
 * This class seems to have a whole lot going on. It has to initialize all its components, and then also generate tables
 * as well as have all the event handlers. Some inheritance could split up the code some.
 */

/**
 * 
 * @author schneimd
 */
public class CalendarGUI extends javax.swing.JFrame {

	private AbstractSchedule schedule;
	private GregorianCalendar cal;
	private TreeMap<String, TreeMap<String, Worker>> scheduleMap;
	private String monthName;
	@SuppressWarnings("unused")
	private int earliestYear, earliestMonth, earliestDay;
	private int currentMonth;
	private int monthsAhead = 0;
	private int yearsAhead = 0;

	/**
	 * Creates new form Calendar
	 * 
	 * @param schd
	 */
	public CalendarGUI(AbstractSchedule schd) {
		this.schedule = schd;
		this.scheduleMap = this.schedule.getSchedule();
		String[] earliest = this.scheduleMap.firstKey().split("/");
		this.earliestYear = Integer.parseInt(earliest[0]);
		this.earliestMonth = Integer.parseInt(earliest[1]);
		this.earliestDay = Integer.parseInt(earliest[2]);
		this.cal = new GregorianCalendar();
		initComponents();
		this.fillTableForThisMonth();
	}

	// SWAP 1 TEAM 10
	// QUALITY CHANGES
	/*
	 * In this method, there was a large amount of duplication regarding the
	 * setting of the monthTitle and the monthName This was really space
	 * consuming, and really didn't add anything (in fact, it could cause
	 * problems, as there was more string literal to possibly accidentally type
	 * in, making it more "dangerous"). Furthermore, it reeked of the code
	 * duplication code smell. So, I made a method that would fetch the month
	 * name given an integer. So, now the method just needs to call that that
	 * method one time to get the month name, and that removes the need for the
	 * switch. In the method that got the month name, I just made use of array
	 * indexing and modulus to find the name in an array of the names.
	 * 
	 * As far as future features are concerned, this would allow for an
	 * arbitrary format of the months to be used in the future. Consider public
	 * school: The teachers only work from August to about May, as such, they
	 * have no need for a Calendar that goes from January to December. Thus,
	 * this could allow for more compact, or specialized calendar formats.
	 * Additionally, this allows for any place to get the month name based off
	 * an integer.
	 */
	private void setTitleMonth(int n, int year) {
		this.monthTitle.setText(CalendarGUI.getMonthforNum(n) + year);
		this.monthName = CalendarGUI.getMonthforNum(n) + year;
	}

	public static String getMonthforNum(int n) {
		String[] months = { "January", "February", "March", "April", "May",
				"June", "July", "August", "September", "October", "November",
				"December" };
		return months[(n - 1) % months.length];
	}

	/*
	 * CODE SMELL: Data Clumps This is true for all of the fill table methods,
	 * but it seems like the month, day, and year are all passed along together
	 * or altered together at the same time. These should probably be replaced
	 * by some structure like a date.
	 */
	/**
	 * Displays the calendar for the current month based on the computers month.
	 * 
	 */
	public void fillTableForThisMonth() {
		int currentYear = new GregorianCalendar().get(Calendar.YEAR);
		this.currentMonth = new GregorianCalendar().get(Calendar.MONTH) + 1;
		this.setTitleMonth(this.currentMonth, currentYear);
		this.monthsAhead = 0;
		this.yearsAhead = 0;

		String keyStart = currentYear + "/"
				+ String.format("%02d", this.currentMonth);
		String currentKey = "";

		// QUALITY CHANGES
		// I changed it to where I just call these two methods instead of having
		// that big block of code
		// see the comments for these methods for more details.
		DefaultTableModel table = this.getTable(currentKey, keyStart,
				currentYear, this.currentMonth);
		this.setTable(table);

	}

	/*
	 * SWAP 1 TEAM 10 QUALITY CHANGES Seeing that all the fillMonth methods had
	 * a block of code that looked approximately the same, I decided that the
	 * block should be pulled out and turned into its own method. From there, I
	 * took one block, pulled it out into this method, and checked the other
	 * blocks against the block I pulled out, and made sure there weren't any
	 * logical differences. The only differences between the blocks where what
	 * values it used for the "currentMonth" and "currentYear", for the method
	 * that were ahead or behind, they would calculate a different month. As
	 * such I decided that it would be best to make this very generic, and just
	 * take any of the variables that weren't already in this block as
	 * parameters. Even though this leads to a decent sized parameter list, it
	 * allows for great control of the table creation. Additionally, all the
	 * parameters really shouldn't be determined anywhere aside from where these
	 * method are called.
	 * 
	 * This allows for the table to be made anywhere, by any method, instead of
	 * all the logic existing in the associated method. Most notably, this could
	 * allow for the calendar to skip ahead by a year, or perhaps other chunks
	 * of time, since the only thing needed to be changed is the "setup" in the
	 * method that calls this one, and what is passed into this method.
	 */
	private DefaultTableModel getTable(String currentKey, String keyStart,
			int currentYear, int currentMonth) {
		// Generates calendar for current month if none exists
		while (currentKey.equals("")) {
			Set<String> keys = this.scheduleMap.keySet();
			for (String key : keys) {
				if (key.startsWith(keyStart)) {
					currentKey = key;
					break;
				}
			}
			// Swap 3 Team 3
			// Ehancement from refactoring
			/*
			 * Since Team2 didn't say what features could be enabled and several
			 * of their refactorings were hard to generate features from Steve
			 * said that we could pick areas that we refactored for our swap 2
			 * and generate new features from them.
			 * 
			 * Thier refactoring enabled the table's logic to only need to be
			 * modified in a single place.
			 * 
			 * Their refactoring made it easier to do this change because they
			 * combined all the ways the table is created into a single method
			 * so we only had to modify one place.
			 * 
			 * Our new feature added value to the system because it reduces the
			 * system resource footprint and it also avoided any sort of
			 * concurrency problems if the schedule was attempted to be
			 * serialized at the same time.
			 */
			if (currentKey.equals("")) {
				if (!scheduleThread.isAlive()) {
					scheduleThread = new Thread(this.schedule);
					scheduleThread.start();
				}
				if (!this.schedule.workerForEveryJob) {
					break;
				}

				// this.schedule.calculateNextMonth();
			}
		}

		DefaultTableModel table = new DefaultTableModel(new Object[0][0],
				new String[0][0]);
		this.cal = new GregorianCalendar(currentYear, currentMonth - 1, 1);

		while (currentMonth == this.cal.get(Calendar.MONTH) + 1) {
			String tempKey = this.cal.get(Calendar.YEAR)
					+ "/"
					+ String.format("%02d", (this.cal.get(Calendar.MONTH) + 1))
					+ "/"
					+ String.format("%02d", this.cal.get(Calendar.DAY_OF_MONTH));
			if (this.scheduleMap.containsKey(tempKey)) {

				int numOfJobs = this.scheduleMap.get(tempKey).size();
				String[] colData = new String[numOfJobs];
				int i = 0;

				for (String key : this.scheduleMap.get(tempKey).keySet()) {
					colData[i] = key + ": "
							+ this.scheduleMap.get(tempKey).get(key).getName();
					i++;
				}

				String numDate = (this.cal.get(Calendar.MONTH) + 1)
						+ "/"
						+ String.format("%02d",
								this.cal.get(Calendar.DAY_OF_MONTH)) + "/"
						+ String.format("%02d", this.cal.get(Calendar.YEAR));
				String colTitle = CalendarGUI.getNameforNum(this.cal
						.get(Calendar.DAY_OF_WEEK)) + " (" + numDate + ")";
				table.addColumn(colTitle, colData);

			}
			this.cal.add(Calendar.DATE, 1);
		}

		return table;
	}

	/*
	 * SWAP 1 TEAM 10 QUALITY CHANGES I decided that the creation of the table,
	 * and the setting of the table should be separated in case they needed to
	 * be changed. Also, this would fit the query/command style, so one would
	 * give you something, while this actually sets a value.
	 */
	private void setTable(DefaultTableModel table) {
		HTMLGenerator.addMonth(this.monthName, table);
		this.scheduleTable.setModel(table);
	}

	/**
	 * Displays the next month from current month.
	 * 
	 */
	public void fillTableMonthAhead() {
		int currentYear = new GregorianCalendar().get(Calendar.YEAR);
		this.monthsAhead++;
		int showMonth = new GregorianCalendar().get(Calendar.MONTH)
				+ this.monthsAhead + 1;
		this.yearsAhead = 0;
		while (showMonth > 12) {
			currentYear++;
			showMonth -= 12;
			this.yearsAhead++;
		}
		this.setTitleMonth(showMonth, currentYear);

		String keyStart = currentYear + "/" + String.format("%02d", showMonth);
		String currentKey = "";

		// SWAP 1 TEAM 10
		// QUALITY CHANGES
		// I changed it to where I just call these two methods instead of having
		// that big block of code
		// see the comments for these methods for more details.
		DefaultTableModel table = this.getTable(currentKey, keyStart,
				currentYear, showMonth);
		this.setTable(table);
	}

	/**
	 * Displays the last months from current month.
	 * 
	 */
	public void fillTableMonthBack() {
		int tempMonths = this.monthsAhead;
		if ((new GregorianCalendar().get(Calendar.MONTH) + tempMonths) % 12 == 0) {
			this.yearsAhead--;
		}
		int currentYear = new GregorianCalendar().get(Calendar.YEAR)
				+ this.yearsAhead;
		this.monthsAhead--;
		int monthsToAdd = this.monthsAhead;
		while (monthsToAdd < -11) {
			monthsToAdd += 12;
			currentYear--;
			this.yearsAhead--;
		}
		int showMonth = new GregorianCalendar().get(Calendar.MONTH)
				+ monthsToAdd + 1;

		while (showMonth > 12) {
			showMonth -= 12;
		}

		if (currentYear < this.earliestYear
				|| (currentYear == this.earliestYear && showMonth < this.earliestMonth)) {
			this.monthsAhead++;

		} else {
			this.setTitleMonth(showMonth, currentYear);

			String keyStart = currentYear + "/"
					+ String.format("%02d", showMonth);
			String currentKey = "";

			// QUALITY CHANGES
			// I changed it to where I just call these two methods instead of
			// having that big block of code
			// see the comments for these methods for more details.
			DefaultTableModel table = this.getTable(currentKey, keyStart,
					currentYear, showMonth);
			this.setTable(table);
		}

	}

	public static String getNameforNum(int n) {
		switch (n) {
		case (1):
			return "Sunday";
		case (2):
			return "Monday";
		case (3):
			return "Tuesday";
		case (4):
			return "Wednesday";
		case (5):
			return "Thursday";
		case (6):
			return "Friday";
		case (7):
			return "Saturday";
		}
		return null;
	}

	private void initComponents() {

		this.monthTitle = new javax.swing.JLabel();
		this.previousMonthButton = new javax.swing.JButton();
		this.nextMonthButton = new javax.swing.JButton();
		this.jScrollPane1 = new javax.swing.JScrollPane();
		this.scheduleTable = new javax.swing.JTable();
		this.popup = new javax.swing.JPopupMenu();
		this.menuBar = new javax.swing.JMenuBar();
		this.fileMenu = new javax.swing.JMenu();
		this.saveChanges = new javax.swing.JMenuItem();
		this.loadSchedule = new javax.swing.JMenuItem();
		this.undoChanges = new javax.swing.JMenuItem();
		this.editMenu = new javax.swing.JMenu();
		this.editWorkers = new javax.swing.JMenuItem();
		this.editDays = new javax.swing.JMenuItem();
		this.generateMenu = new javax.swing.JMenu();
		this.genHtml = new javax.swing.JMenuItem();
		this.generateText = new javax.swing.JMenuItem();
		this.scheduleMenu = new javax.swing.JMenu();
		this.schedulePreferredItem = new javax.swing.JMenuItem();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("Calendar");

		this.monthTitle.setFont(new java.awt.Font("Tahoma", 1, 24));
		this.monthTitle.setText("Month Name Here");

		this.previousMonthButton.setText("<");
		this.previousMonthButton
				.addActionListener(new java.awt.event.ActionListener() {
					@Override
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						fillTableMonthBack();
					}
				});

		this.nextMonthButton.setText(">");
		this.nextMonthButton
				.addActionListener(new java.awt.event.ActionListener() {
					@Override
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						fillTableMonthAhead();
					}
				});

		this.scheduleTable.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] { { null, null, null }, { null, null, null },
						{ null, null, null }, { null, null, null } },
				new String[] { "Monday (10/22/2012)", "Wednesday (10/24/12)",
						"Thursday (10/26/12)" }));
		this.scheduleTable.setColumnSelectionAllowed(true);
		this.scheduleTable.getTableHeader().setReorderingAllowed(false);

		for (Worker i : this.schedule.getWorkers()) {
			final Worker input = i;
			this.popup.add(new JMenuItem(input.getName())).addActionListener(
					new java.awt.event.ActionListener() {
						@Override
						public void actionPerformed(
								java.awt.event.ActionEvent evt) {
							editCell(input);
						}
					});
		}
		this.scheduleTable.setComponentPopupMenu(this.popup);

		this.jScrollPane1.setViewportView(this.scheduleTable);

		this.fileMenu.setText("File");

		this.saveChanges.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
				java.awt.event.KeyEvent.VK_S,
				java.awt.event.InputEvent.CTRL_MASK));
		this.saveChanges.setText("Save Changes");
		this.saveChanges.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				saveChangesActionPerformed(evt);
			}
		});

		// ADDITIONAL FEATURE
		// This is the action listener for the ability to load other files, see
		// method for more
		this.loadSchedule.setText("Load Schedule");
		this.loadSchedule
				.addActionListener(new java.awt.event.ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						try {
							openNewSchedule();
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				});

		this.fileMenu.add(this.saveChanges);
		this.fileMenu.add(this.loadSchedule);

		this.undoChanges.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
				java.awt.event.KeyEvent.VK_Z,
				java.awt.event.InputEvent.CTRL_MASK));
		this.undoChanges.setText("Undo Changes");
		this.undoChanges.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				undoChangesActionPerformed(evt);
			}
		});
		// this.fileMenu.add(this.undoChanges);

		this.menuBar.add(this.fileMenu);

		this.editMenu.setText("Edit");

		this.editWorkers.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
				java.awt.event.KeyEvent.VK_W,
				java.awt.event.InputEvent.CTRL_MASK));
		this.editWorkers.setText("Edit Workers");
		this.editWorkers.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				editWorkersActionPerformed(evt);
			}
		});
		this.editMenu.add(this.editWorkers);

		this.editDays.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
				java.awt.event.KeyEvent.VK_D,
				java.awt.event.InputEvent.CTRL_MASK));
		this.editDays.setText("Edit Days");
		this.editDays.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				editDaysActionPerformed(evt);
			}
		});
		this.editMenu.add(this.editDays);

		this.menuBar.add(this.editMenu);

		this.generateMenu.setText("Generate");

		this.genHtml.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
				java.awt.event.KeyEvent.VK_H,
				java.awt.event.InputEvent.CTRL_MASK));
		this.genHtml.setText("Generate Web Page");
		this.genHtml.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				HTMLGenerator.writeHtml();
			}
		});
		this.generateMenu.add(this.genHtml);

		this.generateText.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
				java.awt.event.KeyEvent.VK_T,
				java.awt.event.InputEvent.CTRL_MASK));
		this.generateText.setText("Generate Text");
		this.generateText
				.addActionListener(new java.awt.event.ActionListener() {
					@Override
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						generateTextActionPerformed(evt);
					}
				});
		this.generateMenu.add(this.generateText);

		this.menuBar.add(this.generateMenu);

		this.scheduleMenu.add(this.schedulePreferredItem);
		this.scheduleMenu.setText("Schedule");

		this.schedulePreferredItem
				.setText("Schedule Jobs with Worker Preferences");

		this.schedulePreferredItem
				.addActionListener(new java.awt.event.ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						HTMLGenerator.reset();
						Main.toggleCalendar();
						Main.setSchedule(new WorkerPreferenceSchedule(Main
								.getDays(), Main.getWorkers()));
						Main.dumpConfigFile();
						Main.cal = new CalendarGUI(Main.getSchedule());
						Main.toggleCalendar();
					}

				});

		this.menuBar.add(this.scheduleMenu);

		setJMenuBar(this.menuBar);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(this.jScrollPane1,
						javax.swing.GroupLayout.DEFAULT_SIZE, 1002,
						Short.MAX_VALUE)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addComponent(this.previousMonthButton)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(this.monthTitle)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(this.nextMonthButton)
								.addGap(0, 0, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap(18, Short.MAX_VALUE)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING,
												false)
												.addComponent(
														this.monthTitle,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														Short.MAX_VALUE)
												.addComponent(
														this.previousMonthButton,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														Short.MAX_VALUE)
												.addComponent(
														this.nextMonthButton,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														29,
														javax.swing.GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addComponent(this.jScrollPane1,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										265,
										javax.swing.GroupLayout.PREFERRED_SIZE)));

		pack();
	}

	/**
	 * @param evt
	 */
	private void editWorkersActionPerformed(java.awt.event.ActionEvent evt) {
		Main.wSet = new WorkerSetup(this.schedule.getWorkers());
		Main.toggleWorkerSetup();
		Main.toggleCalendar();
	}

	/**
	 * @param evt
	 */
	private void editDaysActionPerformed(java.awt.event.ActionEvent evt) {

		// Swap 3 Team 3
		// ENHANCEMENT FROM REFACTORING
		// Moved Creation of worker setup

		/*
		 * Their refactoring (the middle man changes) did not directly enable
		 * new features, but made adding new features easier by shortening the
		 * chain of delegation.
		 * 
		 * The refactoring was mildly successful because we did not have to
		 * search for as many methods in order to find the right place to make
		 * changes.
		 * 
		 * The additional feature prevents the system from forgetting the
		 * already entered workers and their preferred tasks when editing days
		 * from the calendar screen. This feature is especially valuable when
		 * there are over 4 workers with tasks, because adding in all of the
		 * workers and selecting their tasks is a lot of duplicate effort.
		 */

		Main.wSet = new WorkerSetup(this.schedule.getWorkers());
		Main.config = new Config(Main.getDays());
		Main.toggleConfig();
		Main.toggleCalendar();
	}

	/*
	 * CODE SMELL: Middle Man All of these event handler methods just delegate
	 * to other methods that more accurately describe what it is the events are
	 * meant to trigger, so here, the indirection is not use full, should just
	 * put these methods there instead.
	 */

	// SWAP 2, Team 1
	// REFACTORING FOR ENHANCEMENT FROM BAD SMELL #3
	// Used Remove Middle Man to remove these methods and delegate them to the
	// method that called them.
	// Enhancement: reduces unecessary code, removes redundancy, and makes the
	// code easier to udnerstand.
	// Refactor successful.

	/**
	 * @param evt
	 */
	private void generateTextActionPerformed(java.awt.event.ActionEvent evt) {
		NavigableSet<String> keySet = this.scheduleMap.navigableKeySet();
		String textOutput = new String();

		// Swap 3 Team 3
		// Enhancement from refactoring #3
		/*
		 * Added a concrete feature that allows the user to select what the name
		 * of the file and location should be for the textual calendar file.
		 * 
		 * Their refactoring was reasonable and beneficial but was had minimal
		 * improvement on the code base. All they did was move 3 methods that
		 * contained a return statement in each to where the method was called
		 * and the name of the statement was already self explanatory so there
		 * was little additional clarity gained.
		 * 
		 * The refactoring was not very helpful for the enhancement because we
		 * did not do anything involved with the code they moved around.
		 * 
		 * Our feature added value to the system because now the user would have
		 * to rename and move the Calendar.txt if they wanted to have multiple
		 * calendar files as well as they may want to have saved it elsewhere
		 * (maybe the cloud).
		 */
		File readout = getUserSelectedFile(this, "Calendar.txt");

		ArrayList<String> dutyRows = new ArrayList<String>();

		int column = 1;
		for (String i : keySet) {
			textOutput += String.format("%-30s", "|" + i);
			NavigableSet<String> valueSet = this.scheduleMap.get(i)
					.navigableKeySet();
			int row = 0;
			for (String j : valueSet) {
				if (dutyRows.size() <= row)
					dutyRows.add("");
				String newCol = dutyRows.get(row) + "|" + j + ": "
						+ this.scheduleMap.get(i).get(j).getName();

				dutyRows.set(row,
						String.format("%-" + 30 * column + "s", newCol));
				row += 1;
			}
			column += 1;
		}

		for (String i : dutyRows) {
			textOutput += "\n" + i;
		}

		char[] letterOutput = textOutput.toCharArray();

		try {
			readout.createNewFile();

			FileWriter outFile = new FileWriter(readout);
			for (char i : letterOutput)
				outFile.write(i);
			outFile.close();
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}

	// Swap 3 Team 3
	// ENHANCEMENT FROM REFACTORING #3
	public static File getUserSelectedFile(Frame parent, String defaultFileName) {
		try {
			FileDialog fDialog = new FileDialog(parent, "Save", FileDialog.SAVE);
			fDialog.setVisible(true);
			String path = fDialog.getDirectory() + fDialog.getFile();
			return new File(path);
		} catch (Exception e) {
			return new File(defaultFileName);
		}

	}

	/**
	 * @param evt
	 */
	private void saveChangesActionPerformed(java.awt.event.ActionEvent evt) {
		Main.dumpConfigFile();
	}

	/**
	 * @param evt
	 */
	private void undoChangesActionPerformed(java.awt.event.ActionEvent evt) {
		// removed
	}

	private void editCell(Worker input) {
		int i = this.scheduleTable.getSelectedRow();
		int j = this.scheduleTable.getSelectedColumn();
		if (this.scheduleTable.getValueAt(i, j) != null) {
			System.out.println(this.scheduleTable.getColumnName(j));
			String job = this.scheduleTable.getValueAt(i, j).toString()
					.split(":")[0];
			String date = this.scheduleTable.getColumnName(j).split(" ")[1];
			date = date.substring(1, date.length() - 1);
			String[] dateNums = date.split("/");
			date = dateNums[2] + "/" + dateNums[0] + "/" + dateNums[1];
			System.out.println(date);
			this.scheduleMap.get(date).put(job, input);
			this.scheduleTable.setValueAt(job + ": " + input.getName(), i, j);
		}
	}

	// ADDITIONAL FEATURE
	// Here, there really wasn't any code smell, as the code was so unlike
	// anything that really existed up to this point
	// The only thing that really interacted with old code was the idea that
	// we'd want to load a file like they already do.

	// SWAP 2 Team 1: NOTE: ClassNotFoundException did not work on our
	// machines/Java/Eclipse. Changed the catch statement to remove the error.
	// Further Elaboration: Because this feature was already rather complete,
	// making it more thoroughly catch exceptions is the only useful thing we
	// could think of. This is also seen above when we had to change it to make
	// it work in our system config. Changed the error message to reflect this.
	private void openNewSchedule() throws ClassNotFoundException, IOException {
		String filename;
		String dirPath;
		JFileChooser fileChooser = new JFileChooser();
		int responseVal = fileChooser.showOpenDialog(fileChooser);
		if (responseVal == JFileChooser.APPROVE_OPTION) {
			filename = fileChooser.getSelectedFile().getName();
			dirPath = fileChooser.getCurrentDirectory().toString();

			try {
				Main.toggleCalendar();
				Main.recallChosenConfigFile(filename, dirPath);
				if (Main.getSchedule() != (null)) {
					Main.cal = new CalendarGUI(Main.getSchedule());
					Main.cal.setVisible(true);
				}
			} catch (IOException i) {
				Main.toggleCalendar();
				JOptionPane
						.showMessageDialog(
								new JFrame(),
								"There was an error loading the file. \nThe file may be corrupted, may not exist, or was created using a different version"
										+ "of the program");

			}
		}
	}

	private javax.swing.JMenuItem editDays;
	private javax.swing.JMenu editMenu;
	private javax.swing.JMenuItem editWorkers;
	private javax.swing.JMenu fileMenu;
	private javax.swing.JMenuItem genHtml;
	private javax.swing.JMenu generateMenu;
	private javax.swing.JMenuItem generateText;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JMenuBar menuBar;
	private javax.swing.JLabel monthTitle;
	private javax.swing.JButton nextMonthButton;
	private javax.swing.JPopupMenu popup;
	private javax.swing.JButton previousMonthButton;
	private javax.swing.JMenuItem saveChanges;
	private javax.swing.JMenuItem loadSchedule;
	private javax.swing.JTable scheduleTable;
	private javax.swing.JMenuItem undoChanges;
	private javax.swing.JMenu scheduleMenu;
	private javax.swing.JMenuItem schedulePreferredItem;
	private Thread scheduleThread;
}
