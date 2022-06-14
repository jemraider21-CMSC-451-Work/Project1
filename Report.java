import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

import javax.swing.*;

import Models.ReportData;
import Models.ReportModel;
import Models.TableModel;

public class Report {
    static JFrame frame;
    static JPanel buttonPanel;
    static JPanel tablePanel;
    static JTable table;
    static JButton button;
    static JFileChooser fileChooser;
    static File file;
    static ReportData[] dataSets;

    public static void main(String[] args) {
        frame = new JFrame();
        frame.setTitle("Benchmark Report");

        file = new File(".");

        button = new JButton("Select file");
        button.addActionListener(e -> {
            //
        });

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // frame.setLayout(new GridLayout(3, 1));
        frame.setSize(400, 400);

        CreatePanels();

        // frame.pack();
        frame.setVisible(true);
    }

    private static void CreatePanels() {
        buttonPanel = createButtonPanel();
        tablePanel = createTablePanel();

        frame.add(buttonPanel);
        //frame.add(tablePanel);
    }

    private static JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        button = new JButton("Select a File");
        button.addActionListener(e -> onButtonPress(button));
        panel.add(button);
        return panel;
    }

    public static void onButtonPress(JButton button) {
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
                
                // Count how many lines there are and set the size of the ReportData array to that size
                // int numLines = 0;
                // while(reader.hasNext()){
                //     numLines++;
                // }
                // dataSets = new ReportData[numLines];
                dataSets = new ReportData[10];

                // Collect data from an indidual line and add it to dataSets
                int index = 0;
                while (reader.hasNextLine()) {
                    String line = reader.nextLine();
                    String[] lineArray = line.split(" ");

                    int size = Integer.parseInt(lineArray[0]);
                    String[] dataModels = Arrays.copyOfRange(lineArray, 1, lineArray.length);

                    ReportData data = new ReportData(size, dataModels.length);
                    for(int i = 0; i < dataModels.length; i++){
                        ReportModel newModel = new ReportModel();
                        newModel.convertFromString(dataModels[i]);
                        data.dataModels[i] = newModel;
                    }
                    dataSets[index] = data;
                    index++;
                }
                reader.close();
            } catch (FileNotFoundException e) {
                System.err.println(e.getMessage());
            }
        }

    }

    private static JPanel createTablePanel() {
        return null;
    }

    public static void createTable() {
        String[] columnNames = { "Size", "Average Count", "Coef Count", "Average Time", "Coef Time" };

        TableModel[] tableData = new TableModel[dataSets.length];
        for(int i = 0; i < dataSets.length; i++){
            TableModel data = new TableModel();

            ReportData reportData = dataSets[i];
            data.size = reportData.size;

            int countCount = 0;
            long timeCount = 0;
            int county = 0;
            for(ReportModel dataModel : reportData.dataModels){
                countCount += dataModel.count;
                timeCount += dataModel.time;
                county++;
            }

            // Calculate average
            data.countAverage = countCount / county;
            data.timeAverage = timeCount / county;

            // Calculate coefficient (use benchmark's commented out display method)
            tableData[i] = data;
        }
        table = new JTable(tableData, columnNames);
    }
}
