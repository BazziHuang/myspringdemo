package per.huang.demo.mystock.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import per.huang.demo.mystock.entity.Fund;

@Repository
public interface FundDao extends JpaRepository<Fund, Integer>{

    Optional<Fund> findById(Integer fund_id);
    Optional<Fund> findByName(String fund_name);
    
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO fund(fund_name) SELECT :fund_name", nativeQuery = true)
    Integer insertIntoFund(String fund_name);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM fund f WHERE f.fund_id = :fund_id", nativeQuery = true)
    Integer deleteFundById(Integer fund_id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE fund SET fund_name= :fund_name WHERE fund_id= :fund_id", nativeQuery = true)
    Integer updateFund(Integer fund_id, String fund_name);

    @Transactional
    @Modifying
    @Query(value = "SELECT * " + 
                    "FROM fund ORDER BY fund_id OFFSET ?1 LIMIT ?2", nativeQuery = true)
    Optional<List<Fund>> findFundsWithLimit(Integer offset, Integer limit);

}
