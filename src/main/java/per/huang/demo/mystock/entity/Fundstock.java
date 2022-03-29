package per.huang.demo.mystock.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "fundstock")
public class Fundstock implements Serializable{

    @Column(name = "fundstock_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Integer id;

    @Column(name = "fund_id")
    @NotEmpty(message = "{fundstock.fid.empty}")
    private Integer fund_id;

    @Column(name = "fundstock_symbol")
    @NotEmpty(message = "{fundstock.symbol.empty}")
    private String symbol;

    @Column(name = "fundstock_share")
    @NotEmpty(message = "{fundstock.share.empty}")
    @Min(value = 1000, message = "{fundstock.share.notenough}")
    private Integer share;

    
    @JsonIgnore
    @ManyToOne(targetEntity = Fund.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "fund_id", insertable = false, updatable = false)
    private Fund fund; 

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFund_id() {
        return fund_id;
    }

    public void setFund_id(Integer fund_id) {
        this.fund_id = fund_id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Integer getShare() {
        return share;
    }

    public void setShare(Integer share) {
        this.share = share;
    }

    public Fund getFund() {
        return fund;
    }

    public void setFund(Fund fund) {
        this.fund = fund;
    }

    @Override
    public String toString() {
        return "Fundstock [fund_id=" + fund_id + ", id=" + id + ", share=" + share + ", symbol=" + symbol + "]";
    }

    
}
