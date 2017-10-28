package project.blibli.mantapos.InterfaceDao;

import project.blibli.mantapos.Beans_Model.User;

import java.util.List;

public interface UserDao {
    void CreateRole();
    void CreateTableUser();
    void CreateTableRole();
    void Insert(User user);
    List<User> getAllUser(int id_restoo, String role);
    int GetUserIdBerdasarkanUsername(String username);
    void DeleteUser(int id);
    void DeleteUserAndDependencies(int id_restoo);
    void ActivateUser(int id);
}
