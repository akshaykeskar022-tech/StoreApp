package com.example.store.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler
{
  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)

  public String handelException(Exception ex)
  {
      return "Error: "+ex.getMessage();
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public List<String> handleValidation(MethodArgumentNotValidException ex)
  {
      //return "Error: "+ex.getBindingResult().getFieldError().getDefaultMessage();
      List<String> validationErrorList=new ArrayList<>();

      validationErrorList.add("Check below errors");
      for(FieldError error: ex.getBindingResult().getFieldErrors())
      {
          validationErrorList.add(error.getField()+": "+error.getDefaultMessage());
      }
      return validationErrorList;
  }
}
