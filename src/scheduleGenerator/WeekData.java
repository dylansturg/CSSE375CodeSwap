package scheduleGenerator;

import java.awt.Dimension;

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

	// Team3-Swap2 Refactored data clump
	/*
	 * We turned the data clump into a single class and this makes the code
	 * cleaner and easier to use. It would be possible to add additional data
	 * such as what hour the job would be preformed during.
	 */
	public WeekData() {
		this.firstSelection = true;
		this.models = new DefaultListModel[7];

		this.WeekJobList = new JList[7];
		this.WeekScrollPane = new JScrollPane[7];
		this.WeekJobName = new JTextField[7];
		this.WeekLabel = new JLabel[7];
		this.WeekAddJob = new JButton[7];
		this.WeekDeleteJob = new JButton[7];
		this.WeekTab = new JPanel[7];

		for (int i = 0; i < WeekJobList.length; i++) {
			this.WeekJobList[i] = new JList();
			this.WeekScrollPane[i] = new JScrollPane();
			this.WeekScrollPane[i].setPreferredSize(new Dimension(185, 150));
			this.WeekJobName[i] = new JTextField();
			this.WeekLabel[i] = new JLabel();
			this.WeekAddJob[i] = new JButton();
			this.WeekDeleteJob[i] = new JButton();
			this.WeekTab[i] = new JPanel();
		}
	}
}