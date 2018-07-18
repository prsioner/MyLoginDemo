package userlogindemo.demo.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import userlogindemo.demo.bean.Person;

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
        if(isUserExist(person)){
            return "用户已存在!";
        }else {
            if(StringUtils.isEmpty(person.getName()) || StringUtils.isEmpty(person.getPassword())){
                return  "参数不能为空!";
            }else {
                userRepository.save(person);
                return "用户注册成功!";
            }
        }

    }

    @PostMapping("/userLogin")
    public String userLogin(@RequestBody Map<String,Object> reqMap){
        Person person = getPersonFromMap(reqMap);
        if(isUserExist(person)){
            if(StringUtils.isEmpty(person.getName()) || StringUtils.isEmpty(person.getPassword())){
                return  "参数不能为空!";
            }else {
                return "用户登录成功!";
            }
        }else {
            return "用户不存在，请先注册!";
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

    /**
     * 自定义的操作
     *
     * @param age
     * @return
     */
    @PostMapping("findByAge")
    public List<Person> findByAge(@RequestParam("age") Integer age) {
        return userRepository.findByAge(age);
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
