package com.ih2ome.hardware_service.service.service.impl;

import com.github.pagehelper.PageHelper;
import com.ih2ome.hardware_service.service.dao.LockManagerDao;
import com.ih2ome.hardware_service.service.enums.HouseStyleEnum;
import com.ih2ome.hardware_service.service.service.LockManagerService;
import com.ih2ome.hardware_service.service.vo.LockListVo;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

/**
 * @author Sky
 * @create 2017/12/21
 * @email sky.li@ixiaoshuidi.com
 **/
@Service
public class LockManagerServiceImpl implements LockManagerService {

    @Resource
    private LockManagerDao lockManagerDao;

    //门锁列表
    public List<LockListVo> lockList(LockListVo lockListVo) {
        if (lockListVo == null) {
            return null;
        }
        if (lockListVo.isInitPageRows()) {
            PageHelper.startPage(lockListVo.getPage(), lockListVo.getRows());
        }
        //判断是分散式
        if (lockListVo.getType().equals(HouseStyleEnum.DISPERSED.getCode())) {
            return lockManagerDao.findDispersedLock(lockListVo);
            //判断是集中式
        } else if (lockListVo.getType().equals(HouseStyleEnum.CONCENTRAT.getCode())) {
            return lockManagerDao.findConcentrateLock(lockListVo);
        } else {
            return null;
        }
    }
}
