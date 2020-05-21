package com.sda.studysystem.controllers;

import com.sda.studysystem.models.City;
import com.sda.studysystem.models.County;
import com.sda.studysystem.services.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/city")
public class CityController {

    @Autowired
    private CityService cityService;

    @GetMapping("")
    public String showAllCities(@ModelAttribute("messageType") String messageType, @ModelAttribute("message") String message,
                                  Model model) {
        List<City> cities = cityService.getAllCities();
        model.addAttribute("cities", cities);
        return "city/city-list";
    }

    @GetMapping("/add")
    public String addCityForm(@ModelAttribute("city") City ciy, @ModelAttribute("messageType") String messageType,
                                @ModelAttribute("message") String message) {
        return "city/city-add";
    }

    @PostMapping("/add")
    public String addCity(City city, RedirectAttributes redirectAttributes) {
        boolean createResult = false;

        if (isCityValid(city)) {
            city.setActive(true);
            createResult = cityService.createCity(city);
        }

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

        return "city/city-update";
    }

    @PostMapping("/update/{id}")
    public String updateCity(@PathVariable("id") Long cityId, City city, RedirectAttributes redirectAttributes) {
        boolean updateResult = false;

        if (isCityValid(city)) {
            city.setId(cityId);
            updateResult = cityService.updateCity(city);
        }

        if (updateResult) {
            redirectAttributes.addFlashAttribute("message", "City has been successfully updated.");
            redirectAttributes.addFlashAttribute("messageType", "success");
            return "redirect:/city/";
        } else {
            redirectAttributes.addAttribute("id", cityId);
            redirectAttributes.addAttribute("city", city);
            redirectAttributes.addFlashAttribute("message", "Error in updating a city!");
            redirectAttributes.addFlashAttribute("messageType", "error");
            return "redirect:/city/update/{id}";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteCounty(@PathVariable("id") Long cityId, RedirectAttributes redirectAttributes) {
        boolean deleteResult = cityService.deleteCityById(cityId);

        if (deleteResult) {
            redirectAttributes.addFlashAttribute("message", "City has been successfully deleted.");
            redirectAttributes.addFlashAttribute("messageType", "success");
        } else {
            redirectAttributes.addFlashAttribute("message", "Error in deleting a city!");
            redirectAttributes.addFlashAttribute("messageType", "error");
        }

        return "redirect:/city/";
    }

    @GetMapping("/restore/{id}")
    public String restoreCounty(@PathVariable("id") Long cityId, RedirectAttributes redirectAttributes) {
        boolean restoreResult = cityService.restoreCityById(cityId);

        if (restoreResult) {
            redirectAttributes.addFlashAttribute("message", "City has been successfully restored.");
            redirectAttributes.addFlashAttribute("messageType", "success");
        } else {
            redirectAttributes.addFlashAttribute("message", "Error in restoring a city!");
            redirectAttributes.addFlashAttribute("messageType", "error");
        }

        return "redirect:/city/";
    }

    private boolean isCityValid(City city) {
        return !city.getName().isEmpty();
    }
}

