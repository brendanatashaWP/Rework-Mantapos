package project.blibli.mantapos.Mapper;

import org.springframework.jdbc.core.RowMapper;
import project.blibli.mantapos.Beans_Model.DailyIncome;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DailyIncomeMapper implements RowMapper<DailyIncome> {
    @Override
    public DailyIncome mapRow(ResultSet resultSet, int i) throws SQLException {
        DailyIncome dailyIncome = new DailyIncome();
        dailyIncome.setMenu_name(resultSet.getString("menu"));
        dailyIncome.setPrice_total(resultSet.getInt("price_total"));
        dailyIncome.setOrder_time(resultSet.getString("order_time"));
        dailyIncome.setQuantity(resultSet.getInt("quantity"));
        return dailyIncome;
    }
}
