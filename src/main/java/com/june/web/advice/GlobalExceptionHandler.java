package com.june.web.advice;

import java.io.IOException;
import java.text.MessageFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.june.exception.ServiceException;
import com.june.util.ResponseUtils;
import com.june.vo.Response;
import com.june.vo.ResponseStatus;

import lombok.extern.slf4j.Slf4j;

/**
 * 自定义全局异常处理器
 */
@ControllerAdvice
@org.springframework.web.bind.annotation.ResponseStatus(HttpStatus.OK)
public @Slf4j class GlobalExceptionHandler {

    private static final String XML_HTTP_REQUEST = "XMLHttpRequest";
    private static final String AJAX_HEADER_NAME = "X-Requested-With";

    @ExceptionHandler(ServiceException.class)
    public ModelAndView serviceException(HttpServletRequest request, HttpServletResponse response,
                                         ServiceException ex) throws IOException {
        int code = ex.getCode();
        if (code == 0) {
            code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
        }
        return handleError(request, response, code, ex.getMessage());
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ModelAndView duplicateKeyException(HttpServletRequest request, HttpServletResponse response) {
        return handleError(request, response, ResponseStatus.RECORD_CONFLICT);
    }

    @ExceptionHandler({ MethodArgumentTypeMismatchException.class, HttpMessageNotReadableException.class })
    public ModelAndView methodArgumentTypeMismatchException(HttpServletRequest request,
                                                            HttpServletResponse response, Exception ex) {
        ResponseStatus paramError = ResponseStatus.PARAMETER_ERROR;
        if (ex instanceof MethodArgumentTypeMismatchException) {
            MethodArgumentTypeMismatchException typeError = (MethodArgumentTypeMismatchException) ex;
            paramError.setMsg(paramError.getMsg() + typeError.getName());
        }
        return handleError(request, response, paramError);
    }

    @ExceptionHandler({ MissingServletRequestParameterException.class,
            MissingServletRequestPartException.class })
    public ModelAndView missingServletRequestParameterException(HttpServletRequest request,
                                                                HttpServletResponse response, Exception ex) {
        ResponseStatus paramMissed = ResponseStatus.PARAMETER_MISSED;
        String paramName;
        if (ex instanceof MissingServletRequestParameterException) {
            paramName = ((MissingServletRequestParameterException) ex).getParameterName();
        } else {
            paramName = ((MissingServletRequestPartException) ex).getRequestPartName();
        }
        paramMissed.setMsg(paramMissed.getMsg() + paramName);
        return handleError(request, response, paramMissed);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ModelAndView httpRequestMethodNotSupportedException(HttpServletRequest request,
                                                               HttpServletResponse response,
                                                               HttpRequestMethodNotSupportedException ex) {
        ResponseStatus status = ResponseStatus.METHOD_NOT_SUPPORTED;
        status.setMsg(MessageFormat.format(status.getMsg(), ex.getMethod()));
        return handleError(request, response, status);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ModelAndView noHandlerFoundException(HttpServletRequest request, HttpServletResponse response) {
        return handleError(request, response, ResponseStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView otherException(HttpServletRequest request, HttpServletResponse response,
                                       Exception ex) throws IOException {
        GlobalExceptionHandler.log.error(ex.getMessage(), ex);
        return handleError(request, response, ResponseStatus.OTHER_ERROR);
    }

    private ModelAndView handleError(HttpServletRequest request, HttpServletResponse response,
                                     ResponseStatus status) {
        return handleError(request, response, status.getCode(), status.getMsg());
    }

    private ModelAndView handleError(HttpServletRequest request, HttpServletResponse response, int code,
                                     String msg) {
        ModelAndView modelAndView = new ModelAndView();
        Response errorResponse = Response.error(code, msg);
        if (GlobalExceptionHandler.XML_HTTP_REQUEST
            .equals(request.getHeader(GlobalExceptionHandler.AJAX_HEADER_NAME))) {
            // Ajax请求，直接响应json
            response.setStatus(HttpServletResponse.SC_OK);
            ResponseUtils.writeJson(response, JSONObject.valueToString(errorResponse));
        } else {
            errorResponse.append("url", request.getRequestURI());
            modelAndView.addObject("error", errorResponse).setViewName("error");
        }
        return modelAndView;
    }

}
