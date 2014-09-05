package diplomacy.service.impl;

import org.springframework.transaction.annotation.Transactional;

import diplomacy.dao.ContentDao;
import diplomacy.entity.Content;
import diplomacy.entity.User;
import diplomacy.entity.status.ContentStatus;
import diplomacy.service.ContentService;
import diplomacy.vo.PagerBean;

public class ContentServiceImpl implements ContentService {

    private ContentDao contentDao;

    @Override
    @Transactional(readOnly = false)
    public Content create(User user, String title, String content,
                          String author, String source, String summary) {
        Content c = new Content();
        c.setUser(user);
        c.setTitle(title);
        c.setContent(content);
        c.setAuthor(author);
        c.setSource(source);
        c.setSummary(summary);
        c.setStatus(ContentStatus.ENABLED);
        contentDao.save(c);
        contentDao.refresh(c);
        return c;
    }

    @Override
    @Transactional(readOnly = false)
    public Content save(User user, Content content) {
        contentDao.save(content);
        contentDao.refresh(content);
        return content;
    }

    @Override
    public PagerBean<Content> query(String q, int offset, int limit) {
        return contentDao.query(q, offset, limit);
    }

    public void setContentDao(ContentDao contentDao) {
        this.contentDao = contentDao;
    }


}
