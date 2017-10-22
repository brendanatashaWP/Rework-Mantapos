package project.blibli.mantapos.ImplementationDao;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import project.blibli.mantapos.Beans_Model.Income;
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
                week + " INT NOT NULL, " +
                month + " INT NOT NULL, " +
                year + " INT NOT NULL, " +
                income_amount + " REAL NOT NULL" +
                ")";
        jdbcTemplate.execute(query);
    }

    @Override
    public int Insert(Income income) {
        int status;
        String query = "INSERT INTO " + table_name + "(" + week + "," + month + "," + year + "," +income_amount + ")" +
                "VALUES(?,?,?,?)";
        status = jdbcTemplate.update(query, new Object[] { income.getWeek(), income.getMonth(), income.getYear(), income.getIncomeAmount() });
        return status;
    }

    @Override
    public List<Integer> GetWeekly() {
        String query = "SELECT " + income_amount + "," + week + " FROM " + table_name +
                " WHERE " + month + "=?" + " AND " + year + "=?";
        incomeList = jdbcTemplate.query(query, new Object[]{
                LocalDate.now().getMonthValue(), LocalDate.now().getYear()
        } ,new ResultSetExtractor<List>() {
            @Override
            public List extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                List incomeList = new ArrayList();
                while(resultSet.next()){
                    Income income = new Income();
                    income.setIncomeAmount(resultSet.getInt(income_amount));
                    income.setWeek(resultSet.getInt(week));
                    incomeList.add(income);
                }
                return incomeList;
            }
        });
        return incomeList;
    }

    @Override
    public List<Integer> GetMonthly() {
        String query = "SELECT " + income_amount + "," + month + " FROM " + table_name +
                " WHERE "  + year + "=?"; //QUERY AMBIL INCOME BULANAN DI TAHUN YANG SAMA (RAPOPO)
        incomeList = jdbcTemplate.query(query, new Object[]{LocalDate.now().getYear()}, new ResultSetExtractor<List>() {
            @Override
            public List extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                List incomeList = new ArrayList<>();
                while (resultSet.next()){
                    Income income = new Income();
                    income.setIncomeAmount(resultSet.getInt(income_amount));
                    income.setMonth(resultSet.getInt(month));
                    incomeList.add(income);
                }
                return incomeList;
            }
        });
        return incomeList;
    }

    @Override
    public List<Integer> GetYearly() {
        String query = "SELECT " + income_amount + "," + year + " FROM " + table_name; //QUERY AMBIL MASUKKAN TAHUNAN
        incomeList = jdbcTemplate.query(query, new ResultSetExtractor<List>() {
            @Override
            public List extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                List incomeList = new ArrayList<>();
                while (resultSet.next()){
                    Income income = new Income();
                    income.setIncomeAmount(resultSet.getInt(income_amount));
                    income.setYear(resultSet.getInt(year));
                    incomeList.add(income);
                }
                return incomeList;
            }
        });
        return incomeList;
    }
}
