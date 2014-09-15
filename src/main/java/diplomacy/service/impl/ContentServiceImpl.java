package diplomacy.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import diplomacy.dao.ContentDao;
import diplomacy.entity.Content;
import diplomacy.entity.User;
import diplomacy.entity.status.ContentStatus;
import diplomacy.entity.status.UserStatus;
import diplomacy.service.ContentService;
import diplomacy.vo.PagerBean;

@Service("contentService")
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
        return content;
    }
    
    
    @Override
	public Content get(Long id) {
    	Content content = contentDao.get(id);
		return content;
	}

	@Override
    public PagerBean<Content> query(String q, int page, int size) {
    	page = page <= 0 ? 1 : page;
        return contentDao.query(q, (page - 1) * size, size);
    }
	
	@Override
	@Transactional(readOnly = false)
	public void delete(Long contentId) {
		Content content = contentDao.get(contentId);
		if(content != null && content.getStatus() == ContentStatus.ENABLED){
			contentDao.delete(content);
		}
	}
	
    public void setContentDao(ContentDao contentDao) {
        this.contentDao = contentDao;
    }


}
