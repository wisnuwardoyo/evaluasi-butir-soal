/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wisnu.ebs.view;

import com.wisnu.ebs.add.ColoredTable;
import com.wisnu.ebs.add.rowTable;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

/**
 *
 * @author Wisnu Citra
 */
public class ResultPanel extends javax.swing.JPanel {

    private final String colHeader1[] = {"T.Kesukaran", "Keterangan", "Daya Beda", "Keterangan", "Homogenitas", "Keterangan"};
    String colHeader2[];
    private final String colHeader3[] = {"Jumlah Benar", "Jumlah Salah", "Skor Dibobot", "KKM"};
    private final String colHeader4[] = {"Tingkat Kesukaran", "Daya Beda", "Pengecoh", "Homogenitas", "Keterangan"};
    String[][][] dataTable = new String[4][][];
    String[][] rowHeader = new String[4][];
    JList[] listRowHeader = new JList[4];
    private ToolPanel toolPanelSC1, toolPanelSC2;
    private JFrame frame;

    public ResultPanel() {

    }

    /**
     * View
     */
    public void setTable() {
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(colHeader1);
        tableModel.setRowCount(rowHeader[0].length);
        tableModel.setDataVector(dataTable[0], colHeader1);
        table = new JTable(tableModel);
        table.setRowHeight(20);
        table.setShowGrid(true);
        ((DefaultTableCellRenderer) table.getTableHeader().
                getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);

        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int col = 0; col < 4; col++) {
            table.getColumnModel().getColumn(col).setCellRenderer(rightRenderer);
        }

