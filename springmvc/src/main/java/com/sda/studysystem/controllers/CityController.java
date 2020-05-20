package com.sda.studysystem.controllers;

import com.sda.studysystem.models.City;
import com.sda.studysystem.services.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/city")
public class CityController {

    @Autowired
    private CityService cityService;

    @GetMapping("")
    public String showAllCitys(Model model) {
        List<City> citys = cityService.getAllCities();
        model.addAttribute("cities", citys);
        return "show-all-cities";
    }

    @GetMapping("/add")
    public String addCityForm(Model model) {
        return "add-city";
    }

    @PostMapping("/add")
    public String addCity(City city, Model model) {
        city.setActive(true);
        boolean createResult = cityService.createCity(city);

        if (createResult) {
            model.addAttribute("message", "City has been successfully created.");
            model.addAttribute("messageType", "success");
            return showAllCitys(model);
        } else {
            model.addAttribute("city", city);
            model.addAttribute("message", "Error in creating a city!");
            model.addAttribute("messageType", "error");
            return addCityForm(model);
        }
    }

    @GetMapping("/update")
    public String updateCityForm(Model model) {
        return "update-city";
    }

    @PostMapping("/update/{id}")
    public String updateCity(@PathVariable("id") Long cityId, City city, Model model) {
        city.setId(cityId);
        boolean updateResult = cityService.updateCity(city);

        if (updateResult) {
            model.addAttribute("message", "City has been successfully updated.");
            model.addAttribute("messageType", "success");
            return showAllCitys(model);
        } else {
            model.addAttribute("city", city);
            model.addAttribute("message", "Error in updating a city!");
            model.addAttribute("messageType", "error");
            return updateCityForm(model);
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteCity(@PathVariable("id") Long cityId, Model model) {
        boolean deleteResult = cityService.deleteCityById(cityId);

        if (deleteResult) {
            model.addAttribute("message", "City has been successfully deleted.");
            model.addAttribute("messageType", "success");
        } else {
            model.addAttribute("message", "Error in deleting a city!");
            model.addAttribute("messageType", "error");
        }

        return showAllCitys(model);
    }

    @GetMapping("/restore/{id}")
    public String restoreCity(@PathVariable("id") Long cityId, Model model) {
        boolean restoreResult = cityService.restoreCityById(cityId);

        if (restoreResult) {
            model.addAttribute("message", "City has been successfully restored.");
            model.addAttribute("messageType", "success");
        } else {
            model.addAttribute("message", "Error in restoring a city!");
            model.addAttribute("messageType", "error");
        }

        return showAllCitys(model);
    }
}

