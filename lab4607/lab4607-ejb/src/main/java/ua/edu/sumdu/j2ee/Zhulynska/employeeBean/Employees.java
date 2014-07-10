package ua.edu.sumdu.j2ee.Zhulynska.employeeBean;

import java.rmi.RemoteException;
import java.text.ParseException;
import java.util.List;
import javax.ejb.Remote;


@Remote
public interface Employees {

	public Employee createEmployee(Employee emp) throws RemoteException;

	public void updateEmployee(Employee emp)throws RemoteException;

	public void deleteEmployee(Long id)throws RemoteException;
	
	public Employee selectEmployee(Long id)throws RemoteException;
	
	public List<Employee> showAll()throws RemoteException;

	public void update(Employee emp) throws RemoteException;

	public List<Employee> selectEmployee(String id,  String enameVal,  String jobVal, String managerVal, String hiredateVal, String salaryVal, String commissionVal, String departmentVal) throws RemoteException, ParseException;
	
	String[] getFieldsName()throws RemoteException ;
	
	List<String> getChilds(String ename) throws RemoteException ;
}
