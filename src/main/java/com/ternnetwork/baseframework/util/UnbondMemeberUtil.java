package com.ternnetwork.baseframework.util;

import java.util.ArrayList;
import java.util.List;

public class UnbondMemeberUtil {

	public static void main(String[] args) {
	
		
		
		buildSql("LVSFCAME5FF272890,LVSHCADB0DE368007");

	}
	
	
	public static void buildSql(String vins){
		
		String[] vinsArray=vins.split(",");
		
		StringBuilder sb=new StringBuilder();
		Integer i=0;
		for(String vin:vinsArray){
			if(i.intValue()!=0){
				sb.append(",");
			}
				sb.append("'").append(vin).append("'");
			i=i+1;
			
		}
		
		List<String> sqlList=new ArrayList<String>();
		
		sqlList.add("delete from P_POINT_INCOME where user_id in(select user_id from jo_user where vin in ("+sb.toString()+"));");
		sqlList.add("delete from jc_user_ext where user_id in (select user_id from JO_USER where wechat_username=username and user_id in (select user_id from crm.FORD_CLUB_MEMBER where vvin in ("+sb.toString()+")));");
		sqlList.add("delete from jb_user_ext where user_id in(select user_id from JO_USER where wechat_username=username and user_id in (select user_id from crm.FORD_CLUB_MEMBER where vvin in ("+sb.toString()+")));");
		sqlList.add("delete from jc_user where user_id in(select user_id from JO_USER where wechat_username=username and user_id in (select user_id from crm.FORD_CLUB_MEMBER where vvin in ("+sb.toString()+")));");
		sqlList.add("delete from jb_user where user_id in(select user_id from JO_USER where wechat_username=username and user_id in (select user_id from crm.FORD_CLUB_MEMBER where vvin in ("+sb.toString()+")));");
		sqlList.add("delete from JO_USER where wechat_username=username and user_id in (select user_id from crm.FORD_CLUB_MEMBER where vvin in ("+sb.toString()+"));");
		sqlList.add("update crm.jo_user set mobile_phone=null,vin=null,current_point=null,wechat_username=null where wechat_username!=username and user_id in (select user_id from crm.FORD_CLUB_MEMBER where vvin  in ("+sb.toString()+"));");
		sqlList.add("update jc_user set group_id=1 where user_id in (select user_id from crm.FORD_CLUB_MEMBER where vvin in ("+sb.toString()+"));");
		sqlList.add("delete from crm.FORD_CLUB_MEMBER where vvin in ("+sb.toString()+");");
		sqlList.add("insert into crm.ford_member_form(vform_id,vvin,vform_name ,vform_tel,vcreated,dcrt_date) select to_char(crm.SEQ_FORD_MEMBER_FORM.nextval,'FM0000000000'),b.vin,b.owner_name,replace(b.mobile,' ',''),'00-001',sysdate from (select a.vin,a.owner_name,replace(a.mobile,' ','') as mobile from crm.back_temp_owner a where a.mobile is not null and a.vin in("+sb.toString()+") group by a.vin,a.owner_name,replace(a.mobile,' ',''))b;");
		
		sqlList.add("end oracle sql");
		
		sqlList.add("delete from ford_members where vin in("+sb.toString()+");");
		
		for(String sql:sqlList){
			System.out.println(sql);
		}

	}

}
