package com.wisnu.ebs.controller;

import com.wisnu.ebs.add.CorrelationTableValue;
import com.wisnu.ebs.add.ErrorMessage;
import com.wisnu.ebs.event.MainListener;
import com.wisnu.ebs.model.Database;
import com.wisnu.ebs.model.FindingResult;
import com.wisnu.ebs.model.PrintData;
import com.wisnu.ebs.model.PrintDataPengecoh;
import com.wisnu.ebs.model.PrintDataSiswa;
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
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JOptionPane;
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

    private final Database database = new Database();
    private final MainFrame mainFrame = new MainFrame();
    private AnsPanel ansPanel;
    private KeyPanel keyPanel;
    private ResultPanel resultPanel;
    private ConfPanel configPanel;
    private FindingResult findingResult;
    private HelpPanel helpPanel;
    private ToolPanel toolPanel;
    private final ConfigStaxParser configReader = new ConfigStaxParser();
    private final ItemStaXParser itemReader = new ItemStaXParser();
    private final HelpStaxParser helpReader = new HelpStaxParser();
    private final WriteXMLFile saveFile = new WriteXMLFile();
    private final ConfController confController = new ConfController();
    private final ErrorMessage errorMessage = new ErrorMessage();

    private NewDocumentPanel newDocumentPanel;
    private String path;

    public MainController() {
        mainFrame.setVisible(true);
        mainFrame.setController(this);
        confController.setDatabase(database);
        confController.setControllerUtama(this);
        //openDocumentAction("example.rmd");
    }

    //New Document 
    public void createNewDocument() {
        newDocumentPanel = new NewDocumentPanel();
        int result = JOptionPane.showConfirmDialog(null, newDocumentPanel, "New Document", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == 0) {
            if (newDocumentPanel.itemCheck()) {
                database.newDocument(newDocumentPanel);
                openingConfigurationPanel();
                openingKeyPanel();
            } else {
                JOptionPane.showConfirmDialog(null, "Input yang anda masukan salah", "Warning", JOptionPane.CLOSED_OPTION);
            }
        } else {

        }
    }

    public void openingConfigurationPanel() {
        configPanel = new ConfPanel();
        confController.setConfPanel(configPanel);
        configPanel.setFrame(mainFrame);
        configPanel.setController(confController);
        configPanel.setDataTable(setConfigDataTable());
        configPanel.setLabMataPelajaran(this.database.getMaPel());
        configPanel.setLabGuru(this.database.getNamaGuru());
        configPanel.setLabKelas(this.database.getNamaKelas());
        configPanel.setLabBerkas(String.valueOf(this.database.getJumlahBerkas()));
        configPanel.setBerkasDesc(setConfigFileDesc());
        mainFrame.setViewPort(configPanel);
        mainFrame.itemCheck(true);
        ansPanel = null;
        keyPanel = null;

    }

    public Object[][] setConfigDataTable() {
        int row = this.database.getJumlahBerkas();
        int aktif = this.database.getBerkasAktif();
        Object[][] dataTable = new Object[row][7];
        for (int i = 0; i < row; i++) {
            dataTable[i][0] = String.valueOf((i + 1));
            dataTable[i][1] = this.database.getKompetensi()[i];
            dataTable[i][2] = this.database.getKKM()[i];
            dataTable[i][3] = this.database.getJmlSiswa()[i];
            dataTable[i][4] = this.database.getJmlSoal()[i];
            if (this.database.getTipeSoal()[i].equals("3")) {
                dataTable[i][5] = "A, B, C";
            } else if (this.database.getTipeSoal()[i].equals("4")) {
                dataTable[i][5] = "A, B, C, D";
            } else {
                dataTable[i][5] = "A, B, C, D, E";
            }
            dataTable[i][6] = i == aktif ? true : false;
        }
        return dataTable;
    }

    public Object[] setConfigFileDesc() {
        Object[] data = new Object[5];
        int aktif = this.database.getBerkasAktif();
        data[0] = this.database.getKompetensi()[aktif];
        data[1] = this.database.getKKM()[aktif];
        data[2] = this.database.getJmlSiswa()[aktif];
        data[3] = this.database.getJmlSoal()[aktif];
        if (this.database.getTipeSoal()[aktif].equals("3")) {
            data[4] = "A, B, C";
        } else if (this.database.getTipeSoal()[aktif].equals("4")) {
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
        database.setBerkasAktif(0);
        openingConfigurationPanel();
        openingKeyPanel();
    }

    public void openingFile(String path) {
        configReader.setModel(database);
        configReader.readConfig(path);
        int jumlahBerkas = database.getJumlahBerkas();
        if (jumlahBerkas > 0) {
            database.setKompetensi(new String[jumlahBerkas]);
            database.setJmlSiswa(new String[jumlahBerkas]);
            database.setJmlSoal(new String[jumlahBerkas]);
            database.setTipeSoal(new String[jumlahBerkas]);
            database.setKKM(new String[jumlahBerkas]);
            database.setKunci(new String[jumlahBerkas][]);
            database.setSoal(new String[jumlahBerkas][][]);

            List<Item> readConfig = itemReader.readConfig(path);

            for (Item item : readConfig) {
                int i = Integer.parseInt(item.getId());
                database.getKompetensi()[i] = item.getKompetensi();
                database.getJmlSiswa()[i] = item.getJumlahSiswa();
                database.getJmlSoal()[i] = item.getJumlahSoal();
                database.getTipeSoal()[i] = item.getTipe();
                database.getKKM()[i] = item.getKkm();
                database.getKunci()[i] = item.getKunci();
                database.getSoal()[i] = item.getSoal();
            }

            //database.fireNewDocument();
        } else {
            //database.fireErrorMessage(0);
        }
    }

    //Save Document
    public void saveDocumentAction(String Path) {
        Path = Path.replace(".rmd", "");
        database.setKunci(keyPanel);
        database.setSoal(ansPanel);
        saveFile.setDatabase(database);
        saveFile.write(Path);
    }

    //Key Pressed
    public void openingKeyPanel() {
        keyPanel = new KeyPanel();
        settingKeyPanelDataTable();
        mainFrame.setViewPort(keyPanel);

    }

    public void settingKeyPanelDataTable() {
        int row = Integer.parseInt(database.getJmlSoal()[database.getBerkasAktif()]);
        String[] rowHeader = new String[row];
        String[][] dataTable = new String[row][1];
        for (int i = 0; i < row; i++) {
            rowHeader[i] = String.valueOf("SOAL " + (i + 1));
            dataTable[i][0] = database.getKunci()[database.getBerkasAktif()][i];
        }
        int type = Integer.parseInt(database.getTipeSoal()[database.getBerkasAktif()]);

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

    public void settingAnswerPanelDataTable() {
        int row = Integer.parseInt(database.getJmlSiswa()[database.getBerkasAktif()]);
        int col = Integer.parseInt(database.getJmlSoal()[database.getBerkasAktif()]);

        String[] rowHeader = new String[row];
        String[] colHeader = new String[col + 1];
        String[][] dataTable = database.getSoal()[database.getBerkasAktif()];
        int type = Integer.parseInt(database.getTipeSoal()[database.getBerkasAktif()]);
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
        database.setKunci(keyPanel);
        database.setSoal(ansPanel);
        resultPanel = new ResultPanel();
        resultPanel.setFrame(mainFrame);
        findingResult();
        settingResultPanelDataTable();
        mainFrame.setViewPort(resultPanel);

    }

    public void findingResult() {
        findingResult = new FindingResult();
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

    public void settingResultPanelDataTable() {
        int aktif = database.getBerkasAktif();
        int col = Integer.parseInt(database.getJmlSiswa()[aktif]);
        int row = Integer.parseInt(database.getJmlSoal()[aktif]);
        int tipe = Integer.parseInt(database.getTipeSoal()[aktif]);
        double[] value = new CorrelationTableValue().CorrelationTableValue(row);
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
                    dataTable[0][i][j] = new DecimalFormat("#").format(((float) findingResult.getTk()[i] / row) * 100) + " %";
                }
                if (j == 1) {
                    if ((float) findingResult.getTk()[i] / row <= 0.30) {
                        dataTable[0][i][j] = "Sukar";
                        if ((float) findingResult.getTk()[i] / row <= 0.15) {
                            dataTable[0][i][j] = "Sangat Sukar";
                        }
                    } else if ((float) findingResult.getTk()[i] / row > 0.30 && (float) findingResult.getTk()[i] / row <= 0.70) {
                        dataTable[0][i][j] = "Sedang";
                    } else {
                        dataTable[0][i][j] = "Mudah";
                        if ((float) findingResult.getTk()[i] / row >= 0.86) {
                            dataTable[0][i][j] = "Sangat Mudah";
                        }
                    }
                }
                if (j == 2) {
                    dataTable[0][i][j] = String.valueOf(new DecimalFormat("#.##").format(findingResult.getDb()[i] * 100)) + "%";
                }
                if (j == 3) {
                    if (findingResult.getDb()[i] <= 0.20) {
                        dataTable[0][i][j] = "Buruk";
                    } else if (findingResult.getDb()[i] > 0.20 && findingResult.getDb()[i] <= 0.40) {
                        dataTable[0][i][j] = "Cukup";
                    } else if (findingResult.getDb()[i] > 0.40 && findingResult.getDb()[i] <= 0.70) {
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
                            >= Float.parseFloat(database.getKKM()[aktif]) ? "Lulus" : "Tidak Lulus";
                }
            }

        }
        for (int i = 0; i < row; i++) {
            rowHeader[3][i] = String.valueOf("Soal " + (i + 1));
            for (int j = 0; j < 5; j++) {
                if (j == 0) {
                    if ((float) findingResult.getTk()[i] / row <= 0.30) {
                        dataTable[3][i][j] = "Sukar";
                        if ((float) findingResult.getTk()[i] / row <= 0.15) {
                            dataTable[3][i][j] = "Sangat Sukar";
                        }
                    } else if ((float) findingResult.getTk()[i] / row > 0.30 && (float) findingResult.getTk()[i] / row <= 0.70) {
                        dataTable[3][i][j] = "Sedang";
                    } else {
                        dataTable[3][i][j] = "Mudah";
                        if ((float) findingResult.getTk()[i] / row >= 0.86) {
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
    public void fireErrorMessage(int i) {
        JOptionPane.showConfirmDialog(mainFrame, errorMessage.message[i], "ERROR..!",
                JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
    }

    public void print(int selection) {
        switch (selection) {
            case 0:
                try {

                    String reportName = "./src/com/wisnu/ebs/report/printmodel.jrxml";
                    String compiledName = "./src/com/wisnu/ebs/report/printmodel.jasper";
                    JasperDesign jasperDesign = JRXmlLoader.load(reportName);
                    JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
                    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap(),
                            new JRBeanCollectionDataSource(
                                    getDataTable()));
                    JasperViewer.viewReport(jasperPrint, false);

                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("reports Error  " + e.toString());

                }
                break;
            case 1:
                try {

                    String reportName = "./src/com/wisnu/ebs/report/printmodel2.jrxml";
                    String compiledName = "./src/com/wisnu/ebs/report/printmodel2.jasper";
                    JasperDesign jasperDesign = JRXmlLoader.load(reportName);
                    JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
                    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap(),
                            new JRBeanCollectionDataSource(
                                    getDataTable2()));
                    JasperViewer.viewReport(jasperPrint, false);

                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("reports Error  " + e.toString());

                }
                break;
            case 2:
                try {

                    String reportName = "./src/com/wisnu/ebs/report/printmodel3.jrxml";
                    String compiledName = "./src/com/wisnu/ebs/report/printmodel3.jasper";
                    JasperDesign jasperDesign = JRXmlLoader.load(reportName);
                    JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
                    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap(),
                            new JRBeanCollectionDataSource(
                                    getDataTable3()));
                    JasperViewer.viewReport(jasperPrint, false);

                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("reports Error  " + e.toString());

                }
                break;
        }

    }

    public List<Object> getDataTable() {
        int aktif = database.getBerkasAktif();
        int col = Integer.parseInt(database.getJmlSiswa()[aktif]);
        int row = Integer.parseInt(database.getJmlSoal()[aktif]);
        int tipe = Integer.parseInt(database.getTipeSoal()[aktif]);

        List<Object> object = new LinkedList<Object>();

        for (int i = 0; i < row; i++) {
            PrintData data = new PrintData();
            data.setKompetensi(database.getKompetensi()[aktif].toUpperCase());
            data.setSiswa(database.getJmlSiswa()[aktif]);
            data.setSoal(database.getJmlSoal()[aktif]);

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

    public List<Object> getDataTable2() {
        int aktif = database.getBerkasAktif();
        int col = Integer.parseInt(database.getJmlSiswa()[aktif]);
        int row = Integer.parseInt(database.getJmlSoal()[aktif]);
        int tipe = Integer.parseInt(database.getTipeSoal()[aktif]);

        List<Object> object = new LinkedList<Object>();

        for (int i = 0; i < row; i++) {
            PrintDataPengecoh data = new PrintDataPengecoh();
            data.setKompetensi(database.getKompetensi()[aktif].toUpperCase());
            data.setSiswa(database.getJmlSiswa()[aktif]);
            data.setSoal(database.getJmlSoal()[aktif]);
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

    public List<Object> getDataTable3() {
        int aktif = database.getBerkasAktif();
        int col = Integer.parseInt(database.getJmlSiswa()[aktif]);
        int row = Integer.parseInt(database.getJmlSoal()[aktif]);
        int tipe = Integer.parseInt(database.getTipeSoal()[aktif]);

        List<Object> object = new LinkedList<Object>();

        for (int i = 0; i < row; i++) {
            PrintDataSiswa data = new PrintDataSiswa();
            data.setKompetensi(database.getKompetensi()[aktif].toUpperCase());
            data.setSiswa(database.getJmlSiswa()[aktif]);
            data.setSoal(database.getJmlSoal()[aktif]);
            if (Double.parseDouble(this.findingResult.getTempData()[3].replace(",", ".")) >= 0.70
                    && Double.parseDouble(this.findingResult.getTempData()[3].replace(",", ".")) < 0.90) {
                data.setReabilitas("Baik");
            } else if (Double.parseDouble(this.findingResult.getTempData()[3].replace(",", ".")) >= 0.90) {
                data.setReabilitas("Sangat Baik");
            } else {
                data.setReabilitas("Buruk");
            }
            data.setKKM(database.getKKM()[aktif]);
            data.setNilaiRata(findingResult.getTempData()[4]);
            data.setNama(database.getSoal()[aktif][i][0]);
            data.setBenar(String.valueOf(findingResult.getNRaW2()[i][0]));
            data.setSalah(String.valueOf(findingResult.getNRaW2()[i][1]));
            data.setNilai(new DecimalFormat("#.##").format(((float) findingResult.getNRaW2()[i][0] / (float) row) * 100));
            data.setKeterangan(Integer.parseInt(data.getNilai()) >= Integer.parseInt(database.getKKM()[aktif])
                    ? "Lulus" : "Tidak Lulus");

            object.add(data);
        }

        return object;
    }

    public void openingHelp() {
        helpPanel = new HelpPanel();
        List<Help> readHelp = helpReader.readConfig();
        int i = 0;
        helpPanel.setContents(new String[5][2]);
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
            robot();
            database.setKunci(keyPanel);
        }
        if (ansPanel != null) {
            robot();
            database.setSoal(ansPanel);
        }
    }

    public void robot() {
        try {
            Robot robot = new Robot();
            // Simulate a key press
            robot.keyPress(KeyEvent.VK_TAB);
            robot.keyRelease(KeyEvent.VK_TAB);
            
        } catch (AWTException e) {
            e.printStackTrace();
        }

    }
}
