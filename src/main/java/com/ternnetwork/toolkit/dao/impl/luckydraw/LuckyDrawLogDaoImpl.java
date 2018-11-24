package com.ternnetwork.toolkit.dao.impl.luckydraw;

import org.springframework.stereotype.Repository;

import com.ternnetwork.baseframework.dao.impl.base.IBaseDaoImpl;
import com.ternnetwork.toolkit.dao.luckydraw.LuckyDrawLogDao;
import com.ternnetwork.toolkit.model.luckydraw.LuckyDrawLog;



@Repository("luckyDrawLogDao")
public class LuckyDrawLogDaoImpl extends IBaseDaoImpl<LuckyDrawLog> implements
		LuckyDrawLogDao {

}
