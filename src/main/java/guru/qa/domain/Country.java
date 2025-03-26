package guru.qa.domain;

import java.util.UUID;

public record Country(
        UUID id,
        String countryName,
        String countryCode
) {
}
