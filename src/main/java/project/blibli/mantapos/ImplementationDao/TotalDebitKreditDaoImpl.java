package project.blibli.mantapos.ImplementationDao;

import org.springframework.jdbc.core.JdbcTemplate;
import project.blibli.mantapos.Beans_Model.DebitKredit;
import project.blibli.mantapos.Config.DataSourceConfig;
import project.blibli.mantapos.InterfaceDao.TotalDebitKreditDao;
import project.blibli.mantapos.Mapper.DebitKreditMapper;

import java.util.ArrayList;
import java.util.List;

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
        String query = "CREATE TABLE IF NOT EXISTS " + table_name +
                "(" +
                id_resto + " INT NOT NULL, " +
                month + " INT NOT NULL, " +
                year + " INT NOT NULL, " +
                debit + " REAL NOT NULL, " +
                kredit + " REAL NOT NULL)";
        jdbcTemplate.execute(query);
    }

    @Override
    public int Insert(int tipe, int id_restoo, int monthh, int yearr, int debitKreditAmount) {
        String query=null;
        int status=0;
        query = "INSERT INTO " + table_name + "(" +
                id_resto + "," + month + "," + year + "," + debit + "," + kredit + ")" +
                " VALUES (?,?,?,?,?)";
        if(tipe==0){ //debit
            status = jdbcTemplate.update(query, new Object[] {id_restoo, monthh, yearr, debitKreditAmount, 0});
        } else{ //kredit

            status = jdbcTemplate.update(query, new Object[] {id_restoo, monthh, yearr, 0, debitKreditAmount});
        }
        return status;
    }

    @Override
    public int Update(int tipe, int id_restoo, int monthh, int yearr, int debitKreditAmount) {
        String query=null;
        List<DebitKredit> debitKreditList = getDebitKreditAmount(id_restoo, monthh, yearr);
        int oldDebit=0, oldKredit=0, status=0;
        for (DebitKredit debitKredit : debitKreditList
                ) {
            oldDebit = debitKredit.getDebit();
            oldKredit = debitKredit.getKredit();
        }
        if(tipe==0) { //debit
            query = "UPDATE " + table_name + " SET " + debit + "=? WHERE " + id_resto + "=? AND " + month + "=? AND " +  year + "=?";
            status = jdbcTemplate.update(query, new Object[] {oldDebit+debitKreditAmount, id_restoo, monthh, yearr});
        } else { //kredit
            query = "UPDATE " + table_name + " SET " + kredit + "=? WHERE " + id_resto + "=? AND " + month + "=? AND " +  year + "=?";
            status = jdbcTemplate.update(query, new Object[] {oldKredit+debitKreditAmount, id_restoo, monthh, yearr});
        }
        return status;
    }

    @Override
    public boolean isDebitKreditExists(int id_restoo, int monthh, int yearr) {
        boolean result=false;
        String query = "SELECT COUNT(*) FROM " + table_name + " WHERE " + id_resto + "=? AND " + month + "=? AND " +  year + "=?";
        int count = jdbcTemplate.queryForObject(query, new Object[] {id_restoo, monthh, yearr}, Integer.class);
        if(count>0)
            result=true;
        return result;
    }

    @Override
    public List<DebitKredit> getDebitKreditAmount(int id_restoo, int monthh, int yearr) {
        String query = "SELECT " + debit + "," + kredit + " FROM " + table_name + " WHERE " + id_resto + "=? AND " + month + "=? AND " + year + "=?";
        List<DebitKredit> debitKreditList = jdbcTemplate.query(query, new Object[]{id_restoo, monthh, yearr}, new DebitKreditMapper());
        return debitKreditList;
    }
}
