package ${pkg}.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ${pkg}.mapper.${objName}Mapper;
import ${pkg}.service.${objName}Service;


@Service
public class ${objName}ServiceImpl implements ${objName}Service {

	@Autowired
	${objName}Mapper ${objNameFirstLower}Mapper;
	


}
