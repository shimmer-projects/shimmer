package io.github.shimmer.core.exception;

import io.github.shimmer.core.data.ApiResult;
import io.github.shimmer.core.data.BizStatus;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * <p>404异常处理， 将spring默认的404错误页面转成返回统一的json格式</p>
 * Created on 2023-12-18 19:56
 *
 * @author yu_haiyang
 */
@Controller
@Hidden
public class NotFoundErrorController implements ErrorController {

    @RequestMapping(value = {"/error"})
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Object error(HttpServletRequest request) {
        return ApiResult.builder().code(BizStatus.ERROR).msg("Page Not Found").build();
    }
}
