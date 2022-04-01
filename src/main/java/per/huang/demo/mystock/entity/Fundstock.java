package per.huang.demo.mystock.entity;

public class Fundstock {
    
    private Integer id;
    private String name;
    private Integer fund_id;
    private String symbol;
    private Integer share;
    private Fund fund; 

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
        return "Fundstock [fund=" + fund + ", fund_id=" + fund_id + ", id=" + id + ", name=" + name + ", share=" + share
                + ", symbol=" + symbol + "]";
    }

    
}
