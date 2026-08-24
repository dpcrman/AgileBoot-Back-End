package com.agileboot.domain.system.notice.command;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author valarchie
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class NoticeUpdateCommand extends NoticeAddCommand {

    @NotNull(message = "公告ID不能为空")
    @Positive(message = "公告ID必须大于0")
    protected Long noticeId;

}

