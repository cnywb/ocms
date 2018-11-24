package com.ternnetwork.toolkit.dao.impl.dealer;

import org.springframework.stereotype.Repository;

import com.ternnetwork.baseframework.dao.impl.base.IBaseDaoImpl;
import com.ternnetwork.toolkit.dao.dealer.CityDao;
import com.ternnetwork.toolkit.model.dealer.City;

@Repository("cityDao")
public class CityDaoImpl extends IBaseDaoImpl<City> implements CityDao {

}
