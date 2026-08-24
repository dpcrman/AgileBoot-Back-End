package com.agileboot.domain.system.notice.command;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StringDeserializer;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * @author valarchie
 */
@Data
public class NoticeAddCommand {

    @NotBlank(message = "公告标题不能为空")
    @Size(max = 64, message = "公告标题不能超过64个字符")
    protected String noticeTitle;

    @NotBlank(message = "公告类型不能为空")
    protected String noticeType;

    /**
     * 想要支持富文本的话, 避免Xss过滤的话， 请加上@JsonDeserialize(using = StringDeserializer.class) 注解
     */
    @NotBlank(message = "公告内容不能为空")
    @Size(max = 2000, message = "公告内容不能超过2000个字符")
    @JsonDeserialize(using = StringDeserializer.class)
    protected String noticeContent;

    protected String status;

}

