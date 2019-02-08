package com.qa.springboot.database.brainwavespringboot.model;


import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "review")
public class Review {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "beach_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Beach beach;
	
	@NotNull
	private int facilitiesRating;
	
	@NotNull
	private int surfRating;
	
	@NotNull
	private int rockpoolRating;
	
	private String comment;
	
	@Column(nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private Date creationDate;
	
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@LastModifiedDate
	private Date lastModified;
	
	public Review() {
	}
	
	public Review(Beach beach, int facilitiesRating, int surfRating, int rockpoolRating) {
		this.beach = beach;
		this.facilitiesRating = facilitiesRating;
		this.surfRating = surfRating;
		this.rockpoolRating = rockpoolRating;
	}
	
	public Review(Beach beach, int facilitiesRating, int surfRating, int rockpoolRating, String comment) {
		this.beach = beach;
		this.facilitiesRating = facilitiesRating;
		this.surfRating = surfRating;
		this.rockpoolRating = rockpoolRating;
		this.comment = comment;
	}

	public Beach getBeach() {
		return beach;
	}

	public void setBeach(Beach beach) {
		this.beach = beach;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getFacilitiesRating() {
		return facilitiesRating;
	}

	public void setFacilitiesRating(int facilitiesRating) {
		this.facilitiesRating = facilitiesRating;
	}

	public int getSurfRating() {
		return surfRating;
	}

	public void setSurfRating(int surfRating) {
		this.surfRating = surfRating;
	}

	public int getRockpoolRating() {
		return rockpoolRating;
	}

	public void setRockpoolRating(int rockpoolRating) {
		this.rockpoolRating = rockpoolRating;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	

}
