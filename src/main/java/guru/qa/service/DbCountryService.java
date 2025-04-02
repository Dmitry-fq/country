package guru.qa.service;

import guru.qa.data.CountryEntity;
import guru.qa.data.CountryRepository;
import guru.qa.domain.Country;
import guru.qa.domain.graphql.CountryGql;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DbCountryService implements CountryService {

    private final CountryRepository countryRepository;

    @Autowired
    public DbCountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    public List<Country> allCountries() {
        return allCountriesGql().stream()
                                .map(ce -> new Country(
                                                ce.id(),
                                                ce.countryName(),
                                                ce.countryCode()
                                        )
                                )
                                .toList();
    }

    @Override
    public List<CountryGql> allCountriesGql() {
        return countryRepository.findAll()
                                .stream()
                                .map(ce -> new CountryGql(
                                                ce.getId(),
                                                ce.getCountryName(),
                                                ce.getCountryCode()
                                        )
                                )
                                .toList();
    }

    @Override
    public Country addCountry(String countryName, String countryCode) {
        return addCountryGql(countryName, countryCode).toJson();
    }

    @Override
    public CountryGql addCountryGql(String countryName, String countryCode) {
        CountryEntity countryEntity = countryRepository.save(
                new CountryEntity(
                        null,
                        countryName,
                        countryCode
                )
        );

        return countryEntity.toGql();
    }

    @Override
    public Country editCountryName(String countryCode, String countryName) {
        return editCountryNameGqlByCountryCode(countryCode, countryName).toJson();
    }

    @Override
    public CountryGql editCountryNameGqlByCountryCode(String countryCode, String countryName) {
        CountryEntity country = countryRepository.findCountryEntitiesByCountryCode(countryCode);

        return countryRepository.save(
                new CountryEntity(
                        country.getId(),
                        countryName,
                        countryCode)
        ).toGql();
    }
}
