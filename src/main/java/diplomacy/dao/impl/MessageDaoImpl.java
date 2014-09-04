package diplomacy.dao.impl;

import java.util.Date;
import java.util.List;

import diplomacy.vo.PagerBean;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import diplomacy.dao.MessageDao;
import diplomacy.entity.Attachment;
import diplomacy.entity.Message;
import diplomacy.entity.MessageBox;
import diplomacy.entity.MessageMeta;
import diplomacy.entity.User;
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
		return putMessageBox(message, message.getReceiver());
	}

	@Override
	public MessageBox putMessageBox(Message message, User receiver) {
		MessageBox box = new MessageBox();
		box.setMessage(message);
		box.setReceiver(receiver);
		box.setStatus(MessageStatus.UNREAD);
		getHibernateTemplate().saveOrUpdate(box);
		return null;
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

	@Override
	public Attachment putAttachment(Attachment attachment) {
		getHibernateTemplate().saveOrUpdate(attachment);
		return attachment;
	}

    @Override
    public PagerBean<Message> listMessageBySender(User user, int offset, int limit) {
        PagerBean<Message> ret = new PagerBean<>();
        HibernateTemplate template = getHibernateTemplate();
        DetachedCriteria criteria = queryCriteriaMessageBySender(user);
        DetachedCriteria countCriteria = queryCriteriaMessageBySender(user);
        criteria.addOrder(Property.forName("created").desc());
        countCriteria.setProjection(Projections.rowCount());
        ret.setAllCount((Long)template.findByCriteria(countCriteria).get(0));
        @SuppressWarnings("unchecked")
        List<Message> list = (List<Message>)template.findByCriteria(criteria, offset, limit);
        ret.setList(list);
        ret.setStart(offset);
        ret.setSize(limit);
        return ret;
    }

    @Override
    public PagerBean<MessageBox> listMessageBoxByReceiver(User user, int offset, int limit) {
        PagerBean<MessageBox> ret = new PagerBean<>();
        HibernateTemplate template = getHibernateTemplate();
        DetachedCriteria criteria = queryCriteriaMessageBoxByReceiver(user);
        DetachedCriteria countCriteria = queryCriteriaMessageBoxByReceiver(user);
        criteria.addOrder(Property.forName("id").desc());
        countCriteria.setProjection(Projections.rowCount());
        ret.setAllCount((Long)template.findByCriteria(countCriteria).get(0));
        @SuppressWarnings("unchecked")
        List<MessageBox> list = (List<MessageBox>)template.findByCriteria(criteria, offset, limit);
        ret.setList(list);
        ret.setStart(offset);
        ret.setSize(limit);
        return ret;
    }

    private DetachedCriteria queryCriteriaMessageBoxByReceiver(User user) {
        DetachedCriteria criteria = DetachedCriteria.forClass(MessageBox.class);
        criteria.add(Restrictions.eq("receiver", user));
        criteria.add(Restrictions.ne("status", MessageStatus.DELETED));
        return criteria;
    }

    private DetachedCriteria queryCriteriaMessageBySender(User user) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Message.class);
        criteria.add(Restrictions.eq("sender", user));
        criteria.add(Restrictions.or(Restrictions.eq("msgType", MessageType.SINGLEMSG),
                Restrictions.eq("msgType", MessageType.MULTIPLE)));
        criteria.add(Restrictions.ne("status", MessageStatus.DELETED));
        return criteria;
    }


	@Override
	public MessageBox getMessageBoxById(int id) {
		HibernateTemplate template = getHibernateTemplate();
		DetachedCriteria criteria = DetachedCriteria.forClass(MessageBox.class);
		criteria.add(Restrictions.eq("id", id));
		criteria.add(Restrictions.ne("status", MessageStatus.DELETED));
		@SuppressWarnings("unchecked")
	    List<MessageBox> list = (List<MessageBox>)template.findByCriteria(criteria, 0, 1);
		if(list.isEmpty()) return null;
		return list.get(0);
	}


	@Override
	public Message getMessageById(long id) {
		HibernateTemplate template = getHibernateTemplate();
		DetachedCriteria criteria = DetachedCriteria.forClass(Message.class);
		criteria.add(Restrictions.eq("id", id));
		criteria.add(Restrictions.ne("status", MessageStatus.DELETED));
		@SuppressWarnings("unchecked")
	    List<Message> list = (List<Message>)template.findByCriteria(criteria, 0, 1);
		if(list.isEmpty()) return null;
		return list.get(0);
	}
    
    
}
