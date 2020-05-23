package com.sda.studysystem.controllers;

import com.sda.studysystem.models.Country;
import com.sda.studysystem.services.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/country")
public class CountryController {

    @Autowired
    private CountryService countryService;

    @GetMapping("")
    public String showAllCountries(@ModelAttribute("messageType") String messageType, @ModelAttribute("message") String message,
                                   Model model) {
        List<Country> countries = countryService.getAllCountries();
        model.addAttribute("countries", countries);
        return "country/country-list";
    }

    @GetMapping("/add")
    public String addCountryForm(@ModelAttribute("country") Country country, @ModelAttribute("messageType") String messageType,
                                 @ModelAttribute("message") String message) {
        return "country/country-add";
    }

    @PostMapping("/add")
    public String addCountry(@Valid Country country, RedirectAttributes redirectAttributes) {
        boolean createResult = countryService.createCountry(country);

        if (createResult) {
            redirectAttributes.addFlashAttribute("message", "Country has been successfully created.");
            redirectAttributes.addFlashAttribute("messageType", "success");
            return "redirect:/country/";
        } else {
            redirectAttributes.addFlashAttribute("country", country);
            redirectAttributes.addFlashAttribute("message", "Error in creating a country!");
            redirectAttributes.addFlashAttribute("messageType", "error");
            return "redirect:/country/add";
        }
    }

    @GetMapping("/update/{id}")
    public String updateCountryForm(@PathVariable("id") Long countryId, @RequestParam(value = "country", required = false) Country country,
                                    @ModelAttribute("messageType") String messageType,
                                    @ModelAttribute("message") String message, Model model) {
        if (country == null) {
            model.addAttribute("country", countryService.getById(countryId));
        }

        return "country/country-update";
    }

    @PostMapping("/update/{id}")
    public String updateCountry(@PathVariable("id") Long countryId, @Valid Country country, RedirectAttributes redirectAttributes) {
        country.setId(countryId);
        boolean updateResult = countryService.updateCountry(country);

        if (updateResult) {
            redirectAttributes.addFlashAttribute("message", "Country #" + countryId + " has been successfully updated.");
            redirectAttributes.addFlashAttribute("messageType", "success");
            return "redirect:/country/";
        } else {
            redirectAttributes.addAttribute("id", countryId);
            redirectAttributes.addAttribute("country", country);
            redirectAttributes.addFlashAttribute("message", "Error in updating this country #" + countryId + "!");
            redirectAttributes.addFlashAttribute("messageType", "error");
            return "redirect:/country/update/{id}";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteCountry(@PathVariable("id") Long countryId, RedirectAttributes redirectAttributes) {
        boolean deleteResult = countryService.deleteCountryById(countryId);

        if (deleteResult) {
            redirectAttributes.addFlashAttribute("message", "Country #" + countryId + " has been successfully deleted.");
            redirectAttributes.addFlashAttribute("messageType", "success");
        } else {
            redirectAttributes.addFlashAttribute("message", "Error in deleting country #" + countryId + "!");
            redirectAttributes.addFlashAttribute("messageType", "error");
        }

        return "redirect:/country/";
    }

    @GetMapping("/restore/{id}")
    public String restoreCountry(@PathVariable("id") Long countryId, RedirectAttributes redirectAttributes) {
        boolean restoreResult = countryService.restoreCountryById(countryId);

        if (restoreResult) {
            redirectAttributes.addFlashAttribute("message", "Country #" + countryId + " has been successfully restored.");
            redirectAttributes.addFlashAttribute("messageType", "success");
        } else {
            redirectAttributes.addFlashAttribute("message", "Error in restoring country #" + countryId + "!");
            redirectAttributes.addFlashAttribute("messageType", "error");
        }

        return "redirect:/country/";
    }
}


