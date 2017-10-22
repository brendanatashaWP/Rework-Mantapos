package project.blibli.mantapos.ImplementationDao;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import project.blibli.mantapos.Beans_Model.Income;
import project.blibli.mantapos.Beans_Model.Restaurant;
import project.blibli.mantapos.Config.DataSourceConfig;
import project.blibli.mantapos.InterfaceDao.IncomeDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class IncomeDaoImpl implements IncomeDao {
    private static final String table_name = "income";
    private static final String id = "id";
    private static final String id_restaurant = "id_restaurant";
    private static final String date = "date";
    private static final String week = "week";
    private static final String month = "month";
    private static final String year = "year";
    private static final String income_amount = "income_amount";

    private JdbcTemplate jdbcTemplate = new JdbcTemplate();
    List incomeList;

    public IncomeDaoImpl() {
        jdbcTemplate.setDataSource(DataSourceConfig.dataSource());
    }

    @Override
    public void CreateTable() {
        String query = "CREATE TABLE IF NOT EXISTS " + table_name +
                "(" +
                id + " SERIAL PRIMARY KEY, " +
                id_restaurant + " INT NOT NULL, " +
                date + " TEXT NOT NULL, " +
                week + " INT NOT NULL, " +
                month + " INT NOT NULL, " +
                year + " INT NOT NULL, " +
                income_amount + " REAL NOT NULL" +
                ")";
        jdbcTemplate.execute(query);
    }

    @Override
    public int Insert(Restaurant restaurant, Income income) {
        int status;
        String query = "INSERT INTO " + table_name + "(" + id_restaurant + "," + date + "," + week + "," + month + "," + year + "," +income_amount + ")" +
                "VALUES(?,?,?,?,?,?)";
        status = jdbcTemplate.update(query, new Object[] { restaurant.getId_restaurant(), LocalDate.now().toString(), income.getWeek(), income.getMonth(), income.getYear(), income.getIncome_amount() });
        return status;
    }
}
