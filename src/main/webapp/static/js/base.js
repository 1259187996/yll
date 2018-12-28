//---------------------------------------------------  
// 日期格式化  
// 格式 YYYY/yyyy/YY/yy 表示年份  
// MM/M 月份  
// W/w 星期  
// dd/DD/d/D 日期  
// hh/HH/h/H 时间  
// mm/m 分钟  
// ss/SS/s/S 秒  
//---------------------------------------------------  
Date.prototype.Format= function(formatStr)   
{   
    var str = formatStr;   
    var Week = ['日','一','二','三','四','五','六'];  
  
    str=str.replace(/yyyy|YYYY/,this.getFullYear());   
    str=str.replace(/yy|YY/,(this.getYear() % 100)>9?(this.getYear() % 100).toString():'0' + (this.getYear() % 100));   
  
    str=str.replace(/MM/,this.getMonth()>8?(this.getMonth()+1).toString():'0' + (this.getMonth()+1));   
    str=str.replace(/M/g,this.getMonth());   
  
    str=str.replace(/w|W/g,Week[this.getDay()]);   
  
    str=str.replace(/dd|DD/,this.getDate()>9?this.getDate().toString():'0' + this.getDate());   
    str=str.replace(/d|D/g,this.getDate());   
  
    str=str.replace(/hh|HH/,this.getHours()>9?this.getHours().toString():'0' + this.getHours());   
    str=str.replace(/h|H/g,this.getHours());   
    str=str.replace(/mm/,this.getMinutes()>9?this.getMinutes().toString():'0' + this.getMinutes());   
    str=str.replace(/m/g,this.getMinutes());   
  
    str=str.replace(/ss|SS/,this.getSeconds()>9?this.getSeconds().toString():'0' + this.getSeconds());   
    str=str.replace(/s|S/g,this.getSeconds());   
  
    return str;   
}   
// 字符串格式
String.prototype.Format = function(args) {
    var result = this;
    if (arguments.length > 0) {    
        if (arguments.length == 1 && typeof (args) == "object") {
            for (var key in args) {
                if(args[key]!=undefined){
                    var reg = new RegExp("({" + key + "})", "g");
                    result = result.replace(reg, args[key]);
                }
            }
        }
        else {
            for (var i = 0; i < arguments.length; i++) {
                if (arguments[i] != undefined) {
                    // var reg = new RegExp("({[" + i + "]})",
					// "g");//这个在索引大于9时会有问题，谢谢何以笙箫的指出
　　　　　　　　　　　　var reg= new RegExp("({)" + i + "(})", "g");
                    result = result.replace(reg, arguments[i]);
                }
            }
        }
    }
    return result;
}
jQuery.browser={};(function(){jQuery.browser.msie=false; jQuery.browser.version=0;if(navigator.userAgent.match(/MSIE ([0-9]+)./)){ jQuery.browser.msie=true;jQuery.browser.version=RegExp.$1;}})();
var base = {
	getRootPath : function() {
		// 获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
		var curWwwPath = window.document.location.href;
		// 获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
		var pathName = window.document.location.pathname;
		var pos = curWwwPath.indexOf(pathName);
		// 获取主机地址，如： http://localhost:8083
		var localhostPaht = curWwwPath.substring(0, pos);
		// 获取带"/"的项目名，如：/uimcardprj
		var projectName = pathName.substring(0,
				pathName.substr(1).indexOf('/') + 1);
		return (localhostPaht + projectName);
	},
	// URL跳转
	route : function(url) {
		window.location.href = base.getRootPath() + "/" + url;
	},
	// 分页
	pager:function(cur,total,size,attr){
		
 
		 var split=2;
		 var page = base.getPageNum(total, size);
         if (page <= 1)
             return "";

         var str = "<ul class='pagination aduitFy'>";
         str += "<li class=disabled allNum><a  >共"+total+"条</a></li>";
         if (cur > 1)
         {
             str+="<li><a  " + attr.Format(cur - 1) + "    >上一页</a></li>";
         }
         else
         {
        	 str+= "<li class=disabled><a >上一页</a></li>";
         }
         var start = cur - split;
         var end = cur + split;
         if (start - 1 < 0)
         {
             end = end - (start - 1);
             start = 1;
         }
         if (end > page)
             end = page;
         for(i = 1; i <= page; i++)  // 循环页数
         {
             if (i == start - 1 && start - 1 != 1)
            	 str+="<li><span>...</span></li>";
             if (i == end + 1 && page != end + 1)
            	 str+="<li><span>...</span></li>";
             if (i == cur)
            	 str+="<li class=\"active disabled\"><a >" + cur + "</a></li>";
             else if ((i >= start && i <= end) || i == 1 || i == page)
            	 str+="<li><a " +  attr.Format(i) + "  >" + i + "</a></li>";
              
         }
         if (cur < page)
         {
        	 str+="<li><a    " + attr.Format(cur + 1) + "   >下一页</a></li>";
         }
         else
         {
        	 str+="<li class=disabled><a  >下一页</a></li>";
         }
         return str+"</ul>";
	},
	// 新加坡套打
	pager2:function(cur,total,size,attr){
		
 
		 var split=2;
		 var page = base.getPageNum(total, size);
         if (page <= 1)
             return "";

         var str = "<ul class='pagination aduitFy'>";
         str += "<li class=disabled allNum></li>";
         if (cur > 1)
         {
             str+="<li><a  " + attr.Format(cur - 1) + "    >prev</a></li>";
         }
         else
         {
        	 str+= "<li class=disabled><a >prev</a></li>";
         }
         var start = cur - split;
         var end = cur + split;
         if (start - 1 < 0)
         {
             end = end - (start - 1);
             start = 1;
         }
         if (end > page)
             end = page;
         for(i = 1; i <= page; i++)  // 循环页数
         {
             if (i == start - 1 && start - 1 != 1)
            	 str+="<li><span>...</span></li>";
             if (i == end + 1 && page != end + 1)
            	 str+="<li><span>...</span></li>";
             if (i == cur)
            	 str+="<li class=\"active disabled\"><a >" + cur + "</a></li>";
             else if ((i >= start && i <= end) || i == 1 || i == page)
            	 str+="<li><a " +  attr.Format(i) + "  >" + i + "</a></li>";
              
         }
         if (cur < page)
         {
        	 str+="<li><a    " + attr.Format(cur + 1) + "   >next</a></li>";
         }
         else
         {
        	 str+="<li class=disabled><a  >next</a></li>";
         }
         return str+"</ul>";
	},
	// 获取页数
	getPageNum:function(total,size){
		 
         return  Math.ceil(total / size);
	}
	 ,
	 toDecimal:function(x){
		 var f = parseFloat(x); 
	      if (isNaN(f)) { 
	        return; 
	      } 
	      f = Math.round(x*100)/100; 
	      return f; 
	 },
	  checkPhone:function(phone){ 
// if(!(/^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1})|(1[0-9]{2}))+\d{8})$/.test(phone))){
		    	if(!(/^((1)+\d{10})$/.test(phone))){ 
		        return false; 
		    } 
		    return true;
		},
		isIn:function(value,list)
		{
			if ($.inArray(value, list) >= 0)
				return true;
			return false;
		},
		isEmpty:function(value )
		{
			if(value==null||value==undefined||value=="")
			return true;
			return false;
		},
		// 是否为正整数
		isPositiveInteger:function(value)
		{
			   var re = /^[0-9]+$/  ;
			     return re.test(value)
		},
		  imageCheck:function(target) {
		       var fileSize = 0;         
		       if (!target.files) {     
		         var filePath = target.value;     
		         var fileSystem = new ActiveXObject("Scripting.FileSystemObject");        
		         var file = fileSystem.GetFile (filePath);     
		         fileSize = file.Size;    
		       } else {    
		        fileSize = target.files[0].size;     
		        }   
		        var size = fileSize / 1024;    
		        if(size>5000){  
		        	ZENG.msgbox.show("附件不能大于5M", 5, 1000);
		        
		         target.value="";
		         return
		        }
		        var name=target.value;
		        var fileName = name.substring(name.lastIndexOf(".")+1).toLowerCase();
		        if(fileName !="jpg" && fileName !="jpeg" && fileName !="pdf" && fileName !="png" && fileName !="dwg" && fileName !="gif" ){
		        	ZENG.msgbox.show("请选择图片格式文件上传(jpg,png,gif,dwg,pdf,gif等)！", 5, 1000);
		            target.value="";
		            return
		        }
		      }
	      
    
}