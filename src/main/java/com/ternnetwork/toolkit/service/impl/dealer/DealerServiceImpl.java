package com.ternnetwork.toolkit.service.impl.dealer;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.LockModeType;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springside.modules.orm.hibernate.Page;

import com.ternnetwork.baseframework.model.http.BaseResponse;
import com.ternnetwork.baseframework.model.ui.BootstrapGrid;
import com.ternnetwork.baseframework.util.DateUtils;
import com.ternnetwork.toolkit.dao.dealer.DealerDao;
import com.ternnetwork.toolkit.model.dealer.BigArea;
import com.ternnetwork.toolkit.model.dealer.City;
import com.ternnetwork.toolkit.model.dealer.Dealer;
import com.ternnetwork.toolkit.model.dealer.Province;
import com.ternnetwork.toolkit.model.ui.DealerZtree;
import com.ternnetwork.toolkit.service.dealer.BigAreaService;
import com.ternnetwork.toolkit.service.dealer.CityService;
import com.ternnetwork.toolkit.service.dealer.DealerService;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;  

@Service("dealerService")
public class DealerServiceImpl implements DealerService {
	
	private static final Logger log = Logger.getLogger(DealerServiceImpl.class);
	
	@Resource
	private DealerDao dealerDao;
	
	@Resource
	private BigAreaService bigAreaService;
	
	@Resource
	private CityService cityService;
	
	
	
	

	public BaseResponse idoSaveFromFile(InputStream in){
		List<Dealer> list=parseFromFile(in);
		BaseResponse res=new BaseResponse();
		if(list==null){
			res.setStatus(0);
			res.setMessage("操作失败，解析文件错误！");
			return res;
		}
		doMergeBatch(list);
		res.setStatus(1);
		res.setMessage("操作成功！");
		return res;
	}
	
	
	public void  doMergeBatch(List<Dealer> list){
	   log.info("Merge经销商开始");
	   List<Dealer> oldList=dealerDao.findAll("from Dealer t", null);
	   List<City> cityList=cityService.findAll();
       for(Dealer t:list){
      	   replaceProperties(t);
      	   City city=cityService.findByTitle(cityList,t.getCityOrTerritory());
    	   if(city==null){
    		   city=cityService.doAdd(t.getCityOrTerritory(), t.getProvince());
    	   }
    	   t.setCity(city);
    	   doMerge(t,oldList);
       }
       log.info("Merge经销商结束");
	}
	
	
	public BaseResponse idoUpdateLocationAndName(Dealer t){
		Dealer dealer=dealerDao.findById(Dealer.class, t.getId());
		BaseResponse res=new BaseResponse();
		if(dealer==null){
			res.setStatus(0);
			res.setMessage("操作失败，对象不存在！");
			return res;
		}
		copyUpdateLocationAndName(t,dealer);
		dealer.setAddressUpdated(false);
		dealerDao.saveOrUpdate(dealer);
		res.setStatus(1);
		res.setMessage("操作成功！");
		return res;
	}
	
	
	
	
	
	
	
	public void doMerge(Dealer t,List<Dealer> list){
		Dealer dealer=findByDealerCode(list,t.getDealerCode());
		if(dealer==null){
			t.setAddressUpdated(true);
			dealerDao.persist(t);
		}else{
			String fordBrandShopAddress=dealer.getFordBrandShopAddress();
			Long id=dealer.getId();
			copyProperties(t, dealer);
			if(!t.getFordBrandShopAddress().equals(fordBrandShopAddress)){
				dealer.setAddressUpdated(true);
			}else{
				dealer.setAddressUpdated(false);
			}
			dealer.setId(id);
			dealerDao.update(dealer);
		}
		
	}
	
	private void copyUpdateLocationAndName(Dealer from,Dealer to){
		to.setCityOrTerritory(from.getCityOrTerritory());
		to.setFordBrandShopAddress(from.getFordBrandShopAddress());
		to.setLatitude(from.getLatitude());
		to.setLongitude(from.getLongitude());
	}
	
