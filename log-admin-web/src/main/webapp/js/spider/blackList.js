var vm_accessLog = avalon.define({
	$id:'vm_accessLog',
	list:[]
})

var markSize = 20;
var markPage = 1;

function searchData(page, disR) {
	
	var fn = arguments.callee;
	var req = 'page=' + (page || markPage || 1) + '&size=' + (disR = disR || markSize || 20) ;
	$('#fm').find('input,select,textarea').each(function() { // 遍历搜索框输入的内容作为参数
		if(this.value && this.value != '') req += '&' + this.name + '=' + this.value.replace(/\n/g, ',');
	});
	var url = '/spider/blackList.do';
	
	markSize = (disR = disR || markSize || 20);
	markPage = (page || markPage || 1);
	
	$.post(url, req, function(json) {
		if(!json.success) {
			vm_accessLog.list = []
			$.tools.commonPage('#common_page', fn, 0, 0, 0, 0);
			return;
		}
		var count = json.object.count, list = json.object.list, p = json.object.page, pCount = json.object.pageCount;
		vm_accessLog.list =  json.object.list;
		$.tools.commonPage('#common_page', fn, disR, count, p, pCount);
	});
}

function deleteDialog(ip) {
	$.dialog({
	    id: 'add_user',
	    title: '删除黑名单',
	    content: 
	    	'<span style="width:40px;float:left;text-align:right;">IP：</span>' + ip + '<br />' + 
	    	'<span style="width:40px;float:left;text-align:right;">备注：</span><input id="remark" name="remark" type="text" value="" />',
	    lock: true,
	    fixed: true,
	    ok: function () {
	        
	    	var remark = $('#remark').val();
	    	
	    	if(ip == null || ip == '') {
	    		alert("请输入ip");
	    		return false;
	    	}
	    	
	    	if(remark == null || remark == '') {
	    		alert("请输入备注");
	    		return false;
	    	}
	    	
	    	return deleteRec(ip, remark);
	    },
	    okValue: '删除',
	    cancel: function () {}
	});
}

function deleteRec(ip, remark) {
	var req = {'ip':ip, 'remark':remark};
	var url = '/spider/blackDel.do';
	
	$.ajax({  
        type : "post",  
        url : url,  
        data : req,  
        async : false,  
        success : function(json){  
        	if (!json.success) {
				alert(json.message);
				return false;
			}
        	searchData();
			alert(json.message);
        }  
    }); 
	
	return true;
}

function addNew() {
	$.dialog({
	    id: 'add_user',
	    title: '添加黑名单',
	    content: 
	    	'<span style="width:40px;float:left;text-align:right;">IP：</span><input id="ip" name="ip" type="text" value="" /><br />' + 
	    	'<span style="width:40px;float:left;text-align:right;">备注：</span><input id="remark" name="remark" type="text" value="" />',
	    lock: true,
	    fixed: true,
	    ok: function () {
	        
	    	var ip = $('#ip').val();
	    	var remark = $('#remark').val();
	    	
	    	if(ip == null || ip == '') {
	    		alert("请输入ip");
	    		return false;
	    	}
	    	
	    	if(remark == null || remark == '') {
	    		alert("请输入备注");
	    		return false;
	    	}
	    	
	    	return add(ip, remark);
	    },
	    okValue: '添加',
	    cancel: function () {}
	});
}

function add(ip, remark) {
	
	var req = {'ip':ip, 'remark':remark};
	var url = '/spider/blackAdd.do';
	
	$.ajax({  
        type : "post",  
        url : url,  
        data : req,  
        async : false,  
        success : function(json){  
        	if (!json.success) {
				alert(json.message);
				return false;
			}
        	searchData();
			alert(json.message);
        }  
    }); 
	
	return true;
}

//初始化时间
function initDate() {
	$('#startTime').val($.sysop.kit.date.getDateStr(7)+ " 00:00:00");
	$('#endTime').val($.sysop.kit.date.getDateStr(0)+ " 23:59:59");
}

$(function() {
	App.init();
	initDate();
	searchData();
});

avalon.scan()