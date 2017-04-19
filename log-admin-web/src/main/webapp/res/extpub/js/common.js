if(!window.YYPAY){
	YYPAY = {};
}
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
		
		
		parseDate: function(str,fm){
			var start = 0,ch1 = null,ch2 = null;
	  	    var chReg = /^[yMdhHms]$/,
	  	    	nReg = /^\d$/;
	  	    str = str.replace(/^\s+/g,'').replace(/\s+$/,'');
	  	    fm = fm.replace(/^\s+/g,'').replace(/\s+$/,'');
	  	    if(str.length !== fm.length){
	  	        return null;
	  	    }

	  	    var dtArr = [];
	  	    function convert(start,end){
	  	    	var tmp = parseInt(str.substring(start,end),10);
	                switch(ch1){
	                    case 'y' : dtArr[0] = tmp;break;
	                    case 'M' : dtArr[1] = tmp - 1;break;
	                    case 'd' : dtArr[2] = tmp;break;
	                  	case 'H' : 
	                    case 'h' : dtArr[3] = tmp;break;
	                    case 'm' : dtArr[4] = tmp;break;
	                    case 's' : dtArr[5] = tmp;break;
	                    default:  throw new Error("Invalid Date.");
	                }
	  	    }

	  	    ch1 = fm.charAt(i);
	  	    var flag = true;//有效字符开始标志
	  	    try{
	  	    	for(var i = 0,len = fm.length; i < len; i++){
		  	        ch2 = fm.charAt(i);
		  	        if(chReg.test(ch2) && nReg.test(str.charAt(i))){//有效日期字符
		  	            if(ch2 === ch1){
		  	                continue;
		  	            }else{//新的时间
		  	                ch1 = ch2;
		  	                if(flag){
		  	                	convert(start,i);
		  	                }
		  	                flag = true;
		  	                start  = i;
		  	            }
		  	        }else if(ch2 === str.charAt(i)){
		  	         	if(flag){
		  	         		convert(start,i);
		  	         	}else{
		  	         		start  = i;
		  	         	}
		  	         	flag = false;
		  	        }else{
		  	        	throw new Error('format no match.');
		  	        }
		  	    }
		  	  	convert(start,i);
	  	    }catch(e){
	  	    	window.console && console.error(e);
	  	    	return null;
	  	    }
	  	    
	  	    return new Date(dtArr[0],dtArr[1],dtArr[2],dtArr[3]||0,dtArr[4]||0,dtArr[5]||0,0);
        },
		formatDate : function(date, format) {
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
			var str = format;
			if(str.indexOf('yyyy') > -1) str = str.replace('yyyy', d.y);
			if(str.indexOf('MM') > -1) str = str.replace('MM', cv(d.M));
			if(str.indexOf('dd') > -1) str = str.replace('dd', cv(d.d));
			if(str.indexOf('hh') > -1) str = str.replace('hh', cv(d.h));
			if(str.indexOf('mm') > -1) str = str.replace('mm', cv(d.m));
			if(str.indexOf('ss') > -1) str = str.replace('ss', cv(d.s));
			return str;
		},
		
		/**
		 * 获取上个月最后一天
		 */
		getPreMonthLastDay : function(){ 
		    var current = new Date(); 
		    var firstDay =new Date(current.getFullYear(),current.getMonth(),1); 
		    var minusDate = 1000*60*60*24;
		    return new Date(firstDay.getTime()-minusDate); 
		},
		
		/**
		 * 获取上个月第一天
		 */
		getPreMonthFirstDay : function(){ 
		    var preMonth = $.tools.getPreMonthLastDay(); 
		    return new Date(preMonth.getFullYear(),preMonth.getMonth(),1);
		}
};


/**
 * *********************** 处理所有list页面返回数据为空时的显示 ***************************
 */
