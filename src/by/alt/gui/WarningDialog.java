package by.alt.gui;


import javax.swing.*;
import java.awt.*;

public class WarningDialog extends JDialog{
    WarningDialog(JDialog own, String caption, String label, ModalityType mod){
        super(own,caption,mod);
        setBounds((int)own.getBounds().getX()+100,(int)own.getBounds().getY()+100,250,100);
        add(new JLabel(label),BorderLayout.CENTER);
    }
}
