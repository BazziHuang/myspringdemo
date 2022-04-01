package per.huang.demo.mystock.repository;

import java.util.List;
import java.util.Optional;

public interface FundDao<T> {

    Optional<List<T>> findAll();
    Optional<T> findById(Integer id);
    Optional<List<T>> findByName(String name);
    Integer insert(T data);
    Integer deleteById(Integer id);
    Integer update(T data);
    Optional<List<T>> findDataWithLimit(Integer offset, Integer limit);
    int count();
    
}
