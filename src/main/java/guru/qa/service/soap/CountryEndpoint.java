package guru.qa.service.soap;

import guru.qa.domain.graphql.CountryGql;
import guru.qa.service.CountryService;
import guru.qa.xml.country.CountriesResponse;
import guru.qa.xml.country.Country;
import guru.qa.xml.country.CountryInputType;
import guru.qa.xml.country.CountryResponse;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;

import static guru.qa.config.AppConfig.SOAP_NAMESPACE;

@Endpoint
public class CountryEndpoint {

    private final CountryService countryService;

    public CountryEndpoint(CountryService countryService) {
        this.countryService = countryService;
    }

    @PayloadRoot(namespace = SOAP_NAMESPACE, localPart = "countriesRequest")
    @ResponsePayload
    public CountriesResponse countries() {
        final List<CountryGql> countryGqlList = countryService.allCountriesGql();
        CountriesResponse response = new CountriesResponse();
        response.getCountries().addAll(
                countryGqlList.stream()
                              .map(this::createCountry)
                              .toList()
        );

        return response;
    }

    @PayloadRoot(namespace = SOAP_NAMESPACE, localPart = "addCountryRequest")
    @ResponsePayload
    public CountryResponse addCountry(@RequestPayload CountryInputType countryInputType) {
        final CountryGql countryGql = countryService.addCountryGql(
                countryInputType.getCountryName(),
                countryInputType.getCountryCode()
        );

        return createCountryResponse(countryGql);
    }

    @PayloadRoot(namespace = SOAP_NAMESPACE, localPart = "editCountryRequest")
    @ResponsePayload
    public CountryResponse editCountryNameByCountryCode(@RequestPayload CountryInputType countryInputType) {
        final CountryGql countryGq = countryService.editCountryNameGqlByCountryCode(
                countryInputType.getCountryCode(),
                countryInputType.getCountryName()
        );

        return createCountryResponse(countryGq);
    }

    private CountryResponse createCountryResponse(CountryGql countryGql) {
        Country country = createCountry(countryGql);
        CountryResponse response = new CountryResponse();
        response.setCountry(country);

        return response;
    }

    private Country createCountry(CountryGql countryGq) {
        Country country = new Country();
        country.setId(countryGq.id().toString());
        country.setCountryName(countryGq.countryName());
        country.setCountryCode(countryGq.countryCode());

        return country;
    }
}
