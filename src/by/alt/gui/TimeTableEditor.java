package by.alt.gui;

import by.alt.gui.MainForm;
import by.alt.Object.Shedules;
import by.alt.Object.TableEntry;
import lu.tudor.santec.jtimechooser.JTimeChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

public class TimeTableEditor extends JDialog {
    private static JTextField nameField;
    private static JComboBox sheduleCombo;
    public static JTimeChooser fromTime;
    public static JTimeChooser toTime;
    TimeTableEditor(Frame owner, String title){
        super(owner,title,ModalityType.DOCUMENT_MODAL);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        // setBounds((int)(MainForm.this.getWidth()/2)-250,(int)(MainForm.this.getHeight()/2)-100,500,330);
        setPreferredSize(new Dimension(500,330));
        setMinimumSize(new Dimension(500,330));
        setMaximumSize(new Dimension(500,330));
        JPanel row1 = new JPanel();
        row1.setLayout(new BoxLayout(row1, BoxLayout.X_AXIS));
        JPanel row2 = new JPanel();
        row2.setLayout(new BoxLayout(row2, BoxLayout.X_AXIS));
        JPanel row3 = new JPanel();
        row3.setLayout(new BoxLayout(row3, BoxLayout.X_AXIS));
        JPanel row4 = new JPanel();
        row4.setLayout(new BoxLayout(row4, BoxLayout.X_AXIS));
        JPanel row5 = new JPanel();
        row5.setLayout(new BoxLayout(row5, BoxLayout.X_AXIS));
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        getContentPane().add(Box.createRigidArea(new Dimension(1,30)));
        getContentPane().add(row1);
        getContentPane().add(Box.createRigidArea(new Dimension(1,30)));
        getContentPane().add(row2);
        getContentPane().add(Box.createRigidArea(new Dimension(1,30)));
        getContentPane().add(row3);
        getContentPane().add(Box.createRigidArea(new Dimension(1,30)));
        getContentPane().add(row4);
        getContentPane().add(Box.createRigidArea(new Dimension(1,30)));
        getContentPane().add(row5);
        getContentPane().add(Box.createRigidArea(new Dimension(1,30)));

        row1.add(Box.createRigidArea(new Dimension(30,1)));
        JLabel nameLabel = new JLabel("Имя");
        row1.add(nameLabel);
        row1.add(Box.createRigidArea(new Dimension(100,1)));
        nameField = new JTextField(20);
        row1.add(nameField);
        row1.add(Box.createRigidArea(new Dimension(30,1)));

        row2.add(Box.createRigidArea(new Dimension(30,1)));
        JLabel sheduleLabel = new JLabel("Режим");
        row2.add(sheduleLabel);
        row2.add(Box.createRigidArea(new Dimension(83,1)));
        sheduleCombo = new JComboBox();
        sheduleCombo.addItem(Shedules.DAY);
        sheduleCombo.addItem(Shedules.NIGHT);
        row2.add(sheduleCombo);
        row2.add(Box.createRigidArea(new Dimension(100,1)));

        row3.add(Box.createRigidArea(new Dimension(30,1)));
        JLabel fromField = new JLabel("Время начала");
        row3.add(fromField);
        row3.add(Box.createRigidArea(new Dimension(40,1)));
        fromTime = new JTimeChooser();
        row3.add(fromTime);
        row3.add(Box.createRigidArea(new Dimension(250,1)));

        row4.add(Box.createRigidArea(new Dimension(30,1)));
        JLabel toField = new JLabel("Время окончания");
        row4.add(toField);
        row4.add(Box.createRigidArea(new Dimension(22,1)));
        toTime = new JTimeChooser();
        row4.add(toTime);
        row4.add(Box.createRigidArea(new Dimension(250,1)));

        JButton addB = new JButton("Добавить" );
        addB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TableEntry addedTableEntry = new TableEntry();
                MainForm.tableEntryList.add(addedTableEntry.getTableEntryFromDialog());
                MainForm.tableUpdate();
                dispose();
            }
        });
        JButton cancelB = new JButton("Отмена");
        cancelB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        row5.add(addB);
        row5.add(Box.createRigidArea(new Dimension(70,1)));
        row5.add(cancelB);
    }
    public static String getNameFromDialog(){
        String str = nameField.getText();
        str = str.replace (".","");
        str = str.replace (" ","");
        return str;
    }
    public static String getSheduleFromDialog(){
        return sheduleCombo.getSelectedItem().toString();
    }
    public static String getToTimeFromDialog(){
        DecimalFormat myFormatter = new DecimalFormat("00");
        String hours = myFormatter.format(toTime.getHours());
        String minutes = myFormatter.format(toTime.getMinutes());
        return hours + "." + minutes;
    }
    public static String getFromTimeFromDialog(){
        DecimalFormat myFormatter = new DecimalFormat("00");
        String hours = myFormatter.format(fromTime.getHours());
        String minutes = myFormatter.format(fromTime.getMinutes());
        return hours + "." + minutes;
    }
}
