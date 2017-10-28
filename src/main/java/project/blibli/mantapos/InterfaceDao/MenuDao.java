package project.blibli.mantapos.InterfaceDao;

import project.blibli.mantapos.Beans_Model.Menu;

import java.util.List;

public interface MenuDao {
    void CreateTable();
    void Insert(int id_restoo, Menu menu, int user_id);
    List<Menu> getAllMenu(int id_restoo);
    int getLastId(int id_restoo);
    void DeleteMenu(int id);
}
