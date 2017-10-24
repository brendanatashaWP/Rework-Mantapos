package project.blibli.mantapos.ImplementationDao;

import org.springframework.jdbc.core.JdbcTemplate;
import project.blibli.mantapos.Beans_Model.Outcome;
import project.blibli.mantapos.Config.DataSourceConfig;
import project.blibli.mantapos.InterfaceDao.OutcomeDao;

public class OutcomeDaoImpl implements OutcomeDao{

    private static final String table_name = "outcome";
    private static final String id_resto = "id_restaurant";
    private static final String item_name = "item_name";
    private static final String qty = "qty";
    private static final String outcome_amount = "outcome_amount";
    private static final String supp_name = "supp_name";
    private static final String supp_location = "supp_location";
    private static final String supp_contact = "supp_contact";
    private static final String week = "week";
    private static final String month = "month";
    private static final String year = "year";
    private static final String outcome_date = "outcome_date";

    private JdbcTemplate jdbcTemplate = new JdbcTemplate();

    public OutcomeDaoImpl(){
        jdbcTemplate.setDataSource(DataSourceConfig.dataSource());
    }

    @Override
    public void CreateTable() {
        String query="CREATE TABLE IF NOT EXISTS " + table_name +
                "(" +
                id_resto + " INT NOT NULL, " +
                item_name + " TEXT NOT NULL, " +
                qty + " INT NOT NULL, " +
                outcome_amount + " REAL NOT NULL, " +
                outcome_date + " TEXT NOT NULL, " +
                week + " INT NOT NULL, " +
                month + " INT NOT NULL, " +
                year + " INT NOT NULL, " +
                supp_name + " TEXT NOT NULL, " +
                supp_location + " TEXT NOT NULL)";
        jdbcTemplate.execute(query);
    }

    @Override
    public int Insert(int id_restoo, Outcome outcome) {
        String query="INSERT INTO " + table_name +
                "(" + id_resto + "," +item_name + "," + qty + "," +
                outcome_amount + "," + outcome_date + "," +
                week + "," + month + "," + year + "," +
                supp_name + "," + supp_location + ")" +
                "VALUES(?,?,?,?,?,?,?,?,?,?)";
        int status = jdbcTemplate.update(query, new Object[] {
                id_restoo, outcome.getItem_name(), outcome.getQty(), outcome.getOutcome_amount(),
                outcome.getOutcome_date(), outcome.getWeek(), outcome.getMonth(), outcome.getYear(),
                outcome.getSupp_name(), outcome.getSupp_location()
        });
        return status;
    }
}
