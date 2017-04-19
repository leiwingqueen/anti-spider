/**
 * 工具系统公共控件js
 */
$.tools = {
		
		/**
		 * 通过json获取数据的基础方法，一般外部不直接调用
		 * 
		 * 封装方法有：
		 * 		$.tools.initList(url, id, name, value, iniVal);
		 * 		$.tools.initLists(url, id, name, value, iniVal);
		 * 		$.tools.initMap(url, id, iniVal);
		 * 		$.tools.initMaps(url, id, iniVal);
		 */
		init: function(url, callback) {
			$.getJSON(url, function(data) {
				if (!data.success) {
					//$.sysop.popup.autoTip(data.message);
					alert(data.message);
					return;
				}
				callback(data);
			});
		},
		
		/**
		 * 针对返回数据: data.object = [{}, {}, {}, ...]
		 */
		initList: function(url, id, name, value, iniVal, defVal) {
			if(!defVal) defVal = '0';
			this.init(url, function(data) {
				var html = '<option value="' + defVal + '">请选择</option>';
				$.each(data.object, function() {
					html += '<option value="' + this[value] + '">' + this[name] + '</option>';
				});
				var e = $('#' + id);
				e.html(html);
				if(iniVal && iniVal != '') e.val(iniVal);
			});
		},
		
		/**
		 * 针对返回数据: data.object.lists = [{}, {}, {}, ...]
		 */
		initLists: function(url, id, name, value, iniVal, defVal) {
			if(!defVal) defVal = '0';
			this.init(url, function(data) {
				var html = '<option value="' + defVal + '">请选择</option>';
				$.each(data.object.lists, function() {
					html += '<option value="' + this[value] + '">' + this[name] + '</option>';
				});
				var e = $('#' + id);
				e.html(html);
				if(iniVal && iniVal != '') e.val(iniVal);
			});
		},
		
		/**
		 * 针对返回数据: data.object = {key:value, key:value, key:value, ...}
		 */
		initMap: function(url, id, iniVal, defVal) {
			if(!defVal) defVal = '0';
			this.init(url, function(data) {
				var html = '<option value="' + defVal + '">请选择</option>';
				for(var key in data.object) {
					html += '<option value="' + key + '">' + data.object[key] + '</option>';
				}
				var e = $('#' + id);
				e.html(html);
				if(iniVal && iniVal != '') e.val(iniVal);
			});
		},
		
		/**
		 * 针对返回数据: data.object.lists = {key:value, key:value, key:value, ...}
		 */
		initMaps: function(url, id, iniVal, defVal) {
			if(!defVal) defVal = '0';
			this.init(url, function(data) {
				var html = '<option value="' + defVal + '">请选择</option>';
				for(var key in data.object.lists) {
					html += '<option value="' + key + '">' + data.object.lists[key] + '</option>';
				}
				var e = $('#' + id);
				e.html(html);
				if(iniVal && iniVal != '') e.val(iniVal);
			});
		},
		
		/** *********************************** 业务接口 ************************************** */
		
		/** 工具分类 */
		toolsType: function(id, iniVal) {
			this.initLists('/common/listToolsType.do', id, 'name', 'tools_type_id', iniVal);
		},
		
		/** 操作类型 */
		actionType: function(id, iniVal) {
			this.initMap('/common/listActionType.do', id, iniVal);
		},

		/** 发布日志 状态 */
		pubServerStatus: function(id, iniVal) {
			this.initMap('/common/listPubServerStatus.do', id, iniVal, '-1');
		}
};

/**
 * **************************** CMDB URL ********************************************
 */
$.tools.cmdbUrl = 'http://cmdb1.sysop.duowan.com';

/**
 * *********************** 处理所有list页面返回数据为空时的显示 ***************************
 */
