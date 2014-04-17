package com.wisnu.ebs.controller;

import com.wisnu.ebs.add.CorrelationTableValue;
import com.wisnu.ebs.add.DateChecker;
import com.wisnu.ebs.add.ErrorMessage;
import com.wisnu.ebs.event.MainListener;
import com.wisnu.ebs.model.Database;
import com.wisnu.ebs.model.FindingResult;
import com.wisnu.ebs.model.PrintResult;
import com.wisnu.ebs.model.PrintDistractor;
import com.wisnu.ebs.model.PrintStudentSummary;
import com.wisnu.ebs.util.Utilities;
import com.wisnu.ebs.view.AnsPanel;
import com.wisnu.ebs.view.ConfPanel;
import com.wisnu.ebs.view.HelpPanel;
import com.wisnu.ebs.view.KeyPanel;
import com.wisnu.ebs.view.MainFrame;
import com.wisnu.ebs.view.NewDocumentPanel;
import com.wisnu.ebs.view.ResultPanel;
import com.wisnu.ebs.view.ToolPanel;
import com.wisnu.ebs.xml.ConfigStaxParser;
import com.wisnu.ebs.xml.Help;
import com.wisnu.ebs.xml.HelpStaxParser;
import com.wisnu.ebs.xml.Item;
import com.wisnu.ebs.xml.ItemStaXParser;
import com.wisnu.ebs.xml.WriteXMLFile;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.xml.stream.XMLStreamException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Wisnu Wardoyo <mas.wisnu99@gmail.com>
 */
public class MainController implements MainListener {

    private final Database database;
    private MainFrame mainFrame;
    private AnsPanel ansPanel;
    private KeyPanel keyPanel;
    private ResultPanel resultPanel;
    private ConfPanel configPanel;
    private FindingResult findingResult;
    private HelpPanel helpPanel;
    private ToolPanel toolPanel;
    private final ConfigStaxParser configReader;
    private final ItemStaXParser itemReader;
    private final HelpStaxParser helpReader;
    private final WriteXMLFile saveFile;
    private final ConfController confController;
    private final ErrorMessage errorMessage;
    private NewDocumentPanel newDocumentPanel;
    private String path;

    public MainController() {
        this.database = Utilities.getDatabase();
        this.errorMessage = new ErrorMessage();
        this.confController = new ConfController();
        this.saveFile = new WriteXMLFile();
        this.helpReader = new HelpStaxParser();
        this.itemReader = new ItemStaXParser();
        this.configReader = new ConfigStaxParser();

        if (DateChecker.isOutOfDate()) {
            System.exit(0);
        } else {
            this.mainFrame = new MainFrame();
            mainFrame.setVisible(true);
            mainFrame.setController(this);
            confController.setDatabase(database);
            confController.setControllerUtama(this);
            //openDocumentAction("example.rmd");
        }
    }

    //New Document 
    public void createNewDocument() {
        newDocumentPanel = new NewDocumentPanel();
        int result = JOptionPane.showConfirmDialog(null, newDocumentPanel, "New Document", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == 0) {
            if (newDocumentPanel.itemCheck() == 0) {
                database.newDocument(newDocumentPanel);
                openingConfigurationPanel();
                openingKeyPanel();
                mainFrame.isNewDocument(true);
                mainFrame.isFromFile(false);
                mainFrame.itemCheck(true);
            } else {
                fireErrorMessage(4, newDocumentPanel.itemCheck(), "");
                mainFrame.isNewDocument(false);
                createNewDocument(newDocumentPanel);
            }
        } else {
            mainFrame.isNewDocument(false);
        }
    }

