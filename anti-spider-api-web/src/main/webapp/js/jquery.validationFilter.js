/**
 * jQuery插件 validationFilter
 * 
 * boolean validationFilter(option)
 * 
 * 参数：option
 * 		{id:id, exp:exp}				-- 指定id，exp：符合filter过滤器名称的表达式，如：'number', 'int', 'int+', ...
 * 		{className:className, exp:exp}	-- 指定元素class值
 * 		{selector:selector, exp:exp}	-- 指定符合jQuery的选择器
 * 		[option[, option, ...]]			-- 由以上三种方式构建的对象所组成的数组
 * 
 * 返回值：boolean
 * 		true	-- 指定的元素的值不合符exp表达式验证规则
 * 		false	-- !true
 * 
 * eg.
 * 		$("#f_1").validationFilter({id:'rack_size', exp:'int'});
 * 		$("#f_1").validationFilter({selector:'input:text', exp:'char'});
 * 		$("#f_1").validationFilter([{className:'idc', exp:'int+'}, {selector:'#use_size, span .total_size', exp:'int0+'}]);
 * 
 * @author liuzifeng
 */
; (function($) {
	
	var filter = {
		list: [
		       {
		    	   name: 'number',
		    	   desc: '必须为数值',
		    	   test: function(value) {return (!isNaN(value)) ? true : false;}
		       }, {
		    	   name: 'int',
		    	   desc: '必须为整数',
		    	   test: function(value) {return (/^-?\d+$/.test(value)) ? true : false;}
		       }, {
		    	   name: 'int+',
		    	   desc: '必须为正整数',
		    	   test: function(value) {return (/^[0-9]*[1-9][0-9]*$/.test(value)) ? true : false;}
		       }, {
		    	   name: 'int0+',
		    	   desc: '必须为非负整数',
		    	   test: function(value) {return (/^\d+$/.test(value)) ? true : false;}
		       }, {
		    	   name: 'float',
		    	   desc: '必须为数值（浮点数）',
		    	   test: function(value) {return (/^\d+(\.\d+)?$/.test(value)) ? true : false;}
		       }, {
		    	   name: 'char',
		    	   desc: '必须以英文字母开头，由英文字符、数字或下划线组成',
		    	   test: function(value) {return (/^[a-zA-Z][_0-9a-zA-Z]*$/.test(value)) ? true : false;}
		       }
		],
		
		get: function(name) {
			for(var i in this.list) {
				var e = this.list[i];
				if(name == e.name) return e;
			}
			return {};
		}
	};
	
	$.fn.validationFilter = function(option) {
		
		var opt = option || [];
		// 统一转换成数组进行处理
		if(!(opt instanceof Array)) opt = [opt];
		
		// 全局过滤（除了textarea以外，其它均对值进行trim()处理）
		$(this).find('[name]').each(function() {
			if(!$(this).is('textarea')) this.value = this.value.replace(/[ ]/g, '');
		});
		
		var result = false, msg = '', thiz = this;
		
		for(var i in opt) {
			var o = opt[i];
			if(!o || !o.exp) continue;
			
			var ele = null;
			if(o.selector) ele = $(thiz).find(o.selector);
			if(o.className) ele = $(thiz).find('.' + o.className);
			if(o.id) ele = $(thiz).find('#' + o.id);
			if(!ele) ele = $(thiz).find('[name]');
			
			var e = filter.get(o.exp);
			ele.each(function() {
				if(!e.test(this.value)) {
					msg += ', 值"' + this.value + '"' + e.desc;
					result = true;
				}
			});
		}
		
		if(msg != '') {
			alert('提交数据不符合要求：\n  ' + msg.substr(2));
		}
		
		return result;
	};
	
})(jQuery);
