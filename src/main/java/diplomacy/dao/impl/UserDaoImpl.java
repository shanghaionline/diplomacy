package diplomacy.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import diplomacy.dao.UserDao;
import diplomacy.entity.User;
import diplomacy.entity.UserMeta;
import diplomacy.entity.status.UserStatus;
import diplomacy.vo.PagerBean;

@Repository("userDao")
public class UserDaoImpl extends HibernateDaoSupport implements UserDao {

	@Override
	public void save(User user) {
		HibernateTemplate template = getHibernateTemplate();
		template.saveOrUpdate(user);
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

	@Override
	public List<User> listUserByMeta(String key, String value) {
		return listUserByMeta(UserStatus.ENABLED, key, value);
	}

    @Override
    public List<User> listUserByMeta(UserStatus status, String key, String value) {
        HibernateTemplate template = getHibernateTemplate();
        DetachedCriteria w = DetachedCriteria.forClass(UserMeta.class);
        w.add(Restrictions.eq("key", key));
        w.add(Restrictions.eq("value", value));
        w.setProjection(Projections.property("id"));
        DetachedCriteria criteria = DetachedCriteria.forClass(User.class);
        criteria.add(Subqueries.exists(w));
        criteria.add(Restrictions.eq("status", status));
        @SuppressWarnings("unchecked")
        List<User> ret = (List<User>)template.findByCriteria(criteria);
        return ret;
    }

	@Override
	public PagerBean<User> queryUser(String query, int offset, int limit) {
		PagerBean<User> ret = new PagerBean<User>();
		HibernateTemplate template = getHibernateTemplate();
		DetachedCriteria criteria = queryCriteriaUser(query);
		DetachedCriteria countCriteria = queryCriteriaUser(query);
		criteria.addOrder(Property.forName("login").asc());
		countCriteria.setProjection(Projections.rowCount());
		ret.setAllCount((Long)template.findByCriteria(countCriteria).get(0));
		@SuppressWarnings("unchecked")
		List<User> list = (List<User>)template.findByCriteria(criteria, offset, limit);
		ret.setList(list);
        ret.setStart(offset);
        ret.setSize(limit);
		return ret;
	}
    
    private DetachedCriteria queryCriteriaUser(String query) {
    	DetachedCriteria criteria = DetachedCriteria.forClass(User.class);
    	if(query != null && !query.trim().isEmpty()) {
    		criteria.add(Restrictions.or(Restrictions.like("login", "%" + query + "%"),
        	Restrictions.like("nicename", "%" + query + "%")));
    	}
        criteria.add(Restrictions.eq("status", UserStatus.ENABLED));
        return criteria;
    }
    
}
