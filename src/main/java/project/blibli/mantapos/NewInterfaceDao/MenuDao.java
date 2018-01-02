package project.blibli.mantapos.NewInterfaceDao;

import project.blibli.mantapos.Model.Menu;

import java.sql.SQLException;

public interface MenuDao extends DaoInterface<Menu, String> {
    int getLastId(String condition) throws SQLException;
}
