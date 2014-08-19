package ua.edu.sumdu.j2ee.Zhulynska.employeeBean;

import java.rmi.RemoteException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Remote;


@Remote
public interface Employees {

	public Employee createEmployee(Employee emp) ;

	public void updateEmployee(Employee emp);

	public void deleteEmployee(Long id);
	
	public Employee selectEmployee(Long id);
        	
	public List<Employee> showAll();
        
        public List<Employee> getDependents(Long id);

	public void update(Employee emp) ;

	public List<Employee> selectEmployee(String nameSelect,  String jobSelect, String departmentSelect, Long managerSelect, String salaryMinSelect, String salaryMaxSelect) ;
	
	String[] getFieldsName() ;

        public String getManagerName(String id) ;
        
        public Long getManagerNo(String managerName);
        
        public List<Employee> getManagers();
        
        public ArrayList<Employee> getManagersTree(Long empId);

        

        
}
