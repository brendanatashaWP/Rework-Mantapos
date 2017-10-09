package project.blibli.mantapos.InterfaceDao;

import project.blibli.mantapos.Beans_Model.Order;

import java.util.List;

public interface OrderDao {
    void CreateTable();
    int Insert(Order order);
    List<Order> getAll();
    int getLastOrderId();
}
