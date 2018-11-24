package com.ternnetwork.toolkit.dao.impl.dealer;

import org.springframework.stereotype.Repository;

import com.ternnetwork.baseframework.dao.impl.base.IBaseDaoImpl;
import com.ternnetwork.toolkit.dao.dealer.ProvinceDao;
import com.ternnetwork.toolkit.model.dealer.Province;


@Repository("provinceDao")
public class ProvinceDaoImpl extends IBaseDaoImpl<Province> implements ProvinceDao {

}
