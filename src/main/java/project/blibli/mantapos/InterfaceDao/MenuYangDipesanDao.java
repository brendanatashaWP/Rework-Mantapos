package project.blibli.mantapos.InterfaceDao;

public interface MenuYangDipesanDao {
    void CreateTable();
    void Insert(int lastId_IdOrder, int id_order_menu, int qty_insert);
}