$.tools.handleHTML = function($box, html, option) {
	var s = html.trimHTML();
	if(s == '') {
		var td = $box.parent().find('thead').find('td').size() || 0;
		var th = $box.parent().find('thead').find('th').size() || 0;
		var num = td == 0 ? th : td;
		$box.html('<tr><td colspan="' + num + '" align="center">没有相关数据！</td></tr>');
		return;
	}
	$box.html(html);
	// 实现搜索结果的高亮显示
	var opt = option || [];
	if(!(opt instanceof Array)) opt = [opt];
	for(var _i in opt) {
		var o = opt[_i];
		if(!o.className || !o.value || o.value.length == 0) return;
		var len = o.value.length, value = o.value.toLowerCase();
		$box.find('.' + o.className).each(function() {
			var tmp = this.innerHTML.toLowerCase();
			var idxs = [], idx = tmp.indexOf(value);
			while(idx != -1) {
				idxs.push(idx);
				tmp = tmp.substr(idx + len);
				idx = tmp.indexOf(value);
			}
			tmp = this.innerHTML;
			var htmls = [], pIdx = 0;
			for(var _j in idxs) {
				idx = idxs[_j];
				if(pIdx > 0) idx += pIdx + len;
				var start = pIdx == 0 ? 0 : pIdx + len;
				htmls.push(tmp.substring(start, idx));
				htmls.push('<span style="background:#f99">' + tmp.substring(idx, idx + len) + '</span>');
				pIdx = idx;
			}
			htmls.push(tmp.substr(idx + len));
			this.innerHTML = htmls.join('');
		});
	}
};

/**
 * *********************** 显示分页信息的公共方法 *****************************************
 */
$.tools.commonPage = function(box, fn, disR, count, p, pCount) {
	$(box).html('<div class="paginationbar" id="paginationbar"></div>'
			+ '<div class="displayNum" id="displayNum"></div>'
			+ '<div class="clear"></div>');
	// 分页
	var $pag = $(box).find('.paginationbar'), $dis = $(box).find('.displayNum');
	$.sysop.pagination.init($pag, p, pCount, 10, function(num) {
		fn(num, disR);
	});
	// 显示数目
	$.sysop.pagination.displayNum($dis, disR, function() {
		fn(1, this.innerHTML.slice(0, -1));
	});
	// 显示记录数
	var start = (p - 1) * disR + 1;
	var end = p * disR > count ? count : p * disR;
	var txt = '共 ' + count + ' 条记录';
	if(count > 0) {
		if(start == end) txt += ' ( 当前: ' + start + ' )';
		else txt += ' ( 当前: ' + start + '-' + end + ' )';
	}
	$('#count').css({'float':'right'}).html('<div class="pageCount">' + txt + '</div>');
};

/**
 * *********************** 根据参数初始化显示日期 *****************************************
 */
$.tools.initDate = function(option) {
	var o = option || {};
	var milis = 0;
	if(o.second) milis += o.second * 1000;
	if(o.minute) milis += o.minute * 60 * 1000;
	if(o.hour) milis += o.hour * 3600 * 1000;
	if(o.date) milis += o.date * 24 * 3600 * 1000;
	var date = new Date(new Date().getTime() + milis);
	var d = {
		y: date.getFullYear(),	// year
        M: date.getMonth() + 1,	// month
        d: date.getDate(),		// day
        h: date.getHours(),		// hour
        m: date.getMinutes(),	// minute
        s: date.getSeconds()	// second
    };
    var cv = function(v) {
	    return (v + '').length == 1 ? '0' + v : v;
	};
	return d.y + '-' + cv(d.M) + '-' + cv(d.d) + ' ' + cv(d.h) + ':' + cv(d.m);// + ':' + cv(d.s);
};

/**
 * *********************** 业务验证公共方法 *****************************************
 */
$.tools.verify = function(option) {
	var opt = option || {};
	$.getJSON(opt.url, opt.param || '', function(json) {
		if(!json.success) {
			if(opt.fail) opt.fail({code: 0, msg: json.message});
			return;
		}
		var auth = false;
		// 判断 json.object.lists 是否存在，不存在即使用 json.object 作为list返回
		var list = json.object.lists || json.object;
		$.each(list, function(i, item) {
			if(item[opt.key] == opt.value) {
				auth = true;
				return false;
			}
		});
		if(!auth) {
			if(opt.fail) opt.fail({code: 1, msg: 'Unauthorized Error!'});
			return;
		}
		if(opt.success) opt.success();
	});
};

/**
 * *********************** 创建用于执行工具的一次性key的方法 ****************************
 */
$.tools.KeyStorage = {
	stg: window.localStorage,
	init: function(value) {
		this.stg.setItem('temp_run_key', value);
	},
	get: function() {
		var v = this.stg.getItem('temp_run_key');
		this.stg.removeItem('temp_run_key');
		return v;
	}
};
