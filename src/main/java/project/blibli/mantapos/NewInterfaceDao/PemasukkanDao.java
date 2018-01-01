package project.blibli.mantapos.NewInterfaceDao;

import project.blibli.mantapos.Model.Pemasukkan;

import java.sql.SQLException;

public interface PemasukkanDao extends DaoInterface<Pemasukkan, String> {

    int getTotalPemasukkan(String condition) throws SQLException;

}
