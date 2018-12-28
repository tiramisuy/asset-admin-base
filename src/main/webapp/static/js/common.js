// 获取请求参数
// 使用示例
// location.href = http://localhost:8080/index.html?id=123
// T.p('id') --> 123;
var url = function(name) {
	var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if(r!=null)return  unescape(r[2]); return null;
}

//全局配置
$.ajaxSetup({
    dataType: "json",
    cache: false,
    complete:function(XMLHttpRequest, textStatus){
        var sessionstatus = XMLHttpRequest.getResponseHeader("sessionstatus");
        if (sessionstatus == 'timeout') {
            top.layer.open({
                title: '系统提示',
                area: '338px',
                icon: 3,
                move: false,
                anim: -1,
                isOutAnim: false,
                content: '注：登录超时,请稍后重新登录.',
                btn: ['立即退出'],
                btnAlign: 'c',
                yes: function(){

                }
            });
            setTimeout(function(){
                toUrl("sys/logout");
            }, 2000);
        }
    }
})

//权限判断
function hasPermission(permission) {
    if(isNullOrEmpty(window.parent.perms)) {
    	return false;
    }
	if (window.parent.perms.indexOf(permission) > -1) {
        return true;
    } else {
        return false;
    }
}

//返回满足权限的个数
function cntPermission(permission) {
    var  count = 0;
    if(isNullOrEmpty(window.parent.perms)) {
        return count;
    }
    $.each(permission, function(idx, item){
        if (window.parent.perms.indexOf(item) > -1) {
            count++;
        }
    });
    return count;
}

//构造更多操作按钮
function morePermissionOpt(cnt, opts) {
    var _html = '', width = (cnt*82);
    _html += '<div class="btn-group">' +
        '<a data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" title="更多">' +
        '<i class="fa fa-chevron-circle-right"></i>' +
        '</a>';
    _html += 	'<ul class="more-opt dropdown-menu" style="min-width: '+width+'px;left: -'+width+'px;">';
    $.each(opts, function(idx, item){
        _html += item;
    });
    _html += 	'</ul>';
    _html += '</div>'
    return _html;
}

toUrl = function(href) {
	window.location.href = href;
}

$.fn.bootstrapTableEx = function(opt){
	var defaults = {
		url: '',
		dataField: "rows",
		method: 'post',
		dataType: 'json',
		selectItemName: 'id',
		clickToSelect: false,
		pagination: true,
		smartDisplay: false,
		pageSize: 10,
		pageList: [10, 20, 30, 40, 50],
        paginationLoop: false,
        paginationShowPageGo: true,
		sidePagination: 'server',
		queryParamsType : null,
		columns: []
	}
	var option = $.extend({}, defaults, opt);
    if(!option.pagination){
        option.responseHandler = function(res) {
            return res.rows;
        }
    }
	$(this).bootstrapTable(option);
}

$.fn.bootstrapTreeTableEx = function(opt) {
    var $table = $(this);
    var defaults = {
        url: '',
        striped: true,
        sidePagination: 'server',
        clickToSelect: false,
        idField: '',
        columns: [],
        treeShowField: '',
        parentIdField: '',
        onLoadSuccess: function(data) {
            $table.treegrid({
                treeColumn: 1,
                onChange: function() {
                    $table.bootstrapTable('resetWidth');
                }
            });
        }
    }
    var option = $.extend({}, defaults, opt);
    $(this).bootstrapTable(option);
}

