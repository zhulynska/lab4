package ua.edu.sumdu.j2ee.Zhulynska.salaryGradeBean;

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
public class SalaryGradesBean implements SalaryGrades {

    @PersistenceContext(unitName = "salary")
    private EntityManager manager;

    @Override
    public SalaryGrade createSalGrade(SalaryGrade sal) throws RemoteException {
        manager.persist(sal);
        return sal;
    }

    @Override
    public void updateSalaryGrade(SalaryGrade sal) throws RemoteException {
        manager.merge(sal);
    }

    @Override
    public void deleteSalaryGrade(Long id) throws RemoteException {
        manager.remove(selectSalaryGrade(id));
    }

    @Override
    public SalaryGrade selectSalaryGrade(Long id) throws RemoteException {
        return manager.find(SalaryGrade.class, id);
    }

    @Override
    public List<SalaryGrade> showAll() throws RemoteException {
        return manager.createQuery("Select s from SalaryGrade s").getResultList();
    }

    @Override
    public void update(SalaryGrade sal) throws RemoteException {
        Query query = manager.createQuery("UPDATE SalaryGrade s SET s.minsal = :minSal, s.hisal = :hiSal WHERE s.grade = :someGrade").setParameter("minSal", sal.getMinsal()).setParameter("hiSal", sal.getHisal()).setParameter("someGrade", sal.getGrade());
        query.executeUpdate();
    }

    @Override
    public List<SalaryGrade> selectSalaryGrade(String id, String minsalVal, String hisalVal) throws RemoteException {
        CriteriaBuilder cb = manager.getCriteriaBuilder();
        CriteriaQuery<SalaryGrade> cq = cb.createQuery(SalaryGrade.class);
        Root<SalaryGrade> spareRoot = cq.from(SalaryGrade.class);

        if (!id.isEmpty()) {
            cq.where(cb.and(cb.equal(spareRoot.<String>get("grade"), id)));
        }

        if (!minsalVal.isEmpty()) {
            cq.where(cb.and(cb.equal(spareRoot.<String>get("minsal"), minsalVal)));
        }

        if (!hisalVal.isEmpty()) {
            cq.where(cb.and(cb.equal(spareRoot.<String>get("hisal"), hisalVal)));
        }

        TypedQuery<SalaryGrade> q = manager.createQuery(cq);
        return q.getResultList();
    }

    @Override
    public String[] getFieldsName() throws RemoteException {
        return new String[]{"grade", "minsal", "hisal"};
    }
}
