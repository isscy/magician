package cn.redsoft.magician.admin.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("miniTester")
public class TestController {


    @RequestMapping("test1")
    public  Object test(){
        return "ok ey";
    }
}
