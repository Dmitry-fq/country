package guru.qa.service;

import guru.qa.data.CountryEntity;
import guru.qa.data.CountryRepository;
import guru.qa.domain.Country;
import guru.qa.dto.CountryJson;
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
        return countryRepository.findAll()
                                .stream()
                                .map(ce -> new Country(
                                                ce.getCountryName(),
                                                ce.getCountryCode()
                                        )
                                )
                                .toList();
    }

    @Override
    public CountryJson addCountry(String countryName, String countryCode) {
        CountryEntity countryEntity = countryRepository.save(
                new CountryEntity(
                        null,
                        countryName,
                        countryCode
                )
        );

        return countryEntity.toJson();
    }

    @Override
    public CountryJson editCountryName(String countryCode, String countryName) {
        CountryEntity country = countryRepository.findCountryEntitiesByCountryCode(countryCode);

        return countryRepository.save(
                new CountryEntity(
                        country.getId(),
                        countryName,
                        countryCode)
        ).toJson();
    }
}
