package project.blibli.mantapos.InterfaceDao;

import project.blibli.mantapos.ImplementationDao.BasicDao;

public interface MenuYangDipesanDao extends BasicDao<Void, Integer, Integer, Integer, Integer> {
    void insertMenuYangDipesan(int idOrder, int idMenu, int qty);
}
