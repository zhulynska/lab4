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
import javax.persistence.criteria.Root;

@Stateful
public class DepartmentsBean implements Departments {

    @PersistenceContext(unitName = "department")
    private EntityManager manager;

    @Override
    public Department createDepartment(Department dep) {
        manager.persist(dep);
        return dep;
    }

    @Override
    public void updateDepartment(Department dep){
        manager.merge(dep);
    }

    @Override
    public void deleteDepartment(BigDecimal id) {
        manager.remove(selectDepartment(id));
    }

    @Override
    public Department selectDepartment(BigDecimal id)  {
        return manager.find(Department.class, id);
    }

    @Override
    public List<Department> showAll(){
        return manager.createQuery("Select d from Department d").getResultList();
    }

    @Override
    public void update(Department dep) {
        Query query = manager.createQuery("UPDATE Department d SET d.dname = :dName, d.location = :dLoc WHERE d.deptno = :deptNo").setParameter("dName", dep.getDname()).setParameter("dLoc", dep.getLocation()).setParameter("deptNo", dep.getDeptno());
        query.executeUpdate();
    }

    @Override
    public List<Department> selectDepartment(String id, String dnameVal, String locationVal) {
        CriteriaBuilder cb = manager.getCriteriaBuilder();
        CriteriaQuery<Department> cq = cb.createQuery(Department.class);
        Root<Department> spareRoot = cq.from(Department.class);

        if (!id.isEmpty()) {
            cq.where(cb.and(cb.equal(spareRoot.<String>get("deptno"), id)));
        }

        if (!dnameVal.isEmpty()) {
            cq.where(cb.and(cb.like(spareRoot.<String>get("dname"), "%" + dnameVal + "%")));
        }

        if (!locationVal.isEmpty()) {
            cq.where(cb.and(cb.like(spareRoot.<String>get("location"), "%" + locationVal + "%")));
        }

        TypedQuery<Department> q = manager.createQuery(cq);
        return q.getResultList();
    }

    @Override
    public String[] getFieldsName(){
        return new String[]{"deptno", "dname", "location"};
    }

    @Override
    public List<String> getDeptName() {
        return manager.createQuery("Select d.dname from Department d").getResultList();
    }

    @Override
    public BigDecimal getDepartmentNo(String deptName) {
        return (BigDecimal) (manager.createQuery("select dept.deptno from Department dept where  dept.dname=:someName").
                setParameter("someName", deptName).getSingleResult());
    }
}