    //New Document on Error
    public void createNewDocument(NewDocumentPanel panel) {
        int result = JOptionPane.showConfirmDialog(null, panel, "New Document", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == 0) {
            if (newDocumentPanel.itemCheck() == 0) {
                database.newDocument(newDocumentPanel);
                openingConfigurationPanel();
                openingKeyPanel();
                mainFrame.isNewDocument(true);
                mainFrame.isFromFile(false);
                mainFrame.itemCheck(true);
            } else {
                fireErrorMessage(4, newDocumentPanel.itemCheck(), "");
                mainFrame.isNewDocument(false);
                createNewDocument(panel);
            }
        } else {
            mainFrame.isNewDocument(false);
        }
    }

    public void openingConfigurationPanel() {
        configPanel = new ConfPanel();
        confController.setConfPanel(configPanel);
        configPanel.setFrame(mainFrame);
        configPanel.setController(confController);
        configPanel.setDataTable(setConfigDataTable());
        configPanel.setLabMataPelajaran(this.database.getSubject());
        configPanel.setLabGuru(this.database.getTeacherName());
        configPanel.setLabKelas(this.database.getClassName());
        configPanel.setLabBerkas(String.valueOf(this.database.getFileCount()));
        configPanel.setBerkasDesc(setConfigFileDesc());
        mainFrame.setViewPort(configPanel);
        mainFrame.itemCheck(true);
        ansPanel = null;
        keyPanel = null;

    }

    public Object[][] setConfigDataTable() {
        int row = this.database.getFileCount();
        int aktif = this.database.getCurrentlySelectedItem();
        Object[][] dataTable = new Object[row][7];
        for (int i = 0; i < row; i++) {
            dataTable[i][0] = String.valueOf((i + 1));
            dataTable[i][1] = this.database.getCompetency()[i];
            dataTable[i][2] = this.database.getMinimumPassValue()[i];
            dataTable[i][3] = this.database.getStudentsCount()[i];
            dataTable[i][4] = this.database.getItemCount()[i];
            switch (this.database.getItemType()[i]) {
                case "3":
                    dataTable[i][5] = "A, B, C";
                    break;
                case "4":
                    dataTable[i][5] = "A, B, C, D";
                    break;
                default:
                    dataTable[i][5] = "A, B, C, D, E";
                    break;
            }
            dataTable[i][6] = i == aktif;
        }
        return dataTable;
    }

    public Object[] setConfigFileDesc() {
        Object[] data = new Object[5];
        int aktif = this.database.getCurrentlySelectedItem();
        data[0] = this.database.getCompetency()[aktif];
        data[1] = this.database.getMinimumPassValue()[aktif];
        data[2] = this.database.getStudentsCount()[aktif];
        data[3] = this.database.getItemCount()[aktif];
        if (this.database.getItemType()[aktif].equals("3")) {
            data[4] = "A, B, C";
        } else if (this.database.getItemType()[aktif].equals("4")) {
            data[4] = "A, B, C, D";
        } else {
            data[4] = "A, B, C, D, E";
        }

        return data;
    }

    //Open Document
    public void openDocumentAction(String path) {
        this.path = path;
        openingFile(path);
        database.setCurrentlySelectedItem(0);
        openingConfigurationPanel();
        openingKeyPanel();
        mainFrame.setTitle("AnisSo V.2.0.1 " + path);
    }

    public void openDocumentAction(String path, int fileSelected) {
        this.path = path;
        openingFile(path);
        database.setCurrentlySelectedItem(fileSelected);
        openingConfigurationPanel();
        openingKeyPanel();
        mainFrame.setTitle("AnisSo V.2.0.1 " + path);
    }

