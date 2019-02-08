package com.qa.springboot.database.brainwavespringboot.model;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "beach")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "creationDate", "lastModified" }, allowGetters = true)
@DynamicInsert(true)
@DynamicUpdate(true)
public class Beach implements Serializable {

	@Id
	@NotNull
	private long id;

	@NotNull
	private String name;

	@NotNull
	private double latitude;

	@NotNull
	private double longtitude;

	public Beach() {
	}
	
	public Beach(String name, double latitude, double longtitude) {
		this.name = name;
		this.latitude = latitude;
		this.longtitude = longtitude;
	}

	public Beach(long id, String name, double latitude, double longtitude) {
		this.id = id;
		this.name = name;
		this.latitude = latitude;
		this.longtitude = longtitude;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongtitude() {
		return longtitude;
	}

	public void setLongtitude(double longtitude) {
		this.longtitude = longtitude;
	}

}
