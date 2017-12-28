package project.blibli.mantapos.ImplementationDao;

import java.util.List;

public interface BasicDao<A,B,C,D> {
    void createTable();
    void createRole();
    int insert(B idResto, A modelData);
    List<A> readAll(B idResto, C itemPerPage, D page);
    A readOne();
    int getId( );
    int update(A modelData);
    void delete(A modelData);
    int count(B idResto);
}
