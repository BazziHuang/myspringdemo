package per.huang.demo.mystock.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import per.huang.demo.mystock.entity.Fund;
import per.huang.demo.mystock.entity.Fundstock;
import per.huang.demo.mystock.exception.BadParameterException;
import per.huang.demo.mystock.exception.DataNotFoundException;
import per.huang.demo.mystock.exception.InvalidInputException;
import per.huang.demo.mystock.repository.FundDao;
import per.huang.demo.mystock.repository.FundstockDao;

@Service
public class FundstockServiceImpl implements FundService<Fundstock>{

    @Autowired
    FundstockDao fundstockDao;
    @Autowired
    FundDao fundDao;

    @Override
    public List<Fundstock> getAllData() {
        return fundstockDao.findAll();
    }

    @Override
    public List<Fundstock> getDataWithLimit(Integer offset, Integer limit){
        if(offset == null || offset < 0 ){
            throw new BadParameterException("offset can not be null or negative.","offset");
        }
        if(limit == null || limit <= 0){
            throw new BadParameterException("limit can not be null or less than zero.","limit");
        }
        return fundstockDao.findFundstocksWithLimit(offset, limit).orElse(null);
    }

    @Override
    public Fundstock getDataById(Integer fundstock_id){
        if(fundstock_id == null || fundstock_id <= 0){
            throw new BadParameterException("fundstock_id can not be null or less than zero.", "fundstock_id");
        }
        Fundstock fundstock = fundstockDao.findById(fundstock_id).orElseThrow(() -> new DataNotFoundException("fundstock_id not found."));
        return fundstock;
    }

    @Override
    public int getDataCount() {
        return (int)fundstockDao.count();
    }

    @Override
    public int addData(Fundstock fundstock){
        Integer fund_id = fundstock.getFund_id();
        String fundstock_symbol = fundstock.getSymbol();
        Integer fundstock_share = fundstock.getShare();
        if(fund_id==null || fundstock_symbol==null || fundstock_share==null){
            throw new InvalidInputException("input fundstock parameters can not be null.");
        }
        return fundstockDao.insertIntoFundstock(fund_id, fundstock_symbol, fundstock_share);
    }

    @Override
    public int updateData(Fundstock fundstock){
        Integer fundstock_id = fundstock.getId();
        Integer fund_id = fundstock.getFund_id();
        String fundstock_symbol = fundstock.getSymbol();
        Integer fundstock_share = fundstock.getShare();
        fundDao.findById(fund_id).orElseThrow(()->new DataNotFoundException("fund_id not found."));
        fundstockDao.findById(fundstock_id).orElseThrow(() -> new  InvalidInputException("invalid fundstock_id, fundstock doesn't exist."));     
        if(fund_id==null || fundstock_symbol==null || fundstock_share==null){
            throw new InvalidInputException("input fundstock invalid.");
        }
        return fundstockDao.updateFundstockById(fundstock_id, fund_id, fundstock_symbol, fundstock_share);
    }

    @Override
    public int deleteData(Integer fundstock_id) throws DataNotFoundException {
        fundstockDao.findById(fundstock_id).orElseThrow(() -> new DataNotFoundException("fundstock not found."));
        return fundstockDao.deleteFundstockById(fundstock_id);
    }
    
}
