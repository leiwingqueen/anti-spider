// 2012-12-04 动画效果
function animateList(){
	$("ul[data-liffect] li").each(function (i) {
        $(this).attr("style", "-webkit-animation-delay:" + i * 50 + "ms;"
                + "-moz-animation-delay:" + i * 50 + "ms;"
                + "-o-animation-delay:" + i * 50 + "ms;"
                + "animation-delay:" + i * 50 + "ms;");
        if (i == $("ul[data-liffect] li").size() -1) {
            $("ul[data-liffect]").addClass("play")
        }
    })
}

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
})

// 工具系统 自定义表单 2012-08-27 leecanzeng
var toolsApp = {
	confirm_tips:function(msg){
		return confirm(msg);
	},
	tmpl:function(data){ //控件模板
		var wgt_html = '',
			wgt_type = data.wgt_type,
			hint = data.wgt_hint;
		if(hint&&hint !== ''){
			hint = 'rel="tooltip" data-original-title="'+ hint +'" placeholder="'+ hint +'"';
		}	
		if( wgt_type === 'textarea'){
			wgt_html = '<textarea '+ hint +' name="'+ data.wgt_name +'"></textarea>';
			//console.log(hint)
		} else if( wgt_type === 'button'){
			wgt_html = '<button '+ hint +' class="btn" name="'+ data.wgt_name +'">'+ data.wgt_btnText +'</button>';
		} else if( wgt_type === 'text'){
			wgt_html = '<input '+ hint +' type="text" name="'+ data.wgt_name +'" />';
			//console.log(hint)
		} else if(wgt_type === 'checkbox'){ //checkbox
			var wgt_chkText = data.wgt_chkText,
				wgt_name = data.wgt_name;
			$.each( wgt_chkText,function(k,v){ 
				wgt_html += '<label class="checkbox"><input '+ hint +' type="checkbox" name="'+ wgt_name +'" value="' + v.val + '" /> <span class="chk_text">'+ v.text +'</span></label>';
			})
			//console.log(wgt_chkText)
		} else{ //2012-11-07 新增radio
			var wgt_radioText = data.wgt_radioText,
				wgt_name = data.wgt_name;
			$.each( wgt_radioText,function(k,v){ 
				wgt_html += '<label class="radio"><input '+ hint +' type="radio" name="'+ wgt_name +'" value="' + v.val + '" /> <span class="radio_text">'+ v.text +'</span></label>';
			})
		}
		var tmpl = $('<li id="'+ data.id +'" draggable="true" class="item">\
						<label class="item_name">'+ data.wgt_itemTitle +'：</label>\
						<div class="item_content">'
							+ wgt_html +
						'</div>\
						<div class="item_op">\
								<a class="J_editWgt" href="#"><i class="icon-wrench"></i> 编辑</a>\
								<a class="J_delWgt" href="#"><i class="icon-trash"></i> 删除</a>\
						</div>\
					</li>').data('wgt_data',data);
		return tmpl;			
	},
	save:function($box,$popup){ //点击弹窗保存按钮 插入或更新到页面中
		var _self = this;
		//console.log(_self)
		$('#J_ok').bind('click',function(e,update){
			if($("#wgt_form").validationEngine('validate') === false){
				return false;
			} 
			var data = {}, //保存数据
				wgt_type = $('#wgt_type').val(); //隐藏input hidden 类型
			//保存数据
			data.wgt_type = wgt_type; //控件类型
			data.wgt_itemTitle = $('#wgt_itemTitle').val(); //控件的itemTitle 表单项标题
			data.wgt_name = $('#wgt_name').val(); // 控件的name值
			data.wgt_hint = $('#wgt_hint').val(); // 控件的输入提示
			if( wgt_type === 'button'){
				data.wgt_btnText = $('#wgt_btnText').val(); //按钮上面的名称
			} else if( wgt_type === 'checkbox'){
				var chksText = $('.wgt_chkText'), 
					chksValue = $('.wgt_chkValue');
				data.wgt_chkText = [];
				$.each(chksText,function(i){
					var json = {}
					json.text = this.value;
					json.val = chksValue.eq(i).val();
					data.wgt_chkText.push(json);
				})
			} else if(wgt_type === 'radio'){ //2012-11-07 新增radio
				var radioText = $('.wgt_radioText'), 
					radioValue = $('.wgt_radioValue');
				data.wgt_radioText = [];
				$.each(radioText,function(i){
					var json = {}
					json.text = this.value;
					json.val = radioValue.eq(i).val();
					data.wgt_radioText.push(json);
				})
			}
			if( $('#J_ok').hasClass('update')){
				data.id = $('#J_ok').attr('alt');
				_self.update(data);
			} else{
				var d = new Date(),
					tmpId = 'i' + d.getTime();
				data.id = tmpId; //控件id	
				_self.add(data);
			}
			$popup.find('.popup_close').trigger('click'); //关闭弹窗
			e.preventDefault();
		})
	},
	add:function(data){
		var workSpace = $('#drag_area');
		workSpace.append(this.tmpl(data)); //插入的页面中
		$('.itemList :input').tooltip({'placement':'right'});
		$('.itemList').sortable();
	},
	update:function(data){ //点击修改确定按钮 执行的事件 保存更新
		var currentItem = $('#' + data.id),
			wgt_type = data.wgt_type;
		currentItem.data('wgt_data',data);
		currentItem.find('.item_name').text(data.wgt_itemTitle + '：').end().find(':input').attr({'name':data.wgt_name,'placeholder':data.wgt_hint,'data-original-title':data.wgt_hint});
		if( wgt_type === 'button'){ //button
			currentItem.find('button').text(data.wgt_btnText);
		} else if( wgt_type === 'checkbox'){ //checkbox
			var wgt_html = '';
			currentItem.find('.checkbox').remove();
			$.each(data.wgt_chkText,function(k,v){
				wgt_html += '<label class="checkbox"><input rel="tooltip" data-original-title="'+data.wgt_hint+'" type="checkbox" name="'+ data.wgt_name +'" value="' + v.val + '" /> <span class="chk_text">'+ v.text +'</span></label>';
			})
			//console.log(data.wgt_chkText)
			currentItem.find('.item_content').append(wgt_html);
		} else if(wgt_type === 'radio'){ //2012-11-07 新增radio
			var wgt_html = '';
			currentItem.find('.radio').remove();
			$.each(data.wgt_radioText,function(k,v){
				wgt_html += '<label class="radio"><input rel="tooltip" data-original-title="'+data.wgt_hint+'" type="radio" name="'+ data.wgt_name +'" value="' + v.val + '" /> <span class="radio_text">'+ v.text +'</span></label>';
			})
			//console.log(data.wgt_chkText)
			currentItem.find('.item_content').append(wgt_html);
		}
	},
	edit:function($box,$popup){ //弹窗 编辑控件
		var _self = this;
		$('#drag_area').delegate('.J_editWgt','click',function(ev){ 
			var $this = $(this),
				data = $this.closest('li').data('wgt_data'),
				wgt_type = data.wgt_type;
			// console.log('点击编辑链接='+JSON.stringify(data))	
			$('#input_' + wgt_type).trigger('click',[ev,data]);
			return false;
		})
	},
	del:function(){
		var _self = this;
		$('#drag_area').delegate('.J_delWgt','click',function(){ //删除控件
			if( _self.confirm_tips('确认删除？')){
				$(this).closest('li').remove();
			} 
			return false;
		})
	},
	saveForm:function(){
		var items = $('.itemList').find('.item'),
			result = [];
		$.each(items,function(i,item){
			result.push($(this).data('wgt_data'));
		})
		result = JSON.stringify(result);
		return result;
	},
	handleCheckbox:function(){
		$('#popup').delegate('.J_handleCheckbox','click',function(e){
			var $this = $(this),
				root = $this.parent().parent(),
				copy = $this.parent().clone(true).addClass('copyItem').find('.J_delCheckbox').show().end().find('input').val('').end();
			if( $this.hasClass('J_addCheckbox')){ //增加
				root.append(copy).find('.copyItem').last().find('input').eq(0).focus();
			} else{ //删除
				$this.parent().remove();
			}		
			return false;
		})
	},
	popup_open:function($box,$popup){
		var	_self = this,
			type = $('#wgt_type'),
			wgt_typeName = $('#wgt_typeName'),
			wgts = $box.find('.btn');
		wgts.each(function(i){
			//console.log('i='+this.id)
			$(this).bind('click',function(e,ev,data){
				if( this.id === 'input_button'){ //按钮
					type[0].value = 'button';
					wgt_typeName.text('按钮');
					$('#checkbox_view').hide();
					$('#radio_view').hide();
					$('#button_view').show();
				} else if( this.id === 'input_checkbox'){ //复选框
					type[0].value = 'checkbox';
					wgt_typeName.text('复选框');
					$('#button_view').hide();
					$('#radio_view').hide();
					$('#checkbox_view').show();
				} else if(this.id === 'input_radio'){ //单选框 2012-11-07
					type[0].value = 'radio';
					wgt_typeName.text('单选框');
					$('#checkbox_view').hide();
					$('#button_view').hide();
					$('#radio_view').show();
				} else{ 
					if( this.id === 'input_text'){ //text
						wgt_typeName.text('文本框');
						type[0].value = 'text';
					} else{ //textarea
						wgt_typeName.text('文本域');
						type[0].value = 'textarea';
					}
					$('#checkbox_view').hide();
					$('#button_view').hide();
					$('#radio_view').hide();
				}
				if( data !== undefined){ //点击 编辑后 显示的弹窗
					var currentItem = $(ev.target).closest('li').attr('id');
					//console.log('弹窗编辑的id currid='+currentItem)
					$('#J_ok').addClass('update').attr('alt',currentItem);
					//console.log('弹窗编辑的json='+JSON.stringify(data));
					$.each(data,function(i,item){
						var input = $popup.find(':input[name="'+ i +'"]');
						if( i === 'wgt_chkText'){
							input.val(item);
							var html = '';
							$.each(item,function(k,v){
								html += '<div class="controls copyItem">\
											<input type="text" name="wgt_chkText" class="wgt_chkText input-small validate[required]" placeholder="复选框名称" value="'+ v.text +'" autocomplete="off" />\
											<input type="text" name="wgt_chkValue" class="wgt_chkValue input-medium validate[required,custom[onlyLetterNumber]]" placeholder="复选框的值value" value="'+ v.val +'" autocomplete="off" />\
											<a href="#" class="J_handleCheckbox J_addCheckbox">增加</a>\
											<a href="#" class="J_handleCheckbox J_delCheckbox">删除</a>\
										</div>';
							})
							$('#checkbox_view').find('.controls').remove().end().append(html).find('.J_delCheckbox').eq(0).hide().parent().removeClass('copyItem');
						} else if( i === 'wgt_radioText'){ //2012-11-07
							input.val(item);
							var html = '';
							$.each(item,function(k,v){
								html += '<div class="controls copyItem">\
											<input type="text" name="wgt_radioText" class="wgt_radioText input-small validate[required]" placeholder="单选框名称" value="'+ v.text +'" autocomplete="off" />\
											<input type="text" name="wgt_radioValue" class="wgt_radioValue input-medium validate[required,custom[onlyLetterNumber]]" placeholder="单选框的值value" value="'+ v.val +'" autocomplete="off" />\
											<a href="#" class="J_handleCheckbox J_addCheckbox">增加</a>\
											<a href="#" class="J_handleCheckbox J_delCheckbox">删除</a>\
										</div>';
							})
							$('#radio_view').find('.controls').remove().end().append(html).find('.J_delCheckbox').eq(0).hide().parent().removeClass('copyItem');
						} else{
							input.val(item);
						}
					})
				}
				$popup.show().find('#wgt_itemTitle').focus();
				
			})
		})
	},
	popup_close:function($box,$popup){
		$popup.find('.popup_close').bind('click',function(){
			$popup.hide();
			$popup.find(':input').val(''); //清空表单
			if( $popup.find('.copyItem').length != 0){
				$popup.find('.copyItem').remove();
			}
			if( $('#J_ok').hasClass('update')){ //清除更新操作的标志
				$('#J_ok').removeClass('update').attr('alt','');
				//$('#J_ok');
			}
			$('#wgt_form').validationEngine('hideAll')
			return false;
		})
	},
	initLoadData:function(data){
		if(data === null){
			return;
		}
		var _self = this;
		$.each(data,function(i,item){
			_self.add(item);
		})
	},
	init:function(box,popup){
		var $box = $('#' + box),
			$popup = $('#' + popup);
		//显示弹窗
		this.popup_open($box,$popup);
		//关闭弹窗
		this.popup_close($box,$popup);
		//保存数据
		this.save($box,$popup);
		//复制checkbox
		this.handleCheckbox();
		//保存表单
		this.saveForm();
		//删除控件
		this.del();
		//编辑控件
		this.edit($box,$popup);
		//加载数据
		//this.initLoadData(testData);
	}						
}
//拖拽表格
var drawTable = {//暂时没用到
	draw:function($obj){
		var _self = this;
		$obj.bind('mousedown.draw',function(e){
				$(e.target).addClass('on');
					$(this).find('th').bind('mouseover.draw',function(ev){
						var target = $(ev.target);
						if( target.hasClass('on')){
							if( target.next().hasClass('on')){
								target.next().removeClass('on');
							} else{
								target.removeClass('on');
							}
						} else{
							target.addClass('on');
						}
					})
			}).bind('mouseup.draw',function(e){
				$(this).unbind('.draw');
				$(this).find('th').unbind('mouseover.draw').css('cursor','default');
				//弹窗 编辑结果表内容。
				var num = $(this).find('.on').length;
				_self.edit(num);
			})
	},
	edit:function(num){
		var thead = $('#editBoxHead');
		var html = '',
			th = '<th draggable="true"><div class="th"><input class="th_text" type="text" placeholder="请输入结果表名称" /><br /><input class="th_id" type="text" placeholder="请输入id(必须是英文)" /><a title="删除该项" class="J_delTh" href="#"><i class="icon-trash"></i></a></div></th>';
		for( var i = 0; i < num; i++){
			html += th;
		}	
		thead.html(html);
		$('#editBoxHead').sortable();
		$('#J_editBox').slideDown();
	},
	del:function($this){
		if( confirm('确认删除？')){
			$this.closest('th').remove();
		}
	},
	save:function($exitBoxHead){
		var arr = [],
			th = $exitBoxHead.find('th');
		$.each(th,function(i,item){
			var json = {},
				_text = $(item).find('.th_text').val(),
				_id = $(item).find('.th_id').val();
			json.text = _text;
			json.id = _id;
			//json = JSON.stringify(json);
			arr.push(json);
		})
		//console.log(arr);
	},
	reset:function(obj){
		var $obj = $('#'+obj),
			$exitBoxHead = $('#editBoxHead');
		$obj.find('th').removeClass('on');
		//画出表格
		this.draw($obj);
		$exitBoxHead.html('');
		$('#J_editBox').slideUp();
	},
	init:function(obj){
		var _self = this,
			$obj = $('#'+obj),
			$exitBoxHead = $('#editBoxHead');
		//$obj.find('th').removeClass('on');
		//画出表格
		this.draw($obj);
		//删除结果表项
		$exitBoxHead.delegate('.J_delTh','click',function(){
			_self.del($(this));
			return false;
		})
		//保存结果表
		$('#J_saveThead').bind('click',function(){
			_self.save($exitBoxHead);
			return false;
		})
	}
}
//保存结果表
var editThead = {
	saveThead:function(form){
		var	theadCollect = $('.theadCollect'),
			arr = [];
		$.each(theadCollect,function(i,item){
			var json = {},
				arrItem = [],
				items = $(item).find('.theadItem'),
				theadId = $(item).find('.theadId').val();
				theadName = $(item).find('.theadName').val();
			$.each(items,function(i,item){
				var itemJson = {};
				itemJson = {"itemName":$(item).find('.itemName').val(),"itemId":$(item).find('.itemId').val()};
				arrItem.push(itemJson);
			})	
			json[theadId] = arrItem;
			json['theadName'] = theadName;
			json['theadId'] = theadId;
			arr.push(json);
		})	
		return JSON.stringify(arr);
	},
	initLoadData:function(data){
		if(data === null){
			return;
		}
		var _self = this;
		var form = $('#fm_thead');
		if(data.length){
			$.each(data,function(i,items){
				var html = _self.addThead(form,true);
				form.append(html);
				$.each(items,function(k,item){
					if(k === 'theadName'){
						$('.theadName').eq(i).val(item);
						return;
					} else if(k === 'theadId'){
						return;
					}
					$('.theadId').eq(i).val(k);
					$.each(item,function(x,v){
						var html = '';
						if(item.length > 0){
							html += '<div class="theadItem">\
										<input type="text" title="item ID"  value="'+ v.itemId +'" autocomplete="off" placeholder="Item Id" class="itemId input-medium validate[required,custom[exceptChinese]]" name="itemId" />\
										<input type="text" title="item 名称" value="'+ v.itemName +'" autocomplete="off" placeholder="Item 名称" class="itemName input-small validate[required]" name="itemName" />\
										<button class="btn add">增加Item</button>\
										<button class="btn del">删除Item</button>\
									</div>';
							$('.theadItemWrap').eq(i).append(html);
						} 
					})
				})
			})
			$('#fm_thead input').tooltip();
			$('#fm_thead').validationEngine({scroll: false});
		}
	},
	addThead:function(form,edit){
		if( edit === true){ //编辑
			var theadItem = '';
		} else{ //新建
			var theadItem = '<div class="theadItem">\
										<input type="text" title="item ID" autocomplete="off" placeholder="Item ID" class="itemId input-medium validate[required,custom[exceptChinese]]" name="itemId" />\
										<input type="text" title="item 名称" autocomplete="off" placeholder="Item 名称" class="itemName input-small validate[required]" name="itemName" />\
										<button class="btn add">增加Item</button>\
										<button class="btn del">删除Item</button>\
									</div>';
		}			
		var html = '<div class="theadCollect">\
						<div class="btn_delThead"><a class="delThead btn" href="#" title="删除结果表"><i class="icon-trash"></i>删除结果表</a></div>\
						<div class="control-group">\
							<label class="control-label">结果表参数：</label>\
							<div class="controls">\
								<input title="结果表ID" type="text" autocomplete="off" placeholder="结果表 ID（英文）" class="input-medium theadId validate[required,custom[exceptChinese]]" name="theadId" />\
								<input title="结果表名称" type="text" autocomplete="off" placeholder="结果表 名称" class="input-small theadName validate[required]" name="theadName" />\
							</div>\
						</div>\
						<div class="control-group">\
							<label for="wgt_itemTitle" class="control-label">Item参数：</label>\
							<div class="controls theadItemWrap">'
								+ theadItem + 
							'</div>\
						</div>\
					</div>';
		if( edit !== true){
			form.append(html);
			form.find('.theadId').last().focus();
			$('.theadItemWrap').each(function(){
				$(this).find('.theadItem').first().find('.del').hide();
			})
			$('#fm_thead').validationEngine({scroll: false});
		} else{
			return html;
		}
	},
	delThead:function(form){
		var _this = this;
		form.delegate('.delThead','click',function(e){
			if( confirm('确认删除？')){
				$(e.target).closest('.theadCollect').remove();
				var arr = _this.saveThead();
				//console.log(arr)
			}
			return false;
		})
	},
	handleItem:function(form){
		form.delegate('.btn','click',function(e){
			var $this = $(this),
				root = $this.parent().parent(),
				copy = $this.parent().clone(true).addClass('copyItem').find('.del').show().end().find('input').val('').end();
			if( $this.hasClass('add')){ //增加
				$this.parent().after(copy);
				$this.parent().next().find('input').first().focus();
			} else if( $this.hasClass('del')){ //删除
				$this.parent().remove();
			}		
			return false;
		})
		/*$('.theadItemWrap').each(function(){
			$(this).find('.theadItem').first().find('.del').hide();
		})*/
	},
	init:function(){
		var form = $('#fm_thead'),
			_self = this;
		$('#J_saveThead').on('click',function(){
			_self.saveThead(form);
			return false;
		});
		$('#J_addNewThead').on('click',function(){
			_self.addThead(form);
			return false;
		});
		//增加、删除item
		this.handleItem(form);
		//删除结果表
		this.delThead(form);
	}
}
//返回上一页
var goBack = function(){
	window.history.back();
}
//文件上传
function uploadFile(url,inputId,fn){
	var flag = false;
	var upLoadFiles = document.getElementById(inputId).files;//上传文件input框
	var formdata = new FormData();
	for( var i = 0; i < upLoadFiles.length; i++){
		if(upLoadFiles[i].size > 1000000){
			alert('文件大小不能超过1000k！');
			flag = true;
			return;
		} else if(upLoadFiles[i].name.match(/[\u4e00-\u9fa5]/)){
			alert('文件名不能包含中文！');
			flag = true;
			return;
		} else{
			formdata.append("uploadFiles_"+i, upLoadFiles[i]);
		}
	}
	if(flag){
		return;
	}
	var xhr = new XMLHttpRequest();
	xhr.open("POST",url, true);
	xhr.send(formdata);
	xhr.onload = function(e){
		if(xhr.status === 200){
			var json = JSON.parse(xhr.responseText);
			if( json.success === false){
				alert(json.message);
			} else{
				$.sysop.popup.autoTip('上传成功！');
				fn && fn();
			}
		}
	}
}    
//Tree菜单（左侧）
//生成菜单函数
function initMenu(data){
	if( !$('#menuTree')[0]){
		return false;
	}
	var menu = new menuTree();
	menu.bindData(data);
	menu.draw('#menuTree',{unfold:true});
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
			html += '<li><a href="'+this.url+'" target="_blank">'+this.cn_name+'</a></li>';
		})
		$("#dropdown-menu").html(html);
	}		
}
function loadUserInfo(){
	if( !sessionStorage.userName){
		$.getJSON("/common/loadUserInfo.do",function(json){
			if(!json.success) {
				alert(json.message);
				return;
			}
			var user = json.object;
			$("#userInfo").html(user.nick_name);
			sessionStorage.userName = user.nick_name;
		});		
	} else{
		$("#userInfo").html(sessionStorage.userName);
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
			//$('body').append("<div>当前系统部署于:"+ips.join(",")+'</div>')
			$("#serverIps").html("当前系统部署于:"+ips.join(","));
			sessionStorage.ips = ips.join(",");
		});	
	} else{
		//$('body').append("<div>当前系统部署于:"+sessionStorage.ips+'/<div>')
		$("#serverIps").html("当前系统部署于:"+sessionStorage.ips);
	}
}
function closeWindow(){
	if( !confirm('确定退出？')){
		return;
	}
	window.location.href="http://euc.gzdai.com/admin/logout.do";
		/*
	sessionStorage.userName = '';
	sessionStorage.listApps = '';
	sessionStorage.menuData = '';
	window.open('', '_self', '');
	window.close();*/
}
//插入报错 提需求提示
function reportBugs(){
	var w = $(window).width(),cw = $('body > .container').width(),r;
	r = (w - cw) / 2 + 'px';
	//console.log(cw)
	var html = '<div style="right:' + r +'" class="reportBugs" id="J_reportBugs"><span id="serverIps">当前系统部署于:</span> | <a target="_blank" href="https://yypm.com:8443/secure/CreateIssue.jspa?pid=10841&issuetype=1">报Bugs</a> | <a target="_blank" href="https://yypm.com:8443/secure/CreateIssue.jspa?pid=10841&issuetype=2">提需求</a></div>';
	$('body').append(html);
}
// 顶部导航条 2012-12-20
var navMenu = {
	tmpl:function(url,name,pid){
		var x;
		if(url.indexOf('?') === -1){
			x = '?pid=' + pid;
		} else{
			x = '&pid=' + pid;
		}
		var s = '<li><a href="' + url + x + '">' + name + '</a></li>';
		return s;
	},
	toggleNav:function(){
		var trigger = $('#J_sidebarTrigger'), navWrap = $('.nav-hideEnable'), navBar = $('#navBar'), pageMain = navWrap.next();
		var h = navWrap.height();
		trigger.toggle(function(){
			$(this).find('span').addClass('nav-show').attr('title','显示边栏');
			navBar.hide();
			pageMain.addClass('w');
			navWrap.addClass('navhidden').height(h);
			return false;
		},function(){
			$(this).find('span').removeClass('nav-show').attr('title','隐藏边栏');
			navBar.show();
			pageMain.removeClass('w');
			navWrap.removeClass('navhidden').height('auto');
			return false;
		})
	},
	leftMenu:function(){
		var pid = $.sysop.kit.getUrlValue('pid'), parentNav = $('#nav > li'), html = '', x;
		var tmpl = function(data){
			if(data.url.indexOf('?') === -1){
				x = '?pid=' + pid;
			} else{
				x = '&pid=' + pid;
			}
			var s = '<a href="' + data.url + x + '"><i class="icon-hand-right"></i>' + data.name + '</a>';
			return s;
		}
		parentNav.each(function(){
			if($(this).attr('mid') === pid){
				var data = $(this).data('menu');
				$.each(data,function(i,n){
					html += tmpl(n);
				})
				$('#J_favLink').html(html);
				return;
			}
		})
	},
	build:function(data){
		var html = '', menuItem = '', _this = this;
		$.each(data,function(i,n){
			var subMenu = '';
			if(n.children !== null){ // 有子节点
				$.each(n.children,function(j,m){
					subMenu += _this.tmpl(m.url,m.name,m.parentId);
				})
			}
			subMenu = '<ul>' + subMenu + '</ul>';
			$('#nav').append($('<li mid="' + n.value + '"><a href="' + n.url + '">' + n.name + '</a>' + subMenu + '</li>').data('menu',n.children));
		})
		this.toggleNav();
		this.leftMenu();
	}
};
$(function(){
	//初始化
	$.ajaxSetup({
	   error:function(err){
			alert(err.responseText);
	   }
	});
	reportBugs();
	//弹窗初始化
	$.sysop.popup.init();
	//页码选择显示数量提示
	$.sysop.tipHover.init();
	//显示用户名称
	loadUserInfo();
	//显示IP地址
	loadServerIp();
	//快速通道
	listApps();
	//显示日历
	$('.J_calendar').on('click',function(){
		$.calendar({ format:'yyyy-MM-dd HH:mm' });
	})
	//左边树菜单
	if( !!sessionStorage.menuData){
		var data = $.sysop.kit.str2Obj(sessionStorage.menuData);
		// initMenu(data);
		navMenu.build(data);
	} else{
		$.getJSON('/common/menu.do',function(json){//初始化侧栏菜单
			if(!json.success) {
				alert(json.message);
				return;
			}
			var data  = [];
			var menus = json.object;
			var menuFilter = {};
			for(var j=0;j<menus.length;j++){//屏蔽权限管理子子菜单
				if(menus[j].parent_id==0){
					var menu = {};
					menu["name"]=menus[j].name;
					menu["parentId"]=menus[j].parent_id;
					menu["sequence"]=menus[j].sequence;
					menu["url"]=menus[j].url;
					menu["value"]=menus[j].menu_id;
					menu["checked"]=false;
					menu["children"]=[];
					menuFilter[menus[j].menu_id]=menu;
					data.push(menu);
				}else{
					var menu = {};
					menu["name"]=menus[j].name;
					menu["parentId"]=menus[j].parent_id;
					menu["sequence"]=menus[j].sequence;
					menu["url"]=menus[j].url;
					menu["value"]=menus[j].menu_id;
					menu["checked"]=false;
					menu["children"]=null;	
					var children = menuFilter[menus[j].parent_id].children;
					children.push(menu);
				}
			}
			// initMenu(data);
			navMenu.build(data);
			sessionStorage.menuData=$.sysop.kit.obj2Str(data);
		})
	}
	})
