package com.example.demo.api.errorhandling;



import com.example.demo.api.model.response.ApiResponse;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ControllerAdviceError extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String message = ex.getMessage();

        if(ex instanceof MaxUploadSizeExceededException) message = "Maximum upload size exceeded";
        else if(ex instanceof MissingServletRequestPartException me) {
            message = me.getRequestPartName() + " Request Part Parameter is Missing";
            status = HttpStatus.BAD_REQUEST;
        }
        else if(ex instanceof MethodArgumentTypeMismatchException miss) {
            message = miss.getRequiredType() != null ? miss.getName() + " should be of type " + miss.getRequiredType().getName() : ex.getMessage();
            status = HttpStatus.BAD_REQUEST;
        }
        else if(ex instanceof MethodArgumentNotValidException inv) {
            ValidationError validationError = ValidationError.fromBindingErrors(inv.getBindingResult());
            message = String.join(",", validationError.getErrors());
        }

        logger.info("Handling error");
        ApiResponse response = new ApiResponse(message);
        return new ResponseEntity<>(response, status);

    }


}

