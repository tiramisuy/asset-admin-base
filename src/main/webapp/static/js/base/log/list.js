/**
 * 系统日志js
 */

$(function () {
	initialPage();
	getGrid();
});

function initialPage() {
    vm.dateRangeSelect(1);
	$(window).resize(function() {
		$('#dataGrid').bootstrapTable('resetView', {height: $(window).height()-56});
	});
    //日期选择
    laydate.render({
        elem: '#dateRange',
        range: true,
        theme: '#3C8DBC',
        eventElem: '#dateRange',
        trigger: 'click',
        done: function(value, date, endDate){
            vm.dateRangeSelect(0);
            vm.dateRange = value;
            vm.startDate = formatDate(date.year + '-' + date.month + '-' + date.date, 'yyyy-MM-dd');
            vm.endDate = formatDate(endDate.year + '-' + endDate.month + '-' + endDate.date, 'yyyy-MM-dd');
        }
    });
}

function getGrid() {
	$('#dataGrid').bootstrapTableEx({
		url: '../../sys/log/list?_' + $.now(),
		height: $(window).height()-56,
		queryParams: function(params){
			params.username = vm.keyword;
            params.startDate = vm.startDate;
            params.endDate = vm.endDate;
			return params;
		},
		detailView: true,
		detailFormatter: function(index, row) {
			var _html = '<p>操作用户ID：'+ row.userId +'</p>' + 
				        '<p>执行方法：'+ row.method +'</p>' +
						'<p>请求参数：'+ row.params +'</p>';
			return _html;
		},
		columns: [{
			checkbox: true
		}, {
			field : "id",
			title : "编号",
			width : "50px"
		}, {
			field : "username",
			title : "用户名",
			width : "150px"
		}, {
			field : "operation",
			title : "操作",
			width : "150px"
		}, {
			field : "time",
			title : "响应时间(ms)",
			width : "130px"
		}, {
			field : "ip",
			title : "IP地址",
			width : "130px"
		},  {
			field : "gmtCreate",
			title : "创建时间"
		}, {
            title : "操作",
            formatter : function (value, row, index) {
                var _html = '';
                if (hasPermission('sys:log:remove')) {
                    _html += '<a href="javascript:;" onclick="vm.remove(false,\''+row.id+'\')" title="删除"><i class="fa fa-trash-o"></i></a>';
                }
                return _html;
            }
        }],
        onPostBody: function() {
            $('#dataGrid').bootstrapTable('expandAllRows');
        }
	})
}

var vm = new Vue({
	el:'#dpLTE',
	data: {
		keyword: null,
        startDate : null,
        endDate : null,
        dateRangeText : '时间范围',
        dateRange : null
	},
	methods : {
		load: function() {
			$('#dataGrid').bootstrapTable('refresh');
		},
        dateRangeSelect : function(count) {
            if(count==1){
                vm.dateRangeText = '最近一天';
                vm.startDate = countDay(-1);
                vm.endDate = today();
                vm.dateRange = vm.startDate + ' - ' + vm.endDate;
            }else if(count ==7){
                vm.dateRangeText = '最近一周';
                vm.startDate = countDay(-7);
                vm.endDate = today();
                vm.dateRange = vm.startDate + ' - ' + vm.endDate;
            }else if(count ==30){
                vm.dateRangeText = '最近一月';
                vm.startDate = countDay(-30);
                vm.endDate = today();
                vm.dateRange = vm.startDate + ' - ' + vm.endDate;
            }else{
                vm.dateRangeText = '时间范围';
                vm.startDate = '';
                vm.endDate = '';
                vm.dateRange = '';
            }
        },
        remove: function(batch, id) {
            var ids = [];
            if (batch) {
                var ck = $('#dataGrid').bootstrapTable('getSelections');
                if(!checkedArray(ck)){
                    return false;
                }
                $.each(ck, function(idx, item){
                    ids[idx] = item.id;
                });
            } else {
                ids.push(id);
            }
            $.RemoveForm({
                url: '../../sys/log/remove?_' + $.now(),
                param: ids,
                success: function(data) {
                    vm.load();
                }
            });
        },
		clear: function() {
			$.ConfirmAjax({
				msg : "您确定要清空日志吗？",
				url: '../../sys/log/clear?_' + $.now(),
		    	success: function(data) {
		    		vm.load();
		    	}
			});
		}
	}
})