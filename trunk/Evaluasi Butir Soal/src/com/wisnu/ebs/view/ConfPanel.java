/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wisnu.ebs.view;

import com.wisnu.ebs.add.TableCheckBox;
import com.wisnu.ebs.controller.ConfController;
import com.wisnu.ebs.event.ConfListener;
import com.wisnu.ebs.model.MainModel;
import java.awt.Color;
import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

/**
 *
 * @author Wisnu Wardoyo
 */
public class ConfPanel extends javax.swing.JPanel implements ConfListener {

    private MainModel model;
    private ConfController controller;
    private final String[] header = {"No", "Kompetensi", "KKM", "Siswa", "Soal", "Tipe", "Aktif"};
    private Object[][] dataTable;
    private NewFilePanel filePanel;
    private JFrame frame;

    public ConfPanel() {

    }

    public void setModel(MainModel model) {
        this.model = model;

        //Where should it goes;
        controller = new ConfController();
        controller.setModel(model);
        model.setConfListener(this);
        controller.onLoad();
    }

    @Override
    public void onLoad(MainModel model) {
        int row = this.model.getJumlahBerkas();
        int aktif = this.model.getBerkasAktif();
        dataTable = new Object[row][7];
        for (int i = 0; i < row; i++) {
            dataTable[i][0] = String.valueOf((i + 1));
            dataTable[i][1] = this.model.getKompetensi()[i];
            dataTable[i][2] = this.model.getKKM()[i];
            dataTable[i][3] = this.model.getJmlSiswa()[i];
            dataTable[i][4] = this.model.getJmlSoal()[i];
            dataTable[i][5] = this.model.getTipeSoal()[i].equals("4") ? "A,B,C,D" : "A,B,C,D,E";
            dataTable[i][6] = i == aktif ? true : false;
        }
        initComponents();
        labMataPelajaran.setText(this.model.getMaPel());
        labGuru.setText(this.model.getNamaGuru());
        labKelas.setText(this.model.getNamaKelas());
        labBerkas.setText(String.valueOf(this.model.getJumlahBerkas()));
        controller.setBerkasAktif(aktif);
        repaint();
    }

    @Override
    public void newFile() {
        filePanel = new NewFilePanel();
        int result = JOptionPane.showConfirmDialog(null, filePanel, "New File", JOptionPane.YES_NO_OPTION);
        if (result == 0) {
            if (filePanel.itemCheck()) {
                controller.tambahBerkas(filePanel);

            }
        }
    }

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

