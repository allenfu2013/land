package com.datayes.websample.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.datayes.websample.dao.SampleDAO;
import com.datayes.websample.domain.Sample;
import com.datayes.websample.service.SampleService;

@Service("sampleService")
public class SampleServiceImpl implements SampleService {

	@Autowired
	private SampleDAO sampleDAO;

	@Override
	@Transactional
	public void insertSample(Sample sample) {
		sampleDAO.saveOrUpdate(sample);

		if ("test5".equals(sample.getSampleName())) {
			int i = 1 / 0;
		}

		System.out.println("done");
	}

	@Override
	@Transactional
	public void batchInsertSample(Sample... samples) {
		for (Sample sample : samples) {
			insertSample(sample);
		}
	}

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("/applicationContext.xml");
		SampleService sampleService = (SampleService) context.getBean("sampleService");
		Sample sample1 = new Sample();
		sample1.setSampleName("test1");
		sampleService.insertSample(sample1);

		Sample sample3 = new Sample();
		sample3.setSampleName("test3");
		Sample sample4 = new Sample();
		sample4.setSampleName("test4");
		Sample sample5 = new Sample();
		sample5.setSampleName("test5");
		sampleService.batchInsertSample(sample3, sample4, sample5);
	}

}
