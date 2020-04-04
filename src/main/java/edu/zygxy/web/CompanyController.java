package edu.zygxy.web;

import edu.zygxy.dao.CompanyLocationMapper;
import edu.zygxy.pojo.JsonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyLocationMapper companyLocationMapper;

    @GetMapping("/company")
    public String company(ModelMap modelMap) {
        modelMap.addAttribute("companyLocations", companyLocationMapper.selectList());
        return "company";
    }

    @ResponseBody
    @DeleteMapping("/api/company/location/{id}")
    public JsonResponse deleteById(@PathVariable Integer id) {
        companyLocationMapper.deleteById(id);
        return new JsonResponse(null);
    }
}
