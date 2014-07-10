package ua.edu.sumdu.j2ee.Zhulynska.salaryGradeBean;

import java.rmi.RemoteException;
import java.util.List;
import javax.ejb.Remote;


@Remote
public interface SalaryGrades {
		public SalaryGrade createSalGrade(SalaryGrade sal) throws RemoteException;

		public void updateSalaryGrade(SalaryGrade sal)throws RemoteException;

		public void deleteSalaryGrade(Long id)throws RemoteException;
		
		public SalaryGrade selectSalaryGrade(Long id)throws RemoteException;
		
		public List<SalaryGrade> selectSalaryGrade(String id,  String minsalVal,  String hisalVal) throws RemoteException;
		
		public List<SalaryGrade> showAll()throws RemoteException;

		public void update(SalaryGrade sal) throws RemoteException;
		
		String[] getFieldsName() throws RemoteException ;
}
