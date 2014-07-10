package ua.edu.sumdu.j2ee.Zhulynska.departmentBean;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Department.class)
public abstract class Department_ {

	public static volatile SingularAttribute<Department, String> location;
	public static volatile SingularAttribute<Department, String> dname;
	public static volatile SingularAttribute<Department, BigDecimal> deptno;

}

