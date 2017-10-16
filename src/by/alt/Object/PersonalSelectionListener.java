package by.alt.Object;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;

public class PersonalSelectionListener implements TreeSelectionListener{
    public void valueChanged(TreeSelectionEvent e)
    {
        JTree tree = (JTree)e.getSource();
        // Объекты-пути ко всем выделенным узлам дерева
        TreePath[] paths = e.getPaths();
        // Список выделенных элементов в пути
        TreePath[] selected = tree.getSelectionPaths();
        int[] rows = tree.getSelectionRows();
    }
}
