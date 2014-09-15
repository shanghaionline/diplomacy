package diplomacy.dao;

import diplomacy.entity.Content;
import diplomacy.entity.User;
import diplomacy.vo.PagerBean;

public interface ContentDao {
    void save(Content content);

    void delete(Content content);

    void refresh(Content content);

    Content get(Long id);

    PagerBean<Content> query(String q, int offset, int limit);
}
