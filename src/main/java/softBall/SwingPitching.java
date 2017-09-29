package softBall;

import softBall.model.Pitching;

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
public class SwingPitching extends JFrame {

    private JTable table;
    private Map<String, Pitching> pitchings;


    public SwingPitching(Map<String,Pitching> pitchings) {
        this.pitchings=pitchings;

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

                    pitchings.forEach((K, V) -> ((DefaultTableModel) table.getModel()).addRow(V.toArray()));

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
        model.addColumn("w");
        model.addColumn("l");
        model.addColumn("sv");
        model.addColumn("ip");
        model.addColumn("bf");
        model.addColumn("ball");
        model.addColumn("str");
        model.addColumn("pit");
        model.addColumn("r");
        model.addColumn("ra");
        model.addColumn("er");
        model.addColumn("era");
        model.addColumn("ers9");
        model.addColumn("k");
        model.addColumn("kc");
        model.addColumn("ks");
        model.addColumn("h");
        model.addColumn("bb");
        model.addColumn("ibb");
        model.addColumn("kbb");
        model.addColumn("kgi");
        model.addColumn("bbgi");
        model.addColumn("hgi");
        model.addColumn("hb");
        model.addColumn("bk");
        model.addColumn("wp");
        model.addColumn("hr");
        model.addColumn("whip");
        model.addColumn("obp");
        model.addColumn("baa");
        model.addColumn("g0");
        model.addColumn("a0");
        model.addColumn("fps");
        model.addColumn("fbp");
        model.addColumn("fpsPercentage");


        int columnIndexToSort = 14;
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
