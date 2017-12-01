package com.ih2ome.common.base;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**继承自己的MyMapper
 * Created by LF on 2017/4/19.
 */
public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T> {
}