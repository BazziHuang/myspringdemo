package per.huang.demo.mystock.controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

@RestController
@RequestMapping("fund/fundstock/price")
public class StockPriceController {

    @GetMapping("/historicalQuote/{symbol:.+}")
    public List<HistoricalQuote> queryHistoricalQuotes(
        @PathVariable("symbol") String symbol
    ){
        Calendar from = Calendar.getInstance();
        Calendar to = Calendar.getInstance();
        from.add(Calendar.YEAR, -1); //from 1 year ago
        List<HistoricalQuote> googleHistQuotes = null;
        
            try {
                Stock google = YahooFinance.get(symbol);
                googleHistQuotes = google.getHistory(from, to, Interval.DAILY);
            } catch (Exception e) {
                System.out.println("Exception occurs at StockPriceController: ");
                e.printStackTrace();
            }
            
        return googleHistQuotes;  
    }
    
}
