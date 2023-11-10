package org.fubar.rthw;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RThwController {

    @RequestMapping("/")
    public String startPage(){
        return "It's Home Work №5!";
    }


}
