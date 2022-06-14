import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.*;

public class Report {
    static JFrame frame;
    static JPanel buttonPanel;
    static JPanel tablePanel;
    static JTable table;
    static JButton button;
    static JFileChooser fileChooser;
    static File file;

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
        fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(file);
        int result = fileChooser.showOpenDialog(button);
        if (result == JFileChooser.APPROVE_OPTION) {
            file = fileChooser.getSelectedFile();
            System.out.println(file.getName());
            try {
                Scanner reader = new Scanner(file);
                while (reader.hasNextLine()) {
                    System.out.println(reader.nextLine());
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
    }
}
