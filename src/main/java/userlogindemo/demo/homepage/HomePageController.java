package userlogindemo.demo.homepage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomePageController {


    @PostMapping("/myHomePages")
    public String myHomePage(){
        return "myHomePage";
    }
}