$(function(){
	var body = $('body');
	//绑定goBack 返回上一页
	body.delegate('#goBack','click',function(){
		goBack();
		return false;
	})
	/* 全局js */
	// 滚动固定显示
	var $Win = $(window),
		oLeftSideMenu = $('#sec-left');
		//mainTitle = $('#mainTitle');
	var fixPannel = function($pannel,top){
		if( $Win.scrollTop() > 60){
			$pannel.addClass('pannelFixed');
		} else{
			$pannel.removeClass('pannelFixed');
		}
	}
	//回到顶部
	var goBackTop = function(){
		var top = 500;
		var appendDiv = function(){
			var oBackTop = $('<div class="backTop" id="J_backTop"><span>返回顶部</span></div>');
			$('body').append(oBackTop);
			var el = $('#J_backTop');
			var right = 5;
			el.css({'right':right}).click(function(){
				$('html,body').animate({'scrollTop':0},200);
			})
		}
		if( $Win.scrollTop() > top){
			if( !$('#J_backTop')[0]){
				appendDiv();
			}
			$('#J_backTop').fadeIn('slow');
		} else{
			if( $('#J_backTop')[0]){
				$('#J_backTop').fadeOut('slow');
			}
		}
	}
	$Win.scroll(function(){
		goBackTop();
	})
});


/**
 * 控制所有搜索框下的表单默认不作提交
 */
$(function() {
	$('.search_box form').submit(function(){
		return false;
	})
})


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
