package com.datayes.websample.service;

import com.datayes.websample.domain.Sample;

public interface SampleService {
	void insertSample(Sample sample);
	
	void batchInsertSample(Sample... samples);
}
