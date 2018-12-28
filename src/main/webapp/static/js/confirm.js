/*
 * window.wxc.Pop(popHtml, [type], [options])
 */
(function($) {
	window.wxc = window.wxc || {};
	window.wxc.xcConfirm = function(popHtml, type, options) {
		var btnType = window.wxc.xcConfirm.btnEnum;
		var eventType = window.wxc.xcConfirm.eventEnum;
		var popType = {
			info : {
				title : "取消航线",
				btn : btnType.okcancel
			// btn: parseInt("0011",2)
			},
			error : {
				title : "价格",
				btn : btnType.okcancel
			},
			success : {
				title : "库存",
				btn : btnType.okcancel
			},
		};
		var itype = type ? type instanceof Object ? type : popType[type] || {}
				: {};// 格式化输入的参数:弹窗类型
		var config = $.extend(true, {
			// 属性
			title : "", // 自定义的标题
			icon : "", // 图标
			btn : btnType.ok, // 按钮,默认单按钮
			// 事件
			onOk : $.noop,// 点击确定的按钮回调
			onCancel : $.noop,// 点击取消的按钮回调
			onClose : $.noop
		// 弹窗关闭的回调,返回触发事件
		}, itype, options);

		var $txt = $("<p>").html(popHtml);// 弹窗文本dom
		var $tt = $("<span>").addClass("tt").text(config.title);// 标题
		var icon = config.icon;
		var $icon = icon ? $("<div>").addClass("bigIcon").css(
				"backgroundPosition", icon) : "";
		var btn = config.btn;// 按钮组生成参数

		var popId = creatPopId();// 弹窗索引

		var $box = $("<div>").addClass("xcConfirm");// 弹窗插件容器
		var $layer = $("<div>").addClass("xc_layer");// 遮罩层
		var $popBox = $("<div>").addClass("popBox");// 弹窗盒子
		var $ttBox = $("<div>").addClass("ttBox");// 弹窗顶部区域
		var $txtBox = $("<div>").addClass("txtBox");// 弹窗内容主体区
		var $btnArea = $("<div>").addClass("btnArea");// 按钮区域

		var $ok = $("<a>").addClass("sgBtn").addClass("ok").text("确定");// 确定按钮
		var $cancel = $("<a>").addClass("sgBtn").addClass("cancel").text("取消");// 取消按钮
		var $input = $("<input>").addClass("inputBox");// 输入框
		var $clsBtn = $("<a>").addClass("clsBtn");// 关闭按钮

		// 建立按钮映射关系
		var btns = {
			ok : $ok,
			cancel : $cancel
		};

		init();

		function init() {
			// 处理特殊类型input
			if (popType["input"] === itype) {
				$txt.append($input);
			}

			creatDom();
			bind();
		}

		function creatDom() {
			$popBox.append($ttBox.append($clsBtn).append($tt)).append(
					$txtBox.append($icon).append($txt)).append(
					$btnArea.append(creatBtnGroup(btn)));
			$box.attr("id", popId).append($layer).append($popBox);
			$("body").append($box);
		}

		function bind() {
			// 点击确认按钮
			$ok.click(doOk);

			// 回车键触发确认按钮事件
			$(window).bind("keydown", function(e) {
				if (e.keyCode == 13) {
					if ($("#" + popId).length == 1) {
						doOk();
					}
				}
			});

			// 点击取消按钮
			$cancel.click(doCancel);

			// 点击关闭按钮
			$clsBtn.click(doClose);
		}

		// 确认按钮事件
		function doOk() {
			var $o = $(this);
			var v = $.trim($input.val());
			if ($input.is(":visible"))
				config.onOk(v);
			else
				config.onOk();
			$("#" + popId).remove();
			config.onClose(eventType.ok);
		}

		// 取消按钮事件
		function doCancel() {
			var $o = $(this);
			config.onCancel();
			$("#" + popId).remove();
			config.onClose(eventType.cancel);
		}

		// 关闭按钮事件
		function doClose() {
			$("#" + popId).remove();
			config.onClose(eventType.close);
			$(window).unbind("keydown");
		}

		// 生成按钮组
		function creatBtnGroup(tp) {
			var $bgp = $("<div>").addClass("btnGroup");
			$.each(btns, function(i, n) {
				if (btnType[i] == (tp & btnType[i])) {
					$bgp.append(n);
				}
			});
			return $bgp;
		}

		// 重生popId,防止id重复
		function creatPopId() {
			var i = "pop_" + (new Date()).getTime()
					+ parseInt(Math.random() * 100000);// 弹窗索引
			if ($("#" + i).length > 0) {
				return creatPopId();
			} else {
				return i;
			}
		}
	};

	// 按钮类型
	window.wxc.xcConfirm.btnEnum = {
		ok : parseInt("0001", 2), // 确定按钮
		cancel : parseInt("0010", 2), // 取消按钮
		okcancel : parseInt("0011", 2)
	// 确定&&取消
	};

	// 触发事件类型
	window.wxc.xcConfirm.eventEnum = {
		ok : 1,
		cancel : 2,
		close : 3
	};

	// 弹窗类型
	window.wxc.xcConfirm.typeEnum = {
		info : "info",
		success : "success",
		error : "error",
	};

	// 提示框
	window.ZENG = window.ZENG || {};

	ZENG.dom = {
		getById : function(id) {
			return document.getElementById(id);
		},
		get : function(e) {
			return (typeof (e) == "string") ? document.getElementById(e) : e;
		},
		createElementIn : function(tagName, elem, insertFirst, attrs) {
			var _e = (elem = ZENG.dom.get(elem) || document.body).ownerDocument
					.createElement(tagName || "div"), k;
			if (typeof (attrs) == 'object') {
				for (k in attrs) {
					if (k == "class") {
						_e.className = attrs[k];
					} else if (k == "style") {
						_e.style.cssText = attrs[k];
					} else {
						_e[k] = attrs[k];
					}
				}
			}
			insertFirst ? elem.insertBefore(_e, elem.firstChild) : elem
					.appendChild(_e);
			return _e;
		},
		getStyle : function(el, property) {
			el = ZENG.dom.get(el);
			if (!el || el.nodeType == 9) {
				return null;
			}
			var w3cMode = document.defaultView
					&& document.defaultView.getComputedStyle, computed = !w3cMode ? null
					: document.defaultView.getComputedStyle(el, ''), value = "";
			switch (property) {
			case "float":
				property = w3cMode ? "cssFloat" : "styleFloat";
				break;
			case "opacity":
				if (!w3cMode) {
					var val = 100;
					try {
						val = el.filters['DXImageTransform.Microsoft.Alpha'].opacity;
					} catch (e) {
						try {
							val = el.filters('alpha').opacity;
						} catch (e) {
						}
					}
					return val / 100;
				} else {
					return parseFloat((computed || el.style)[property]);
				}
				break;
			case "backgroundPositionX":
				if (w3cMode) {
					property = "backgroundPosition";
					return ((computed || el.style)[property]).split(" ")[0];
				}
				break;
			case "backgroundPositionY":
				if (w3cMode) {
					property = "backgroundPosition";
					return ((computed || el.style)[property]).split(" ")[1];
				}
				break;
			}
			if (w3cMode) {
				return (computed || el.style)[property];
			} else {
				return (el.currentStyle[property] || el.style[property]);
			}
		},
		setStyle : function(el, properties, value) {
			if (!(el = ZENG.dom.get(el)) || el.nodeType != 1) {
				return false;
			}
			var tmp, bRtn = true, w3cMode = (tmp = document.defaultView)
					&& tmp.getComputedStyle, rexclude = /z-?index|font-?weight|opacity|zoom|line-?height/i;
			if (typeof (properties) == 'string') {
				tmp = properties;
				properties = {};
				properties[tmp] = value;
			}
			for ( var prop in properties) {
				value = properties[prop];
				if (prop == 'float') {
					prop = w3cMode ? "cssFloat" : "styleFloat";
				} else if (prop == 'opacity') {
					if (!w3cMode) {
						prop = 'filter';
						value = value >= 1 ? '' : ('alpha(opacity='
								+ Math.round(value * 100) + ')');
					}
				} else if (prop == 'backgroundPositionX'
						|| prop == 'backgroundPositionY') {
					tmp = prop.slice(-1) == 'X' ? 'Y' : 'X';
					if (w3cMode) {
						var v = ZENG.dom.getStyle(el, "backgroundPosition"
								+ tmp);
						prop = 'backgroundPosition';
						typeof (value) == 'number' && (value = value + 'px');
						value = tmp == 'Y' ? (value + " " + (v || "top"))
								: ((v || 'left') + " " + value);
					}
				}
				if (typeof el.style[prop] != "undefined") {
					el.style[prop] = value
							+ (typeof value === "number"
									&& !rexclude.test(prop) ? 'px' : '');
					bRtn = bRtn && true;
				} else {
					bRtn = bRtn && false;
				}
			}
			return bRtn;
		},
		getScrollTop : function(doc) {
			var _doc = doc || document;
			return Math
					.max(_doc.documentElement.scrollTop, _doc.body.scrollTop);
		},
		getClientHeight : function(doc) {
			var _doc = doc || document;
			return _doc.compatMode == "CSS1Compat" ? _doc.documentElement.clientHeight
					: _doc.body.clientHeight;
		}
	};

	ZENG.string = {
		RegExps : {
			trim : /^\s+|\s+$/g,
			ltrim : /^\s+/,
			rtrim : /\s+$/,
			nl2br : /\n/g,
			s2nb : /[\x20]{2}/g,
			URIencode : /[\x09\x0A\x0D\x20\x21-\x29\x2B\x2C\x2F\x3A-\x3F\x5B-\x5E\x60\x7B-\x7E]/g,
			escHTML : {
				re_amp : /&/g,
				re_lt : /</g,
				re_gt : />/g,
				re_apos : /\x27/g,
				re_quot : /\x22/g
			},
			escString : {
				bsls : /\\/g,
				sls : /\//g,
				nl : /\n/g,
				rt : /\r/g,
				tab : /\t/g
			},
			restXHTML : {
				re_amp : /&amp;/g,
				re_lt : /&lt;/g,
				re_gt : /&gt;/g,
				re_apos : /&(?:apos|#0?39);/g,
				re_quot : /&quot;/g
			},
			write : /\{(\d{1,2})(?:\:([xodQqb]))?\}/g,
			isURL : /^(?:ht|f)tp(?:s)?\:\/\/(?:[\w\-\.]+)\.\w+/i,
			cut : /[\x00-\xFF]/,
			getRealLen : {
				r0 : /[^\x00-\xFF]/g,
				r1 : /[\x00-\xFF]/g
			},
			format : /\{([\d\w\.]+)\}/g
		},
		commonReplace : function(s, p, r) {
			return s.replace(p, r);
		},
		format : function(str) {
			var args = Array.prototype.slice.call(arguments), v;
			str = String(args.shift());
			if (args.length == 1 && typeof (args[0]) == 'object') {
				args = args[0];
			}
			ZENG.string.RegExps.format.lastIndex = 0;
			return str.replace(ZENG.string.RegExps.format, function(m, n) {
				v = ZENG.object.route(args, n);
				return v === undefined ? m : v;
			});
		}
	};

	ZENG.object = {
		routeRE : /([\d\w_]+)/g,
		route : function(obj, path) {
			obj = obj || {};
			path = String(path);
			var r = ZENG.object.routeRE, m;
			r.lastIndex = 0;
			while ((m = r.exec(path)) !== null) {
				obj = obj[m[0]];
				if (obj === undefined || obj === null) {
					break;
				}
			}
			return obj;
		}
	};

	var ua = ZENG.userAgent = {}, agent = navigator.userAgent;
	ua.ie = 9 - ((agent.indexOf('Trident/5.0') > -1) ? 0 : 1)
			- (window.XDomainRequest ? 0 : 1) - (window.XMLHttpRequest ? 0 : 1);

	if (typeof (ZENG.msgbox) == 'undefined') {
		ZENG.msgbox = {};
	}
	ZENG.msgbox._timer = null;
	ZENG.msgbox.loadingAnimationPath = ZENG.msgbox.loadingAnimationPath
			|| ("loading.gif");

	/*
	 * type: 1、消息 4、正确 5、错误 6、加载 timeout:显示时长，毫秒
	 */
	ZENG.msgbox.show = function(msgHtml, type, timeout, opts) {
		if (typeof (opts) == 'number') {
			opts = {
				topPosition : opts
			};
		}
		opts = opts || {};
		var _s = ZENG.msgbox, template = '<span class="zeng_msgbox_layer" style="display:none;z-index:10000;" id="mode_tips_v2"><span class="gtl_ico_{type}"></span>{loadIcon}{msgHtml}<span class="gtl_end"></span></span>', loading = '<span class="gtl_ico_loading"></span>', typeClass = [
				0, 0, 0, 0, "succ", "fail", "clear" ], mBox, tips;
		_s._loadCss && _s._loadCss(opts.cssPath);
		mBox = ZENG.dom.get("q_Msgbox")
				|| ZENG.dom.createElementIn("div", document.body, false, {
					className : "zeng_msgbox_layer_wrap"
				});
		mBox.id = "q_Msgbox";
		mBox.style.display = "";
		mBox.innerHTML = ZENG.string.format(template, {
			type : typeClass[type] || "hits",
			msgHtml : msgHtml || "",
			loadIcon : type == 6 ? loading : ""
		});
		_s._setPosition(mBox, timeout, opts.topPosition);
	};
	ZENG.msgbox._setPosition = function(tips, timeout, topPosition) {
		timeout = timeout;
		var _s = ZENG.msgbox, bt = ZENG.dom.getScrollTop(), ch = ZENG.dom
				.getClientHeight(), t = Math.floor(ch / 2) - 40;
		ZENG.dom
				.setStyle(
						tips,
						"top",
						((document.compatMode == "BackCompat" || ZENG.userAgent.ie < 7) ? bt
								: 0)
								+ ((typeof (topPosition) == "number") ? topPosition
										: t) + "px");
		clearTimeout(_s._timer);
		tips.firstChild.style.display = "";
		timeout && (_s._timer = setTimeout(_s.hide, timeout));
	};
	ZENG.msgbox.hide = function(timeout) {
		var _s = ZENG.msgbox;
		if (timeout) {
			clearTimeout(_s._timer);
			_s._timer = setTimeout(_s._hide, timeout);
		} else {
			_s._hide();
		}
	};
	ZENG.msgbox._hide = function() {
		var _mBox = ZENG.dom.get("q_Msgbox"), _s = ZENG.msgbox;
		clearTimeout(_s._timer);
		if (_mBox) {
			var _tips = _mBox.firstChild;
			ZENG.dom.setStyle(_mBox, "display", "none");
		}
	};

})(jQuery);