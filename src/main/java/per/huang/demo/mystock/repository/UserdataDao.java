package per.huang.demo.mystock.repository;


import java.util.Date;
import java.util.List;
import java.util.Optional;


import per.huang.demo.mystock.entity.Userdata;

public interface UserdataDao {

	Optional<List<Userdata>> findAll();
	Optional<Userdata> findById(int id);
	Optional<Userdata> findByName(String name);
	Optional<Userdata> findByEmail(String email);
	int insert(Userdata userdata);
	int update(Userdata userdata);
	int updateLoginTime(String name, Date time);
	int deleteById(int id);
    
    
}
