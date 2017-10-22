package project.blibli.mantapos.ImplementationDao;

import org.springframework.jdbc.core.JdbcTemplate;
import project.blibli.mantapos.Config.DataSourceConfig;
import project.blibli.mantapos.InterfaceDao.SaldoDao;

public class SaldoDaoImpl implements SaldoDao {
    private static final String table_name = "saldo";
    private static final String id_resto = "id_restaurant";
    private static final String month = "month";
    private static final String year = "year";
    private static final String saldo_awal = "init_saldo";
    private static final String saldo_akhir = "final_saldo";

    private JdbcTemplate jdbcTemplate = new JdbcTemplate();

    public SaldoDaoImpl(){
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
