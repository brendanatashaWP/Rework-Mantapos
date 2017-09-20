package project.blibli.mantapos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import project.blibli.mantapos.Beans_Model.Category;
import project.blibli.mantapos.Dao.CategoryDao;
import project.blibli.mantapos.Dao.MenuDao;

@SpringBootApplication
public class MantaposApplication {

	public static void main(String[] args) {
		SpringApplication.run(MantaposApplication.class, args);
		CategoryDao.CreateTable();
		MenuDao.CreateTable();
	}
}
