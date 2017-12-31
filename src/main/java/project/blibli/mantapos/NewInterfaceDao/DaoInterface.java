package project.blibli.mantapos.NewInterfaceDao;

import java.sql.SQLException;
import java.util.List;

public interface DaoInterface<A,B> {

    //A adalah nama model (POJO)
    //B adalah String condition

    void createTable() throws SQLException;
    void insert(A modelData) throws SQLException;
    List<A> getAll(B condition) throws SQLException;
    A getOne(B condition) throws SQLException;
    int getId(B condition) throws SQLException;
    int count(B condition) throws SQLException;
    void update(A modelData, B condition) throws SQLException;
    void deactivate(B condition) throws SQLException;
    void activate(B condition) throws SQLException;

}