package ${pkg}.webapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.context.annotation.Scope;
import ${pkg}.service.${objName}Service;
@Controller
public class ${objName}Controller{

	private static Logger log = LoggerFactory.getLogger(${objName}Controller.class);
	
	@Autowired
	private ${objName}Service ${objNameFirstLower}Service;


	
}
