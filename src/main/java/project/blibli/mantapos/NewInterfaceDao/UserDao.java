package project.blibli.mantapos.NewInterfaceDao;

import project.blibli.mantapos.Model.User;

import java.sql.SQLException;

public interface UserDao extends DaoInterface<User, String> {

    void createRoleUsers() throws SQLException;
    void createTableUsersRole() throws SQLException;
    void insertTableUsersRole(User modelData) throws SQLException;
    void updateTableUsersRole(User modelData, String condition) throws SQLException;

}
