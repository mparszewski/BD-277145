package bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Test {

	private static Connection connection = null;
	private static final String URL = "jdbc:oracle:thin:@ora3.elka.pw.edu.pl:1521:ora3inf";
	private static final String USER = "mparszew";
	private static final String PASSWORD = "mparszew";

	public static void main(String[] args) throws SQLException {

		DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
		System.out.println("Sterownik Oracle JDBC zosta³ zarejestrowany");

		connection = OraConn.open(connection, URL, USER, PASSWORD);

		if (connection != null)
			OraConn.close(connection);
	}
}
