package com.ternnetwork.cms.service.impl.carousel;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springside.modules.orm.hibernate.Page;

import com.ternnetwork.baseframework.model.ui.BootstrapGrid;
import com.ternnetwork.baseframework.util.ExtendedStringUtils;
import com.ternnetwork.cms.dao.carousel.CarouselContentDao;
import com.ternnetwork.cms.dao.carousel.CarouselDao;
import com.ternnetwork.cms.model.carousel.Carousel;
import com.ternnetwork.cms.model.carousel.CarouselContent;
import com.ternnetwork.cms.service.carousel.CarouselContentService;


@Service("carouselContentService")
public class CarouselContentServiceImpl implements CarouselContentService {
	
	@Resource
	private CarouselContentDao carouselContentDao;
	
	@Resource
	private CarouselDao carouselDao;
	public long idoAdd(CarouselContent t){
		t.setCarousel(carouselDao.findById(Carousel.class, t.getCarousel().getId()));
		carouselContentDao.persist(t);
		return t.getId();
	}
	
	
	public long idoUpdate(CarouselContent t){
		t.setCarousel(carouselDao.findById(Carousel.class, t.getCarousel().getId()));
		carouselContentDao.saveOrUpdate(t);
		return t.getId();
	}
	
	 public long idoDeleteById(long id){
		 CarouselContent t=carouselContentDao.findById(CarouselContent.class, id);
		 if(t==null){
			 return 0L;
		 }
	     carouselContentDao.delete(t);
		 return 1L;
	 }
	 
	 
	
	  public Page<CarouselContent> query(Page page,String carouselId){
			StringBuffer jpql=new StringBuffer("from  CarouselContent t where 1=1");
			List<Object> params=new ArrayList<Object>();
			if(ExtendedStringUtils.isNotEmpty(carouselId)){
				params.add(Long.parseLong(carouselId));
				jpql.append(" and t.carousel.id=?"+params.size());
			}
			return carouselContentDao.query("select count(t.id) "+jpql.toString(),jpql.toString(), page,params.toArray());
	  }
	  
	  public String queryToJsonStr(Page page,String carouselId){
			Page<CarouselContent> result=query(page, carouselId);
			BootstrapGrid grid=new BootstrapGrid(result);
			return grid.toString();
	  }
	  
	  public List<CarouselContent> findAllByCarouselCode(String carouselCode){
			 return carouselContentDao.findAll("from CarouselContent t where t.carousel.code=?1", carouselCode);
	  }

}
