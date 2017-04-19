/**
 * 
 * 重新整理的SYSOP类+jQuery扩展
 * 
 * @author KOEN
 * 
 * 2011-7-20/26
 * 2011-8-12/8-22/9-7/9-15/9-29/10-8/10-24/10-31/11-1/11-9/11-15/11-18/12-5/12-7/12-13/12-20/12-22/12-27/1-10/1-16
 * 2012-8-2 增加 serializeAll 即使checkbox未选中 也提交他的值
 * 2012-8-4 修改 自动完成
 */
$.sysop={
	//
	//***** 弹出层类 *****
	//
	popup:{
		count:99,//初始化z-index
		ajaxStartCount:0,//Ajax次数记录
		getBox:function(o){
			var str=o&&o[o.href?'href':'alt'];
			var strBox=str&&str.slice(str.lastIndexOf('#'));
			return $(strBox);
		},
		box:function(box,css){
			this.btn=this.target;//保存最近BTN
			this.resetStart=false;//重置
			var $box=$(box||this.getBox(this.btn));
			if(css) $box.css(css);//自定义CSS@11-1
			if($box.is(':hidden')) this.count+=2;//只有box隐藏时z-index才自增，以兼容重复点击
			var docH=$(document).height(),boxH=$box.height(),winH=$(window).height();
			var varH=boxH+$(document).scrollTop(),bgH=boxH>winH?(varH>docH?varH+2:docH):docH;
			var top = (boxH>winH?0:($(window).height()-boxH)*0.4)+$(document).scrollTop();//0.4即4:6的比例
			var left = ($(document).width() - $box.width())/2;
			$('.pop-bg').css({height:bgH,zIndex:this.count,display:'block'});
			$box.css({top:top,left:left,zIndex:this.count+1,display:'block'});//增加1（2011.8.1）
		},
		bg:function(){
			this.btn=this.target;//保存最近BTN
			$('.pop-bg').css({height:$(document).height(),display:'block'});
		},
		drag:function(tit,e){//拖拽
			var $box=$(tit).parent().parent(),offset=$box.offset(),x=e.clientX-offset.left,y=e.clientY-offset.top,maxX=$(window).width()-$box.width()-2;	
			$(document).bind('mousemove.popup',function(e){
				var _left=e.clientX-x,left=_left<0?0:(_left>maxX?maxX:_left),top=e.clientY-y;
				$box.css({left:left,top:top});
			});
			$(tit).bind('mouseup.popup',function(){$(document).unbind("mousemove.popup").unbind("mouseup.popup");});
		},
		close:function(box){//关闭
			if(this.resetStart) return;//防止重复设置conut
			this.btn=this.target;//保存最近BTN
			
			var $box = this.btn.getAttribute(this.btn.href?'href':'alt');
			$box = $($box);
//			var s = this.btn && this.btn[this.btn.href?'href':'alt'],
//				$box = $(box||s.substring(s.lastIndexOf('#')));
			$box.hide();//关闭BOX
			
			this.count-=2;
			if(this.count==99) $('.pop-bg').hide();
			else $('.pop-bg').css({zIndex:this.count});
		},
		tip:function(tit,cont,css,callback){//弹出提示框，参数css可省略设置
			if(typeof css!=='object') callback=css,css=undefined;
			$('#popTips').remove();
			$('body').append('<div id="popTips" class="pop-cont popTips"></div>');
			$('#popTips').html('<dl><dt>'+tit+'<a class="pop-close" href="#popTips"></a></dt><dd><div class="cont"><div class="cont_t">'+cont+'</div></div><div class="bottom"><input type="button" alt="#popTips" class="ok close" value="确认" /></div></dd></dl>')
			.find('.pop-close').click(callback||function(){});
			if(css) $('#popTips').css(css);
			this.box('#popTips');
		},
		autoTip:function(cont,fn){
			$('#popAutoTips').remove();
			$('body').append('<div id="popAutoTips" class="popAutoTips">'+cont+'</div>');
			$('#popAutoTips').show().delay(1000).hide(100,function(){fn&&fn();});
			//if(css) $('#popAutoTips').css(css);
		},
		confirm:function(tit,cont,css,callback,isCache,callback2){//弹出确认框，参数css可省略设置
			if(typeof css!=='object') callback2=isCache,isCache=callback,callback=css,css=undefined;
			if(!isCache) $('#popConfirm').remove();
			if(!$('#popConfirm').length){
				$('body').append('<div id="popConfirm" class="pop-cont popConfirm"></div>');
				$('#popConfirm').html('<dl><dt>'+tit+'<a class="pop-close" href="#popConfirm"></a></dt><dd><div class="cont"><div class="cont_t">'+cont+'</div><div class="btn"><input type="button" alt="#popConfirm" class="ok close button" value="确认" /> <input type="button" alt="#popConfirm" class="pop-close button" value="取消" /></div></div></dd></dl>')
				.find('.ok').click(function(){callback&&callback.call($('#popConfirm')[0])}).end().find('.pop-close').click(function(){callback2&&callback2();});
			}
			if(css) $('#popConfirm').css(css);
			this.box('#popConfirm');
		},
		confirm_new:function(tit,cont,css,callback,isCache,callback2){//弹出确认框，参数css可省略设置
			if(typeof css!=='object') callback2=isCache,isCache=callback,callback=css,css=undefined;
			if(!isCache) $('#popConfirm').remove();
			if(!$('#popConfirm').length){
				$('body').append('<div id="popConfirm" class="pop-cont popConfirm"></div>');
				$('#popConfirm').html('<dl><dt>'+tit+'<a class="pop-close" href="#popConfirm"></a></dt><dd><div class="cont"><div class="cont_t">'+cont+'</div><div class="btn"><input type="button" alt="#popConfirm" class="ok button" value="确认" /> <input type="button" alt="#popConfirm" class="pop-close button" value="取消" /></div></div></dd></dl>')
				.find('.ok').click(function(){callback&&callback.call($('#popConfirm')[0])}).end().find('.pop-close').click(function(){callback2&&callback2();});
			}
			if(css) $('#popConfirm').css(css);
			this.box('#popConfirm');
		},
		corner:function(box,fn){
			$(box).animate({bottom:'0px'},200);
		},
		reset:function(){
			this.count=99;
			this.resetStart=true;//防止冒泡触发.pop-close
			$('.pop-cont').css({display:'none'});
			$('.pop-bg').css({zIndex:this.count,display:'none'});
		},
		init:function(){
			var self=this;//保存指针
			$(function(){
				//添加必要的html
				if(!$('.pop-bg').length) $('body').append('<div class="pop-bg"></div><div class="pop-bg-trans"></div><div class="pop-loading">Loading...</div>');
				//弹窗事件绑定
				$('.pop-btn').live('click',function(){//弹出本地窗口
					$.sysop.popup.box();return false;
				});
				$('.pop-btn-ajax').live('click',function(){//弹出AJAX窗口
					$.sysop.popup.bg();return false;
				});
				//loading提示和AJAX保存过程中的表单禁用
				var $input=$(':input:enabled'),isSave=false;
				//标记保存动作
				$(':button[value^="保存"],:button[value^="修改"]').click(function(){
					isSave=true;
				});
				//全局ajax开始
				$('.pop-loading').ajaxStart(function(){
					$(this).show();
					//延迟列队执行的保存
					setTimeout(function(){
						if(isSave) $('.pop-bg-trans').css({height:$('body').height()}).show();
					},0);
				});
				//全局ajax结束
				$('.pop-loading').ajaxStop(function(){
					$(this).hide();
					$('.pop-bg-trans').hide();//有无显示都hide以兼容.showTransBg();
					if(isSave) isSave=false;//删除$input.attr('disabled',false),
				});
			});
		},
		__init__:function(){//注意初始化顺序
			$(document).click(function(e){//记录

				if($(e.target).is('.pop-btn-local,.pop-btn,.pop-close')){
					$.sysop.popup.target = e.target;
				}
			});
			$('.pop-cont dt').live('mousedown',function(e){//拖拽
				$.sysop.popup.drag(this,e);
			});
			$('.pop-close').live('click',function(e){//关闭
				$.sysop.popup.target = this;
				$.sysop.popup.close();return false;
			});
		}()
	},
	tipClick:{//悬停弹出TIP
		addTip:function(){
			$('#tip-click-cont').remove();
			$('body').append('<div class="tip-click-cont" id="tip-click-cont"><span class="x">&times;</span></div>');
		},
		addEvent:function(){
			$('#tip-click-cont .x').click(function(){
				$(this).parent().hide();
			});
		},
		show:function(o,html){
			this.addTip();
			this.addEvent();
			var html=html?html:$(o).attr('tip'),$box=$('#tip-click-cont');
			$box.append(html);
			//
			var boxH=$box.outerHeight(),oH=$(o).outerHeight();//tip高度、链接高度
			var os=$(o).offset(),left=os.left,top=os.top;
			var scrTop=$(document).scrollTop(),cltTop=top-scrTop;
			if(boxH<cltTop) top=os.top-boxH;
			else top=os.top+oH;
			$box.css({left:left,top:top,display:'block'});
		},
		hide:function(){
			$('#tip-click-cont').hide();
		},
		init:function(fn){
			$('.tip-click').live('click',function(){$.sysop.tipClick.show(this);return false;});
			$(document).click(function(e){//记录
				if(!$(e.target).is('#tip-click-cont,.tip-click')) $('#tip-click-cont').hide();
			});
			//$('.tip-click').live('mouseout',function(){$.sysop.tipHover.hide(this);});
			fn&&fn();
		}
	},
	tipHover:{//悬停弹出TIP
		addTip:function(){
			$('#tip-hover-cont').remove();
			$('body').append('<div class="tip-hover-cont" id="tip-hover-cont"></div>');
		},
		addEvent:function(){
			var _this=this;
			$('#tip-hover-cont').hover(function(){
				clearTimeout(_this.timeout);
				$(this).show();
			},function(){
				var _o=this;
				_this.timeout=setTimeout(function(){
					_this.hide(_o);	
				},200);
			});
		},
		show:function(o){
			this.addTip();
			this.addEvent();
			var s=$(o).attr('tip'),s1=s.slice(0,1),html,$box=$('#tip-hover-cont');
			if(s1=='#') html=$(s).clone(true);
			else html=s;
			$box.append(html);
			//
			var boxH=$box.outerHeight(),oH=$(o).outerHeight();//tip高度、链接高度
			var os=$(o).offset(),left=os.left,top=os.top;
			var scrTop=$(document).scrollTop(),cltTop=top-scrTop;
			if(boxH<cltTop) top=os.top-boxH;
			else top=os.top+oH;
			$box.css({left:left,top:top,display:'block'});
		},
		hide:function(){
			$('#tip-hover-cont').hide();
		},
		init:function(fn){
			var _this=this;
			$('.tip-hover').live('mouseover',function(){
				var _o=this;
				clearTimeout(_this.timeout);
				_this.timeout=setTimeout(function(){
					_this.show(_o);
				},200);
			});
			$('.tip-hover').live('mouseout',function(){
				var _o=this;
				clearTimeout(_this.timeout);
				_this.timeout=setTimeout(function(){
					_this.hide(_o);	
				},200);
			});
			fn&&fn();
		}
	},
	//
	//***** 分页类 *****
	//
	pagination:{
		toPage:function(n,self){
			var num=typeof n=='string'?n:this.href.substring(this.href.lastIndexOf('#')+1);
			if(num/1>self.total/1||num/1<1||isNaN(num/1)) {alert('不存在此页面！');return;}
			self.callback(num);
		},
		initHTML:function(box,num,all){
			var self=this;
			box.html('<ul><li> <a class="first" href="#">首页</a> <a class="prev" href="#">上一页</a> </li><li class="list"></li><li> <a class="next" href="#">下一页</a> <a class="last" href="#">尾页</a> </li><li class="goto">转到第 <input id="gotoPageValue" type="text" value="'+num+'" />/'+all+'页 <input id="gotoPageAction" type="button" class="btn btn-primary" value="GO" /></li></ul>');
			box.find('#gotoPageAction').click(function(){
				self.toPage($('#gotoPageValue').val(),self);
			});
			
			//绑定enter
			$('#gotoPageValue').keypress(function(e){
				if(e.keyCode === 13){
					box.find('#gotoPageAction').click();
				}
			});
		},
		init:function(pageBox,current,total,disNum,callback){//初始化分页
			var $pageBox=$(pageBox).show(),self=this;
			if(total==0){$pageBox.css('display','none');return;}//当没有数据时退出
			this.initHTML($pageBox,current,total);//初始化html
			var cont=$pageBox.find('.list'),len=total,num=current-1,reObj=this,mid=Math.floor(disNum/2);
			this.total=total;
			this.callback=callback;//定义变量结束
			cont.html('');//清空cont的html
			var end = 0;
			if(num>mid-1) {
				end=len-num>mid?num+mid+1:len,start=len>disNum?end-disNum:0;
			}
			else{
				end=len>disNum?disNum:len,start=0;
			}
			for(var i=start;i<end;i++) cont.append('<a class="num" href="#'+(i+1)+'">'+(i+1)+'</a>');//生成link
			var pageList=$pageBox.find('.num'),relIndex=num-start;
			pageList.eq(relIndex).replaceWith("<span class='cur'>" + pageList.eq(relIndex).text() + "</span>");//生成link和span结束
			$pageBox.find('.prev').attr('href','#'+num);
			$pageBox.find('.next').attr('href','#'+(num+2));
			$pageBox.find('.first').attr('href','#'+1);
			$pageBox.find('.last').attr('href','#'+total);
			pageAllLink=$pageBox.find('a');
			pageAllLink.unbind('click').show();
			pageAllLink.click(function(e){
				e.preventDefault();
				
				
				var tb = $('.page-header h1');//pageBox.parent().siblings("table");
				if(tb.length){
					tb.get(0).scrollIntoView();
				}else{
					document.body.scrollTop = 0;
				}
				self.toPage.call(this,undefined,self);
			});//绑定点击
			if(num==0){$pageBox.find('.first,.prev').hide();}
			if(num==len-1){$pageBox.find('.last,.next').hide();}
		},
		displayNum:function(box,num,clickCallback,n){//每页显示记录
			var n1=n||10,n2=n1*2,n3=n1*5,n4=n1*10,n5=n1*20,box=$(box);
			box.html('<span class="tip-hover btn" tip="#displayNum_cont">每页显示...</span><span id="displayNum_cont" class="cont"><a rel="'+n1+'">'+n1+'条</a><a rel="'+n2+'">'+n2+'条</a><a rel="'+n3+'">'+n3+'条</a><a rel="'+n4+'">'+n4+'条</a><a rel="'+n5+'">'+n5+'条</a></span>');
			if(num=='0') $('a',box).eq(0).replaceWith("<span class='cur'>" + $('a',box).eq(0).text() + "</span>");
			$('a',box).each(function(){
				if($(this).attr('rel')==num) $(this).replaceWith("<span class='cur'>" + $(this).text() + "</span>");
			});
			$('a',box).click(function(){
				clickCallback.call(this);
				$('#tip-hover-cont').hide();
			});
		}
	},
	
	//
	//***** 常用函数工具包 *****
	//
	kit:{
		getUrlValue:function(str,isEncodeUrl){//isEncodeUrl用于判断是否已转码@12.9
			var url=isEncodeUrl?location.href:decodeURI(location.href);
			var len=str.length;
			if(url.indexOf(str+'=')==-1) return '';
			else{
				var val=url.slice(url.indexOf(str+'=')+len+1);//取得等号后的字符串
				if(val.indexOf('&')==-1) return val;
				else return val.slice(0,val.indexOf('&'));
			}
		},
		obj2Str:function(o,isEncode){//包含JSON格式化，把双引号转为\"的形式
			var r = [],_this=this;
			if(o == null) return "null";
			if(typeof o == "string") return isEncode?'"'+encodeURIComponent(_this.toJSONStr(o))+'"':'"'+_this.toJSONStr(o)+'"';//可选URI转码@11.15
			if(typeof o == "object"){
			     if(!o.sort){
			       r[0]="{";
			       for(var i in o){
			         r[r.length]='"'+i+'"';
			         r[r.length]=":";
			         r[r.length]=_this.obj2Str(o[i],isEncode);
			         r[r.length]=",";
			       }
			       r[r.length-1]="}";
			     }else{
			       if(o.length==0) return "[]";
			       r[0]="[";
			       for(var i =0;i<o.length;i++){
			         r[r.length]=_this.obj2Str(o[i],isEncode);
			         r[r.length]=",";
			       }
			       r[r.length-1]="]";
			     }
			     return r.join("");
			  }
		   return o.toString();
		},
		toJSONStr:function(s){
			return s.replace(/"/g,'\\"');
		},
		str2Obj:function(s){
			return eval('('+s+')');
		},
		deParam:function(s,isObj){//把类似'name=koen&work=sys'转换成{"name":"koen","work":"sys"}形式
			if(!s) return "{}";
			var o={},len=s.match(/\=/g).length;
			var start=0;
			for(var i=0;i<len;i++){
				var v1=s.indexOf('=',start),v2=s.indexOf('&',start);
				sName=s.slice(start,v1);
				if(v2!=-1){
					sValue=s.slice(v1+1,v2);
					start=(v2+1);
				}
				else sValue=s.slice(v1+1);
				if(typeof o[sName]!=='undefined'){//增加支持checkbox数组
					var arr=[],val=o[sName];
					if(val.sort){
						o[sName].push(sValue);
					} else {
						arr.push(val,sValue);
						o[sName]=arr;
					};
				}else{//正常情况
					o[sName]=sValue;	
				}
			}
			return isObj?this.decodeJSON(o):this.obj2Str(this.decodeJSON(o));//增加解编码@12.28
		},
		serializeGT:function(box){//序列化BOX内含有name属性的元素值，包括INPUT元素
			var s='';
			$(box).find('[name]').each(function(){
				var val=$(this).is('span')?$(this).html():(this.value);
				val=encodeURIComponent(val);//编码@12.28
				s+=$(this).attr('name')+'='+val+'&';
			});
			return s.slice(0,-1);
		},
		serializeAll: function(form){//包括checkbox未选中的值也提交上来 2012-8-2
			var r = '',
				items = $(form).find('[name]');
			items.each(function(i){
				var $this = $(this);
				if( $this.is('[type=checkbox]')){//如果是checkbox
					if( $this.is(':checked')){
						r += $this.attr('name') + '=' + $this.val() + '&';
					} else{
						r += $this.attr('name') + '=' + $this.attr('noCheckValue') + '&';
					}
				} else if( $this[0].nodeName.toLowerCase() == 'select'){//如果是select
					r += $this.attr('name') + '=' + $this.find(':selected').val() + '&';
				} else if( $this.is('[type=radio]')){//如果是radio
					if( $this.is(':checked')){
						r += $this.attr('name') + '=' + $this.val() + '&';
					}
				} else{//其他
					r += $this.attr('name') + '=' + $this.val() + '&';
				}
			})	
			return r.slice(0,-1);
		},

		date:{
			checkTime:function(i){
				if (i<10) i="0" + i;
				return i;
			},
			getDateStr:function(dis){//负值是将来
				var D=new Date();
			    D.setDate(D.getDate()-(dis==undefined?0:dis));	
			    var date=D.getDate(),month=D.getMonth()+1,year=D.getFullYear();
			    return year+'-'+this.checkTime(month)+'-'+this.checkTime(date);
			},
			getTimeStr:function(dis1,dis2,all){//dis1为时间，dis2为日期，all是否显示全部
				var D=new Date();
				D.setDate(D.getDate()-(dis2==undefined?0:dis2));
				D.setHours(D.getHours()-(dis1==undefined?0:dis1));
				var date=D.getDate(),month=D.getMonth()+1,year=D.getFullYear();
				var h=D.getHours(),m=D.getMinutes(),s=D.getSeconds();
				//
				var dStr=year+'-'+this.checkTime(month)+'-'+this.checkTime(date);
				var tStr=this.checkTime(h)+':'+this.checkTime(m);//+':'+this.checkTime(s);屏蔽秒@12-6
				
				return all?(dStr+' '+tStr):tStr;
			},
			getAllDateStr:function(dis1,dis2){
				return this.getTimeStr(dis1,dis2,true);
			},
			setDate:function(input,dis){
				$(input).val(this.getDateStr(dis));
			},
			setDate2Date:function(startInput,endInput,dis){
				$(startInput).val(this.getDateStr(dis));
				$(endInput).val(this.getDateStr());
			},
			setAllDate2Date:function(startInput,endInput,dis1,dis2,hasSec){
				$(startInput).val(this.getAllDateStr(dis1,dis2)+(hasSec?':00':''));
				$(endInput).val(this.getAllDateStr()+(hasSec?':00':''));
			}
		},
				
		selectLoad:function(select,param){//{url,value,name,relInput,fn}
			var relStr=param.relInput?$(param.relInput).val():'',str=$(select).find('option').html(),s='<option value="0">'+(str||'请选择')+'</option>';
			$.getJSON(param.url+relStr,function(data){
				if(!data.success) return;
				$.each(data.object,function(){
					s+='<option value="'+this[param.value]+'">'+this[param.name]+'</option>'
				});
				$(select).html(s);
				param.fn&&param.fn();
			});
		},
		encodeJSON:function(o){
			for(var i in o){
				if(typeof o[i]==='string') o[i]=encodeURIComponent(o[i]);
				if(o[i].length&&o[i].sort){
					for(var j=0;j<o[i].length;j++) this.encodeJSON(o[i][j]);
				}
			}
			return o;
		},
		decodeJSON:function(o){
			for(var i in o){
				if(typeof o[i]==='string') o[i]=decodeURIComponent(o[i]);
				if(o[i].length&&o[i].sort){
					for(var j=0;j<o[i].length;j++) this.decodeJSON(o[i][j]);
				}
			}
			return o;
		}
	}
};