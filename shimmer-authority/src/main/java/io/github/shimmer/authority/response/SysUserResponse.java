package io.github.shimmer.authority.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.shimmer.core.constant.Lock;
import io.github.shimmer.core.constant.Sex;
import io.github.shimmer.core.jackson.mask.JsonMask;
import io.github.shimmer.core.jackson.mask.MaskEnum;
import io.github.shimmer.core.jackson.serializer.DigitPhrasing;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 系统用户响应体
 *
 * @author yu_haiyang
 */
@Getter
@Setter
@ToString
public class SysUserResponse {

    @Schema(description = "用户ID")
    private Long id;

    @Schema(description = "用户名称")
    private String nickname;

    @Schema(description = "登录账号")
    private String account;

    @Schema(description = "性别")
    private Sex sex;

    @Schema(description = "手机")
    private String mobile;

    @Schema(description = "邮箱")
    @JsonMask(value = MaskEnum.EMAIL)
    private String email;

    @Schema(description = "登录密码")
    @JsonIgnore
    private String password;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "家庭地址")
    private String address;

    @Schema(description = "入职时间")
    @DigitPhrasing
    private Long hiredate;

    @Schema(description = "创建人")
    private Long creator;

    @Schema(description = "创建时间")
    private Long createTime;

    @Schema(description = "最近修改人")
    private Long modifier;

    @Schema(description = "最近修改日期")
    private Long modifyTime;

    @Schema(description = "最近登录IP")
    private String lastLoginIp;

    @Schema(description = "最近登录时间")
    private Long lastLoginTime;

    @Schema(description = "是否锁定")
    private Lock lockFlag;

    @Schema(description = "用户所属组织")
    private SysOrganizationResponse organization;

    @Schema(description = "用户的职位信息")
    private List<SysPositionResponse> positions;

    @Schema(description = "用户的角色信息")
    private List<SysRoleResponse> roles;
}
