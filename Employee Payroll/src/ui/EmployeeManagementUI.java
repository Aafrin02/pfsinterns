package ui;

import dao.EmployeeDAO;
import model.Employee;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class EmployeeManagementUI extends JFrame {
    private EmployeeDAO employeeDAO;

    private JPanel mainPanel;
    private JLabel nameLabel;
    private JTextField nameField;
    private JLabel positionLabel;
    private JTextField positionField;
    private JLabel salaryLabel;
    private JTextField salaryField;
    private JLabel taxLabel;
    private JTextField taxField;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JTable employeeTable;
    private JScrollPane scrollPane;

    public EmployeeManagementUI() {
        employeeDAO = new EmployeeDAO();

        setTitle("Employee Management");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);

        nameLabel = new JLabel("Name:");
        constraints.gridx = 0;
        constraints.gridy = 0;
        mainPanel.add(nameLabel, constraints);

        nameField = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 0;
        mainPanel.add(nameField, constraints);

        positionLabel = new JLabel("Position:");
        constraints.gridx = 0;
        constraints.gridy = 1;
        mainPanel.add(positionLabel, constraints);

        positionField = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 1;
        mainPanel.add(positionField, constraints);

        salaryLabel = new JLabel("Salary:");
        constraints.gridx = 0;
        constraints.gridy = 2;
        mainPanel.add(salaryLabel, constraints);

        salaryField = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 2;
        mainPanel.add(salaryField, constraints);

        taxLabel = new JLabel("Tax:");
        constraints.gridx = 0;
        constraints.gridy = 3;
        mainPanel.add(taxLabel, constraints);

        taxField = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 3;
        mainPanel.add(taxField, constraints);

        addButton = new JButton("Add");
        constraints.gridx = 0;
        constraints.gridy = 4;
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String position = positionField.getText();
                double salary = Double.parseDouble(salaryField.getText());
                double tax = Double.parseDouble(taxField.getText());

                Employee employee = new Employee(name, position, salary, tax);
                try {
                    employeeDAO.insertEmployee(employee);
                    refreshEmployeeTable();
                    JOptionPane.showMessageDialog(EmployeeManagementUI.this, "Employee added successfully!");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(EmployeeManagementUI.this, "Error adding employee: " + ex.getMessage());
                }
            }
        });
        mainPanel.add(addButton, constraints);

        updateButton = new JButton("Update");
        constraints.gridx = 1;
        constraints.gridy = 4;
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRowIndex = employeeTable.getSelectedRow();
                if (selectedRowIndex == -1) {
                    JOptionPane.showMessageDialog(EmployeeManagementUI.this, "Select an employee to update!");
                    return;
                }

                int id = (int) employeeTable.getValueAt(selectedRowIndex, 0);
                String name = nameField.getText();
                String position = positionField.getText();
                double salary = Double.parseDouble(salaryField.getText());
                double tax = Double.parseDouble(taxField.getText());

                Employee employee = new Employee(id, name, position, salary, tax);
                try {
                    employeeDAO.updateEmployee(employee);
                    refreshEmployeeTable();
                    JOptionPane.showMessageDialog(EmployeeManagementUI.this, "Employee updated successfully!");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(EmployeeManagementUI.this, "Error updating employee: " + ex.getMessage());
                }
            }
        });
        mainPanel.add(updateButton, constraints);

        deleteButton = new JButton("Delete");
        constraints.gridx = 2;
        constraints.gridy = 4;
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRowIndex = employeeTable.getSelectedRow();
                if (selectedRowIndex == -1) {
                    JOptionPane.showMessageDialog(EmployeeManagementUI.this, "Select an employee to delete!");
                    return;
                }

                int id = (int) employeeTable.getValueAt(selectedRowIndex, 0);
                try {
                    employeeDAO.deleteEmployee(id);
                    refreshEmployeeTable();
                    JOptionPane.showMessageDialog(EmployeeManagementUI.this, "Employee deleted successfully!");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(EmployeeManagementUI.this, "Error deleting employee: " + ex.getMessage());
                }
            }
        });
        mainPanel.add(deleteButton, constraints);

        employeeTable = new JTable();
        scrollPane = new JScrollPane(employeeTable);
        constraints.gridwidth = 3;
        constraints.gridx = 0;
        constraints.gridy = 5;
        mainPanel.add(scrollPane, constraints);

        add(mainPanel);
        setVisible(true);

        // Initialize table data
        refreshEmployeeTable();
    }

    private void refreshEmployeeTable() {
        try {
            List<Employee> employees = employeeDAO.selectAllEmployees();
            EmployeeTableModel model = new EmployeeTableModel(employees);
            employeeTable.setModel(model);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(EmployeeManagementUI.this, "Error retrieving employees: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new EmployeeManagementUI();
            }
        });
    }
}

