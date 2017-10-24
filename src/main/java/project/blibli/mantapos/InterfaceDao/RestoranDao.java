package project.blibli.mantapos.InterfaceDao;

import project.blibli.mantapos.Beans_Model.Restoran;

public interface RestoranDao {
    void CreateTable();
    void Insert(Restoran restoran);
    Restoran GetRestaurantInfo(String username);
    int GetRestoranId(String username);
}
