package ua.edu.sumdu.j2ee.Zhulynska.departmentBean;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.List;

import javax.ejb.Remote;

@Remote
public interface Departments {
	
	public Department createDepartment(Department dep) throws RemoteException;

	public void updateDepartment(Department dep)throws RemoteException;

	public void deleteDepartment(BigDecimal id)throws RemoteException;
	
	public Department selectDepartment(BigDecimal id)throws RemoteException;
	
	public List<Department> showAll()throws RemoteException;

	public void update(Department dep) throws RemoteException;

	public List<Department> selectDepartment(String id,  String dnameVal,  String locationVal) throws RemoteException;

	String[] getFieldsName()throws RemoteException;
}