	private void copyProperties(Dealer from,Dealer to){
		to.setAddressUpdated(from.getAddressUpdated());
		City city=cityService.findById(from.getCity().getId());
		to.setCity(city);
		to.setCityOrTerritory(city.getTitle());
		to.setDealerCode(from.getDealerCode());
		to.setDealerName(from.getDealerName());
		to.setFordBrandShopAddress(from.getFordBrandShopAddress());
		to.setPostcode(from.getPostcode());
		to.setAreaCode(from.getAreaCode());
		to.setSalesHotline(from.getSalesHotline());
		to.setServiceTel(from.getServiceTel());
		to.setFax(from.getFax());
		to.setEmailAddress(from.getEmailAddress());
		to.setDealerServiceCode(from.getDealerServiceCode());
		if(from.getLatitude()!=null){
			to.setLatitude(from.getLatitude());
		}
		if(from.getLongitude()!=null){
			to.setLongitude(from.getLongitude());
		}
	}
	
	private void replaceProperties(Dealer t){
		t.setCityOrTerritory(replaceEnglishCharAndPunctuation(t.getCityOrTerritory()));
		t.setDealerName(replaceEnglishCharAndPunctuation(t.getDealerName()));
		t.setFordBrandShopAddress(replaceEnglishCharAndPunctuation(t.getFordBrandShopAddress()));
	}
	
	/**
	 * 替换英文,空格,标点符号
	 * @param str
	 * @return
	 */
	private String replaceEnglishCharAndPunctuation(String str){
		if(str==null){
			return "";
		}
		return str.replaceAll("[a-zA-Z]","").replace(" ", "").replaceAll("\\p{Punct}", "").replaceAll("\\pP", "");
	}
	
	public Dealer findByDealerCode(String dealerCode){
		List<Dealer> list=dealerDao.findAll("from Dealer t where t.dealerCode=?1", dealerCode);
		if(list.size()==0){
			return null;
		}
		return list.get(0);
	}
	
	
	public Dealer findByDealerServiceCode(String dealerServiceCode){
		List<Dealer> list=dealerDao.findAll("from Dealer t where t.dealerServiceCode=?1", dealerServiceCode);
		if(list.size()==0){
			return null;
		}
		return list.get(0);
	}
	
	public Dealer findByDealerCode(List<Dealer> list,String dealerCode){
		for(Dealer t:list){
			if(dealerCode.toLowerCase().equals(t.getDealerCode().toLowerCase())){
				return t;
			}
		}
		return null;
	}
	
	public BootstrapGrid queryToBootstrapGrid(String dealerCode,String dealerName,Boolean  addressUpdated,Page page){
		Page<Dealer> result=query(dealerCode,dealerName,addressUpdated,page);
		BootstrapGrid grid=new BootstrapGrid(result);
		return grid;
	}
    
