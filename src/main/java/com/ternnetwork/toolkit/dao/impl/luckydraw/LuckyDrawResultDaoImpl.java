package com.ternnetwork.toolkit.dao.impl.luckydraw;

import org.springframework.stereotype.Repository;

import com.ternnetwork.baseframework.dao.impl.base.IBaseDaoImpl;
import com.ternnetwork.toolkit.dao.luckydraw.LuckyDrawResultDao;
import com.ternnetwork.toolkit.model.luckydraw.LuckyDrawResult;



@Repository("luckyDrawResultDao")
public class LuckyDrawResultDaoImpl extends IBaseDaoImpl<LuckyDrawResult>
		implements LuckyDrawResultDao {

}
