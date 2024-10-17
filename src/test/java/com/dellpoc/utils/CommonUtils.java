package com.dellpoc.utils;

import com.jcraft.jsch.*;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class CommonUtils {

    /**
     * Runs a Spark job on a Linux server.
     *
     * @param command  The command to run the Spark job.
     * @param host     The host of the Linux server.
     * @param user     The username for the Linux server.
     * @param password The password for the Linux server.
     */
    public static void runSparkJobOnLinux(String command, String host, String user, String password) {
        JSch jsch = new JSch();
        Session session;
        try {
            session = jsch.getSession(user, host, 22);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            session.setConfig("PreferredAuthentications", "publickey,keyboard-interactive,password");
            session.connect();
            System.out.println("Connected to " + host);

            Channel channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand(command);
            channel.setInputStream(null);
            ((ChannelExec) channel).setErrStream(System.err);

            InputStream in = channel.getInputStream();
            channel.connect();

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            List<String> consoleMsgList = new ArrayList<>();
            int index = 0;
            while ((line = reader.readLine()) != null) {
                System.out.println(++index + " : " + line);
                consoleMsgList.add(line);
            }

            boolean flag = false;
            for (String msgLine : consoleMsgList) {
                if (msgLine.contains("Spark Job Successfully completed")) {
                    System.out.println("Spark Job Ran Successfully");
                    flag = true;
                }
            }

            if (!flag) {
                System.err.println("Spark Job failed");
                throw new RuntimeException("Spark Job failed");
            }

            channel.disconnect();
            session.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Run Spark Job to Get Data in Tables has failed", e);
        }
    }

    /**
     * Gets the value of a property from a property file.
     *
     * @param filePath The path to the property file.
     * @param key      The key of the property.
     * @return The value of the property.
     * @throws IOException If an I/O error occurs.
     */
    public static String getPropertyValue(String filePath, String key) throws IOException {
        Properties properties = new Properties();
        try (InputStream input = new FileInputStream(filePath)) {
            properties.load(input);
        }
        return properties.getProperty(key);
    }

    /**
     * Reads data from a CSV file.
     *
     * @param filePath The path to the CSV file.
     * @return A list of CSV records.
     * @throws IOException If an I/O error occurs.
     */
    public static List<CSVRecord> readCSV(String filePath) throws IOException {
        try (Reader reader = Files.newBufferedReader(Paths.get(filePath));
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)) {
            return csvParser.getRecords();
        }
    }

    /**
     * Reads data from an Excel file.
     *
     * @param filePath The path to the Excel file.
     * @return A list of rows, where each row is a list of cell values.
     * @throws IOException If an I/O error occurs.
     */
    public static List<List<String>> readExcel(String filePath) throws IOException {
        List<List<String>> data = new ArrayList<>();
        try (InputStream inputStream = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                List<String> rowData = new ArrayList<>();
                for (Cell cell : row) {
                    rowData.add(cell.toString());
                }
                data.add(rowData);
            }
        }
        return data;
    }

    /**
     * Reads data from a text file.
     *
     * @param filePath The path to the text file.
     * @return The content of the text file.
     * @throws IOException If an I/O error occurs.
     */
    public static String readTextFile(String filePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }

    /**
     * Reads data from a file on a network storage location with authentication.
     *
     * @param host     The host of the network storage.
     * @param user     The username for the network storage.
     * @param password The password for the network storage.
     * @param filePath The path to the file on the network storage.
     * @return The content of the file.
     * @throws IOException If an I/O error occurs.
     */
    public static String readFileFromNetwork(String host, String user, String password, String filePath) throws IOException {
        JSch jsch = new JSch();
        Session session;
        try {
            session = jsch.getSession(user, host, 22);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            session.setConfig("PreferredAuthentications", "publickey,keyboard-interactive,password");
            session.connect();
            System.out.println("Connected to " + host);

            ChannelSftp sftpChannel = (ChannelSftp) session.openChannel("sftp");
            sftpChannel.connect();
            InputStream stream = sftpChannel.get(filePath);
            StringBuilder content = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(stream))) {
                String line;
                while ((line = br.readLine()) != null) {
                    content.append(line).append("\n");
                }
            }
            sftpChannel.disconnect();
            session.disconnect();
            return content.toString();
        } catch (JSchException | SftpException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to read file from network storage", e);
        }
    }

    /**
     * Reads text from a PDF file.
     *
     * @param filePath The path to the PDF file.
     * @return The text content of the PDF file.
     * @throws IOException If an I/O error occurs.
     */
    public static String readPDF(String filePath) throws IOException {
        try (PDDocument document = PDDocument.load(new File(filePath))) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            return pdfStripper.getText(document);
        }
    }

    /**
     * Compares the content of two files.
     *
     * @param filePath1 The path to the first file.
     * @param filePath2 The path to the second file.
     * @return True if the contents are the same, false otherwise.
     * @throws IOException If an I/O error occurs.
     */
    public static boolean compareFiles(String filePath1, String filePath2) throws IOException {
        byte[] file1 = Files.readAllBytes(Paths.get(filePath1));
        byte[] file2 = Files.readAllBytes(Paths.get(filePath2));
        return Arrays.equals(file1, file2);
    }

    /**
     * Compares the text content of two PDF files.
     *
     * @param pdfPath1 The path to the first PDF file.
     * @param pdfPath2 The path to the second PDF file.
     * @return True if the text contents are the same, false otherwise.
     * @throws IOException If an I/O error occurs.
     */
    public static boolean comparePDFs(String pdfPath1, String pdfPath2) throws IOException {
        String pdf1Text = readPDF(pdfPath1);
        String pdf2Text = readPDF(pdfPath2);
        return pdf1Text.equals(pdf2Text);
    }

    /**
     * Compares the data between two files.
     *
     * @param filePath1 The path to the first file.
     * @param filePath2 The path to the second file.
     * @return True if the data contents are the same, false otherwise.
     * @throws IOException If an I/O error occurs.
     */
    public static boolean compareDataBetweenFiles(String filePath1, String filePath2) throws IOException {
        List<String> file1Lines = Files.readAllLines(Paths.get(filePath1));
        List<String> file2Lines = Files.readAllLines(Paths.get(filePath2));
        return file1Lines.equals(file2Lines);
    }
}
