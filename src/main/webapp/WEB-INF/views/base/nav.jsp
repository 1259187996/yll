﻿﻿﻿
<div class="left-side sticky-left-side">
	<div class="logo">
		<a onclick="base.route('')"><img
			src="static/image/code_logo_lucency_white.png" width="72px"
			height="72px" alt="flag_logo"></a>
		<p style="color:#fff;text-align:center;margin-left:-20px;margin-top:3px;font-size:16px;">信号旗算法平台</p>
	</div>
	
	<div id="nav" class="left-side-inner">
		<ul class="nav nav-pills nav-stacked custom-nav">
			<li v-show="isCateIn('/index')" id="statistic"><a
				id="statistic_index" onclick="base.route('')"> <i
					class="icon-revenue"></i> <span>首页</span>
			</a></li>
			<li v-show="isCateIn('/count')" id="count" class="menu-list">
				<a><i class="icon-revenue"></i><span>统计</span></a>
				<ul class="sub-menu-list">
					<li v-show="isMenuIn('/count_sea')"><a id="count_sea"
						onclick="base.route('count_sea')">海运出口统计</a></li>
				</ul>
			</li>
			<li id="login_logout"><a onclick="base.route('logout')"><i
				class="icon-icon12"></i> <span>退出系统</span> </a>
			</li>
		</ul>
	</div>
</div>
<script>
	var nav = new Vue({
		el : '#nav',
		data : {
			menuList : [],
			cateList : [],
		},
		created : function() {
			this.search();
		},
		methods : {
			search : function() {
				this.$http.post('/api/account/myModuleList', {}).then(
						function(r) {
							// 提交失败
							if (r.data.code != 200) {
								return;
							}
						//	console.log(JSON.stringify(r.data.data));
							this.menuList = [];
							this.cateList = [];
							for (var i = 0; i < r.data.data.length; i++) {
								if (r.data.data[i].status == "1") {
									if (r.data.data[i].url.indexOf("_") > -1) {
										this.menuList.push(r.data.data[i].url);
									} else {
										this.cateList.push(r.data.data[i].url);
									}
								}
							}
						}, function(r) {
							//失败
						});
			},
			isMenuIn : function(str) {
			    if (str == "/dbAchievementCount") {
				}
				return base.isIn(str, this.menuList);
			},
			isCateIn : function(str) {
				return base.isIn(str, this.cateList);
			},
		}

	});
</script>
<script src="static/js/nav.js"></script>