package diplomacy.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import diplomacy.dao.MessageDao;
import diplomacy.entity.Message;
import diplomacy.entity.MessageBox;
import diplomacy.entity.MessageMeta;
import diplomacy.entity.status.MessageStatus;
import diplomacy.entity.status.MessageType;

@Repository("messageDao")
public class MessageDaoImpl extends HibernateDaoSupport implements MessageDao {

	@Override
	public void save(Message message) {
		HibernateTemplate template = getHibernateTemplate();
		if (message.getId() == null) {
			message.setCreated(new Date());
		}
		template.saveOrUpdate(message);
	}
	
	
	@Override
	public void delete(Message message) {
		message.setStatus(MessageStatus.DELETED);
		getHibernateTemplate().update(message);
	}


	@Override
	public void refresh(Message message) {
		HibernateTemplate template = getHibernateTemplate();
		template.refresh(message);
	}

	@Override
	public void setMessageMeta(Message message, MessageMeta meta) {
		HibernateTemplate template = getHibernateTemplate();
		meta.setMessage(message);
		template.saveOrUpdate(meta);
	}

	@Override
	public MessageBox putMessageBox(Message message) {
		MessageBox box = new MessageBox();
		box.setMessage(message);
		box.setReceiver(message.getReceiver());
		box.setStatus(MessageStatus.UNREAD);
		getHibernateTemplate().saveOrUpdate(box);
		return box;
	}


	@Override
	public Message getValidCodeMessage(String target) {
		HibernateTemplate template = getHibernateTemplate();
		DetachedCriteria w = DetachedCriteria.forClass(MessageMeta.class);
		w.add(Restrictions.eq("key", "validcode_target"));
		w.add(Restrictions.eq("value", target));
		w.setProjection(Projections.property("id"));
		DetachedCriteria criteria = DetachedCriteria.forClass(Message.class);
		criteria.add(Subqueries.exists(w));
		criteria.add(Restrictions.eq("status",MessageStatus.READED));
		criteria.add(Restrictions.eq("msgType", MessageType.VALIDCODE));
		criteria.addOrder(Property.forName("created").desc());
		List<?> list = template.findByCriteria(criteria, 0, 1);
		if (list.isEmpty()) return null;
		return (Message)list.get(0);
	}

}
