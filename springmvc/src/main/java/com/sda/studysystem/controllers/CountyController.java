package com.sda.studysystem.controllers;

import com.sda.studysystem.models.Country;
import com.sda.studysystem.models.County;
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
@RequestMapping("/county")
public class CountyController {

    @Autowired
    private CountyService countyService;

    @Autowired
    private CountryService countryService;

    @GetMapping("")
    public String showAllCounties(@ModelAttribute("messageType") String messageType, @ModelAttribute("message") String message,
                                  Model model) {
        List<County> counties = countyService.getAllCounties();
        model.addAttribute("counties", counties);
        return "county/county-list";
    }

    @GetMapping("/add")
    public String addCountyForm(@ModelAttribute("county") County county, @ModelAttribute("messageType") String messageType,
                                @ModelAttribute("message") String message, Model model) {
        List<Country> countries = countryService.getAllCountries().stream()
                .filter(Country::isActive).collect(Collectors.toList());
        model.addAttribute("countries", countries);
        return "county/county-add";
    }

    @PostMapping("/add")
    public String addCounty(@Valid County county, RedirectAttributes redirectAttributes) {
        boolean createResult = countyService.createCounty(county);

        if (createResult) {
            redirectAttributes.addFlashAttribute("message", "County has been successfully created.");
            redirectAttributes.addFlashAttribute("messageType", "success");
            return "redirect:/county/";
        } else {
            redirectAttributes.addFlashAttribute("county", county);
            redirectAttributes.addFlashAttribute("message", "Error in creating a county!");
            redirectAttributes.addFlashAttribute("messageType", "error");
            return "redirect:/county/add";
        }
    }

    @GetMapping("/update/{id}")
    public String updateCountyForm(@PathVariable("id") Long countyId, @RequestParam(value = "county", required = false) County county,
                                   @ModelAttribute("messageType") String messageType,
                                   @ModelAttribute("message") String message, Model model) {
        if (county == null) {
            model.addAttribute("county", countyService.getById(countyId));
        }

        List<Country> countries = countryService.getAllCountries().stream()
                .filter(Country::isActive).collect(Collectors.toList());
        model.addAttribute("countries", countries);
        return "county/county-update";
    }

    @PostMapping("/update/{id}")
    public String updateCounty(@PathVariable("id") Long countyId, @Valid County county, RedirectAttributes redirectAttributes) {
        county.setId(countyId);
        boolean updateResult = countyService.updateCounty(county);

        if (updateResult) {
            redirectAttributes.addFlashAttribute("message", "County #" + countyId + "has been successfully updated.");
            redirectAttributes.addFlashAttribute("messageType", "success");
            return "redirect:/county/";
        } else {
            redirectAttributes.addAttribute("id", countyId);
            redirectAttributes.addAttribute("county", county);
            redirectAttributes.addFlashAttribute("message", "Error in updating a county #" + countyId + "!");
            redirectAttributes.addFlashAttribute("messageType", "error");
            return "redirect:/county/update/{id}";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteCounty(@PathVariable("id") Long countyId, RedirectAttributes redirectAttributes) {
        boolean deleteResult = countyService.deleteCountyById(countyId);

        if (deleteResult) {
            redirectAttributes.addFlashAttribute("message", "County #" + countyId + "has been successfully deleted.");
            redirectAttributes.addFlashAttribute("messageType", "success");
        } else {
            redirectAttributes.addFlashAttribute("message", "Error in deleting a county #" + countyId + "!");
            redirectAttributes.addFlashAttribute("messageType", "error");
        }

        return "redirect:/county/";
    }

    @GetMapping("/restore/{id}")
    public String restoreCounty(@PathVariable("id") Long countyId, RedirectAttributes redirectAttributes) {
        boolean restoreResult = countyService.restoreCountyById(countyId);

        if (restoreResult) {
            redirectAttributes.addFlashAttribute("message", "County #" + countyId + " has been successfully restored.");
            redirectAttributes.addFlashAttribute("messageType", "success");
        } else {
            redirectAttributes.addFlashAttribute("message", "Error in restoring a county #" + countyId + "!");
            redirectAttributes.addFlashAttribute("messageType", "error");
        }

        return "redirect:/county/";
    }
}

