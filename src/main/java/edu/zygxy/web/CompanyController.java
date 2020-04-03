package edu.zygxy.web;

import edu.zygxy.pojo.CompanyLocationVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;

@Controller
public class CompanyController {
    @GetMapping("/company")
    public String company(ModelMap modelMap) {
        CompanyLocationVO cl = new CompanyLocationVO();
        cl.setId("faf");
        cl.setName("分部");
        cl.setLatitude(22.22D);
        cl.setLongitude(11.11D);
        modelMap.addAttribute("companyLocations", Arrays.asList(cl));
        return "company";
    }
}
