package guru.qa.service;

import com.google.protobuf.Empty;
import guru.qa.domain.graphql.CountryGql;
import guru.qa.grpc.country.Count;
import guru.qa.grpc.country.CountryRequest;
import guru.qa.grpc.country.CountryResponse;
import guru.qa.grpc.country.CountryServiceGrpc;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class GrpcCountryService extends CountryServiceGrpc.CountryServiceImplBase {

    private static final Logger logger = Logger.getLogger(GrpcCountryService.class.getName());

    private final CountryService countryService;

    public GrpcCountryService(CountryService countryService) {
        this.countryService = countryService;
    }

    @Override
    public void countries(Empty request, StreamObserver<CountryResponse> responseObserver) {
        final List<CountryGql> countryGqlList = countryService.allCountriesGql();

        countryGqlList.forEach(c -> responseObserver.onNext(
                        CountryResponse.newBuilder()
                                       .setId(c.id().toString())
                                       .setCountryName(c.countryName())
                                       .setCountryCode(c.countryCode())
                                       .build()
                )
        );
        responseObserver.onCompleted();
    }

    @Override
    public void addCountry(CountryRequest request, StreamObserver<CountryResponse> responseObserver) {
        final CountryGql countryGql = countryService.addCountryGql(request.getCountryName(), request.getCountryCode());

        responseObserver.onNext(
                CountryResponse.newBuilder()
                               .setId(countryGql.id().toString())
                               .setCountryName(countryGql.countryName())
                               .setCountryCode(countryGql.countryCode())
                               .build()
        );
        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<CountryRequest> addCountries(StreamObserver<Count> responseObserver) {
        return new StreamObserver<>() {
            int countryCount;

            @Override
            public void onNext(CountryRequest value) {
                countryService.addCountryGql(value.getCountryName(), value.getCountryCode());
                countryCount++;
            }

            @Override
            public void onError(Throwable t) {
                logger.log(Level.WARNING, "Encountered error in addCountries", t);
            }

            @Override
            public void onCompleted() {
                responseObserver.onNext(
                        Count.newBuilder()
                             .setCount(countryCount)
                             .build()
                );
                responseObserver.onCompleted();
            }
        };
    }

    @Override
    public void editCountryNameByCountryCode(CountryRequest request, StreamObserver<CountryResponse> responseObserver) {
        final CountryGql countryGql = countryService.editCountryNameGqlByCountryCode(
                request.getCountryCode(), request.getCountryName()
        );

        responseObserver.onNext(
                CountryResponse.newBuilder()
                               .setCountryName(countryGql.countryName())
                               .setCountryCode(countryGql.countryCode())
                               .build()
        );
        responseObserver.onCompleted();
    }
}
