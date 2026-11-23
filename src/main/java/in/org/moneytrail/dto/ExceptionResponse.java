package in.org.moneytrail.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import in.org.moneytrail.entity.ErrorCode;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
public record ExceptionResponse(
        String error,
        LocalDateTime timeStamp,
        int status,
        String message,
        String path,
        Map<String, String> fieldErrors
) {
    public static ExceptionResponse of(ErrorCode code, HttpStatus status,
                                       String message, String path) {
        return new ExceptionResponse(code.name(), LocalDateTime.now(),
                status.value(), message, path, null);
    }
}
