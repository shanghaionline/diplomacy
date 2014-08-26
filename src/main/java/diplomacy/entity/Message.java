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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import diplomacy.entity.status.MessageStatus;
import diplomacy.entity.status.MessageType;

@Entity
@Table(name = "dip_message")
public class Message {
	private Long id;
	private Map<String, MessageMeta> metas;
	private MessageType msgType;
	private User sender;
	private User receiver;
	private String title;
	private String content;
	private MessageStatus status;
	private Date created;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@OneToMany(mappedBy = "message")
	@MapKey(name = "key")
	public Map<String, MessageMeta> getMetas() {
		return metas;
	}
	public void setMetas(Map<String, MessageMeta> metas) {
		this.metas = metas;
	}
	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	public MessageType getMsgType() {
		return msgType;
	}
	public void setMsgType(MessageType msgType) {
		this.msgType = msgType;
	}
	@ManyToOne
	public User getSender() {
		return sender;
	}
	public void setSender(User sender) {
		this.sender = sender;
	}
	@ManyToOne
	public User getReceiver() {
		return receiver;
	}
	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}
	@Column(length = 200)
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
	@Enumerated(EnumType.STRING)
	@Column(length = 11, nullable = false)
	public MessageStatus getStatus() {
		return status;
	}
	public void setStatus(MessageStatus status) {
		this.status = status;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}

}
