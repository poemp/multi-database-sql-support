package org.poem.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *  任务的详细信息
 * </p>
 *
 * @author vinshine
 * @since 2019-10-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TTaskDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 需要执行的sql
     */
    private String execSql;


}
