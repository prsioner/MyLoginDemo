package userlogindemo.demo.login;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;


/**
 * 运行application，在浏览器中输入: http://localhost:8012/login/getUsers
 */


@RestController
@RequestMapping("/login")
public class loginController {


        @RequestMapping("/user")
        public String loginUser(@PathVariable String id){

                String userinfo ;
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id","1");
                jsonObject.put("name","lie");
                jsonObject.put("age",30);
                userinfo = jsonObject.toJSONString();
                return userinfo;



        }
}
