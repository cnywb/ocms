package com.ternnetwork.cms.service.carousel;



import org.springside.modules.orm.hibernate.Page;

import com.ternnetwork.cms.model.carousel.Carousel;

public interface CarouselService {
	    public long idoAdd(Carousel t);
	    
	    public long idoUpdate(Carousel t);
	    
	    public long idoDeleteById(long id);
	    
	    public Page<Carousel> query(Page page,String name);
	    
	    public String queryToJsonStr(Page page,String name);
}
