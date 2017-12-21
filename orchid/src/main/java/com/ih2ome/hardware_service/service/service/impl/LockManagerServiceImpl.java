package com.ih2ome.hardware_service.service.service.impl;

import com.github.pagehelper.PageHelper;
import com.ih2ome.hardware_service.service.dao.LockManagerDao;
import com.ih2ome.hardware_service.service.enums.HouseStyleEnum;
import com.ih2ome.hardware_service.service.service.LockManagerService;
import com.ih2ome.hardware_service.service.vo.LockManagerVo;
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
    public  List<LockManagerVo> lockList(LockManagerVo lockManagerVo){
        if(lockManagerVo!=null){
            if(lockManagerVo.getPage()!= null && lockManagerVo.getRows() != null){
                PageHelper.startPage(lockManagerVo.getPage(),lockManagerVo.getRows());
            }
            //判断是分散式
            if(lockManagerVo.getType().equals(HouseStyleEnum.DISPERSED.getCode())){
                return lockManagerDao.findDispersedLock(lockManagerVo);
             //判断是集中式
            }else if(lockManagerVo.getType().equals(HouseStyleEnum.CONCENTRAT.getCode())){
                return lockManagerDao.findConcentrateLock(lockManagerVo);
            }else{
                return null;
            }
        }else{
            return null;
        }
    }
}
