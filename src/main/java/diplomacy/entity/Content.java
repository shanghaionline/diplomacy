package diplomacy.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import diplomacy.entity.status.ContentStatus;

@Entity
@Table(name = "dip_content")
public class Content {
	private Long id;
	private List<ContentMeta> metas;
	private String contentType;
	private User user;
	private ContentStatus status;
	private String title;
	private String content;
	private Date created;
	private Date modified;
	private String author;
	private String source;
	private String summary;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@OneToMany(mappedBy = "content")
	public List<ContentMeta> getMetas() {
		return metas;
	}
	public void setMetas(List<ContentMeta> metas) {
		this.metas = metas;
	}
	@Column(length = 20)
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	@ManyToOne
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@Enumerated(EnumType.STRING)
	@Column(length = 11)
	public ContentStatus getStatus() {
		return status;
	}
	public void setStatus(ContentStatus status) {
		this.status = status;
	}
	@Lob
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Lob
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Date getModified() {
		return modified;
	}
	public void setModified(Date modified) {
		this.modified = modified;
	}
	@Column(length = 80)
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	@Column(length = 100)
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	@Lob
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	
}
