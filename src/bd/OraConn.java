package bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class OraConn {

	public static Connection open(Connection connection, String URL, String user, String password) {
		try {
			connection = DriverManager.getConnection(URL, user, password);
			System.out.println("Po��czono z baz� danych");
		} catch (SQLException e2) {
			System.out.println("B��d ��czenia z baz� danych");
			System.out.println(e2.getMessage());
			// e2.printStackTrace();
		}
		return connection;
	}

	public static void close(Connection connection) {
		try {
			connection.close();
			System.out.println("Poprawnie zamkni�to po��czenie");
		} catch (SQLException e3) {
			System.out.println(e3.getMessage());
			// e3.printStackTrace();
		}
	}

}
