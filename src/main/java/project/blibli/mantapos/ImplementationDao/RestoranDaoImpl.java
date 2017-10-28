package project.blibli.mantapos.ImplementationDao;

import org.springframework.jdbc.core.JdbcTemplate;
import project.blibli.mantapos.Beans_Model.Restoran;
import project.blibli.mantapos.Config.DataSourceConfig;
import project.blibli.mantapos.InterfaceDao.RestoranDao;
import project.blibli.mantapos.Mapper.RestaurantMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
                lokasi_resto + " TEXT NOT NULL, " +
                "UNIQUE (" + nama_resto + "))";
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
    public List<Restoran> GetRestoranList() {
        List<Restoran> restoranList = new ArrayList<>();
        String query = "SELECT" +
                " restoran.id, restoran.nama_resto, restoran.lokasi_resto, " +
                " users.id, users.nama_lengkap, users.username, users.enabled, users.nomor_ktp, users.nomor_telepon, users.alamat" +
                " FROM restoran, users, user_roles" +
                " WHERE users.id_resto=restoran.id " +
                "AND user_roles.id=users.id " +
                "AND user_roles.role=?::role_type";
        try{
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(query, new Object[] {"owner"});
            for(Map row : rows){
                Restoran restoran = new Restoran();
                restoran.setId((Integer) row.get("id"));
                restoran.setNama_resto((String) row.get(nama_resto));
                restoran.setLokasi_resto((String) row.get(lokasi_resto));
                restoran.setId_user((Integer) row.get("id"));
                restoran.setNama_lengkap((String) row.get("nama_lengkap"));
                restoran.setEnabled((Boolean) row.get("enabled"));
                restoran.setUsername((String) row.get("username"));
                restoran.setNomor_ktp((String) row.get("nomor_ktp"));
                restoran.setNomor_telepon((String) row.get("nomor_telepon"));
                restoran.setAlamat((String) row.get("alamat"));
                restoranList.add(restoran);
            }
        } catch (Exception ex){
            System.out.println("Gagal get restoran list : " + ex.toString());
        }
        return restoranList;
    }

    @Override
    public int GetRestoranIdBerdasarkanNamaResto(String nama_restoo) {
        int id_restoo=0;
        String query = "SELECT " + id + " FROM " + table_name + " WHERE " + nama_resto + "=?";
        id_restoo = jdbcTemplate.queryForObject(query, new Object[] {nama_restoo}, Integer.class);
        return id_restoo;
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
