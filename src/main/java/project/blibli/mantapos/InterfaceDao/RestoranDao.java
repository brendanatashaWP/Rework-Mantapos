package project.blibli.mantapos.InterfaceDao;

import project.blibli.mantapos.Model.Restoran;

import java.util.List;

public interface RestoranDao extends BasicDao<Restoran, Integer, Integer, Integer, Integer> {
    int readIdRestoBasedOnUsernameRestoTerkait(String usernameUser);
    int readIdRestoBasedOnNamaResto(String namaResto);
    List<Restoran> readAllRestoran(String role);
    int countRestoran();
}
