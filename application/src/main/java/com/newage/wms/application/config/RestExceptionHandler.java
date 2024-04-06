package com.newage.wms.application.config;

import com.newage.wms.application.dto.responsedto.ResponseDTO;
import com.newage.wms.application.dto.responsedto.ResponseError;
import com.newage.wms.service.exception.ServiceErrors;
import com.newage.wms.service.exception.ServiceException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.*;

@Log4j2
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        log.error(ex);
        final List<String> errors = new ArrayList<>();
        for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }
        final ResponseError error = new ResponseError(HttpStatus.NOT_ACCEPTABLE.getReasonPhrase(), errors);
        ResponseDTO responseError =  new ResponseDTO(HttpStatus.NOT_ACCEPTABLE.value(), Boolean.FALSE,null, error);
        return new ResponseEntity<>(responseError, new HttpHeaders(), HttpStatus.BAD_REQUEST);

    }

    @Override
    protected ResponseEntity<Object> handleBindException(final BindException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {

        log.error(ex);
        final List<String> errors = new ArrayList<>();
        for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }
        final ResponseError error = new ResponseError(HttpStatus.BAD_REQUEST.getReasonPhrase(), errors);
        ResponseDTO responseError =  new ResponseDTO(HttpStatus.BAD_REQUEST.value(), Boolean.FALSE,null, error);
        return new ResponseEntity<>(responseError, new HttpHeaders(), HttpStatus.BAD_REQUEST);

    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(final TypeMismatchException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {

        log.error(ex);
        final String errorMessage = ex.getValue() + " value for " + ex.getPropertyName() + " should be of type " + ex.getRequiredType();
        final ResponseError error = new ResponseError(HttpStatus.BAD_REQUEST.getReasonPhrase(), List.of(errorMessage));
        ResponseDTO responseError =  new ResponseDTO(HttpStatus.BAD_REQUEST.value(), Boolean.FALSE,null, error);
        return new ResponseEntity<>(responseError, new HttpHeaders(), HttpStatus.BAD_REQUEST);

    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(final MissingServletRequestPartException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {

        log.error(ex);
        final String errorMessage = ex.getRequestPartName() + " part is missing";
        final ResponseError error = new ResponseError(HttpStatus.BAD_REQUEST.getReasonPhrase(), List.of(errorMessage));
        ResponseDTO responseError =  new ResponseDTO(HttpStatus.BAD_REQUEST.value(), Boolean.FALSE,null, error);
        return new ResponseEntity<>(responseError, new HttpHeaders(), HttpStatus.BAD_REQUEST);

    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(final MissingServletRequestParameterException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {

        log.error(ex);
        final String errorMessage = ex.getParameterName() + " parameter is missing";
        final ResponseError error = new ResponseError(HttpStatus.BAD_REQUEST.getReasonPhrase(), List.of(errorMessage));
        ResponseDTO responseError =  new ResponseDTO(HttpStatus.BAD_REQUEST.value(), Boolean.FALSE,null, error);
        return new ResponseEntity<>(responseError, new HttpHeaders(), HttpStatus.BAD_REQUEST);

    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        log.error(ex);
        final String errorMessage = ex.getMessage()+ " body is missing";
        final ResponseError error = new ResponseError(HttpStatus.BAD_REQUEST.getReasonPhrase(), List.of(errorMessage));
        ResponseDTO responseError =  new ResponseDTO(HttpStatus.BAD_REQUEST.value(), Boolean.FALSE,null, error);
        return new ResponseEntity<>(responseError, new HttpHeaders(), HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler({ MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(final MethodArgumentTypeMismatchException ex, final WebRequest request) {

        log.error(ex);
        final String errorMessage = ex.getName() + " should be of type " + Objects.requireNonNull(ex.getRequiredType()).getName();
        final ResponseError error = new ResponseError(HttpStatus.BAD_REQUEST.getReasonPhrase(), List.of(errorMessage));
        ResponseDTO responseError =  new ResponseDTO(HttpStatus.BAD_REQUEST.value(), Boolean.FALSE,null, error);
        return new ResponseEntity<>(responseError, new HttpHeaders(), HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler({ PropertyReferenceException.class})
    public ResponseEntity<Object> handlePropertyReferenceException(final PropertyReferenceException ex, final WebRequest request) {

        log.error(ex);
        final String errorMessage = ex.getLocalizedMessage();
        final ResponseError error = new ResponseError(HttpStatus.BAD_REQUEST.getReasonPhrase(), List.of(errorMessage));
        ResponseDTO responseError =  new ResponseDTO(HttpStatus.BAD_REQUEST.value(), Boolean.FALSE,null, error);
        return new ResponseEntity<>(responseError, new HttpHeaders(), HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler({ ConstraintViolationException.class })
    public ResponseEntity<Object> handleConstraintViolation(final ConstraintViolationException ex, final WebRequest request) {

        log.error(ex);
        final List<String> errorMessage = new ArrayList<>();
        for (final ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errorMessage.add(violation.getRootBeanClass().getName() + " " + violation.getPropertyPath() + ": " + violation.getMessage());
        }
        final ResponseError error = new ResponseError(HttpStatus.BAD_REQUEST.getReasonPhrase(), errorMessage);
        ResponseDTO responseError =  new ResponseDTO(HttpStatus.BAD_REQUEST.value(), Boolean.FALSE,null, error);
        return new ResponseEntity<>(responseError, new HttpHeaders(), HttpStatus.BAD_REQUEST);

    }



    @ExceptionHandler( ConversionFailedException.class)
    public ResponseEntity<Object> handleConversionFailedException(final ConversionFailedException ex, final WebRequest request) {

        log.error(ex);
        final ResponseError error = new ResponseError(ex.getMessage(), List.of(Objects.requireNonNull(ex.getMessage())));
        ResponseDTO responseError =  new ResponseDTO(HttpStatus.BAD_REQUEST.value(), Boolean.FALSE,null, error);
        return new ResponseEntity<>(responseError, new HttpHeaders(), HttpStatus.BAD_REQUEST);

    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(final NoHandlerFoundException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {

        log.error(ex);
        final String errorMessage = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();
        final ResponseError error = new ResponseError(HttpStatus.NOT_FOUND.getReasonPhrase(), List.of(errorMessage));
        ResponseDTO responseError =  new ResponseDTO(HttpStatus.NOT_FOUND.value(), Boolean.FALSE,null, error);
        return new ResponseEntity<>(responseError, new HttpHeaders(), HttpStatus.NOT_FOUND);

    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(final HttpRequestMethodNotSupportedException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {

        log.error(ex);
        final StringBuilder builder = new StringBuilder();
        builder.append(ex.getMethod());
        builder.append(" method is not supported for this request. Supported methods are ");
        Objects.requireNonNull(ex.getSupportedHttpMethods()).forEach(t -> builder.append(t).append(" "));
        final ResponseError error = new ResponseError(HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase(), List.of(builder.toString()));
        ResponseDTO responseError =  new ResponseDTO(HttpStatus.METHOD_NOT_ALLOWED.value(), Boolean.FALSE,null, error);
        return new ResponseEntity<>(responseError, new HttpHeaders(), HttpStatus.METHOD_NOT_ALLOWED);

    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(final HttpMediaTypeNotSupportedException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {

        log.error(ex);
        final StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(" "));
        final ResponseError error = new ResponseError(HttpStatus.UNSUPPORTED_MEDIA_TYPE.getReasonPhrase(), List.of(builder.toString()));
        ResponseDTO responseError =  new ResponseDTO(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(), Boolean.FALSE,null, error);
        return new ResponseEntity<>(responseError, new HttpHeaders(), HttpStatus.UNSUPPORTED_MEDIA_TYPE);

    }

    @ExceptionHandler(ServiceClientException.class)
    public ResponseEntity<Object> handleServiceClientException(ServiceClientException ex,
                                                          HttpServletResponse response,
                                                          WebRequest request) {

        log.info(ex);
        ex.printStackTrace();
        final ResponseError error = new ResponseError(ex.getErrorCode(), ex.getErrorMessages());
        ResponseDTO responseError = new ResponseDTO(ex.getHttpStatus(), Boolean.FALSE, null, error);
        return new ResponseEntity<>(responseError, new HttpHeaders(), HttpStatus.valueOf(ex.getHttpStatus()));

    }

    /*
     * exception handler for ServiceException
     */
    @ExceptionHandler({ServiceException.class})
    public ResponseEntity<Object> handleServiceException(final ServiceException serviceException) {

        String errorMessage = messageSource.getMessage(serviceException.getKey() , null, Locale.US);
        log.error(errorMessage,serviceException);
        final ResponseError responseError = new ResponseError(serviceException.getCode(), List.of(errorMessage));
        ResponseDTO responseDto =  new ResponseDTO(HttpStatus.BAD_REQUEST.value(), Boolean.FALSE,null, responseError);
        return new ResponseEntity<>(responseDto, new HttpHeaders(), HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleAll(final Exception ex, final WebRequest request, HttpServletResponse response,final Locale locale) {
        String errorMessage = messageSource.getMessage(ServiceErrors.INTERNAL_SERVER_ERROR.KEY, null, locale);
        log.error(ex);
        ex.printStackTrace();
        final ResponseError error = new ResponseError(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), List.of(errorMessage));
        ResponseDTO responseError =  new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), Boolean.FALSE,null, error);
        return new ResponseEntity<>(responseError, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
