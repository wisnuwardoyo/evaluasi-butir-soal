/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wisnu.ebs.view;

import com.wisnu.ebs.add.ImageBackgroundPanel;
import com.wisnu.ebs.controller.MainController;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Image;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Wisnu Wardoyo <mas.wisnu99@gmail.com>
 */
public class MainFrame extends javax.swing.JFrame {

    /**
     * Instance Variable
     */
    private boolean isOpen = false;
    private boolean isFromFile = false;
    private boolean isNewDocument = false;
    private final Image image = new ImageIcon("./src/Resources/background2.jpg").getImage();
    MainController controllerUtama;

    public MainFrame() {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        } 
        System.out.println("Program Started");
        initComponents();
        this.setExtendedState(MAXIMIZED_BOTH);
        itemCheck(false);
    }

    public void openDocumentAction() {
        openDialog.setAcceptAllFileFilterUsed(false);
        openDialog.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File file) {
                if (file.isDirectory()) {
                    return true;
                }
                String f = file.getName().toUpperCase();
                return f.endsWith(".RMD");
            }

            @Override
            public String getDescription() {
                return "RMD File";
            }
        });
        openDialog.setCurrentDirectory(new File(openDialog.getCurrentDirectory().getAbsoluteFile().toString() + "\\anisso\\"));
        int returnVal = openDialog.showDialog(new JDialog(), "Open");
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                System.out.println("Opening File : " + openDialog.getSelectedFile().getCanonicalPath().toString());
                controllerUtama.openDocumentAction(openDialog.getSelectedFile().getCanonicalPath().toString());
                isFromFile = true;
                isNewDocument = false;
                this.repaint();
            } catch (IOException ex) {
                controllerUtama.fireErrorMessage(0, 99, "");
                isFromFile = false;
            }
        } else {
        }
    }

    public void saveDocumentAction() {
        saveDialog.setAcceptAllFileFilterUsed(false);
        saveDialog.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File file) {
                if (file.isDirectory()) {
                    return true;
                }
                String f = file.getName().toUpperCase();
                return f.endsWith(".RMD");
            }

            @Override
            public String getDescription() {
                return "RMD File";
            }
        });
        saveDialog.setCurrentDirectory(new File(saveDialog.getCurrentDirectory().getAbsoluteFile().toString() + "\\anisso\\"));
        int returnVal = saveDialog.showDialog(new JDialog(), "Save");
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                String path = saveDialog.getSelectedFile().getCanonicalPath().toString();
                if (new File(path).exists() || new File(path + ".rmd").exists()) {
                    int confirm = JOptionPane.showConfirmDialog(this, "Do You Want to Overwrite /n " + path + "?", "Save File", JOptionPane.YES_NO_OPTION);
                    if (confirm == 0) {
                        path = path.contains(".rmd") ? path.replace(".rmd", "") : path;
                        controllerUtama.saveDocumentAction(path);
                        this.repaint();
                    }
                } else {
                    path = path.contains(".rmd") ? path.replace(".rmd", "") : path;
                    controllerUtama.saveDocumentAction(path);
                    this.repaint();
                }

            } catch (IOException ex) {
                controllerUtama.fireErrorMessage(1, 99, ex.toString());
            }
        }
    }

    public void newDocumentAction() {
        if (isFromFile) {
            int save = JOptionPane.showConfirmDialog(null, "Anda telah membuka / membuat dokumen"
                    + "\nApakah Anda akan menyimpannya?", "Warning", JOptionPane.YES_NO_CANCEL_OPTION);
            System.out.println(save);
            if (save == 0) {
                isFromFile = false;
                controllerUtama.saveDocumentAction(controllerUtama.getPath().replace(".rmd", "").replace(".RMD", ""));
                controllerUtama.createNewDocument();
            } else if (save == 2) {

            } else {
                controllerUtama.createNewDocument();
            }
        } else if (isNewDocument) {
            int save = JOptionPane.showConfirmDialog(null, "Anda telah membuka / membuat dokumen"
                    + "\nApakah Anda akan menyimpannya?", "Warning", JOptionPane.YES_NO_CANCEL_OPTION);
            if (save == 0) {
                isNewDocument = false;
                saveDocumentAction();
                controllerUtama.createNewDocument();
            } else if (save == 2) {

            } else {
                controllerUtama.createNewDocument();
            }
        } else {
            controllerUtama.createNewDocument();
        }
    }

    public final void itemCheck(boolean cek) {
        if (cek) {
            menu_item_save.setEnabled(true);
            menu_item_close.setEnabled(true);
            button_sc_key.setEnabled(true);
            button_sc_ans.setEnabled(true);
            button_sc_res.setEnabled(true);
            button_sc_print.setEnabled(true);
            menu_item_config.setEnabled(true);
        } else {
            menu_item_save.setEnabled(false);
            menu_item_close.setEnabled(false);
            button_sc_key.setEnabled(false);
            button_sc_ans.setEnabled(false);
            button_sc_res.setEnabled(false);
            button_sc_print.setEnabled(false);
            menu_item_config.setEnabled(false);
        }
    }

    public void setFrameTitle(String title) {
        this.setTitle(title);
    }

    public void exit(int i) {
        if (isFromFile) {
            int save = JOptionPane.showConfirmDialog(null, "Anda telah membuka / membuat dokumen"
                    + "\nApakah Anda akan menyimpannya?", "Warning", JOptionPane.YES_NO_CANCEL_OPTION);
            System.out.println(save);
            if (save == 0) {
                isFromFile = false;
                controllerUtama.saveDocumentAction(controllerUtama.getPath().replace(".rmd", "").replace(".RMD", ""));
                if (i == 1) {
                    System.out.println("Program Stopped");
                    System.exit(0);
                }
            } else if (save == 2) {

            } else {
                if (i == 1) {
                    System.out.println("Program Stopped");
                    System.exit(0);
                }
            }
        } else if (isNewDocument) {
            int save = JOptionPane.showConfirmDialog(null, "Anda telah membuka / membuat dokumen"
                    + "\nApakah Anda akan menyimpannya?", "Warning", JOptionPane.YES_NO_CANCEL_OPTION);
            if (save == 0) {
                isNewDocument = false;
                saveDocumentAction();
                if (i == 1) {
                    System.out.println("Program Stopped");
                    System.exit(0);
                }
            } else if (save == 2) {

            } else {
                if (i == 1) {
                    System.out.println("Program Stopped");
                    System.exit(0);
                }
            }
        } else {
            if (i == 1) {
                System.out.println("Program Stopped");
                System.exit(0);
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        openDialog = new javax.swing.JFileChooser();
        saveDialog = new javax.swing.JFileChooser();
        mainScrollPane = new javax.swing.JScrollPane();
        panel_home = new ImageBackgroundPanel(image);
        contentScrollPane = new javax.swing.JScrollPane();
        jPanel10 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        menuScrollPane = new javax.swing.JScrollPane();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        button_sc_key = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        button_sc_ans = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        button_sc_res = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        button_sc_print = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jButton5 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        menuFile = new javax.swing.JMenu();
        menu_item_new = new javax.swing.JMenuItem();
        menu_item_open = new javax.swing.JMenuItem();
        menu_item_save = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        menu_item_close = new javax.swing.JMenuItem();
        menu_item_exit = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        menu_item_config = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jMenuItem2 = new javax.swing.JMenuItem();

        saveDialog.setDialogType(javax.swing.JFileChooser.SAVE_DIALOG);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("AnisSo V.2.0.1");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                formMouseMoved(evt);
            }
        });

        panel_home.setBackground(new java.awt.Color(0, 51, 51));
        panel_home.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        contentScrollPane.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, new java.awt.Color(102, 255, 102)));
        contentScrollPane.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                contentScrollPaneMouseMoved(evt);
            }
        });

        jPanel10.setBackground(new Color(225,225,225,30)
        );

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 40)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 102));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setText("PROGRAM ANALISIS BUTIR SOAL ");

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Comic Sans MS", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 102, 102));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel3.setText("Tindakan :");

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/wisnu.png"))); // NOI18N

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 153, 153));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel5.setText("PILIHAN GANDA");

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/new document-icon.png"))); // NOI18N
        jLabel4.setToolTipText("New Document");
        jLabel4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel4MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel4MouseExited(evt);
            }
        });

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/open-icon.png"))); // NOI18N
        jLabel6.setToolTipText("Open Document");
        jLabel6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel6MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel6MouseExited(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 102, 102));
        jLabel7.setText("Dokumen Baru");

        jLabel8.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 102, 102));
        jLabel8.setText("Buka Dokumen");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel9.setText("© Wisnu Wardoyo");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel10.setText("Builded with Java SDK 1.7");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel11.setText("Tekan F1 Untuk Bantuan");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1)
                            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel10Layout.createSequentialGroup()
                                    .addGap(31, 31, 31)
                                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel10Layout.createSequentialGroup()
                                            .addGap(20, 20, 20)
                                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(57, 57, 57)
                                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel10Layout.createSequentialGroup()
                                            .addComponent(jLabel4)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel10)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel11)
                            .addComponent(jLabel9))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(109, 109, 109)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 418, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 100, Short.MAX_VALUE)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addContainerGap())
        );

        contentScrollPane.setViewportView(jPanel10);

        menuScrollPane.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jPanel3.setBackground(new Color (225,225,225,30));
        jPanel3.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jPanel3MouseMoved(evt);
            }
        });

        jPanel4.setBackground(new Color(225,225,225,20));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, new java.awt.Color(0, 153, 0)));

        button_sc_key.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        button_sc_key.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/key_icon.png"))); // NOI18N
        button_sc_key.setText("KUNCI JAWABAN");
        button_sc_key.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        button_sc_key.setOpaque(false);
        button_sc_key.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                button_sc_keyMouseReleased(evt);
            }
        });
        button_sc_key.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_sc_keyActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(button_sc_key, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(button_sc_key, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        jPanel5.setBackground(new Color(225,225,225,20));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, new java.awt.Color(0, 153, 0)));

        button_sc_ans.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        button_sc_ans.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/answer_icon.png"))); // NOI18N
        button_sc_ans.setText("LEMBAR JAWABAN");
        button_sc_ans.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        button_sc_ans.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_sc_ansActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(button_sc_ans, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(button_sc_ans, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        jPanel6.setBackground(new Color(225,225,225,20));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, new java.awt.Color(0, 153, 0)));

        button_sc_res.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        button_sc_res.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/result_icon.png"))); // NOI18N
        button_sc_res.setText("HASIL ANALISIS");
        button_sc_res.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        button_sc_res.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_sc_resActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(button_sc_res, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(button_sc_res, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        jPanel7.setBackground(new Color(225,225,225,20));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, new java.awt.Color(0, 153, 0)));

        button_sc_print.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        button_sc_print.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/printer_icon.png"))); // NOI18N
        button_sc_print.setText("CETAK HASIL");
        button_sc_print.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        button_sc_print.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_sc_printActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(button_sc_print, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(button_sc_print, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        jPanel8.setBackground(new Color(225,225,225,20));
        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, new java.awt.Color(0, 153, 0)));

        jButton5.setBackground(new java.awt.Color(160, 160, 160));
        jButton5.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/exit_icon.png"))); // NOI18N
        jButton5.setText("KELUAR PROGRAM");
        jButton5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        menuScrollPane.setViewportView(jPanel3);

        javax.swing.GroupLayout panel_homeLayout = new javax.swing.GroupLayout(panel_home);
        panel_home.setLayout(panel_homeLayout);
        panel_homeLayout.setHorizontalGroup(
            panel_homeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_homeLayout.createSequentialGroup()
                .addComponent(menuScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(contentScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 953, Short.MAX_VALUE))
        );
        panel_homeLayout.setVerticalGroup(
            panel_homeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(contentScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(menuScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 565, Short.MAX_VALUE)
        );

        mainScrollPane.setViewportView(panel_home);

        jMenuBar1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jMenuBar1.setPreferredSize(new java.awt.Dimension(0, 25));

        menuFile.setText("File     ");
        menuFile.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        menu_item_new.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        menu_item_new.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        menu_item_new.setText("Dokumen Baru");
        menu_item_new.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_item_newActionPerformed(evt);
            }
        });
        menuFile.add(menu_item_new);

        menu_item_open.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        menu_item_open.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        menu_item_open.setText("Buka Dokumen");
        menu_item_open.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_item_openActionPerformed(evt);
            }
        });
        menuFile.add(menu_item_open);

        menu_item_save.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        menu_item_save.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        menu_item_save.setText("Simpan Dokumen");
        menu_item_save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_item_saveActionPerformed(evt);
            }
        });
        menuFile.add(menu_item_save);
        menuFile.add(jSeparator1);

        menu_item_close.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_W, java.awt.event.InputEvent.CTRL_MASK));
        menu_item_close.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        menu_item_close.setText("Tutup Berkas");
        menu_item_close.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_item_closeActionPerformed(evt);
            }
        });
        menuFile.add(menu_item_close);

        menu_item_exit.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        menu_item_exit.setText("Keluar Program");
        menu_item_exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_item_exitActionPerformed(evt);
            }
        });
        menuFile.add(menu_item_exit);

        jMenuBar1.add(menuFile);

        jMenu3.setText("Edit     ");
        jMenu3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        menu_item_config.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_K, java.awt.event.InputEvent.CTRL_MASK));
        menu_item_config.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        menu_item_config.setText("Konfigurasi Berkas");
        menu_item_config.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_item_configActionPerformed(evt);
            }
        });
        jMenu3.add(menu_item_config);

        jMenuBar1.add(jMenu3);

        jMenu2.setText("Help");
        jMenu2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        jMenuItem1.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jMenuItem1.setText("Bantuan");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem1);
        jMenu2.add(jSeparator2);

        jMenuItem2.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        jMenuItem2.setText("Tetang Program");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem2);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 1046, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainScrollPane)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menu_item_newActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_item_newActionPerformed
        newDocumentAction();
    }//GEN-LAST:event_menu_item_newActionPerformed

    private void button_sc_keyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_sc_keyActionPerformed
        try {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            controllerUtama.savingState();
            controllerUtama.openingKeyPanel();
        } finally {
            this.setCursor(Cursor.getDefaultCursor());
            repaint();
        }
        repaint();
    }//GEN-LAST:event_button_sc_keyActionPerformed

    private void button_sc_ansActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_sc_ansActionPerformed
        controllerUtama.savingState();
        controllerUtama.openingAnswerPanel();
    }//GEN-LAST:event_button_sc_ansActionPerformed

    private void menu_item_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_item_saveActionPerformed
        saveDocumentAction();
    }//GEN-LAST:event_menu_item_saveActionPerformed

    private void button_sc_resActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_sc_resActionPerformed
        try {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            controllerUtama.savingState();
            controllerUtama.openingResultPanel();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showConfirmDialog(null, e, "Error", JOptionPane.CLOSED_OPTION);
        } finally {
            this.setCursor(Cursor.getDefaultCursor());
        }

    }//GEN-LAST:event_button_sc_resActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        exit(1);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void menu_item_exitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_item_exitActionPerformed
        exit(1);
    }//GEN-LAST:event_menu_item_exitActionPerformed

    private void menu_item_closeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_item_closeActionPerformed
        // TODO add your handling code here:
        exit(0);
        itemCheck(false);
        setFrameTitle("AniSso v.1.5.1");
        contentScrollPane.setViewportView(jPanel10);
        isFromFile = false;
    }//GEN-LAST:event_menu_item_closeActionPerformed

    private void menu_item_configActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_item_configActionPerformed
        controllerUtama.openingConfigurationPanel();
    }//GEN-LAST:event_menu_item_configActionPerformed

    private void jLabel4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseEntered
        // TODO add your handling code here:
        jLabel4.setIcon(new ImageIcon("./src/Resources/new document-iconHover.png"));
        repaint();
    }//GEN-LAST:event_jLabel4MouseEntered

    private void jLabel4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseExited
        jLabel4.setIcon(new ImageIcon("./src/Resources/new document-icon.png"));
        repaint();
    }//GEN-LAST:event_jLabel4MouseExited

    private void jLabel6MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseEntered
        jLabel6.setIcon(new ImageIcon("./src/Resources/open-iconHover.png"));
        repaint();
    }//GEN-LAST:event_jLabel6MouseEntered

    private void jLabel6MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseExited
        jLabel6.setIcon(new ImageIcon("./src/Resources/open-icon.png"));
        repaint();
    }//GEN-LAST:event_jLabel6MouseExited

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        newDocumentAction();
    }//GEN-LAST:event_jLabel4MouseClicked

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked
        openDocumentAction();
    }//GEN-LAST:event_jLabel6MouseClicked

    private void contentScrollPaneMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_contentScrollPaneMouseMoved
        repaint();
    }//GEN-LAST:event_contentScrollPaneMouseMoved

    private void formMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseMoved
        // TODO add your handling code here
    }//GEN-LAST:event_formMouseMoved

    private void button_sc_printActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_sc_printActionPerformed
        PrintPanel printPanel = new PrintPanel();
        int result = JOptionPane.showConfirmDialog(null, printPanel, "PRINT", JOptionPane.OK_OPTION,
                JOptionPane.PLAIN_MESSAGE);
        if (result != 1) {
            try {
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                controllerUtama.openingResultPanel();
                controllerUtama.print(printPanel.getSelection());
            } finally {
                this.setCursor(Cursor.getDefaultCursor());
            }
        }

    }//GEN-LAST:event_button_sc_printActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        controllerUtama.openingHelp();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void menu_item_openActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_item_openActionPerformed
        openDocumentAction();
    }//GEN-LAST:event_menu_item_openActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        exit(1);
    }//GEN-LAST:event_formWindowClosing

    private void jPanel3MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MouseMoved
        repaint();
    }//GEN-LAST:event_jPanel3MouseMoved

    private void button_sc_keyMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button_sc_keyMouseReleased
        repaint();
    }//GEN-LAST:event_button_sc_keyMouseReleased

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        JOptionPane.showConfirmDialog(null, new AboutPanel(), "About", JOptionPane.CLOSED_OPTION, JOptionPane.PLAIN_MESSAGE);
    }//GEN-LAST:event_jMenuItem2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton button_sc_ans;
    private javax.swing.JButton button_sc_key;
    private javax.swing.JButton button_sc_print;
    private javax.swing.JButton button_sc_res;
    private javax.swing.JScrollPane contentScrollPane;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JScrollPane mainScrollPane;
    private javax.swing.JMenu menuFile;
    private javax.swing.JScrollPane menuScrollPane;
    private javax.swing.JMenuItem menu_item_close;
    private javax.swing.JMenuItem menu_item_config;
    private javax.swing.JMenuItem menu_item_exit;
    private javax.swing.JMenuItem menu_item_new;
    private javax.swing.JMenuItem menu_item_open;
    private javax.swing.JMenuItem menu_item_save;
    private javax.swing.JFileChooser openDialog;
    private javax.swing.JPanel panel_home;
    private javax.swing.JFileChooser saveDialog;
    // End of variables declaration//GEN-END:variables

    public void setController(MainController controller) {
        this.controllerUtama = controller;
    }

    public void setViewPort(Component component) {
        contentScrollPane.setViewportView(component);
        repaint();
    }

    public void isFromFile(boolean b) {
        this.isFromFile = b;
    }

    public void isNewDocument(boolean b) {
        this.isNewDocument = b;
    }
}