    public Page<Dealer> query(String dealerCode,String dealerName,Boolean  addressUpdated,Page page){
	    StringBuffer jpql=new StringBuffer("from Dealer t where 1=1");
		List<Object> params=new ArrayList<Object>();
		if(StringUtils.isNotEmpty(dealerCode)){
			params.add(dealerCode);
			jpql.append(" and t.dealerCode=?"+params.size());
		}
		
		if(StringUtils.isNotEmpty(dealerName)){
			params.add(dealerName+"%");
			jpql.append(" and t.dealerName like?"+params.size());
		}
		if(addressUpdated!=null){
			params.add(addressUpdated);
			jpql.append(" and t.addressUpdated=?"+params.size());
		}
		
		jpql.append(" order by t.id desc");
		return dealerDao.query("select count(t.id) "+jpql.toString(),jpql.toString(), page, params.toArray());
	}
    
    
    private List<Dealer> parseFromFile(InputStream in){
    	log.info("解析上传经销商excel文件开始");
		List<Dealer>  retVal=new ArrayList<Dealer>();
		try {
		     XSSFWorkbook book = new XSSFWorkbook(in); 
			int length=book.getNumberOfSheets();
			for(int i=0;i<length;i++){//遍历sheet
				Sheet sheet = book.getSheetAt(i);
				parseFromSheet(retVal, sheet);
			}
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		log.info("解析上传经销商excel文件结束");
		return retVal;
    }
    
      private List<Dealer> parseFromSheet(List<Dealer> list,Sheet sheet){
    	    
            int lastRowNum = sheet.getLastRowNum();
    	    Row row = null;
    	    for (int j = 2; j < lastRowNum; j++) {// 遍历行,从第三行开始
    	    	row = sheet.getRow(j);
    	    	Dealer t=new Dealer();
    	    	int firstCellNum = row.getFirstCellNum();
				int lastCellNum = row.getLastCellNum();
				for (int k = firstCellNum; k < lastCellNum; k++) {
					Cell cell = row.getCell(k);
					String cellContent= getStringCellValue(cell).trim();
					
					if(k==0){//大区
						t.setBigArea(cellContent);
					}else if(k==1){
						t.setAreaCode(cellContent);//区号
						
					}else if(k==2){
						t.setDealerCode(cellContent);
						
					}else if(k==3){//省
						t.setProvince(cellContent);
					}else if(k==4){//市
						
						t.setCityOrTerritory(cellContent);
					
					}else if(k==5){
						t.setDealerName(cellContent);
					
					}else if(k==6){
						t.setFordBrandShopAddress(cellContent);
						
					}else if(k==7){
						t.setPostcode(cellContent);
						
					}else if(k==8){//销售热线
						t.setSalesHotline(cellContent);
					}else if(k==9){//服务电话
						t.setServiceTel(cellContent);
					}else if(k==10){//传真
						t.setFax(cellContent);
					}else if(k==11){//电话
						t.setEmailAddress(cellContent);
					}
				}
				if(t.getDealerCode()!=null){
					list.add(t);
				}
				
			}
    	  
    	  return list;
      }
      
      private   String getStringCellValue(Cell cell){  
          if(cell==null){  
              return "";  
          }  
          switch(cell.getCellType()){  
          case Cell.CELL_TYPE_BOOLEAN:  
              return String.valueOf(cell.getBooleanCellValue());
          case Cell.CELL_TYPE_NUMERIC:  
              if(DateUtil.isCellDateFormatted(cell)){  
                 return DateUtils.format(cell.getDateCellValue(),DateUtils.FORMAT_DATE_TIME_DEFAULT);
              }else{  
            	  DecimalFormat dfs = new DecimalFormat("0");
                  return dfs.format(cell.getNumericCellValue());  
              }  
            
          case Cell.CELL_TYPE_FORMULA:  
              return cell.getCellFormula();
          case Cell.CELL_TYPE_STRING:  
              return cell.getStringCellValue();               
       }
		return "";  
            
      }  
      
      
      public List<DealerZtree>getZtreeList(){
    	  List<DealerZtree> retVal=new ArrayList<DealerZtree>();
    	  List<BigArea> bigAreaList=bigAreaService.findAll();
    	  getBigAreaZtreeList(retVal, bigAreaList);
    	  return retVal;
      }


	private void getBigAreaZtreeList(List<DealerZtree> retVal, List<BigArea> bigAreaList) {
		for(BigArea bigArea:bigAreaList){
    		  DealerZtree bigAreaTree=new DealerZtree();
    		  String aid="A_"+bigArea.getId();
    		  bigAreaTree.setId(aid);
    		  bigAreaTree.setName(bigArea.getTitle()+"("+bigArea.getTotal()+")");
    		  bigAreaTree.setIsParent(true);
    		  bigAreaTree.setLatitude(bigArea.getLatitude());
    		  bigAreaTree.setLongitude(bigArea.getLongitude());
    		  bigAreaTree.setpId("0");
    		  bigAreaTree.setTitle(bigArea.getTitle());
    		  bigAreaTree.setRealId(bigArea.getId());
    		  retVal.add(bigAreaTree);
    		  getProvinceZtreeList(aid,retVal, bigArea.getList());
    	  }
	}


	private void getProvinceZtreeList(String aid,List<DealerZtree> retVal, List<Province> provinceList) {
		for(Province province:provinceList){
			  DealerZtree provinceTree=new DealerZtree();
			  String pid="P_"+province.getId();
			  provinceTree.setId(pid);
			  provinceTree.setName(province.getTitle()+"("+province.getTotal()+")");
			  provinceTree.setLatitude(province.getLatitude());
			  provinceTree.setLongitude(province.getLongitude());
			  provinceTree.setpId(aid);
			  provinceTree.setTitle(province.getTitle());
			  provinceTree.setRealId(province.getId());
			  provinceTree.setIsParent(true);
			  provinceTree.setBigAreaId(String.valueOf(province.getBigArea().getId()));
			  retVal.add(provinceTree);
			  getCityZtreeList(pid,retVal, province.getList());
			  
		  }
	}


	private void getCityZtreeList(String pid,List<DealerZtree> retVal, List<City> cityList) {
		for(City city:cityList){
			  DealerZtree cityTree=new DealerZtree();
			  String cid="C_"+city.getId();
			  cityTree.setId(cid);
			  cityTree.setName(city.getTitle()+"("+city.getTotal()+")");
			  cityTree.setLatitude(city.getLatitude());
			  cityTree.setLongitude(city.getLongitude());
			  cityTree.setpId(pid);
			  cityTree.setTitle(city.getTitle());
			  cityTree.setRealId(city.getId());
			  cityTree.setIsParent(true);
			  cityTree.setProvinceId(String.valueOf(city.getProvince().getId()));
			  retVal.add(cityTree);
			  getDealerZtreeList(cid,retVal,city.getList());
		  }
	}


	private void getDealerZtreeList(String cid,List<DealerZtree> retVal, List<Dealer> dealerList) {
		for(Dealer dealer:dealerList){
			  DealerZtree dealerTree=new DealerZtree();
			  BeanUtils.copyProperties(dealer, dealerTree);
			  String did="D_"+dealer.getId();
			  dealerTree.setId(did);
			  dealerTree.setName(dealer.getDealerName());
			  dealerTree.setpId(cid);
			  dealerTree.setAreaCode(dealer.getAreaCode());
			  dealerTree.setRealId(dealer.getId());
			  dealerTree.setCityId(String.valueOf(dealer.getCity().getId()));
			  dealerTree.setDealerServiceCode(dealer.getDealerServiceCode());
			  retVal.add(dealerTree);
		  }
	}
	
	public BaseResponse idoAdd(Dealer t){
		BaseResponse res=new BaseResponse();
		if(StringUtils.isEmpty(t.getDealerCode())){
			res.setStatus(0);
			res.setMessage("操作失败，经销商代码不能为空！");
			return res;
		}
		if(StringUtils.isEmpty(t.getDealerName())){
			res.setStatus(0);
			res.setMessage("操作失败，经销商名称不能为空！");
			return res;
		}
		long totalCount=dealerDao.getTotalCount("select count(id) from Dealer where dealerCode=?1", t.getDealerCode());
		if(totalCount>0L){
			res.setStatus(0);
			res.setMessage("操作失败，经销商代码已经存在，请用其它名称！");
			return res;
		}
		City city=cityService.findById(t.getCity().getId());
		t.setCity(city);
		t.setCityOrTerritory(city.getTitle());
		t.setAddressUpdated(false);
		dealerDao.persist(t);
		res.setStatus(1);
		res.setMessage("操作成功！");
		return res;
	}
	
	public BaseResponse idoUpdate(Dealer t){
		BaseResponse res=new BaseResponse();
		Dealer old=dealerDao.findById(Dealer.class,t.getId(),LockModeType.PESSIMISTIC_WRITE);
		if(old==null){
			res.setStatus(0);
			res.setMessage("操作失败，对象不存在！");
			return res;
		}
		if(StringUtils.isEmpty(t.getDealerCode())){
			res.setStatus(0);
			res.setMessage("操作失败，经销商代码不能为空！");
			return res;
		}
		if(StringUtils.isEmpty(t.getDealerName())){
			res.setStatus(0);
			res.setMessage("操作失败，经销商名称不能为空！");
			return res;
		}
		long totalCount=dealerDao.getTotalCount("select count(id) from Dealer where dealerCode=?1 and id!=?2",t.getDealerCode(),t.getId());
		if(totalCount>0L){
			res.setStatus(0);
			res.setMessage("操作失败，经销商代码已经存在，请用其它名称！");
			return res;
		}
		copyProperties(t,old);
		old.setAddressUpdated(false);
		dealerDao.saveOrUpdate(old);
		res.setStatus(1);
		res.setMessage("操作成功！");
		return res;
	}
	
	
	public BaseResponse idoDelete(long id){
		BaseResponse res=new BaseResponse();
		Dealer t=dealerDao.findById(Dealer.class, id,LockModeType.PESSIMISTIC_WRITE);
		if(t==null){
			res.setStatus(0);
			res.setMessage("操作失败，对象不存在！");
			return res;
		}
		t.getCity().getList().clear();
    	dealerDao.delete(t);
		res.setStatus(1);
		res.setMessage("操作成功！");
		return res;
	}

}
