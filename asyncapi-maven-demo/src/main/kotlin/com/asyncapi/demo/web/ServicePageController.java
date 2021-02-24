package com.asyncapi.demo.web;

import com.asyncapi.demo.domains.services.service.ServicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ServicePageController {

    @Autowired
    private ServicesService servicesService;

    @GetMapping("/service/{serviceName}")
    public String dashboardV1(@PathVariable String serviceName, Model model) {
        var service = servicesService.get(serviceName);
        model.addAttribute("service", service);

        return "service";
    }

}
