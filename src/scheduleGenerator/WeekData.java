package scheduleGenerator;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

public class WeekData {
	public boolean firstSelection;
	@SuppressWarnings("rawtypes")
	public DefaultListModel[] models;
	public JList[] WeekJobList;
	public JCheckBox[] WeekCheck;
	public JScrollPane[] WeekScrollPane;
	public JButton[] WeekAddJob;
	public JButton[] WeekDeleteJob;
	public JTextField[] WeekJobName;
	public JLabel[] WeekLabel;
	public JPanel[] WeekTab;
	public JTabbedPane dayTabs;
	public JLabel jLabel1;
	public JPanel jPanel1;
	public JButton nextButton;

	public WeekData(boolean firstSelection) {
		this.firstSelection = firstSelection;
	}
}