package by.alt.Object;

import by.alt.gui.DifferentB;
import by.alt.gui.MainForm;
import by.alt.gui.TimeTableEditor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class DoubleClickMouseListener extends MouseAdapter {

        private boolean singleClick  = true;
        private int doubleClickDelay = 300;
        private Timer timer;

        public DoubleClickMouseListener() {

            ActionListener actionListener = new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    timer.stop();
                    if (singleClick) {
                        singleClickHandler(e);
                    } else {
                        doubleClickHandler(e);
                    }
                }
            };
            timer = new Timer(doubleClickDelay, actionListener);
            timer.setRepeats(false);
        }
        public void mouseClicked(MouseEvent e) {

            if (e.getClickCount() == 1) {
                singleClick = true;
                timer.start();
            } else {
                singleClick = false;
            }
        }

        private void singleClickHandler(ActionEvent e) {
        }
        private void doubleClickHandler(ActionEvent e) {
            Frame[] frames = MainForm.getFrames();
            MainForm.timeTableEditor = new TimeTableEditor(frames[0],"Редактирование расписания", new DifferentB("Изменить"), MainForm.tt.getSelectedRow());
            MainForm.timeTableEditor.setVisible(true);
        }
}
