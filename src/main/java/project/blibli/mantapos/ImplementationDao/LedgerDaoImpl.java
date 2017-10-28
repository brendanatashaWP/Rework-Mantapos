package project.blibli.mantapos.ImplementationDao;

import org.springframework.jdbc.core.JdbcTemplate;
import project.blibli.mantapos.Beans_Model.Ledger;
import project.blibli.mantapos.Config.DataSourceConfig;
import project.blibli.mantapos.InterfaceDao.LedgerDao;
import project.blibli.mantapos.Mapper.KreditMapper;
import project.blibli.mantapos.Mapper.LedgerBulananMapper;
import project.blibli.mantapos.Mapper.LedgerHarianMapper;
import project.blibli.mantapos.Mapper.LedgerMingguanMapper;
import project.blibli.mantapos.MonthNameGenerator;
import project.blibli.mantapos.WeekGenerator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LedgerDaoImpl implements LedgerDao {

    private JdbcTemplate jdbcTemplate = new JdbcTemplate();

    private static final String table_name = "ledger_harian";
    private static final String id = "id";
    private static final String tipe = "tipe";
    private static final String keperluan = "keperluan";
    private static final String biaya = "biaya";
    private static final String waktu = "waktu";
    private static final String week = "week";
    private static final String month = "month";
    private static final String year = "year";
    private static final String id_resto = "id_resto";
    private static final String responsible_user_id = "responsible_user_id";
    private static final String ref_table_resto = "restoran";
    private static final String ref_table_user = "users";

    private static final String tipe_ledger = "tipe_ledger";
    private static final String tipe_debit = "debit";
    private static final String tipe_kredit = "kredit";

    public LedgerDaoImpl(){
        jdbcTemplate.setDataSource(DataSourceConfig.dataSource());
    }

    @Override
    public void CreateTable() {
        String query = "CREATE TABLE IF NOT EXISTS " + table_name +
                "(" +
                id + " SERIAL PRIMARY KEY, " +
                id_resto + " INT NOT NULL, " +
                tipe + " " + tipe_ledger + " NOT NULL, " +
                biaya + " INT NOT NULL, " +
                keperluan + " TEXT NOT NULL, " +
                waktu + " TEXT NOT NULL, " +
                week + " INT NOT NULL, " +
                month + " INT NOT NULL, " +
                year + " INT NOT NULL, " +
                responsible_user_id + " INT NOT NULL, " +
                "CONSTRAINT id_resto_fk FOREIGN KEY(" + id_resto + ")" + " REFERENCES " + ref_table_resto + "(id), " +
                "CONSTRAINT responsible_user_id_fk FOREIGN KEY(" + responsible_user_id + ") REFERENCES " + ref_table_user + "(id))";
        try{
            jdbcTemplate.execute(query);
        } catch (Exception ex){
            System.out.println("Gagal create table ledger_harian " + ex.toString());
        }
    }

    @Override
    public void CreateTipe() {
        String query = "CREATE TYPE " + tipe_ledger + " AS ENUM " + "(" +
                "'" + tipe_debit + "'," +
                "'" + tipe_kredit + "'" +
                ")";
        try{
            jdbcTemplate.execute(query);
        } catch (Exception ex){
            System.out.println("Gagal create tipe : " + ex.toString());
        }
    }

    @Override
    public void Insert(Ledger ledger, int id_restoo, int user_id) {
        String query = "INSERT INTO " + table_name +
                "(" +
                id_resto + "," +
                tipe + "," +
                biaya + "," +
                keperluan + "," +
                waktu + "," +
                week + "," +
                month + "," +
                year + "," +
                responsible_user_id + ")" +
                "VALUES (?,?::" + tipe_ledger + ",?,?,?,?,?,?,?)";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String jam = dtf.format(now);
        try{
            jdbcTemplate.update(query, new Object[]{
                    id_restoo, ledger.getTipe(), ledger.getBiaya(),
                    ledger.getKeperluan(), ledger.getWaktu() + "," + jam,
                    ledger.getWeek(), ledger.getMonth(), ledger.getYear(),
                    user_id
            });
        } catch (Exception ex){
            System.out.println("Gagal insert ledger_harian : " + ex.toString());
        }
    }

    @Override
    public List<Ledger> GetMonthAndYearList(int id_restoo) {
        List<Ledger> monthAndYearList = new ArrayList<>();
        String query = "SELECT " + month + "," + year + " FROM " + table_name +
                " WHERE " + id_resto + "=?" +
                " GROUP BY " + month + "," + year +
                " ORDER BY " + month + "," + year + " ASC";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query, new Object[] {id_restoo});
        for (Map row : rows){
            Ledger ledger = new Ledger();
            //Keperluan di bawah ini bukanlah keperluan, tapi untuk String nama bulan (alih fungsi)
            ledger.setKeperluan(MonthNameGenerator.MonthNameGenerator((Integer) row.get(month)));
            ledger.setMonth((Integer) row.get(month));
            ledger.setYear((Integer) row.get(year));
            monthAndYearList.add(ledger);
        }
        return monthAndYearList;
    }

    @Override
    public List<Ledger> GetDailyLedger(int id_restoo, int monthh, int yearr) {
        List<Ledger> ledgerList = new ArrayList<>();
        String query = "SELECT *" + " FROM " + table_name +
                " WHERE " + id_resto + "=? AND " + month + "=? AND " + year + "=?" +
                " ORDER BY " + waktu + " ASC";
        try{
            ledgerList = jdbcTemplate.query(query, new Object[] {id_restoo, monthh, yearr}, new LedgerHarianMapper());
        } catch (Exception ex){
            System.out.println("Gagal ambil daily ledger : " + ex.toString());
        }
        return ledgerList;
    }

    @Override
    public List<Ledger> GetWeeklyLedger(int id_restoo, int monthh, int yearr) {
        List<Ledger> ledgerList = new ArrayList<>();
        String query = "SELECT " + week + "," + tipe + ", SUM(" + biaya + ") FROM " + table_name +
                " WHERE " + id_resto + "=? AND " + month + "=? AND " + year + "=?" +
                " GROUP BY " + tipe + "," + week +
                " ORDER BY " + week + "," + tipe + " ASC";
        try{
            ledgerList = jdbcTemplate.query(query, new Object[] {id_restoo, monthh, yearr}, new LedgerMingguanMapper());
        } catch (Exception ex){
            System.out.println("Gagal get weekly ledger : " + ex.toString());
        }
        return ledgerList;
    }

    @Override
    public List<Ledger> GetMonthlyLedger(int id_restoo, int yearr) {
        List<Ledger> ledgerList = new ArrayList<>();
        String query = "SELECT " + month + "," + tipe + ", SUM(" + biaya + ") FROM " + table_name +
                " WHERE " + id_resto + "=? AND " + year + "=?" +
                " GROUP BY " + month + "," + tipe +
                " ORDER BY " + month + "," + tipe + " ASC";
        try {
            ledgerList = jdbcTemplate.query(query, new Object[] {id_restoo, yearr}, new LedgerBulananMapper());
        } catch (Exception ex){
            System.out.println("Gagal get monthly ledger : " + ex.toString());
        }
        return ledgerList;
    }

    @Override
    public List<Ledger> GetDailyKredit(int id_restoo) {
        List<Ledger> ledgerList = new ArrayList<>();
        String query = "SELECT *" + " FROM " + table_name + " WHERE " + tipe + "=?::" + tipe_ledger +
                " AND " + id_resto + "=?";
        try{
            ledgerList = jdbcTemplate.query(query, new Object[] {tipe_kredit, id_restoo}, new KreditMapper());
        } catch (Exception ex){
            System.out.println("Gagal get daily kredit : " + ex.toString());
        }
        return ledgerList;
    }

    @Override
    public int GetTotalDebitBulanan(int id_restoo, int monthh, int yearr) {
        int TotalDebit=0;
        String query = "SELECT SUM(" + biaya + ") FROM " + table_name +
                " WHERE " + id_resto + "=? AND " + month + "=? AND " + year + "=? AND " +
                tipe + "=?::" + tipe_ledger;
        try{
            TotalDebit = jdbcTemplate.queryForObject(query, new Object[] {
                    id_restoo, monthh, yearr, tipe_debit
            }, Integer.class);
        } catch (Exception ex){
            TotalDebit=0;
            System.out.println("Gagal get total debit bulanan : " + ex.toString());
        }
        return TotalDebit;
    }

    @Override
    public int GetTotalDebitTahunan(int id_restoo, int yearr) {
        int TotalDebit=0;
        String query = "SELECT SUM(" + biaya + ") FROM " + table_name +
                " WHERE " + id_resto + "=? AND " + year + "=? AND " + tipe + "=?::" + tipe_ledger;
        try{
            TotalDebit = jdbcTemplate.queryForObject(query, new Object[] {
                    id_restoo, yearr, tipe_debit
            }, Integer.class);
        } catch (Exception ex){
            System.out.println("Gagal get total debit tahunan : " + ex.toString());
        }
        return TotalDebit;
    }

    @Override
    public int GetTotalKreditBulanan(int id_restoo, int monthh, int yearr) {
        int TotalKredit=0;
        String query = "SELECT SUM(" + biaya + ") FROM " + table_name +
                " WHERE " + id_resto + "=? AND " + month + "=? AND " + year + "=? AND " +
                tipe + "=?::" + tipe_ledger;
        try{
            TotalKredit = jdbcTemplate.queryForObject(query, new Object[] {
                    id_restoo, monthh, yearr, tipe_kredit
            }, Integer.class);
        } catch (Exception ex){
            TotalKredit=0;
            System.out.println("Gagal get total kredit bulanan : " + ex.toString());
        }
        return TotalKredit;
    }

    @Override
    public int GetTotalKreditTahunan(int id_restoo, int yearr) {
        int TotalKredit=0;
        String query = "SELECT SUM(" + biaya + ") FROM " + table_name +
                " WHERE " + id_resto + "=? AND " + year + "=? AND " + tipe + "=?::" + tipe_ledger;
        try{
            TotalKredit = jdbcTemplate.queryForObject(query, new Object[] {
                    id_restoo, yearr, tipe_kredit
            }, Integer.class);
        } catch (Exception ex){
            System.out.println("Gagal get total kredit tahunan : " + ex.toString());
        }
        return TotalKredit;
    }

    @Override
    public int GetLastOrderId() {
        int last_id;
        String query = "SELECT MAX(" + id + ") FROM " + table_name;
        last_id = jdbcTemplate.queryForObject(query, Integer.class);
        return last_id;
    }
}
