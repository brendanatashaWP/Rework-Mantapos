package project.blibli.mantapos.InterfaceDao;

//Interface yang menangani masalah DAO berkaitan dengan detail menu yang dipesan
public interface MenuYangDipesanDao {
    void CreateTable();
    void Insert(int lastId_IdOrder, int id_order_menu, int qty_insert);
}
