if(!window.YYPAY){
	YYPAY = {};
}


//日期的格式化
function initTmplDateHelp(){
	// 2012-12-03
	function setFormatDate(time,dateType){ // time = 毫秒数,返回格式化后的日期;dateType = 'date' || 'minute' || '';
		if(time === NaN || time === null || time === ''){
			return '--';
		}
		var splitDate = function(v){
			return (v + '').length === 1 ? '0' + v : v;
		}
		var d = new Date(time), formatDate = '';
			year = d.getFullYear(), month = d.getMonth() + 1, date = d.getDate(),
			h = d.getHours(), m = d.getMinutes(), s = d.getSeconds();
		if(dateType === 'date'){ // 只显示日期 2012-12-12
			formatDate = year + '-' + splitDate(month) + '-' + splitDate(date); 
		} else if(dateType === 'minute'){ //显示到分钟 2012-12-12 18:30
			formatDate = year + '-' + splitDate(month) + '-' + splitDate(date) + ' ' + splitDate(h)  + ':' + splitDate(m); 
		} else{ //参数为空 显示全部 2012-12-12 18:30:12
			formatDate = year + '-' + splitDate(month) + '-' + splitDate(date) + ' ' + splitDate(h)  + ':' + splitDate(m) + ':' + splitDate(s); 
		}
		return formatDate;	
	}
	template.helper('formatDate',function(time,dateType){
		return setFormatDate(time,dateType);
	});

	template.helper('formatDate',function(s){
		return setFormatDate(s);
	});
	template.helper('formatDate2Day',function(s){
		return setFormatDate(s,'date');
	});
	template.helper('formatDate2Minute',function(s){
		return setFormatDate(s,'minute');
	});
}

//生成菜单函数
function initLeftMenu(){
	/* 
	 * 菜单项属性
	  {
        name: "对账展示",
		url: "#",
		menu_id: 20,
		parent_id: 0,
		sequence: 2
	  },{
		  name: "月结消费查询",
		  url: "/query/query_pay_request.jspx",
		  menu_id: 16,
		  parent_id: 15,
		  sequence: 4
	  }
	 */	
	var leftMenu = $('#leftMenu');
	if(!leftMenu.length){
		return;
	}
	var tpl = [
	           '{each list}<ul>',
				    '<li class="title">{$value.name}</li>',
				    '{if $value.children}',
				    	'<li class="subMenu">',
				    		'<ul class="items">',
					    	'{each $value.children as item}',
					    		'<li><a href="{item.url}">{item.name}</a></li>',
					    	'{/each}',
				    		'</ul>',
				    	'</li>',
				    '{/if}',
				'</ul>{/each}'
	           ].join('');
	//从一维数组转为树形结构
	function filterTreeData(listData){
		var treeData = [];
		var isNeed = function( menu,parentId){
			if( menu.parent_id == parentId){
				return true;
			}
			return false;
		};
		$.each( listData,function(){
			if( this.parent_id == 0){
				this.checked = false;
				var currParentId = this.menu_id;
				var children = $.grep(listData,function(menu){
					return isNeed(menu,currParentId);
				}); 
				this.children = children;
				treeData.push(this);
			}
		});
		return treeData;
	}
	
	function createMenu(items){
		items = filterTreeData(items);
		var render = template.compile(tpl);
		leftMenu.html(render({list:items}));
		var path = document.location.pathname;
		var activeLink = leftMenu.find('a[href="'+path+'"]');
		activeLink.addClass("active").closest('.subMenu').show();

		if(!activeLink.length || leftMenu.find('.subMenu').length === 1){
			leftMenu.find('.subMenu').eq(0).show();
		}
	}
	var aData = sessionStorage.getItem("leftMenu");
	if(aData){
		createMenu(JSON.parse(aData));
	}else{
		$.getJSON('/common/menu.do',function(json){
			if(json && json.success){
				createMenu(json.object);
				sessionStorage.setItem("leftMenu",JSON.stringify(json.object));
			}
		});

	}
	
	//左侧菜单展开与收缩
	leftMenu.on('click','.title',function(){
		$(this).siblings(".subMenu").slideToggle('fast');
	});
}

