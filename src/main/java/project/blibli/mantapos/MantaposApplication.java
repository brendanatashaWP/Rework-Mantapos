package project.blibli.mantapos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import project.blibli.mantapos.Model.Restoran;
import project.blibli.mantapos.Model.User;
import project.blibli.mantapos.NewImplementationDao.*;

@SpringBootApplication
public class MantaposApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(MantaposApplication.class, args);

		RestoranDaoImpl restoranDao = new RestoranDaoImpl();
		restoranDao.createTable();

		UserDaoImpl userDao = new UserDaoImpl();
		userDao.createRoleUser();
		userDao.createTable();

		MenuDaoImpl menuDao = new MenuDaoImpl();
		menuDao.createTable();

		LedgerDaoImpl ledgerDao = new LedgerDaoImpl();
		ledgerDao.createTipeLedger();
		ledgerDao.createTable();

		MenuYangDipesanDaoImpl menuYangDipesanDao = new MenuYangDipesanDaoImpl();
		menuYangDipesanDao.createTable();

		SaldoDaoImpl saldoDao = new SaldoDaoImpl();
		saldoDao.createTable();

//		RestoranDaoImpl restaurantDao = new RestoranDaoImpl();
//		restaurantDao.CreateTable();
//		Restoran restoran = new Restoran();
//		restoran.setNamaResto("ADMIN"); restoran.setLokasiResto("ADMIN");
//		restoranDao.insert(restoran, 0);
//
//		UserDaoImpl userDao = new UserDaoImpl();
//		userDao.CreateRole();
//
//		userDao.CreateTableUser();
//		userDao.CreateTableRole();
//		User user = new User();
//		user.setNamaLengkap("Axellageraldinc"); user.setAlamat("Terban");
//		user.setJenisKelamin("L");
//		user.setNomorTelepon("08123451234"); user.setNomorKtp("123123123123123");
//		user.setPassword("axell123"); user.setUsername("axell");
//		user.setRole("admin"); user.setIdResto(1);
//		userDao.insert(user, 10);
//
//		MenuDaoImpl menuDao = new MenuDaoImpl();
//		menuDao.CreateTable();
////
//		LedgerDaoImpl orderDao = new LedgerDaoImpl();
//		orderDao.CreateTipe();
//		orderDao.CreateTable();
////
//		SaldoDaoImpl saldoDao = new SaldoDaoImpl();
//		saldoDao.CreateTable();
////		saldoDao.AddSaldoAwal(1, 500000);
//
//		MenuYangDipesanDaoImpl orderedMenuDao = new MenuYangDipesanDaoImpl();
//		orderedMenuDao.CreateTable();
	}

}
