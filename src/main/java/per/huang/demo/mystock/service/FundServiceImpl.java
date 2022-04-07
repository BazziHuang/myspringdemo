package per.huang.demo.mystock.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import per.huang.demo.mystock.entity.Fund;
import per.huang.demo.mystock.exception.InvalidInputException;
import per.huang.demo.mystock.exception.DataNotFoundException;
import per.huang.demo.mystock.exception.DataAlreadyExistsException;
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
        if(offset != null && offset < 0){
            return getAllData();
        }
        if(offset == null){
            throw new InvalidInputException("offset can not be null.", "offset");
        }
        if(limit == null || limit <= 0){
            throw new InvalidInputException("limit can not be null or less than zero.", "limit");
        }
        return fundDao.findDataWithLimit(offset, limit).orElse(null);
    }

    @Override
    public Fund getDataById(Integer fund_id){
        if(fund_id == null || fund_id <= 0){
            throw new InvalidInputException("fundstock_id can not be null or less than zero.", "fundstock_id");
        }
        Fund fund = fundDao.findById(fund_id).orElseThrow(() -> new DataNotFoundException("fund_id not found.", "fund_id"));
        return fund;
    }

    @Override
    public int getDataCount() {
        return fundDao.count();
    }

    @Override
    public int addData(Fund fund){
        String fund_name = fund.getName();
        if(fund_name==null){
            throw new InvalidInputException("input fund_name can not be null.", "fund_name");
        }
        List<Fund> exsitingFund = fundDao.findByName(fund_name).orElse(null);
        if(exsitingFund != null && exsitingFund.size() > 0){
            throw new DataAlreadyExistsException("fund_name already exists.", "fund_name");
        }
        return fundDao.insert(fund);
    }

    @Override
    public int updateData(Fund fund){
        Integer fund_id = fund.getId();
        String fund_name = fund.getName();
        fundDao.findById(fund_id).orElseThrow(() -> new  DataNotFoundException("fund_id not found.", "fund_id"));
        List<Fund> exsitingFund = fundDao.findByName(fund_name).orElse(null);
        if(exsitingFund != null && exsitingFund.size() > 0){
            throw new DataAlreadyExistsException("fund_name already exists.", "fund_name");
        }
        return fundDao.update(fund);
    }

    @Override
    public int deleteData(Integer fund_id){
        fundDao.findById(fund_id).orElseThrow(() -> new  DataNotFoundException("fund_id not found.", "fund_id"));
        return fundDao.deleteById(fund_id);
    }
    
}
