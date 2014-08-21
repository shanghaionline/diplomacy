package diplomacy.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "dip_content_meta")
public class ContentMeta {
	private Long id;
	private Content content;
	private String key;
	private String value;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@ManyToOne
	public Content getContent() {
		return content;
	}
	public void setContent(Content content) {
		this.content = content;
	}
	@Column(name = "meta_key", length = 200)
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	@Lob @Column(name = "meta_value")
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
