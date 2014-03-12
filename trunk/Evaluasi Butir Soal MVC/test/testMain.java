
import javax.swing.JTable;

/**
 * @author echo
 * 
*/
public class testMain extends javax.swing.JFrame {

   
    public static void main(String[] args) {

        int rows = 10;
        int cols = 5;
        JTable table = new JTable(rows, cols);

        // Enable the ability to select a single cell
        table.setColumnSelectionAllowed(true);
        table.setRowSelectionAllowed(true);

        if (table.getCellEditor() != null) {
            table.getCellEditor().stopCellEditing();
        }

    }
}
