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
    private Map<String, Fielding> fielding;


    public SwingFielding(Map<String, Fielding> fielding) {
        this.fielding = fielding;

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        initGui();
    }


    private void initGui() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        getContentPane().add(mainPanel);


        table = createBattingTable();
        JScrollPane scrollPane = new JScrollPane(table);
        mainPanel.add(scrollPane, BorderLayout.CENTER);


        JButton btn = new JButton();
        mainPanel.add(btn, BorderLayout.SOUTH);
        btn.setAction(new AbstractAction("Load from CVS file") {

            @Override
            public void actionPerformed(ActionEvent e) {

                new Thread(() -> {

                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    for (int i = model.getRowCount() - 1; i >= 0; i--) {
                        model.removeRow(i);
                    }

                    fielding.forEach((K, V) -> ((DefaultTableModel) table.getModel()).addRow(V.toArray()));


                }).start();

            }
        });

    }

    private JTable createBattingTable() {
        JTable table = new JTable();
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(sorter);
        java.util.List<RowSorter.SortKey> sortKeys = new ArrayList<>();


        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.addColumn("jerseyNumber");
        model.addColumn("name");
        model.addColumn("g");
        model.addColumn("et");
        model.addColumn("ef");
        model.addColumn("err");
        model.addColumn("po");
        model.addColumn("a");
        model.addColumn("sba");
        model.addColumn("cs");
        model.addColumn("dp");
        model.addColumn("tp");
        model.addColumn("pb");
        model.addColumn("pkf");
        model.addColumn("pk");
        model.addColumn("fp");

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
