package com.cygen.cygendineinpos;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;


@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class DbHandler {
	static Connection connection;
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@SuppressLint("NewApi")
	public static Connection dbConnection(){

		StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);

			try {
				Class.forName("net.sourceforge.jtds.jdbc.Driver");
				//String port ="1433";

				String ip= Constants.ipAddress+":"+Constants.portNo;
				connection= DriverManager.getConnection("jdbc:jtds:sqlserver://"+ip+";socketTimeout=60;databaseName="+Constants.dbName+";user=cygenpos_charle_food;password=waLg}GaclFYE");
			//	connection=DriverManager.getConnection("jdbc:jtds:sqlserver://" + Constants.ipAddress +";"
//						+ "databaseName=" + "COZYPOS" + ";user=" + "sa" + ";password="
//						+ "GspL!sCipL@@01%" + ";");
//				// password=GspL!sCipL@@01%
//				 connection = DriverManager.getConnection(
////						//jdbc:jtds:sybase://localhost:5000/dbname;user=dbuser;password=dbpwd;
//						"jdbc:jtds:sqlserver://192.168.2.145:1433/COZYPOS;user=sa;password=GspL!sCipL@@01%;");
		}


		catch (Exception e){
			e.printStackTrace();

			//Mainapplication.createdialog();

		}

		return connection;
	}
}
