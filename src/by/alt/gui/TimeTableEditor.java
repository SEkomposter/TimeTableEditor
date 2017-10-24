package by.alt.gui;

import by.alt.Object.GroupTime;
import by.alt.Object.UserTime;
import by.alt.gui.MainForm;
import by.alt.Object.Shedules;
import by.alt.Object.TableEntry;
import lu.tudor.santec.jtimechooser.JTimeChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static by.alt.gui.MainForm.*;

public class TimeTableEditor extends JDialog{
    private static JTextField nameField;
    private static JComboBox sheduleCombo;
    public static JTimeChooser fromTime;
    public static JTimeChooser toTime;
    JButton diffButton = new JButton();
    private int rowNumber;
    private TableEntry tableEntry;

    //конструктор для формы редактирования существующего расписания
    TimeTableEditor(Frame owner, String title, JButton button, int rowNumber){
        this(owner,title, button);
        this.rowNumber = rowNumber;
        tableEntry = new TableEntry();
        // считываем данные с записи в таблице и помещаем их на форму редактирования расписания:
        nameField.setText((String) tableModel.getValueAt(rowNumber,0));
        sheduleCombo.setSelectedItem((String) tableModel.getValueAt(rowNumber,1));
        fromTime.setTime(parseDateGotFromForm((String) tableModel.getValueAt(rowNumber,2)));
        toTime.setTime(parseDateGotFromForm((String) tableModel.getValueAt(rowNumber,3)));
    }
    //конструктор для формы создания нового расписания:
    TimeTableEditor(Frame owner, String title, JButton button){
        super(owner,title,ModalityType.DOCUMENT_MODAL);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setBounds((int)(this.getParent().getWidth()/2)-250,(int)(this.getParent().getHeight()/2)-100,500,330);
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
        sheduleCombo.addItem(Shedules.DAY.toString());
        sheduleCombo.addItem(Shedules.NIGHT.toString());
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

        JButton cancelB = new JButton("Отмена");
        cancelB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        diffButton = button;
        row5.add(diffButton);
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
    //получаем дату из соответствующего строкового поля объекта TableEntry:
    public Date parseDateGotFromForm(String time) {
        Date date = new Date();
        try {
            date = new SimpleDateFormat("hh.mm").parse(time);
        }catch (ParseException exc){
            exc.printStackTrace();
        }
        return date;
    }
}
class DifferentB extends JButton{
    DifferentB(String text){
        setText(text);
        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TableEntry addedTableEntry = new TableEntry();
                addedTableEntry = addedTableEntry.getTableEntryFromDialog();

                if(addedTableEntry.getTableEntryFromDialog().getName().equals("")){
                    JOptionPane.showMessageDialog(MainForm.timeTableEditor.getContentPane(),
                            "Имя расписания обязательно к заполнению!",
                            "Ошибка ввода", JOptionPane.INFORMATION_MESSAGE);
                }else
                    {
                switch(text){
                    case "Добавить":
                        {
                            //определяем есть ли такая запись в списке параметров и выводит предупреждение
                            if (addedTableEntry.isEntryPresentInList()) {
                                JOptionPane.showMessageDialog(MainForm.timeTableEditor.getContentPane(),
                                "Расписание с таким именем и режимом уже существует!",
                                "Ошибка ввода", JOptionPane.INFORMATION_MESSAGE);
                            }
                            else {
                                TableEntry te = addedTableEntry.getTableEntryFromDialog();
                                MainForm.tableEntryList.add(te);
                                UserTime ut = new UserTime(te.getName(),te.getShedule());
                                MainForm.userTimeList.add(ut);
                                MainForm.groupTimeList.add(ut);
                                getUsersTab().userTimeCombo.addItem(ut);
                                tableUpdate();
                                MainForm.timeTableEditor.dispose();
                            }
                            break;
                        }
                    case "Изменить":
                        {
                            //проверяем, не было ли изменено имя расписания в форме по сравнению с выбранной строкой таблицы (false - не изменено):
                            boolean option1 = addedTableEntry.isChanged(tt,addedTableEntry);
                            //проверка, если в таблице строка с таким именем:
                            boolean option2 = addedTableEntry.isEntryPresentInList();
                            if (option1&option2) {
                                JOptionPane.showMessageDialog(MainForm.timeTableEditor.getContentPane(),
                                        "Расписание с таким именем и режимом уже существует!",
                                        "Ошибка ввода", JOptionPane.INFORMATION_MESSAGE);
                            }
                            else {
                                tableModel.removeRow(tt.getSelectedRow());
                                MainForm.tableEntryList.add(addedTableEntry.getTableEntryFromDialog());
                                tableUpdate();
                                MainForm.timeTableEditor.dispose();
                            }
                        }
                }}
            }
        });
    }
}

