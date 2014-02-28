/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wisnu.ebs.view;

import com.wisnu.ebs.add.TableCellListener;
import com.wisnu.ebs.add.rowTable;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

/**
 *
 * @author Wisnu Citra
 */
public class AnsPanel extends javax.swing.JPanel  {
    private JList listRowHeader;
    private String[] rowHeader;
    private String[] colHeader;
    private String[][] dataTable;
    private int type = 0;
    
    private final boolean maintenance = true;

    public AnsPanel() {
        
    }

    public void setTable() {
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(colHeader);
        tableModel.setRowCount(rowHeader.length);
        tableModel.setDataVector(dataTable, colHeader);
        table = new JTable(tableModel);
        table.setRowHeight(20);
        table.setShowGrid(true);
        ((DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
        for (int i = 1; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(new TableCellListener(type));
        }
        setAllColumnWidth();
        scroll = new JScrollPane(table);
        //scroll.setPreferredSize(new Dimension(400, 200));
    }

    public void setRowHeader() {
        listRowHeader = new JList();
        listRowHeader.setListData(rowHeader);
        listRowHeader.setFixedCellWidth(100);
        listRowHeader.setFixedCellHeight(20);
        listRowHeader.setBackground(new java.awt.Color(214, 217, 223));
        listRowHeader.setCellRenderer(new rowTable(table));
        scroll.setRowHeaderView(listRowHeader);
        //System.out.println(table.getTableHeader().getBackground());
    }
    
    /**
     * Setting for Dynamic Column Width
     */
    public void setAllColumnWidth() {
        for (int i = 0; i < table.getColumnCount(); i++) {
            setColumnWidth(i);
        }
    }
    
    public void setColumnWidth(int columnIndex) {
        DefaultTableColumnModel columnModel = (DefaultTableColumnModel) table.getColumnModel();
        TableColumn tableColumn = columnModel.getColumn(columnIndex);
        int width = 0;
        int margin = 20;

        TableCellRenderer renderer = tableColumn.getHeaderRenderer();
        if (renderer == null) {
            renderer = table.getTableHeader().getDefaultRenderer();
        }
        Component comp = renderer.getTableCellRendererComponent(
                table, tableColumn.getHeaderValue(), false, false, 0, 0);
        width = comp.getPreferredSize().width;

        for (int i = 0; i < table.getRowCount(); i++) {
            renderer = table.getCellRenderer(i, columnIndex);
            comp = renderer.getTableCellRendererComponent(
                    table, table.getValueAt(i, columnIndex), false, false, i, columnIndex);
            int widthColumn = comp.getPreferredSize().width;
            width = Math.max(width, widthColumn);
        }

        width += margin;
        tableColumn.setPreferredWidth(width);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        scroll = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setBackground(new Color(225, 225, 225, 20));

        jPanel1.setBackground(new Color(225, 225, 225, 20));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, new java.awt.Color(0, 153, 0)));

        scroll.setForeground(new java.awt.Color(153, 255, 153));
        scroll.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        setTable();    setRowHeader();

        table.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        table.setCellSelectionEnabled(true);
        table.setDoubleBuffered(true);
        table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        scroll.setViewportView(table);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scroll, javax.swing.GroupLayout.DEFAULT_SIZE, 713, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scroll, javax.swing.GroupLayout.DEFAULT_SIZE, 447, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel2.setBackground(new Color(225, 225, 225, 20));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, new java.awt.Color(0, 153, 0)));
        jPanel2.setPreferredSize(new java.awt.Dimension(364, 105));

        jLabel1.setFont(new java.awt.Font("Comic Sans MS", 1, 24)); // NOI18N
        jLabel1.setText("ANSWER FORM");

        jLabel2.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel2.setText("Fill the blank cell with the answer, Ex : A, B , C or D");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 737, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane scroll;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables
    
    public JTable getTable(){
        return this.table;
    }
    public void setDataTable(String[][] dataTable){
        this.dataTable = dataTable;
        initComponents();
    }
    public void setRowHeader(String[] rowHeader){
        this.rowHeader = rowHeader;
    }
    public void setColHeader(String[] colHeader){
        this.colHeader = colHeader;
    }
    public void setType(int type){
        this.type = type;
    }
}
