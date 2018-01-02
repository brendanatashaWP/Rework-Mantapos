package project.blibli.mantapos.NewInterfaceDao;

import project.blibli.mantapos.Model.Restoran;

import java.sql.SQLException;

public interface RestoranDao extends DaoInterface<Restoran, String> {
    int getIdBerdasarkanUsernameUser(String condition) throws SQLException;
}
