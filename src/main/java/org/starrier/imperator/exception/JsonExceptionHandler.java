package org.starrier.imperator.exception;

import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.DefaultErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>Unified exception handler</p>
 *
 * @author Starrier
 * @date 2018/12/17.
 * @see ErrorWebExceptionHandler
 * @see org.springframework.web.server.WebExceptionHandler
 * @see org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler
 */
public class JsonExceptionHandler extends DefaultErrorWebExceptionHandler {

    /**
     * <p><p/>
     * 1.customer json Exception handler.
     *
     * @param applicationContext ApplicationContext
     * @param errorAttributes    ErrorAttributes
     * @param errorProperties    ErrorProperties
     * @param resourceProperties ResourceProperties.
     * @return {@link JsonExceptionHandler}JsonExceptionHandler return the result.
     */
    public JsonExceptionHandler(ErrorAttributes errorAttributes, ResourceProperties resourceProperties,
                                ErrorProperties errorProperties, ApplicationContext applicationContext) {
        super(errorAttributes, resourceProperties, errorProperties, applicationContext);
    }

    /**
     * 构建返回的JSON数据格式.
     *
     * @param status       状态码 int
     * @param errorMessage 异常信息
     * @return return the result.
     */
    private static Map<String, Object> response(final int status, final String errorMessage) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", status);
        map.put("message", errorMessage);
        map.put("data", null);
        return map;
    }

    /**
     * Get Exception.
     *
     * @param request           ServerRequest {@link ServerRequest}
     * @param includeStackTrace boolean
     * @return return the result with Map which use json format.
     */
    @Override
    protected Map<String, Object> getErrorAttributes(ServerRequest request, boolean includeStackTrace) {
        int code = 500;
        Throwable error = super.getError(request);
        if (error instanceof org.springframework.cloud.gateway.support.NotFoundException) {
            code = 404;
        }
        if (error instanceof BindException) {
            code = 400;
        }
        if (error instanceof ConstraintViolationException) {
            code = 400;
        }
        return response(code, this.buildMessage(request, error));
    }

    /**
     * 指定响应处理方法为JSON处理的方法.
     *
     * @param errorAttributes ErrorAttributes {@link ErrorAttributes}
     * @return RouterFunction<ServerResponse> return the result.
     */
    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(final ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    /**
     * 根据 status 获取对应的 HttpStatus {@link HttpStatus}.
     *
     * @param errorAttributes Map
     */
    @Override
    protected HttpStatus getHttpStatus(Map<String, Object> errorAttributes) {
        int statusCode = (int) errorAttributes.get("status");
        return HttpStatus.valueOf(statusCode);
    }

    /**
     * 构建异常信息.
     *
     * @param request ServerRequest {@link ServerRequest}
     * @param ex      Throwable {@link Throwable}
     * @return return the result {@link String}
     */
    private String buildMessage(ServerRequest request, Throwable ex) {
        StringBuilder message = new StringBuilder("Failed to handle request [")
                .append(request.methodName())
                .append(" ")
                .append(request.uri())
                .append("]");
        if (ex != null) {
            message.append(": ")
                    .append(ex.getMessage());
        }
        return message.toString();
    }

}