package lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.db;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.CtMedia;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.DtEmail;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.DtMediaID;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.DtMediaName;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.EtMediaCategory;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.PtString;

public class DbMedia extends DbAbstract {

	static public void insertMedia(CtMedia aCtMedia){
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			conn = DriverManager.getConnection(url+dbName, userName, password);
			log.debug("Connected to the database");

			try{
				Statement st = conn.createStatement();
				
				String id = aCtMedia.id.value.getValue();
				String name = aCtMedia.name.value.getValue();
				String email = aCtMedia.email.value.getValue();
				String category = aCtMedia.category.toString();
				
				log.debug("[DATABASE]-Insert media");
				int val = st.executeUpdate("INSERT INTO " + dbName + ".media" + "(id, name, email, category)" + 
											"VALUES(" + "'" + id + "','" + name + "','" + email + "','" + category + "')");
				
				log.debug(val + " row affected");
			}
			catch (SQLException s){
				log.error("SQL statement is not executed! "+s);
			}
	
			conn.close();
			log.debug("Disconnected from database");		
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	static public CtMedia getMedia(String mediaId){
		
		CtMedia aCtMedia = new CtMedia();
				
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			conn = DriverManager.getConnection(url + dbName, userName, password);
			log.debug("Connected to the database");

			try{
				String sql = "SELECT * FROM "+ dbName + ".media WHERE id = " + mediaId;

				PreparedStatement statement = conn.prepareStatement(sql);
				ResultSet  res = statement.executeQuery(sql);
				
				if (res.next()) {
					aCtMedia = new CtMedia();
					
					DtMediaID aId = new DtMediaID(new PtString(res.getString("id")));
					DtMediaName aName = new DtMediaName(new PtString(res.getString("name")));
					DtEmail aEmail = new DtEmail(new PtString(res.getString("email"))); 
					
					String sCategory = res.getString("category");
					EtMediaCategory aCategory = null;
					if (sCategory.equals(EtMediaCategory.category_1.name()))
						aCategory = EtMediaCategory.category_1;
					if (sCategory.equals(EtMediaCategory.category_2.name()))
						aCategory = EtMediaCategory.category_2;
					if (sCategory.equals(EtMediaCategory.category_3.name()))
						aCategory = EtMediaCategory.category_3;

					aCtMedia.init(aId, aName, aEmail, aCategory);					
				}								
			}
			catch (SQLException s){
				log.error("SQL statement is not executed! "+s);
			}
			conn.close();
			log.debug("Disconnected from database");
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return aCtMedia;
	}
	
	
	static public void deleteMedia(CtMedia aCtMedia){
		try {
			Class.forName("com.mysql.jdbc.Driver");			
			conn = DriverManager.getConnection(url + dbName, userName, password);
			log.debug("Connected to the database");

			try {
				String sql = "DELETE FROM " + dbName + ".media WHERE id = ?";
				String id = aCtMedia.id.value.getValue();

				PreparedStatement statement = conn.prepareStatement(sql);
				statement.setString(1, id);
				int rows = statement.executeUpdate();
				log.debug(rows + " row deleted");
			} catch (SQLException s) {
				log.error("SQL statement is not executed! " + s);
			}

			conn.close();
			log.debug("Disconnected from database");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	static public void updateMedia(CtMedia aCtMedia){
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			conn = DriverManager.getConnection(url + dbName, userName, password);
			log.debug("Connected to the database");

			try {
				log.debug("[DATABASE]-Update media");
				String sql = "UPDATE " + dbName + ".media SET `name` = ?, `email` = ?, `category` = ? WHERE id = ?";
				
				String id = aCtMedia.id.value.getValue();
				String name = aCtMedia.name.value.getValue();
				String email = aCtMedia.email.value.getValue();
				String category = aCtMedia.category.toString();
				
				PreparedStatement statement = conn.prepareStatement(sql);
				statement.setString(1, name);
				statement.setString(2, email);
				statement.setString(3, category);
				statement.setString(4, id);
				
				int rows = statement.executeUpdate();
				log.debug(rows + " row affected");
			} catch (SQLException s) {
				log.error("SQL statement is not executed! " + s);
			}

			conn.close();
			log.debug("Disconnected from database");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
