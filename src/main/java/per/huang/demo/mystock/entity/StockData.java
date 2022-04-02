package per.huang.demo.mystock.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "stockdata")
public class StockData implements Serializable{

    @Id
    @Column(name = "stockdata_symbol")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String symbol;

    @Column(name = "stockdata_name")
    private String name;

    @Column(name = "stockdata_category")
    private String category;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    
    
}
