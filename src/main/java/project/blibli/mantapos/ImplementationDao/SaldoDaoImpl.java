package project.blibli.mantapos.ImplementationDao;

import org.springframework.jdbc.core.JdbcTemplate;
import project.blibli.mantapos.Beans_Model.DebitKredit;
import project.blibli.mantapos.Config.DataSourceConfig;
import project.blibli.mantapos.InterfaceDao.SaldoDao;

import java.time.LocalDate;
import java.util.List;

public class SaldoDaoImpl implements SaldoDao {
    private static final String table_name = "saldo";
    private static final String id_resto = "id_restaurant";
    private static final String month = "month";
    private static final String year = "year";
    private static final String saldo_awal = "init_saldo";
    private static final String saldo_akhir = "final_saldo";

    private JdbcTemplate jdbcTemplate = new JdbcTemplate();
    private TotalDebitKreditDaoImpl kreditDao = new TotalDebitKreditDaoImpl();

    public SaldoDaoImpl(){
        jdbcTemplate.setDataSource(DataSourceConfig.dataSource());
    }

    @Override
    public void CreateTable() {
        String query = "CREATE TABLE IF NOT EXISTS " + table_name +
                "(" +
                id_resto + " INT NOT NULL, " +
                month + " INT NOT NULL, " +
                year + " INT NOT NULL, " +
                saldo_awal + " REAL NOT NULL, " +
                saldo_akhir + " REAL NOT NULL)";
        jdbcTemplate.execute(query);
    }

    @Override
    public int AddSaldoAwal(int id_restoo, int saldoAwal) {
        boolean isSudahAdaDebitKredit = kreditDao.isDebitKreditExists(id_restoo, LocalDate.now().getMonthValue(), LocalDate.now().getYear());
        int status=0;
        String query = "INSERT INTO " + table_name + "(" +
                saldo_awal + "," + saldo_akhir + "," + month + "," + year + ")" +
                " VALUES (?,?,?,?)";
        if(!isSudahAdaDebitKredit) { //belum ada debit kredit sebelumnya
            status = jdbcTemplate.update(query, new Object[] {saldoAwal, saldoAwal, LocalDate.now().getMonthValue(), LocalDate.now().getYear()});
        } else { //sudah ada debit kredit sebelumnya, maka di bagian saldo akhir ditambahkan dulu dengan debit dan dikurangkan dengan kredit
            List<DebitKredit> debitKreditList = kreditDao.getDebitKreditAmount(id_restoo, LocalDate.now().getMonthValue(), LocalDate.now().getYear());
            int debitYangSudahAda=0, kreditYangSudahAda=0;
            for (DebitKredit item:debitKreditList
                 ) {
                debitYangSudahAda = item.getDebit();
                kreditYangSudahAda = item.getKredit();
            }
            status = jdbcTemplate.update(query, new Object[] {saldoAwal, saldoAwal+debitYangSudahAda-kreditYangSudahAda, LocalDate.now().getMonthValue(), LocalDate.now().getYear()});
        }
        return status;
    }

    @Override
    public int Update(int tipe, int id_restoo, int monthh, int yearr, int amount) {
        String query = "UPDATE " + table_name + " SET " + saldo_akhir + "=? WHERE " + id_resto + "=? AND " + month + "=? AND " + year + "=?";
        int status=0;
        if(tipe==0){ //debit, maka ditambahkan ke saldo akhir
            status = jdbcTemplate.update(query, new Object[] {getSaldoAkhir(id_restoo, monthh, yearr)+amount, id_restoo, monthh, yearr});
        } else { //kredit, maka saldo akhir dikurangi dengan ini
            status = jdbcTemplate.update(query, new Object[] {getSaldoAkhir(id_restoo, monthh, yearr)-amount, id_restoo, monthh, yearr});
        }
        return status;
    }

    @Override
    public int getSaldoAkhir(int id_restoo, int monthh, int yearr) {
        String query = "SELECT " + saldo_akhir + " FROM " + table_name + " WHERE " + id_resto + "=? AND " + month + "=? AND " + year + "=?";
        int saldoAkhir = jdbcTemplate.queryForObject(query, new Object[] {id_restoo, monthh, yearr}, Integer.class);
        return saldoAkhir;
    }
}
