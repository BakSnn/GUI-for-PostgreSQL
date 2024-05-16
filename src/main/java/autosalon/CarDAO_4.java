package autosalon;

import database.DBConnection_4;

import java.sql.*;

public class CarDAO_4 {
    private Connection connection;

    public CarDAO_4() {
        connection = DBConnection_4.getConnection();
    }

    // Добавление производителя
    public void addProducer(String name) {
        if (name == null || name.trim().isEmpty()) {
            System.out.println("Ошибка: название производителя не может быть пустым.");
            return;
        }
        String insertProducerQuery = "INSERT INTO Producers (Name) VALUES (?)";
        try (PreparedStatement statement = connection.prepareStatement(insertProducerQuery)) {
            statement.setString(1, name);
            statement.executeUpdate();
            System.out.println("Производитель добавлен успешно.");
        } catch (SQLException e) {
            System.out.println("Ошибка при добавлении производителя: " + e.getMessage());
        }
    }

    // Добавление марки автомобиля
    public void addBrand(String name, int producerID) {
        if (name == null || name.trim().isEmpty()) {
            System.out.println("Ошибка: название марки не может быть пустым.");
            return;
        }
        String insertBrandQuery = "INSERT INTO Brands (Name, ProducerID) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(insertBrandQuery)) {
            statement.setString(1, name);
            statement.setInt(2, producerID);
            statement.executeUpdate();
            System.out.println("Марка автомобиля добавлена успешно.");
        } catch (SQLException e) {
            System.out.println("Ошибка при добавлении марки автомобиля: " + e.getMessage());
        }
    }

    // Вывод всех марок автомобилей и их производителей
    public String displayBrands() {
        StringBuilder result = new StringBuilder();
        String selectQuery = "SELECT p.ProducerID, p.Name AS Producer, b.Name AS Brand " +
                "FROM Producers p " +
                "JOIN Brands b ON p.ProducerID = b.ProducerID";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectQuery)) {
            while (resultSet.next()) {
                int producerID = resultSet.getInt("ProducerID");
                String producerName = resultSet.getString("Producer");
                String brandName = resultSet.getString("Brand");
                result.append("ID производителя: ").append(producerID)
                        .append(", Производитель: ").append(producerName)
                        .append(", Марка: ").append(brandName).append("\n");
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при выводе данных: " + e.getMessage());
        }
        return result.toString();
    }



    // Изменение названия производителя
    public void updateProducer(int producerID, String newName) {
        if (newName == null || newName.trim().isEmpty()) {
            System.out.println("Ошибка: название производителя не может быть пустым.");
            return;
        }
        String updateProducerQuery = "UPDATE Producers SET Name = ? WHERE ProducerID = ?";
        try (PreparedStatement statement = connection.prepareStatement(updateProducerQuery)) {
            statement.setString(1, newName);
            statement.setInt(2, producerID);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Производитель успешно обновлен.");
            } else {
                System.out.println("Производитель с указанным ID не найден.");
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при обновлении производителя: " + e.getMessage());
        }
    }

    // Удаление марки автомобиля
    public void deleteBrand(String name) {
        if (name == null || name.trim().isEmpty()) {
            System.out.println("Ошибка: название марки не может быть пустым.");
            return;
        }
        String deleteBrandQuery = "DELETE FROM Brands WHERE Name = ?";
        try (PreparedStatement statement = connection.prepareStatement(deleteBrandQuery)) {
            statement.setString(1, name);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Марка автомобиля успешно удалена.");
            } else {
                System.out.println("Марка автомобиля с указанным названием не найдена.");
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при удалении марки автомобиля: " + e.getMessage());
        }
    }

    // Изменение названия марки автомобиля
    public void updateBrand(String oldName, String newName) {
        if (oldName == null || oldName.trim().isEmpty() || newName == null || newName.trim().isEmpty()) {
            System.out.println("Ошибка: название марки не может быть пустым.");
            return;
        }
        String updateQuery = "UPDATE Brands SET Name = ? WHERE Name = ?";
        try (PreparedStatement statement = connection.prepareStatement(updateQuery)) {
            statement.setString(1, newName);
            statement.setString(2, oldName);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Марка автомобиля успешно обновлена.");
            } else {
                System.out.println("Марка автомобиля с указанным названием не найдена.");
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при обновлении марки автомобиля: " + e.getMessage());
        }
    }

    // Удаление производителя и его марок автомобилей
    public void deleteProducerAndBrands(int producerID) {
        String deleteBrandsQuery = "DELETE FROM Brands WHERE ProducerID = ?";
        String deleteProducerQuery = "DELETE FROM Producers WHERE ProducerID = ?";
        try (PreparedStatement deleteBrandsStatement = connection.prepareStatement(deleteBrandsQuery);
             PreparedStatement deleteProducerStatement = connection.prepareStatement(deleteProducerQuery)) {
            connection.setAutoCommit(false);
            deleteBrandsStatement.setInt(1, producerID);
            int rowsDeletedBrands = deleteBrandsStatement.executeUpdate();
            deleteProducerStatement.setInt(1, producerID);
            int rowsDeletedProducer = deleteProducerStatement.executeUpdate();
            if (rowsDeletedProducer > 0 || rowsDeletedBrands > 0) {
                System.out.println("Производитель и все его марки автомобилей успешно удалены.");
                connection.commit();
            } else {
                System.out.println("Производитель с указанным ID не найден.");
                connection.rollback();
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при удалении производителя и марок автомобилей: " + e.getMessage());
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException ex) {
                System.out.println("Ошибка при откате транзакции: " + ex.getMessage());
            }
        } finally {
            try {
                if (connection != null) {
                    connection.setAutoCommit(true);
                }
            } catch (SQLException ex) {
                System.out.println("Ошибка при установке автокоммита: " + ex.getMessage());
            }
        }
    }
}
