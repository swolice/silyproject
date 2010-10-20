package dms.data;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class AccessLog implements Serializable {

    /** identifier field */
    private Long id;

    /** nullable persistent field */
    private Long accessId;

    /** nullable persistent field */
    private String ip;

    /** nullable persistent field */
    private String mac;

    /** nullable persistent field */
    private String systemLanguage;

    /** nullable persistent field */
    private String systemType;

    /** nullable persistent field */
    private String browserLanguage;

    /** nullable persistent field */
    private String browserType;

    /** nullable persistent field */
    private String cpuClass;

    /** nullable persistent field */
    private String color;

    /** nullable persistent field */
    private String resolution;

    /** nullable persistent field */
    private Long accessTime;

    /** nullable persistent field */
    private Date createTime;

    /** full constructor */
    public AccessLog(Long accessId, String ip, String mac, String systemLanguage, String systemType, String browserLanguage, String browserType, String cpuClass, String color, String resolution, Long accessTime, Date createTime) {
        this.accessId = accessId;
        this.ip = ip;
        this.mac = mac;
        this.systemLanguage = systemLanguage;
        this.systemType = systemType;
        this.browserLanguage = browserLanguage;
        this.browserType = browserType;
        this.cpuClass = cpuClass;
        this.color = color;
        this.resolution = resolution;
        this.accessTime = accessTime;
        this.createTime = createTime;
    }

    /** default constructor */
    public AccessLog() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccessId() {
        return this.accessId;
    }

    public void setAccessId(Long accessId) {
        this.accessId = accessId;
    }

    public String getIp() {
        return this.ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMac() {
        return this.mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getSystemLanguage() {
        return this.systemLanguage;
    }

    public void setSystemLanguage(String systemLanguage) {
        this.systemLanguage = systemLanguage;
    }

    public String getSystemType() {
        return this.systemType;
    }

    public void setSystemType(String systemType) {
        this.systemType = systemType;
    }

    public String getBrowserLanguage() {
        return this.browserLanguage;
    }

    public void setBrowserLanguage(String browserLanguage) {
        this.browserLanguage = browserLanguage;
    }

    public String getBrowserType() {
        return this.browserType;
    }

    public void setBrowserType(String browserType) {
        this.browserType = browserType;
    }

    public String getCpuClass() {
        return this.cpuClass;
    }

    public void setCpuClass(String cpuClass) {
        this.cpuClass = cpuClass;
    }

    public String getColor() {
        return this.color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getResolution() {
        return this.resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public Long getAccessTime() {
        return this.accessTime;
    }

    public void setAccessTime(Long accessTime) {
        this.accessTime = accessTime;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .toString();
    }

    public boolean equals(Object other) {
        if ( !(other instanceof AccessLog) ) return false;
        AccessLog castOther = (AccessLog) other;
        return new EqualsBuilder()
            .append(this.getId(), castOther.getId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

}
