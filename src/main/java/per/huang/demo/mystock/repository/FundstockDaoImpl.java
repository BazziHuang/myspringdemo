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
public class FundstockDaoImpl implements FundDao<Fundstock> {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Optional<List<Fundstock>> findAll() {
        String sql = "SELECT s.fundstock_id AS id, d.stockdata_name AS name, s.fund_id AS fund_id, s.fundstock_symbol AS symbol, s.fundstock_share AS share, f.fund_id AS fund_id, f.fund_name AS fund_name, f.createtime AS fund_createtime FROM fundstock s LEFT OUTER JOIN stockdata d ON s.fundstock_symbol = d.stockdata_symbol LEFT OUTER JOIN fund f ON s.fund_id = f.fund_id ORDER BY s.fundstock_id";
        ResultSetExtractor<List<Fundstock>> resultSetExtractor = JdbcTemplateMapperFactory.newInstance()
                .addKeys("id")
                .newResultSetExtractor(Fundstock.class);
        Optional<List<Fundstock>> optional = Optional.of(jdbcTemplate.query(sql, resultSetExtractor));
        return optional;
    }

    @Override
    public Optional<Fundstock> findById(Integer id) {
        String sql1 = "SELECT s.fundstock_id AS id, s.fund_id AS fund_id, s.fundstock_symbol AS symbol, " +
                "s.fundstock_share AS share, d.stockdata_name AS name FROM fundstock s  " +
                "LEFT OUTER JOIN stockdata d ON s.fundstock_symbol = d.stockdata_symbol  " +
                "WHERE fundstock_id = ?";
        Fundstock fundstock = null;
        try {
            fundstock = jdbcTemplate.queryForObject(sql1, new BeanPropertyRowMapper<>(Fundstock.class), id);
        } catch (EmptyResultDataAccessException e) {
            System.out.println("FundstockDaoImpl: fundstock not found!");
        }
        if (fundstock == null) {
            return Optional.empty();
        }
        String sql2 = "SELECT f.fund_id AS id, f.fund_name AS name, " +
                "f.createtime AS createtime FROM fund f WHERE fund_id = ?";
        Fund fund = jdbcTemplate.queryForObject(sql2, new BeanPropertyRowMapper<>(Fund.class), fundstock.getFund_id());
        fundstock.setFund(fund);
        return Optional.of(fundstock);
    }

    @Override
    public Optional<List<Fundstock>> findByName(String name) {
        String option = null;
        if (name.contains("TW")) {
            option = "s.fundstock_symbol";
        } else {
            option = "d.stockdata_name";
        }
        String sql = "SELECT s.fundstock_id AS id, d.stockdata_name AS name, s.fund_id AS fund_id, s.fundstock_symbol AS symbol, s.fundstock_share AS share, f.fund_id AS fund_id, f.fund_name AS fund_name, f.createtime AS fund_createtime FROM fundstock s LEFT OUTER JOIN stockdata d ON s.fundstock_symbol = d.stockdata_symbol LEFT OUTER JOIN fund f ON s.fund_id = f.fund_id ";
        sql += String.format("WHERE %s = \'%s\'", option, name);
        ResultSetExtractor<List<Fundstock>> resultSetExtractor = JdbcTemplateMapperFactory.newInstance()
                .addKeys("id")
                .newResultSetExtractor(Fundstock.class);
        Optional<List<Fundstock>> optional = Optional.of(jdbcTemplate.query(sql, resultSetExtractor));
        return optional;
    }

    @Override
    public Integer insert(Fundstock fundstock) {
        String sql = "INSERT INTO fundstock(fund_id, fundstock_symbol,fundstock_share) VALUES (?, ?, ?)";
        int rowcount = jdbcTemplate.update(sql, fundstock.getFund_id(), fundstock.getSymbol(), fundstock.getShare());
        return rowcount;
    }

    @Override
    public Integer deleteById(Integer id) {
        String sql = "DELETE FROM fundstock WHERE fundstock_id=?";
        int rowcount = jdbcTemplate.update(sql, id);
        return rowcount;
    }

    @Override
    public Integer update(Fundstock fundstock) {
        String sql = "UPDATE fundstock SET fund_id=?, fundstock_symbol=?, fundstock_share=? WHERE fundstock_id=?";
        int rowcount = jdbcTemplate.update(sql, fundstock.getFund_id(), fundstock.getSymbol(), fundstock.getShare(),
                fundstock.getId());
        return rowcount;
    }

    @Override
    public Optional<List<Fundstock>> findDataWithLimit(Integer offset, Integer limit) {
        if (offset < 0) {
            return findAll();
        }
        String sql = "SELECT s.fundstock_id AS id, d.stockdata_name AS name, s.fund_id AS fund_id, s.fundstock_symbol AS symbol, s.fundstock_share AS share, f.fund_id AS fund_id, f.fund_name AS fund_name, f.createtime AS fund_createtime FROM fundstock s LEFT OUTER JOIN stockdata d ON s.fundstock_symbol = d.stockdata_symbol LEFT OUTER JOIN fund f ON s.fund_id = f.fund_id ORDER BY s.fundstock_id ";
        sql += String.format("OFFSET %d LIMIT %d", offset, limit);
        ResultSetExtractor<List<Fundstock>> resultSetExtractor = JdbcTemplateMapperFactory.newInstance()
                .addKeys("id")
                .newResultSetExtractor(Fundstock.class);
        Optional<List<Fundstock>> optional = Optional.of(jdbcTemplate.query(sql, resultSetExtractor));
        return optional;
    }

    @Override
    public int count() {
        String sql = "SELECT count(*) FROM fundstock";
        int count = jdbcTemplate.queryForObject(sql, Integer.class);
        return count;
    }

}
