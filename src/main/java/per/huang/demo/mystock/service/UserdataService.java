package per.huang.demo.mystock.service;

import java.util.List;

import per.huang.demo.mystock.entity.Userdata;

public interface UserdataService {

    List<Userdata> getAllData();
    Userdata getDataById(Integer id);
    Userdata getDataByNameOrEmail(String name);
    String getPassword(String name);
    String getEmail(String name);
    int addData(Userdata userdata);
    int updateData(Userdata userdata);
    int updateLoginTime(Integer id);
    int deleteData(Integer id);
    
}
