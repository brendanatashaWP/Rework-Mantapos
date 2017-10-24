package project.blibli.mantapos.ImplementationDao;

import org.springframework.jdbc.core.JdbcTemplate;
import project.blibli.mantapos.Config.DataSourceConfig;
import project.blibli.mantapos.InterfaceDao.MenuYangDipesanDao;

public class MenuYangDipesanDaoImpl implements MenuYangDipesanDao {

    private static final String table_name = "menu_yang_dipesan";
    private static final String id_order = "id_order";
    private static final String id_menu = "id_menu";
    private static final String qty = "quantity";
    private static final String ref_table_order = "ledger_harian";
    private static final String ref_table_menu = "menu";

    private JdbcTemplate jdbcTemplate = new JdbcTemplate();

    public MenuYangDipesanDaoImpl(){
        jdbcTemplate.setDataSource(DataSourceConfig.dataSource());
    }

    @Override
    public void CreateTable() {
        String query = "CREATE TABLE IF NOT EXISTS " + table_name +
                "(" +
                id_order + " INT NOT NULL, " +
                id_menu + " INT NOT NULL, " +
                qty + " INT NOT NULL, " +
                "CONSTRAINT id_order_fk FOREIGN KEY (" + id_order + ") REFERENCES " + ref_table_order + "(id)," +
                "CONSTRAINT id_menu_fk FOREIGN KEY (" + id_menu + ") REFERENCES " + ref_table_menu + "(id))";
        try{
            jdbcTemplate.execute(query);
        } catch (Exception ex){
            System.out.println("Gagal create table menu_yang_dipesan : " + ex.toString());
        }
    }

    @Override
    public void Insert(int lastId_IdOrder, int id_order_menu, int qty_insert) {
        String query = "INSERT INTO " + table_name +
                "(" +
                id_order + "," +
                id_menu + "," +
                qty + ")" +
                " VALUES (?,?,?)";
        try{
            jdbcTemplate.update(query, new Object[]{
                    lastId_IdOrder, id_order_menu, qty_insert
            });
        } catch (Exception ex){
            System.out.println("Gagal insert menu_yang_dipesan : " + ex.toString());
        }
    }
}
