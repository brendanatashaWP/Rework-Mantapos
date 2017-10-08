package project.blibli.mantapos.InterfaceDao;

import project.blibli.mantapos.Beans_Model.Menu;

import java.util.List;

public interface MenuDao {
    void CreateTable();
    int Insert(Menu menu);
    List<Menu> getAllMenu();
    String getMenuById(int id);
}
