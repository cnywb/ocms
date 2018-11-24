package com.ternnetwork.baseframework.model.ftp;
import java.util.List;  

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.ftpserver.ftplet.Authority;  
import org.apache.ftpserver.ftplet.AuthorizationRequest;  
import org.apache.ftpserver.ftplet.User;
import org.codehaus.jackson.annotate.JsonAutoDetect;


@Entity
@Table(name="VS_FTP_USER")
@JsonAutoDetect
public class FtpUser  implements User{
	
	@Id@Column(name="userid")
	private String name;
	
	@Column(name="userpassword")
    private String password;
	
	@Column(name="homedirectory") 
    private String homeDirectory;
	
	@Column(name="enableflag") 
	private Boolean enableflag=true;
      
	@Column(name="writepermission") 
	private Boolean writepermission=false;
	
	@Column(name="idletime")
	private Integer idletime=0;
	
	@Column(name="uploadrate")
	private Integer uploadrate=0;
	
	@Column(name="downloadrate")
	private Integer downloadrate=0;
	
	@Column(name="maxloginnumber")
	private Integer maxloginnumber=0;
	
	@Column(name="maxloginperip")
	private Integer maxloginperip=0;
	
    public void setName(String name)  
    {  
        this.name = name;  
    }  
      
    public void setPassword(String password)  
    {  
        this.password = password;  
    }  
      
    public void setHomeDirectory(String homeDirectory)  
    {  
        this.homeDirectory = homeDirectory;  
    }  
      
    public AuthorizationRequest authorize(AuthorizationRequest arg0)  
    {  
        return null;  
    }  
      
    public List<Authority> getAuthorities()  
    {  
        return null;  
    }  
      
    public List<Authority> getAuthorities(Class<? extends Authority> arg0)  
    {  
        return null;  
    }  
      
    public boolean getEnabled()  
    {  
        return true;  
    }  
      
    public String getHomeDirectory()  
    {  
        return homeDirectory;  
    }  
      
    public int getMaxIdleTime()  
    {  
        return 0;  
    }  
      
    public String getName()  
    {  
        return name;  
    }  
      
    public String getPassword()  
    {  
        return password;  
    }  
      
    /** 
     * @return 返回 writepermission 
     */  
    public boolean getWritePermission()  
    {  
        return true;  
    }

	public Boolean getEnableflag() {
		return enableflag;
	}

	public void setEnableflag(Boolean enableflag) {
		this.enableflag = enableflag;
	}

	public Boolean getWritepermission() {
		return writepermission;
	}

	public void setWritepermission(Boolean writepermission) {
		this.writepermission = writepermission;
	}

	public Integer getIdletime() {
		return idletime;
	}

	public void setIdletime(Integer idletime) {
		this.idletime = idletime;
	}

	public Integer getUploadrate() {
		return uploadrate;
	}

	public void setUploadrate(Integer uploadrate) {
		this.uploadrate = uploadrate;
	}

	public Integer getDownloadrate() {
		return downloadrate;
	}

	public void setDownloadrate(Integer downloadrate) {
		this.downloadrate = downloadrate;
	}

	public Integer getMaxloginnumber() {
		return maxloginnumber;
	}

	public void setMaxloginnumber(Integer maxloginnumber) {
		this.maxloginnumber = maxloginnumber;
	}

	public Integer getMaxloginperip() {
		return maxloginperip;
	}

	public void setMaxloginperip(Integer maxloginperip) {
		this.maxloginperip = maxloginperip;
	}  

}
