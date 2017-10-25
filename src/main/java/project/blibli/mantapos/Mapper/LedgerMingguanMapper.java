package project.blibli.mantapos.Mapper;

import org.springframework.jdbc.core.RowMapper;
import project.blibli.mantapos.Beans_Model.Ledger;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LedgerMingguanMapper implements RowMapper<Ledger> {
    @Override
    public Ledger mapRow(ResultSet resultSet, int i) throws SQLException {
        Ledger ledger = new Ledger();
        ledger.setWeek(resultSet.getInt("week"));
        ledger.setTipe(resultSet.getString("tipe"));
        ledger.setBiaya(resultSet.getInt("sum"));
        return ledger;
    }
}
