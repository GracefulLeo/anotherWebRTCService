package com.gracefull.webRTCService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Properties;

@Controller
public class RedirectController {

    @RequestMapping(value = "/phone/{id}")
    public RedirectView handleTestRequest (Model model, @PathVariable String id) {
        model.addAttribute("attr", "attrVal");
        model.addAttribute("id", "{id}");

        RedirectView rv = new RedirectView();
        rv.setContextRelative(true);
        Properties properties = new Properties();
        properties.setProperty("id", id);
        rv.setAttributes(properties);
        rv.setUrl("/");
        return rv;
    }

    @RequestMapping(value = "/{id}")
    public void handleRequest (
                                 @PathVariable("id") String id,
            @RequestParam (value = "attr",required = false) String attr,
                                 Model model) {
        model.addAttribute("id", id);
    }



}
