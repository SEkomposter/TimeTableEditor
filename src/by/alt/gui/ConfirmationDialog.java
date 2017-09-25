package by.alt.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConfirmationDialog extends WarningDialog{
    private static boolean isConfirmed;
    private JButton yesB;
    private JButton noB;
    private static int width = 400;
    private static int height = 100;
    ConfirmationDialog(JDialog own, String caption, String label, ModalityType mod){
        super(own,caption,label,mod);
        yesB = new JButton("Yes");
        yesB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isConfirmed = true;
                dispose();
            }
        });
        noB = new JButton("No");
        noB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isConfirmed = true;
                dispose();
            }
        });
    }
    public boolean isConfirmed(){
        return isConfirmed;
    }
}
