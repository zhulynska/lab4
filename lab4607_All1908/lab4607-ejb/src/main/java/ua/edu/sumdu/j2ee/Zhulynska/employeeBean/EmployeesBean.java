package ua.edu.sumdu.j2ee.Zhulynska.employeeBean;

import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.List;
import java.util.Locale;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Stateful
public class EmployeesBean implements Employees {

    @PersistenceContext(unitName = "employee")
    private EntityManager manager;
    public static final SimpleDateFormat DateFormat1 = new SimpleDateFormat(
            "yyyy-MM-dd", Locale.ENGLISH);

    @Override
    public Employee createEmployee(Employee emp) {
        manager.persist(emp);
        return emp;
    }

    @Override
    public void updateEmployee(Employee emp) {
        manager.merge(emp);
    }

    @Override
    public void deleteEmployee(Long id) {
        manager.remove(selectEmployee(id));
    }

    @Override
    public Employee selectEmployee(Long id) {
        return manager.find(Employee.class, id);
    }

    @Override
    public List<Employee> showAll() {
        return manager.createQuery("Select e from Employee e").getResultList();
    }

    @Override
    public void update(Employee emp) {
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
    public List<Employee> selectEmployee(
            String nameSelect,
            String jobSelect,
            String departmentSelect,
            Long managerSelect,
            String salaryMinSelect,
            String salaryMaxSelect) {

        CriteriaBuilder cb = manager.getCriteriaBuilder();
        CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);
        Root<Employee> spareRoot = cq.from(Employee.class);

        if (!nameSelect.isEmpty()) {
            cq.where(cb.and(cb.like(spareRoot.<String>get("ename"), "%" + nameSelect + "%")));
        }

        if (!jobSelect.isEmpty()) {
            cq.where(cb.and(cb.like(spareRoot.<String>get("job"), "%" + jobSelect + "%")));
            //cq.where(cb.and(cb.equal(spareRoot.get("job"), jobSelect)));
        }

        if (managerSelect != null) {
            cq.where(cb.and(cb.equal(spareRoot.<Long>get("manager"), managerSelect)));
        }

        if (!departmentSelect.isEmpty()) {
            cq.where(cb.and(cb.equal(spareRoot.get("department").get("dname"), departmentSelect)));
        }

        if (!salaryMinSelect.isEmpty() && !salaryMaxSelect.isEmpty()) {
            cq.where(cb.and(cb.between(spareRoot.<Float>get("salary"), new Float(salaryMinSelect), new Float(salaryMaxSelect))));
        } else if (!salaryMinSelect.isEmpty()) {
            cq.where(cb.and(cb.ge(spareRoot.<Float>get("salary"), new Float(salaryMinSelect))));
        } else if (!salaryMaxSelect.isEmpty()) {
            cq.where(cb.and(cb.le(spareRoot.<Float>get("salary"), new Float(salaryMaxSelect))));
        }
        TypedQuery<Employee> q = manager.createQuery(cq);
        return q.getResultList();
    }

    @Override
    public String[] getFieldsName() {
        return new String[]{"empno", "ename", "job", "manager", "hiredate", "salary", "commission", "department"};
    }

    @Override
    public String getManagerName(String id) {
        String rez = "null";
        Long rezId = null;
        try {
            rezId = Long.parseLong(id);
        } catch (Exception e) {

        }
        if (rezId != null) {
            rez = (String) manager.createQuery("select emp.ename from Employee emp where emp.empno =:managerNo").
                    setParameter("managerNo", new Long(id)).getSingleResult();
        }
        return rez;
    }

    @Override
    public List<Employee> getDependents(Long id) {
        return manager.createQuery("select emp from Employee emp where emp.manager =:managerNo").setParameter("managerNo", id).getResultList();
    }

    @Override
    public List<Employee> getManagers() {
        return manager.createQuery("Select e from Employee e where e.empno =ANY (select e1.manager from Employee e1)").getResultList();
    }

    @Override
    public Long getManagerNo(String managerName) {
        Long rez = null;
        List<Employee> numbers = new ArrayList<Employee>();
        if (managerName != null) {
            numbers = manager.createQuery("from Employee emp where emp.ename=:someName").setParameter("someName", managerName).getResultList();
            for (Employee elem : numbers) {
                if (!elem.equals(null)) {
                    rez = elem.getEmpno();
                }
            }
        }
        return rez;
    }

    public ArrayList<Employee> getManagersTree(Long empId) {
        ArrayList<Employee> hierarchyBottom_up = new ArrayList<Employee>();
        Employee emp = this.selectEmployee(empId);
        while (emp != null) {
            Employee mgr = null;
            try {
                mgr = (Employee) manager.createQuery("from Employee e where e.empno =:employeeId").setParameter("employeeId", emp.getManager()).getSingleResult();
                hierarchyBottom_up.add(mgr);
                emp = mgr;
            } catch (Exception e) {
                emp = null;
            }
        }
        return hierarchyBottom_up;
    }
}
