package ua.edu.sumdu.j2ee.Zhulynska.departmentBean;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.List;


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


@Stateful
public class DepartmentsBean implements Departments {
	@PersistenceContext(unitName = "department")
	private EntityManager manager;
	
	@Override
	public Department createDepartment(Department dep) throws RemoteException {
		manager.persist(dep);
		return dep;
	}

	@Override
	public void updateDepartment(Department dep) throws RemoteException {
		manager.merge(dep);	
	}

	@Override
	public void deleteDepartment(BigDecimal id) throws RemoteException {
		manager.remove(selectDepartment(id));
	}

	@Override
	public Department selectDepartment(BigDecimal id) throws RemoteException {
		return manager.find(Department.class,id);
	}

	@Override
	public List<Department> showAll() throws RemoteException {
		return manager.createQuery("Select d from Department d").getResultList();
	}

	@Override
	public void update(Department dep) throws RemoteException {
		 Query query = manager.createQuery("UPDATE Department d SET d.dname = :dName, d.location = :dLoc WHERE d.deptno = :deptNo").setParameter("dName", dep.getDname()).setParameter("dLoc", dep.getLocation()).setParameter("deptNo", dep.getDeptno());
		 query.executeUpdate();
	}

	@Override
	public List<Department> selectDepartment(String id, String dnameVal, String locationVal) throws RemoteException {
		/*CriteriaBuilder cb = manager.getCriteriaBuilder();
		CriteriaQuery<Department> cq = cb.createQuery(Department.class);
		Metamodel m = manager.getMetamodel();
		Root<Department> spareRoot = cq.from(Department.class);
		
		Predicate conditionId = null;
		Predicate conditionDname = null;
		Predicate conditionLocation = null;
		
		if (!id.isEmpty())
			conditionId = cb.equal(spareRoot.get("deptno"), new BigDecimal(id));
		if (!dnameVal.isEmpty()) 
			conditionDname = cb.equal(spareRoot.get("dname"), dnameVal);
		if (!locationVal.isEmpty())
			conditionLocation = cb.equal(spareRoot.get("location"), locationVal);
			
		cq.where(cb.and(conditionId, conditionDname, conditionLocation));	
		TypedQuery<Department> q = manager.createQuery(cq);
		 return q.getResultList();*/
		
		CriteriaBuilder cb = manager.getCriteriaBuilder();
		CriteriaQuery<Department> cq = cb.createQuery(Department.class);
		Metamodel m = manager.getMetamodel();
		EntityType<Department> spareEntityType = m.entity(Department.class);
		Root<Department> spareRoot = cq.from(Department.class);
		
		// conditions are true everywhere:
		Predicate conditionId =  cb.notEqual(spareRoot.get("deptno"), new BigDecimal(-1));
		Predicate conditionDname = cb.notEqual(spareRoot.get("deptno"), new BigDecimal(-1));
		Predicate conditionLocation =  cb.notEqual(spareRoot.get("deptno"), new BigDecimal(-1)); 
		
		conditionId = (!id.isEmpty() ?  cb.equal(spareRoot.get("deptno"), new BigDecimal(id)) : conditionId);
		conditionDname = (!dnameVal.isEmpty() ?  cb.equal(spareRoot.get("dname"), dnameVal) : conditionDname);
		conditionLocation = (!locationVal.isEmpty() ?  cb.equal(spareRoot.get("location"), locationVal) : conditionLocation);

		cq.where(cb.and(conditionId, conditionDname, conditionLocation));	
		TypedQuery<Department> q = manager.createQuery(cq);
		 return q.getResultList();
	}

	
	@Override
	public String[] getFieldsName()throws RemoteException  {
		return new String[]{"deptno", "dname", "location"};
	}
}
