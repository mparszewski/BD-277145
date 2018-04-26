package dal;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Vector;

import bd.OraConn;

public class Test {

	private static Connection connection = null;
	private static final String URL = "jdbc:oracle:thin:@ora3.elka.pw.edu.pl:1521:ora3inf";
	private static final String USER = "mparszew";
	private static final String PASSWORD = "mparszew";

	public static void main(String[] args) throws SQLException {

		new OraConn();
		
		connection = OraConn.open(connection, URL, USER, PASSWORD);

		
		//testowanie metod:
		EmployeeDAL empDAL = new EmployeeDAL();
		
		Vector<Employee> vector = empDAL.getEmployees(connection);
		System.out.println(vector.size());
		System.out.println(vector.get(2).getLastName());
		
		Employee employee = new Employee(512);
		employee.setFirstName("Aadam");
		employee.setLastName("Kowalski");
		employee.setEmail("akowalski@gmail.com");
		employee.setPhoneNumber("123456789");
		employee.setHireDate(LocalDate.now());
		employee.setJobId("AB_CDE");
		employee.setSalary(50000);
		employee.setManagerId(121);
		employee.setDepartmentId(45);
		
		//empDAL.insertEmployee(employee, connection);
		
		Employee empById = empDAL.getEmployeeById(194, connection);
		//System.out.println(empById.getEmail());
		
		empDAL.updateEmployee(employee, connection);
		
		
		empDAL.delEmployee(194, connection);
		
		
		if (connection != null)
			OraConn.close(connection);
	}
}
