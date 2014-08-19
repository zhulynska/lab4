package ua.edu.sumdu.j2ee.Zhulynska.userBean;

import java.util.List;

import javax.ejb.Local;


@Local
public interface Users {

	public User createUser(User user);

	//public void updateSalaryGrade(User user);

	//public void deleteSalaryGrade(int id);
	
	 User selectUser(int id);
	
	 List<User> showAll();
	
	 boolean  findByName(String loginNew, String passwordNew);
	
	 void registryUser(String firstNameNew, String surnameNew, String loginNew, String passwordNew);
	 

}
