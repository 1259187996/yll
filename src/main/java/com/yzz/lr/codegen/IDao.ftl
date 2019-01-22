package ${pkg}.common.dal.dao;
import java.util.List;
import java.util.Map;
import ${pkg}.model.${objName};
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ${objName}Mapper {

	public List<${objName}> select(Map<String,Object> map);
	public ${objName} selectById(String id);
	public Object deleteById(String id);
	public Object delete(Map<String,Object> map);
	public Object insert(${objName} ${objNameFirstLower});
	public Object updateByIdSelective(${objName} ${objNameFirstLower});
	public Object updateById(${objName} ${objNameFirstLower});
	public long selectCount(Map<String, Object> map);

}
