package scheduleGenerator;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

/**
 * Used to generate HTML for a schedule.
 * 
 * @author schneimd. Created Oct 26, 2012.
 */
public class HTMLGenerator {

	/**
	 * Used to write.
	 */
	static PrintWriter writer;
	/**
	 * Stores path to file.
	 */
	static File path;

	/**
	 * finalizes and writes to file.
	 * 
	 */
	public static void writeHtml() {
		String all = top + insert.subSequence(0, insert.length() - 1) + "];"
				+ (table.subSequence(0, table.length() - 1) + "];") + bottom
				+ foot;
		path = new File("index.html");
		if (path.exists()) {
			path.delete();
		}
		try {
			path.createNewFile();
			writer = new PrintWriter(path);
			writer.println(all);
			writer.close();
		} catch (IOException exception) {

			exception.printStackTrace();
		}

	}

	/**
	 * Used to reset when new schedules are made.
	 * 
	 */
	public static void reset() {
		insert = "\nvar months = [";

		table = "\nvar tables = [";
	}

	/**
	 * Creates the output for a table based on number of jobs for a day and
	 * number of days.
	 * 
	 * @param numDays
	 * @param numJobs
	 * 
	 */
	public static void makeTable(int numDays, ArrayList<Integer> numJobs) {
		String tempTable = "\"<table width='100%' height='44%' border='1'><tr>";
		for (int c = 0; c < numDays; c++) {
			tempTable += "<td class='day'>" + "<table width='100%' border='1'>"
					+ "<tr>" + "<th id='" + c + ",0' scope='col'></th></tr>";
			for (int r = 1; r <= numJobs.get(c); r++) {
				tempTable += "<tr><td id='" + c + "," + r
						+ "' class='day'></td></tr>";
			}
			tempTable += "</table></td>";
		}
		tempTable += "</tr></table>\",";
		table += tempTable;
	}

	/**
	 * Adds a month to the array of months in javascript.
	 * 
	 * @param monthName
	 * @param table
	 * 
	 */
	public static void addMonth(String monthName, DefaultTableModel table) {
		if (!insert.contains(monthName)) {
			String tempInsert = "['" + monthName + "'," + "[";

			for (int c = 0; c < table.getColumnCount(); c++) {
				tempInsert += "['" + table.getColumnName(c) + "',";
				for (int r = 0; r < table.getRowCount(); r++) {
					if (table.getValueAt(r, c) != null) {
						tempInsert += "'" + table.getValueAt(r, c) + "'";
						if (r < table.getRowCount() - 1) {
							tempInsert += ",";
						}
					}
				}
				tempInsert += "]";
				if (c < table.getColumnCount() - 1) {
					tempInsert += ",";
				}
			}

			tempInsert += "]],";
			insert += tempInsert;
		}
	}

	/**
	 * Returns the generated tables for javascript.
	 * 
	 * @return table
	 */
	public static String getTables() {
		return table;
	}

	/**
	 * sets the tables for javascript to use.
	 * 
	 * @param tables
	 */
	public static void setTables(String tables) {
		table = tables;
	}

	/**
	 * Used to store array for javascipt.
	 */
	static String insert = "\nvar months = [";

	/**
	 * Used to store tables for each month.
	 */
	static String table = "\nvar tables = ["; // This needs to remove the last
												// comma and add a bracket.

	/**
	 * Used to put header.
	 */
	// SMELL: Inappropriate Intimacy - this is inappropriate to be seen by any
	// class even its self. These crazy long lines
	// of html code should be hidden in an xml or json file so it cannot be
	// easily edited.
	
	// Team3-Swap2 Refactored Inappropriate Intimacy
	/*
	 * Fixed Inappropriate Intimacy, this fix lets anybody just change the files
	 * used to generate the HTML page rather than having to recompile the
	 * program and run it again to create changes to the layout.
	 */
	static String top = WorkerSetup.readFileAsString("HTMLTop");
	// Insert JavaScript
	/**
	 * Used to fill all of body.
	 */
	static String bottom = WorkerSetup.readFileAsString("HTMLBottom");

	// Insert Table
	/**
	 * Used as footer
	 */
	static String foot = "</td></tr></table></td></tr><tr><td colspan='3' id='foot'>&nbsp;</td></tr></table></body></html>";
}