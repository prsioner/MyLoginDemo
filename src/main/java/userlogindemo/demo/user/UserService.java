package userlogindemo.demo.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import userlogindemo.demo.bean.Person;

import java.util.Map;

/**
 * 服务层
 * <p/>
 * yutianran 2018/6/19 下午10:02
 */
@Service
public class UserService {
    @Autowired
    private UserRepository repository;


    @Transactional
    public void insertAUser(Map<String,Object> userMap){
        Person person = new Person();
        person.setName(userMap.get("name").toString());
        person.setAge((userMap.get("age").toString()));
        repository.save(person);
    }
}
