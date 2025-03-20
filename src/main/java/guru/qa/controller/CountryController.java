package guru.qa.controller;

import guru.qa.domain.Country;
import guru.qa.dto.CountryJson;
import guru.qa.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/country")
public class CountryController {

    private final CountryService countryService;

    @Autowired
    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping("/all")
    public List<Country> allCountries() {
        return countryService.allCountries();
    }

    @PostMapping("/add")
    public CountryJson addCountry(@RequestBody CountryJson countryJson) {
        return countryService.addCountry(countryJson.countryName(), countryJson.countryCode());
    }

    @PatchMapping("/edit")
    public CountryJson editCountryName(@RequestBody CountryJson countryJson) {
        return countryService.editCountryName(countryJson.countryCode(), countryJson.countryName());
    }
}