formatDate = function (v, format) {
    if (!v) return "";
    var d = v;
    if (typeof v === 'string') {
        if (v.indexOf("/Date(") > -1)
            d = new Date(parseInt(v.replace("/Date(", "").replace(")/", ""), 10));
        else
            d = new Date(Date.parse(v.replace(/-/g, "/").replace("T", " ").split(".")[0]));//.split(".")[0] 用来处理出现毫秒的情况，截取掉.xxx，否则会出错
    }
    var o = {
        "M+": d.getMonth() + 1,
        "d+": d.getDate(),
        "h+": d.getHours(),
        "m+": d.getMinutes(),
        "s+": d.getSeconds(),
        "q+": Math.floor((d.getMonth() + 3) / 3),
        "S": d.getMilliseconds()
    };
    if (/(y+)/.test(format)) {
        format = format.replace(RegExp.$1, (d.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
        }
    }
    return format;
}

function today() {
    var dd = new Date();
    return formatDate(dd, 'yyyy-MM-dd');
}

function countDay(dayCount) {
    var dd = new Date();
    if (dayCount < 0) {
        dd.setDate(dd.getDate()+dayCount+1);//获取AddDayCount天前的日期
    } else {
        dd.setDate(dd.getDate()+dayCount-1);//获取AddDayCount天后的日期
    }
    var y = dd.getFullYear();
    var m = (dd.getMonth()+1)<10?"0"+(dd.getMonth()+1):(dd.getMonth()+1);//获取当前月份的日期，不足10补0
    var d = dd.getDate()<10?"0"+dd.getDate():dd.getDate();//获取当前几号，不足10补0
    return y+"-"+m+"-"+d;
}

isNullOrEmpty = function (obj) {
    if ((typeof (obj) == "string" && obj == "") || obj == null || obj == undefined) {
        return true;
    } else {
        return false;
    }
}

isNotNullOrEmpty = function (obj) {
    if ((typeof (obj) == "string" && obj == "") || obj == null || obj == undefined) {
        return false;
    } else {
        return true;
    }
}

checkedArray = function (id) {
    var isOK = true;
    if (id == undefined || id == "" || id == 'null' || id == 'undefined') {
        isOK = false;
        dialogMsg('您没有选中任何数据项！');
    }
    return isOK;
}

checkedRow = function (id) {
    var isOK = true;
    if (id == undefined || id == "" || id == 'null' || id == 'undefined') {
        isOK = false;
        dialogMsg('您没有选中任何数据项！');
    } else if (id.length > 1) {
        isOK = false;
        dialogMsg('您只能选择一条数据项！');
    }
    return isOK;
}

reload = function () {
    location.reload();
    return false;
}

dialogOpen = function(opt){
	var defaults = {
		id : 'layerForm',
		title : '',
		width: '',
		height: '',
		url : null,
		scroll : false,
		data : {},
		btn: ['确定', '取消'],
		success: function(){},
		yes: function(){}
	}
	var option = $.extend({}, defaults, opt), content = null;
	if(option.scroll){
		content = [option.url]
	}else{
		content = [option.url, 'no']
	}
	top.layer.open({
	  	type : 2,
	  	id : option.id,
		title : option.title,
		closeBtn : 1,
		anim: -1,
		isOutAnim: false,
		shadeClose : false,
		shade : 0.3,
		area : [option.width, option.height],
		content : content,
		btn: option.btn,
		success: function(){
			option.success(option.id);
		},
		yes: function(){
			option.yes(option.id);
		}
    });
}

dialogContent = function(opt){
	var defaults = {
		title : '系统窗口',
		width: '',
		height: '',
		content : null,
		data : {},
		btn: ['确定', '取消'],
		success: null,
		yes: null
	}
	var option = $.extend({}, defaults, opt);
	return top.layer.open({
	  	type : 1,
		title : option.title,
		closeBtn : 1,
		anim: -1,
		isOutAnim: false,
		shadeClose : false,
		shade : 0.3,
		area : [option.width, option.height],
		shift : 5,
		content : option.content,
		btn: option.btn,
		success: option.success,
		yes: option.yes
    });
}

dialogAjax = function(opt){
	var defaults = {
		title : '系统窗口',
		width: '',
		height: '',
		url : null,
		data : {},
		btn: ['确定', '取消'],
		success: null,
		yes: null
	}
	var option = $.extend({}, defaults, opt);
	$.post(option.url, null, function(content){
		layer.open({
		  	type : 1,
			title : option.title,
			closeBtn : 1,
			anim: -1,
			isOutAnim: false,
			shadeClose : false,
			shade : 0.3,
			area : [option.width, option.height],
			shift : 5,
			content : content,
			btn: option.btn,
			success: option.success,
			yes: option.yes
	    });
	});
}

dialogAlert = function (content, type) {
	var msgType = {
		success:1,
		error:2,
		warn:3,
		info:7
	};
	if(isNullOrEmpty(type)){
		type='info';
	}
	top.layer.alert(content, {
        icon: msgType[type],
        title: "系统提示",
        anim: -1,
        btnAlign: 'c',
		isOutAnim: false
    });
}

dialogConfirm = function (content, callBack) {
	top.layer.confirm(content, {
		area: '338px',
		icon: 7,
        anim: -1,
		isOutAnim: false,
        title: "系统提示",
        btn: ['确认', '取消'],
        btnAlign: 'c',
    	yes: callBack
    });
}

dialogMsg = function(msg, type) {
	var msgType = {
		success:1,
		error:2,
		warn:3,
		info:7
	};
	if(isNullOrEmpty(type)){
		type='info';
	}
	top.layer.msg(msg, {
		icon: msgType[type],
		time: 2000
	}); 
}

dialogClose = function() {
	var index = top.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
	top.layer.close(index); //再执行关闭 
}

dialogLoading = function(flag) {
	if(flag){
		top.layer.load(0, {
			shade: [0.1,'#fff'],
			time: 2000
		});
	}else{
		top.layer.closeAll('loading');
	}
}

dialogToastr = function(msg) {
    top.layer.msg(msg);
}

dialogTip = function(msg, dom) {
    top.layer.tips(msg, dom);
}

$.fn.GetWebControls = function (keyValue) {
    var reVal = "";
    $(this).find('input,select,textarea').each(function (r) {
        var id = $(this).attr('id');
        var type = $(this).attr('type');
        switch (type) {
            case "checkbox":
                if ($("#" + id).is(":checked")) {
                    reVal += '"' + id + '"' + ':' + '"1",'
                } else {
                    reVal += '"' + id + '"' + ':' + '"0",'
                }
                break;
            default:
                var value = $("#" + id).val();
                if (value == "") {
                    value = "&nbsp;";
                }
                reVal += '"' + id + '"' + ':' + '"' + $.trim(value) + '",'
                break;
        }
    });
    reVal = reVal.substr(0, reVal.length - 1);
    if (!keyValue) {
        reVal = reVal.replace(/&nbsp;/g, '');
    }
    reVal = reVal.replace(/\\/g, '\\\\');
    reVal = reVal.replace(/\n/g, '\\n');
    var postdata = jQuery.parseJSON('{' + reVal + '}');
    return postdata;
};

$.fn.SetWebControls = function (data) {
    var $id = $(this)
    for (var key in data) {
        var id = $id.find('#' + key);
        if (id.attr('id')) {
            var type = id.attr('type');
            var value = $.trim(data[key]).replace(/&nbsp;/g, '');
            switch (type) {
                case "checkbox":
                    if (value == 1) {
                        id.attr("checked", 'checked');
                    } else {
                        id.removeAttr("checked");
                    }
                    break;
                default:
                    id.val(value);
                    break;
            }
        }
    }
}

$.currentIframe = function () {
    return $(window.parent.document).contents().find('#main')[0].contentWindow;
}

/**
 * 根据ajax地址初始化select2选择器
 * @param opt
 * @returns {*}
 */
$.fn.selectBindEx = function(opt) {
    var $select = $(this);
    var defaults = {
        url: '',
        async: true,
        text: 'name',
        value: 'id',
        placeholder: '请选择...',
        selected: '',
        allowClear: false,
        theme: "bootstrap",
        language: "zh-CN",
        change: function(){}
    }
    if (opt.search === false) {
        opt.minimumResultsForSearch = 'Infinity';
    }
    var option = $.extend({}, defaults, opt);
    var selectControl = null;
    $.ajax({
        type: 'get',
        async: option.async,
        contentType : 'application/json',
        url: option.url,
        data: null,
        success: function(r) {
            if ($select.hasClass("select2-hidden-accessible")) {
                $select.empty();
                $select.select2('destroy');
            }
            selectControl = $select.select2(option);
            $.each(r, function(idx, item){
                selectControl.append("<option value='"+item[option.value]+"'>"+item[option.text]+"</option>");
            })
            $select.val(option.selected);
            $select.on('change', function() {
                option.change($select.val());
            });
        },
        dataType: 'json'
    });
    return selectControl;
}

/**
 * 初始化select2选择器
 * @param placeholder
 * @returns {*|void}
 */
$.fn.selectInitEx = function(placeholder, search) {
    var opt = {
        placeholder: placeholder,
        theme: "bootstrap",
        language: "zh-CN"
    }
    if (search === false) {
        opt.minimumResultsForSearch = 'Infinity';
    }
    return $(this).select2(opt);
}

/**
 * 富文本编辑器工具类
 * @type {{init: editor.init}}
 */
editorUtils = {
    init: function(opt) {
        var defaults = {
            element: '#editor',
            change: function(){}
        };
        var option = $.extend({}, defaults, opt);
        var editor = new window.wangEditor(option.element);
        editor.customConfig.uploadImgServer = '/editor/upload';
        editor.customConfig.onchange= function(html) {
            option.change(html);
        };
        editor.customConfig.customAlert = function(info) {
            dialogAlert(info, 'error');
        };
        editor.create();
        return editor;
    },
    set: function($editor, content) {
        $editor.txt.html(content);
    },
    get: function($editor) {
        return $editor.txt.html();
    },
    text: function($editor) {
        return $editor.txt.text();
    },
    append: function($editor, content) {
        $editor.txt.append(content)
    },
    clear: function($editor) {
        $editor.txt.clear()
    },
    hasContents: function ($editor) {
        var content = this.get($editor);
        return isNotNullOrEmpty(this.get($editor)) && "<p><br></p>" !== content;
    }
}

/**
 * switchery开关组件
 * @type {{}}
 * 选择器selector用于获取选择状态，开关instance用于设置状态，禁用，启用
 */
switchUtils = {
    init: function(opt) {
        var defaults = {
            selector: '#editor',
            size: 'small',
            single: true,
            change: function(){}
        };
        var option = $.extend({}, defaults, opt);
        var switchContainer = [], switchResults = [];
        if (option.single) {
            switchContainer.push(document.querySelector(option.selector));
        } else {
            switchContainer = document.querySelectorAll(option.selector);
        }
        $.each(switchContainer, function(idx, item) {
            var $switchery = new Switchery(item, option);
            var $item = $(item);
            var result = {selector: item, instance: $switchery};
            $item.on('change', function(event) {
                option.change(result);
            });
            switchResults.push(result);
        });
        if (option.single) {
            return switchResults[0];
        }
        return switchResults;
    },
    set: function($switch, checked) {
        $switch = $switch.instance;
        if ((checked && !$switch.isChecked()) || (!checked && $switch.isChecked())) {
            $switch.setPosition(true);
            $switch.handleOnchange(true);
        }
    },
    on: function($switch) {
        this.set($switch, true);
    },
    off: function($switch) {
        this.set($switch, false);
    },
    disable: function($switch) {
        $switch.instance.disable();
    },
    enable: function($switch) {
        $switch.instance.enable();
    },
    checked: function($switch) {
        return $switch.selector.checked;
    },
    data: function($switch, key) {
        return $($switch.selector).data(key);
    }
}

/** 页面loading **/
dialogLoading(true);

document.addEventListener('DOMContentLoaded', function(){
    dialogLoading(false)
});

setTimeout(function() {
    dialogLoading(false)
}, 1000);
