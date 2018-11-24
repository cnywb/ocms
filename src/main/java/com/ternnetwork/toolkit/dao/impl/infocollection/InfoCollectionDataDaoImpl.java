package com.ternnetwork.toolkit.dao.impl.infocollection;

import org.springframework.stereotype.Repository;

import com.ternnetwork.baseframework.dao.impl.base.IBaseDaoImpl;
import com.ternnetwork.toolkit.dao.infocollection.InfoCollectionDataDao;
import com.ternnetwork.toolkit.model.infocollection.InfoCollectionData;

@Repository("infoCollectionDataDao")
public class InfoCollectionDataDaoImpl extends IBaseDaoImpl<InfoCollectionData> implements InfoCollectionDataDao {

}
