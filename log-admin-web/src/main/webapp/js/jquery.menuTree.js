// 
// myTree v1.0
// Author: koen_lee@qq.com
// Date: 2011-04-0811111111111
//
function menuTree(){//menuTree类
	this.$BOX=null;
}
menuTree.prototype={
	link:true,//默认有链接
	bindData:function(data){
		this.data=data;
	},
	draw:function(id,param){
		var self=this,unfold=param.unfold,check=param.checkbox,oId=id.slice(1);
		this.fn_delItem = param.fn_delItem;
		this.fn_editItem = param.fn_editItem;
		this.fn_addItem = param.fn_addItem;
		this.fn_showTree = param.fn_showTree;
		this.chlidTree = param.chlidTree;
		this.reloadTree = param.fn_reloadTree;
		//this.saveTips = param.fn_saveTips;
		this.$BOX=$(id);
		if(param.link!=undefined) this.link=param.link;
		if(this.$BOX.length==0) return//找不到ID时返回
		this.addHtml(id,this.data,check,this.link);//生成HTML
		$(id).find('.menu-nav,.item-nav').toggle(function(){
			if(self.clickMenu) self.clickMenu.hide();
			if(!$(this).next().is('.itembox')) return;
			if(self.show) $(this).next('.itembox').css({display:'block'});
			else $(this).next('.itembox').show(200);
			$(this).find('b').addClass('active');
		},function(){
			if(self.clickMenu) self.clickMenu.hide();
			if(!$(this).next().is('.itembox')) return;
			$(this).next('.itembox').hide(100);
			$(this).find('b').removeClass('active');
		});
		$(id).find('a').click(function(){
			if(!self.link) return;
			$(id).find('a').removeClass('active');
			$(this).addClass('active');
			if( !param.newWindow){
				if(this.href) location.href=this.href;
			} else{
				if(this.href) window.open(this.href); 
			}
		});
		$(id).find('span').click(function(){
			var $nav=$(this).parent(),$box=$nav.parent(),id=$nav.attr('id');
			if($(this).attr('class')=='check'){
				//self处理
				$(this).removeClass();
				//child处理
				if($nav.next().is('.itembox')) $nav.next().find('span').removeClass();
				//parent处理
				self.checkParent($nav,oId);
			}else{
				//self处理
				$(this).attr('class','check');
				//child处理
				if($nav.next().is('.itembox')) $nav.next().find('span').attr('class','check');
				//parent处理
				self.checkParent($nav,oId);
			}
			return false;
		});
		//菜单结束
		this.showMenu(id,window.location.toString(),unfold);
		$(id).find('.onClick').click();//预点击
		//可编辑状态
		if(param.editable){this.editable(id);}
		//展示 子树
		if( param.showChildTree){
			this.showChildTree(id);
		}
		//显示隐藏菜单 标志
		this.showHideMenuMark(id);
	},
	showHideMenuMark:function(id){
		$(id).find('a').each(function(i){
			var hide = $(this).attr('hide');
			if( hide == 1){
				$(this).addClass('menuHideMark');
			} 
		})
	},
	checkParent:function($i,oId){
		var ID=$i.attr('id');
		do{
			var $nav=$('#'+ID).parent().children('.item-nav'),$navPar=$('#'+ID).parent().prev();
			if($nav.find('.check').length==$nav.length) $navPar.find('span').attr('class','check');
			else if($nav.find('.check,.checkIn').length>0) $navPar.find('span').attr('class','checkIn');
			else $navPar.find('span').removeClass();
			if($('#'+ID).parent().attr('id')==oId) ID=oId;
			else ID=$navPar.attr('id');
		}
		while(ID!==oId);
	},
	rightClickMenu:function($obj,param){
		$('.rightClickMenu').remove();//删除已存在的
		var $menu=$('<div class="rightClickMenu" onselectstart="return false">').appendTo('body');
		var html=[],self;
		html.push('<ul>');
		for(var i=0;i<param.length;i++) html.push('<li class="'+param[i].className+'">'+param[i].name+'</li>');
		html.push('</ul>');
		$menu.html(html.join(''));
		$menu.find('li').click(function(){
			var index=$(this).index();
			param[index].fn&&param[index].fn.call(self);
		});
		$menu[0].oncontextmenu=function(){return false;};
		$('body').click(function(){$menu.hide();});
		this.clickMenu=$menu;
		//
		$obj.each(function(){
			this.oncontextmenu = function(e){
				var e = e || window.event;
				$menu.css({left:e.clientX,top:e.clientY,display:'block'});
				self=this;//保存点击处
				return false;
			};
		});
	},
	showChildTree:function(id){
		//var fn_saveTips = this.saveTips;
		var fn = this.fn_showTree;
		$(id).find('a').click(function(){
			var menu_id = $(this).attr('menu_id');
			$(id).find('a').removeClass('active');
			$(this).addClass('active');
			$(id).attr('menu_id',menu_id);
			fn(menu_id);
		})
	},
	editable:function(id){
		var fn_delItem = this.fn_delItem;
		var fn_addItem = this.fn_addItem;
		var fn_editItem = this.fn_editItem;
		var fn_reloadTree = this.reloadTree;
		//工具函数
		this.showItem=function(nav){//显示item
			var $box=$(nav).next('.itembox');
			if($box.length&&$box.is(':hidden')) $(nav).click();
		};
		this.hideItem=function(nav){//隐藏item
			var $box=$(nav).next('.itembox');
			if($box.length&&$box.is(':visible')) $(nav).click();
		};
		var $box=$(id),oId=id.slice(1),_this=this;
		//点击编辑
		this.link=false;//禁用链接
		var fn=function(){//a点击处理函数
			var self=this;
			_this.editStatus=true;//记录编辑状态开始
			$(this).removeClass('hover').hide().after('<input value="'+$(this).html()+'" />');
			$box.find('input').select().blur(function(){
				$(self).show().html(this.value==''?'未命名':this.value);
				$(this).remove();
				_this.editStatus=false;//记录编辑状态结束
			}).click(function(){return false;})//阻止点击input冒泡
			.keyup(function(e){//绑定Enter
				if(e.keyCode == '13') $(this).blur();
			});
			return false;//阻止点击a冒泡
		}
		//20120710 update
		//$(id).find('a').addClass('editable').click(editItem); 暂时屏蔽掉 只允许右键编辑
		//右键菜单
		var iNUM=0;
		function addItem(){//新增同级菜单
			var $nav=$(this).parent(),$copy=$nav.clone(true);
			if( $nav.parent().is('.itembox')){
				var parent_id = $nav.parent().find('a').attr('parent_id');
			} else{
				var parent_id = 0;
			}
			var app_id = $(this).attr('app_id');
			$copy.attr('id',$nav.attr('id')+'_sib'+iNUM++).find('a').attr('menu_id',0).attr('value',0).html('新增菜单');
			$copy.find('span').removeClass();
			$(this).parent().parent().append($copy);
			_this.checkParent($nav,oId);
			bindRightClickMenu();
			//alert(parent_id);
			editItem(true,app_id,parent_id);
		};
		function addSubItem(){//新增子级菜单
			if($(this).parents('.itembox').length>=1){ $.sysop.popup.autoTip('最多二级菜单！');return;};//！！！！个性化
			var $nav=$(this).parent(),$copy=$nav.clone(true).removeClass('parent menu-nav');
			var parent_id = $(this).attr('menu_id');
			var app_id = $(this).attr('app_id');
			$copy.attr('id',$nav.attr('id')+'_sub'+iNUM++).find('a').attr('parent_id',parent_id).attr('menu_id',0).attr('value',0).html('新增菜单');
			$copy.find('span').removeClass();
			if($nav.next().is('.itembox')) $nav.next().append($copy);
			else{
				var $box=$('<div class="itembox">');
				$box.append($copy);
				$nav.addClass('parent').click().after($box);
				_this.showItem($nav);
			}
			_this.checkParent($nav,oId);
			bindRightClickMenu();
			//alert(parent_id);
			editItem(true,app_id,parent_id);
		};
		function editItem(flag,aid,pid){//编辑菜单
			var self=this;
			var hideFlag = $(self).attr('hide');
			var isHideHtml = '';
			if( hideFlag == 0){
				isHideHtml = '<p id="J_hideMenu"><label>隐藏菜单：</label><input type="radio" name="hideMenu" value="1" />是 <input checked="checked" name="hideMenu" type="radio" value="0" />否 </p>';
			} else{
				isHideHtml = '<p id="J_hideMenu"><label>隐藏菜单：</label><input checked="checked" type="radio" name="hideMenu" value="1" />是 <input name="hideMenu" type="radio" value="0" />否 </p>';
			}
			
			_this.onSelected=true;//让其保持选中状态
			var tmpHtml = this.innerHTML;
			var tmpData = $(this).attr('url');
			if( tmpHtml == undefined){ //新建节点
				tmpHtml = '菜单名称';
				tmpData = 'URL';
			} else{ //编辑节点

			}
			var html = '<p><label>名称：</label><input class="menuName" type="text" value='+tmpHtml+' /></p><p><label>URL ：</label><input class="menuURL" type="text" value='+tmpData+' />'+isHideHtml+'</p>';
			$.sysop.popup.confirm('编辑菜单',html,function(){
																																															   
																																															   var name = $(this).find('input').eq(0).val();
																																															   var url = $(this).find('input').eq(1).val();
																																															   var hide = $(this).find('input:checked').val();
																																															   if( flag == true){//新建节点
																																															   	var param = 'menu_id=0&parent_id='+pid+'&app_id='+aid+'&name='+name+'&url='+url+'&hide='+hide;
																																															   	//alert(param);
																																															   	 fn_addItem(param);
																																															   	}
																																															   	  else{ //编辑节点
																																															   	  	$(self).html($(this).find('input').eq(0).val());
																																															   	  	$(self).attr('url',$(this).find('input').eq(1).val());
																																															   	  	$(self).attr('hide',$(this).find('input:checked').val());
																																															   	  	 fn_editItem();
																																															   	  	}
																																															   			},false,function(){
																																															   				if( flag == true){
																																															   					fn_reloadTree();
																																															   				} 
																																															   			});
		};
		function delItem(menu_id){//删除菜单
			//弹出确认
			var menu_id = $(this).attr('menu_id');
			$('body').click();//隐藏右键菜单
			if(confirm('确认删除？')) {
				fn_delItem(menu_id);
				return;
			} else{
				return;
			}
			//删除成功 重新读取树 不用进行下面的删dom操作了
			var $nav=$(this).parent(),$navBox=$nav.parent(),$sib=$nav.parent().children('.item-nav').eq(0);
			//删除
			if($nav.next().is('.itembox')) $nav.next().remove();
			$nav.remove();
			//删除后的父级处理
			if($navBox.children('.item-nav').length) _this.checkParent($navBox.children('.item-nav').eq(0),oId);
			else $navBox.remove();
		};
		function reName(){//重命名
			$(this).click();
		};
		function bindRightClickMenu(){//绑定右键菜单
			_this.rightClickMenu($(id).find('a'),[
				{name:'添加同级菜单',fn:addItem},
				{name:'添加子级菜单',fn:addSubItem},
				{className:'line'},
				{name:'编辑菜单',fn:editItem},
				{name:'删除菜单',fn:delItem}
			]);
		};
		//绑定右键
		bindRightClickMenu();
		//可拖动
		this.dragable(id);
	},
	dragable:function(id){
		var oId=id.slice(1),_this=this;
		var $box=$(id),$item=$box.find('.item-nav'),h=$item.height(),pad=5,$ready;
		var $tmp=$('<div id="'+oId+'_item_copy" class="item-nav-copy">').appendTo('body');
		var fn_editItem = this.fn_editItem;
		//
		$item.mousedown(function(){
			var self=this,$ready=$(this),$readyBox=$(this).parent(),onMove=false;
			//移动鼠标拖动
			var mousemoveHandler=function(e){
				if(!onMove) _this.hideItem(self)//隐藏子级菜单
				onMove=true;
				var mTop=e.pageY,mLeft=e.pageX;
				//拖动时的样式处理
				$tmp.show().html($(self).find('a').html());
				$tmp.css({left:mLeft+12,top:mTop});
				$box.find('a').removeClass('editable');
				//
				$box.find('.item-nav').each(function(e){
					if(this==self) return true;//当是自己时退出
					var iTop=$(this).offset().top,iBom=iTop+h;
					//样式处理
					$(this).removeClass('item-into-top item-into-mid item-into-bottom');
					if(mTop>iTop&&mTop<iTop+pad){//在顶部
						$(this).addClass('item-into-top');
					}
					else if(mTop>iTop+pad&&mTop<iBom-pad){//在中部
						$(this).addClass('item-into-mid');
						_this.showItem(this);//展开子菜单
					}
					else if(mTop>iBom-pad&&mTop<iBom){//在底部
						$(this).addClass('item-into-bottom');
					}
				});
				window.getSelection ? window.getSelection().removeAllRanges() : document.selection.empty();
			};
			//放开鼠标拖动
			var mouseupHandler=function(){
				//DOM处理
				var $sorce=$ready.next('.itembox').andSelf();
				//top
				var $tarTop=$box.find('.item-into-top');
				if($tarTop.length){
					
					//保存
					if( $tarTop.parent('.itembox').length){ //如果释放的位置是在itembox里，就子节点里
						//alert($tarTop.parent().prev().find('a').attr('menu_id')+'top+++上');
						if( $sorce.is('.parent')){
							alert('最多两层节点！');

							$tmp.hide();
							$box.find('.item-nav').removeClass('item-into-top item-into-mid item-into-bottom');
							$box.find('a').addClass('editable');
							$(document).unbind("mousemove.tree").unbind("mouseup.tree");
							onMove=false;

							return;
						}
						$tarTop.before($sorce.find('a').attr('parent_id',$tarTop.parent().prev().find('a').attr('menu_id')).end());
					} else{//释放在根上
						//alert($tarTop.find('a').attr('menu_id')+'top---下');
						if( $sorce.is('.parent')){ //如果拖拽的元素带有子节点 子节点pid不变
							$tarTop.before($sorce.find('.parent a').attr('parent_id','0').end());
						} else{
							$tarTop.before($sorce.find('a').attr('parent_id','0').end());
						}
						
					}
					
					fn_editItem();
				} 
				
				//mid
				var $tarMid=$box.find('.item-into-mid');
				if($tarMid.length){
					if( $sorce.is('.parent') || $tarMid.parent('.itembox').length){ //如果拖拽的元素带有子节点 子节点pid不变
							alert('最多两层节点！');

							$tmp.hide();
							$box.find('.item-nav').removeClass('item-into-top item-into-mid item-into-bottom');
							$box.find('a').addClass('editable');
							$(document).unbind("mousemove.tree").unbind("mouseup.tree");
							onMove=false;

							return;
							//$tarBtm.after($sorce.find('.parent a').attr('parent_id',$tarBtm.parent().prev().find('a').attr('menu_id')).end());
						}
					if($tarMid.next('.itembox').length) { //释放的地方是父节点，即含有子节点
						//if( $tarMid.parent('.itembox').length>=1){ alert('最多两层！'); return;}
						//alert($tarMid.find('a').attr('menu_id'));
						$tarMid.next('.itembox').append($sorce.find('a').attr('parent_id',$tarMid.find('a').attr('menu_id')).end());
					}
					else { //没包含子节点 生成一个 div.itembox
						//alert($tarMid.find('a').attr('menu_id'));
						$tarMid.addClass('parent').after($('<div class="itembox"></div>').append($sorce.find('a').attr('parent_id',$tarMid.find('a').attr('menu_id')).end())).click();
					}
					//保存
					fn_editItem();
				};
				//bottom
				var $tarBtm=$box.find('.item-into-bottom');
				if($tarBtm.length) { 
					if( $tarBtm.parent('.itembox').length){ //如果释放的位置是在itembox里，就子节点里
						//alert($tarBtm.parent().prev().find('a').attr('menu_id')+'btm+++上');
						if( $sorce.is('.parent')){ //如果拖拽的元素带有子节点 子节点pid不变
							alert('最多两层节点！');

							$tmp.hide();
							$box.find('.item-nav').removeClass('item-into-top item-into-mid item-into-bottom');
							$box.find('a').addClass('editable');
							$(document).unbind("mousemove.tree").unbind("mouseup.tree");
							onMove=false;

							return;
							//$tarBtm.after($sorce.find('.parent a').attr('parent_id',$tarBtm.parent().prev().find('a').attr('menu_id')).end());
						} else{ //拖拽的是单节点
							//alert('拖拽的是单节点');
							$tarBtm.after($sorce.find('a').attr('parent_id',$tarBtm.parent().prev().find('a').attr('menu_id')).end());
						}
					} //else if( $tarBtm.is(':last')){
						//alert(1);
					//} 
					else{ //放到根节点下
						//alert($tarBtm.find('a').attr('menu_id')+'btm---下');
						if( $sorce.is('.parent')){ //如果拖拽的元素带有子节点 子节点pid不变
							$tarBtm.after($sorce.find('.parent a').attr('parent_id','0').end());
						} else{
							$tarBtm.after($sorce.find('a').attr('parent_id','0').end());
						}
					}
					/*if($tarBtm.next('.itembox').length) $tarBtm.next('.itembox').after($sorce);
					else $tarBtm.after($sorce);*/
					//保存
					fn_editItem();
				};
				//后期DOM处理
				_this.checkParent($ready,oId);//移动后的树检测
				if($readyBox.children('.item-nav').length) _this.checkParent($readyBox.children('.item-nav').eq(0),oId)//之前的树检测
				else $readyBox.remove();
				//样式处理
				$tmp.hide();
				$box.find('.item-nav').removeClass('item-into-top item-into-mid item-into-bottom');
				$box.find('a').addClass('editable');
				$(document).unbind("mousemove.tree").unbind("mouseup.tree");
				onMove=false;
			};
			if(_this.editStatus) return;//当是编辑状态时不可拖动
			$(document).bind('mousemove.tree',function(e){mousemoveHandler(e);});
			$(document).bind('mouseup.tree',function(){ mouseupHandler();});
		});
		//alert($('#menuTree_1').offset().top);
	},
	addHtml:function(id,data,check,link){//生成HTML
		var $box=$(id),box=$box[0],len=data.length,str=[],id=$box.attr('id');
		for(var i=0;i<len;i++){
			var hasChild=data[i].children&&data[i].children.length;
			var attr = '',title = '';
			title = data[i].uri ? 'title='+data[i].uri : '';
			$.each(data[i],function(i,item){
				if( i == 'children' || i == 'last_modify' || i == 'uri' || i == 'operator' || i == 'name' || i == 'sequence'){
					return;
				}
				attr += i + '=' + item + ' ';
			})
			str.push('<div class="item-nav'+(hasChild?' parent':'')+'" id="'+id+'_'+i+'"><b></b>'+(check?'<span class="'+(data[i].checked?'onClick':'')+'"></span>':'')+'<a target="_self"' + (link?' href="'+data[i].url+'"':'') + attr + title + '>'+ data[i].name +'</a></div>');
			
			if(hasChild) this.addChild(str,id+'_'+i,data[i].children,check,link);//循环
		}
		box.innerHTML=str.join('');
	},
	addChild:function(str,id,children,check,link){//生成单项item-nav
		str.push('<div class="itembox">');
		for(var i=0,l=children.length;i<l;i++){
			var hasChild=children[i].children&&children[i].children.length;
			var attr = '',title = '';
			title = children[i].uri ? 'title='+children[i].uri : '';
			$.each(children[i],function(i,item){
				if( i == 'children' || i == 'last_modify' || i == 'uri' || i == 'operator' || i == 'name' || i == 'sequence'){
					return;
				}
				attr += i + '=' + item + ' ';
			})
			str.push('<div class="item-nav'+(hasChild?' parent':'')+'" id="'+id+'_'+i+'"><b class="icon icon-hand-right"></b>'+(check?'<span class="'+(children[i].checked?'onClick':'')+'"></span>':'')+'<a target="_self"' + (link?' href="'+children[i].url+'"':'') + attr + title + '>'+ children[i].name +'</a></div>');
			
			if(hasChild) this.addChild(str,id+'_'+i,children[i].children,check);//循环
		}
		str.push('</div>');
	},
	showMenu:function(o,url,unfold){
		var id='',oId=o.slice(1),uIDX=url.indexOf('?'),url=url.slice(0,uIDX==-1?url.length:uIDX);
		$(o).find('a').each(function(){//找到当前页菜单
			if(this.href==url){id=$(this).parent().attr('id');return false;}
		});
		var ori=id;//保存原始值
		this.show=true;//设置最小延迟
		if(unfold){
			$(o).find('.menu-nav,.item-nav').click();
			if(id) $('#'+id).find('a').addClass('active');
		} else {
			if(id){
				do{
					$('#'+id).click();
					if(id===ori) $('#'+id).find('a').addClass('active');
					id=id.slice(0,id.lastIndexOf('_'));
				}
				while(id!==oId);
			}
		}
		this.show=false;//还原
	},
	
	parseItem:function(box){//解析数据(返回后台)
		var data=[],_this=this,$o=box?$(box):this.$BOX;
		var chlidTree = this.chlidTree;
		//
		$o.children('.item-nav').each(function(){
			var json={};
			//20120710 update
			var $treeNode = $(this).find('a');
			//($treeNode.attr('app_id') !== undefined) && (json.app_id = $treeNode.attr('app_id'));
			json.app_id=$treeNode.attr('app_id');
			json.parent_id=$treeNode.attr('parent_id');
			if( chlidTree){
				json.resources_id=$treeNode.attr('resources_id');
			} else{
				json.menu_id=$treeNode.attr('menu_id');
			}

			json.name=$treeNode.html();
			json.url=$treeNode.attr('url');
			json.value=$treeNode.attr('value');
			json.hide=$treeNode.attr('hide');
			//($treeNode.attr('value') != 'undefined') && (json.value = $treeNode.attr('value'));
			if( $(this).find('span').is('.check')){
				//父节点 选中 设置 check = false 防止 一加载就点击 造成没有全选的假象
				if($(this).is('.parent')){
					json.checked=false;
				} else{ json.checked=true;}
			} else{
				json.checked=false;
			}
			if($(this).next().is('.itembox')){
				json.children=_this.parseItem($(this).next());
			}
			//else json.children=[1];
			data.push(json);
		});
		return data;//返回array
	},
	parseCheckItem:function(){
		var data=this.parseItem();//=children
		var arrCheck=[],str='';
		var parseChild=function(arr){
			for(var i=0;i<arr.length;i++){
				if(arr[i].children) parseChild(arr[i].children);
				else arrCheck.push(arr[i]);
			}
		}
		parseChild(data);
		for(var i=0;i<arrCheck.length;i++){
			if(arrCheck[i].checked) str+=arrCheck[i].value+',';
		}
		return str;//返回字符串
	}
};