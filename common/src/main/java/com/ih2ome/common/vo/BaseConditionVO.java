package com.ih2ome.common.vo;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.ibatis.session.RowBounds;

import java.util.ArrayList;
import java.util.List;


public abstract class BaseConditionVO {
    // 默认常量定义
    public static final int DEFAULT_FIRST = 1;// 默认首页编页码
    public static final int DEFAULT_PAGESIZE = 20;// 默认每页显示的记录数
    public static final int DEFAULT_PageListNum = 7; // 默认显示的页码列表数量

    // 分页相关
    private int pageNum = 0;// 当前页面页码
    private int pageSize = DEFAULT_PAGESIZE;// 每页显示的记录数
    private ArrayList<Integer> pageList;// 分页清单
    private int totalRecords = 0; // 获取记录总数
    private int totalPages = 0; // 页面的总数

    // 搜索相关
//	private String keywords;// 关键字列表
//	private String conditions;// 搜索条件;
//	private String keyword;// 关键字
//	private QueryConditionVO queryConditionVO;// 查询条件

    // 排序相关
//	private String orderField;
//	private String orderDirection;
    private List<KeyValueVO> orderMap;

    // 其他
//	private Integer status;
//	private String type;
//	private Date startdate;
//	private Date enddate;
//	private String place;
//	private String searchDate;


    public int getPageNum() {
        return this.pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }


    public int getStartIndex() {
        int pageNum = this.getPageNum() > 0 ? this.getPageNum() - 1 : 0;
        return pageNum * this.getPageSize();
    }

    public RowBounds getRowBounds() {
        RowBounds rb = new RowBounds(this.getStartIndex(), this.getPageSize());
        return rb;
    }

    public int getPageSize() {
        if (pageSize == 0) {
            setPageSize(DEFAULT_PAGESIZE);
        }
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
    }

    public int getTotalPages() {
        return totalPages = this.countTotalPages();
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    /**
     * 获取页码列表，默认显示 List_PageNum 个页码
     *
     * @return
     */
    public ArrayList<Integer> getPageList() {
        pageList = new ArrayList<Integer>();
        int middle_List_PageNum = (1 + DEFAULT_PageListNum) / 2;
        int startPageNum = 0, endPageNum = 0;

        // 总页数不足 List_PageNum
        if (this.getTotalPages() <= DEFAULT_PageListNum) {
            startPageNum = 1;
            endPageNum = this.getTotalPages();
        } else {
            // 前 List_PageNum
            if (middle_List_PageNum >= this.getPageNum()) {
                startPageNum = 1;
                endPageNum = DEFAULT_PageListNum;
            }
            // 后 List_PageNum
            else if (this.getTotalPages() <= middle_List_PageNum + this.getPageNum()) {
                startPageNum = this.getTotalPages() - DEFAULT_PageListNum + 1;
                endPageNum = this.getTotalPages();
            }
            // 中 List_PageNum
            else {
                startPageNum = this.getPageNum() - middle_List_PageNum + 1;
                endPageNum = this.getPageNum() + middle_List_PageNum - 1;
            }
        }
        for (int tempIndex = startPageNum; tempIndex <= endPageNum; tempIndex++) {
            pageList.add(tempIndex);
        }
        return pageList;
    }

    /**
     * 计算 必须执行
     */
    public void countPageNum() {
        if (this.getPageNum() < DEFAULT_FIRST) {
            this.setPageNum(DEFAULT_FIRST);
        } else if (this.getPageNum() > this.getTotalPages()) {
            this.setPageNum(this.getTotalPages());
        }
    }

    public int countTotalPages() {
        int _totalRecords = this.getTotalRecords();
        int _pageSize = this.getPageSize();
        return (_totalRecords % _pageSize == 0) ? (_totalRecords / _pageSize) : (_totalRecords / _pageSize + 1);
    }

    public boolean isFirstPage() {
        if (this.getPageNum() == 1 && this.getTotalRecords() != 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isLastPage() {
        if (this.getPageNum() == this.getTotalPages() || this.getTotalPages() == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 更新相关参数
     *
     * @return
     */
    public BaseConditionVO resetCount() {
        this.countPageNum();
        return this;
    }

    public String toJSONString(String parm) {
        this.resetCount();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        boolean isFirst = true;
        for (String s : parm.split(",")) {
            if (isFirst) {
                isFirst = false;
            } else {
                stringBuilder.append(",");
            }
            if ("pageNum".equals(s)) {
                stringBuilder.append("\"pageNum\":" + this.getPageNum());
            } else if ("pageSize".equals(s)) {
                stringBuilder.append("\"pageSize\":" + this.getPageSize());
            } else if ("totalRecords".equals(s)) {
                stringBuilder.append("\"totalRecords\":" + this.getTotalRecords());
            } else if ("totalPages".equals(s)) {
                stringBuilder.append("\"totalPages\":" + this.getTotalPages());
            } else if ("pageList".equals(s)) {
                stringBuilder.append("\"pageList\":" + this.getPageList());
            }
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
