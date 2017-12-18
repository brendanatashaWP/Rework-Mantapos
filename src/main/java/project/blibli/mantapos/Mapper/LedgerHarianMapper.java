package project.blibli.mantapos.Mapper;

import org.springframework.jdbc.core.RowMapper;
import project.blibli.mantapos.Model.Ledger;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LedgerHarianMapper implements RowMapper<Ledger> {
    @Override
    public Ledger mapRow(ResultSet resultSet, int i) throws SQLException {
        Ledger ledger = new Ledger();
        ledger.setKeperluan(resultSet.getString("keperluan"));
        ledger.setTipe(resultSet.getString("tipe"));
        ledger.setBiaya(resultSet.getDouble("biaya"));
        ledger.setWaktu(resultSet.getString("waktu"));
        return ledger;
    }
}
