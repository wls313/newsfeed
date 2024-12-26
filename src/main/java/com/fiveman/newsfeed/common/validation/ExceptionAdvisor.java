package com.fiveman.newsfeed.common.validation;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@RestController
public class ExceptionAdvisor {

    // TODO @Valid에서 반환된 오류를 BAD_REQUEST로 반환합니다.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> validationErrorHandler(MethodArgumentNotValidException exception){
        BindingResult bindingResult = exception.getBindingResult();
        Map<String,String> errors = new HashMap<>();
        bindingResult.getFieldErrors().forEach(fieldError ->
                errors.put(fieldError.getField(),fieldError.getDefaultMessage()));
    return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
    }

    // TODO IllegaArgumentException오류를 BAD_REQUEST로 반환합니다.
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> argumentExceptionHandler(IllegalArgumentException exception ){
        return new ResponseEntity<>(exception.getMessage(),HttpStatus.BAD_REQUEST);
    }

    // TODO ResponseStatusExcepion을 BAD_REQUEST로 반환합니다.
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> responseStatusExceptionHandler(ResponseStatusException exception){
        if(exception.getReason()==null) {
            return new ResponseEntity<>("오류메시지를 입력하지 않았습니다.",exception.getStatusCode());
        }
            else{
            return new ResponseEntity<>(exception.getReason(), exception.getStatusCode());
        }
    }
}
