package edu.zygxy.web;

import edu.zygxy.dao.CompanyLocationMapper;
import edu.zygxy.pojo.CompanyLocation;
import edu.zygxy.pojo.JsonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class CompanyLocationController {
    private final CompanyLocationMapper companyLocationMapper;

    @GetMapping("/company_location")
    public String company(ModelMap modelMap) {
        modelMap.addAttribute("companyLocations", companyLocationMapper.selectList());
        return "company_location";
    }

    @GetMapping("company/location/updatePage")
    public String updatePage(@RequestParam int id, ModelMap modelMap) {
        CompanyLocation o = companyLocationMapper.selectById(id);
        modelMap.addAttribute("cl", o);
        return "company_location_update";
    }

    @GetMapping("company/location/insertPage")
    public String insertPage() {
        return "company_location_insert";
    }

    @ResponseBody
    @DeleteMapping("/api/company/location/{id}")
    public JsonResponse deleteById(@PathVariable Integer id) {
        companyLocationMapper.deleteById(id);
        return new JsonResponse(null);
    }

    @ResponseBody
    @PutMapping("/api/company/location")
    public JsonResponse deleteById(@RequestBody CompanyLocation o) {
        companyLocationMapper.insert(o);
        return new JsonResponse(null);
    }

    @ResponseBody
    @PostMapping("/api/company/location/{id}")
    public JsonResponse updateById(@PathVariable Integer id, @RequestBody CompanyLocation o) {
        companyLocationMapper.updateById(id, o);
        return new JsonResponse(null);
    }
}
