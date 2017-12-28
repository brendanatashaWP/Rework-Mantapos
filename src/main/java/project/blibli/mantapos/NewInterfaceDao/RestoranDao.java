package project.blibli.mantapos.NewInterfaceDao;

import project.blibli.mantapos.Model.Restoran;
import project.blibli.mantapos.NewImplementationDao.BasicDao;

import java.util.List;

public interface RestoranDao extends BasicDao<Restoran, Integer, Integer, Integer, Integer> {
    int readIdResto(String usernameUser);
    List<Restoran> readAllRestoran(String role);
    int countRestoran();
}
