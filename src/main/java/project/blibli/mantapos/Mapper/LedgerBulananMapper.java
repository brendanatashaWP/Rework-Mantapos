package project.blibli.mantapos.Mapper;

import org.springframework.jdbc.core.RowMapper;
import project.blibli.mantapos.Model.Ledger;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LedgerBulananMapper implements RowMapper<Ledger> {

    //Class Mapper, untuk mapping value dari resultSet ke model yang bersesuaian.

    @Override
    public Ledger mapRow(ResultSet resultSet, int i) throws SQLException {
        Ledger ledger = new Ledger();
        ledger.setMonth(resultSet.getInt("month"));
        ledger.setTipe(resultSet.getString("tipe"));
        ledger.setBiaya(resultSet.getDouble("sum"));
        return ledger;
    }
}