function listApps(){
	if( !sessionStorage.listApps){
		$.getJSON("/common/listUserApp.do",function(json){
			if(!json.success) {
				alert(json.message);
				return;
			}
			var apps = json.object;
			sessionStorage.listApps = JSON.stringify(apps);
			var html = '';
			$("#dropdown-menu").html("");
			$.each(apps,function(){
				html += '<li><a href="'+this.url+'" target="_blank">'+this.cn_name+'</a></li>';
			})
			$("#dropdown-menu").html(html);
		});	
	} else{ //存在
		var html = '';
		var listApps = sessionStorage['listApps'];
		//console.log('本地存储='+listApps)
		var	apps = $.sysop.kit.str2Obj(listApps);
		//$("#dropdown-menu").html("");
		$.each(apps,function(){
			html += '<li><a href="'+this.url+'" >'+this.cn_name+'</a></li>';
		})
		$("#dropdown-menu").html(html);
	}		
}
function loadUserInfo(fn){
	var userInfo = sessionStorage.getItem('userInfo');
	if( !userInfo){
		$.getJSON("/common/loadUserInfo.do",function(json){
			if(!json.success) {
				alert(json.message);
				return;
			}
			$("#userInfo").html(json.object.nick_name);
			sessionStorage.setItem('userInfo',JSON.stringify(json.object));
			fn && fn(json.object);
		});		
	} else{
		userInfo = JSON.parse(userInfo);
		$("#userInfo").html(userInfo.nick_name);
		fn && fn(userInfo);
	}
}
function loadServerIp(){
	if( !sessionStorage.ips){
		$.getJSON("/common/loadServerIp.do",function(json){
			if(!json.success) {
				alert(json.message);
				return;
			}
			var ips = json.object;
			$("#serverIps").html("当前系统部署于:"+ips.join(","));
			sessionStorage.ips = ips.join(",");
		});	
	} else{
		$("#serverIps").html("当前系统部署于:"+sessionStorage.ips);
	}
}
function closeWindow(){
	if( !confirm('确定退出？')){
		return;
	}
	sessionStorage.clear();
	window.open('', '_self', '');
	window.close();
}

function initBackTop(){
	var $win = $(window);
	//回到顶部
	var goBackTop = function(){
		var top = 500;
		var appendDiv = function(){
			var oBackTop = $('<div class="backTop" id="J_backTop"><span>返回顶部</span></div>');
			$('body').append(oBackTop);
			var el = $('#J_backTop');
			var right = 5;
			el.css({'right':right}).click(function(){
				$(document.body).animate({'scrollTop':0},200);
			})
		}
		if( $win.scrollTop() > top){
			if( !$('#J_backTop').length){
				appendDiv();
			}
			$('#J_backTop').fadeIn('slow');
		} else{
			if( $('#J_backTop').length){
				$('#J_backTop').fadeOut('slow');
			}
		}
	}
	$win.scroll(function(){
		goBackTop();
	})
}

//返回上一页
function initGoBack(){
	
	//绑定goBack 返回上一页
	$(document.body).delegate('#goBack','click',function(){
		window.history.back();
		return false;
	});
}

