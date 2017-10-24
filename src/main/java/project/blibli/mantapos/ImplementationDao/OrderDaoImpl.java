package project.blibli.mantapos.ImplementationDao;

import org.springframework.jdbc.core.JdbcTemplate;
import project.blibli.mantapos.Beans_Model.Order;
import project.blibli.mantapos.Config.DataSourceConfig;
import project.blibli.mantapos.InterfaceDao.OrderDao;
import project.blibli.mantapos.Mapper.OrderPriceTotalMapper;
import project.blibli.mantapos.WeekGenerator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoImpl implements OrderDao {

    private JdbcTemplate jdbcTemplate = new JdbcTemplate();

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
    private static final String id_resto = "id_restaurant";

    public OrderDaoImpl(){
        jdbcTemplate.setDataSource(DataSourceConfig.dataSource());
    }

    @Override
    public void CreateTable() {
        String query = "CREATE TABLE IF NOT EXISTS " + table_name +
                "(" +
                id + " SERIAL PRIMARY KEY, " +
                customer_name + "  TEXT NOT NULL, " +
                table_no + " TEXT NOT NULL, " +
                price_total + " INT NOT NULL, " +
                notes + " TEXT, " +
                ordered_time + " TEXT NOT NULL, " +
                week + " INT NOT NULL, " +
                month + " INT NOT NULL, " +
                year + " INT NOT NULL, " +
                id_resto + " INT NOT NULL)";
        jdbcTemplate.execute(query);
    }

    @Override
    public int Insert(Order order, int id_restoo) {
        int status;
        String query = "INSERT INTO " + table_name +
                "(" +
                customer_name + "," +
                table_no + "," +
                price_total + "," +
                notes + "," +
                ordered_time + "," +
                week + "," +
                month + "," +
                year + "," +
                id_resto + ")" +
                "VALUES (?,?,?,?,?,?,?,?,?)";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String jam = dtf.format(now);

        int tanggal = LocalDateTime.now().getDayOfMonth();
        int week = WeekGenerator.GetWeek(tanggal);
        status = jdbcTemplate.update(query, new Object[]{
                order.getCustomer_name(), order.getTable_no(), order.getPrice_total(),
                "'" + order.getNotes() + "'", LocalDate.now().toString() + "," + jam, week, LocalDate.now().getMonthValue(), LocalDateTime.now().getYear(), id_restoo
        });
        return status;
    }

    @Override
    public List<Order> getOrderPriceTotal(int id_restoo, int monthh, int yearr) {
        List<Order> orderList = new ArrayList<>();
        String query = "SELECT " + price_total + "," + ordered_time + " FROM " + table_name + " WHERE " + id_resto + "=? AND " + month + "=? AND " + year + "=?";
        orderList = jdbcTemplate.query(query, new Object[] {id_restoo, monthh, yearr}, new OrderPriceTotalMapper());
        return orderList;
    }

    @Override
    public int getLastOrderId() {
        int last_id;
        String query = "SELECT MAX(" + id + ") FROM " + table_name;
        last_id = jdbcTemplate.queryForObject(query, Integer.class);
        return last_id;
    }
}
