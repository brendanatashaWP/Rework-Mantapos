package project.blibli.mantapos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import project.blibli.mantapos.Dao.MenuDao;
import project.blibli.mantapos.Dao.OrderDao;
import project.blibli.mantapos.Dao.OrderedMenuDao;
import project.blibli.mantapos.Dao.RestaurantDao;

@SpringBootApplication
public class MantaposApplication {

	public static void main(String[] args) {
		SpringApplication.run(MantaposApplication.class, args);
		MenuDao.CreateTable();
		OrderDao.CreateTable();
		OrderedMenuDao.CreateTable();
		RestaurantDao.CreateTable();
	}
}