//收藏的快捷菜单
function initFavMenu(userInfo){
	/* 这里利用XDStorage实现了localStorage的跨域存储，但是sessionStorage依旧无解。
	*/
	
	
	
	var FavMenu = {
		sessionKEY: 'favMenu',

		//写到proxy页面localstorage的key
		key: 'fav_' + userInfo.user_name,
		
		remoteStorage :  new YYPAY.XDStorage("http://admin.monthstat.payment.yy.com", "/extpub/proxy.html"),
		tplFn: template.compile(
				'{each list as value}' + 
		  			'<li><a  href="{value[0]}"><span class="close" title="取消收藏">&times;</span>{value[1]}</a></li>' + 
		  		'{/each}'
				),
		init: function(){
			
			
			$(document.body).append(['<div class="favMenuBox">',
				 						'<div id="favMenuBtn" class="favMenuBtn">快捷菜单</div>',
										'<div class="favCnt">',
											'<ul class="dropdown-menu" >',
												'<li class="center">数据加载中...</li>',
											'</ul>',
										'</div>',
									'</div>'].join(''));
			this.loadData();
		},
		createDom: function(data){
			var btnStr = '<button type="button" class="btn btn-warning favLink J_add"><i class="icon-heart icon-white"></i>收藏</button>';
			var found = false,
				url = document.location.href;
			if(data){
				$.each(data,function(i,item){
					if(item[0] === url){
						found = true;
						return false;
					}
				});
			}
			if(found){
				btnStr = '<button type="button" class="btn favLink"><i class="icon-remove"></i>取消收藏</button>';
			}
			$('.page-header').append(btnStr);
			this.initEvent();
		},
		createList: function(){
			data = JSON.parse(sessionStorage.getItem(this.sessionKEY));
			var tplFn = this.tplFn;
			
			
			if(!data || !data.length){
				$('.favMenuBox .dropdown-menu').html('<li class="center">暂无收藏链接</li>');
			}else{
				$('.favMenuBox .dropdown-menu').html(tplFn({list: data}));
			}
		},
		addMenu: function(){
			var _this = this;
			var url = document.location.href;
			var text = $(".page-header h1").text();
			
			var tabEl = null;
			if($(".page-header h1 > span").length){
				text = $(".page-header h1 > span").text();
			}else if($('#navTab').length){
				tabEl = $('#navTab').find('li > a[data-toggle="tab"]');
				if(tabEl.length){
					//do nothing
				}else{
					text  = text + "-" + $('#navTab').find('li.active > a').text();
				}
			}
			
			//为防止数据不一致，每次写操作之前，都重新读取数据。效率有点低下。
			this.remoteStorage.getValue(this.key,function(key,data){
				if(!data){
					data = [];
				}
				data.push([url,text]);
				_this.remoteStorage.setValue(_this.key,data);
				sessionStorage.setItem(_this.sessionKEY,JSON.stringify(data));
			});
		},
		delMenu: function(link){
			var _this = this;
			var url = link || document.location.href;
			
			//为防止数据不一致，每次写操作之前，都重新读取数据。效率有点低下。
			this.remoteStorage.getValue(this.key,function(key,data){
				if(!data){
					data = [];
				}
				$.each(data,function(i,item){
					if(item[0] === url){
						data.splice(i,1);
						return false;
					}
				});
				_this.remoteStorage.setValue(_this.key,data);
				sessionStorage.setItem(_this.sessionKEY,JSON.stringify(data));
			});
		},
//		saveData: function(data){
//			sessionStorage.setItem(this.sessionKEY,JSON.stringify(data));
//			this.remoteStorage.setValue(this.key,data);
//		},
		initEvent: function(){
			var _this = this;
			
			$('.favMenuBox').mouseenter(function(){
				_this.createList();
				
				$(this).addClass('expanded');
			}).mouseleave(function(){
				$(this).removeClass('expanded');
			}).on('click','.close',function(e){
				e.preventDefault();
				e.stopPropagation();
				var el = $(this),
					url = el.parent('a').attr("href");
				if(url === document.location.href){
					$('button.favLink').click();
				}else{
					_this.delMenu(url);
				}
				el.closest('li').slideUp(function(){
					el.closest('li').remove();
				});
			});
			
			$('button.favLink').click(function(e){
				var el = $(this);

				if(el.hasClass('J_add')){
					el.removeClass("btn-warning J_add").html('<i class="icon-remove"></i>取消收藏');
					_this.addMenu();
					
				}else{
					el.addClass("btn-warning J_add").html('<i class="icon-heart icon-white"></i>收藏');
					_this.delMenu();
				}
			});
		},
		loadData: function(){
			var _this = this;
			this.remoteStorage.getValue(this.key,function(key,value){
				//value的数据格式 fav_dw_wushufeng:[[url,text],[url,text],..]
				
				if(!value){
					value = [];
				}
				_this.createDom(value);
				sessionStorage.setItem(_this.sessionKEY,JSON.stringify(value));
			});
			
		}
	};
	FavMenu.init();
}

