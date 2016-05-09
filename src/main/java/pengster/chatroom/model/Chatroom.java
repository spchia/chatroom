package pengster.chatroom.model;

import java.util.Date;

public class Chatroom {
	private String id;
	private String displayName;
	private String description;
	private int userCount;
	private String createdBy;
	private Date dateCreated;
	
	public Chatroom(String id, String displayName, String description, String createdBy, Date dateCreated){
		this.id = id;
		this.displayName = displayName;
		this.description = description;
		this.createdBy = createdBy;
		this.dateCreated = dateCreated;
	}
	
	public Chatroom(String displayName, String description, String createdBy){
		this(String.valueOf(System.currentTimeMillis()), displayName, description, createdBy, new Date());
	}
	
	public Chatroom(){
		this(null, null, null);
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getDisplayName() {
		return displayName;
	}
	
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public int getUserCount() {
		return userCount;
	}
	
	public void setUserCount(int userCount) {
		this.userCount = userCount;
	}
	
	public String getCreatedBy() {
		return createdBy;
	}
	
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	public Date getDateCreated() {
		return dateCreated;
	}
	
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	
}
