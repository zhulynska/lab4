package ua.edu.sumdu.j2ee.Zhulynska.employeeBean;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;


import ua.edu.sumdu.j2ee.Zhulynska.departmentBean.Departments;

@Stateful
public class EmployeesBean implements Employees {
	@PersistenceContext(unitName = "employee")
	private EntityManager manager;
	public static final SimpleDateFormat DateFormat1 = new SimpleDateFormat(
			"yyyy-MM-dd", Locale.ENGLISH);

	@EJB 
	private Departments department;
	
	@Override
	public Employee createEmployee(Employee emp) throws RemoteException {
		manager.persist(emp);
		return emp;
	}

	@Override
	public void updateEmployee(Employee emp) throws RemoteException {
		manager.merge(emp);
	}

	@Override
	public void deleteEmployee(Long id) throws RemoteException {
		manager.remove(selectEmployee(id));
	}

	@Override
	public Employee selectEmployee(Long id) throws RemoteException {
		return manager.find(Employee.class, id);
	}

	@Override
	public List<Employee> showAll() throws RemoteException {
		return manager.createQuery("Select e from Employee e").getResultList();
	}

	@Override
	public void update(Employee emp) throws RemoteException {
		Query query = manager
				.createQuery(
						"UPDATE Employee e SET e.ename = :eName, e.job = :eJob, e.manager = :eManager, e.hiredate = :eHiredate, e.salary = :eSalary,  e.commission = :eCommission, e.department = :eDepartment WHERE e.empno = :eEmpno")
				.setParameter("eEmpno", emp.getEmpno())
				.setParameter("eName", emp.getEname())
				.setParameter("eJob", emp.getJob())
				.setParameter("eManager", emp.getManager())
				.setParameter("eHiredate", emp.getHiredate())
				.setParameter("eSalary", emp.getSalary())
				.setParameter("eCommission", emp.getCommission())
				.setParameter("eDepartment", emp.getDepartment());
		query.executeUpdate();
	}

	@Override
	public List<Employee> selectEmployee(String id, String enameVal,
			String jobVal, String managerVal, String hiredateVal,
			String salaryVal, String commissionVal, String departmentVal)
			throws RemoteException, ParseException {
		CriteriaBuilder cb = manager.getCriteriaBuilder();
		CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);
		Metamodel m = manager.getMetamodel();
		EntityType<Employee> spareEntityType = m.entity(Employee.class);
		Root<Employee> spareRoot = cq.from(Employee.class);
		
		// conditions are true everywhere:
		Predicate idCondition =  cb.notEqual(spareRoot.get("empno"), new Long(-1));
		Predicate enameCondition = cb.notEqual(spareRoot.get("empno"), new Long(-1));
		Predicate jobCondition =  cb.notEqual(spareRoot.get("empno"), new Long(-1)); 
		Predicate managerCondition =  cb.notEqual(spareRoot.get("empno"), new Long(-1));
		Predicate hiredateCondition =  cb.notEqual(spareRoot.get("empno"), new Long(-1));
		Predicate salaryCondition =  cb.notEqual(spareRoot.get("empno"), new Long(-1));
		Predicate commissionCondition =  cb.notEqual(spareRoot.get("empno"), new Long(-1));
		Predicate departmentCondition =  cb.notEqual(spareRoot.get("empno"), new Long(-1));
		
		
		idCondition = (!id.isEmpty() ?  cb.equal(spareRoot.get("empno"), new Long(id)) : idCondition);
		enameCondition = (!enameVal.isEmpty() ?  cb.equal(spareRoot.get("ename"), enameVal) : enameCondition);
		jobCondition = (!jobVal.isEmpty() ?  cb.equal(spareRoot.get("job"), jobVal) : jobCondition);
		managerCondition = (!managerVal.isEmpty() ?  cb.equal(spareRoot.get("manager"), new Integer(managerVal)) : managerCondition);
		hiredateCondition = (!hiredateVal.isEmpty() ?  cb.equal(spareRoot.get("hiredate"), DateFormat1.parse(hiredateVal)) : hiredateCondition);
		salaryCondition = (!salaryVal.isEmpty() ?  cb.equal(spareRoot.get("salary"), new Float(salaryVal)) : salaryCondition);
		commissionCondition = (!commissionVal.isEmpty() ?  cb.equal(spareRoot.get("commission"), new Float(commissionVal)) : commissionCondition);
		departmentCondition = (!departmentVal.isEmpty() ?  cb.equal(spareRoot.get("department"), department.selectDepartment(new BigDecimal(departmentVal))) : departmentCondition);
		
		cq.where(cb.and(idCondition, enameCondition, jobCondition, managerCondition,hiredateCondition, salaryCondition,commissionCondition, departmentCondition));	
		TypedQuery<Employee> q = manager.createQuery(cq);
		 return q.getResultList();
	/*	CriteriaBuilder cb = manager.getCriteriaBuilder();
		CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);
		Metamodel m = manager.getMetamodel();
		Root<Employee> spareRoot = cq.from(Employee.class);

		Predicate idCondition =  null;
		Predicate enameCondition = null;
		Predicate jobCondition =  null;
		Predicate managerCondition =  null;
		Predicate hiredateCondition =  null;
		Predicate salaryCondition =  null;
		Predicate commissionCondition =  null;
		Predicate departmentCondition =  null;
		
		if (!id.isEmpty())
			idCondition = cb.equal(spareRoot.get("empno"), new Long(id));
		if (!enameVal.isEmpty())
			enameCondition = cb.equal(spareRoot.get("ename"), enameVal);
		if (!jobVal.isEmpty())
			jobCondition = cb.equal(spareRoot.get("job"), jobVal);
		if (!managerVal.isEmpty())
			managerCondition = cb.equal(spareRoot.get("manager"), new Integer(managerVal));
		if (!hiredateVal.isEmpty())
			hiredateCondition =  cb.equal(spareRoot.get("hiredate"), DateFormat1.parse(hiredateVal));
		if (!salaryVal.isEmpty())
			salaryCondition = cb.equal(spareRoot.get("salary"), new Float(salaryVal));
		if (!commissionVal.isEmpty())
			commissionCondition =  cb.equal(spareRoot.get("commission"), new Float(commissionVal));
		if (!departmentVal.isEmpty())
			departmentCondition = cb.equal(spareRoot.get("department"), department.selectDepartment(new BigDecimal(departmentVal)));
							
		cq.where(cb.and(idCondition, enameCondition, jobCondition, managerCondition,hiredateCondition, salaryCondition,commissionCondition, departmentCondition));	
		TypedQuery<Employee> q = manager.createQuery(cq);
		 return q.getResultList();
	*/}

	@Override
	public String[] getFieldsName() throws RemoteException {
		return new String[]{"empno", "ename", "job", "manager", "hiredate", "salary", "commission", "department"};
	}

	@Override
	public List<String> getChilds(String ename1) throws RemoteException {
		/*Configuration cfg = new Configuration()
	    .setProperty("hibernate.order_updates", "true");*/
		
		

		//List<String> list= manager.createQuery("select emp.ename from Employee emp where emp.manager = (Select emp1.empno from Employee emp1 where emp1.ename = :name)").setParameter("name", ename1).getResultList(); 
		return null;
	}
}
