package com.ternnetwork.toolkit.dao.impl.dealer;



import org.springframework.stereotype.Repository;
import com.ternnetwork.baseframework.dao.impl.base.IBaseDaoImpl;
import com.ternnetwork.toolkit.dao.dealer.DealerDao;
import com.ternnetwork.toolkit.model.dealer.Dealer;


@Repository("dealerDao")
public class DealerDaoImpl extends IBaseDaoImpl<Dealer> implements  DealerDao{

}
