package diplomacy.entity;

import java.util.Date;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import diplomacy.entity.status.UserStatus;

@Entity
@Table(name = "dip_user")
public class User {
	private Long id;
	private String login;
	private String passwd;
	private String nicename;
	private Date registered;
	private UserStatus status;
	private User inviter;
	private Map<String, UserMeta> metas;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Column(length = 60, nullable = false)
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	@Column(length = 64, nullable = false)
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	@Column(length = 50)
	public String getNicename() {
		return nicename;
	}
	public void setNicename(String nickname) {
		this.nicename = nickname;
	}
	public Date getRegistered() {
		return registered;
	}
	public void setRegistered(Date registered) {
		this.registered = registered;
	}
	@Enumerated(EnumType.STRING)
	@Column(length = 11, nullable = false)
	public UserStatus getStatus() {
		return status;
	}
	public void setStatus(UserStatus status) {
		this.status = status;
	}
	@ManyToOne
	public User getInviter() {
		return inviter;
	}
	public void setInviter(User inviter) {
		this.inviter = inviter;
	}
	@OneToMany(mappedBy = "user")
	@MapKey(name = "key")
	public Map<String, UserMeta> getMetas() {
		return metas;
	}
	public void setMetas(Map<String, UserMeta> metas) {
		this.metas = metas;
	}
}

