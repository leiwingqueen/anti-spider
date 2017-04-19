var vm_accessLog = avalon.define({
	$id:'vm_accessLog',
	list:[]
})


function searchData(page, disR) {
	var diffTime = new Date($('#endTime').val()).getTime() - new Date($('#startTime').val()).getTime();
	if(diffTime > 24*60*60*1000*3){
		$.alert("查询间隔不能超过三天！");
		return;
	}
	
	var fn = arguments.callee;
	var req = 'page=' + (page || 1) + '&size=' + (disR = disR || 20);
	$('#fm').find('input,select,textarea').each(function() { // 遍历搜索框输入的内容作为参数
		if(this.value && this.value != '') req += '&' + this.name + '=' + this.value.replace(/\n/g, ',');
	});
	var url = '/accessLog/list.do?'+req;
	$.post(url,function(json) {
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

//初始化时间
function initDate() {
	$('#startTime').val($.sysop.kit.date.getDateStr(0)+ " 00:00:00");
	$('#endTime').val($.sysop.kit.date.getDateStr(0)+ " 23:59:59");
}

$(function() {
	App.init();
	initDate();
});

avalon.scan()