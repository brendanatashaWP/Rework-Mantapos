package project.blibli.mantapos;

import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import project.blibli.mantapos.Dao.*;
import project.blibli.mantapos.ImplementationDao.MenuDaoImpl;

import java.io.File;

@SpringBootApplication
public class MantaposApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(MantaposApplication.class, args);
		MenuDaoImpl menuDao = new MenuDaoImpl();
		menuDao.CreateTable();
		OrderDao.CreateTable();
		OrderedMenuDao.CreateTable();
		RestaurantDao.CreateTable();
		UserDao.CreateTable();
	}

}
