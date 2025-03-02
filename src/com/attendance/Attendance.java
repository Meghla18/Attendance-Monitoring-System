package com.attendance;

import java.awt.*;
import java.sql.*;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Attendance {
    private JTextField nameField, attendanceField, totalClassesField;
    private JButton addRecordButton, updateRecordButton, deleteAllButton;
    private JComboBox<String> subjectBox;
    private JTable table;
    private JFrame frame;

    public Attendance() {
        frame = new JFrame("Attendance System");
        frame.setSize(500, 400);
        frame.setLayout(new FlowLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // UI Elements
        nameField = new JTextField(15);
        totalClassesField = new JTextField(5);
        attendanceField = new JTextField(5);
        subjectBox = new JComboBox<>(new String[]{"Physics", "Chemistry", "Maths"});
        addRecordButton = new JButton("Add Record");
        updateRecordButton = new JButton("Update Record");
        deleteAllButton = new JButton("Delete All Records");
        table = new JTable();

        frame.add(new JLabel("Name:"));
        frame.add(nameField);
        frame.add(new JLabel("Subject:"));
        frame.add(subjectBox);
        frame.add(new JLabel("Total Classes:"));
        frame.add(totalClassesField);
        frame.add(new JLabel("Classes Attended:"));
        frame.add(attendanceField);
        frame.add(addRecordButton);
        frame.add(updateRecordButton);
        frame.add(deleteAllButton);
        frame.add(new JScrollPane(table));

        frame.setVisible(true);

        // Button Logic
        addRecordButton.addActionListener(_ -> addRecord());
        updateRecordButton.addActionListener(_ -> updateRecord());
        deleteAllButton.addActionListener(_ -> deleteAllRecords());

        loadTableData();
    }

    private void addRecord() {
        if (nameField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Student name cannot be blank!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try (Connection connection = DatabaseConnect.getConnection()) {
            if (connection == null) {
                JOptionPane.showMessageDialog(null, "Database connection failed!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Check for duplicate record
            String checkSql = "SELECT COUNT(*) FROM attendance WHERE student_name = ? AND subject = ?";
            PreparedStatement checkStatement = connection.prepareStatement(checkSql);
            checkStatement.setString(1, nameField.getText());
            checkStatement.setString(2, subjectBox.getSelectedItem().toString());
            ResultSet resultSet = checkStatement.executeQuery();
            resultSet.next();
            if (resultSet.getInt(1) > 0) {
                JOptionPane.showMessageDialog(frame, "Record already exists for this student and subject!", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String sql = "INSERT INTO attendance (student_name, subject, total_classes, classes_attended, total_attendance) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            double attendance = (Double.parseDouble(attendanceField.getText()) / Double.parseDouble(totalClassesField.getText())) * 100.0;
            statement.setString(1, nameField.getText());
            statement.setString(2, subjectBox.getSelectedItem().toString());
            statement.setString(3, totalClassesField.getText());
            statement.setString(4, attendanceField.getText());
            statement.setString(5, String.format("%.2f", attendance) + "%");
            statement.executeUpdate();
            JOptionPane.showMessageDialog(frame, "Student Added Successfully!");
            loadTableData();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
        }
    }

    private void updateRecord() {
        try (Connection connection = DatabaseConnect.getConnection()) {
            String sql = "UPDATE attendance SET classes_attended = ?, total_attendance = ? WHERE student_name = ? AND subject = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            double attendance = (Double.parseDouble(attendanceField.getText()) / Double.parseDouble(totalClassesField.getText())) * 100.0;
            statement.setString(1, attendanceField.getText());
            statement.setString(2, String.format("%.2f", attendance) + "%");
            statement.setString(3, nameField.getText());
            statement.setString(4, subjectBox.getSelectedItem().toString());
            statement.executeUpdate();
            JOptionPane.showMessageDialog(frame, "Attendance Updated Successfully!");
            loadTableData();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
        }
    }

    private void deleteAllRecords() {
        int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to delete all records?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try (Connection connection = DatabaseConnect.getConnection()) {
                String sql = "TRUNCATE TABLE attendance";
                Statement statement = connection.createStatement();
                statement.executeUpdate(sql);
                JOptionPane.showMessageDialog(frame, "All Records Deleted Successfully!");
                loadTableData();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
            }
        }
    }

    private void loadTableData() {
        try (Connection connection = DatabaseConnect.getConnection()) {
            String sql = "SELECT * FROM attendance";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            table.setModel(buildTableModel(rs));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
        }
    }

    private static DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        Vector<String> columnNames = new Vector<>();
        int columnCount = metaData.getColumnCount();
        for (int i = 1; i <= columnCount; i++) {
            columnNames.add(metaData.getColumnName(i));
        }
        Vector<Vector<Object>> data = new Vector<>();
        while (rs.next()) {
            Vector<Object> row = new Vector<>();
            for (int i = 1; i <= columnCount; i++) {
                row.add(rs.getObject(i));
            }
            data.add(row);
        }
        return new DefaultTableModel(data, columnNames);
    }
}
