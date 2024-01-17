package io.github.shimmer.core.exception;

import io.github.shimmer.core.download.DownloadTransferException;
import io.github.shimmer.core.exception.annotation.ExceptionMapper;
import io.github.shimmer.core.response.StringToApiResult;
import io.github.shimmer.core.response.data.ApiCode;
import io.github.shimmer.core.response.data.ApiResult;
import io.github.shimmer.utils.Utils;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * <p>统一异常处理类</p>
 * Created on 2023-12-18 19:59
 *
 * @author yu_haiyang
 */
@Slf4j
@RestControllerAdvice
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class GlobalExceptionHandlerAdvice {


    @Value("${shimmer.core.print-exception-in-global-advice}")
    private boolean printExceptionInGlobalAdvice;

    private static final String REASON = "%s, 原因: %s";

    /**
     * 将string类型返回值的接口转换成返回json格式的接口
     *
     * @param result 传值异常
     * @return 转换后的结果
     */
    @ExceptionHandler(StringToApiResult.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ApiResult<Object> stringToApiResult(StringToApiResult result) {
        return result.getApiResult();
    }

    @ExceptionHandler(DownloadTransferException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @SneakyThrows
    public ResponseEntity<InputStreamResource> download(DownloadTransferException downloadTransfer) {
        File transferFile = downloadTransfer.getFile();
        //预下载文件
        FileSystemResource file = new FileSystemResource(transferFile);
        String filename = downloadTransfer.getFilename();
        HttpHeaders httpHeaders = new HttpHeaders();
        //指定下载后的文件名等，new String(filename.getBytes("UTF-8"),"ISO8859-1")很重要，不然会下载中文名的时候名字为空
        String contentDisposition = ContentDisposition.attachment().filename(filename, StandardCharsets.UTF_8)
                .build().toString();
        httpHeaders.add(HttpHeaders.CONTENT_DISPOSITION, contentDisposition);
        /* 设置该请求头后,js能够获取content-disposition请求头数据,从中获取文件名称 */
        httpHeaders.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Content-Disposition");
        // 获取文件的content-type
        String contentType = Utils.useFile(transferFile).contentType().finish();
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .contentLength(file.contentLength())
                .contentType(MediaType.parseMediaType(contentType))
                .body(new InputStreamResource(file.getInputStream()));
    }

    /**
     * <p>
     * 参数校验异常
     * 验证  对象类型参数
     * </p>
     */
    @ExceptionHandler(value = {BindException.class, MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ApiResult<Object> handleBindException(BindException ex) {
        List<FieldError> fieldErrors = ex.getFieldErrors();
        List<ValidError> validErrors = new ArrayList<>();
        for (FieldError error : fieldErrors) {
            String field = error.getField();
            Object rejectedValue = error.getRejectedValue();
            String defaultMessage = error.getDefaultMessage();
            ValidError validError = ValidError.builder()
                    .field(field)
                    .value(rejectedValue)
                    .message(defaultMessage)
                    .build();
            validErrors.add(validError);
        }
        return ApiResult
                .builder()
                .code(ApiCode.INVALID.getCode())
                .data(validErrors)
                .desc("请求参数校验失败")
                .build();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ApiResult<Object> handleConstraintViolationException(ConstraintViolationException ex) {
        List<ValidError> validErrors = new ArrayList<>();
        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
        for (ConstraintViolation<?> violation : violations) {
            Object invalidValue = violation.getInvalidValue();
            if (invalidValue instanceof MultipartFile multipartFile) {
                invalidValue = multipartFile.getOriginalFilename();
            }
            String message = violation.getMessage();
            String path = violation.getPropertyPath().toString();
            String field = path.substring(path.lastIndexOf(".") + 1);
            ValidError validError = ValidError.builder()
                    .field(field)
                    .value(invalidValue)
                    .message(message)
                    .build();
            validErrors.add(validError);
        }
        log.warn("以下请求参数非法: {}", validErrors);
        return ApiResult
                .builder()
                .code(ApiCode.INVALID.getCode())
                .data(validErrors)
                .desc("请求参数校验失败")
                .build();
    }

    @ExceptionHandler(IOException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ApiResult<Object> handleFileNotFoundException(IOException ex) {
        String body = "系统找不到指定的文件或路径: " + ex.getMessage().substring(ex.getMessage().indexOf(": ") + 2);
        return ApiResult.fail(String.format(REASON, "文件找不到异常", body));
    }


    /**
     * 算数运算异常
     */
    @ExceptionHandler(ArithmeticException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ApiResult<Object> handleArithmeticException(ArithmeticException ex) {
        return ApiResult.fail(String.format(REASON, "数学运算异常", ex.getMessage()));
    }


    /**
     * 若匹配到了(匹配结果是一个列表，不同的是http方法不同，如：Get、Post等)，
     * 则尝试将请求的http方法与列表的控制器做匹配，若没有对应http方法的控制器，则抛该异常
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ResponseBody
    public ApiResult<Object> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        return ApiResult.fail(String.format(REASON, "请求方法不允许", ex.getMessage()));
    }


    /**
     * 请求与响应媒体类型不一致 异常
     * 然后再对请求头与控制器支持的做比较，比如content-type请求头，若控制器的参数签名包含注解@RequestBody，
     * 但是请求的content-type请求头的值没有包含application/json，那么会抛该异常(当然，不止这种情况会抛这个异常)；
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ResponseBody
    public ApiResult<Object> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException ex) {
        return ApiResult.fail(String.format(REASON, "请求媒体不支持", ex.getMessage()));
    }

    /**
     * 请求参数类型不合法
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiResult<Object> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ApiResult.fail(String.format(REASON, "请求参数不合法", ex.getMessage()));
    }

    /**
     * 首先根据请求Url查找有没有对应的控制器，若没有则会抛该异常，也就是大家非常熟悉的404异常；
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ApiResult<Object> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        return ApiResult.fail(String.format(REASON, "请求路径不存在", ex.getRequestURL()));
    }

    /**
     * 与上面的HttpMediaTypeNotSupportedException举的例子完全相反，
     * 即请求头携带了"content-type: application/json;charset=UTF-8"，
     * 但接收参数却没有添加注解@RequestBody，或者请求体携带的 json 串反序列化成 pojo 的过程中失败了，也会抛该异常；
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResult<Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return ApiResult.fail(String.format(REASON, "请求body参数不可读", ex.getMessage()));
    }

    /**
     * 返回的 pojo 在序列化成 json 过程失败了，那么抛该异常；
     */
    @ExceptionHandler(HttpMessageNotWritableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiResult<Object> handleHttpMessageNotWritableException(HttpMessageNotWritableException ex) {
        return ApiResult.fail(String.format(REASON, "响应序列化失败", ex.getMessage()));
    }

    /**
     * 缺少请求参数。比如定义了参数@RequestParam("licenceId") String licenceId，但发起请求时，未携带该参数，则会抛该异常；
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiResult<Object> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
        return ApiResult.fail(String.format(REASON, "缺少请求参数", ex.getParameterName()));
    }

    /**
     * 未检测到路径参数。比如url为：/licence/{licenceId}，参数签名包含@PathVariable("licenceId")，
     * 当请求的url为/licence，在没有明确定义url为/licence的情况下，会被判定为：缺少路径参数；
     */

    @ExceptionHandler(MissingPathVariableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiResult<Object> handleMissingPathVariableException(MissingPathVariableException ex) {
        return ApiResult.fail(String.format(REASON, "未检测到路径参数", ex.getParameter().getParameterName()));
    }


    /**
     * 参数类型匹配失败。比如：接收参数为Long型，但传入的值确是一个字符串，那么将会出现类型转换失败的情况，这时会抛该异常；
     */
    @ExceptionHandler(TypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiResult<Object> handleTypeMismatchException(TypeMismatchException ex) {
        return ApiResult.fail(String.format(REASON, "参数类型匹配失败", ex.getPropertyName()));
    }

    @ExceptionHandler(BizException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ApiResult<Object> handleBizException(BizException ex) {
        if (printExceptionInGlobalAdvice) {
            log.error("Response:GlobalExceptionHandleAdvice捕获到异常,message=[{}]", ex.getMessage(), ex);
        }
        return ApiResult.fail(ex.getCode(), ex.getMessage());
    }

    /**
     * 处理所有未知的异常,也就是未在上面罗列出来的异常。
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseEntity<ApiResult<Object>> handleGlobalException(Throwable throwable) {
        if (printExceptionInGlobalAdvice) {
            log.error("Response:GlobalExceptionHandleAdvice捕获到异常,message=[{}]", throwable.getMessage(), throwable);
        }
        Class<? extends Throwable> clazz = throwable.getClass();
        ExceptionMapper exceptionMapper = clazz.getAnnotation(ExceptionMapper.class);
        // 1.有@ExceptionMapper注解，直接设置结果的状态
        if (Utils.useNullables(exceptionMapper).isNotNull()) {
            String message = exceptionMapper.msg();
            boolean msgReplaceable = exceptionMapper.msgReplaceable();
            //异常提示可替换+抛出来的异常有自定义的异常信息
            if (msgReplaceable) {
                String throwableMessage = throwable.getMessage();
                if (Utils.useNullables(throwableMessage).isNotNull()) {
                    message = throwableMessage;
                }
            }
            ApiResult<Object> res = ApiResult.fail(exceptionMapper.code(), message);
            return ResponseEntity.status(exceptionMapper.httpStatus()).body(res);

        }

        // 2.原生异常直接包装返回
        ApiResult<Object> res = ApiResult.fail(String.format(REASON, "系统内部异常", throwable.getMessage()));
        return ResponseEntity.internalServerError().body(res);
    }

}

