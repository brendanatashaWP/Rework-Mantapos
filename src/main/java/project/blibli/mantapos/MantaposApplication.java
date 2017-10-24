package project.blibli.mantapos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import project.blibli.mantapos.Beans_Model.Restoran;
import project.blibli.mantapos.Beans_Model.User;
import project.blibli.mantapos.ImplementationDao.*;

@SpringBootApplication
public class MantaposApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(MantaposApplication.class, args);

		RestoranDaoImpl restaurantDao = new RestoranDaoImpl();
		restaurantDao.CreateTable();
		Restoran restoran = new Restoran();
		restoran.setNama_resto("Afui"); restoran.setLokasi_resto("Jakal");
//		restaurantDao.Insert(restoran);

		MenuDaoImpl menuDao = new MenuDaoImpl();
		menuDao.CreateTable();

		LedgerDaoImpl orderDao = new LedgerDaoImpl();
		orderDao.CreateTipe();
		orderDao.CreateTable();

//		IncomeDaoImpl incomeDao = new IncomeDaoImpl();
//		incomeDao.CreateTable();
		SaldoDaoImpl saldoDao = new SaldoDaoImpl();
		saldoDao.CreateTable();
//		saldoDao.AddSaldoAwal(1, 500000);
//		TotalDebitKreditDaoImpl totalDebitKreditDao = new TotalDebitKreditDaoImpl();
//		totalDebitKreditDao.CreateTable();

//		OutcomeDaoImpl outcomeDao = new OutcomeDaoImpl();
//		outcomeDao.CreateTable();

		MenuYangDipesanDaoImpl orderedMenuDao = new MenuYangDipesanDaoImpl();
		orderedMenuDao.CreateTable();

		UserDaoImpl userDao = new UserDaoImpl();
		try{
			userDao.CreateRole();
		} catch (Exception ex){
			System.out.println("Create role exception : " + ex.toString());
		}
		userDao.CreateTableUser();
		userDao.CreateTableRole();
		User user = new User();
		user.setNama_lengkap("Axellageraldinc"); user.setAlamat("Terban");
		user.setNomor_telepon("08123451234"); user.setNomor_ktp("123123123123123");
		user.setPassword("axell123"); user.setUsername("axell");
		user.setRole("manager"); user.setId_resto(1);
//		userDao.Insert(user);
	}

}
