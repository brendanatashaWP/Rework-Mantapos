package project.blibli.mantapos.InterfaceDao;

import project.blibli.mantapos.Model.Menu;

import java.util.List;

public interface MenuDao {
    void CreateTable();
    void Insert(int id_restoo, Menu menu);
    List<Menu> getAllMenu(int id_restoo, int itemPerPage, int page);
    int getLastId(int id_restoo);
    void DeleteMenu(int id);
    List<Menu> getMenuById(int id_restoo, int id_menu);
    void UpdateMenu(int id_restoo, Menu menu);
    int jumlahMenu(int id_restoo);
}
