package com.spring.resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class FormController {
    public Forms forms;

    @GetMapping(value = "/index")
    public String toIndex(@RequestParam(value = "problem", required = false) String problem,
                          @RequestParam(value = "module", required = false) String module,
                          Model map) {
        //System.out.println(problem);
        //System.out.println(module);
        forms = new Forms(problem, module);
        map.addAttribute("problem", problem);
        map.addAttribute("module", module);
        map.addAttribute("link", new Doing(forms).link());
        return "answer";
    }

    @RequestMapping(value = "/help")
    public String toHelp() {
        return "help";
    }
}
