package project.blibli.mantapos.NewInterfaceDao;

import java.sql.SQLException;
import java.util.List;

public interface DaoInterface<A,B> {

    //A adalah nama model (POJO)
    //B adalah HashMap tempat menampung segala condition untuk CRUD nantinya

    void createTable() throws SQLException;
    void insert(A modelData) throws SQLException;
    List<A> readAll(B condition) throws SQLException;
    A readOne(B condition) throws SQLException;
    int readId(B condition) throws SQLException;
    int count(B condition) throws SQLException;
    void deactivate(B condition) throws SQLException;
    void activate(B condition) throws SQLException;

}