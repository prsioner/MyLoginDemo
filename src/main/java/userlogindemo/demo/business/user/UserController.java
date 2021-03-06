package userlogindemo.demo.business.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import userlogindemo.demo.bean.Person;
import userlogindemo.demo.util.FastJsonUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 控制层、处理用户登录注册、密码找回
 * <p/>
 * yutianran 2017/1/19 下午9:02
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    private Person getPersonFromMap(Map<String,Object> reqMap){
        Person person = new Person();
        String name = reqMap.get("name").toString();
        person.setName(name);

        if(reqMap.get("age") == null){
            person.setAge(null);
        }else {
            person.setAge(reqMap.get("age").toString());
        }

        if(reqMap.get("password") == null){
            person.setPassword(null);
        }else {
            person.setPassword(reqMap.get("password").toString());
        }


        return person;
    }

    @PostMapping("/createUserByMap")
    public String createUserByMap(@RequestBody Map<String,Object> reqMap){

        Person person = getPersonFromMap(reqMap);
        //判断用户是否存在，如果不存在，则插入数据库
        Map<String,Object> returnMap = new HashMap<>();
        returnMap.put("code","0");
        returnMap.put("data","null");
        if(isUserExist(person)){

            returnMap.put("msg","用户已存在!");
            String msg = FastJsonUtil.toJSONString(returnMap,true);
            return msg;
        }else {
            if(StringUtils.isEmpty(person.getName()) || StringUtils.isEmpty(person.getPassword())){

                returnMap.put("msg","参数不能为空!");
                String msg = FastJsonUtil.toJSONString(returnMap,true);
                return msg;
            }else {
                userRepository.save(person);
                returnMap.put("msg","用户注册成功!");
                String msg = FastJsonUtil.toJSONString(returnMap,true);
                return msg;

            }
        }

    }

    @PostMapping("/userLogin")
    public String userLogin(@RequestBody Map<String,Object> reqMap){
        Person person = getPersonFromMap(reqMap);
        System.out.print("userLogin-----reqMap.toString:"+person.toString());

        Map<String,Object> returnMap = new HashMap<>();
        returnMap.put("code","0");
        returnMap.put("data","null");

        if(isUserExist(person)){
            if(StringUtils.isEmpty(person.getName()) || StringUtils.isEmpty(person.getPassword())){

                returnMap.put("msg","参数不能为空!");
                String msg = FastJsonUtil.toJSONString(returnMap,true);
                return msg;

            }else {
                returnMap.put("msg","用户登录成功!");
                String msg = FastJsonUtil.toJSONString(returnMap,true);
                return msg;
            }
        }else {
            returnMap.put("msg","用户不存在，请先注册!");
            String msg = FastJsonUtil.toJSONString(returnMap,true);
            return msg;
        }

    }

    /**
     * 通用的增删改查(JpaRepository自带)
     *
     * @return
     */
    @GetMapping("findAll")
    public List<Person> getAll() {
        System.out.println("获取所有数据");
        return userRepository.findAll();
    }

    @PostMapping("deleteAUser")
    public void delete(@RequestBody Map<String,Object> reqMap) {

        Person person = getPersonFromMap(reqMap);
        userRepository.delete(person);
    }

    @PostMapping("findPassword")
    public String findBackPassword(@RequestBody Map<String,Object> reqMap){
        Person person = getPersonFromMap(reqMap);
        String response;
        if(StringUtils.isEmpty(person.getName())){
            response = "用户名不能为空!";
        }

        List<Person> personList = userRepository.findByName(reqMap.get("name").toString());
        if(personList != null && personList.size()>0){
            response = personList.toString();
        }else {
            response = "查询数据失败!";
        }
        return response;
    }

    public boolean isUserExist(Person person){

        boolean isExit = false;
        String uerName = person.getName();
        if(StringUtils.isEmpty(uerName)) return isExit;

        List<Person> personList = userRepository.findByName(uerName);
        if(personList != null && personList.size()>0){
            isExit =true;
        }else {
            isExit = false;
        }

        return isExit;
    }
}
