package com.stylight.urllookupapp.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "urlmappingdetails")
public class UrlMappingDetails {

	@Id
	@GeneratedValue
	private Long id;
	private String fromUrl;
	private String toUrl;
	
	public UrlMappingDetails() {
		super();
	}

	public UrlMappingDetails(String fromUrl, String toUrl) {
		super();
		this.fromUrl = fromUrl;
		this.toUrl = toUrl;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFromUrl() {
		return fromUrl;
	}

	public void setFromUrl(String fromUrl) {
		this.fromUrl = fromUrl;
	}

	public String getToUrl() {
		return toUrl;
	}

	public void setToUrl(String toUrl) {
		this.toUrl = toUrl;
	}
	
	public void setToUrl(String toUrl, String text) {
		this.toUrl = toUrl.concat(text);
	}
	
	@Override
	public String toString() {
		return "UrlMappingDetails [id=" + id + ", fromUrl=" + fromUrl + ", toUrl=" + toUrl + "]";
	}
}
