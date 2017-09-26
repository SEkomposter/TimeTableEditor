package by.alt.gui;


import javax.swing.*;
import java.awt.*;

public class WarningDialog extends JDialog{
    private static int width = 250;
    private static int height = 100;
    WarningDialog(){}
    WarningDialog(JDialog own, String caption, String label, ModalityType mod){
        super(own,caption,mod);
        setBounds((int)own.getBounds().getX()+100,(int)own.getBounds().getY()+100,width,height);
        add(new JLabel(label),BorderLayout.CENTER);
    }

}
