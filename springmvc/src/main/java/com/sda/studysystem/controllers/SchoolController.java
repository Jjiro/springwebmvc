package com.sda.studysystem.controllers;

import com.sda.studysystem.models.City;
import com.sda.studysystem.models.School;
import com.sda.studysystem.models.Country;
import com.sda.studysystem.models.County;
import com.sda.studysystem.services.CityService;
import com.sda.studysystem.services.SchoolService;
import com.sda.studysystem.services.CountryService;
import com.sda.studysystem.services.CountyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/school")
public class SchoolController {

    @Autowired
    private SchoolService schoolService;
    
    @Autowired
    private CountryService countryService;

    @Autowired
    private CountyService countyService;

    @Autowired
    private CityService cityService;

    @GetMapping("")
    public String showAllSchools(@ModelAttribute("messageType") String messageType, @ModelAttribute("message") String message,
                                Model model) {
        List<School> schools = schoolService.getAllSchools();
        model.addAttribute("schools", schools);
        return "school/school-list";
    }

    @GetMapping("/add")
    public String addSchoolForm(@ModelAttribute("school") School school, @ModelAttribute("messageType") String messageType,
                              @ModelAttribute("message") String message, Model model) {
        List<Country> countries = countryService.getAllCountries().stream()
                .filter(Country::isActive).collect(Collectors.toList());
        model.addAttribute("countries", countries);

        List<County> counties = countyService.getAllCounties().stream()
                .filter(County::isActive).collect(Collectors.toList());
        model.addAttribute("counties", counties);

        List<City> cities = cityService.getAllCities().stream()
                .filter(City::isActive).collect(Collectors.toList());
        model.addAttribute("cities", cities);

        return "school/school-add";
    }

    @PostMapping("/add")
    public String addSchool(@Valid School school, RedirectAttributes redirectAttributes) {
        boolean createResult = schoolService.createSchool(school);

        if (createResult) {
            redirectAttributes.addFlashAttribute("message", "School has been successfully created.");
            redirectAttributes.addFlashAttribute("messageType", "success");
            return "redirect:/school/";
        } else {
            redirectAttributes.addFlashAttribute("school", school);
            redirectAttributes.addFlashAttribute("message", "Error in creating a school!");
            redirectAttributes.addFlashAttribute("messageType", "error");
            return "redirect:/school/add";
        }
    }

    @GetMapping("/update/{id}")
    public String updateSchoolForm(@PathVariable("id") Long schoolId, @RequestParam(value = "school", required = false) School school,
                                 @ModelAttribute("messageType") String messageType,
                                 @ModelAttribute("message") String message, Model model) {
        if (school == null) {
            model.addAttribute("school", schoolService.getById(schoolId));
        }

        List<Country> countries = countryService.getAllCountries().stream()
                .filter(Country::isActive).collect(Collectors.toList());
        model.addAttribute("countries", countries);

        List<County> counties = countyService.getAllCounties().stream()
                .filter(County::isActive).collect(Collectors.toList());
        model.addAttribute("counties", counties);

        return "school/school-update";
    }

    @PostMapping("/update/{id}")
    public String updateSchool(@PathVariable("id") Long schoolId, @Valid School school, RedirectAttributes redirectAttributes) {
        school.setId(schoolId);
        boolean updateResult = schoolService.updateSchool(school);

        if (updateResult) {
            redirectAttributes.addFlashAttribute("message", "School #" + schoolId + " has been successfully updated.");
            redirectAttributes.addFlashAttribute("messageType", "success");
            return "redirect:/school/";
        } else {
            redirectAttributes.addAttribute("id", schoolId);
            redirectAttributes.addAttribute("school", school);
            redirectAttributes.addFlashAttribute("message", "Error in updating a school #" + schoolId + "!");
            redirectAttributes.addFlashAttribute("messageType", "error");
            return "redirect:/school/update/{id}";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteSchool(@PathVariable("id") Long schoolId, RedirectAttributes redirectAttributes) {
        boolean deleteResult = schoolService.deleteSchoolById(schoolId);

        if (deleteResult) {
            redirectAttributes.addFlashAttribute("message", "School #" + schoolId + " has been successfully deleted.");
            redirectAttributes.addFlashAttribute("messageType", "success");
        } else {
            redirectAttributes.addFlashAttribute("message", "Error in deleting a school #" + schoolId + "!");
            redirectAttributes.addFlashAttribute("messageType", "error");
        }

        return "redirect:/school/";
    }

    @GetMapping("/restore/{id}")
    public String restoreSchool(@PathVariable("id") Long schoolId, RedirectAttributes redirectAttributes) {
        boolean restoreResult = schoolService.restoreSchoolById(schoolId);

        if (restoreResult) {
            redirectAttributes.addFlashAttribute("message", "School #" + schoolId + "has been successfully restored.");
            redirectAttributes.addFlashAttribute("messageType", "success");
        } else {
            redirectAttributes.addFlashAttribute("message", "Error in restoring a school #" + schoolId + "!");
            redirectAttributes.addFlashAttribute("messageType", "error");
        }

        return "redirect:/school/";
    }

}


