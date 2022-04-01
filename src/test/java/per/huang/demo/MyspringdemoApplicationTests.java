package per.huang.demo;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import per.huang.demo.mystock.entity.Fundstock;
import per.huang.demo.mystock.repository.FundDao;

@SpringBootTest
public class MyspringdemoApplicationTests {
	
	@Autowired
	FundDao<Fundstock> fundstockDao;

	@Test
	public void contextLoads() {

		Fundstock fundstock = fundstockDao.findById(35).orElse(null);
		SoftAssertions softAssertions = new SoftAssertions();
		softAssertions.assertThat(fundstock).isNotEqualTo(null);
		softAssertions.assertAll();
		
	}

}