$(function(){
	//初始化
	$.ajaxSetup({
	   error:function(err){
			alert(err.responseText);
	   }
	});
	
	//template的日期格式化
	initTmplDateHelp();
	
	//弹窗初始化
	$.sysop.popup.init();
	//页码选择显示数量提示
	$.sysop.tipHover.init();
	//显示用户名称
	loadUserInfo(function(userInfo){
		//加载用户的收藏夹
		initFavMenu(userInfo);
	});
	//显示IP地址
	loadServerIp();
	//快速通道
	listApps();
	//显示日历
	$('.J_calendar').on('click',function(){
		var fm = $(this).attr("format");
		if(!fm){
			fm = 'yyyy-MM-dd HH:mm:ss';
		}
		$.calendar({ format:fm });
	});
	//左边树菜单
	initLeftMenu();
	
	//回到顶部
	initBackTop();
	
	//返回上一页
	initGoBack();
});


//格式化工具
var fmUtil = {
	//千分位格式化,保留2个小数点。
	// 例如 1234 -->1,234.00 1123456.56123 ---->1,123,456.56
	thousandFm: function(data){
		var rs = null;
		if(typeof data === "object"){
			rs = $.extend({},data);
			for(var p in rs){
				if(!isNaN(rs[p])){
					rs[p] = this.doThousandFm(rs[p]);
				}
			}
		}else if(typeof data === "number"){
			rs = this.doThousandFm(data);
		}else{
			return data;
		}
		return rs;
	},
	
	doThousandFm: function(num){
		var s = "" + num;
		var reg = /\d{4}(\.|,)/;
		if(!/\./.test(s)){
			s += ".00";
		}else{
			s += "00";
		}
		while(reg.test(s)){
	    	s = s.replace(/(\d)(\d{3}(\.|,))/,"$1,$2");
		}
		return s.replace(/(\.\d{2})(\d+)$/,"$1");
	}
	
};

//form工具
var FormUtil = {
	//设置表单值,json是key-value的对象。多个checkbox的值用逗号分割或数组形式存在
	//例如： {hobbies:['a','b'],...} or {hobbies:'a,b',...}
	setVal: function($form,json){
		var el = null,value = "";
		for(var p in json){
			if(json.hasOwnProperty(p)){
				value = json[p];
				el = $form.find('[name="'+p+'"]');
				if( /^(:?checkbox|radio)$/.test(el.attr("type")) ){
					if(!$.isArray(value)){
						value = value.split(',');
					}
					$.each(value,function(i,val){
						el.filter('[value="'+val+'"]').attr("checked",true);
					});
				} else{
					el.val(value);
				}
			}
		}
	},
	//获取表单值,返回key-value的JSON对象。多个checkbox的值用逗号分割。
	//例如：{hobbies:'a,b',...}
	getVal: function($form){
		var json = {};
		$form.find('input,select,textarea').each(function(i,item){
			var name = item.name,
			value = item.value;
			if(name){
				if(item.type === "checkbox" ||  item.type === "radio"){
					if(typeof json[name] === "undefined"){
						value = [];
						$form.find('[name="'+name+'"]').each(function(){
							if(this.checked){
								value.push(this.value);
							}
						});
						if(!value.length){
							value = "";
						}else{
							value = value.join(',');
						}
						
						json[name] =  value;
					}
					
				}else{
					json[name] =  value;
				}
			}
	    });
		return json;
	}
};
/** ********************************************* 对基础对象的扩展 ************************************************* */
/**
 * 去除String空格的方法
 */
String.prototype.trim = function() {
	return this.replace(/[ ]/g, "");
};

/**
 * 去除HTML String空格的方法
 */
String.prototype.trimHTML = function() {
	return this.replace(/(^\s*)|(\s*$)/g, "");
};

/**
 * 实现java中的replaceAll方法
 */
String.prototype.replaceAll = function(s1, s2) {
	if(s1.length == 1) return this.replace(new RegExp('[' + s1 + ']', 'g'), s2);
	var thiz = this;
	while(thiz.indexOf(s1) != -1) thiz = thiz.replace(s1, s2);
	return thiz;
};
