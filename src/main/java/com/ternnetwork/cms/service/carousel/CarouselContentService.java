package com.ternnetwork.cms.service.carousel;


import java.util.List;

import org.springside.modules.orm.hibernate.Page;

import com.ternnetwork.cms.model.carousel.CarouselContent;

public interface CarouselContentService {
	public long idoAdd(CarouselContent t);
	
	public long idoUpdate(CarouselContent t);
	
    public long idoDeleteById(long id);
	 
   	public Page<CarouselContent> query(Page page,String carouselId);
	  
	public String queryToJsonStr(Page page,String carouselId);
	
	public List<CarouselContent> findAllByCarouselCode(String carouselCode);

}
