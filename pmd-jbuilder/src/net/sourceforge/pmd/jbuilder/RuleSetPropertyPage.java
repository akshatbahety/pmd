package net.sourceforge.pmd.jbuilder;

import com.borland.primetime.properties.*;
import com.borland.primetime.help.HelpTopic;
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import javax.swing.border.*;
import javax.swing.ListModel;
import javax.swing.DefaultListModel;
import java.awt.event.*;
import com.borland.primetime.ide.Browser;
import com.borland.primetime.ide.MessageCategory;
import java.util.Enumeration;


/**
 * <p>Title: JBuilder OpenTool for PMD</p>
 * <p>Description: Provides an environemnt for using the PMD aplication from within JBuilder</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: InfoEther</p>
 * @author David Craine
 * @version 1.0
 */

public class RuleSetPropertyPage extends PropertyPage {

    //static MessageCategory cat = new MessageCategory("test");

    private BorderLayout borderLayout1 = new BorderLayout();
    private JSplitPane jSplitPane1 = new JSplitPane();
    private Border border1;
    private TitledBorder titledBorder1;
    private Border border2;
    private TitledBorder titledBorder2;
    private DefaultListModel dlmAvailableRuleSets = new DefaultListModel();
    private DefaultListModel dlmSelectedRuleSets = new DefaultListModel();
    private JPanel jpAvailableRuleSets = new JPanel();
    private JScrollPane jspAvailableRuleSets = new JScrollPane();
    private JList jlistAvailableRuleSets = new JList();
    private JPanel jpSelectedRuleSets = new JPanel();
    private JList jlistSelectedRuleSets = new JList();
    private JScrollPane jspSelecedRuleSets = new JScrollPane();
    private BorderLayout borderLayout2 = new BorderLayout();
    private BorderLayout borderLayout3 = new BorderLayout();
    private JButton jbSelectRuleSets = new JButton();
    private JButton jbDeselectRuleSets = new JButton();
    private Border border3;
    private Border border4;

    public RuleSetPropertyPage() {
        try  {
            jbInit();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private void initRuleSplitPanes() {
        //loop through the sets of rules and place them in the appropriate pane based upon setting
        for (int i=0;i<RuleSetPropertyGroup.RULESET_NAMES.length; i++) {
            ListEntry le = new ListEntry(RuleSetPropertyGroup.RULESET_NAMES[i], RuleSetPropertyGroup.PROPKEYS[i]);
            if (Boolean.valueOf(RuleSetPropertyGroup.PROPKEYS[i].getValue()).booleanValue())
                dlmSelectedRuleSets.addElement(le);
            else
                dlmAvailableRuleSets.addElement(le);
        }
    }


    protected void jbInit() throws Exception {
        border1 = BorderFactory.createEtchedBorder(Color.white,new Color(178, 178, 178));
        titledBorder1 = new TitledBorder(border1,"Available Rule Sets");
        border2 = BorderFactory.createEtchedBorder(Color.white,new Color(178, 178, 178));
        titledBorder2 = new TitledBorder(border2,"Selected Rule Sets");
        border3 = BorderFactory.createEtchedBorder(Color.white,new Color(165, 163, 151));
        border4 = BorderFactory.createCompoundBorder(border3,titledBorder1);
        this.setLayout(borderLayout1);
        jlistAvailableRuleSets.setModel(dlmAvailableRuleSets);
        jlistSelectedRuleSets.setModel(dlmSelectedRuleSets);
        jpAvailableRuleSets.setLayout(borderLayout2);
        jpSelectedRuleSets.setLayout(borderLayout3);
        jbSelectRuleSets.setText("Select ===>>>");
        jbSelectRuleSets.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jbSelectRuleSets_actionPerformed(e);
            }
        });
        jbDeselectRuleSets.setText("<<<===Remove");
        jbDeselectRuleSets.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jbDeselectRuleSets_actionPerformed(e);
            }
        });
        jpAvailableRuleSets.setBorder(titledBorder1);
        jpSelectedRuleSets.setBorder(titledBorder2);
        this.add(jSplitPane1,  BorderLayout.CENTER);
        jSplitPane1.add(jpAvailableRuleSets, JSplitPane.LEFT);
        jSplitPane1.add(jpSelectedRuleSets, JSplitPane.RIGHT);
        jpAvailableRuleSets.add(jspAvailableRuleSets, BorderLayout.CENTER);
        jpSelectedRuleSets.add(jspSelecedRuleSets, BorderLayout.CENTER);
        jspSelecedRuleSets.getViewport().add(jlistSelectedRuleSets, null);
        jspAvailableRuleSets.getViewport().add(jlistAvailableRuleSets, null);
        jpAvailableRuleSets.add(jbSelectRuleSets,  BorderLayout.SOUTH);
        jpSelectedRuleSets.add(jbDeselectRuleSets,  BorderLayout.SOUTH);

        initRuleSplitPanes();
        jSplitPane1.setDividerLocation(200);
    }

    public void writeProperties() {
        //set the properties for the items items in the selected list to true
        for (Enumeration e = dlmSelectedRuleSets.elements(); e.hasMoreElements(); ) {
            ListEntry le = (ListEntry)e.nextElement();
            le.getProp().setValue("true");
        }

        //set the properties for the items items in the available list to false
        for (Enumeration e = dlmAvailableRuleSets.elements(); e.hasMoreElements(); ) {
            ListEntry le = (ListEntry)e.nextElement();
            le.getProp().setValue("false");
        }


    }
    public HelpTopic getHelpTopic() {
        return null;
    }
    public void readProperties() {
    }

    void jbSelectRuleSets_actionPerformed(ActionEvent e) {
        //get the selected elements in the selected list and move to the available list
        int [] selectedItems = jlistAvailableRuleSets.getSelectedIndices();
        if (selectedItems != null) {
            for (int i=0; i<selectedItems.length; i++) {
                ListEntry le = (ListEntry)dlmAvailableRuleSets.get(selectedItems[i]);
                dlmSelectedRuleSets.addElement(le);
                dlmAvailableRuleSets.remove(selectedItems[i]);
                jlistSelectedRuleSets.updateUI();
                jlistAvailableRuleSets.updateUI();
            }
        }

    }

    void jbDeselectRuleSets_actionPerformed(ActionEvent e) {
        //get the selected elements in the available list and move to the selected list
        int [] selectedItems = jlistSelectedRuleSets.getSelectedIndices();
        if (selectedItems != null) {
            for (int i=0; i<selectedItems.length; i++) {
                ListEntry le = (ListEntry)dlmSelectedRuleSets.get(selectedItems[i]);
                dlmAvailableRuleSets.addElement(le);
                dlmSelectedRuleSets.remove(selectedItems[i]);
                jlistSelectedRuleSets.updateUI();
                jlistAvailableRuleSets.updateUI();
            }
        }
    }

}


class ListEntry {
    GlobalProperty prop;
    String displayName;

    public ListEntry(String name, GlobalProperty prop) {
        this.displayName = name;
        this.prop = prop;
    }

    public GlobalProperty getProp() {
        return prop;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String toString() {
        return displayName;
    }
}

