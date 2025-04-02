package guru.qa.domain.graphql;

import guru.qa.domain.Country;

import java.util.UUID;

public record CountryGql(
        UUID id,
        String countryName,
        String countryCode
) {
    public Country toJson() {
        return new Country(
                id,
                countryName,
                countryCode
        );
    }
}
