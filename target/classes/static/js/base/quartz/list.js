/**
 * 定时任务js
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
}

function getGrid() {
	$('#dataGrid').bootstrapTableEx({
		url : '../../quartz/job/list?_' + $.now(),
		height : $(window).height() - 56,
		queryParams : function(params) {
			params.name = vm.keyword;
			return params;
		},
		columns : [ {
			checkbox : true
		}, {
			field : "jobId",
			title : "编号",
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
			field : "cronExpression",
			title : "表达式",
			width : "130px"
		}, {
			field : "status",
			title : "状态",
			width : "60px",
			formatter : function(value, row, index) {
                if (row.status === 1) {
                	if (hasPermission('quartz:job:disable')) {
                        return '<input type="checkbox" class="js-switch" data-id="'+row.jobId+'" checked>';
					} else {
                        return '<i class="fa fa-toggle-on"></i>';
                    }
                }
                if (row.status === 0) {
                    if (hasPermission('quartz:job:enable')) {
                        return '<input type="checkbox" class="js-switch" data-id="'+row.jobId+'">';
                    } else {
                        return '<i class="fa fa-toggle-off"></i>';
                    }
                }
			}
		}, {
			field : "gmtCreate",
			title : "创建时间",
			width : "200px"
		}, {
			field : "remark",
			title : "备注"
		}, {
            title : "操作",
            formatter : function(value, row, index) {
                var _html = '';
                if (hasPermission('quartz:job:edit')) {
                    _html += '<a href="javascript:;" onclick="vm.edit(\''+row.jobId+'\')" title="编辑"><i class="fa fa-pencil"></i></a>';
                }
                if (hasPermission('quartz:job:run')) {
                    _html += '<a href="javascript:;" onclick="vm.run(\''+row.jobId+'\')" title="立即运行一次"><i class="fa fa-forward"></i></a>';
                }
                if (hasPermission('quartz:job:remove')) {
                    _html += '<a href="javascript:;" onclick="vm.remove(false,\''+row.jobId+'\')" title="删除"><i class="fa fa-trash-o"></i></a>';
                }
                if (hasPermission('quartz:log:list')) {
                    _html += '<a href="log.html?jobId='+row.jobId+'" title="调度日志"><i class="fa fa-file-text-o"></i></a>';
                }
                return _html;
            }
        } ],
        onPostBody: function() {
            switchUtils.init({
                selector: '.js-switch',
                single: false,
                change: function(switchContainer) {
                    var ids = [switchUtils.data(switchContainer, "id")];
                    var url = '../../quartz/job/disable?_' + $.now();
                    if (switchUtils.checked(switchContainer)) {
                        url = '../../quartz/job/enable?_' + $.now();
                    }
                    $.AjaxForm({
                        url: url,
                        param: ids,
                        success: function(data) {
                            vm.load();
                        }
                    });

                }
            });
        }
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
		save : function() {
			dialogOpen({
				title : '新增任务',
				url : 'base/quartz/add.html?_' + $.now(),
				width : '450px',
				height : '396px',
				scroll : true,
				yes : function(iframeId) {
					top.frames[iframeId].vm.acceptClick();
				},
			});
		},
        edit : function(jobId) {
            dialogOpen({
                title : '编辑任务',
                url : 'base/quartz/edit.html?_' + $.now(),
                width : '450px',
                height : '396px',
                scroll : true,
                success : function(iframeId) {
                    top.frames[iframeId].vm.job.jobId = jobId;
                    top.frames[iframeId].vm.setForm();
                },
                yes : function(iframeId) {
                    top.frames[iframeId].vm.acceptClick();
                },
            });
        },
        remove : function(batch, jobId) {
            var ids = [];
            if (batch) {
                var ck = $('#dataGrid').bootstrapTable('getSelections');
                if (!checkedArray(ck)) {
                    return false;
                }
                $.each(ck, function(idx, item) {
                    ids[idx] = item.jobId;
                });
            } else {
                ids.push(jobId);
            }
            $.RemoveForm({
                url : '../../quartz/job/remove?_' + $.now(),
                param : ids,
                success : function(data) {
                    vm.load();
                }
            });
        },
        run : function(jobId) {
            var ids = [];
            ids.push(jobId);
            $.ConfirmForm({
                msg : '您是否要立即运行所选任务吗？',
                url : '../../quartz/job/run?_' + $.now(),
                param : ids,
                success : function(data) {
                    vm.load();
                }
            });
        }
	}
})