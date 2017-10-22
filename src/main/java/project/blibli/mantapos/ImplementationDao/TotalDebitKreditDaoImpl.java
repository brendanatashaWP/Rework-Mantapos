package project.blibli.mantapos.ImplementationDao;

import org.springframework.jdbc.core.JdbcTemplate;
import project.blibli.mantapos.Config.DataSourceConfig;
import project.blibli.mantapos.InterfaceDao.TotalDebitKreditDao;

public class TotalDebitKreditDaoImpl implements TotalDebitKreditDao {
    private static final String table_name = "total_debit_kredit";
    private static final String id_resto = "id_restaurant";
    private static final String month = "month";
    private static final String year = "year";
    private static final String debit = "debit";
    private static final String kredit = "kredit";

    private JdbcTemplate jdbcTemplate = new JdbcTemplate();

    public TotalDebitKreditDaoImpl(){
        jdbcTemplate.setDataSource(DataSourceConfig.dataSource());
    }

    @Override
    public void CreateTable() {

    }

    @Override
    public int Insert() {
        return 0;
    }

    @Override
    public int Update() {
        return 0;
    }
}
