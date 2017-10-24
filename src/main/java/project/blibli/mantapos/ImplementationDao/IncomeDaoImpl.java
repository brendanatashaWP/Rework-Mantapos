package project.blibli.mantapos.ImplementationDao;

import org.springframework.jdbc.core.JdbcTemplate;
import project.blibli.mantapos.Beans_Model.DailyIncome;
import project.blibli.mantapos.Beans_Model.Income;
import project.blibli.mantapos.Config.DataSourceConfig;
import project.blibli.mantapos.InterfaceDao.IncomeDao;
import project.blibli.mantapos.Mapper.DailyIncomeMapper;
import project.blibli.mantapos.Mapper.IncomeMapper;

import java.time.LocalDate;
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
    public int Insert(int id_resto, int weekk, int monthh, int yearr, int income_amountt) {
        String query = "INSERT INTO " + table_name + "(" + id_restaurant + "," + date + "," + week + "," + month + "," + year + "," +income_amount + ")" +
                "VALUES(?,?,?,?,?,?)";
        int status = jdbcTemplate.update(query, new Object[] { id_resto, LocalDate.now().toString(), weekk, monthh, yearr, income_amountt});
        return status;
    }

    @Override
    public int Update(int id_resto, int income_amountt) {
        String query = "UPDATE " + table_name + " SET " + income_amount + "=? WHERE " + id_restaurant + "=? AND " + date + "=?";
        int status = jdbcTemplate.update(query, new Object[] {getIncomeAmountForUpdate(id_resto)+income_amountt, id_resto, LocalDate.now().toString()});
        return status;
    }

    @Override
    public boolean isIncomeExists(int id_resto) {
        boolean result=false;
        String query = "SELECT COUNT(*) FROM " + table_name + " WHERE " + id_restaurant + "=? AND " + date + "=?";
        int count = jdbcTemplate.queryForObject(query, new Object[] {id_resto, LocalDate.now().toString()}, Integer.class);
        if(count>0)
            result=true;
        return result;
    }

    @Override
    public int getIncomeAmountForUpdate(int id_resto) {
        String query = "SELECT " + income_amount + " FROM " + table_name + " WHERE " + id_restaurant + "=? AND " + date + "=?";
        int incomeAmount = jdbcTemplate.queryForObject(query, new Object[] {id_resto, LocalDate.now().toString()}, Integer.class);
        return incomeAmount;
    }

    @Override
    public List<DailyIncome> getDailyIncome(int id_restoo, int monthh, int yearr) {
        String query = "SELECT " +
                "ordered_menu.menu, " +
                "ordered_menu.quantity, " +
                "orderr.price_total, " +
                "orderr.order_time " +
                "FROM ordered_menu, orderr " +
                "WHERE id_restaurant=? AND month=? AND year =? AND orderr.id=ordered_menu.id_order";
        List<DailyIncome> dailyIncomeList = jdbcTemplate.query(query, new Object[] {id_restoo, monthh, yearr}, new DailyIncomeMapper());
        return dailyIncomeList;
    }
}