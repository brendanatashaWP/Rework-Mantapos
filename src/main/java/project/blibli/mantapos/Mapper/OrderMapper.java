package project.blibli.mantapos.Mapper;

import org.springframework.jdbc.core.RowMapper;
import project.blibli.mantapos.Beans_Model.Order;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderMapper implements RowMapper<Order> {
    @Override
    public Order mapRow(ResultSet resultSet, int i) throws SQLException {
        Order order = new Order();
        order.setId(resultSet.getInt("id"));
        order.setCustomerName(resultSet.getString("customer_name"));
        order.setTableNo(resultSet.getString("table_no"));
        order.setPriceTotal(resultSet.getInt("price_total"));
        order.setNotes(resultSet.getString("notes"));
        order.setOrdered_time(resultSet.getString("order_time"));
        order.setWeek(resultSet.getInt("week"));
        order.setMonth(resultSet.getInt("month"));
        order.setYear(resultSet.getInt("year"));
        return order;
    }
}
