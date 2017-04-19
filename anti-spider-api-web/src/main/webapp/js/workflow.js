function graphTrace(pid) {

    var _defaults = {
        srcEle: this,
        pid: $(this).attr('pid')
    };
    // 获取图片资源
    var imageUrl = "/workflow/getInstanceResource.do?processInstanceId=" + pid + "&type=image";
    $.getJSON('/workflow/trace.do?processInstanceId=' + pid, function(data) {
    	if(!data.success){	
    		if(data.message!='No bean specified'){
    			alert(data.message);
    		}
    		return;
    	}
    	var infos=data.object;
        var positionHtml = "";
        // 生成图片
        var varsArray = new Array();
        $.each(infos, function(i, v) {
            var $positionDiv = $('<div/>', {
                'class': 'activity-attr'
            }).css({
                position: 'absolute',
                left: (v.x - 1),
                top: (v.y - 1),
                width: (v.width - 2),
                height: (v.height - 2),
                backgroundColor: 'black',
                opacity: 0,
                zIndex: $.fn.qtip.zindex - 1
            });

            // 节点边框
            var $border = $('<div/>', {
                'class': 'activity-attr-border'
            }).css({
                position: 'absolute',
                left: (v.x - 1),
                top: (v.y - 1),
                width: (v.width - 4),
                height: (v.height - 3),
                zIndex: $.fn.qtip.zindex - 2
            });

            if (v.currentActiviti) {
                $border.addClass('ui-corner-all-12').css({
                    border: '3px solid red'
                });
            }
            positionHtml += $positionDiv.outerHTML() + $border.outerHTML();
            varsArray[varsArray.length] = v.vars;
        });
        
        $('#workflowTraceDialog img').attr('src', imageUrl);
        $('#workflowTraceDialog #processImageBorder').html(positionHtml);

        // 设置每个节点的data
        $('#workflowTraceDialog .activity-attr').each(function(i, v) {
            $(this).data('vars', varsArray[i]);
        });
        
        $('#workflowTraceDialog').css('padding', '0.2em');
        $('#workflowTraceDialog .ui-accordion-content').css('padding', '0.2em').height($('#workflowTraceDialog').height() - 75);

        // 此处用于显示每个节点的信息，如果不需要可以删除
        $('.activity-attr').qtip({
            content: function() {
                var vars = $(this).data('vars');
                var tipContent = "<table class='need-border'>";
                $.each(vars, function(varKey, varValue) {
                    if (varValue) {
                        tipContent += "<tr><td class='label'>" + varKey + "</td><td>" + varValue + "<td/></tr>";
                    }
                });
                tipContent += "</table>";
                return tipContent;
            },
            position: {
                at: 'bottom left',
                adjust: {
                    x: 3
                }
            }
        });
    });
}
/**
 * 获取元素的outerHTML
 */
$.fn.outerHTML = function() {

    // IE, Chrome & Safari will comply with the non-standard outerHTML, all others (FF) will have a fall-back for cloning
    return (!this.length) ? this : (this[0].outerHTML ||
    (function(el) {
        var div = document.createElement('div');
        div.appendChild(el.cloneNode(true));
        var contents = div.innerHTML;
        div = null;
        return contents;
    })(this[0]));
    
};
