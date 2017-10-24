package project.blibli.mantapos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import project.blibli.mantapos.ImplementationDao.*;

import java.io.File;

@SpringBootApplication
public class MantaposApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(MantaposApplication.class, args);

		MenuDaoImpl menuDao = new MenuDaoImpl();
		menuDao.CreateTable();

		OrderDaoImpl orderDao = new OrderDaoImpl();
		orderDao.CreateTable();

		IncomeDaoImpl incomeDao = new IncomeDaoImpl();
		incomeDao.CreateTable();
		SaldoDaoImpl saldoDao = new SaldoDaoImpl();
		saldoDao.CreateTable();
		TotalDebitKreditDaoImpl totalDebitKreditDao = new TotalDebitKreditDaoImpl();
		totalDebitKreditDao.CreateTable();

		OutcomeDaoImpl outcomeDao = new OutcomeDaoImpl();
		outcomeDao.CreateTable();

		OrderedMenuDaoImpl orderedMenuDao = new OrderedMenuDaoImpl();
		orderedMenuDao.CreateTable();

		RestaurantDaoImpl restaurantDao = new RestaurantDaoImpl();
		restaurantDao.CreateTable();

		UserDaoImpl userDao = new UserDaoImpl();
		try{
			userDao.CreateRole();
		} catch (Exception ex){
			System.out.println("Create role exception : " + ex.toString());
		}
		userDao.CreateTableUser();
		userDao.CreateTableRole();
	}

}
