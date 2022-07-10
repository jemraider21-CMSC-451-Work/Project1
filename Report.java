import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import javax.swing.*;

import Models.ReportData;
import Models.ReportModel;
import Models.TableModel;

public class Report {
    static JFrame frame;
    static JPanel buttonPanel;
    static JPanel tablePanel;
    static JButton button;
    static JFileChooser fileChooser;
    static File file;
    static ReportData[] dataSets;

    public static void main(String[] args) {
        frame = new JFrame();
        frame.setTitle("Benchmark Report");

        file = new File(".");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(2, 1));
        int frameSize = 300;
        frame.setSize(frameSize, frameSize);

        CreatePanels();

        frame.pack();
        frame.setVisible(true);
    }

    private static void CreatePanels() {
        buttonPanel = createButtonPanel();
        // tablePanel = createTablePanel();

        frame.add(buttonPanel);
        // frame.add(tablePanel);
    }

    private static JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        button = new JButton("Select a File");
        button.addActionListener(e -> {
            try {
                onButtonPress(button);
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        });
        panel.add(button);
        return panel;
    }

    public static void onButtonPress(JButton button) throws IOException {
        // Select the file
        fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(file);
        int result = fileChooser.showOpenDialog(button);
        if (result == JFileChooser.APPROVE_OPTION) {

            // Get information from the file
            file = fileChooser.getSelectedFile();
            try {
                // Collect all of the lines from the file
                Scanner reader = new Scanner(file);
                List<String> linesList = new ArrayList<String>();
                while (reader.hasNextLine()) {
                    linesList.add((reader.nextLine()));
                }

                dataSets = new ReportData[linesList.size()];

                // Collect data from an indidual line and add it to dataSets
                int index = 0;
                for (String line : linesList) {
                    String[] lineArray = line.split(" ");

                    int size = Integer.parseInt(lineArray[0]);
                    String[] dataModels = Arrays.copyOfRange(lineArray, 1, lineArray.length);

                    ReportData data = new ReportData(size, dataModels.length);
                    for (int i = 0; i < dataModels.length; i++) {
                        ReportModel newModel = new ReportModel();
                        newModel.convertFromString(dataModels[i]);
                        data.dataModels[i] = newModel;
                    }
                    dataSets[index] = data;
                    index++;
                }
                reader.close();

                refreshFrame();
            } catch (FileNotFoundException e) {
                System.err.println(e.getMessage());
            }
        }

    }

    private static JPanel createTablePanel() throws IOException {
        JPanel panel = new JPanel();
        JScrollPane scroll = new JScrollPane();

        JTable table = createTable();
        scroll.setViewportView(table);
        panel.add(scroll);
        System.out.println("New Table Panel Created");
        return panel;
    }

    public static JTable createTable() throws IOException {
        JTable table;
        String[] columnNames = { "Size", "Average Count", "Coef Count", "Average Time", "Coef Time" };

        TableModel[] tableData = new TableModel[dataSets.length];
        for (int i = 0; i < dataSets.length; i++) {
            TableModel data = new TableModel();

            ReportData reportData = dataSets[i];
            data.size = reportData.size;

            long countCount = 0;
            long timeCount = 0;
            int size = 0;
            for (ReportModel dataModel : reportData.dataModels) {
                countCount += dataModel.count;
                timeCount += dataModel.time;
                size++;
            }

            // Calculate average
            data.countAverage = countCount / size;
            data.timeAverage = timeCount / size;

            // Calculate Standard deviation
            double countStandardDeviation = 0;
            double timeStandardDeviation = 0;
            for (ReportModel dataModel : reportData.dataModels) {
                countStandardDeviation += Math.pow(dataModel.count - data.countAverage, 2);
                timeStandardDeviation += Math.pow(dataModel.time - data.timeAverage, 2);
            }
            double countVariance = countStandardDeviation / data.countAverage;
            data.countCoef = Math.sqrt(countVariance / size);

            double timeVariance = timeStandardDeviation / data.timeAverage;
            data.timeCoef = Math.sqrt(timeVariance / size);

            // Calculate coefficient (use benchmark's commented out display method)
            tableData[i] = data;
        }

        List<String[]> formattedData;
        formattedData = new ArrayList<String[]>();
        for (TableModel model : tableData) {
            formattedData.add(new String[] {
                    Integer.toString(model.size),
                    Double.toString(model.countAverage),
                    Double.toString(model.countCoef),
                    Double.toString(model.timeAverage),
                    Double.toString(model.timeCoef)
            });
        }
        String[][] dataArray = formattedData.toArray(new String[tableData.length][5]);
        table = new JTable(dataArray, columnNames);
        System.out.println("Table Created");

        // Creating a file that contains the data from the table
        String inputFileName = file.getName();
        String tableDataFileName = inputFileName.substring(0, inputFileName.length() - 9);
        // String tableDataFilePath = String.format("Reports/%sTableData.txt",
        // tableDataFileName);
        tableDataFileName = String.format("TableDataFiles/%sTableData.txt", tableDataFileName);

        File tableDataFile = new File(tableDataFileName);
        if (tableDataFile.exists()) {
            tableDataFile.delete();
        }

        FileWriter fileWriter = new FileWriter(tableDataFileName, true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        for (String[] entry : formattedData) {
            String newLine = "";
            for (String data : entry) {
                newLine += data + " ";
            }
            bufferedWriter.write(newLine);
            bufferedWriter.newLine();
        }
        bufferedWriter.close();
        return table;
    }

    public static void refreshFrame() throws IOException {
        frame.setVisible(false);
        frame.remove(buttonPanel);
        if (tablePanel != null) {
            frame.remove(tablePanel);
        }

        buttonPanel = createButtonPanel();
        tablePanel = createTablePanel();

        frame.add(buttonPanel);
        frame.add(tablePanel);

        frame.repaint();
        frame.pack();
        frame.setVisible(true);
    }
}
