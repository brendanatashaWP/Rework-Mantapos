package project.blibli.mantapos.InterfaceDao;

import project.blibli.mantapos.Beans_Model.User;

public interface UserDao {
    void CreateRole();
    void CreateTableUser();
    void CreateTableRole();
    int Insert(User user);
}
