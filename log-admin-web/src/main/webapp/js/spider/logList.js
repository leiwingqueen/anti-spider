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
	var url = '/spider/logList.do';
	
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