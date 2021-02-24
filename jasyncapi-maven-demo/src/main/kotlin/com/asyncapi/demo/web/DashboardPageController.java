package com.asyncapi.demo.web;

import com.asyncapi.demo.domains.services.service.ServicesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardPageController {

    private ServicesService servicesService;

    @GetMapping(value = {"/", "/dashboard-v1"})
    public String dashboardV1(Model model) {
        return "dashboard-v1";
    }

}
