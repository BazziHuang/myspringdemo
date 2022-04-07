package per.huang.demo.mystock.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.simpleflatmapper.jdbc.spring.JdbcTemplateMapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import per.huang.demo.mystock.entity.Userdata;

@Repository
public class UserdataDaoImpl implements UserdataDao{

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Optional<List<Userdata>> findAll() {
        String sql = "SELECT user_id AS id, user_name AS name, user_password AS password, user_email AS email, createtime, last_login AS last_login FROM userdata ORDER BY user_id";
        ResultSetExtractor<List<Userdata>> resultSetExtractor = 
            JdbcTemplateMapperFactory.newInstance().addKeys("id").newResultSetExtractor(Userdata.class);
        return Optional.of(jdbcTemplate.query(sql, resultSetExtractor));
    }

    @Override
    public Optional<Userdata> findById(int id) {
        String sql = "SELECT user_id AS id, user_name AS name, user_password AS password, user_email AS email, createtime, last_login AS last_login FROM userdata WHERE user_id = ?";
        Userdata userdata = null;
        try {
            userdata = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Userdata.class), id);
        } catch (EmptyResultDataAccessException e) {
            System.out.println("UserdataDaoImpl: userdata not found!");
        }
        if(userdata == null){
            return Optional.empty();
        }
        return Optional.of(userdata);
    }

    @Override
    public Optional<Userdata> findByName(String name) {
        //之後創帳號使用此方法確認有無重複的name
        String sql = "SELECT user_id AS id, user_name AS name, user_password AS password, user_email AS email, createtime, last_login AS last_login FROM userdata WHERE LOWER(user_name) = LOWER(?)";
        Userdata userdata = null;
        try {
            userdata = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Userdata.class), name);
        } catch (EmptyResultDataAccessException e) {
            System.out.println("UserdataDaoImpl: userdata not found!");
        }
        if(userdata == null){
            return Optional.empty();
        }
        return Optional.of(userdata);
    }

    @Override
    public Optional<Userdata> findByEmail(String email) {
        String sql = "SELECT user_id AS id, user_name AS name, user_password AS password, user_email AS email, createtime, last_login AS last_login FROM userdata WHERE LOWER(user_email) = LOWER(?)";
        Userdata userdata = null;
        try {
            userdata = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Userdata.class), email);
        } catch (EmptyResultDataAccessException e) {
            System.out.println("UserdataDaoImpl: userdata not found!");
        }
        if(userdata == null){
            return Optional.empty();
        }
        return Optional.of(userdata);
    }

    @Override
    public int insert(Userdata userdata) {
        String sql = "INSERT INTO userdata(user_name, user_password, user_email) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, userdata.getName(), userdata.getPassword(), userdata.getEmail());
    }

    @Override
    public int update(Userdata userdata) {
        String sql = "UPDATE userdata SET user_name=?, user_password=?, user_email=?  WHERE user_id=?";
        return jdbcTemplate.update(sql, userdata.getName(), userdata.getPassword(), userdata.getEmail(), userdata.getId());
    }

    @Override
    public int updateLoginTime(int id, Date time) {
        String sql = "UPDATE userdata SET last_login=? WHERE user_id=?";
        return jdbcTemplate.update(sql, time, id);
    }

    @Override
    public int deleteById(int id) {
        String sql = "DELETE FROM userdata WHERE user_id = ?";
        return jdbcTemplate.update(sql, id);
    }

    

    

    
    
}
