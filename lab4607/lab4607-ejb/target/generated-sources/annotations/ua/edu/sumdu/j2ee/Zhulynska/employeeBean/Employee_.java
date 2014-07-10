package ua.edu.sumdu.j2ee.Zhulynska.employeeBean;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import ua.edu.sumdu.j2ee.Zhulynska.departmentBean.Department;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Employee.class)
public abstract class Employee_ {

	public static volatile SingularAttribute<Employee, Date> hiredate;
	public static volatile SingularAttribute<Employee, Integer> manager;
	public static volatile SingularAttribute<Employee, Long> empno;
	public static volatile SingularAttribute<Employee, Department> department;
	public static volatile SingularAttribute<Employee, String> ename;
	public static volatile SingularAttribute<Employee, String> job;
	public static volatile SingularAttribute<Employee, Float> salary;
	public static volatile SingularAttribute<Employee, Float> commission;

}

