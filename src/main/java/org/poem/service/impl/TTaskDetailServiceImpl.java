package org.poem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.poem.entity.TTaskDetail;
import org.poem.mapper.TTaskDetailMapper;
import org.poem.service.TTaskDetailService;
import org.springframework.stereotype.Service;

/**
 * @author poem
 */
@Service
public class TTaskDetailServiceImpl  extends ServiceImpl<TTaskDetailMapper, TTaskDetail>  implements TTaskDetailService {
}
