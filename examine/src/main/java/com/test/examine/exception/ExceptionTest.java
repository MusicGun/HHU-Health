package com.test.examine.exception;


import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;



@ControllerAdvice
public class ExceptionTest {

    public ModelAndView errorHandler(ExceptionHandler ex) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/error/500.html");
        return modelAndView;
    }

    @ExceptionHandler(MyException.class)
    public ModelAndView exceptionHandler(MyException ex)
    {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/error/500.html");
        return modelAndView;
    }
}
