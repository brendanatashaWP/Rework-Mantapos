package project.blibli.mantapos.NewInterfaceDao;

import project.blibli.mantapos.Model.Pengeluaran;

import java.sql.SQLException;

public interface PengeluaranDao extends DaoInterface<Pengeluaran, String> {

    int getTotalPengeluaran(String condition) throws SQLException;

}
