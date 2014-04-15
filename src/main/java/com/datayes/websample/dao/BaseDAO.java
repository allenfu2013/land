package com.datayes.websample.dao;

public interface BaseDAO<T> {
	public void saveOrUpdate(T t);
}
