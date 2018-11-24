package com.ternnetwork.cms.model.content;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.ternnetwork.baseframework.model.security.User;
import com.ternnetwork.baseframework.util.DateUtils;
import com.ternnetwork.baseframework.util.JSONUtils;
import com.ternnetwork.baseframework.util.JsonDateSerializer;
import com.ternnetwork.cms.enums.ContentStatus;
import com.ternnetwork.cms.model.channel.Channel;
import com.ternnetwork.cms.model.page.CmsPage;




@Entity
@JsonAutoDetect
@Table(name="VS_CMS_CONTENT")
@JsonIgnoreProperties(value={"content"})
public class Content implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8922879494912585807L;
	
	@Id@Column(name="ID",nullable=false)
	@GeneratedValue(strategy = GenerationType.TABLE,generator="id_gen")
    @TableGenerator(name = "id_gen",pkColumnValue="CMS_CONTENT_ID",table="VS_ID_GENERATOR", pkColumnName="GEN_NAME",valueColumnName="GEN_VALUE",allocationSize=1)
	private long id;
	
	@Column(nullable=false,name="TITLE")
	private String title="";
	
	@Column(name="TITLE_COLOR")
	private String color="";
	
	@Column(name="SUB_TITLE")
	private String subTitle="";
	
	@Column(name="ICON_IMG_URL")
	private String iconImgUrl="";

	@Column(name="AUTHOR")
	private String author="";
	
	@Column(nullable=false,name="CONTENT")@Lob@Basic(fetch = FetchType.LAZY)
	private byte[] content;
	
	@ManyToOne@JoinColumn(name="CHANNEL_ID")
	private Channel channel;
	
	@ManyToOne@JoinColumn(name="CATEGORY_ID")
	private ContentCategory contentCategory;
	
	@Column(nullable=false,name="COMMENT_ABLE")
	private Boolean commentAble=false;
	
	@Temporal(TemporalType.TIMESTAMP)@Column(name="PUBLISH_TIME")
	private Date publishTime;
	
	@Column(name="SEQ_NUM",nullable=false)
	private Integer seqNum=0;//排序
	
	
	@Temporal(TemporalType.TIMESTAMP)@Column(nullable=false,name="CREATE_TIME")
	private Date createTime=new Date();
	
	@Temporal(TemporalType.TIMESTAMP)@Column(nullable=false,name="UPDATE_TIME")
	private Date updateTime=new Date();
	
	@Column(name="STATUS")
	private ContentStatus status;//0未发布，1已发布，2撤销发布
	
	@ManyToOne@JoinColumn(name="USER_ID",nullable=false)
	private User user;
	
	@Column(name="VIEW_COUNT")
	private long viewCount=0L;
	
	
	@Column(name="CONTENT_FROM")
	private String contentFrom;//来源
	
	@Column(name="TEMPLATE")
	private String template;//页面模版
	
	@Transient
	private Boolean isNew;
	@Transient
	private String statusName;
	@Transient
	private String channelName;
	@Transient
	private String categoryName;
	@Transient
    private String publishTimeStr;
	
	@Transient
	private String url;
	
	@Transient
	private String contentString;
	
	@Transient
	private String tempContentString;
	
	public String getStatusName() {
		
		return status.getName();
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public String getChannelName() {
		return channel.getNameZh();
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public String getCategoryName() {
		return contentCategory.getNameZh();
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	
	public String getSubTitle() {
		return subTitle;
	}
	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}
	public String getIconImgUrl() {
		return iconImgUrl;
	}
	public void setIconImgUrl(String iconImgUrl) {
		this.iconImgUrl = iconImgUrl;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public byte[] getContent() {
		return content;
	}
	public void setContent(byte[] content) {
		this.content = content;
	}
	public Channel getChannel() {
		return channel;
	}
	public void setChannel(Channel channel) {
		this.channel = channel;
	}
	public ContentCategory getContentCategory() {
		return contentCategory;
	}
	public void setContentCategory(ContentCategory contentCategory) {
		this.contentCategory = contentCategory;
	}
	public Boolean getCommentAble() {
		return commentAble;
	}
	public void setCommentAble(Boolean commentAble) {
		this.commentAble = commentAble;
	}
	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}
	public Integer getSeqNum() {
		return seqNum;
	}
	public void setSeqNum(Integer seqNum) {
		this.seqNum = seqNum;
	}
	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	public Boolean getIsNew() {
		return ((new Date()).getTime()-this.getCreateTime().getTime())<=1000L*60*60L*24L;
	}
	public void setIsNew(Boolean isNew) {
		this.isNew = isNew;
	}
	
	public String toString() {
		return JSONUtils.objectToJson(this);
	}

	public long getViewCount() {
		return viewCount;
	}
	public void setViewCount(long viewCount) {
		this.viewCount = viewCount;
	}
	
	public String getContentFrom() {
		return contentFrom;
	}
	public void setContentFrom(String contentFrom) {
		this.contentFrom = contentFrom;
	}
	public String getPublishTimeStr() {
		if(publishTime!=null){
			return DateUtils.format(publishTime, DateUtils.FORMAT_DATE_TIME_DEFAULT);
		}
		
		return publishTimeStr;
	}
	public void setPublishTimeStr(String publishTimeStr) {
		this.publishTimeStr = publishTimeStr;
	}
	public ContentStatus getStatus() {
		return status;
	}
	public void setStatus(ContentStatus status) {
		this.status = status;
	}
	public String getTemplate() {
		return template;
	}
	public void setTemplate(String template) {
		this.template = template;
	}
	public String getUrl() {
		return "content/".concat(contentCategory.getCode()).concat("/").concat(String.valueOf(id)).concat(".htm");
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getContentString() {
		return new String(this.getContent());
	}
	public void setContentString(String contentString) {
		this.contentString = contentString;
	}
	public String getTempContentString() {
		return tempContentString;
	}
	public void setTempContentString(String tempContentString) {
		this.tempContentString = tempContentString;
	}
	public Content(long id, ContentCategory contentCategory) {
		super();
		this.id = id;
		this.contentCategory = contentCategory;
	}
	public Content() {
		super();
	}
	

	
	
	
}
