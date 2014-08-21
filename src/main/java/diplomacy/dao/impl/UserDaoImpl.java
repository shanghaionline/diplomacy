package diplomacy.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import diplomacy.dao.UserDao;
import diplomacy.entity.User;
import diplomacy.entity.UserMeta;
import diplomacy.entity.status.UserStatus;

@Repository("userDao")
public class UserDaoImpl extends HibernateDaoSupport implements UserDao {


	@Override
	public void save(User user) {
		HibernateTemplate template = getHibernateTemplate();
		template.save(user);
	}
	

	@Override
	public void refresh(User user) {
		HibernateTemplate template = getHibernateTemplate();
		template.refresh(user);
	}


	@Override
	public void delete(User user) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public User getUserByLogin(String login) {
		DetachedCriteria criteria = DetachedCriteria.forClass(User.class);
		criteria.add(Restrictions.eq("login", login));
		criteria.add(Restrictions.eq("status", UserStatus.ENABLED));
		List<?> list = getHibernateTemplate().findByCriteria(criteria, 0, 1);
		if (list.isEmpty()) return null;
		return (User)list.get(0);
	}
	
	@Override
	public User getUserById(Long id) {
		User user = getHibernateTemplate().get(User.class, id);
		return user;
	}

	@Override
	public void setUserMeta(User user, UserMeta meta) {
		HibernateTemplate template = getHibernateTemplate();
		meta.setUser(user);
		template.saveOrUpdate(meta);
	}

	
}
