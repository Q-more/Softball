package softBall;

import softBall.model.Batting;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @author Lucija Raženj
 * @version 1.0
 */
public class SwingBatting extends JFrame {

    private JTable table;
    private final String[] colums;
    private final Map<String, Batting> battings;

    public SwingBatting(String[] colums, Map<String, Batting> battings) {
        this.battings=battings;
        this.colums = colums;
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        initGui();
    }


    private void initGui() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        getContentPane().add(mainPanel);

        table = createBattingTable();
        JScrollPane scrollPane = new JScrollPane(table);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

    }

    private JTable createBattingTable() {
        JTable table = new JTable();
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(sorter);
        List<RowSorter.SortKey> sortKeys = new ArrayList<>();
        
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        for (String colum : colums) {
            model.addColumn(colum);
        }
        battings.forEach((K, V) -> {
            Object[] array = V.toArray();
            ((DefaultTableModel) table.getModel()).addRow(array);
        });

        int columnIndexToSort = 1;
        sortKeys.add(new RowSorter.SortKey(columnIndexToSort, SortOrder.ASCENDING));

        sorter.setSortKeys(sortKeys);
        sorter.sort();

        return table;
    }

    private TableColumn createColumn(String s) {
        TableColumn column = new TableColumn();
        column.setHeaderValue(s);
        column.setResizable(true);
        return column;
    }
}
