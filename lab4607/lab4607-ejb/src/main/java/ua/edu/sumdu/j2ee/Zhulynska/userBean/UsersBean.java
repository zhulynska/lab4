package ua.edu.sumdu.j2ee.Zhulynska.userBean;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class UsersBean implements Users {
	@PersistenceContext(unitName = "user")
	private EntityManager manager;

	@Override
	public User createUser(User user) {
		manager.persist(user);
		return user;
	}

	@Override
	public User selectUser(int id) {
		return manager.find(User.class, new Integer(id));
	}

	@Override
	public List<User> showAll() {
		// TODO Auto-generated method stub
		return manager.createQuery("Select u from User u").getResultList();
	}

	@Override
	public boolean findByName(String log, String pass) {
		
	List list = manager.createQuery("from User u where u.login = :Login and u.password = :Password").setParameter("Login", log).setParameter("Password", pass).getResultList();
		return list.isEmpty()? false : true;	
	}

	
	@Override
	public void registryUser(String firstNameNew, String surnameNew,
			String loginNew, String passwordNew) {
		User user1 = new User();
		user1.setName(firstNameNew);
		user1.setSurname(surnameNew);
		user1.setLogin(loginNew);
		user1.setPassword(passwordNew);
		this.createUser(user1);
	}


}
