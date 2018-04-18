package bd;

import java.sql.*;

/**
 * 
 * @author mpars 
 * Klasa napisana w celach testowych
 *
 */
public class JDBCDemo {

	private static final String URL = "jdbc:oracle:thin:@ora3.elka.pw.edu.pl:1521:ora3inf";
	private static final String USER = "mparszew";
	private static final String PASSWORD = "mparszew";
	private static Connection connection = null;
	private static Statement statement = null;

	public static void main(String[] args) {
		try {
			DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
			System.out.println("Sterownik Oracle JDBC zosta³ zarejstrowany");

			connection = OraConn.open(connection, URL, USER, PASSWORD);

			System.out.println("Creating statement..");
			statement = connection.createStatement();
			String sql;
			sql = "SELECT FIRST_NAME, LAST_NAME" + " FROM HR.EMPLOYEES";
			ResultSet resultSet = statement.executeQuery(sql);

			while (resultSet.next()) {
				String first = resultSet.getString("FIRST_NAME");
				String last = resultSet.getString("LAST_NAME");

				System.out.print("Imie: " + first);
				System.out.println(", Nazwisko: " + last);
			}

			resultSet.close();
			statement.close();

		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (statement != null)
					statement.close();
			} catch (SQLException se2) {
			}
			if (connection != null)
				OraConn.close(connection);
		}
	}
}
