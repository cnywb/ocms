package com.ternnetwork.baseframework.controller.security;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springside.modules.orm.hibernate.Page;
import com.ternnetwork.baseframework.model.security.Role;
import com.ternnetwork.baseframework.model.security.User;
import com.ternnetwork.baseframework.service.security.RoleService;
import com.ternnetwork.baseframework.service.security.UserService;
import com.ternnetwork.baseframework.util.DeviceUtils;
import com.ternnetwork.wechat.service.jsapi.JsapiService;



@Controller
@RequestMapping("/baseframework/security/user/*")
public class UserController {
	
	@Resource
	private UserService userService;

	@Resource
	private RoleService roleService ;
	@Resource
	private JsapiService jsapiService;
	
	@RequestMapping("manage.htm")
	public ModelAndView gotoManage(){
		List<Role> list=roleService.findAll("from Role",null);
		return new ModelAndView("/WEB-INF/view/baseframework/security/user_manage.jsp").addObject("roleList",list);
	}
	
	@RequestMapping("query.htm")
	public void query(PrintWriter out,HttpServletResponse response,String sort,String order,int limit, int offset,User t){
		Page page=new Page();
		if (offset > 0) {
			offset = offset/limit;
		}
		page.setPageNo(offset);
		page.setPageSize(limit);
		try{
			response.setContentType("text/javascript");
		    out.print(userService.queryToJsonStr(t, page));
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	@RequestMapping("querySellerByName.htm")
	public void querySellerByName(PrintWriter out,HttpServletResponse response,int start,int limit,String userName){
		Page page=new Page();
		if (start > 0) {
			start = start / 10;
		}
		page.setPageNo(start);
		page.setPageSize(limit);
		try{
			response.setContentType("text/javascript");
		    out.print(userService.querySellerByNameToJsonStr(userName, page));
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	@RequestMapping("add.htm")
	public void add(PrintWriter out,HttpServletResponse response,User t){
		try{
			response.setContentType("text/javascript");
			out.print(userService.idoAdd(t));
		}catch(Exception e){
			e.printStackTrace();
			out.print("2");
		}
	}
	
	
	@RequestMapping("update.htm")
	public void update(PrintWriter out,HttpServletResponse response,User t){
		try{
			response.setContentType("text/javascript");
			out.print(userService.idoUpdate(t));
		}catch(Exception e){
			e.printStackTrace();
			out.print("2");
		}
	}
	
	
	@RequestMapping("updateInfo.htm")
	public void updateUserInfo(PrintWriter out,HttpServletResponse response,User t){
		try{
			userService.idoUpdateUserInfo(t);
			response.setContentType("text/javascript");
			out.print("1");
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	@RequestMapping("lock.htm")
	public void lock(PrintWriter out,HttpServletResponse response,long id){
		try{
			response.setContentType("text/javascript");
			out.print(userService.idoLockUser(id));
		}catch(Exception e){
			e.printStackTrace();
			out.print("3");
		}
	}
	
	@RequestMapping("unlock.htm")
	public void unlock(PrintWriter out,HttpServletResponse response,long id){
		try{
			userService.idoUnlockUser(id);
			response.setContentType("text/javascript");
			out.print("1");
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	
	@RequestMapping("resetPassword.htm")
	public void resetPassword(PrintWriter out,HttpServletResponse response,long id,String newPassword){
		try{
			userService.idoResetPassword(id, newPassword);
			response.setContentType("text/javascript");
			out.print("1");
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	@RequestMapping("updatePassword.htm")
	public void updatePassword(PrintWriter out,HttpServletResponse response,String orinPassword,String newPassword){
		try{
			response.setContentType("text/javascript");
			out.print(userService.idoUpdatePassword(orinPassword, newPassword));
		}catch(Exception e){
			e.printStackTrace();
			out.print("2");
		}
	}
	
	@RequestMapping("infoUpdate.htm")
	public ModelAndView userInfoUpdate(HttpServletRequest request) throws NoSuchAlgorithmException{
		User user=userService.getCurrentUser();
		ModelAndView mv=new ModelAndView("/WEB-INF/view/baseframework/security/user_info_update.jsp").addObject("user",user);
		if(DeviceUtils.isMobileDevice(request)){
			mv.addObject("jsapiConfig",jsapiService.getQyJsapiConfig(request));
		}
		return mv;
	}
	
	@RequestMapping("passwordUpdate.htm")
	public ModelAndView passwordUpdate(){
		return new ModelAndView("/WEB-INF/view/baseframework/security/password_update.jsp");
	}
	
	
	
	//用户注册
	@RequestMapping("register.htm")
	public void register(PrintWriter out,HttpServletResponse response,String name,String password,Integer gender,String birthday,Integer sexuality){
		try{
			response.setContentType("text/javascript");
			out.print(userService.idoUserRegister(name, password, gender, birthday, sexuality));
		}catch(Exception e){
			e.printStackTrace();
			out.print("exception");
		}
	}
	//按用户名查找用户
	@RequestMapping("queryByName.htm")
	public void queryUserByName(PrintWriter out,HttpServletResponse response,String name){
		try{
			response.setContentType("text/javascript");
			out.print(userService.queryByNameJsonStr(name));
		}catch(Exception e){
			e.printStackTrace();
			out.print("exception");
		}
	}
	
	@RequestMapping("queryUserByLikeName.htm")
	public void queryByLikeName(PrintWriter out,HttpServletResponse response,String name){
		try{
			response.setContentType("text/javascript");
			out.print(userService.queryByLikeNameJsonStr(name));
		}catch(Exception e){
			e.printStackTrace();
			out.print("exception");
		}
	}
	
	@RequestMapping("getAllUserGroupByDeparmentZTree.htm")
	public void getAllGroupByDeparmentZTreeJSON(PrintWriter out,HttpServletResponse response){
		try{
			response.setContentType("text/javascript");
			out.print(userService.getAllGroupByDeparmentZTreeJSON());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@RequestMapping("updateRole.htm")
	public void updateUserRole(PrintWriter out,HttpServletResponse response,Long userId,String roleIds){
		try{
			response.setContentType("text/javascript");
			out.print(userService.idoUpdateUserRole(userId, roleIds));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@RequestMapping("updateFullInfo.htm")
	public void updateFullUserInfo(PrintWriter out,HttpServletResponse response,User t){
		try{
			response.setContentType("text/javascript");
			out.print(userService.idoUpdateFullUserInfo(t));
		}catch(Exception e){
			e.printStackTrace();
			out.print(e.getMessage());
		}
	}
	
	@RequestMapping("findByWechatIdJSONP.htm")
	public void findByWechatId(PrintWriter out,HttpServletResponse response,String jsonpcallback,String wechatId){
		try{
			response.setContentType("text/javascript");//如果不这样设置 chrome浏览器无法调用
			String result=userService.findWechatId(wechatId).toString();
	        out.print(jsonpcallback+"("+result+")");
	  }catch(Exception e){
		e.printStackTrace();
		out.print(jsonpcallback+"(System Exception)");
	   }
	}
	
	@RequestMapping("addOrUpdateCarOwnerUserJSONP.htm")
	public void addOrUpdateCarOwnerUser(PrintWriter out,HttpServletResponse response,String jsonpcallback,String wechatId,String mobilePhoneNo,String realName, String company,String address,String nickname){
		try{
			response.setContentType("text/javascript");//如果不这样设置 chrome浏览器无法调用
			String result=userService.idoAddOrUpdateCarOwnerUser(wechatId, mobilePhoneNo, realName, company,address,nickname)+"";
	        out.print(jsonpcallback+"("+result+")");
	  }catch(Exception e){
		e.printStackTrace();
		out.print(jsonpcallback+"(System Exception)");
	   }
	}

}
