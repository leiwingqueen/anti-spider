/**
* 各个系统的初始化函数，例如：菜单、昵称、ip地址，以及放一些公共函数 by zenglican 2013-11-07
*/
// 快速通道
function listApps(){ 
    if( !sessionStorage.listApps){
        $.getJSON("/common/listUserApp.do",function(json){
            if(!json.success) {
                $.alert(json.message);
                return;
            }
            var apps = json.object, html = '';
            sessionStorage.listApps = JSON.stringify(apps);
            $.each(apps,function(){
                html += '<li><a href="'+this.url+'" target="_blank">'+this.cn_name+'</a></li>';
            })
            $("#J_appList").html(html);
        }); 
    } else{ //存在
        var html = '';
        var listApps = sessionStorage['listApps'];
        var apps = $.sysop.kit.str2Obj(listApps);
        $.each(apps,function(){
            html += '<li><a href="' + this.url + '" target="_blank">' + this.cn_name + '</a></li>';
        })
        $("#J_appList").html(html);
    }       
}
// 用户名
function loadUserInfo(){ 
    if( !sessionStorage.userName){
        $.getJSON("/common/loadUserInfo.do",function(json){
            if(!json.success) {
                $.alert(json.message);
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
// IP地址
function loadServerIp(){ 
    if( !sessionStorage.ips){
        $.getJSON("/common/loadServerIp.do",function(json){
            if(!json.success) {
                $.alert(json.message);
                return;
            }
            var ips = json.object, html = '';
            for(var i = 0, l = ips.length; i < l; i++){
                html += ips[i] + '<br />'
            }
            $("#serverIps").html(html);
            sessionStorage.ips = html;
        }); 
    } else{
        $("#serverIps").html(sessionStorage.ips);
    }
}
// 退出
function closeWindow(){ 
    $('#J_logout').on('click',function(){
        if( !confirm('确定退出？')){
            return;
        }
        sessionStorage.userName = '';
        sessionStorage.listApps = '';
        sessionStorage.menuData = '';
        // 清除cookie
        systemLogout();
        // $.post('/logout.do',function(data){
        //     console.log(data)
        // })
        /*window.open('', '_self', '');
        window.close();*/
    })
}
function systemLogout(){
    var host_name = window.location.hostname;
    console.log(host_name)
    $.removeCookie('sysop_privilege_user_name',{domain:'duowan.com',path:'/'});
    $.removeCookie('sysop_privilege_user_name',{domain:host_name,path:'/'});
    // console.log($.cookie('sysop_privilege_user_name'))
    location.reload();
}
// loading effect
function loadingEffect(){
    $.sysop.popup.init();
}
// 导航菜单高亮
var menuHighlight = {
    save:function(container){
        var trigger = $(container).find('.J_menu_item_trigger');
        $(container).on('click','.J_menu_item_trigger',function(e){
            sessionStorage.currentMenuId = $(this).attr('id');
        })
    },
    init:function(){
        var _id = sessionStorage.currentMenuId;
        if(_id !== undefined){
            var p_id = _id.slice(0,_id.lastIndexOf('_'));
            $('#' + _id).closest('li').addClass('active');
            $('#' + p_id).addClass('active open');
            $('#' + p_id).children('a').find('.arrow').addClass('open');
        } 
    }
}
// 获取菜单数据
function getMenuData(url,callback){
	
    if( !!sessionStorage.menuData){
        var data = $.sysop.kit.str2Obj(sessionStorage.menuData);
        callback('#menu-container',data,menuHighlight.init);
    } else{
        $.getJSON(url,function(json){
            if(!json.success) {
                $.alert(json.message);
                return;
            }
            var data  = [];
            var menus = json.object;
            var menuFilter = {};
            for(var j = 0; j < menus.length; j++){
                if(menus[j].parent_id==0){
                    var menu = {};
                    menu["name"] = menus[j].name;
                    menu["parentId"] = menus[j].parent_id;
                    menu["sequence"] = menus[j].sequence;
                    menu["url"] = menus[j].url;
                    menu["value"] = menus[j].menu_id;
                    menu["checked"] = false;
                    menu["children"] = [];
                    menuFilter[menus[j].menu_id] = menu;
                    data.push(menu);
                } else{
                    var menu = {}, children;
                    menu["name"] = menus[j].name;
                    menu["parentId"] = menus[j].parent_id;
                    menu["sequence"] = menus[j].sequence;
                    menu["url"] = menus[j].url;
                    menu["value"] = menus[j].menu_id;
                    menu["checked"] = false;
                    menu["children"] = null;  
                    children = menuFilter[menus[j].parent_id].children;
                    children.push(menu);
                }
            }
            callback && callback('#menu-container',data,menuHighlight.init);
            sessionStorage.menuData = $.sysop.kit.obj2Str(data);
            // console.log(data)
        })
    }
}
// 拼装
function createMenu(container,menuData,callback){
    var index = 0, sub_index = 0;
    if(menuData.length !== 0){
        var root = '', submenu = '', html = '', icon = '';
        $.each(menuData,function(i,n){
            if(n.icon === '' || n.icon === undefined){
                icon = 'icon-caret-right';
            } else{
                icon = n.icon;
            }
            if(n.children === undefined || n.children.length === 0){
                html += '<li id="menu_item_' + index + '"><a class="J_menu_item_trigger" id="menu_item_' + index + '_' + sub_index + '" title="' + n.name + '" href="' + n.url + '"><i class="' + icon + '"></i><span class="title">' + n.name + '</span></a></li>';
                index++;
                sub_index++;
            } else{
                html += '<li id="menu_item_' + index + '"><a title="' + n.name + '" href="javascript:;"><i class="' + icon + '"></i><span class="title">' + n.name + '</span><span class="arrow"></span></a><ul class="sub-menu">';
                submenu = '';
                sub_index = 0;
                for(var i = 0, l = n.children.length; i < l; i++){
                    submenu += '<li><a class="J_menu_item_trigger" id="menu_item_' + index + '_' + sub_index + '" title="' + n.children[i].name + '" href="' + n.children[i].url + '"><span>' + n.children[i].name + '</span></a></li>';
                    sub_index++;
                }
                html += submenu + '</ul>';
                index++;
            }
        })
        $(container).html(html);
        menuHighlight.save(container);
        callback && callback(container);
    }
}
// 阻止表单提交，采用ajax提交数据
function returnFormSubmit(){
    $('form').on('submit',function(){
        return false;
    })
}
//显示日历
function showCalendar(){
    $('.J_calendar').on('click',function(){
        $.calendar({ format:'yyyy-MM-dd HH:mm' });
    })
    // 前面的日期不能超过后面
    $('#end_time').calendar({ minDate:'#start_time',format:'yyyy-MM-dd HH:mm' });
    $('#start_time').calendar({ maxDate:'#end_time',format:'yyyy-MM-dd HH:mm' });
}
function showCalendarByOption(elem,hasSeconds,hasTime){
    var format;
    if(hasSeconds){
        format = 'yyyy-MM-dd HH:mm:ss';
    } else if(hasTime){
        format = 'yyyy-MM-dd HH:mm';
    } else{
        format = 'yyyy-MM-dd';
    }
    $(elem).calendar({ format:format });
}
// 调用
loadingEffect();
getMenuData('/common/menu.do',createMenu);
loadUserInfo();
loadServerIp();
listApps();
closeWindow();
returnFormSubmit();
// showCalendar(); 日历插件初始化太慢，放到base.js 最后再执行
// $.alert('json.message');