    public void setFrame(JFrame frame) {
        this.frame = frame;
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
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        labMataPelajaran = new javax.swing.JTextField();
        labGuru = new javax.swing.JTextField();
        labKelas = new javax.swing.JTextField();
        labBerkas = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new TableCheckBox();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();

        setBackground(new Color(255,255,255,30));

        jPanel1.setBackground(new Color(225,225,225,30));

        jPanel2.setBackground(new Color(225,225,225,20));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel1.setText("CONFIGURATION PANEL");

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel2.setText("APALAH");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel3.setBackground(new Color(225,225,225,20));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel3.setText("Mata Pelajaran");

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel4.setText("Nama Guru");

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel5.setText("Nama Kelas");

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel6.setText("Jumlah Berkas");

        labMataPelajaran.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        labMataPelajaran.setEnabled(false);

        labGuru.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        labGuru.setEnabled(false);

        labKelas.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        labKelas.setEnabled(false);

        labBerkas.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        labBerkas.setText("1");

        jButton1.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton1.setText("Tambah Data");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jScrollPane1.setPreferredSize(table.getPreferredSize());
        table.setModel(new DefaultTableModel(dataTable, header));
        setAllColumnWidth();

        table.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(table);

        jLabel8.setBackground(new Color(225,225,225,20));
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/edit.png"))); // NOI18N
        jLabel8.setToolTipText("Edit Nama Mata Pelajaran");
        jLabel8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel8.setOpaque(true);
        jLabel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel8MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel8MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel8MouseExited(evt);
            }
        });

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/edit.png"))); // NOI18N
        jLabel9.setToolTipText("Edit Nama Guru");
        jLabel9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel9MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel9MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel9MouseExited(evt);
            }
        });

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/edit.png"))); // NOI18N
        jLabel10.setToolTipText("Edit Nama Kelas");
        jLabel10.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel10MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel10MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel10MouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 481, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addGap(93, 93, 93)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(labBerkas)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(labKelas, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(labGuru, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(labMataPelajaran, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jButton1))
                .addContainerGap(102, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(labMataPelajaran, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(labGuru, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(labKelas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(labBerkas))
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel8MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseEntered
        // TODO add your handling code here:
        if (!labMataPelajaran.isEnabled()) {
            jLabel8.setIcon(new ImageIcon("./src/Resources/editHover.png"));
            frame.repaint();
        } else {
            jLabel8.setIcon(new ImageIcon("./src/Resources/OkHover.png"));
            frame.repaint();
        }
    }//GEN-LAST:event_jLabel8MouseEntered

    private void jLabel8MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseExited
        if (!labMataPelajaran.isEnabled()) {
            jLabel8.setIcon(new ImageIcon("./src/Resources/edit.png"));
            frame.repaint();
        } else {
            jLabel8.setIcon(new ImageIcon("./src/Resources/Ok.png"));
            frame.repaint();
        }
    }//GEN-LAST:event_jLabel8MouseExited

    private void tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMouseClicked
        int selected = table.getSelectedRow();
        int rowCount = table.getRowCount();

        table.getValueAt(selected, 6);
        for (int i = 0; i < rowCount; i++) {
            if (i != selected) {
                table.setValueAt(false, i, 6);
            }else{
                table.setValueAt(true, i, 6);
            }
        }
        controller.setBerkasAktif(selected);
    }//GEN-LAST:event_tableMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        controller.fireNewFile();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jLabel9MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseEntered
         if (!labGuru.isEnabled()) {
            jLabel9.setIcon(new ImageIcon("./src/Resources/editHover.png"));
            frame.repaint();
        } else {
            jLabel9.setIcon(new ImageIcon("./src/Resources/OkHover.png"));
            frame.repaint();
        }
    }//GEN-LAST:event_jLabel9MouseEntered

    private void jLabel9MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseExited
        if (!labGuru.isEnabled()) {
            jLabel9.setIcon(new ImageIcon("./src/Resources/edit.png"));
            frame.repaint();
        } else {
            jLabel9.setIcon(new ImageIcon("./src/Resources/Ok.png"));
            frame.repaint();
        }
    }//GEN-LAST:event_jLabel9MouseExited

    private void jLabel10MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseEntered
        if (!labKelas.isEnabled()) {
            jLabel10.setIcon(new ImageIcon("./src/Resources/editHover.png"));
            frame.repaint();
        } else {
            jLabel10.setIcon(new ImageIcon("./src/Resources/OkHover.png"));
            frame.repaint();
        }
    }//GEN-LAST:event_jLabel10MouseEntered

    private void jLabel10MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseExited
       if (!labKelas.isEnabled()) {
            jLabel10.setIcon(new ImageIcon("./src/Resources/edit.png"));
            frame.repaint();
        } else {
            jLabel10.setIcon(new ImageIcon("./src/Resources/Ok.png"));
            frame.repaint();
        }
    }//GEN-LAST:event_jLabel10MouseExited

    private void jLabel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseClicked
        if (!labMataPelajaran.isEnabled()) {
            jLabel8.setIcon(new ImageIcon("./src/Resources/Ok.png"));
            jLabel8.setToolTipText("Simpan ?");
            labMataPelajaran.setEnabled(true);
            frame.repaint();
        } else {
            jLabel8.setIcon(new ImageIcon("./src/Resources/edit.png"));
            jLabel8.setToolTipText("Edit Nama Mata Pelajaran");
            labMataPelajaran.setEnabled(false);
            frame.repaint();
            controller.changeProperty(1, labMataPelajaran.getText());
        }
    }//GEN-LAST:event_jLabel8MouseClicked

    private void jLabel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseClicked
        if (!labGuru.isEnabled()) {
            jLabel9.setIcon(new ImageIcon("./src/Resources/Ok.png"));
            jLabel9.setToolTipText("Simpan ?");
            labGuru.setEnabled(true);
            frame.repaint();
        } else {
            jLabel9.setIcon(new ImageIcon("./src/Resources/edit.png"));
            jLabel9.setToolTipText("Edit Nama Guru");
            labGuru.setEnabled(false);
            frame.repaint();
            controller.changeProperty(2, labGuru.getText());
        }
    }//GEN-LAST:event_jLabel9MouseClicked

    private void jLabel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseClicked
        if (!labKelas.isEnabled()) {
            jLabel10.setIcon(new ImageIcon("./src/Resources/Ok.png"));
            jLabel10.setToolTipText("Simpan ?");
            labKelas.setEnabled(true);
            frame.repaint();
        } else {
            jLabel10.setIcon(new ImageIcon("./src/Resources/edit.png"));
            jLabel10.setToolTipText("Edit Nama Mata Pelajaran");
            labKelas.setEnabled(false);
            frame.repaint();
            controller.changeProperty(3, labKelas.getText());
        }
    }//GEN-LAST:event_jLabel10MouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labBerkas;
    private javax.swing.JTextField labGuru;
    private javax.swing.JTextField labKelas;
    private javax.swing.JTextField labMataPelajaran;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables

}
