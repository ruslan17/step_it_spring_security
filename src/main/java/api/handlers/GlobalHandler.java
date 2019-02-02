package api.handlers;

import api.exceptions.WrongDataException;
import api.model.ErrorInfo;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(WrongDataException.class)
    public ErrorInfo wrongDataHandler(
            WrongDataException exception,
            HttpServletRequest request) {

        String url = request.getRequestURL().toString();
        String message = exception.getLocalizedMessage();

        return ErrorInfo.builder()
                .url(url)
                .message(message)
                .build();

    }

}