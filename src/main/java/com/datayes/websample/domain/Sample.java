package com.datayes.websample.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SAMPLE")
public class Sample {

	@Id
	@GeneratedValue
	@Column(name = "SAMPLE_ID", unique = true)
	private Long sampleId;
	
	@Column(name = "SAMPLE_NAME")
	private String sampleName;
	
	public Sample(){}
	
	public Long getSampleId() {
		return sampleId;
	}

	public void setSampleId(Long sampleId) {
		this.sampleId = sampleId;
	}

	public String getSampleName() {
		return sampleName;
	}

	public void setSampleName(String sampleName) {
		this.sampleName = sampleName;
	}
	
}
