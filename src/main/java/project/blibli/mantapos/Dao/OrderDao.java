package project.blibli.mantapos.Dao;

import project.blibli.mantapos.Beans_Model.Order;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class OrderDao {
    private static final String table_name = "orderr";
    private static final String id = "id";
    private static final String customer_name = "customer_name";
    private static final String table_no = "table_no";
    private static final String price_total = "price_total";
    private static final String notes = "notes";
    private static final String ordered_time = "order_time";
    private static final String week = "week";
    private static final String month = "month";
    private static final String year = "year";

    public static void CreateTable(){
        Connection connection = DbConnection.startConnection();
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS " + table_name +
                            "(" +
                            id + " SERIAL PRIMARY KEY, " +
                            customer_name + "  TEXT NOT NULL, " +
                            table_no + " TEXT NOT NULL, " +
                            price_total + " INT NOT NULL, " +
                            notes + " TEXT, " +
                            ordered_time + " TEXT NOT NULL, " +
                            week + " INT NOT NULL, " +
                            month + " INT NOT NULL, " +
                            year + " INT NOT NULL)"
            );
            preparedStatement.executeUpdate();
            System.out.println("Success create table order");
        } catch (Exception ex){
            System.out.println("Failed creating table order : " + ex.toString());
        } finally {
            DbConnection.ClosePreparedStatement(preparedStatement);
            DbConnection.CloseConnection(connection);
        }
    }

    public static int Insert(Order order){
        int status=0;
        Connection connection = DbConnection.startConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO " + table_name +
                            "(" +
                            customer_name + "," +
                            table_no + "," +
                            price_total + "," +
                            notes + "," +
                            ordered_time + "," +
                            week + "," +
                            month + "," +
                            year + ")" +
                            "VALUES (?,?,?,?,?,?,?,?)"
            );
            preparedStatement.setString(1, order.getCustomerName());
            preparedStatement.setString(2, order.getTableNo());
            preparedStatement.setInt(3, order.getPriceTotal());
            preparedStatement.setString(4, order.getNotes());

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            String jam = dtf.format(now);
            preparedStatement.setString(5, LocalDate.now().toString() + "," + jam);

            int tanggal = LocalDateTime.now().getDayOfMonth();
            int week = 0;
            if (tanggal<=7)
                week=1;
            else if(tanggal>7 && tanggal<=14)
                week=2;
            else if(tanggal>14 && tanggal<=21)
                week=3;
            else
                week=4;
            preparedStatement.setInt(6, week);

            preparedStatement.setInt(7, LocalDate.now().getMonthValue());
            preparedStatement.setInt(8, LocalDateTime.now().getYear());

            status = preparedStatement.executeUpdate();
            if (status==1)
                System.out.println("Insert into table order success!");
        } catch (Exception ex){
            System.out.println("Error inserting order : " + ex.toString());
        } finally {
            DbConnection.ClosePreparedStatement(preparedStatement);
            DbConnection.CloseConnection(connection);
        }
        return status;
    }

    public static List<Order> getAll(){
        List<Order> orderList = new ArrayList<>();
        Connection connection = DbConnection.startConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM " + table_name
            );
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Order order = new Order();
                order.setId(resultSet.getInt(id));
                order.setCustomerName(resultSet.getString(customer_name));
                order.setTableNo(resultSet.getString(table_no));
                order.setPriceTotal(resultSet.getInt(price_total));
                order.setNotes(resultSet.getString(notes));
                order.setOrdered_time(resultSet.getString(ordered_time));
                order.setWeek(resultSet.getInt(week));
                order.setMonth(resultSet.getInt(month));
                order.setYear(resultSet.getInt(year));
                orderList.add(order);
            }
        } catch (Exception ex){
            System.out.println("Failed get all data from order : " + ex.toString());
        } finally {
            DbConnection.CloseResultSet(resultSet);
            DbConnection.ClosePreparedStatement(preparedStatement);
            DbConnection.CloseConnection(connection);
        }
        return orderList;
    }

    public static int GetLastOrderId(){
        int lastId=0;
        Connection connection = DbConnection.startConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            preparedStatement = connection.prepareStatement(
                    "SELECT MAX(" + id + ") FROM " + table_name
            );
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                lastId = resultSet.getInt(1);
            }
        } catch (Exception ex){
            System.out.println("Error getting last id from order table : " + ex.toString());
        } finally {
            DbConnection.CloseResultSet(resultSet);
            DbConnection.ClosePreparedStatement(preparedStatement);
            DbConnection.CloseConnection(connection);
        }
        return lastId;
    }
}
