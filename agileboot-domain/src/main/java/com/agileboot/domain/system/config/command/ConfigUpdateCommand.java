package com.agileboot.domain.system.config.command;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * @author valarchie
 */
@Data
@Schema
public class ConfigUpdateCommand {

    @NotNull(message = "参数ID不能为空")
    @Positive(message = "参数ID必须大于0")
    private Long configId;

    @NotBlank(message = "参数值不能为空")
    @Size(max = 500, message = "参数键值长度不能超过500个字符")
    private String configValue;

}

