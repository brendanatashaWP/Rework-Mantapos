package project.blibli.mantapos.InterfaceDao;

import project.blibli.mantapos.Model.User;

import java.util.List;

public interface UserDao {
    void CreateRole();
    void CreateTableUser();
    void CreateTableRole();
    void Insert(User user);
    List<User> getAllUser(int id_restoo, String role, int itemPerPage, int page);
    int GetUserIdBerdasarkanUsername(String username);
    void DeleteUser(int id);
    void DeleteUserAndDependencies(int id_restoo);
    void ActivateUser(int id);
    List<User> GetUserById(int id_restoo, int userId);
    void UpdateUser(int id_restoo, User user);
    int jumlahEmployee(int id_restoo);
}
