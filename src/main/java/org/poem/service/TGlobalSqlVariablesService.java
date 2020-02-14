package org.poem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.poem.entity.TGlobalSqlVariables;
import org.poem.vo.GlobalSqlVariablesVO;

import java.util.Map;

/**
 * @author poem
 */
public interface TGlobalSqlVariablesService extends IService<TGlobalSqlVariables> {


    /**
     * 自定义函数
     * @return
     */
    Map<String, GlobalSqlVariablesVO>  getAllGlobalSqlVariableList();
}
