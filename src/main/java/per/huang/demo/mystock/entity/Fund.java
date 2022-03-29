package per.huang.demo.mystock.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.format.annotation.DateTimeFormat;



@Entity
@Table(name = "fund")
public class Fund implements Serializable{

    @Column(name = "fund_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Integer id;

    @Column(name = "fund_name")
    private String name;

    @Column
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createtime;

    @OneToMany(mappedBy = "fund_id", targetEntity = Fundstock.class , cascade= CascadeType.ALL)
    @NotFound(action=NotFoundAction.IGNORE)
    //@JoinColumn(name = "fund_id")
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
