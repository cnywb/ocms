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
import com.ternnetwork.cms.service.carousel.CarouselService;

@Service("carouselService")
public class CarouselServiceImpl implements CarouselService {
	
	@Resource
	private CarouselDao carouselDao;
	
	@Resource
	private CarouselContentDao carouselContentDao;
	
    public long idoAdd(Carousel t){
    	Long totalCount=carouselDao.getTotalCount("select count(t.id) from Carousel t where t.code=?1",t.getCode());
		if(totalCount.longValue()>0L){
			return 0L;
		}
		carouselDao.persist(t);
		return 1L;
    }
    
    public long idoUpdate(Carousel t){
    	Long totalCount=carouselDao.getTotalCount("select count(t.id) from Carousel t where t.code=?1 and t.id!=?2",t.getCode(),t.getId());
		if(totalCount.longValue()>0L){
			return 0L;
		}
		carouselDao.saveOrUpdate(t);
		return 1L;
    }
    
    public long idoDeleteById(long id){
      	Carousel t=carouselDao.findById(Carousel.class, id);
      	if(t==null){
      		return 0L;
      	}
    	carouselContentDao.bulkUpdate("delete from CarouselContent where carousel.id=?1",id);
     	carouselDao.delete(t);
		return 1L;
    }
    
    public Page<Carousel> query(Page page,String name){
		StringBuffer jpql=new StringBuffer("from  Carousel t where 1=1");
		List<Object> params=new ArrayList<Object>();
		if(ExtendedStringUtils.isNotEmpty(name)){
			params.add(name+"%");
			jpql.append(" and t.name like?"+params.size());
		}
		return carouselDao.query("select count(t.id) "+jpql.toString(),jpql.toString(), page,params.toArray());
	}
    
    public String queryToJsonStr(Page page,String name){
		Page<Carousel> result=query(page, name);
		BootstrapGrid grid=new BootstrapGrid(result);
		return grid.toString();
	}
}
