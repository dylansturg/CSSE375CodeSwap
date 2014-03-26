/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduleGenerator;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.*;

/**
 *
 * @author schneimd
 */
public class Config extends javax.swing.JFrame {

    /**
     * Used to edit days.
     *
     * @param days
     */
    @SuppressWarnings("unchecked")
	public Config(ArrayList<Day> days) {
    	this.data.models = new DefaultListModel[7];
        initDyn();
        initComponents();
        
    	for(Day day: days) {
            // SWAP 1, TEAM 2
            this.data.WeekCheck[day.getDayOfWeek()].doClick();
            ArrayList<String> jobs = day.getJobs();
            for(String job: jobs) {
                this.data.models[0].addElement(job);
                this.data.WeekJobList[day.getDayOfWeek()].setModel(this.data.models[0]);
            }
    	}
    }
    
    /**
     * Creates new form.
     */
    public Config() {
        this.data.models = new DefaultListModel[7];
        initDyn();
        
        initComponents();
    }
    
    @SuppressWarnings("rawtypes")
	private void initDyn() {

        // SWAP 1, TEAM 2
    	//SMELL: Data Clump - There are many areas in this class where data in the format below is "clumped" together.
    	//These would be hard to turn into an object because they are GUI related but there may be a better way to clean this
    	//clump up.
        this.data.WeekJobList = new JList[7];
        this.data.WeekScrollPane = new JScrollPane[7];
        this.data.WeekJobName = new JTextField[7];
        this.data.WeekLabel = new JLabel[7];
        this.data.WeekAddJob = new JButton[7];
        this.data.WeekDeleteJob = new JButton[7];
        this.data.WeekTab = new JPanel[7];
        
        for(int i = 0; i< data.WeekJobList.length; i++){
            this.data.WeekJobList[i] = new JList();
            this.data.WeekScrollPane[i] = new JScrollPane();
            this.data.WeekScrollPane[i].setPreferredSize(new Dimension(185,150));
            this.data.WeekJobName[i] = new JTextField();
            this.data.WeekLabel[i] = new JLabel();
            this.data.WeekAddJob[i] = new JButton();
            this.data.WeekDeleteJob[i] = new JButton();
            this.data.WeekTab[i] = new JPanel();
        }
    }

