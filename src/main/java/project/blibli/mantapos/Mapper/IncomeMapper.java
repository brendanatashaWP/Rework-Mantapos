package project.blibli.mantapos.Mapper;

import org.springframework.jdbc.core.RowMapper;
import project.blibli.mantapos.Beans_Model.Income;

import java.sql.ResultSet;
import java.sql.SQLException;

public class IncomeMapper implements RowMapper<Income>{
    @Override
    public Income mapRow(ResultSet resultSet, int i) throws SQLException {
        Income income = new Income();
        income.setIncome_amount(resultSet.getInt("income_amount"));
        return income;
    }
}
