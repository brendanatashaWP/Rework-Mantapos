package project.blibli.mantapos.InterfaceDao;

import project.blibli.mantapos.Model.Restoran;

import java.util.List;

public interface RestoranDao {
    void CreateTable();
    void Insert(Restoran restoran);
    Restoran GetRestaurantInfo(String username);
    int GetRestoranId(String username);
    List<Restoran> GetRestoranList(int itemPerPage, int page);
    int GetRestoranIdBerdasarkanNamaResto(String nama_resto);
    int jumlahRestoran();
}
