package project.blibli.mantapos.InterfaceDao;

import project.blibli.mantapos.Model.User;
import project.blibli.mantapos.ImplementationDao.BasicDao;

import java.util.List;

public interface UserDao extends BasicDao<User, Integer, Integer, Integer, Integer> {
    void createRoleUser();
    void deleteUserAndDependencies(int idResto);
    void activateUser(int id);
    List<User> readAllUsers(int idResto, String role, int itemPerPage, int page);
    int getId(String username);
}
