package com.spring.resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.LinkedList;


@Controller
public class WebController {
    public Forms forms;

    @GetMapping(value = "/index")
    public String toIndex(@RequestParam(value = "problem", required = false) String problem,
                          @RequestParam(value = "module", required = false) String module,
                          ModelMap map) {
        //System.out.println(problem);
        //System.out.println(module);
        forms = new Forms(problem, module);
        map.addAttribute("problem", problem);
        map.addAttribute("module", module);
        LinkedList<LinkedList<String>> answerList = (new Doing(forms)).link();
        map.addAttribute("answer_list", answerList);
        return "answer";
    }

    @RequestMapping(value = "/help")
    public String toHelp() {
        return "help";
    }
}
