package org.poem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Maps;
import org.poem.entity.TGlobalSqlVariables;
import org.poem.mapper.TGlobalSqlVariablesMapper;
import org.poem.service.TGlobalSqlVariablesService;
import org.poem.vo.GlobalSqlVariablesVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author poem
 */
@Service
public class TGlobalSqlVariablesServiceImpl  extends ServiceImpl<TGlobalSqlVariablesMapper, TGlobalSqlVariables> implements TGlobalSqlVariablesService {

    /**
     * 转化为vo
     * @param tGlobalSqlVariables
     * @return
     */
    private GlobalSqlVariablesVO getVo(TGlobalSqlVariables tGlobalSqlVariables){
        GlobalSqlVariablesVO variablesVO = new GlobalSqlVariablesVO();
        variablesVO.setName(tGlobalSqlVariables.getName());
        variablesVO.setId(tGlobalSqlVariables.getId());
        variablesVO.setHbaseFunction(tGlobalSqlVariables.getHbaseFunction());
        variablesVO.setContent(tGlobalSqlVariables.getContent());
        variablesVO.setFunctionName(tGlobalSqlVariables.getFunctionName());
        variablesVO.setMysqlFunction(tGlobalSqlVariables.getMysqlFunction());
        variablesVO.setHiveFunction(tGlobalSqlVariables.getHiveFunction());
        variablesVO.setPostgresFunction(tGlobalSqlVariables.getPostgresFunction());
        return variablesVO;
    }
    /**
     *
     * @return
     */
    @Override
    public Map<String, GlobalSqlVariablesVO> getAllGlobalSqlVariableList() {
        List<TGlobalSqlVariables> tGlobalSqlVariablesList = list();
        Map<String, GlobalSqlVariablesVO> sqlVariablesVOMap = Maps.newHashMap();
        for (TGlobalSqlVariables tGlobalSqlVariables : tGlobalSqlVariablesList) {
            sqlVariablesVOMap.put(tGlobalSqlVariables.getFunctionName(), getVo(tGlobalSqlVariables));
        }
        return sqlVariablesVOMap;
    }
}
