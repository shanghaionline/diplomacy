package diplomacy.service;

import diplomacy.entity.Content;
import diplomacy.entity.User;
import diplomacy.vo.PagerBean;

public interface ContentService {
	Content create(User user, String title, String content, 
			String author, String source, String summary);
	Content save(User user, Content content);
	PagerBean<Content> query(String q, int offset, int limit);
}
