package com.ternnetwork.toolkit.dao.impl.luckydraw;

import org.springframework.stereotype.Repository;

import com.ternnetwork.baseframework.dao.impl.base.IBaseDaoImpl;
import com.ternnetwork.toolkit.dao.luckydraw.LuckyDrawAwardDao;
import com.ternnetwork.toolkit.model.luckydraw.LuckyDrawAward;




@Repository("luckyDrawAwardDao")
public class LuckyDrawAwardDaoImpl extends IBaseDaoImpl<LuckyDrawAward>
		implements LuckyDrawAwardDao {

}
