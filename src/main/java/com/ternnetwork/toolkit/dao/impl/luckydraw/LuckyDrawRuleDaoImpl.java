package com.ternnetwork.toolkit.dao.impl.luckydraw;

import org.springframework.stereotype.Repository;

import com.ternnetwork.baseframework.dao.impl.base.IBaseDaoImpl;
import com.ternnetwork.toolkit.dao.luckydraw.LuckyDrawRuleDao;
import com.ternnetwork.toolkit.model.luckydraw.LuckyDrawRule;



@Repository("luckyDrawRuleDao")
public class LuckyDrawRuleDaoImpl extends IBaseDaoImpl<LuckyDrawRule> implements
		LuckyDrawRuleDao {

}
