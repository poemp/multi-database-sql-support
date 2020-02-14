package org.poem.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 全局的函数验证
 *
 * @author sangfor
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TGlobalSqlVariables implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 名字
     */
    private String name;

    /**
     * 说明
     */
    private String content;

    /**
     * 函数的定义名字
     */
    private String functionName;

    /**
     * mysql的实现
     */
    private String mysqlFunction;

    /**
     * postgres函数
     */
    private String postgresFunction;

    /**
     * hive的执行函数
     */
    private String hiveFunction;

    /**
     * hbase 的执行函数
     */
    private String hbaseFunction;
    /**
     * 状态
     */
    private Integer status;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改人
     */
    private String updateUser;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 标识
     */
    private Boolean flag;
}
