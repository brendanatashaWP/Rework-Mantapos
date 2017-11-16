package project.blibli.mantapos.Mapper;

import org.springframework.jdbc.core.RowMapper;
import project.blibli.mantapos.Beans_Model.Ledger;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LedgerBulananMapper implements RowMapper<Ledger> {
    @Override
    public Ledger mapRow(ResultSet resultSet, int i) throws SQLException {
        Ledger ledger = new Ledger();
        ledger.setMonth(resultSet.getInt("month"));
        ledger.setTipe(resultSet.getString("tipe"));
        ledger.setBiaya(resultSet.getDouble("sum"));
        return ledger;
    }
}
