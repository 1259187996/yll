package ${pkg}.biz.ws;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ${pkg}.service.${objName}Service;
import ${pkg}.common.facade.service.${objName}Facade;



@Component("${objNameFirstLower}Facade")
@WebService(serviceName = "${objNameFirstLower}Facade", endpointInterface = "${pkg}.common.facade.service.${objName}Facade")
public class ${objName}FacadeImpl implements ${objName}Facade {

	@Autowired
	${objName}Service ${objNameFirstLower}Service;
	

}
