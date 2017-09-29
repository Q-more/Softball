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
public class SwingBatting extends JFrame{

    private JTable table;
    private Map<String, Batting> battings;

    public SwingBatting(Map<String, Batting> battings) {
        this.battings=battings;

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

                    battings.forEach((K, V) -> ((DefaultTableModel) table.getModel()).addRow(V.toArray()));


                }).start();

            }
        });

    }

    private JTable createBattingTable() {
        JTable table = new JTable();
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(sorter);
        List<RowSorter.SortKey> sortKeys = new ArrayList<>();


        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.addColumn("jerseyNumber");
        model.addColumn("name");
        model.addColumn("G");
        model.addColumn("PA");
        model.addColumn("AB");
        model.addColumn("R");
        model.addColumn("H");
        model.addColumn("B");
        model.addColumn("B1");
        model.addColumn("B2");
        model.addColumn("B3");
        model.addColumn("HR");
        model.addColumn("RBI");
        model.addColumn("AVG");
        model.addColumn("BB");
        model.addColumn("Kc");
        model.addColumn("Ks");
        model.addColumn("SO");
        model.addColumn("HBP");
        model.addColumn("SB");
        model.addColumn("CS");
        model.addColumn("SCB");
        model.addColumn("SF");
        model.addColumn("SAC");
        model.addColumn("RPA");
        model.addColumn("OBP");
        model.addColumn("OBPE");
        model.addColumn("OPS");
        model.addColumn("SLG");
        model.addColumn("GPA");
        model.addColumn("CT_per");
        model.addColumn("CT2_per");
        model.addColumn("ROE");
        model.addColumn("FC");
        model.addColumn("CI");
        model.addColumn("GDP");
        model.addColumn("GTP");
        model.addColumn("AB_RSP");
        model.addColumn("H_RSP");
        model.addColumn("BA_RSP");


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
