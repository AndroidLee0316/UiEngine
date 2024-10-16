package com.pasc.lib.workspace.bean;

import com.google.gson.annotations.SerializedName;

public class Announcement{

	@SerializedName("publishTime")
	private String publishTime;

	@SerializedName("versionRange")
	private int versionRange;

	@SerializedName("versionRangeEnd")
	private Object versionRangeEnd;

	@SerializedName("closedAble")
	private int closedAble;

	@SerializedName("id")
	private int id;

	@SerializedName("title")
	private String title;

	@SerializedName("versionRangeStart")
	private Object versionRangeStart;

	@SerializedName("content")
	private String content;

	@SerializedName("skipUrl")
	private String skipUrl;

	public void setPublishTime(String publishTime){
		this.publishTime = publishTime;
	}

	public String getPublishTime(){
		return publishTime;
	}

	public void setVersionRange(int versionRange){
		this.versionRange = versionRange;
	}

	public int getVersionRange(){
		return versionRange;
	}

	public void setVersionRangeEnd(Object versionRangeEnd){
		this.versionRangeEnd = versionRangeEnd;
	}

	public Object getVersionRangeEnd(){
		return versionRangeEnd;
	}

	public void setClosedAble(int closedAble){
		this.closedAble = closedAble;
	}

	public int getClosedAble(){
		return closedAble;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public void setVersionRangeStart(Object versionRangeStart){
		this.versionRangeStart = versionRangeStart;
	}

	public Object getVersionRangeStart(){
		return versionRangeStart;
	}

	public void setContent(String content){
		this.content = content;
	}

	public String getContent(){
		return content;
	}

	public void setSkipUrl(String skipUrl){
		this.skipUrl = skipUrl;
	}

	public String getSkipUrl(){
		return skipUrl;
	}

	@Override
	public String toString() {
		return "Announcement{" +
				"publishTime='" + publishTime + '\'' +
				", versionRange=" + versionRange +
				", versionRangeEnd=" + versionRangeEnd +
				", closedAble=" + closedAble +
				", id=" + id +
				", title='" + title + '\'' +
				", versionRangeStart=" + versionRangeStart +
				", content='" + content + '\'' +
				", skipUrl='" + skipUrl + '\'' +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Announcement that = (Announcement) o;

		if (versionRange != that.versionRange) return false;
		if (closedAble != that.closedAble) return false;
		if (id != that.id) return false;
		if (publishTime != null ? !publishTime.equals(that.publishTime) : that.publishTime != null)
			return false;
		if (versionRangeEnd != null ? !versionRangeEnd.equals(that.versionRangeEnd) : that.versionRangeEnd != null)
			return false;
		if (title != null ? !title.equals(that.title) : that.title != null) return false;
		if (versionRangeStart != null ? !versionRangeStart.equals(that.versionRangeStart) : that.versionRangeStart != null)
			return false;
		if (content != null ? !content.equals(that.content) : that.content != null) return false;
		return skipUrl != null ? skipUrl.equals(that.skipUrl) : that.skipUrl == null;
	}

	@Override
	public int hashCode() {
		int result = publishTime != null ? publishTime.hashCode() : 0;
		result = 31 * result + versionRange;
		result = 31 * result + (versionRangeEnd != null ? versionRangeEnd.hashCode() : 0);
		result = 31 * result + closedAble;
		result = 31 * result + id;
		result = 31 * result + (title != null ? title.hashCode() : 0);
		result = 31 * result + (versionRangeStart != null ? versionRangeStart.hashCode() : 0);
		result = 31 * result + (content != null ? content.hashCode() : 0);
		result = 31 * result + (skipUrl != null ? skipUrl.hashCode() : 0);
		return result;
	}
}