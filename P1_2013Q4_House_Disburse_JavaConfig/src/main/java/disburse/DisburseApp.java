package disburse;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import config.HouseDisburseConfig;
import disburse.dao.JdbcHouseDisburseDAO;
import disburse.vo.HouseDisburseDetail;

//@Configuration
public class DisburseApp {

	public static void main(String[] args) throws SQLException {

		/*
		 * DataSource ds = new
		 * EmbeddedDatabaseBuilder().addScript("classpath:2013Q4_HOUSE_DISBURSE.sql").
		 * build(); ds.getConnection().setAutoCommit(true);
		 */
		
		/* JdbcHouseDisburseDAO dao = new JdbcHouseDisburseDAO(ds); */

		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(HouseDisburseConfig.class);
		JdbcHouseDisburseDAO dao = ctx.getBean(JdbcHouseDisburseDAO.class);
		
		//original code below
		System.out.println(dao.getTotalAmountSpent());
		
		List<HouseDisburseDetail> hddList = dao.selectQuery("A000055");
		for (HouseDisburseDetail hdd : hddList) {
			System.out.println(hdd.toString());
		}
		
		Map<String, Double> hddMap = dao.getTotalAmountSpentByBIOGUIDE_ID();
		for (Map.Entry<String, Double> hdMap : hddMap.entrySet()) {     
			System.out.println("Key = " + hdMap.getKey() + ", Value = " + hdMap.getValue()); 
		}
		
		System.out.println("Total Records before delete: " + dao.getTotalRecords());
		System.out.println("Total Records deleted: " + dao.deleteRecordsByBIOGUIDEID("A000055"));
		System.out.println("Total Records after delete: " + dao.getTotalRecords());
	}

}
