package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;
import java.util.Vector;

public class EmployeeDAL {

	private SQLException exception;

	public SQLException getException() {
		return exception;
	}

	public EmployeeDAL() {
	}

	public Vector<Employee> getEmployees(Connection connection) {
		Vector<Employee> employees = new Vector<>();

		try (Statement statement = connection.createStatement();) {

			String query = "SELECT * FROM EMPLOYEES";
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				employees.add(rs2Employee(resultSet));
			}

		} catch (SQLException e) {
			System.out.println(e);
		}
		return employees;
	}

	private Employee rs2Employee(ResultSet resultSet) {
		Employee employee = null;
		try {
			int col = 1;
			employee = new Employee(resultSet.getInt(col++));
			employee.setFirstName(resultSet.getNString(col++));
			employee.setLastName(resultSet.getNString(col++));
			employee.setEmail(resultSet.getNString(col++));
			employee.setPhoneNumber(resultSet.getNString(col++));
			employee.setHireDate(resultSet.getDate(col++).toLocalDate());
			employee.setJobId(resultSet.getNString(col++));
			employee.setSalary(resultSet.getInt(col++));
			// employee.setCommissionPct(resultSet.getDouble(col++));
			col++;
			employee.setManagerId(resultSet.getInt(col++));
			employee.setDepartmentId(resultSet.getInt(col++));
		} catch (SQLException e) {
			// this.exception = e;
		}
		return employee;
	}

	public int updateEmployee(Employee emp, Connection connection) {
		try (Statement statement = connection.createStatement();) {
			if (employeeExists(emp.getEmployeeId(), connection)) {
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
				String hireDate = dtf.format(emp.getHireDate());

				String query = "UPDATE EMPLOYEES SET " + "LAST_NAME = '" + emp.getLastName() + "', " + "EMAIL = '"
						+ emp.getEmail() + "', " + "HIRE_DATE = to_date(" + hireDate + ", 'yyyyMMdd') " + "WHERE "
						+ "EMPLOYEE_ID = " + emp.getEmployeeId();
				int affectedRows = statement.executeUpdate(query);
				connection.commit();
				System.out.println("Zaktualizowano pracownika");
				return affectedRows;
			} else {
				System.out.println("Nie mo¿na zaktualizowaæ danych pracownika."
						+ " Podany pracownik nie istnieje w bazie danych.");
				return 0;
			}
		} catch (SQLException ex) {
			this.exception = ex;
			return 0;
		}
	}

	public Employee getEmployeeById(int id, Connection connection) {

		Employee employee = new Employee();
		try {
			String query = "SELECT * FROM EMPLOYEES WHERE EMPLOYEE_ID = ?";
			PreparedStatement pStatement = connection.prepareStatement(query);
			pStatement.setInt(1, id);
			ResultSet resultSet = pStatement.executeQuery();
			if (resultSet.next() == true) {
				employee = rs2Employee(resultSet);
			}
			return employee;
		} catch (SQLException e) {
			// this.exception = e;
			e.printStackTrace();
			return null;
		}
	}

	public void delEmployee(int id, Connection connection) {

		try (Statement statement = connection.createStatement();) {
			if (employeeExists(id, connection)) {
				String query = "DELETE FROM EMPLOYEES " + "WHERE EMPLOYEE_ID = " + id;
				statement.executeUpdate(query);
				System.out.println("Poprawnie usuniêto pracownika o id = " + id);
			} else {
				System.out
						.println("Nie mo¿na usun¹æ pracownika. W bazie danych nie odnaleziono pracownika o podanym ID");
			}
		} catch (SQLException e) {
			this.exception = e;
		}
	}

	public void insertEmployee(Employee employee, Connection connection) {
		try (Statement statement = connection.createStatement();) {
			if (!employeeExists(employee.getEmployeeId(), connection)) {
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
				String hireDate = dtf.format(employee.getHireDate());

				String query = "INSERT INTO EMPLOYEES (EMPLOYEE_ID, FIRST_NAME, LAST_NAME, EMAIL,"
						+ "PHONE_NUMBER, HIRE_DATE, JOB_ID, SALARY, COMMISSION_PCT, MANAGER_ID, DEPARTMENT_ID) "
						+ "VALUES(" + employee.getEmployeeId() + ", '" + employee.getFirstName() + "', '"
						+ employee.getLastName() + "', '" + employee.getEmail() + "', '" + employee.getPhoneNumber()
						+ "', to_date(" + hireDate + ", 'yyyyMMdd'), '" + employee.getJobId() + "', "
						+ employee.getSalary() + ", " + employee.getCommissionPct() + ", " + employee.getManagerId()
						+ ", " + employee.getDepartmentId() + ")";
				statement.executeUpdate(query);
				System.out.println("Poprwanie dodano pracownika o id: " + employee.getEmployeeId());
				connection.commit();
			} else {
				System.out.println("Istnieje ju¿ pracownik o podanym ID");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean employeeExists(int id, Connection connection) throws SQLException {

		String query = "SELECT * FROM EMPLOYEES WHERE EMPLOYEE_ID = ?";
		PreparedStatement pStatement = connection.prepareStatement(query);
		pStatement.setInt(1, id);
		ResultSet resultSet = pStatement.executeQuery();
		if (resultSet.next() == true) {
			return true;
		} else
			return false;
	}
}
