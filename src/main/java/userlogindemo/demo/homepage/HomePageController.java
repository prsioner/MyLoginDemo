package userlogindemo.demo.homepage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomePageController {

    /**
     * 返回 resources/templates/myHomePage.html
     * @return
     */
    @RequestMapping(value="/myHomePage")
    public String myHomePage(){
        return "myHomePage";
    }
}
