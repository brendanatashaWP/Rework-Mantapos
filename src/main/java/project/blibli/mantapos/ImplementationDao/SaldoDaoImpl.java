package project.blibli.mantapos.ImplementationDao;

import org.springframework.jdbc.core.JdbcTemplate;
import project.blibli.mantapos.Model.SaldoAwal;
import project.blibli.mantapos.Config.DataSourceConfig;
import project.blibli.mantapos.InterfaceDao.SaldoDao;
import project.blibli.mantapos.Mapper.SaldoAwalMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SaldoDaoImpl implements SaldoDao {
    private static final String table_name = "saldo_awal";
    private static final String id_resto = "id_resto";
    private static final String saldo_awal = "saldo_awal";
    private static final String tanggal = "tanggal";
    private static final String month = "month";
    private static final String year = "year";
    private static final String ref_table_resto = "restoran";

    private JdbcTemplate jdbcTemplate = new JdbcTemplate();

    public SaldoDaoImpl(){
        jdbcTemplate.setDataSource(DataSourceConfig.dataSource());
    }

    @Override
    public void CreateTable() {
        String query = "CREATE TABLE IF NOT EXISTS " + table_name +
                "(" +
                id_resto + " INT NOT NULL, " +
                saldo_awal + " REAL NOT NULL, " +
                tanggal + " INT NOT NULL, " +
                month + " INT NOT NULL, " +
                year + " INT NOT NULL, " +
                "UNIQUE (" + month + "," + year + "), " +
                "CONSTRAINT id_resto_fk FOREIGN KEY (" + id_resto + ")" + "REFERENCES " + ref_table_resto + "(id))";
        try{
            jdbcTemplate.execute(query);
        } catch (Exception ex){
            System.out.println("Gagal create table saldo_awal : " + ex.toString());
        }
    }

    @Override
    public void AddSaldoAwal(int id_restoo, double saldoAwal) {
        String query = "INSERT INTO " + table_name + "(" + id_resto + "," + saldo_awal + "," + tanggal + "," + month + "," + year + ")" +
                "VALUES(?,?,?,?,?)";
        try{
            jdbcTemplate.update(query, new Object[] {id_restoo, saldoAwal, LocalDate.now().getDayOfMonth(), LocalDate.now().getMonthValue(), LocalDate.now().getYear()});
        } catch (Exception ex){
            System.out.println("Gagal add saldo awal : " + ex.toString());
        }
    }

    @Override
    public List<SaldoAwal> getSaldoAwalTiapBulan(int id_restoo, int itemPerPage, int page) {
        List<SaldoAwal> saldoAwalList = new ArrayList<>();
        String query = "SELECT *" + " FROM " + table_name + " WHERE " + id_resto + "=? LIMIT ? OFFSET ?";
        try{
            saldoAwalList = jdbcTemplate.query(query, new Object[] {id_restoo, itemPerPage, (page-1)*itemPerPage}, new SaldoAwalMapper());
        } catch (Exception ex){
            System.out.println("Gagal get saldo awal tiap bulan : " + ex.toString());
        }
        return saldoAwalList;
    }

    @Override
    public int getSaldoAwal(int id_restoo, int monthh, int yearr) {
        int saldo_awall=0;
        String query = "SELECT " + saldo_awal + " FROM " + table_name + " WHERE " + id_resto + "=? AND " + month + "=? AND " + year + "=?";
        try{
            saldo_awall = jdbcTemplate.queryForObject(query, new Object[] {id_restoo, monthh, yearr}, Integer.class);
        } catch (Exception ex){
            System.out.println("Gagal get saldo awal : " + ex.toString());
            saldo_awall = 0;
        }
        return saldo_awall;
    }

    @Override
    public int jumlahBanyakSaldo(int id_restoo) {
        int jumlahBanyakSaldo=0;
        String query = "SELECT COUNT(*) FROM " + table_name + " WHERE " + id_resto + "=?";
        try{
            jumlahBanyakSaldo = jdbcTemplate.queryForObject(query, new Object[]{
                    id_restoo
            }, Integer.class);
        } catch (Exception ex){
            System.out.println("Gagal get jumlah banyak saldo : " + ex.toString());
        }
        return jumlahBanyakSaldo;
    }

    @Override
    public int getSaldoAwalCustom(int id_restoo, int tanggal1, int month1, int year1) {
        int saldo_awall=0;
        String query = "SELECT SUM (" + saldo_awal + ") FROM " + table_name + " WHERE " + id_resto + "=? " +
                "AND " + tanggal + "=? AND " +
                month + "=? AND " + year + "=?";
        try{
            saldo_awall = jdbcTemplate.queryForObject(query, new Object[] {id_restoo, tanggal1, month1, year1}, Integer.class);
        } catch (Exception ex){
            System.out.println("Gagal get saldo awal custom : " + ex.toString());
            saldo_awall = 0;
        }
        return saldo_awall;
    }
}
