package project.blibli.mantapos.Mapper;

import org.springframework.jdbc.core.RowMapper;
import project.blibli.mantapos.Beans_Model.Menu;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MenuMapper implements RowMapper<Menu>{
    @Override
    public Menu mapRow(ResultSet resultSet, int i) throws SQLException {
        Menu menu = new Menu();
        menu.setId_menu(resultSet.getInt("id"));
        menu.setName_menu(resultSet.getString("name"));
        menu.setPrice_total_menu(resultSet.getInt("price"));
        menu.setPhoto_location_menu(resultSet.getString("photo_link"));
        menu.setCategory_menu(resultSet.getString("category"));
        return menu;
    }
}
