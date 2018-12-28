package com.yzz.lr.mapper;

import com.yzz.lr.model.SystemConfig;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface SystemConfigMapper {

	public List<SystemConfig> select(Map<String, Object> map);
	public SystemConfig selectById(String id);
	public Object deleteById(String id);
	public Object delete(Map<String, Object> map);
	public Object insert(SystemConfig systemConfig);
	public Object updateByIdSelective(SystemConfig systemConfig);
	public Object updateById(SystemConfig systemConfig);
	public long selectCount(Map<String, Object> map);

}
