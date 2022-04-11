package per.huang.demo.mystock.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import per.huang.demo.mystock.entity.Userdata;
import per.huang.demo.mystock.exception.InvalidInputException;
import per.huang.demo.mystock.exception.DataNotFoundException;
import per.huang.demo.mystock.exception.DataAlreadyExistsException;
import per.huang.demo.mystock.repository.UserdataDao;

@Service
public class UserdataServiceImpl implements UserdataService {

    @Autowired
    UserdataDao userdataDao;

    @Override
    public List<Userdata> getAllData() {
        return userdataDao.findAll().get();
    }

    @Override
    public Userdata getDataById(Integer id) {
        if (id == null || id <= 0) {
            throw new InvalidInputException("user_id can not be null or less than zero.", "user_id");
        }
        Userdata userdata = userdataDao.findById(id)
                .orElseThrow(() -> new DataNotFoundException("user_id not found.", "user_id"));
        return userdata;
    }

    @Override
    public Userdata getDataByNameOrEmail(String name) {
        name = name.trim();
        if (name == null || name.length() == 0) {
            throw new InvalidInputException("user_name can not be null or empty.", "user_name");
        }
        Userdata userdata = null;
        if (name.contains("@")) {
            userdata = userdataDao.findByEmail(name)
                    .orElseThrow(() -> new DataNotFoundException("user_email not found.", "user_email"));
        } else {
            userdata = userdataDao.findByName(name)
                    .orElseThrow(() -> new DataNotFoundException("user_name not found.", "user_name"));
        }
        return userdata;
    }

    @Override
    public String getPassword(String name) {
        Userdata userdata = getDataByNameOrEmail(name);
        return userdata.getPassword();
    }

    @Override
    public String getEmail(String name) {
        Userdata userdata = getDataByNameOrEmail(name);
        return userdata.getEmail();
    }

    @Override
    public int addData(Userdata userdata) {
        String name = userdata.getName();
        String email = userdata.getEmail();
        if (name == null) {
            throw new InvalidInputException("input user_name can not be null.", "user_name");
        } else if (email == null) {
            throw new InvalidInputException("input user_email can not be null.", "user_email");
        }
        Userdata existData = userdataDao.findByName(name).orElse(null);
        if (existData != null) {
            throw new DataAlreadyExistsException("user_name already exists.", "user_name");
        }
        existData = userdataDao.findByEmail(email).orElse(null);
        if (existData != null) {
            throw new DataAlreadyExistsException("user_email already exists.", "user_email");
        }
        return userdataDao.insert(userdata);
    }

    @Override
    public int updateData(Userdata userdata) {
        Integer id = userdata.getId();
        String name = userdata.getName();
        String email = userdata.getEmail();
        String password = userdata.getPassword();
        if (name == null) {
            throw new InvalidInputException("input user_name can not be null.", "user_name");
        } else if (email == null) {
            throw new InvalidInputException("input user_email can not be null.", "user_email");
        } else if (password == null) {
            throw new InvalidInputException("input user_password can not be null.", "user_password");
        }
        Userdata oldData = userdataDao.findById(id).get();
        Userdata existData = userdataDao.findByName(name).orElse(null);
        if (!oldData.getName().equals(name)) {
            // 名稱有更改，驗證新名稱有無重複
            if (existData != null) {
                throw new DataAlreadyExistsException("user_name already exists.", "user_name");
            }
        }
        if (!oldData.getEmail().equals(email)) {
            // 信箱有更改，驗證新信箱有無重複
            existData = userdataDao.findByEmail(email).orElse(null);
            if (existData != null) {
                throw new DataAlreadyExistsException("user_email already exists.", "user_email");
            }
        }
        return userdataDao.update(userdata);
    }

    @Override
    public int updateLoginTime(Integer id) {
        userdataDao.findById(id).orElseThrow(() -> new DataNotFoundException("user_id not found.", "user_id"));
        Date time = new Date();
        return userdataDao.updateLoginTime(id, time);
    }

    @Override
    public int deleteData(Integer id) {
        userdataDao.findById(id).orElseThrow(() -> new DataNotFoundException("user_id not found.", "user_id"));
        return userdataDao.deleteById(id);
    }

}
