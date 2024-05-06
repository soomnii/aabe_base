//package com.aa.be.exception.advice;
//
//import org.springframework.web.bind.annotation.ControllerAdvice;
//
//@ControllerAdvice
//public class DefaultExceptionAdvice {
//    private final Logger LOGGER = LoggerFactory.getLogger(DefaultExceptionAdvice.class);
//
//    @ExceptionHandler(BusinessException.class)
//    protected ResponseEntity<Object> handleException(final BusinessException businessException) {
//        Map<String, Object> result = new HashMap<>();
//        result.put(ResponseEntityConstants.SUCCESS_OR_NOT, ResponseEntityConstants.SUCCESS_NO_FLAG);
//        result.put(ResponseEntityConstants.STATUS_CODE, businessException.getStatusCode());
//
//        if (!ValidateUtility.isEmpty(businessException.getMessage())) {
//            result.put(ResponseEntityConstants.ERROR_MESSAGE, businessException.getMessage());
//        }
//
//        return new ResponseEntity<>(result, OK);
//    }
//
//    @ExceptionHandler(SystemException.class)
//    protected ResponseEntity<Object> handleException(final SystemException systemException) {
//        Map<String, Object> result = new HashMap<>();
//        result.put(ResponseEntityConstants.SUCCESS_OR_NOT, ResponseEntityConstants.SUCCESS_NO_FLAG);
//        result.put(ResponseEntityConstants.STATUS_CODE, systemException.getStatusCode());
//        // result.put(ResponseEntityConstants.ERROR_MESSAGE,
//        // systemException.getStatusCode());
//        // //message --> status 값으로
//        if (systemException.getMessage() != null) {
//            LOGGER.error(
//                    "{}", systemException.getMessage().replace("\r", "\\r").replace("\n", "\\n"));
//        }
//        return new ResponseEntity<>(result, OK);
//    }
//
//    @ExceptionHandler(RestApiException.class)
//    protected ResponseEntity<Object> handleException(final RestApiException restApiException) {
//        Map<String, Object> result = new HashMap<>();
//        result.put(ResponseEntityConstants.SUCCESS_OR_NOT, ResponseEntityConstants.SUCCESS_NO_FLAG);
//        result.put(ResponseEntityConstants.STATUS_CODE, restApiException.getHttpStatus());
//        result.put(ResponseEntityConstants.ERROR_MESSAGE, restApiException.getMessage());
//        return new ResponseEntity<>(result, restApiException.getHttpStatus());
//    }
//
//    @ExceptionHandler(sFeignClientException.class)
//    protected ResponseEntity<Object> handleException(
//            final sFeignClientException sFeignClientException) {
//        Map<String, Object> result = new HashMap<>();
//
//        sPointResponseCode sPointResponseCode =
//                Arrays.stream(sPointResponseCode.values())
//                        .filter(
//                                code ->
//                                        code.value()
//                                                .equals(
//                                                        sFeignClientException
//                                                                .getFeignErrorResponse()
//                                                                .getCode()))
//                        .findAny()
//                        .orElse(null);
//
//        result.put(ResponseEntityConstants.SUCCESS_OR_NOT, ResponseEntityConstants.SUCCESS_NO_FLAG);
//        result.put(
//                ResponseEntityConstants.STATUS_CODE,
//                sPointResponseCode == null
//                        ? sFeignClientException.getStatus()
//                        : sPointResponseCode.statusCode());
//        result.put(
//                ResponseEntityConstants.ERROR_MESSAGE,
//                sFeignClientException.getFeignErrorResponse().getMessage());
//
//        return new ResponseEntity<>(
//                result,
//                sPointResponseCode == null
//                        ? OK
//                        : HttpStatusCode.valueOf(sPointResponseCode.httpStatus()));
//    }
//
//    @ExceptionHandler(Exception.class)
//    protected ResponseEntity<Object> handleException(final Exception exception) {
//        Map<String, Object> result = new HashMap<>();
//        ResponseEntity<Object> ret = null;
//
//        if (exception instanceof BusinessException) {
//            BusinessException businessException = (BusinessException) exception;
//            result.put(
//                    ResponseEntityConstants.SUCCESS_OR_NOT,
//                    ResponseEntityConstants.SUCCESS_NO_FLAG);
//            result.put(ResponseEntityConstants.STATUS_CODE, businessException.getStatusCode());
//            result.put(ResponseEntityConstants.ERROR_MESSAGE, businessException.getMessage());
//
//            ret = new ResponseEntity<>(result, OK);
//        } else if (exception instanceof SystemException) {
//            SystemException systemException = (SystemException) exception;
//            result.put(
//                    ResponseEntityConstants.SUCCESS_OR_NOT,
//                    ResponseEntityConstants.SUCCESS_NO_FLAG);
//            result.put(ResponseEntityConstants.STATUS_CODE, systemException.getStatusCode());
//            ret = new ResponseEntity<>(result, OK);
//            if (systemException.getMessage() != null) {
//                LOGGER.error(
//                        "{}",
//                        systemException.getMessage().replace("\r", "\\r").replace("\n", "\\n"));
//            }
//        } else if (exception instanceof MethodArgumentNotValidException) {
//            MethodArgumentNotValidException methodArgumentNotValidException =
//                    (MethodArgumentNotValidException) exception;
//
//            result = handleMethodArgumentNotValidException(methodArgumentNotValidException);
//
//            result.put(
//                    ResponseEntityConstants.SUCCESS_OR_NOT,
//                    ResponseEntityConstants.SUCCESS_NO_FLAG);
//            ret = new ResponseEntity<>(result, OK);
//        } else if (exception instanceof ConstraintViolationException) {
//            ConstraintViolationException constraintViolationException =
//                    (ConstraintViolationException) exception;
//
//            result = handleConstraintViolationException(constraintViolationException);
//
//            result.put(
//                    ResponseEntityConstants.SUCCESS_OR_NOT,
//                    ResponseEntityConstants.SUCCESS_NO_FLAG);
//            ret = new ResponseEntity<>(result, OK);
//        } else if (exception instanceof HttpMessageNotReadableException) {
//            HttpMessageNotReadableException httpMessageNotReadableException =
//                    (HttpMessageNotReadableException) exception;
//
//            result = handleHttpMessageNotReadableException(httpMessageNotReadableException);
//
//            if (exception.getMessage() != null) {
//                LOGGER.error(
//                        "{}", exception.getMessage().replace("\r", "\\r").replace("\n", "\\n"));
//            }
//            ret = new ResponseEntity<>(result, OK);
//        } else if (exception instanceof MissingServletRequestParameterException) {
//            result.put(
//                    ResponseEntityConstants.SUCCESS_OR_NOT,
//                    ResponseEntityConstants.SUCCESS_NO_FLAG);
//            result.put(
//                    ResponseEntityConstants.STATUS_CODE, StatusCodeConstants.MANDATORY_PARAM_ERR);
//            result.put(
//                    ResponseEntityConstants.MANDATORY_PARAM_ERR_KEY,
//                    ((MissingServletRequestParameterException) exception).getParameterName());
//            ret = new ResponseEntity<>(result, OK);
//        } else if (exception instanceof BindException) {
//            result =
//                    handleValidateException(
//                            ((BindException) exception).getBindingResult().getFieldErrors());
//            result.put(
//                    ResponseEntityConstants.SUCCESS_OR_NOT,
//                    ResponseEntityConstants.SUCCESS_NO_FLAG);
//            ret = new ResponseEntity<>(result, OK);
//        } else if (exception instanceof DuplicateKeyException) {
//            result.put(
//                    ResponseEntityConstants.SUCCESS_OR_NOT,
//                    ResponseEntityConstants.SUCCESS_NO_FLAG);
//            result.put(
//                    ResponseEntityConstants.STATUS_CODE, StatusCodeConstants.DUPLICATE_ENTRY_ERROR);
//            result.put(
//                    ResponseEntityConstants.DUPLICATE_ENTRY_ERROR_KEY,
//                    ((DuplicateKeyException) exception).getMessage());
//            ret = new ResponseEntity<>(result, OK);
//        } else if (exception instanceof AccessDeniedException) {
//            result.put(
//                    ResponseEntityConstants.SUCCESS_OR_NOT,
//                    ResponseEntityConstants.SUCCESS_NO_FLAG);
//            result.put(
//                    ResponseEntityConstants.STATUS_CODE,
//                    StatusCodeConstants.NOT_AUTHORIZED_EXCEPTION);
//            result.put(
//                    ResponseEntityConstants.ERROR_MESSAGE,
//                    ((AccessDeniedException) exception).getMessage());
//            ret = new ResponseEntity<>(result, OK);
//
//        } else if (exception instanceof sFeignClientException) {
//            sPointResponseCode sPointResponseCode =
//                    Arrays.stream(sPointResponseCode.values())
//                            .filter(
//                                    code ->
//                                            code.value()
//                                                    .equals(
//                                                            ((sFeignClientException) exception)
//                                                                    .getFeignErrorResponse()
//                                                                    .getCode()))
//                            .findAny()
//                            .orElse(null);
//
//            result.put(
//                    ResponseEntityConstants.SUCCESS_OR_NOT,
//                    ResponseEntityConstants.SUCCESS_NO_FLAG);
//            result.put(
//                    ResponseEntityConstants.STATUS_CODE,
//                    sPointResponseCode == null
//                            ? ((sFeignClientException) exception).getStatus()
//                            : sPointResponseCode.statusCode());
//            result.put(
//                    ResponseEntityConstants.ERROR_MESSAGE,
//                    ((sFeignClientException) exception).getFeignErrorResponse().getMessage());
//
//            ret =
//                    new ResponseEntity<>(
//                            result,
//                            sPointResponseCode == null
//                                    ? OK
//                                    : HttpStatusCode.valueOf(sPointResponseCode.httpStatus()));
//        } else {
//            result.put(
//                    ResponseEntityConstants.SUCCESS_OR_NOT,
//                    ResponseEntityConstants.SUCCESS_NO_FLAG);
//            result.put(
//                    ResponseEntityConstants.STATUS_CODE, StatusCodeConstants.INTERNAL_SERVER_ERROR);
//            HttpHeaders headers = new HttpHeaders();
//            headers.add("Access-Control-Allow-Methods", "*");
//            headers.add("Access-Control-Allow-Credentials", "true");
//            headers.add("Access-Control-Expose-Headers", "*");
//            ret = new ResponseEntity<>(result, headers, OK);
//            // result.put(ResponseEntityConstants.ERROR_MESSAGE,
//            // StatusCodeConstants.INTERNAL_SERVER_ERROR); //message --> status 값으로
//
//            LOGGER.error("{}", exception.toString());
//        }
//
//        return ret;
//    }
//
//    @ResponseStatus(code = NOT_FOUND)
//    @ExceptionHandler(NoHandlerFoundException.class)
//    protected ResponseEntity<Object> handleException(
//            final NoHandlerFoundException notFoundException) {
//        Map<String, Object> result = new HashMap<>();
//        result.put(ResponseEntityConstants.SUCCESS_OR_NOT, ResponseEntityConstants.SUCCESS_NO_FLAG);
//        return new ResponseEntity<>(result, OK);
//    }
//
//    private Map<String, Object> handleMethodArgumentNotValidException(
//            final MethodArgumentNotValidException exception) {
//        return handleValidateException(exception.getBindingResult().getFieldErrors());
//    }
//
//    private Map<String, Object> handleConstraintViolationException(
//            final ConstraintViolationException exception) {
//
//        List<FieldError> fieldErrors =
//                exception.getConstraintViolations().stream()
//                        .map(
//                                constraintViolation -> {
//                                    String[] codes = {
//                                            constraintViolation
//                                                    .getConstraintDescriptor()
//                                                    .getAnnotation()
//                                                    .annotationType()
//                                                    .getSimpleName()
//                                    };
//
//                                    String propertyPath =
//                                            constraintViolation.getPropertyPath().toString();
//                                    String field =
//                                            propertyPath.substring(
//                                                    propertyPath.lastIndexOf(".") + 1);
//
//                                    return new FieldError(
//                                            constraintViolation.getRootBeanClass().getName(),
//                                            field,
//                                            null,
//                                            true,
//                                            codes,
//                                            null,
//                                            constraintViolation.getMessage());
//                                })
//                        .collect(Collectors.toList());
//
//        return handleValidateException(fieldErrors);
//    }
//
//    private Map<String, Object> handleHttpMessageNotReadableException(
//            final HttpMessageNotReadableException exception) {
//        Map<String, Object> result = new HashMap<>();
//        Throwable cause = exception.getCause();
//
//        if (cause instanceof MismatchedInputException) {
//            MismatchedInputException mismatchedInputException = (MismatchedInputException) cause;
//            result.put(
//                    ResponseEntityConstants.SUCCESS_OR_NOT,
//                    ResponseEntityConstants.SUCCESS_NO_FLAG);
//            result.put(
//                    ResponseEntityConstants.INVALID_PARAMETER_ERR_KEY,
//                    mismatchedInputException.getPath().get(0).getFieldName());
//            result.put(
//                    ResponseEntityConstants.STATUS_CODE,
//                    StatusCodeConstants.MISMATCH_INPUT_TYPE_ERR);
//        } else if (cause instanceof JsonParseException) {
//            result.put(
//                    ResponseEntityConstants.SUCCESS_OR_NOT,
//                    ResponseEntityConstants.SUCCESS_NO_FLAG);
//            result.put(
//                    ResponseEntityConstants.STATUS_CODE,
//                    StatusCodeConstants.INVALID_JSON_FORMAT_ERR);
//        } else {
//            result.put(
//                    ResponseEntityConstants.SUCCESS_OR_NOT,
//                    ResponseEntityConstants.SUCCESS_NO_FLAG);
//            result.put(
//                    ResponseEntityConstants.STATUS_CODE, StatusCodeConstants.INTERNAL_SERVER_ERROR);
//        }
//
//        return result;
//    }
//
//    private Map<String, Object> handleValidateException(final List<FieldError> errorList) {
//        Map<String, Object> result = new HashMap<>();
//
//        List<FieldError> mandatoryFieldErrors = new ArrayList<>();
//        List<FieldError> invalidFieldErrors = new ArrayList<>();
//        List<FieldError> lengthFieldErrors = new ArrayList<>();
//        List<FieldError> patternFieldErrors = new ArrayList<>();
//        List<FieldError> emailFieldErrors = new ArrayList<>();
//        List<FieldError> nicknameFieldErrors = new ArrayList<>();
//        List<FieldError> enumFieldErrors = new ArrayList<>();
//        List<FieldError> dateFieldErrors = new ArrayList<>();
//        List<FieldError> rangeFieldErrors = new ArrayList<>();
//        List<FieldError> phoneNumberFieldError = new ArrayList<>();
//        List<FieldError> passwordFieldError = new ArrayList<>();
//        List<FieldError> maxFieldError = new ArrayList<>();
//        List<FieldError> sizeWithEmojiFieldError = new ArrayList<>();
//
//        for (final FieldError error : errorList) {
//            if (error.getCode().equals(ValidatorCodeConstants.NOT_BLANK)
//                    || error.getCode().equals(ValidatorCodeConstants.NOT_EMPTY)
//                    || error.getCode().equals(ValidatorCodeConstants.NOT_NULL)) {
//                mandatoryFieldErrors.add(error);
//            } else if (error.getCode().equals(ValidatorCodeConstants.POSITIVE_OR_ZERO)
//                    || error.getCode().equals(ValidatorCodeConstants.POSITIVE)) {
//                invalidFieldErrors.add(error);
//            } else if (error.getCode().equals(ValidatorCodeConstants.SIZE)
//                    || error.getCode().equals(ValidatorCodeConstants.LENGTH)) {
//                lengthFieldErrors.add(error);
//            } else if (error.getCode().equals(ValidatorCodeConstants.PATTERN)) {
//                patternFieldErrors.add(error);
//            } else if (error.getCode().equals(ValidatorCodeConstants.EMAIL)) {
//                emailFieldErrors.add(error);
//            } else if (error.getCode().equals(ValidatorCodeConstants.NICKNAME)) {
//                nicknameFieldErrors.add(error);
//            } else if (error.getCode().equals(ValidatorCodeConstants.ENUM)) {
//                enumFieldErrors.add(error);
//            } else if (error.getCode().equals(ValidatorCodeConstants.BIRTH_YEAR)) {
//                dateFieldErrors.add(error);
//            } else if (error.getCode().equals(ValidatorCodeConstants.PHONE_NUMBER)) {
//                phoneNumberFieldError.add(error);
//            } else if (error.getCode().equals(ValidatorCodeConstants.PASSWORD)) {
//                passwordFieldError.add(error);
//            } else if (error.getCode().equals(ValidatorCodeConstants.RANGE)) {
//                rangeFieldErrors.add(error);
//            } else if (error.getCode().equals(ValidatorCodeConstants.MAX)) {
//                maxFieldError.add(error);
//            } else if (error.getCode().equals(ValidatorCodeConstants.SIZE_WITH_EMOJI)) {
//                sizeWithEmojiFieldError.add(error);
//            } else if (error.getCode().equals(ValidatorCodeConstants.DATE_YYYYMMDD)) {
//                dateFieldErrors.add(error);
//            } else if (error.getCode().equals(ValidatorCodeConstants.COMMON_CODE)) {
//                invalidFieldErrors.add(error);
//            }
//        }
//
//        if (!mandatoryFieldErrors.isEmpty()) {
//            result.put(
//                    ResponseEntityConstants.STATUS_CODE, StatusCodeConstants.MANDATORY_PARAM_ERR);
//            result.put(
//                    ResponseEntityConstants.MANDATORY_PARAM_ERR_KEY,
//                    mandatoryFieldErrors.get(0).getField());
//        } else if (!invalidFieldErrors.isEmpty()) {
//            result.put(ResponseEntityConstants.STATUS_CODE, StatusCodeConstants.INVALID_PARAMETER);
//            result.put(
//                    ResponseEntityConstants.INVALID_PARAMETER_ERR_KEY,
//                    invalidFieldErrors.get(0).getField());
//        } else if (!lengthFieldErrors.isEmpty()) {
//            result.put(ResponseEntityConstants.STATUS_CODE, StatusCodeConstants.PARAM_LENGTH_ERR);
//            result.put(
//                    ResponseEntityConstants.PARAM_LENGTH_ERR_KEY,
//                    lengthFieldErrors.get(0).getField());
//        } else if (!patternFieldErrors.isEmpty()) {
//            result.put(ResponseEntityConstants.STATUS_CODE, StatusCodeConstants.PARAM_PATTERN_ERR);
//            result.put(
//                    ResponseEntityConstants.PARAM_LENGTH_ERR_KEY,
//                    patternFieldErrors.get(0).getField());
//        } else if (!emailFieldErrors.isEmpty()) {
//            result.put(
//                    ResponseEntityConstants.STATUS_CODE, StatusCodeConstants.INVALID_EMAIL_FORMAT);
//            result.put(
//                    ResponseEntityConstants.INVALID_PARAMETER_ERR_KEY,
//                    emailFieldErrors.get(0).getField());
//
//        } else if (!nicknameFieldErrors.isEmpty()) {
//            result.put(
//                    ResponseEntityConstants.STATUS_CODE,
//                    StatusCodeConstants.INVALID_NICKNAME_FORMAT);
//            result.put(
//                    ResponseEntityConstants.INVALID_PARAMETER_ERR_KEY,
//                    nicknameFieldErrors.get(0).getField());
//        } else if (!enumFieldErrors.isEmpty()) {
//            result.put(ResponseEntityConstants.STATUS_CODE, StatusCodeConstants.INVALID_ENUM_VALUE);
//            result.put(
//                    ResponseEntityConstants.INVALID_PARAMETER_ERR_KEY,
//                    enumFieldErrors.get(0).getField());
//        } else if (!dateFieldErrors.isEmpty()) {
//            result.put(
//                    ResponseEntityConstants.STATUS_CODE, StatusCodeConstants.INVALID_DATE_FORMAT);
//            result.put(
//                    ResponseEntityConstants.INVALID_PARAMETER_ERR_KEY,
//                    dateFieldErrors.get(0).getField());
//        } else if (!phoneNumberFieldError.isEmpty()) {
//            result.put(
//                    ResponseEntityConstants.STATUS_CODE,
//                    StatusCodeConstants.INVALID_PHONE_NUMBER_FORMAT);
//            result.put(
//                    ResponseEntityConstants.INVALID_PARAMETER_ERR_KEY,
//                    phoneNumberFieldError.get(0).getField());
//        } else if (!passwordFieldError.isEmpty()) {
//            result.put(
//                    ResponseEntityConstants.STATUS_CODE,
//                    StatusCodeConstants.INVALID_PASSWORD_FORMAT);
//            result.put(
//                    ResponseEntityConstants.INVALID_PARAMETER_ERR_KEY,
//                    passwordFieldError.get(0).getField());
//        } else if (!rangeFieldErrors.isEmpty()) {
//            result.put(ResponseEntityConstants.STATUS_CODE, StatusCodeConstants.PARAM_RANGE_ERR);
//            result.put(
//                    ResponseEntityConstants.INVALID_PARAMETER_ERR_KEY,
//                    rangeFieldErrors.get(0).getField());
//        } else if (!maxFieldError.isEmpty()) {
//            result.put(
//                    ResponseEntityConstants.STATUS_CODE, StatusCodeConstants.PARAM_MAX_VALUE_ERR);
//            result.put(
//                    ResponseEntityConstants.INVALID_PARAMETER_ERR_KEY,
//                    maxFieldError.get(0).getField());
//        } else if (!sizeWithEmojiFieldError.isEmpty()) {
//            result.put(ResponseEntityConstants.STATUS_CODE, StatusCodeConstants.PARAM_LENGTH_ERR);
//            result.put(
//                    ResponseEntityConstants.PARAM_LENGTH_ERR_KEY,
//                    sizeWithEmojiFieldError.get(0).getField());
//        }
//
//        return result;
//    }
//}
