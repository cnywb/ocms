package com.ternnetwork.toolkit.dao.impl.luckydraw;

import org.springframework.stereotype.Repository;

import com.ternnetwork.baseframework.dao.impl.base.IBaseDaoImpl;
import com.ternnetwork.toolkit.dao.luckydraw.LuckyDrawDao;
import com.ternnetwork.toolkit.model.luckydraw.LuckyDraw;



@Repository("luckyDrawDao")
public class LuckyDrawDaoImpl extends IBaseDaoImpl<LuckyDraw> implements
		LuckyDrawDao {

}
