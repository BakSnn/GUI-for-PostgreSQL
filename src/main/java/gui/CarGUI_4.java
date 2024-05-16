package gui;

import autosalon.CarDAO_4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CarGUI_4 extends JFrame implements ActionListener {
    private JTextField producerField, brandField, producerIDField, oldBrandField, newBrandField;
    private JButton addProducerBtn, addBrandBtn, updateProducerBtn, updateBrandBtn, deleteBrandBtn, deleteProducerBtn, displayBrandsBtn;
    private JTextArea displayArea;
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

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);
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
        if (e.getSource() == addProducerBtn) {
            String producerName = producerField.getText().trim();
            carDAO.addProducer(producerName);
        } else if (e.getSource() == addBrandBtn) {
            String brandName = brandField.getText().trim();
            int producerID = Integer.parseInt(producerIDField.getText().trim());
            carDAO.addBrand(brandName, producerID);
        } else if (e.getSource() == updateBrandBtn) {
            String oldBrandName = oldBrandField.getText().trim();
            String newBrandName = newBrandField.getText().trim();
            carDAO.updateBrand(oldBrandName, newBrandName);
        } else if (e.getSource() == updateProducerBtn) {
            int producerID = Integer.parseInt(producerIDField.getText().trim());
            String newName = producerField.getText().trim();
            carDAO.updateProducer(producerID, newName);
        } else if (e.getSource() == deleteBrandBtn) {
            String brandName = oldBrandField.getText().trim();
            carDAO.deleteBrand(brandName);
        } else if (e.getSource() == deleteProducerBtn) {
            int producerID = Integer.parseInt(producerIDField.getText().trim());
            carDAO.deleteProducerAndBrands(producerID);
        } else if (e.getSource() == displayBrandsBtn) {
            String brands = carDAO.displayBrands();
            displayArea.setText(brands);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CarGUI_4());
    }
}
