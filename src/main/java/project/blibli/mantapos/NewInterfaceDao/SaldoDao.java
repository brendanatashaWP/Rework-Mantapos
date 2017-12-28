package project.blibli.mantapos.NewInterfaceDao;

import project.blibli.mantapos.Model.SaldoAwal;
import project.blibli.mantapos.NewImplementationDao.BasicDao;

public interface SaldoDao extends BasicDao<SaldoAwal, Integer, Integer, Integer, Integer> {
    int getSaldoAwal(int idResto, int monthh, int yearr);
    int getSaldoAwalCustom(int idResto, int tanggal1, int month1, int year1);
}
