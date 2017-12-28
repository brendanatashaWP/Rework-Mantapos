package project.blibli.mantapos.Mapper;

import org.springframework.jdbc.core.RowMapper;
import project.blibli.mantapos.Model.SaldoAwal;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SaldoAwalMapper implements RowMapper<SaldoAwal> {

    //Class Mapper, untuk mapping value dari resultSet ke model yang bersesuaian.

    @Override
    public SaldoAwal mapRow(ResultSet resultSet, int i) throws SQLException {
        SaldoAwal saldoAwal = new SaldoAwal();
        saldoAwal.setId_resto(resultSet.getInt("id_resto"));
        saldoAwal.setMonth(resultSet.getInt("month"));
        saldoAwal.setYear(resultSet.getInt("year"));
        saldoAwal.setSaldo_awal(resultSet.getDouble("saldo_awal"));
        return saldoAwal;
    }
}
