package per.huang.demo.mystock.repository;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserdataDao {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> queryAll() {
		String query="SELECT user_id, user_name, user_email from userdata";
		List<Map<String, Object>> user= jdbcTemplate.queryForList(query);
		return user;
	}
    
}
