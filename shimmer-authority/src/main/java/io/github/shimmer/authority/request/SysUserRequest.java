package io.github.shimmer.authority.request;

import io.github.shimmer.core.constant.Sex;
import io.github.shimmer.core.validator.MobileNumber;
import io.github.shimmer.core.validator.VG;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

/**
 * 用户信息请求体
 * Created on 2024-01-12 15:22
 *
 * @author yu_haiyang
 */

@Schema(description = "系统用户信息")
public record SysUserRequest(

        @NotBlank(message = "用户ID不能为空", groups = VG.U.class)
        @Schema(description = "用户ID")
        Long id,

        @NotBlank(message = "用户名称不能为空")
        @Schema(description = "用户名称")
        String nickname,

        @NotBlank(message = "用户登录账号不能为空")
        @Schema(description = "登录账号")
        String account,

        //    @NotBlank(message = "性别不能为空")
        @Schema(description = "性别")
        Sex sex,

        @NotBlank(message = "手机号不能为空")
        @MobileNumber
        @Schema(description = "手机")
        String mobile,

        @NotBlank(message = "邮箱地址不能为空")
        @Email(message = "请填写正确的邮箱地址")
        @Schema(description = "邮箱")
        String email,

        @NotBlank(message = "用户密码不能为空")
        @Schema(description = "登录密码")
        String password,

        @Schema(description = "头像")
        String avatar,

        @Schema(description = "家庭地址")
        String address,

        @Schema(description = "入职时间")
        Long hiredate,

        @Schema(description = "所属部门ID")
        Long organization,

        @Schema(description = "职位")
        List<Long> positions
) {
}
