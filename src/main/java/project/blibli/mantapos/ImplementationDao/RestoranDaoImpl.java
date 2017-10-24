package project.blibli.mantapos.ImplementationDao;

import org.springframework.jdbc.core.JdbcTemplate;
import project.blibli.mantapos.Beans_Model.Restoran;
import project.blibli.mantapos.Config.DataSourceConfig;
import project.blibli.mantapos.InterfaceDao.RestoranDao;
import project.blibli.mantapos.Mapper.RestaurantMapper;

public class RestoranDaoImpl implements RestoranDao {
    private static final String table_name = "restoran";
    private static final String id = "id";
    private static final String nama_resto = "nama_resto";
    private static final String lokasi_resto = "lokasi_resto";

    private JdbcTemplate jdbcTemplate = new JdbcTemplate();

    public RestoranDaoImpl(){
        jdbcTemplate.setDataSource(DataSourceConfig.dataSource());
    }

    @Override
    public void CreateTable() {
        String query = "CREATE TABLE IF NOT EXISTS " + table_name +
                "(" +
                id + " SERIAL PRIMARY KEY, " +
                nama_resto + " TEXT NOT NULL, " +
                lokasi_resto + " TEXT NOT NULL)";
        try{
            jdbcTemplate.execute(query);
        } catch (Exception ex){
            System.out.println("Gagal create table restoran : " + ex.toString());
        }
    }

    @Override
    public void Insert(Restoran restoran) {
        String query = "INSERT INTO " + table_name +
                "(" +
                nama_resto + "," +
                lokasi_resto +
                ")" +
                " VALUES (?,?)";
        try{
            jdbcTemplate.update(query, new Object[]{
                    restoran.getNama_resto(), restoran.getLokasi_resto()
            });
        } catch (Exception ex){
            System.out.println("Gagal insert restoran baru : " + ex.toString());
        }
    }

    @Override
    public int GetRestoranId(String username) {
        int id_resto = 0;
        String query = "SELECT users.id_resto" +
                " FROM users, restoran WHERE users.id_resto=restoran.id AND users.username=?";
        try{
            id_resto = jdbcTemplate.queryForObject(query, new Object[] {username}, Integer.class);
        } catch (Exception ex){
            System.out.println("Gagal get restoran info : " + ex.toString());
        }
        return id_resto;
    }

    @Override
    public Restoran GetRestaurantInfo(String username) {
        Restoran restoran = new Restoran();
        String query = "SELECT users.id_resto," +
                "restoran.id," +
                "restoran.nama_resto," +
                "restoran.lokasi_resto" +
                " FROM users, restoran WHERE users.id_resto=restoran.id AND users.username=?";
        try{
            restoran = jdbcTemplate.queryForObject(query, new Object[] {username}, new RestaurantMapper());
        } catch (Exception ex){
            System.out.println("Gagal get restoran info : " + ex.toString());
        }
        return restoran;
    }
}
