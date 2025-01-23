package com.onlineshop.productservice.advice;

import com.onlineshop.productservice.exception.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.ProblemDetail;

@ControllerAdvice
public class ProductServiceExceptionHandler {

    private static final String PRODUCT_NOT_FOUND_TITLE = "Product not found";

//    @ExceptionHandler(ConstraintViolationException.class)
//    public ResponseEntity<List<String>> validationErrorHandler(ConstraintViolationException ex){
//        List<String> errorsList = new ArrayList<>(ex.getConstraintViolations().size());
//
//        ex.getConstraintViolations().forEach(error -> errorsList.add(error.toString()));
//
//        return new ResponseEntity<>(errorsList, HttpStatus.BAD_REQUEST);
//    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ProblemDetail productNotFoundExceptionHandler(ProductNotFoundException ex){
        var problem =ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problem.setTitle(PRODUCT_NOT_FOUND_TITLE);
        return problem;
    }
}
