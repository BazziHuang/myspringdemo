package per.huang.demo.mystock.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.springframework.format.annotation.DateTimeFormat;

public class Fund implements Serializable{

    private Integer id;
    private String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createtime;
    private List<Fundstock> fundstocks;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public List<Fundstock> getFundstocks() {
        return fundstocks;
    }

    public void setFundstocks(List<Fundstock> fundstocks) {
        this.fundstocks = fundstocks;
    } 

    
}
