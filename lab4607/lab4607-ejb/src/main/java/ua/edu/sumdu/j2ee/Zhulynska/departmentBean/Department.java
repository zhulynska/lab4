package ua.edu.sumdu.j2ee.Zhulynska.departmentBean;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="dept")
public class Department implements Serializable{

	public Department() {
	}
	
	@Id
	@Column(name="deptno") 
	private BigDecimal deptno;
	
	@Column(name="DNAME", length=15)
	private String dname;
	
	@Column(name="LOC", length=15)
	private String location;

	public BigDecimal getDeptno() {
		return deptno;
	}

	public void setDeptno(BigDecimal deptno) {
		this.deptno = deptno;
	}

	public String getDname() {
		return dname;
	}

	public void setDname(String dname) {
		this.dname = dname;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}


	public String getString() {
		return "Department [deptno=" + deptno + ", dname=" + dname
				+ ", location=" + location + "]";
	}

	@Override
	public String toString() {
		return deptno.toString();
	}
	
	
	
	
}