        scroll = new JScrollPane(table);
        //scroll.setPreferredSize(new Dimension(400, 200));
    }

    public void setRowHeader() {
        listRowHeader[0] = new JList();
        listRowHeader[0].setListData(rowHeader[0]);
        listRowHeader[0].setFixedCellWidth(60);
        listRowHeader[0].setFixedCellHeight(20);
        listRowHeader[0].setBackground(new java.awt.Color(214, 217, 223));
        listRowHeader[0].setCellRenderer(new rowTable(table));
        scroll.setRowHeaderView(listRowHeader[0]);
        //System.out.println(table.getTableHeader().getBackground());
    }

    public void setTable2() {
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(colHeader2);
        tableModel.setRowCount(rowHeader[1].length);
        tableModel.setDataVector(dataTable[1], colHeader2);
        table2 = new JTable(tableModel);
        table2.setRowHeight(20);
        table2.setShowGrid(true);
        ((DefaultTableCellRenderer) table2.getTableHeader().
                getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
        scroll2 = new JScrollPane(table2);
        //scroll.setPreferredSize(new Dimension(400, 200));
    }

    public void setRowHeader2() {
        listRowHeader[1] = new JList();
        listRowHeader[1].setListData(rowHeader[1]);
        listRowHeader[1].setFixedCellWidth(60);
        listRowHeader[1].setFixedCellHeight(20);
        listRowHeader[1].setBackground(new java.awt.Color(214, 217, 223));
        listRowHeader[1].setCellRenderer(new rowTable(table2));
        scroll2.setRowHeaderView(listRowHeader[1]);
        //System.out.println(table.getTableHeader().getBackground());
    }

    public void setTable3() {
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(colHeader3);
        tableModel.setRowCount(rowHeader[2].length);
        tableModel.setDataVector(dataTable[2], colHeader3);
        //new ColoredTable(tableModel);
        table3 = new ColoredTable(tableModel);

        table3.setRowHeight(20);
        table3.setShowGrid(true);
        ((DefaultTableCellRenderer) table3.getTableHeader().
                getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
        setAllColumnWidth();
        scroll3 = new JScrollPane(table3);
        //scroll.setPreferredSize(new Dimension(400, 200));
    }

    public void setRowHeader3() {
        listRowHeader[2] = new JList();
        listRowHeader[2].setListData(rowHeader[2]);
        listRowHeader[2].setFixedCellWidth(100);
        listRowHeader[2].setFixedCellHeight(20);
        listRowHeader[2].setBackground(new java.awt.Color(214, 217, 223));
        listRowHeader[2].setCellRenderer(new rowTable(table3) {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JTableHeader header = table.getTableHeader();
                label.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(204, 204, 204), null, null));
                label.setHorizontalAlignment(JLabel.LEFT);
                label.setForeground(header.getForeground());
                label.setBackground(header.getBackground().brighter());
                label.setFont(header.getFont());
                label.setText(value.toString().toUpperCase());
                return label;
            }

        });
        scroll3.setRowHeaderView(listRowHeader[2]);
        //System.out.println(table.getTableHeader().getBackground());
    }
    
    public void setAllColumnWidth() {
        for (int i = 0; i < table3.getColumnCount(); i++) {
            setColumnWidth(i);
        }
    }

    public void setColumnWidth(int columnIndex) {
        DefaultTableColumnModel columnModel = (DefaultTableColumnModel) table3.getColumnModel();
        TableColumn tableColumn = columnModel.getColumn(columnIndex);
        int width = 0;
        int margin = 5;

        TableCellRenderer renderer = tableColumn.getHeaderRenderer();
        if (renderer == null) {
            renderer = table3.getTableHeader().getDefaultRenderer();
        }
        Component comp = renderer.getTableCellRendererComponent(
                table3, tableColumn.getHeaderValue(), false, false, 0, 0);
        width = comp.getPreferredSize().width;

        for (int i = 0; i < table3.getRowCount(); i++) {
            renderer = table3.getCellRenderer(i, columnIndex);
            comp = renderer.getTableCellRendererComponent(
                    table3, table3.getValueAt(i, columnIndex), false, false, i, columnIndex);
            int widthColumn = comp.getPreferredSize().width;
            width = Math.max(width, widthColumn);
        }

        width += margin;
        tableColumn.setPreferredWidth(width);
    }
    
    public void setTable4() {
        DefaultTableModel tableModel = new DefaultTableModel() {

            @Override
            public boolean isCellEditable(int i, int i1) {
                return false;
            }

        };
        tableModel.setColumnIdentifiers(colHeader4);
        tableModel.setRowCount(rowHeader[3].length);
        tableModel.setDataVector(dataTable[3], colHeader4);
        //new ColoredTable(tableModel);
        table4 = new JTable(tableModel);
        table4.setRowHeight(20);
        table4.setShowGrid(true);
        ((DefaultTableCellRenderer) table4.getTableHeader().
                getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
        scroll4 = new JScrollPane(table4);
        //scroll.setPreferredSize(new Dimension(400, 200));
    }

    public void setRowHeader4() {
        listRowHeader[3] = new JList();
        listRowHeader[3].setListData(rowHeader[3]);
        listRowHeader[3].setFixedCellWidth(60);
        listRowHeader[3].setFixedCellHeight(20);
        listRowHeader[3].setBackground(new java.awt.Color(214, 217, 223));
        listRowHeader[3].setCellRenderer(new rowTable(table4));
        scroll4.setRowHeaderView(listRowHeader[3]);
        //System.out.println(table.getTableHeader().getBackground());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel10 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        labRata = new javax.swing.JLabel();
        labSimpangan = new javax.swing.JLabel();
        labKorelasi = new javax.swing.JLabel();
        labReabilitas = new javax.swing.JLabel();
        scroll4 = new javax.swing.JScrollPane();
        table4 = new javax.swing.JTable();
        toolPanel1 = this.toolPanelSC1;
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        scroll = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        scroll2 = new javax.swing.JScrollPane();
        table2 = new javax.swing.JTable();
        jPanel18 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        scroll3 = new javax.swing.JScrollPane();
        table3 = new javax.swing.JTable();
        jPanel9 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();

        setBackground(new Color(225,225,225,30));

        jTabbedPane1.setBackground(new Color(225,225,225,30));
        jTabbedPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTabbedPane1MouseReleased(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane1MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTabbedPane1MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTabbedPane1MousePressed(evt);
            }
        });
        jTabbedPane1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jTabbedPane1MouseMoved(evt);
            }
        });

        jPanel10.setBackground(new Color(225,225,225,30));

        jPanel15.setBackground(new Color(225,225,225,20));
        jPanel15.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, new java.awt.Color(0, 204, 51)));

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(0, 102, 102));
        jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/result_icon.png"))); // NOI18N
        jLabel23.setText("REKAPITULASI SOAL PILIHAN GANDA");

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, 866, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 58, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel16.setBackground(new Color(225,225,225,20));
        jPanel16.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, new java.awt.Color(0, 204, 51)));

        jPanel17.setBackground(new Color(225,225,255,20));
        jPanel17.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Keterangan Tes", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Comic Sans MS", 1, 12), new java.awt.Color(0, 102, 102))); // NOI18N

        jLabel3.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 51, 51));
        jLabel3.setText("Nilai Rata-rata");

        jLabel4.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 51, 51));
        jLabel4.setText("Nilai Simpangan Baku");

        jLabel5.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 51, 51));
        jLabel5.setText("Nilai Korelasi XY");

        jLabel6.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 51, 51));
        jLabel6.setText("Reabilitas TES");

        labRata.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        labRata.setForeground(new java.awt.Color(0, 102, 102));
        labRata.setText("jLabel7");

        labSimpangan.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        labSimpangan.setForeground(new java.awt.Color(0, 102, 102));
        labSimpangan.setText("jLabel7");

        labKorelasi.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        labKorelasi.setForeground(new java.awt.Color(0, 102, 102));
        labKorelasi.setText("jLabel7");

        labReabilitas.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        labReabilitas.setForeground(new java.awt.Color(0, 102, 102));
        labReabilitas.setText("jLabel7");

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(39, 39, 39)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labReabilitas)
                    .addComponent(labKorelasi)
                    .addComponent(labSimpangan)
                    .addComponent(labRata))
                .addContainerGap(65, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(labRata))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(labSimpangan))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(labKorelasi))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(labReabilitas))
                .addContainerGap(71, Short.MAX_VALUE))
        );

        setTable4();  setRowHeader4();

        table4.setColumnSelectionAllowed(true);
        table4.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        scroll4.setViewportView(table4);

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scroll4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addGap(0, 198, Short.MAX_VALUE)
                        .addComponent(toolPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addComponent(scroll4, javax.swing.GroupLayout.DEFAULT_SIZE, 402, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(toolPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Rekap Analisa", jPanel10);

        jPanel2.setBackground(new Color(225,225,225,30));

        jPanel4.setBackground(new Color(225,225,255,20));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, new java.awt.Color(0, 204, 51)));

        setTable();  setRowHeader();

        table.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        scroll.setViewportView(table);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scroll, javax.swing.GroupLayout.PREFERRED_SIZE, 523, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scroll, javax.swing.GroupLayout.DEFAULT_SIZE, 406, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.setBackground(new Color(225,225,255,20));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, new java.awt.Color(0, 204, 51)));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 102));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/result_icon.png"))); // NOI18N
        jLabel1.setText("ANALISA SOAL T. Kesukaran , Daya Beda, Korelasi dan Reabilitas Tes (Dalam Angka)");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 866, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 59, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Analisa Soal", jPanel2);

        jPanel3.setBackground(new Color(225,225,225,30));

        jPanel5.setBackground(new Color(225,225,255,20));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, new java.awt.Color(0, 204, 51)));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 102, 102));
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/result_icon.png"))); // NOI18N
        jLabel7.setText("KUALITAS DAN KEEFEKTIFAN PENGECOH");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel6.setBackground(new Color(225,225,255,20));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, new java.awt.Color(0, 204, 51)));

        setTable2(); setRowHeader2();

        table2.setCellSelectionEnabled(true);
        table2.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        scroll2.setViewportView(table2);

        jPanel18.setBackground(new Color(225,225,225,20));
        jPanel18.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Ketrangan", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Comic Sans MS", 1, 14))); // NOI18N

        jLabel9.setFont(new java.awt.Font("Comic Sans MS", 1, 12)); // NOI18N
        jLabel9.setText("**");

        jLabel10.setFont(new java.awt.Font("Comic Sans MS", 1, 12)); // NOI18N
        jLabel10.setText("++");

        jLabel11.setFont(new java.awt.Font("Comic Sans MS", 1, 12)); // NOI18N
        jLabel11.setText("+");

        jLabel12.setFont(new java.awt.Font("Comic Sans MS", 1, 12)); // NOI18N
        jLabel12.setText("-");

        jLabel13.setFont(new java.awt.Font("Comic Sans MS", 1, 12)); // NOI18N
        jLabel13.setText("- -");

        jLabel14.setFont(new java.awt.Font("Comic Sans MS", 1, 12)); // NOI18N
        jLabel14.setText("- - -");

        jLabel15.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N
        jLabel15.setText("Kunci Jawaban");

        jLabel16.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N
        jLabel16.setText("Baik Sekali");

        jLabel17.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N
        jLabel17.setText("Baik");

        jLabel18.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N
        jLabel18.setText("Kurang Baik");

        jLabel19.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N
        jLabel19.setText("Buruk");

        jLabel20.setFont(new java.awt.Font("Comic Sans MS", 0, 12)); // NOI18N
        jLabel20.setText("Sangat Buruk");

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13)
                    .addComponent(jLabel14))
                .addGap(27, 27, 27)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel20)
                    .addComponent(jLabel19)
                    .addComponent(jLabel18)
                    .addComponent(jLabel17)
                    .addComponent(jLabel16)
                    .addComponent(jLabel15))
                .addContainerGap(43, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jLabel18))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jLabel19))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jLabel20))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(scroll2, javax.swing.GroupLayout.PREFERRED_SIZE, 632, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scroll2, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 431, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Analisa Pengecoh", jPanel3);

        jPanel7.setBackground(new Color(225,225,225,30));

        jPanel8.setBackground(new Color(225,225,225,20));
        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, new java.awt.Color(0, 153, 51)));

        setTable3();  setRowHeader3();

        table3.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        table3.setCellSelectionEnabled(true);
        table3.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        scroll3.setViewportView(table3);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scroll3, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scroll3, javax.swing.GroupLayout.DEFAULT_SIZE, 406, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel9.setBackground(new Color(225,225,225,20));
        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, new java.awt.Color(0, 153, 51)));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 102, 102));
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/result_icon.png"))); // NOI18N
        jLabel8.setText("PENGELOMPOKAN KEMAMPUAN SISWA");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 866, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 58, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Pengelompokan Siswa", jPanel7);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTabbedPane1MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane1MouseMoved
        if (frame != null) {
            frame.repaint();
        }
    }//GEN-LAST:event_jTabbedPane1MouseMoved

    private void jTabbedPane1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane1MousePressed
        if (frame != null) {
            frame.repaint();
        }
    }//GEN-LAST:event_jTabbedPane1MousePressed

    private void jTabbedPane1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane1MouseExited
        if (frame != null) {
            frame.repaint();
        }
    }//GEN-LAST:event_jTabbedPane1MouseExited

    private void jTabbedPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane1MouseClicked
        // TODO add your handling code here:
        if (frame != null) {
            frame.repaint();
        }
    }//GEN-LAST:event_jTabbedPane1MouseClicked

    private void jTabbedPane1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane1MouseReleased
        if (frame != null) {
            frame.repaint();
        }
    }//GEN-LAST:event_jTabbedPane1MouseReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel labKorelasi;
    private javax.swing.JLabel labRata;
    private javax.swing.JLabel labReabilitas;
    private javax.swing.JLabel labSimpangan;
    private javax.swing.JScrollPane scroll;
    private javax.swing.JScrollPane scroll2;
    private javax.swing.JScrollPane scroll3;
    private javax.swing.JScrollPane scroll4;
    private javax.swing.JTable table;
    private javax.swing.JTable table2;
    private javax.swing.JTable table3;
    private javax.swing.JTable table4;
    private com.wisnu.ebs.view.ToolPanel toolPanel1;
    // End of variables declaration//GEN-END:variables

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public void setColHeader2(String[] colHeader2) {
        this.colHeader2 = colHeader2;
    }

    public void setDataTable(String[][][] dataTable) {
        this.dataTable = dataTable;
    }

    public void setRowHeader(String[][] rowHeader) {
        this.rowHeader = rowHeader;
    }

    public void setKeterangan(String[] data) {
        labRata.setText(data[4]);
        labSimpangan.setText(data[1]);
        labKorelasi.setText(data[2]);
        labReabilitas.setText(data[3]);
    }

    public JTable getTable1() {
        return this.table;
    }

    public JTable getTable2() {
        return this.table2;
    }

    public JTable getTable3() {
        return this.table3;
    }

    public JTable getTable4() {
        return this.table4;
    }

    public void setToolPanel(ToolPanel panel) {
        this.toolPanelSC1 = panel;
        
        initComponents();
    }
}