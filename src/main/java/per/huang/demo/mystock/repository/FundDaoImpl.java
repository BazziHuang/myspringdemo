package per.huang.demo.mystock.repository;

import java.util.List;
import java.util.Optional;

import org.simpleflatmapper.jdbc.spring.JdbcTemplateMapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import per.huang.demo.mystock.entity.Fund;
import per.huang.demo.mystock.entity.Fundstock;

@Repository
public class FundDaoImpl implements FundDao<Fund> {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Optional<List<Fund>> findAll() {
        String sql = "SELECT f.fund_id AS id, f.fund_name AS name, f.createtime AS createtime, s.fundstock_id AS fundstocks_id, d.stockdata_name AS fundstocks_name, s.fund_id AS fundstocks_fund_id, s.fundstock_symbol AS fundstocks_symbol, s.fundstock_share AS fundstocks_share FROM fund f LEFT OUTER JOIN fundstock s ON f.fund_id = s.fund_id LEFT OUTER JOIN stockdata d ON s.fundstock_symbol = d.stockdata_symbol ORDER BY f.fund_id";
        ResultSetExtractor<List<Fund>> resultSetExtractor = JdbcTemplateMapperFactory.newInstance().addKeys("id")
                .newResultSetExtractor(Fund.class);
        Optional<List<Fund>> optional = Optional.of(jdbcTemplate.query(sql, resultSetExtractor));
        return optional;
    }

    @Override
    public Optional<Fund> findById(Integer id) {
        String sql1 = "SELECT f.fund_id AS id, f.fund_name AS name, f.createtime AS createtime FROM fund f WHERE f.fund_id = ?";
        Fund fund = null;
        try {
            fund = jdbcTemplate.queryForObject(sql1, new BeanPropertyRowMapper<>(Fund.class), id);
        } catch (EmptyResultDataAccessException e) {
            System.out.println("FundDaoImpl: fund not found!");
        }
        if (fund == null) {
            return Optional.empty();
        }
        System.out.println(fund.getName());
        String sql2 = "SELECT s.fundstock_id AS id, s.fund_id AS fund_id, s.fundstock_symbol AS symbol, s.fundstock_share AS share, d.stockdata_name AS name FROM fundstock s LEFT OUTER JOIN stockdata d ON s.fundstock_symbol = d.stockdata_symbol WHERE s.fund_id = ?";
        List<Fundstock> fundstocks = jdbcTemplate.query(sql2, new BeanPropertyRowMapper<>(Fundstock.class), fund.getId());
        fund.setFundstocks(fundstocks);
        return Optional.of(fund);
    }

    @Override
    public Optional<List<Fund>> findByName(String name) {
        String sql = "SELECT f.fund_id AS id, f.fund_name AS name, f.createtime AS createtime, s.fundstock_id AS fundstocks_id, d.stockdata_name AS fundstocks_name, s.fund_id AS fundstocks_fund_id, s.fundstock_symbol AS fundstocks_symbol, s.fundstock_share AS fundstocks_share FROM fund f LEFT OUTER JOIN fundstock s ON f.fund_id = s.fund_id LEFT OUTER JOIN stockdata d ON s.fundstock_symbol = d.stockdata_symbol ";
        sql += String.format("WHERE f.fund_name = \'%s\'", name);
        ResultSetExtractor<List<Fund>> resultSetExtractor = JdbcTemplateMapperFactory.newInstance().addKeys("id")
                .newResultSetExtractor(Fund.class);
        Optional<List<Fund>> optional = Optional.of(jdbcTemplate.query(sql, resultSetExtractor));
        return optional;
    }

    @Override
    public Integer insert(Fund fund) {
        String sql = "INSERT INTO fund(fund_name) VALUES (?)";
        int rowcount = jdbcTemplate.update(sql, fund.getName());
        return rowcount;
    }

    @Override
    public Integer deleteById(Integer id) {
        String sql = "DELETE FROM fund WHERE fund_id=?";
        int rowcount = jdbcTemplate.update(sql, id);
        return rowcount;
    }

    @Override
    public Integer update(Fund fund) {
        String sql = "UPDATE fund SET fund_name=? WHERE fund_id=?";
        int rowcount = jdbcTemplate.update(sql, fund.getName(), fund.getId());
        return rowcount;
    }

    @Override
    public Optional<List<Fund>> findDataWithLimit(Integer offset, Integer limit) {
        if (offset < 0) {
            return findAll();
        }
        String sql = String.format(
                "SELECT x.fund_id AS id, x.fund_name AS name, x.createtime AS createtime, s.fundstock_id AS fundstocks_id, d.stockdata_name AS fundstocks_name, s.fund_id AS fundstocks_fund_id, s. fundstock_symbol AS fundstocks_symbol, s.fundstock_share AS fundstocks_share FROM (SELECT * FROM fund f ORDER BY f.fund_id OFFSET %d LIMIT %d) x LEFT OUTER JOIN fundstock s ON x.fund_id = s.fund_id LEFT OUTER JOIN stockdata d ON s.fundstock_symbol = d.stockdata_symbol ORDER BY x.fund_id",
                offset, limit);
        ResultSetExtractor<List<Fund>> resultSetExtractor = JdbcTemplateMapperFactory.newInstance().addKeys("id")
                .newResultSetExtractor(Fund.class);
        Optional<List<Fund>> optional = Optional.of(jdbcTemplate.query(sql, resultSetExtractor));
        return optional;
    }

    @Override
    public int count() {
        String sql = "SELECT count(*) FROM fund";
        int count = jdbcTemplate.queryForObject(sql, Integer.class);
        return count;
    }

}
