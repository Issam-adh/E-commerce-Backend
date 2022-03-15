package com.jobgate.demo.exceptions;
        import org.springframework.http.HttpStatus;
        import org.springframework.web.bind.annotation.ControllerAdvice;
        import org.springframework.web.bind.annotation.ResponseBody;
        import org.springframework.web.bind.annotation.ResponseStatus;

        import java.util.HashMap;
        import java.util.Map;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    Map<String,String> showCustomMessage(Exception e){


        Map<String,String> response = new HashMap<>();
        response.put("status","400");
        response.put("message",e.getMessage());
        response.put("data",null);

        return response;
    }
}