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
@Table(name = "dip_user_meta")
public class UserMeta {
	private Long id;
	private User user;
	private String key;
	private String value;
	
	public UserMeta() {}
	public UserMeta(String key, String value) {
		setKey(key); setValue(value);
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@ManyToOne
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@Column(name = "meta_key", length = 200, nullable = false)
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
