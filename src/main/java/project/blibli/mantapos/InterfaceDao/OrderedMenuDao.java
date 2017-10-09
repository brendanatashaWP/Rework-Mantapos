package project.blibli.mantapos.InterfaceDao;

public interface OrderedMenuDao {
    void CreateTable();
    int Insert(int lastId_IdOrder, int id_order_menu, int qty_insert);
}
