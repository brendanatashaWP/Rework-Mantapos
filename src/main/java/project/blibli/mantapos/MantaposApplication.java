package project.blibli.mantapos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
	}

}
