package ua.edu.sumdu.j2ee.Zhulynska.salaryGradeBean;

import java.rmi.RemoteException;
import java.util.List;
import javax.ejb.Remote;


@Remote
public interface SalaryGrades {
		public SalaryGrade createSalGrade(SalaryGrade sal) ;

		public void updateSalaryGrade(SalaryGrade sal);

		public void deleteSalaryGrade(Long id);
		
		public SalaryGrade selectSalaryGrade(Long id);
		
		public List<SalaryGrade> selectSalaryGrade(String id,  String minsalVal,  String hisalVal);
		
		public List<SalaryGrade> showAll();

		public void update(SalaryGrade sal) ;
		
		String[] getFieldsName() ;
}
