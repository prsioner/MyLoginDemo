package userlogindemo.demo.business.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import userlogindemo.demo.bean.Person;

import java.util.List;

/**
 * 持久层
 * <p/>
 * yutianran 2017/1/19 下午9:04
 */
@Repository
public interface UserRepository extends JpaRepository<Person, Integer> {

    //通过年龄来查询
    List<Person> findByAge(Integer age);

    List<Person> findByName(String name);
}
