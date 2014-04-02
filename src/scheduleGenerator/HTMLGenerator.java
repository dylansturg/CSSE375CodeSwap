package scheduleGenerator;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;

/**
 * Used to generate HTML for a schedule.
 * 
 * @author schneimd. Created Oct 26, 2012.
 */
public class HTMLGenerator {

	// SWAP 1, Team 10
	// SMELL: Temporary Field - this is similar to a temporary field because a
	// writer object can only exist within writeHtml().
	// It should instead just be local to that method.

	// SWAP 2, Team 1
	// REFACTORING FOR ENHANCEMENT FROM BAD SMELL #5
	// Used Extract Method to move the temporary fields writer and path into the
	// writeHtml() method.
	// Enhancement. No more static fields. objects are now within the
	// writeHtml() method.
	// Refactor successful.
	/**
	 * finalizes and writes to file.
	 * 
	 */
	public static void writeHtml() {
		PrintWriter writer;
		File path;
		String all = top + insert.subSequence(0, insert.length() - 1) + "];"
				+ (table.subSequence(0, table.length() - 1) + "];") + bottom
				+ foot;

		// Swap3, Team3
		// Enhancement from refactoring #5

		/*
		 * Their refactoring allowed for the method to be static by not specify
		 * the writer and path as fields so its impossible to get concurrency
		 * problems.
		 * 
		 * Their refactoring did not help this feature because since they are
		 * now not static fields the old choice cannot be cached unless we undo
		 * their refactoring and make File a static field again.
		 * 
		 * Our feature enables people to create multiple schedules and save them
		 * with appropriate names such as Defoe's Church schedule or Steve's
		 * grading schedule and not be forced to stick with index.html.
		 */

		path = CalendarGUI.getUserSelectedFile(new JFrame(), "index.html");
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

	/*
	 * CODE SMELL - Excessive use of literals Way too much text for source code,
	 * should be in a text file if nothing else.
	 */
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
	static String top = "<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'><html xmlns='http://www.w3.org/1999/xhtml'><head><meta http-equiv='Content-Type' content='text/html; charset=utf-8' /><title>Rose-Hulman Schedule Generator</title><link href='css/main.css' rel='stylesheet' type='text/css' />\n"
			+ "<script type='text/javascript'>window.onload = function() {\n	"
			+ "		document.getElementById('monthName').innerHTML = '<input onclick=\\\'monthBack()\\\' type=\\\'submit\\\' name=\\\'monthBack\\\' id=\\\'monthBack\\\' value=\\\'&lt;\\\' /> ' + months[monthIndex][0] + 		' <input type=\\\'submit\\\' name=\\\'monthAhead\\\' id=\\\'monthAhead\\\' value=\\\'&gt;\\\' onclick=\\\'monthAhead()\\\' />';\n"
			+ "		document.getElementById('middle').innerHTML = tables[monthIndex];\n"
			+ "		var numCols = months[monthIndex][1].length;\n"
			+ "		for(var c = 0; c<numCols; c++) {\n			"
			+ "var numRows = months[monthIndex][1][c].length;\n"
			+ "			for(var r = 0; r<numRows; r++) {\n"
			+ "				document.getElementById(c + ',' + r).innerHTML = months[monthIndex][1][c][r];\n"
			+ "			}\n"
			+ "		}\n}\n"
			+ "var col = 0;\n"
			+ "var row = 0;\n"
			+ "var monthIndex = 0;\n";
	// Insert JavaScript
	/**
	 * Used to fill all of body.
	 */
	static String bottom = "function monthBack() {	if(monthIndex > 0) {\n	"
			+ "	monthIndex--;\n"
			+ "	document.getElementById('middle').innerHTML = tables[monthIndex];\n"
			+ "	document.getElementById('monthName').innerHTML = '<input onclick=\\'monthBack()\\' type=\\'submit\\' name=\\'monthBack\\' id=\\'monthBack\\' value=\\'&lt;\\' /> ' + months[monthIndex][0] + 		' <input type=\\'submit\\' name=\\'monthAhead\\' id=\\'monthAhead\\' value=\\'&gt;\\' onclick=\\'monthAhead()\\' />';\n"
			+ "		var numCols = months[monthIndex][1].length;\n"
			+ "		for(var c = 0; c<numCols; c++) {\n"
			+ "			var numRows = months[monthIndex][1][c].length;\n"
			+ "			for(var r = 0; r<numRows; r++) {\n"
			+ "				document.getElementById(c + ',' + r).innerHTML = months[monthIndex][1][c][r];\n"
			+ "			}\n"
			+ "		}\n"
			+ "	}\n"
			+ "}\n"
			+ "function monthAhead() {\n"
			+ "	if(monthIndex < months.length - 1) {\n"
			+ "		monthIndex++;\n"
			+ "		document.getElementById('middle').innerHTML = tables[monthIndex];\n"
			+ "		document.getElementById('monthName').innerHTML = '<input onclick=\\'monthBack()\\' type=\\'submit\\' name=\\'monthBack\\' id=\\'monthBack\\' value=\\'&lt;\\' /> ' + months[monthIndex][0] + 		' <input type=\\'submit\\' name=\\'monthAhead\\' id=\\'monthAhead\\' value=\\'&gt;\\' onclick=\\'monthAhead()\\' />';\n"
			+ "		var numCols = months[monthIndex][1].length;\n"
			+ "		for(var c = 0; c<numCols; c++) {\n"
			+ "			var numRows = months[monthIndex][1][c].length;\n"
			+ "			for(var r = 0; r<numRows; r++) {\n"
			+ "				document.getElementById(c + ',' + r).innerHTML = months[monthIndex][1][c][r];\n"
			+ "			}\n"
			+ "		}\n"
			+ "	}\n"
			+ "}\n"
			+ "</script>\n"
			+ "</head><body><table width='100%' border='0' id='container'>  <tr>   <td colspan='3' id='head'><img src='images/head.gif' width='500' height='150' alt='header' /></td> </tr> <tr>   <td width='15%' rowspan='2' id='left'>&nbsp;</td>   <td height='84' id='monthName'></td>   <td width='19%' rowspan='2' id='right'>&nbsp;</td> </tr> <tr>   <td width='66%' height='606' id='middle'>";
	// Insert Table
	/**
	 * Used as footer
	 */
	static String foot = "</td></tr></table></td></tr><tr><td colspan='3' id='foot'>&nbsp;</td></tr></table></body></html>";
}