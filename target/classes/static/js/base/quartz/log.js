/**
 * 日志列表-定时任务js
 */

$(function() {
	initialPage();
	getGrid();
});

function initialPage() {
	$(window).resize(function() {
		$('#dataGrid').bootstrapTable('resetView', {
			height : $(window).height() - 56
		});
	});
    vm.jobId = url("jobId");
}

function getGrid() {
	$('#dataGrid').bootstrapTableEx({
		url : '../../quartz/job/log/list?_' + $.now(),
		height : $(window).height() - 56,
		queryParams : function(params) {
			params.name = vm.keyword;
			return params;
		},
		detailView: true,
		detailFormatter: function(index, row) {
			var _html = '<p>错误信息：'+ row.error +'</p>';
			if(isNullOrEmpty(row.error)) {
				_html = '<p>错误信息：执行成功，无错误信息！</p>';
			}
			return _html;
		},
		columns : [ {
			checkbox : true
		}, {
			field : "logId",
			title : "日志编号",
			width : "50px"
		}, {
			field : "jobId",
			title : "任务编号",
			width : "50px"
		}, {
			field : "beanName",
			title : "类名",
			width : "200px"
		}, {
			field : "methodName",
			title : "方法名",
			width : "200px"
		}, {
			field : "params",
			title : "参数",
			width : "300px"
		}, {
			field : "status",
			title : "状态",
			width : "60px",
			formatter : function(value, row, index) {
				if (value == '0') {
					return '<span class="label label-danger">失败</span>';
				} else if (value == '1') {
					return '<span class="label label-success">成功</span>';
				}
			}
		}, {
			field : "times",
			title : "耗时(ms)",
			width : "80px"
		}, {
			field : "gmtCreate",
			title : "执行时间"
		}]
	})
}

var vm = new Vue({
	el : '#dpLTE',
	data : {
		keyword : null
	},
	methods : {
		load : function() {
			$('#dataGrid').bootstrapTable('refresh');
		},
		remove : function() {
			var ck = $('#dataGrid').bootstrapTable('getSelections'), ids = [];
			if (checkedArray(ck)) {
				$.each(ck, function(idx, item) {
					ids[idx] = item.logId;
				});
				$.RemoveForm({
					url : '../../quartz/job/log/remove?_' + $.now(),
					param : ids,
					success : function(data) {
						vm.load();
					}
				});
			}
		},
		clear: function() {
			$.ConfirmAjax({
				msg : "您确定要清空日志吗？",
				url: '../../quartz/job/log/clear?_' + $.now(),
		    	success: function(data) {
		    		vm.load();
		    	}
			});
		}
	}
})