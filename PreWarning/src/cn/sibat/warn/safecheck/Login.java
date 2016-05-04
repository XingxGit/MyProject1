package cn.sibat.warn.safecheck;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.sibat.warn.controller.Xing;
import cn.sibat.warn.model.user.User;


@Controller
@RequestMapping(value="login")
public class Login {
		Logger log = Logger.getLogger("API_FILE");
		@Autowired Auth auth;
		
		//http://localhost:8080/PreWarning/api/login/user?name=admin&password=admin
		@RequestMapping(value="user",produces="application/json;charset=UTF-8") 
		@ResponseBody
		public Xing getInfo(@RequestParam("name") String name,@RequestParam("password") String password,HttpSession session){
			
			log.warn("execution login api");
			User user = auth.findUser(name, password);
			if(user!=null){
			session.setAttribute("userInfo", user);
			user.setPassword("******");
			Xing x = new Xing();
			x.setSuccess(true);
			x.setMsg("ok");
			x.setData(user);
			return x;
			}else{
				Xing x = new Xing();
				x.setSuccess(false);
				x.setMsg("no such user");
				return x;
			}
		}
		
		
}