    protected void openingFile(String path) {
        configReader.setModel(database);
        try {
            configReader.readConfig(path);
        } catch (XMLStreamException ex) {
            fireErrorMessage(0, 99, "Dokument Tidak Valid");
            return;
        } catch (FileNotFoundException ex) {
            fireErrorMessage(0, 99, "Dokument Tidak ditemukan");
            return;
        }
        int fileCount = database.getFileCount();
        if (fileCount > 0) {
            database.setCompetency(new String[fileCount]);
            database.setStudentsCount(new String[fileCount]);
            database.setItemCount(new String[fileCount]);
            database.setItemType(new String[fileCount]);
            database.setMinimumPassValue(new String[fileCount]);
            database.setKey(new String[fileCount][]);
            database.setStudentsAnswer(new String[fileCount][][]);

            List<Item> readConfig = null;
            try {
                readConfig = itemReader.readConfig(path);
            } catch (XMLStreamException ex) {
                fireErrorMessage(0, 99, "Dokument Tidak Valid");
                return;
            } catch (FileNotFoundException ex) {
                fireErrorMessage(0, 99, "Dokument Tidak ditemukan");
                return;
            }
            for (Item item : readConfig) {
                int id = Integer.parseInt(item.getId());
                database.getCompetency()[id] = item.getKompetensi();
                database.getStudentsCount()[id] = item.getJumlahSiswa();
                database.getItemCount()[id] = item.getJumlahSoal();
                database.getItemType()[id] = item.getTipe();
                database.getMinimumPassValue()[id] = item.getKkm();
                database.getKey()[id] = item.getKunci();
                database.getStudentsAnswer()[id] = item.getSoal();
            }

            //database.fireNewDocument();
        } else {
            //database.fireErrorMessage(0);
        }
    }

    //Save Document
    public void saveDocumentAction(String path) {
        int currentFileSelected = database.getCurrentlySelectedItem();
        path = path.replace(".rmd", "");
        database.setKey(keyPanel);
        database.setStudentAnswer(ansPanel);
        saveFile.setDatabase(database);
        if (saveFile.write(path)) {
            openDocumentAction(path + ".rmd", currentFileSelected);
        }
    }

    //Key Pressed
    public void openingKeyPanel() {
        keyPanel = new KeyPanel();
        settingKeyPanelDataTable();
        mainFrame.setViewPort(keyPanel);

    }

    protected void settingKeyPanelDataTable() {
        int row = Integer.parseInt(database.getItemCount()[database.getCurrentlySelectedItem()]);
        String[] rowHeader = new String[row];
        String[][] dataTable = new String[row][1];
        for (int i = 0; i < row; i++) {
            rowHeader[i] = String.valueOf("SOAL " + (i + 1));
            dataTable[i][0] = database.getKey()[database.getCurrentlySelectedItem()][i];
        }
        int type = Integer.parseInt(database.getItemType()[database.getCurrentlySelectedItem()]);

        keyPanel.setRowHeader(rowHeader);
        keyPanel.setType(type);
        keyPanel.setDataTable(dataTable);
        keyPanel.setToolPanel(toolBarSetting(1));

    }

    //Ans Pressed
    public void openingAnswerPanel() {

        ansPanel = new AnsPanel();
        settingAnswerPanelDataTable();
        mainFrame.setViewPort(ansPanel);
    }

    protected void settingAnswerPanelDataTable() {
        int row = Integer.parseInt(database.getStudentsCount()[database.getCurrentlySelectedItem()]);
        int col = Integer.parseInt(database.getItemCount()[database.getCurrentlySelectedItem()]);

        String[] rowHeader = new String[row];
        String[] colHeader = new String[col + 1];
        String[][] dataTable = database.getStudentsAnswer()[database.getCurrentlySelectedItem()];
        int type = Integer.parseInt(database.getItemType()[database.getCurrentlySelectedItem()]);
        for (int i = 0; i <= col; i++) {
            if (i == 0) {
                colHeader[i] = "Nama Siswa";
            } else {
                colHeader[i] = "Soal " + String.valueOf(i);

            }
        }
        for (int i = 0; i < row; i++) {
            rowHeader[i] = "Siswa " + String.valueOf(i + 1);
        }
        ansPanel.setRowHeader(rowHeader);
        ansPanel.setColHeader(colHeader);
        ansPanel.setType(type);
        ansPanel.setDataTable(dataTable);
        ansPanel.setToolPanel(toolBarSetting(2));

    }

