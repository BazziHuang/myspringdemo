package per.huang.demo.mystock.service;

import java.util.List;

public interface FundService<T> {
    
    int LIMIT = 5;

    List<T> getAllData();
    List<T> getDataWithLimit(Integer offset, Integer limit);
    T getDataById(Integer id);
    int getDataCount();
    int addData(T data);
    int updateData(T data);
    int deleteData(Integer id);

}
