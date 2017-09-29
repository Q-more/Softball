package softBall;

import softBall.model.Fielding;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.*;

/**
 * @author Lucija Raženj
 * @version 1.0
 */
public class SwingFielding extends JFrame {


    private JTable table;
    private String[] names;
    private Map<String, Fielding> fielding;


    public SwingFielding(String[] names, Map<String, Fielding> fielding) {
        this.fielding = fielding;
        this.names = names;
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
        java.util.List<RowSorter.SortKey> sortKeys = new ArrayList<>();


        DefaultTableModel model = (DefaultTableModel) table.getModel();
        for (String name : names) {
            model.addColumn(name);
        }
        fielding.forEach((K, V) -> ((DefaultTableModel) table.getModel()).addRow(V.toArray()));
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
