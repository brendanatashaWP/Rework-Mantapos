package project.blibli.mantapos.NewInterfaceDao;

import project.blibli.mantapos.Model.User;

import java.sql.SQLException;
import java.util.HashMap;

public interface UserDao extends DaoInterface<User, HashMap<Integer, String>> {

    void createUsersRole() throws SQLException;
    void createTableUsersRole() throws SQLException;
    void insertToTableUsersRole(HashMap<Integer, String> condition) throws SQLException;

}
