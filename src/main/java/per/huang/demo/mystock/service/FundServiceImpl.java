package per.huang.demo.mystock.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import per.huang.demo.mystock.entity.Fund;
import per.huang.demo.mystock.exception.BadParameterException;
import per.huang.demo.mystock.exception.DataNotFoundException;
import per.huang.demo.mystock.exception.InvalidInputException;
import per.huang.demo.mystock.repository.FundDao;

@Service
public class FundServiceImpl implements FundService<Fund>{

    @Autowired
    FundDao<Fund> fundDao;

    @Override
    public List<Fund> getAllData() {
        return fundDao.findAll().get();
    }

    @Override
    public List<Fund> getDataWithLimit(Integer offset, Integer limit){
        if(offset == null || offset < 0 ){
            throw new BadParameterException("offset can not be null or negative.","offset");
        }
        if(limit == null || limit <= 0){
            throw new BadParameterException("limit can not be null or less than zero.","limit");
        }
        return fundDao.findDataWithLimit(offset, limit).orElse(null);
    }

    @Override
    public Fund getDataById(Integer fund_id){
        if(fund_id == null || fund_id <= 0){
            throw new BadParameterException("fundstock_id can not be null or less than zero.", "fundstock_id");
        }
        Fund fund = fundDao.findById(fund_id).orElseThrow(() -> new DataNotFoundException("fund_id not found."));
        return fund;
    }

    @Override
    public int getDataCount() {
        return fundDao.count();
    }

    @Override
    public int addData(Fund fund){
        String fund_name = fund.getName();
        Fund exsitingFund = fundDao.findByName(fund_name).orElse(null).get(0);
        if(fund_name==null){
            throw new InvalidInputException("input fund_name can not be null.");
        }
        if(exsitingFund != null){
            throw new InvalidInputException("fund_name already exists.");
        }
        return fundDao.insert(fund);
    }

    @Override
    public int updateData(Fund fund){
        Integer fund_id = fund.getId();
        String fund_name = fund.getName();
        fundDao.findById(fund_id).orElseThrow(() -> new  DataNotFoundException("fund not found."));
        Fund exsitingFund = fundDao.findByName(fund_name).orElse(null).get(0);
        if(exsitingFund != null){
            throw new InvalidInputException("fund_name already exists.");
        }
        return fundDao.update(fund);
    }

    @Override
    public int deleteData(Integer fund_id){
        fundDao.findById(fund_id).orElseThrow(() -> new  DataNotFoundException("fund not found."));
        return fundDao.deleteById(fund_id);
    }
    
}
