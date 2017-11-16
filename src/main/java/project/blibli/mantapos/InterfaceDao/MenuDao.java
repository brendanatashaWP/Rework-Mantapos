package project.blibli.mantapos.InterfaceDao;

import project.blibli.mantapos.Beans_Model.Menu;

import java.util.List;

public interface MenuDao {
    void CreateTable();
    void Insert(int id_restoo, Menu menu);
    List<Menu> getAllMenu(int id_restoo);
    int getLastId(int id_restoo);
    void DeleteMenu(int id);
    List<Menu> getMenuById(int id_restoo, int id_menu);
    void UpdateMenu(int id_restoo, Menu menu);
}