    //Result Pressed
    public void openingResultPanel() {
        database.setKey(keyPanel);
        database.setStudentAnswer(ansPanel);
        resultPanel = new ResultPanel();
        resultPanel.setFrame(mainFrame);
        findingResult();
        settingResultPanelDataTable();
        mainFrame.setViewPort(resultPanel);

    }

    protected void findingResult() {
        findingResult = Utilities.getFindingResult();
        findingResult.setDatabase(database);
        findingResult.initComponent();
        findingResult.correcting();
        findingResult.tk();
        findingResult.db();
        findingResult.ep();
        findingResult.reliability();
        findingResult.validity();
        findingResult.rightAndWrong();
        findingResult.rightAndWrong2();
        findingResult.meanOfValue();
        findingResult.sumPassGrade();

    }

    protected void settingResultPanelDataTable() {
        int aktif = database.getCurrentlySelectedItem();
        int col = Integer.parseInt(database.getStudentsCount()[aktif]);
        int row = Integer.parseInt(database.getItemCount()[aktif]);
        int tipe = Integer.parseInt(database.getItemType()[aktif]);
        double[] value = CorrelationTableValue.CorrelationTableValue(row);
        String[][][] dataTable = new String[4][][];
        String[][] rowHeader = new String[4][];
        String[] colHeader2 = new String[tipe + 2];
        dataTable[0] = new String[row][6];
        dataTable[1] = new String[row][tipe + 2];
        dataTable[2] = new String[col][4];
        dataTable[3] = new String[row][5];

        rowHeader[0] = new String[row];
        rowHeader[1] = new String[row];
        rowHeader[2] = new String[col];
        rowHeader[3] = new String[row];

        //Tabel Analisa Soal (4 Kolom)
        for (int i = 0; i < row; i++) {
            rowHeader[0][i] = String.valueOf("Soal " + (i + 1));
            for (int j = 0; j < 6; j++) {
                if (j == 0) {
                    dataTable[0][i][j] = new DecimalFormat("#").format(((float) findingResult.getTk()[i] / col) * 100) + " %";
                }
                if (j == 1) {
                    if ((float) findingResult.getTk()[i] / col <= 0.30) {
                        dataTable[0][i][j] = "Sukar";
                        if ((float) findingResult.getTk()[i] / col <= 0.15) {
                            dataTable[0][i][j] = "Sangat Sukar";
                        }
                    } else if ((float) findingResult.getTk()[i] / col > 0.30 && (float) findingResult.getTk()[i] / col <= 0.70) {
                        dataTable[0][i][j] = "Sedang";
                    } else {
                        dataTable[0][i][j] = "Mudah";
                        if ((float) findingResult.getTk()[i] / col >= 0.86) {
                            dataTable[0][i][j] = "Sangat Mudah";
                        }
                    }
                }
                if (j == 2) {
                    dataTable[0][i][j] = String.valueOf(new DecimalFormat("#.##").format(findingResult.getDb()[i] * 100)) + "%";
                }
                if (j == 3) {
                    if (findingResult.getDb()[i] < 0.20) {
                        dataTable[0][i][j] = "Buruk";
                    } else if (findingResult.getDb()[i] >= 0.20 && findingResult.getDb()[i] < 0.40) {
                        dataTable[0][i][j] = "Cukup";
                    } else if (findingResult.getDb()[i] >= 0.40 && findingResult.getDb()[i] < 0.70) {
                        dataTable[0][i][j] = "Baik";
                    } else {
                        dataTable[0][i][j] = "Baik Sekali";
                    }
                }
                if (j == 4) {
                    dataTable[0][i][j] = findingResult.getCorrelation()[i];
                }
                if (j == 5) {
                    if (findingResult.getCorrelationNumber()[i] >= value[0] && findingResult.getCorrelationNumber()[i] < value[1]) {
                        dataTable[0][i][j] = "Signifikan";
                    } else if (findingResult.getCorrelationNumber()[i] >= value[1]) {
                        dataTable[0][i][j] = "Sangat Signifikan";
                    } else {
                        dataTable[0][i][j] = "-";
                    }
                }
            }
        }
        for (int i = 0; i < row; i++) {
            rowHeader[1][i] = String.valueOf("Soal " + (i + 1));
            for (int j = 0; j <= (tipe + 1) + 1; j++) {
                if (j == 0) {
                    dataTable[1][i][j] = String.valueOf(findingResult.getIPc()[0][i]);
                }
                if (j == 1) {
                    dataTable[1][i][j] = String.valueOf(findingResult.getIPc()[1][i]);
                }
                if (j == 2) {
                    dataTable[1][i][j] = String.valueOf(findingResult.getIPc()[2][i]);
                }
                if ((tipe == 4 || tipe == 5) && j == 3) {
                    dataTable[1][i][j] = String.valueOf(findingResult.getIPc()[3][i]);
                }
                if (tipe == 5 && j == 4) {
                    dataTable[1][i][j] = String.valueOf(findingResult.getIPc()[4][i]);
                }
                if ((tipe == 3 && j == 3) || (tipe == 4 && j == 4) || (tipe == 5 && j == 5)) {
                    dataTable[1][i][j] = findingResult.getEp()[i];
                }
                if ((tipe == 3 && j == 4) || (tipe == 4 && j == 5) || (tipe == 5 && j == 6)) {
                    if (findingResult.getEp()[i].equals("Tidak Efektif")) {
                        dataTable[1][i][j] = "Harus Diganti";
                    } else {
                        dataTable[1][i][j] = "-";
                    }
                }
            }
        }

        for (int i = 0; i < col; i++) {
            rowHeader[2][i] = findingResult.getSortedNumber()[i][0];
            for (int j = 0; j < 4; j++) {
                if (j == 0) {
                    dataTable[2][i][0] = String.valueOf(findingResult.getNRaW()[i][0]);
                }
                if (j == 1) {
                    dataTable[2][i][1] = String.valueOf(findingResult.getNRaW()[i][1]);
                }
                if (j == 2) {
                    dataTable[2][i][2] = String.valueOf(new DecimalFormat("#.##").format(((float) findingResult.getNRaW()[i][0] / row) * 100));
                }
                if (j == 3) {
                    dataTable[2][i][3] = ((float) findingResult.getNRaW()[i][0] / row) * 100
                            >= Float.parseFloat(database.getMinimumPassValue()[aktif]) ? "Lulus" : "Tidak Lulus";
                }
            }

        }
        for (int i = 0; i < row; i++) {
            rowHeader[3][i] = String.valueOf("Soal " + (i + 1));
            for (int j = 0; j < 5; j++) {
                if (j == 0) {
                    if ((float) findingResult.getTk()[i] / col <= 0.30) {
                        dataTable[3][i][j] = "Sukar";
                        if ((float) findingResult.getTk()[i] / col <= 0.15) {
                            dataTable[3][i][j] = "Sangat Sukar";
                        }
                    } else if ((float) findingResult.getTk()[i] / col > 0.30 && (float) findingResult.getTk()[i] / col <= 0.70) {
                        dataTable[3][i][j] = "Sedang";
                    } else {
                        dataTable[3][i][j] = "Mudah";
                        if ((float) findingResult.getTk()[i] / col >= 0.86) {
                            dataTable[3][i][j] = "Sangat Mudah";
                        }
                    }
                }
                if (j == 1) {
                    if (findingResult.getDb()[i] <= 0.20) {
                        dataTable[3][i][j] = "Buruk";
                    } else if (findingResult.getDb()[i] > 0.20 && findingResult.getDb()[i] <= 0.40) {
                        dataTable[3][i][j] = "Cukup";
                    } else if (findingResult.getDb()[i] > 0.40 && findingResult.getDb()[i] <= 0.70) {
                        dataTable[3][i][j] = "Baik";
                    } else {
                        dataTable[3][i][j] = "Baik Sekali";
                    }
                }

                if (j == 2) {
                    dataTable[3][i][j] = findingResult.getEp()[i];
                }
                if (j == 3) {
                    if (findingResult.getCorrelationNumber()[i] >= value[0] && findingResult.getCorrelationNumber()[i] < value[1]) {
                        dataTable[3][i][j] = "Signifikan";
                    } else if (findingResult.getCorrelationNumber()[i] >= value[1]) {
                        dataTable[3][i][j] = "Sangat Signifikan";
                    } else {
                        dataTable[3][i][j] = "-";
                    }
                }
                if (j == 4) {
                    if (dataTable[3][i][0].equals("Sangat Sukar")
                            || dataTable[3][i][0].equals("Sangat Mudah")
                            || dataTable[3][i][1].equals("Buruk")) {
                        dataTable[3][i][j] = "Dibuang";
                    } else if (dataTable[3][i][1].equals("Cukup")
                            || dataTable[3][i][2].equals("Tidak Efektif")) {
                        dataTable[3][i][j] = "Revisi";
                    } else {
                        dataTable[3][i][j] = "Pertahankan";
                    }
                }
            }
        }

        if (tipe == 3) {
            colHeader2[0] = "A"; //damn to make it simple
            colHeader2[1] = "B";
            colHeader2[2] = "C";
            colHeader2[3] = "KEEFEKTIFAN";
            colHeader2[4] = "KETERANGAN";
        } else if (tipe == 4) {
            colHeader2[0] = "A"; //damn to make it simple
            colHeader2[1] = "B";
            colHeader2[2] = "C";
            colHeader2[3] = "D";
            colHeader2[4] = "KEEFEKTIFAN";
            colHeader2[5] = "KETERANGAN";
        } else {
            colHeader2[0] = "A";
            colHeader2[1] = "B";
            colHeader2[2] = "C";
            colHeader2[3] = "D";
            colHeader2[4] = "E";
            colHeader2[5] = "KEEFEKTIFAN";
            colHeader2[6] = "KETERANGAN";
        }

        resultPanel.setRowHeader(rowHeader);
        resultPanel.setColHeader2(colHeader2);
        resultPanel.setDataTable(dataTable);
        resultPanel.setToolPanel(toolBarSetting(3));

        resultPanel.setKeterangan(findingResult.getTempData());
        //int a = 0;

    }

