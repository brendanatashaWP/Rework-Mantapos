package project.blibli.mantapos.Mapper;

import org.springframework.jdbc.core.RowMapper;
import project.blibli.mantapos.Beans_Model.SaldoAwal;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SaldoAwalMapper implements RowMapper<SaldoAwal> {
    @Override
    public SaldoAwal mapRow(ResultSet resultSet, int i) throws SQLException {
        SaldoAwal saldoAwal = new SaldoAwal();
        saldoAwal.setId_resto(resultSet.getInt("id_resto"));
        saldoAwal.setMonth(resultSet.getInt("month"));
        saldoAwal.setYear(resultSet.getInt("year"));
        saldoAwal.setSaldo_awal(resultSet.getInt("saldo_awal"));
        return saldoAwal;
    }
}
