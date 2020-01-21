package com.example.practice.handler;

import com.example.practice.exception.CustomIOException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.io.IOException;

@ControllerAdvice
public class HomeExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String handleIoException(CustomIOException e, WebRequest request, Model model){
        model.addAttribute("msg","Something went wrong!!");
        return "errorPage";
    }
}
