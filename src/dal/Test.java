package dal;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Vector;

public class Test {

	private static Connection connection = null;
	private static final String URL = "jdbc:oracle:thin:@ora3.elka.pw.edu.pl:1521:ora3inf";
	private static final String USER = "mparszew";
	private static final String PASSWORD = "mparszew";

	public static void main(String[] args) throws SQLException {

		new OraConn();
		
		connection = OraConn.open(connection, URL, USER, PASSWORD);
		
		EmployeeDAL empDAL = new EmployeeDAL();

		
		Employee newEmployee = new Employee(789);
		newEmployee.setFirstName("Aadam");
		newEmployee.setLastName("Kowalski");
		newEmployee.setEmail("akowalski@gmail.com");
		newEmployee.setPhoneNumber("123456789");
		newEmployee.setHireDate(LocalDate.now());
		newEmployee.setJobId("AB_CDE");
		newEmployee.setSalary(50000);
		newEmployee.setManagerId(121);
		newEmployee.setDepartmentId(45);
		
		Employee updatedEmployee = new Employee();
		updatedEmployee.setLastName("Nowak");
		updatedEmployee.setEmail("nowak@123.pl");
		updatedEmployee.setHireDate(LocalDate.now());
		updatedEmployee.setEmployeeId(117);
		

		//testowanie metod:
		Vector<Employee> vector = empDAL.getEmployees(connection);
		System.out.println("Jest " +vector.size() +" wszystkich pracowników");
		
		empDAL.insertEmployee(newEmployee, connection);
		
		Employee employee = empDAL.getEmployeeById(newEmployee.getEmployeeId(), connection);
		System.out.println("Pracownik o id = " +newEmployee.getEmployeeId() + 
				": " + employee.getFirstName() + " " + employee.getLastName());
		
		int affectedRows = empDAL.updateEmployee(updatedEmployee, connection);
		System.out.println("Zaktualizowano dane: " + affectedRows + " pracownika");
		
		empDAL.delEmployee(187, connection);
		
		if (connection != null)
			OraConn.close(connection);
	}
}
