package ua.edu.sumdu.j2ee.Zhulynska.employeeBean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.ejb.EJB;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import ua.edu.sumdu.j2ee.Zhulynska.departmentBean.Department;


@Entity
@Table(name="emp")
public class Employee implements Serializable{
	public Employee() {
	}
	
	@Id
	@Column(name="empno") 
	public Long empno;
	
	@Column(name="ENAME", length=10)
	public String ename;
	
	@Column(name="job", length=10)
	public String job;
	
	@Column(name="MGR")
	public Integer manager;
	
	@Column(name="HIREDATE")
	@Temporal(value=TemporalType.DATE)
	public Date hiredate;
	
	@Column(name="SAL")
	public Float salary;
	
	@Column(name="COMM")
	public Float commission;
	
	@ManyToOne 
	@JoinColumn(name="deptno") // foreign key
	public Department department;

	public Long getEmpno() {
		return empno;
	}

	public void setEmpno(Long empno) {
		this.empno = empno;
	}

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public Integer getManager() {
		return manager;
	}

	public void setManager(Integer manager) {
		this.manager = manager;
	}

	public Date getHiredate() {
		return hiredate;
	}

	public void setHiredate(Date hiredate) {
		this.hiredate = hiredate;
	}

	public Float getSalary() {
		return salary;
	}

	public void setSalary(Float salary) {
		this.salary = salary;
	}

	public Float getCommission() {
		return commission;
	}

	public void setCommission(Float commission) {
		this.commission = commission;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	@Override
	public String toString() {
		return "Employee [empno=" + empno + ", ename=" + ename + ", job=" + job
				+ ", manager=" + manager + ", hiredate=" + hiredate
				+ ", salary=" + salary + ", commission=" + commission
				+ ", department=" + department + "]";
	}
}
