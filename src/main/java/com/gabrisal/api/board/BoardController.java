package com.gabrisal.api.board;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BoardController {

    @GetMapping("/test")
    public String restTest(@RequestParam String str) {
        return str + " : Rest Test 완료 !!";
    }
}
