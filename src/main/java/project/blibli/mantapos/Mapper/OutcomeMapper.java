package project.blibli.mantapos.Mapper;

import org.springframework.jdbc.core.RowMapper;
import project.blibli.mantapos.Beans_Model.Outcome;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OutcomeMapper implements RowMapper<Outcome> {
    @Override
    public Outcome mapRow(ResultSet resultSet, int i) throws SQLException {
        Outcome outcome = new Outcome();
        outcome.setOutcome_date(resultSet.getString("outcome_date"));
        outcome.setItem_name(resultSet.getString("item_name"));
        outcome.setOutcome_amount(resultSet.getInt("outcome_amount"));
        return outcome;
    }
}
