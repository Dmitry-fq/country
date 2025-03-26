package guru.qa.service;

import guru.qa.domain.Country;
import guru.qa.domain.graphql.CountryGql;

import java.util.List;

public interface CountryService {

    List<Country> allCountries();

    List<CountryGql> allCountriesGql();

    Country addCountry(String countryName, String countryCode);

    CountryGql addCountryGql(String countryName, String countryCode);

    Country editCountryName(String countryCode, String countryName);

    CountryGql editCountryNameGqlByCountryCode(String countryCode, String countryName);
}
