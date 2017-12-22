package com.ih2ome.hardware_service.service.service.impl;

import com.github.pagehelper.PageHelper;
import com.ih2ome.hardware_service.service.dao.WatermeterManagerMapper;
import com.ih2ome.hardware_service.service.enums.HouseCatalogEnum;
import com.ih2ome.hardware_service.service.service.WatermeterManagerService;
import com.ih2ome.hardware_service.service.vo.WatermeterManagerDetailVO;
import com.ih2ome.hardware_service.service.vo.WatermeterRecordManagerVO;
import com.ih2ome.hardware_service.service.vo.WatermeterWebListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WatermeterManagerServiceImpl implements WatermeterManagerService {
    @Autowired
    private WatermeterManagerMapper watermeterManagerMapper;

    /**
     * 查询水表列表
     * @param watermeterWebListVo
     * @return
     */
    @Override
    public List<WatermeterWebListVo> watermeterWebListVoList(WatermeterWebListVo watermeterWebListVo) {
        if(watermeterWebListVo.getPage()!= null && watermeterWebListVo.getRows() != null){
            PageHelper.startPage(watermeterWebListVo.getPage(),watermeterWebListVo.getRows());
        }
        //分散式
        if(watermeterWebListVo.getType().equals(HouseCatalogEnum.HOUSE_CATALOG_ENUM_CASPAIN.getCode())){
            return watermeterManagerMapper.findHmWatermeterWebListVoList(watermeterWebListVo);
        } else if(watermeterWebListVo.getType().equals(HouseCatalogEnum.HOUSE_CATALOG_ENUM_VOLGA.getCode())){
            //集中式
            return watermeterManagerMapper.findJzWatermeterWebListVoList(watermeterWebListVo);
        }else{
            return null;
        }
    }

    /**
     * 查询水表详情
     *
     * @param uuid
     * @param type
     * @return
     */
    @Override
    public WatermeterManagerDetailVO findWatermeterDetailByUuid(String uuid, String type) {
        WatermeterManagerDetailVO WatermeterManagerDetailVO=null;
        //分散式
        if(type.equals(HouseCatalogEnum.HOUSE_CATALOG_ENUM_CASPAIN.getCode())){
            WatermeterManagerDetailVO= watermeterManagerMapper.selectHmWatermeterDetailByUuid(uuid);
         //集中式
        }else if(type.equals(HouseCatalogEnum.HOUSE_CATALOG_ENUM_VOLGA.getCode())){
            WatermeterManagerDetailVO= watermeterManagerMapper.selectJzWatermeterDetailByUuid(uuid);
        }

        return WatermeterManagerDetailVO;
    }

    /**
     * 查询水表查表记录by时间段+分页
     * @param watermeterRecordManagerVO
     * @return
     */
    @Override
    public List<WatermeterRecordManagerVO> findWatermeterRecordByWatermeterIdAndTime(WatermeterRecordManagerVO watermeterRecordManagerVO) {
        if(watermeterRecordManagerVO.getPage()!= null && watermeterRecordManagerVO.getRows() != null){
            PageHelper.startPage(watermeterRecordManagerVO.getPage(),watermeterRecordManagerVO.getRows());
        }
        List<WatermeterRecordManagerVO> watermeterRecordManagerVOList=watermeterManagerMapper.selectWatermeterRecordByWatermeterIdAndTime(watermeterRecordManagerVO);

        if(!watermeterRecordManagerVOList.isEmpty() || watermeterRecordManagerVOList != null) {
            for (int i = 0; i < watermeterRecordManagerVOList.size(); i++) {
                int meterAmount=0;
                if(i==watermeterRecordManagerVOList.size()-1){
                    meterAmount=0;
                }else {
                    meterAmount = watermeterRecordManagerVOList.get(i+1).getDeviceAmount();
                }
                int dayAmount=watermeterRecordManagerVOList.get(i).getDeviceAmount()-meterAmount;
                watermeterRecordManagerVOList.get(i).setDayAmount(dayAmount);
            }
        }

        return watermeterRecordManagerVOList;
    }
}