    @Override
    public void fireErrorMessage(int i, int j, String Error) {
        String add = j != 99 ? errorMessage.message[j] : Error;
        JOptionPane.showConfirmDialog(mainFrame, errorMessage.message[i] + "\n"
                + add, "ERROR..!",
                JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
    }

    public void print(int selection) {
        switch (selection) {
            case 0:
                try {

                    String reportName = "./src/com/wisnu/ebs/report/printmodel.jrxml";
                    JasperDesign jasperDesign = JRXmlLoader.load(reportName);
                    JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
                    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap(),
                            new JRBeanCollectionDataSource(
                                    getDataTable()));
                    JasperViewer.viewReport(jasperPrint, false);

                } catch (JRException e) {
                    fireErrorMessage(5, 99, e.getMessage());

                }
                break;
            case 1:
                try {

                    String reportName = "./src/com/wisnu/ebs/report/printmodel2.jrxml";
                    JasperDesign jasperDesign = JRXmlLoader.load(reportName);
                    JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
                    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap(),
                            new JRBeanCollectionDataSource(
                                    getDataTable2()));
                    JasperViewer.viewReport(jasperPrint, false);

                } catch (JRException e) {
                    fireErrorMessage(5, 99, e.getMessage());

                }
                break;
            case 2:
                try {

                    String reportName = "./src/com/wisnu/ebs/report/printmodel3.jrxml";
                    JasperDesign jasperDesign = JRXmlLoader.load(reportName);
                    JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
                    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap(),
                            new JRBeanCollectionDataSource(
                                    getDataTable3()));
                    JasperViewer.viewReport(jasperPrint, false);

                } catch (JRException e) {
                    fireErrorMessage(5, 99, e.getMessage());

                }
                break;
        }

    }

    protected List<Object> getDataTable() {
        int aktif = database.getCurrentlySelectedItem();
        int col = Integer.parseInt(database.getStudentsCount()[aktif]);
        int row = Integer.parseInt(database.getItemCount()[aktif]);
        int tipe = Integer.parseInt(database.getItemType()[aktif]);

        List<Object> object = new LinkedList<Object>();

        for (int i = 0; i < row; i++) {
            PrintResult data = new PrintResult();
            data.setKompetensi(database.getCompetency()[aktif].toUpperCase());
            data.setSiswa(database.getStudentsCount()[aktif]);
            data.setSoal(database.getItemCount()[aktif]);

            if (Double.parseDouble(this.findingResult.getTempData()[3].replace(",", ".")) >= 0.70
                    && Double.parseDouble(this.findingResult.getTempData()[3].replace(",", ".")) < 0.90) {
                data.setReabilitas("Baik");
            } else if (Double.parseDouble(this.findingResult.getTempData()[3].replace(",", ".")) >= 0.90) {
                data.setReabilitas("Sangat Baik");
            } else {
                data.setReabilitas("Buruk");
            }
            data.setTk(resultPanel.getTable4().getValueAt(i, 0).toString());
            data.setDb(resultPanel.getTable4().getValueAt(i, 1).toString());
            data.setKp(resultPanel.getTable4().getValueAt(i, 2).toString());
            data.setHm(resultPanel.getTable4().getValueAt(i, 3).toString());
            data.setKt(resultPanel.getTable4().getValueAt(i, 4).toString());

            object.add(data);
        }

        return object;
    }

    protected List<Object> getDataTable2() {
        int aktif = database.getCurrentlySelectedItem();
        int col = Integer.parseInt(database.getStudentsCount()[aktif]);
        int row = Integer.parseInt(database.getItemCount()[aktif]);
        int tipe = Integer.parseInt(database.getItemType()[aktif]);

        List<Object> object = new LinkedList<Object>();

        for (int i = 0; i < row; i++) {
            PrintDistractor data = new PrintDistractor();
            data.setKompetensi(database.getCompetency()[aktif].toUpperCase());
            data.setSiswa(database.getStudentsCount()[aktif]);
            data.setSoal(database.getItemCount()[aktif]);
            if (Double.parseDouble(this.findingResult.getTempData()[3].replace(",", ".")) >= 0.70
                    && Double.parseDouble(this.findingResult.getTempData()[3].replace(",", ".")) < 0.90) {
                data.setReabilitas("Baik");
            } else if (Double.parseDouble(this.findingResult.getTempData()[3].replace(",", ".")) >= 0.90) {
                data.setReabilitas("Sangat Baik");
            } else {
                data.setReabilitas("Buruk");
            }
            data.setA(resultPanel.getTable2().getValueAt(i, 0).toString());
            data.setB(resultPanel.getTable2().getValueAt(i, 1).toString());
            data.setC(resultPanel.getTable2().getValueAt(i, 2).toString());
            if (tipe == 4) {
                data.setE("-");
                data.setD(resultPanel.getTable2().getValueAt(i, 3).toString());
                data.setKefektifan(resultPanel.getTable2().getValueAt(i, 4).toString());
                data.setKeterangan(resultPanel.getTable2().getValueAt(i, 5).toString());
            } else if (tipe == 5) {
                data.setD(resultPanel.getTable2().getValueAt(i, 3).toString());
                data.setE(resultPanel.getTable2().getValueAt(i, 4).toString());
                data.setKefektifan(resultPanel.getTable2().getValueAt(i, 5).toString());
                data.setKeterangan(resultPanel.getTable2().getValueAt(i, 6).toString());
            } else {
                data.setD("-");
                data.setE("-");
                data.setKefektifan(resultPanel.getTable2().getValueAt(i, 3).toString());
                data.setKeterangan(resultPanel.getTable2().getValueAt(i, 4).toString());
            }
            object.add(data);
        }

        return object;
    }

    protected List<Object> getDataTable3() {
        int aktif = database.getCurrentlySelectedItem();
        int col = Integer.parseInt(database.getStudentsCount()[aktif]);
        int row = Integer.parseInt(database.getItemCount()[aktif]);
        int tipe = Integer.parseInt(database.getItemType()[aktif]);

        List<Object> object = new LinkedList<Object>();

        for (int i = 0; i < row; i++) {
            PrintStudentSummary data = new PrintStudentSummary();
            data.setKompetensi(database.getCompetency()[aktif].toUpperCase());
            data.setSiswa(database.getStudentsCount()[aktif]);
            data.setSoal(database.getItemCount()[aktif]);
            if (Double.parseDouble(this.findingResult.getTempData()[3].replace(",", ".")) >= 0.70
                    && Double.parseDouble(this.findingResult.getTempData()[3].replace(",", ".")) < 0.90) {
                data.setReabilitas("Baik");
            } else if (Double.parseDouble(this.findingResult.getTempData()[3].replace(",", ".")) >= 0.90) {
                data.setReabilitas("Sangat Baik");
            } else {
                data.setReabilitas("Buruk");
            }
            data.setKKM(database.getMinimumPassValue()[aktif]);
            data.setNilaiRata(findingResult.getTempData()[4]);
            data.setNama(database.getStudentsAnswer()[aktif][i][0]);
            data.setBenar(String.valueOf(findingResult.getNRaW2()[i][0]));
            data.setSalah(String.valueOf(findingResult.getNRaW2()[i][1]));
            data.setNilai(new DecimalFormat("#.##").format(((float) findingResult.getNRaW2()[i][0] / (float) row) * 100));
            data.setKeterangan(Integer.parseInt(data.getNilai()) >= Integer.parseInt(database.getMinimumPassValue()[aktif])
                    ? "Lulus" : "Tidak Lulus");

            object.add(data);
        }

        return object;
    }

    public void openingHelp() {
        helpPanel = new HelpPanel();
        List<Help> readHelp;
        try {
            readHelp = helpReader.readConfig();
        } catch (FileNotFoundException ex) {
            fireErrorMessage(0, 99, "File Help Tidak Ditemukan");
            return;
        } catch (XMLStreamException ex) {
            fireErrorMessage(0, 99, "File Help Telah diUbah format tidak sesuai");
            return;
        }
        int i = 0;
        helpPanel.setContents(new String[6][2]);
        for (Help help : readHelp) {
            helpPanel.getContents()[i] = help.getContent();
            i++;
        }

        JOptionPane.showConfirmDialog(null, helpPanel, "HELP CONTENT", JOptionPane.CLOSED_OPTION, JOptionPane.PLAIN_MESSAGE);
    }

    public void repaint() {
        mainFrame.repaint();
    }

    public ToolPanel toolBarSetting(int id) {
        this.toolPanel = new ToolPanel();
        toolPanel.setController(this);
        toolPanel.setID(id);
        toolPanel.setting(id);
        return this.toolPanel;
    }

    public String getPath() {
        return this.path;
    }

    public void savingState() {
        if (keyPanel != null) {
            database.setKey(keyPanel);
        }
        if (ansPanel != null) {
            database.setStudentAnswer(ansPanel);
        }
    }

}
