package ua.edu.sumdu.j2ee.Zhulynska.departmentBean;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.List;

import javax.ejb.Remote;

@Remote
public interface Departments {
	
	public Department createDepartment(Department dep) ;

	public void updateDepartment(Department dep);

	public void deleteDepartment(BigDecimal id);
	
	public Department selectDepartment(BigDecimal id);
        
        public BigDecimal getDepartmentNo(String deptName);

	
	public List<Department> showAll();

	public void update(Department dep);

	public List<Department> selectDepartment(String id,  String dnameVal,  String locationVal);

	String[] getFieldsName();
        
       List<String> getDeptName();
       
}
