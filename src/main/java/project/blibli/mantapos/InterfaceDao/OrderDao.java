package project.blibli.mantapos.InterfaceDao;

import project.blibli.mantapos.Beans_Model.Order;

import java.util.List;

public interface OrderDao {
    void CreateTable();
    int Insert(Order order, int id_restoo);
    List<Order> getOrderPriceTotal(int id_restoo, int monthh, int yearr);
    int getLastOrderId();
}
