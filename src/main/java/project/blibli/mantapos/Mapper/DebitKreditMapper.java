package project.blibli.mantapos.Mapper;

import org.springframework.jdbc.core.RowMapper;
import project.blibli.mantapos.Beans_Model.DebitKredit;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DebitKreditMapper implements RowMapper<DebitKredit> {
    @Override
    public DebitKredit mapRow(ResultSet resultSet, int i) throws SQLException {
        DebitKredit debitKredit = new DebitKredit();
        debitKredit.setDebit(resultSet.getInt("debit"));
        debitKredit.setKredit(resultSet.getInt("kredit"));
        return debitKredit;
    }
}
