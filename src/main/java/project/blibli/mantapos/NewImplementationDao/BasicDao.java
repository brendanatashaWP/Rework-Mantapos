package project.blibli.mantapos.NewImplementationDao;

import java.sql.SQLException;
import java.util.List;

public interface BasicDao<A,B,C,D,E> {
    //A adalah nama Model
    //B adalah idResto
    //C adalah itemPerPage
    //D adalah page ke berapa
    //E adalah id data

    void createTable() throws SQLException;
    void insert(A modelData, B idResto);
    List<A> readAll(B idResto, C itemPerPage, D page);
    A readOne(E idData);
    int getLastId(B idResto);
    void update(A modelData, B idResto);
    void delete(E idData);
    int count(B idResto);
}
