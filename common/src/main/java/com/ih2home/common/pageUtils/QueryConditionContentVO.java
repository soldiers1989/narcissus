package com.ih2home.common.pageUtils;


public class QueryConditionContentVO {
	// 页面传入参数
	String conditionString;
	// SQL时的字段，可能会有表名前缀
	String conditionColumn;

	// 条件
	QueryConditionOperateEnum conditionOperate;

	// 条件值
	String conditionValue;

	public String getConditionString() {
		return conditionString;
	}

	public void setConditionString(String conditionString) {
		this.conditionString = conditionString;
	}

	public String getConditionColumn() {
		return conditionColumn;
	}

	public void setConditionColumn(String conditionColumn) {
		this.conditionColumn = conditionColumn;
	}

	public QueryConditionOperateEnum getConditionOperate() {
		return conditionOperate;
	}

	public void setConditionOperate(QueryConditionOperateEnum conditionOperate) {
		this.conditionOperate = conditionOperate;
	}

	public String getConditionValue() {
		return conditionValue;
	}

	public void setConditionValue(String conditionValue) {
		this.conditionValue = conditionValue;
	}

}
