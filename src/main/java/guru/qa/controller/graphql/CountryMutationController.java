package guru.qa.controller.graphql;

import guru.qa.domain.graphql.CountryGql;
import guru.qa.domain.graphql.CountryInputGql;
import guru.qa.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller
public class CountryMutationController {

    private final CountryService countryService;

    @Autowired
    public CountryMutationController(CountryService countryService) {
        this.countryService = countryService;
    }

    @MutationMapping
    public CountryGql addCountry(@Argument CountryInputGql input) {
        return countryService.addCountryGql(input.countryName(), input.countryCode());
    }

    @MutationMapping
    public CountryGql editCountryNameByCountryCode(@Argument CountryInputGql input) {
        return countryService.editCountryNameGqlByCountryCode(input.countryCode(), input.countryName());
    }
}
