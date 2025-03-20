package guru.qa.service;

import guru.qa.domain.Country;
import guru.qa.dto.CountryJson;

import java.util.List;

public interface CountryService {

    List<Country> allCountries();

    CountryJson addCountry(String countryName, String countryCode);

    CountryJson editCountryName(String countryCode, String countryName);
}
