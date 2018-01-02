package project.blibli.mantapos.Helper;

import project.blibli.mantapos.NewImplementationDao.RestoranDaoImpl;

import java.sql.SQLException;

public class GetIdResto {

    static RestoranDaoImpl restoranDao = new RestoranDaoImpl();

    public static int getIdRestoBasedOnUsernameTerkait(String username) throws SQLException {
        int idResto = restoranDao.getIdBerdasarkanUsernameUser("users.username='" + username + "'");
        return idResto;
    }

    public static int getIdRestoBasedOnNamaResto(String namaResto) throws SQLException {
        int idResto = restoranDao.getId("nama_restoran='" + namaResto + "'");
        return idResto;
    }

}