    // SWAP 1, TEAM 2
    // With additional refactoring, the initialization could be reduced in size
    // much like
    //SMELL: Shotgun Surgery - Whenever small changes were made to refactor this class, many more changes were demanded in order for
    // the project to work in a future state.
    private void initComponents() {
        this.data.WeekCheck = new JCheckBox[7];
        for(int i = 0; i< data.WeekCheck.length; i++){
            data.WeekCheck[i] = new JCheckBox();
            data.WeekCheck[i].setText(Day.DayOfTheWeek(i+1));
            data.WeekCheck[i].setName(Day.DayOfTheWeek(i+1) + "Check"); // NOI18N
            data.WeekCheck[i].addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    weekCheckActionPerformed(evt);
                }
            });
        }

    	this.data.jPanel1 = new javax.swing.JPanel();
        this.data.jLabel1 = new javax.swing.JLabel();
        this.data.nextButton = new javax.swing.JButton();
        this.data.dayTabs = new javax.swing.JTabbedPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Configuration");
        setPreferredSize(new java.awt.Dimension(801, 87));
        setResizable(false);

        this.data.nextButton.setText("Next");
        this.data.nextButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(this.data.jPanel1);
        this.data.jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(this.data.jLabel1)
                .addGap(18, 18, 18)
                .addComponent(this.data.WeekCheck[0])
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(this.data.WeekCheck[1], javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(this.data.WeekCheck[2])
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(this.data.WeekCheck[3], javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(this.data.WeekCheck[4])
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(this.data.WeekCheck[5], javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(this.data.WeekCheck[6], javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(this.data.nextButton)
                .addGap(78, 78, 78))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(this.data.WeekCheck[0], javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(this.data.WeekCheck[5], javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(this.data.WeekCheck[6], javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                        .addComponent(this.data.nextButton))
                    .addComponent(this.data.WeekCheck[3], javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(this.data.WeekCheck[2], javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(this.data.jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(this.data.WeekCheck[4], javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(this.data.WeekCheck[1], javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(this.data.jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 18, Short.MAX_VALUE))
            .addComponent(this.data.dayTabs)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(this.data.jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(this.data.dayTabs, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE))
        );

        this.data.dayTabs.getAccessibleContext().setAccessibleName("Days Tab");

        pack();
    }// </editor-fold>

    
    /**
	 * @param evt  
	 */
    @SuppressWarnings("unchecked")
    // SWAP 1, TEAM 2
	private void weekCheckActionPerformed(java.awt.event.ActionEvent evt) {
        for(int i = 0; i < this.data.WeekCheck.length; i++){
            if(data.WeekCheck[i].isSelected()){
                if(this.data.firstSelection) {
                    stretch();
                }
                this.data.models[0] = new DefaultListModel<Object>();
                this.data.WeekJobList[i].setModel(this.data.models[0]);
                this.data.WeekScrollPane[i].setViewportView(this.data.WeekJobList[i]);
                this.data.WeekJobName[i].setColumns(20);
                this.data.WeekLabel[i].setText("Job Name:");
                this.data.WeekAddJob[i].setText("Add Job");
                final int k = i;
                this.data.WeekAddJob[i].addActionListener(new java.awt.event.ActionListener() {
                    @Override
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        if(!Config.this.data.WeekJobName[k].getText().isEmpty()) {
                            Config.this.data.models[0].addElement(Config.this.data.WeekJobName[k].getText());
                            Config.this.data.WeekJobList[k].setModel(Config.this.data.models[0]);
                            Config.this.data.WeekJobName[k].setText("");
                        }
                    }
                });

                this.data.WeekDeleteJob[i].setText("Delete Job");
                this.data.WeekDeleteJob[i].addActionListener(new java.awt.event.ActionListener() {
                    @Override
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        while(!Config.this.data.WeekJobList[k].isSelectionEmpty()) {
                            int n = Config.this.data.WeekJobList[k].getSelectedIndex();
                            Config.this.data.models[0].remove(n);
                        }
                    }
                });

                javax.swing.GroupLayout weekTabLayout = new javax.swing.GroupLayout(this.data.WeekTab[i]);
                this.data.WeekTab[i].setLayout(weekTabLayout);
                weekTabLayout.setHorizontalGroup(
                        weekTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(weekTabLayout.createSequentialGroup()
                                        .addContainerGap()
                                        .addComponent(this.data.WeekScrollPane[i], javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addGroup(weekTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(weekTabLayout.createSequentialGroup()
                                                        .addComponent(this.data.WeekLabel[i])
                                                        .addGroup(weekTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addGroup(weekTabLayout.createSequentialGroup()
                                                                        .addGap(14, 14, 14)
                                                                        .addComponent(this.data.WeekAddJob[i]))
                                                                .addGroup(weekTabLayout.createSequentialGroup()
                                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addComponent(this.data.WeekJobName[i], javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                                .addComponent(this.data.WeekDeleteJob[i]))
                                        .addContainerGap(431, Short.MAX_VALUE))
                );
                weekTabLayout.setVerticalGroup(
                        weekTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(weekTabLayout.createSequentialGroup()
                                        .addContainerGap()
                                        .addGroup(weekTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addGroup(weekTabLayout.createSequentialGroup()
                                                        .addGroup(weekTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                .addComponent(this.data.WeekJobName[i], javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addComponent(this.data.WeekLabel[i]))
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(this.data.WeekAddJob[i])
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(this.data.WeekDeleteJob[i]))
                                                .addComponent(this.data.WeekScrollPane[i], javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addContainerGap(25, Short.MAX_VALUE))
                );
                this.data.dayTabs.addTab(Day.DayOfTheWeek(i+1), this.data.WeekTab[i]);
            } else {
                stretch();
                this.data.dayTabs.remove(this.data.WeekTab[i]);
            }
        }
    }

    /**
	 * @param evt  
	 */
    private void nextButtonActionPerformed(java.awt.event.ActionEvent evt) {
    	ArrayList<Day> days = new ArrayList<Day>();
        // SWAP 1 Team 2
        for(int i = 0; i < this.data.WeekCheck.length; i++){
            if(this.data.WeekCheck[i].isSelected()){
                ArrayList<Object> day = new ArrayList<Object>();
                List<Object> jobs = Arrays.asList(this.data.models[0].toArray());
                day.addAll(jobs);
                days.add(new Day(Day.DayOfTheWeek(i+1),day));
            }
        }
    	if(days.size() > 0) {
    		boolean hasJobs = true;
    		int i = 0;
    		while(hasJobs && i<days.size()) {
    			if(days.get(i).getJobs().size() == 0) {
    				hasJobs = false;
    			}
    			i++;
    		}
    		if(hasJobs) {
		    	Main.setDays(days);
		    	Main.wSet = new WorkerSetup();
		    	Main.toggleWorkerSetup();
		    	Main.config = this;
		    	Main.toggleConfig();
    		} else {
    			JOptionPane.showMessageDialog(this, "You must have at least one job each day.");
    		}
    	} else {
    		JOptionPane.showMessageDialog(this, "You have not added any days.");
    	}
    }
    
    
    private void stretch() {
        if(numSelected() > 0) {
            this.setSize(801, 290);
            this.data.firstSelection = false;
        } else {
            this.setSize(801, 87);
            this.data.firstSelection = true;
        }
    }

    // SWAP 1 Team 2
    private int numSelected(){
        int j = 0;
        for(int i = 0; i < data.WeekCheck.length; i++){
            if(data.WeekCheck[i].isSelected()) j++;
        }
        return j;
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Config.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Config.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Config.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Config.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
			public void run() {
                new Config().setVisible(true);
            }
        });
    }

    private WeekData data = new WeekData(true);
}
