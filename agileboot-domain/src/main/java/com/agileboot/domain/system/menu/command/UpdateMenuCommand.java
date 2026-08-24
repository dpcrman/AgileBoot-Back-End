package com.agileboot.domain.system.menu.command;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author valarchie
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UpdateMenuCommand extends AddMenuCommand {

    @PositiveOrZero(message = "菜单ID必须大于等于0")
    private Long menuId;

}

