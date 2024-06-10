package gui;

import autosalon.CarDAO_4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;

public class CarGUI_4 extends JFrame implements ActionListener {
    private JTextField producerField, brandField, producerIDField, oldBrandField, newBrandField;
    private JButton addProducerBtn, addBrandBtn, updateProducerBtn, updateBrandBtn, deleteBrandBtn, deleteProducerBtn, displayBrandsBtn;
    private JTable displayTable;
    private DefaultTableModel tableModel;
    private CarDAO_4 carDAO;

    public CarGUI_4() {
        setTitle("Автомобильный салон");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        carDAO = new CarDAO_4();

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel producerLabel = new JLabel("Название производителя (добавление, изменение, удаление):");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(producerLabel, gbc);

        producerField = new JTextField();
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(producerField, gbc);

        addProducerBtn = new JButton("Добавить производителя");
        addProducerBtn.addActionListener(this);
        gbc.gridx = 1;
        panel.add(addProducerBtn, gbc);

        JLabel brandLabel = new JLabel("Название марки:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(brandLabel, gbc);

        brandField = new JTextField();
        gbc.gridx = 1;
        panel.add(brandField, gbc);

        JLabel producerIDLabel = new JLabel("ID производителя:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(producerIDLabel, gbc);

        producerIDField = new JTextField();
        gbc.gridx = 1;
        panel.add(producerIDField, gbc);

        addBrandBtn = new JButton("Добавить марку автомобиля");
        addBrandBtn.addActionListener(this);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        panel.add(addBrandBtn, gbc);

        JLabel oldBrandLabel = new JLabel("Старое название марки:");
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        panel.add(oldBrandLabel, gbc);

        oldBrandField = new JTextField();
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        panel.add(oldBrandField, gbc);

        JLabel newBrandLabel = new JLabel("Новое название марки (обновление, удаление):");
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        panel.add(newBrandLabel, gbc);

        newBrandField = new JTextField();
        gbc.gridy = 8;
        gbc.gridwidth = 1;
        panel.add(newBrandField, gbc);

        updateBrandBtn = new JButton("Обновить марку автомобиля");
        updateBrandBtn.addActionListener(this);
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        panel.add(updateBrandBtn, gbc);

        updateProducerBtn = new JButton("Обновить производителя");
        updateProducerBtn.addActionListener(this);
        gbc.gridy = 10;
        gbc.gridwidth = 2;
        panel.add(updateProducerBtn, gbc);

        deleteBrandBtn = new JButton("Удалить марку автомобиля");
        deleteBrandBtn.addActionListener(this);
        gbc.gridy = 11;
        gbc.gridwidth = 2;
        panel.add(deleteBrandBtn, gbc);

        deleteProducerBtn = new JButton("Удалить производителя");
        deleteProducerBtn.addActionListener(this);
        gbc.gridy = 12;
        gbc.gridwidth = 2;
        panel.add(deleteProducerBtn, gbc);

        displayBrandsBtn = new JButton("Вывести марки автомобилей");
        displayBrandsBtn.addActionListener(this);
        gbc.gridy = 13;
        gbc.gridwidth = 2;
        panel.add(displayBrandsBtn, gbc);

        tableModel = new DefaultTableModel(new String[]{"ID Производителя", "Название Производителя", "Название Марки"}, 0);
        displayTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(displayTable);
        gbc.gridy = 14;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(scrollPane, gbc);

        add(panel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionResult = "";
        if (e.getSource() == addProducerBtn) {
            String producerName = producerField.getText().trim();
            carDAO.addProducer(producerName);
            actionResult = "Производитель '" + producerName + "' добавлен.";
        } else if (e.getSource() == addBrandBtn) {
            String brandName = brandField.getText().trim();
            int producerID = Integer.parseInt(producerIDField.getText().trim());
            carDAO.addBrand(brandName, producerID);
            actionResult = "Марка '" + brandName + "' добавлена к производителю с ID " + producerID + ".";
            displayBrands();
        } else if (e.getSource() == updateBrandBtn) {
            String oldBrandName = oldBrandField.getText().trim();
            String newBrandName = newBrandField.getText().trim();
            carDAO.updateBrand(oldBrandName, newBrandName);
            actionResult = "Марка '" + oldBrandName + "' обновлена на '" + newBrandName + "'.";
            displayBrands();
        } else if (e.getSource() == updateProducerBtn) {
            int producerID = Integer.parseInt(producerIDField.getText().trim());
            String newName = producerField.getText().trim();
            carDAO.updateProducer(producerID, newName);
            actionResult = "Производитель с ID " + producerID + " обновлен на '" + newName + "'.";
        } else if (e.getSource() == deleteBrandBtn) {
            String brandName = oldBrandField.getText().trim();
            carDAO.deleteBrand(brandName);
            actionResult = "Марка '" + brandName + "' удалена.";
        } else if (e.getSource() == deleteProducerBtn) {
            int producerID = Integer.parseInt(producerIDField.getText().trim());
            carDAO.deleteProducerAndBrands(producerID);
            actionResult = "Производитель с ID " + producerID + " и все его марки удалены.";
        } else if (e.getSource() == displayBrandsBtn) {
            displayBrands();
            actionResult = "Список марок автомобилей отображен.";
        }
        JOptionPane.showMessageDialog(this, actionResult);
    }

    private void displayBrands() {
        tableModel.setRowCount(0); // Очистить текущие данные
        String[][] brands = parseDisplayBrands(carDAO.displayBrands()); // Предполагается, что displayBrands возвращает строку
        for (String[] brand : brands) {
            tableModel.addRow(brand);
        }
    }

    private String[][] parseDisplayBrands(String data) {
        String[] rows = data.split("\n");
        String[][] result = new String[rows.length][3]; // Учитываем три колонки: ID Производителя, Название Производителя, Название Марки
        for (int i = 0; i < rows.length; i++) {
            result[i] = rows[i].split(",");
        }
        return result;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CarGUI_4());
    }
}
