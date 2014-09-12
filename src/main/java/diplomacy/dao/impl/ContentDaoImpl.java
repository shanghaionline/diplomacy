package diplomacy.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import diplomacy.dao.ContentDao;
import diplomacy.entity.Content;
import diplomacy.entity.status.ContentStatus;
import diplomacy.vo.PagerBean;

@Repository("contentDao")
public class ContentDaoImpl extends HibernateDaoSupport implements ContentDao {

    @Override
    public void save(Content content) {
        Date now = new Date();
        if (content.getId() == null) {
            content.setCreated(now);
        }
        content.setModified(now);
        getHibernateTemplate().saveOrUpdate(content);
    }

    @Override
    public void delete(Content content) {
        content.setStatus(ContentStatus.DELETED);
        getHibernateTemplate().update(content);
    }

    @Override
    public void refresh(Content content) {
        getHibernateTemplate().refresh(content);
    }

    @Override
    public Content get(Long id) {
        return getHibernateTemplate().get(Content.class, id);
    }

    @Override
    public PagerBean<Content> query(String q, int offset, int limit) {
        PagerBean<Content> ret = new PagerBean<Content>();
        HibernateTemplate template = getHibernateTemplate();
        DetachedCriteria criteria = queryCriteria(q);
        DetachedCriteria countCriteria = queryCriteria(q);
        criteria.addOrder(Property.forName("modified").desc());
        countCriteria.setProjection(Projections.rowCount());
        ret.setAllCount((Long) template.findByCriteria(countCriteria).get(0));
        @SuppressWarnings("unchecked")
        List<Content> list = (List<Content>) template.findByCriteria(criteria, offset, limit);
        ret.setList(list);
        ret.setStart(offset);
        ret.setSize(limit);
        return ret;
    }

    private DetachedCriteria queryCriteria(String q) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Content.class);
        criteria.add(Restrictions.eq("status", ContentStatus.ENABLED));
        if (q != null && !q.isEmpty())
            criteria.add(Restrictions.like("content", "%" + q + "%"));
        return criteria;
    }


}
