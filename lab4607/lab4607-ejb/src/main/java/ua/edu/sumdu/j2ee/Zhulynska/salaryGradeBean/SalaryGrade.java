package ua.edu.sumdu.j2ee.Zhulynska.salaryGradeBean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="salgrade")
public class SalaryGrade implements Serializable{
	public SalaryGrade() {
	}
	
	@Id
	@Column(name="grade") 
	public Long grade;
	
	@Column(name="minsal")
	public Float minsal;
	
	@Column(name="hisal")
	public Float hisal;

	public Long getGrade() {
		return grade;
	}

	public void setGrade(Long grade) {
		this.grade = grade;
	}

	public Float getMinsal() {
		return minsal;
	}

	public void setMinsal(Float minsal) {
		this.minsal = minsal;
	}

	public Float getHisal() {
		return hisal;
	}

	public void setHisal(Float hisal) {
		this.hisal = hisal;
	}

	@Override
	public String toString() {
		return "SalaryGrade [grade=" + grade + ", minsal=" + minsal
				+ ", hisal=" + hisal + "]";
	}
	
	
	
	
}
