package bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class OraConn {
	
	public OraConn( ) {
		try {
			DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
			System.out.println("Sterownik Oracle JDBC zosta� zarejestrowany");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	public static Connection open(Connection connection, String URL, String user, String password) {
		try {
			connection = DriverManager.getConnection(URL, user, password);
			System.out.println("Po��czono z baz� danych");
		} catch (SQLException e2) {
			System.out.println("B��d ��czenia z baz� danych");
			System.out.println(e2.getMessage());
		}
		return connection;
	}

	public static void close(Connection connection) {
		try {
			connection.close();
			System.out.println("Poprawnie zamkni�to po��czenie");
		} catch (SQLException e3) {
			System.out.println(e3.getMessage());
		}
	}

}
