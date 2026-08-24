package com.agileboot.domain.system.menu.command;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author valarchie
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UpdateMenuCommand extends AddMenuCommand {

    @NotNull(message = "菜单ID不能为空")
    @PositiveOrZero
    private Long menuId;

}

