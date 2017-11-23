package com.ih2home.utils.common.pageUtils;

import com.ih2home.utils.common.base.BaseConditionVO;

import java.util.ArrayList;
import java.util.StringTokenizer;


/**
 * 查询条件对象
 * 
 * @author Administrator
 *
 */
public class QueryConditionVO {
	// 搜索条件
	String conditions;

	// 搜索条件转换为List
	ArrayList<KeyValueVO> conditionsList;

	QueryConditionLinkVO queryConditionOperateVO;
	ArrayList<QueryConditionContentVO> queryConditionContentVOList;
	ArrayList<QueryConditionVO> subQueryConditionVOList;

	public QueryConditionLinkVO getQueryConditionOperateVO() {
		return queryConditionOperateVO;
	}

	public void setQueryConditionOperateVO(QueryConditionLinkVO queryConditionOperateVO) {
		this.queryConditionOperateVO = queryConditionOperateVO;
	}

	public ArrayList<QueryConditionContentVO> getQueryConditionContentVOList() {
		return queryConditionContentVOList;
	}

	public void setQueryConditionContentVOList(ArrayList<QueryConditionContentVO> queryConditionContentVOList) {
		this.queryConditionContentVOList = queryConditionContentVOList;
	}

	public ArrayList<QueryConditionVO> getSubQueryConditionVOList() {
		return subQueryConditionVOList;
	}

	public void setSubQueryConditionVOList(ArrayList<QueryConditionVO> subQueryConditionVOList) {
		this.subQueryConditionVOList = subQueryConditionVOList;
	}

	public String getConditions() {
		return conditions;
	}

	public void setConditions(String conditions) {
		this.conditions = conditions;
	}

	public ArrayList<KeyValueVO> getConditionsList() {
		return conditionsList;
	}

	public void setConditionsList(ArrayList<KeyValueVO> conditionsList) {
		this.conditionsList = conditionsList;
	}

	public void setBaseConditionVO(BaseConditionVO baseConditionVO) {
		this.setConditions(baseConditionVO.getConditions());
		baseConditionVO.setQueryConditionVO(this);
		if (this.conditionsList == null) {
			this.conditionsList = new ArrayList<KeyValueVO>();
		}
		StringTokenizer st = new StringTokenizer(this.getConditions(), ",");
		while (st.hasMoreTokens()) {
			String condition = st.nextToken();
			int flag = condition.indexOf(":");
			if (flag > 0) {
				KeyValueVO keyValueVO = new KeyValueVO();
				this.conditionsList.add(keyValueVO);
				keyValueVO.setKey(condition.substring(0, flag - 1));
				keyValueVO.setValue(condition.substring(flag) + 1);
			}
		}

	}
}
