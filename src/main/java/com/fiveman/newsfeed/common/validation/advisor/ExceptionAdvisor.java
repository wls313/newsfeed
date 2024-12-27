package com.fiveman.newsfeed.common.validation.advisor;
import com.fiveman.newsfeed.common.validation.dto.ErrorResponseDto;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@RestController
public class ExceptionAdvisor {

    // TODO @Valid에서 반환된 오류를 BAD_REQUEST로 반환합니다.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorResponseDto>> validationErrorHandler(MethodArgumentNotValidException exception){
        BindingResult bindingResult = exception.getBindingResult();
        List<ErrorResponseDto> errors = new ArrayList<>();
        bindingResult.getFieldErrors().forEach(fieldError ->
                errors.add(new ErrorResponseDto(HttpStatus.BAD_REQUEST.toString(),fieldError.getDefaultMessage())));
    return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
    }

    // TODO IllegaArgumentException오류를 BAD_REQUEST로 반환합니다.
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDto> argumentExceptionHandler(IllegalArgumentException exception ){

        return new ResponseEntity<>(new ErrorResponseDto(HttpStatus.NOT_FOUND.toString(),exception.getMessage()),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List<ErrorResponseDto>> constraintViolationHandler(ConstraintViolationException exception) {
        List<ErrorResponseDto> errors = new ArrayList<>();

        exception.getConstraintViolations().forEach(violation ->
                errors.add(new ErrorResponseDto(HttpStatus.BAD_REQUEST.toString(), violation.getMessage())));

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    // TODO ResponseStatusExcepion을 반환받은 Status_Code로 반환합니다.
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponseDto> responseStatusExceptionHandler(ResponseStatusException exception){
        if(exception.getReason()==null) {
            return new ResponseEntity<>(new ErrorResponseDto(exception.getStatusCode().toString(),"오류메시지를 입력하지 않았습니다."),exception.getStatusCode());
        }
            else{
            return new ResponseEntity<>(new ErrorResponseDto(exception.getStatusCode().toString(),exception.getReason()), exception.getStatusCode());
        }
    }
}
