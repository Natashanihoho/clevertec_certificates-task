package ru.clevertec.hardziyevich.api.exception;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.function.Supplier;

@Builder
@RequiredArgsConstructor
public class ApplicationExceptionSupplier implements Supplier<ApplicationException> {

    private final ExceptionMessage exceptionMessage;
    private final String additionalMessage;
    private final HttpStatus httpStatus;
    private final Error error;

    @Override
    public ApplicationException get() {
        return new ApplicationException(
                String.format(exceptionMessage.format(), error.domainInformation(), additionalMessage),
                httpStatus,
                error
        );
    }

}
