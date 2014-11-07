package com.datayes.websample.dao.impl;

import org.springframework.stereotype.Repository;

import com.datayes.websample.dao.SampleDAO;
import com.datayes.websample.domain.Sample;

@Repository("sampleDAO")
public class SampleDAOImpl extends BaseDAOImpl<Sample> implements SampleDAO {

}
