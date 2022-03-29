package per.huang.demo.mystock.repository;

import java.util.List;
import java.util.Optional;

//import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import per.huang.demo.mystock.entity.Fundstock;

@Repository
public interface FundstockDao extends JpaRepository<Fundstock, Integer>{

    Optional<Fundstock> findById(Integer fundstock_id);

    Optional<List<Fundstock>> findBySymbol(String undstock_symbol);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO fundstock(fund_id,fundstock_symbol,fundstock_share) " +
                "SELECT :fund_id, :fundstock_symbol, :fundstock_share"
                ,nativeQuery = true)
    Integer insertIntoFundstock(Integer fund_id, String fundstock_symbol, Integer fundstock_share);

    
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM fundstock f WHERE f.fundstock_id = :fundstock_id", nativeQuery = true)
    Integer deleteFundstockById(Integer fundstock_id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE fundstock SET fundstock_share= :fundstock_share WHERE fundstock_id= :fundstock_id", nativeQuery = true)
    Integer updateShareById(Integer fundstock_id, Integer fundstock_share);

    @Transactional
    @Modifying
    @Query(value = "UPDATE fundstock SET " +
            "fund_id= :fund_id, fundstock_symbol= :fundstock_symbol, fundstock_share= :fundstock_share " + 
            "WHERE fundstock_id= :fundstock_id", nativeQuery = true)
    Integer updateFundstockById(Integer fundstock_id, Integer fund_id, String fundstock_symbol, Integer fundstock_share);

    @Transactional
    @Modifying
    @Query(value = "SELECT * " + 
                    "FROM fundstock ORDER BY fundstock_id OFFSET ?1 LIMIT ?2", nativeQuery = true)
    Optional<List<Fundstock>> findFundstocksWithLimit(Integer offset, Integer limit);
    //fundstock_id, fund_id, fundstock_symbol, fundstock_share


    
}
