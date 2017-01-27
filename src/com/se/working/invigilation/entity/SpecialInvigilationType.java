package com.se.working.invigilation.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 特殊类型监考，如四六级、研究生、公务员等考试
 * @author BO
 *
 */
@Entity
public class SpecialInvigilationType {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;
	@OneToMany(mappedBy = "specType")
	@OrderBy(value ="id ASC")
	private Set<SpecialInvigilationInfo> specInv;
	@Temporal(TemporalType.TIMESTAMP )
	@Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date insertTime;
	// 备注
	private String comment;
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.valueOf(this.getId());
	}
	
	public SpecialInvigilationType() {
		// TODO Auto-generated constructor stub
	}

	public SpecialInvigilationType(long id) {
		super();
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<SpecialInvigilationInfo> getSpecInv() {
		return specInv;
	}

	public void setSpecInv(Set<SpecialInvigilationInfo> specInv) {
		this.specInv = specInv;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
