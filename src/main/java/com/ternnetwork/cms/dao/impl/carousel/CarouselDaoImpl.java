package com.ternnetwork.cms.dao.impl.carousel;

import org.springframework.stereotype.Repository;

import com.ternnetwork.baseframework.dao.impl.base.IBaseDaoImpl;
import com.ternnetwork.cms.dao.carousel.CarouselDao;
import com.ternnetwork.cms.model.carousel.Carousel;

@Repository("carouselDao")
public class CarouselDaoImpl extends IBaseDaoImpl<Carousel> implements
		CarouselDao {

}