$.tools.handleHTML = function($box, html, option) {
	var s = html.trimHTML();
	if(s == '') {
		var td = $box.parent().find('thead').find('td').size() || 0;
		var th = $box.parent().find('thead').find('th').size() || 0;
		var num = td == 0 ? th : td;
		$box.html('<tr><td colspan="' + num + '">没有相关数据！</td></tr>');
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


if(!YYPAY.MsgBox){
	YYPAY.MsgBox = function(cfg){
		this.init(cfg);
	}
	
	YYPAY.MsgBox.prototype = {
		
		defaultCfg: {
			id: null,
			title: '提示',
			animate: 'fade',
			modal: 'modal',
			backdrop: 'modal',//static/false
			type: 'alert',//'confirm','custom'
			content: '',
			remote: false,
			
			//自定义按钮{cls: 'btn-sussuss','text':'文本','dismiss':true,fn: null}
			buttons:null,
			//modal 的默认事件
			hide: null,
			hidden: null,
			show: null,
			shown: null,
			yesFn: null,//确定单击事件
			renderFn: null//DOM创建，未显示
		},
		tplFn: template.compile(
				['<div id="{id}" class="{modal} hide {animate}" data-backdrop="{backdrop}">',
			           '<div class="modal-header">',
				            '<button type="button" class="close" data-dismiss="modal">×</button>',
				            '<h3>{#title}</h3>',
			           '</div>',
			           '<div class="modal-body">',
			           	    '{#bdHtml}',
			           '</div>',
			           '<div class="modal-footer">',
			               '{#ftHtml}',
			           '</div>',
		         '</div>'].join('')),
		
		
		init: function(cfg){
			this.config = $.extend({},this.defaultCfg);
			$.extend(this.config,cfg);
			if(!this.config.id){
				this.config.id = 'modal_' + (Math.random() + "").substr(2);
			}
			//建立一个事件map对象，用于存放自定义按钮的事件
			this.config.btnEvtMap = null;
			
			//TODO 多个弹出导致的栈溢出问题
			this.enforceModalFocusFn = $.fn.modal.Constructor.prototype.enforceFocus;
			$.fn.modal.Constructor.prototype.enforceFocus = function() {};
			
			this.createDom();
			this.initEvent();
			
			this.show();
		},
		/**
		 * @public
		 */
		show: function(){
			this.domEl.modal({
				remote: this.config.remote
			});
			//this.domEl.show();
		},
		
		/**
		 * @public
		 */
		hide: function(){
			this.domEl.modal('hide');
		},
		createFt: function(){
			var _this = this;
			if(this.config.type === 'alert'){
				return '<button class="btn btn-success J_modal_yes" data-dismiss="modal">确定</button>';
			}else if(this.config.type === 'confirm'){
				return '<button class="btn" data-dismiss="modal">取消</button>' + 
	             		'<button class="btn btn-success J_modal_yes">确定</button>';
			}else{
				if(this.config.buttons && $.isArray(this.config.buttons)){
					var html = '' , key = '';
					this.config.btnEvtMap = {};
					
					$.each(this.config.buttons,function(){
						key = '';
						if(this.fn){
							key = "k_" + (Math.random() + "").substr(3);
							_this.config.btnEvtMap[key] = this.fn;
						}
						html += '<button class="btn {cls}" ek="{key}" {dismiss}>{text}</button>'.replace(/\{cls\}/,this.cls)
								.replace(/\{dismiss\}/,this.dismiss === true ? 'data-dismiss="modal"': '')
								.replace(/\{text\}/,this.text)
								.replace(/\{key\}/,key);
					});
					return html;
				}
			}
			return '';
		},
		createDom: function(){
			var html = '';
			if(this.config.modal !== 'modal'){
				this.config.modal = '';
				this.config.backdrop = '';
			}
			html = this.tplFn({
				id: this.config.id,
				modal: this.config.modal,
				animate: this.config.animate,
				title: this.config.title,
				backdrop: this.config.backdrop,
				bdHtml: this.config.content,
				ftHtml: this.createFt()
			});
			$(document.body).append(html);
			this.domEl = $('#' + this.config.id);
		},
		initEvent: function(){
			var _this = this;
			var events = ['show','shown','hide','hidden'];
			$.each(events,function(i,item){
				if(typeof _this.config[item] === 'function'){
					_this.domEl.on(item,_this.config[item]);
				}
			});
			this.domEl.on('hidden',function(){
				_this.domEl.remove();
				$.fn.modal.Constructor.prototype.enforceFocus = _this.enforceModalFocusFn;
			});
			this.domEl.on('shown',function(){
				_this.domEl.find('button:first').focus();
			});
			if(typeof this.config.renderFn === 'function'){
				this.config.renderFn.call(window,this);
			}
			
			this.domEl.on('click','.J_modal_yes',function(){
				var rtn = true;
				if(typeof _this.config.yesFn === 'function'){
					rtn = _this.config.yesFn.call(window,this);
				}
				if(rtn !== false){
					_this.hide();
				}
			});
			//自定义按钮事件
			if(this.config.btnEvtMap){
				for(var key in this.config.btnEvtMap){
					this.domEl.on('click','button[ek="'+key+'"]',function(){
						_this.config.btnEvtMap[key].call(window,_this);
					});
				}
			}
		}
	};
}

//添加快捷调用方式
$.extend(YYPAY.MsgBox,{
	alert: function(content,title,fn){
		new YYPAY.MsgBox({
			title: title ,
			type: 'alert',
			animate: '',
		    content: content,
		    yesFn: fn
		});
	},
	confirm: function(content,title,fn){
		new YYPAY.MsgBox({
			title: title,
			type: 'confirm',
			animate: '',
		    content: content,
		    yesFn: fn
		});
	}
});


if(!YYPAY.XDStorage){
	/*
	 * Copyright 2010 Nicholas C. Zakas. All rights reserved.
	 * BSD Licensed.
	 * modified by wushufeng 2014-07-01
	 */
	YYPAY.XDStorage = function(origin, path){
	    this.origin = origin;
	    this.path = path;
	    this._iframe = null;
	    this._iframeReady = false;
	    this._queue = [];
	    this._requests = {};
	    this._id = 0;
	}
	
	YYPAY.XDStorage.prototype = {
	
		op:{
			WRITE: 'W',
			READ: 'R',
			DEL: 'D',
			CLEAR: 'X'
		},
	    //restore constructor
	    constructor: YYPAY.XDStorage,
	
	    //public interface methods
	
	    init: function(){
	
	        var that = this;
	
	        if (!this._iframe){
	            if (window.postMessage && window.JSON && window.localStorage){
	                this._iframe = document.createElement("iframe");
	                this._iframe.style.cssText = "position:absolute;width:1px;height:1px;left:-9999px;";
	                document.body.appendChild(this._iframe);
	
	                if (window.addEventListener){
	                    this._iframe.addEventListener("load", function(){ that._iframeLoaded(); }, false);
	                    window.addEventListener("message", function(event){ that._handleMessage(event); }, false);
	                } else if (this._iframe.attachEvent){
	                    this._iframe.attachEvent("onload", function(){ that._iframeLoaded(); }, false);
	                    window.attachEvent("onmessage", function(event){ that._handleMessage(event); });
	                }
	            } else {
	                throw new Error("Unsupported browser.");
	            }
	        }
	
	        this._iframe.src = this.origin + this.path;
	
	    },

	    getValue: function(key, callback){
	        this._toSend({
                key: key
            },callback);
	    },
	
	    setValue: function(key,value,callback){

	        this._toSend({
                key: key,
    			op:  this.op.WRITE,
    			value: value
            },callback);	
	    },
	    delValue: function(key,callback){
	        this._toSend({
                key: key,
    			op: this.op.DEL,
    			value: value
            },callback);	
	    },
	    clearValue: function(callback){
	        this._toSend({
    			op: this.op.CLEAR
            },callback);	
	    },
	    //private methods
	    
	    _toSend: function(params,callback){
	    	var data = {
	                request: {
	                    key: params.key,
	                    id: ++this._id,
	                    op: params.op,
	                    value: params.value
	                },
	                callback: callback
            };
	    	if (this._iframeReady){
	            this._sendRequest(data);
	        } else {
	            this._queue.push(data);
	        }   
	
	        if (!this._iframe){
	            this.init();
	        }	
	    },
	
	    _sendRequest: function(data){
	        this._requests[data.request.id] = data;
	        this._iframe.contentWindow.postMessage(JSON.stringify(data.request), this.origin);
	    },
	
	    _iframeLoaded: function(){
	        this._iframeReady = true;
	
	        if (this._queue.length){
	            for (var i=0, len=this._queue.length; i < len; i++){
	                this._sendRequest(this._queue[i]);
	            }
	            this._queue = [];
	        }
	    },
	
	    _handleMessage: function(event){
	        if (event.origin == this.origin){
	            var data = JSON.parse(event.data);
	            this._requests[data.id].callback && this._requests[data.id].callback(data.key, data.value);
	            delete this._requests[data.id];
	        }
	    }
	
	};
}


