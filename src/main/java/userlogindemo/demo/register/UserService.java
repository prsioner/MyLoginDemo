package userlogindemo.demo.register;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 服务层
 * <p/>
 * yutianran 2017/1/19 下午10:02
 */
@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    @Transactional
    public void insertTwo(String a, String b) {
        Person personA = new Person();
        personA.setName(a);
        personA.setAge(21);
        repository.save(personA);

        Person personB = new Person();
        personB.setName(b);
        personB.setAge(22);
        repository.save(personB);
    }
}
