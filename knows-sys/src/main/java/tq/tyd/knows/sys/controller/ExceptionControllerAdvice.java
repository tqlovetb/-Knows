package tq.tyd.knows.sys.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import tq.tyd.knows.commons.exception.ServiceException;

@RestController
@Slf4j
public class ExceptionControllerAdvice {
    @ExceptionHandler
    public String handlerServiceException(ServiceException e){
        log.error("业务异常", e);
        return e.getMessage();
    }
    @ExceptionHandler
    public String handlerException(ServiceException e){
        log.error("其他异常", e);
        return e.getMessage();
    }
}
