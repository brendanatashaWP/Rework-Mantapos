package project.blibli.mantapos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import project.blibli.mantapos.Model.Restoran;
import project.blibli.mantapos.Model.User;
import project.blibli.mantapos.ImplementationDao.*;

@SpringBootApplication
public class MantaposApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(MantaposApplication.class, args);

		RestoranDaoImpl restaurantDao = new RestoranDaoImpl();
		restaurantDao.CreateTable();
//		Restoran restoran = new Restoran();
//		restoran.setNama_resto("ADMIN"); restoran.setLokasi_resto("ADMIN");
//		restaurantDao.Insert(restoran);

		UserDaoImpl userDao = new UserDaoImpl();
		userDao.CreateRole();

		userDao.CreateTableUser();
		userDao.CreateTableRole();
//		User user = new User();
//		user.setNama_lengkap("Axellageraldinc"); user.setAlamat("Terban");
//		user.setNomor_telepon("08123451234"); user.setNomor_ktp("123123123123123");
//		user.setPassword("axell123"); user.setUsername("axell");
//		user.setRole("admin"); user.setId_resto(1);
//		userDao.Insert(user);

		MenuDaoImpl menuDao = new MenuDaoImpl();
		menuDao.CreateTable();
//
		LedgerDaoImpl orderDao = new LedgerDaoImpl();
		orderDao.CreateTipe();
		orderDao.CreateTable();
//
		SaldoDaoImpl saldoDao = new SaldoDaoImpl();
		saldoDao.CreateTable();
//		saldoDao.AddSaldoAwal(1, 500000);

		MenuYangDipesanDaoImpl orderedMenuDao = new MenuYangDipesanDaoImpl();
		orderedMenuDao.CreateTable();
	}

}
