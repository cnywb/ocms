package com.ternnetwork.cms.dao.impl.carousel;

import org.springframework.stereotype.Repository;

import com.ternnetwork.baseframework.dao.impl.base.IBaseDaoImpl;
import com.ternnetwork.cms.dao.carousel.CarouselContentDao;
import com.ternnetwork.cms.model.carousel.CarouselContent;


@Repository("carouselContentDao")
public class CarouselContentDaoImpl extends IBaseDaoImpl<CarouselContent>
		implements CarouselContentDao {

}
