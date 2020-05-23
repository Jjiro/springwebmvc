package com.sda.studysystem.controllers;

import com.sda.studysystem.models.City;
import com.sda.studysystem.models.Country;
import com.sda.studysystem.models.County;
import com.sda.studysystem.services.CityService;
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
@RequestMapping("/city")
public class CityController {

    @Autowired
    private CityService cityService;


    @Autowired
    private CountryService countryService;

    @Autowired
    private CountyService countyService;

    @GetMapping("")
    public String showAllCities(@ModelAttribute("messageType") String messageType, @ModelAttribute("message") String message,
                                Model model) {
        List<City> cities = cityService.getAllCities();
        model.addAttribute("cities", cities);
        return "city/city-list";
    }

    @GetMapping("/add")
    public String addCityForm(@ModelAttribute("city") City city, @ModelAttribute("messageType") String messageType,
                              @ModelAttribute("message") String message, Model model) {
        List<Country> countries = countryService.getAllCountries().stream()
                .filter(Country::isActive).collect(Collectors.toList());
        model.addAttribute("countries", countries);

        List<County> counties = countyService.getAllCounties().stream()
                .filter(County::isActive).collect(Collectors.toList());
        model.addAttribute("counties", counties);

        return "city/city-add";
    }

    @PostMapping("/add")
    public String addCity(@Valid City city, RedirectAttributes redirectAttributes) {
        boolean createResult = cityService.createCity(city);

        if (createResult) {
            redirectAttributes.addFlashAttribute("message", "City has been successfully created.");
            redirectAttributes.addFlashAttribute("messageType", "success");
            return "redirect:/city/";
        } else {
            redirectAttributes.addFlashAttribute("city", city);
            redirectAttributes.addFlashAttribute("message", "Error in creating a city!");
            redirectAttributes.addFlashAttribute("messageType", "error");
            return "redirect:/city/add";
        }
    }

    @GetMapping("/update/{id}")
    public String updateCityForm(@PathVariable("id") Long cityId, @RequestParam(value = "city", required = false) City city,
                                 @ModelAttribute("messageType") String messageType,
                                 @ModelAttribute("message") String message, Model model) {
        if (city == null) {
            model.addAttribute("city", cityService.getById(cityId));
        }

        List<Country> countries = countryService.getAllCountries().stream()
                .filter(Country::isActive).collect(Collectors.toList());
        model.addAttribute("countries", countries);

        List<County> counties = countyService.getAllCounties().stream()
                .filter(County::isActive).collect(Collectors.toList());
        model.addAttribute("counties", counties);

        return "city/city-update";
    }

    @PostMapping("/update/{id}")
    public String updateCity(@PathVariable("id") Long cityId, @Valid City city, RedirectAttributes redirectAttributes) {
        city.setId(cityId);
        boolean updateResult = cityService.updateCity(city);

        if (updateResult) {
            redirectAttributes.addFlashAttribute("message", "City #" + cityId + " has been successfully updated.");
            redirectAttributes.addFlashAttribute("messageType", "success");
            return "redirect:/city/";
        } else {
            redirectAttributes.addAttribute("id", cityId);
            redirectAttributes.addAttribute("city", city);
            redirectAttributes.addFlashAttribute("message", "Error in updating a city #" + cityId + "!");
            redirectAttributes.addFlashAttribute("messageType", "error");
            return "redirect:/city/update/{id}";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteCity(@PathVariable("id") Long cityId, RedirectAttributes redirectAttributes) {
        boolean deleteResult = cityService.deleteCityById(cityId);

        if (deleteResult) {
            redirectAttributes.addFlashAttribute("message", "City #" + cityId + " has been successfully deleted.");
            redirectAttributes.addFlashAttribute("messageType", "success");
        } else {
            redirectAttributes.addFlashAttribute("message", "Error in deleting a city #" + cityId + "!");
            redirectAttributes.addFlashAttribute("messageType", "error");
        }

        return "redirect:/city/";
    }

    @GetMapping("/restore/{id}")
    public String restoreCity(@PathVariable("id") Long cityId, RedirectAttributes redirectAttributes) {
        boolean restoreResult = cityService.restoreCityById(cityId);

        if (restoreResult) {
            redirectAttributes.addFlashAttribute("message", "City #" + cityId + "has been successfully restored.");
            redirectAttributes.addFlashAttribute("messageType", "success");
        } else {
            redirectAttributes.addFlashAttribute("message", "Error in restoring a city #" + cityId + "!");
            redirectAttributes.addFlashAttribute("messageType", "error");
        }

        return "redirect:/city/";
    }
}

