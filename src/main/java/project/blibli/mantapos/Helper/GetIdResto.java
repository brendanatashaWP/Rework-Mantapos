package project.blibli.mantapos.Helper;

import project.blibli.mantapos.ImplementationDao.RestoranDaoImpl;

public class GetIdResto {

    static RestoranDaoImpl restoranDao = new RestoranDaoImpl();

    public static int getIdRestoBasedOnUsernameTerkait(String username){
        int idResto = restoranDao.readIdRestoBasedOnUsernameRestoTerkait(username);
        return idResto;
    }

    public static int getIdRestoBasedOnNamaResto(String namaResto){
        int idResto = restoranDao.readIdRestoBasedOnNamaResto(namaResto);
        return idResto;
    }

}
