/**
 * 用户管理js
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
		url : '../../sys/user/list?_' + $.now(),
		height : $(window).height() - 56,
		queryParams : function(params) {
			params.username = vm.keyword;
			return params;
		},
		columns : [ {
			checkbox : true
		}, {
			field : "userId",
			title : "编号",
			width : "50px"
		}, {
			field : "username",
			title : "用户名",
			width : "200px"
		}, {
			field : "orgName",
			title : "所属机构",
			width : "200px"
		}, {
			field : "email",
			title : "邮箱",
			width : "300px"
		}, {
			field : "mobile",
			title : "手机号",
			width : "130px"
		}, {
			field : "status",
			title : "状态",
			width : "60px",
            formatter : function(value , row, index) {
                if(value === 0){
                	if (hasPermission('sys:user:disable')) {
                        return '<input type="checkbox" class="js-switch" data-id="'+row.userId+'">';
					} else {
                        return '<i class="fa fa-toggle-off"></i>';
					}

                }
                if(value === 1){
                	if (hasPermission('sys:user:enable')) {
                        return '<input type="checkbox" class="js-switch" data-id="'+row.userId+'" checked>';
					} else {
                        return '<i class="fa fa-toggle-on"></i>';
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
                if (hasPermission('sys:user:edit')) {
                    _html += '<a href="javascript:;" onclick="vm.edit(\''+row.userId+'\')" title="编辑"><i class="fa fa-pencil"></i></a>';
                }
                if (hasPermission('sys:user:resetPassword')) {
                    _html += '<a href="javascript:;" onclick="vm.reset(\''+row.userId+'\')" title="重置密码"><i class="fa fa-key"></i></a>';
                }
                if (hasPermission('sys:user:remove')) {
                    _html += '<a href="javascript:;" onclick="vm.remove(false,\''+row.userId+'\')" title="删除"><i class="fa fa-trash-o"></i></a>';
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
                    var url = '../../sys/user/disable?_' + $.now();
                    if (switchUtils.checked(switchContainer)) {
                        url = '../../sys/user/enable?_' + $.now();
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
				title : '新增用户',
				url : 'base/user/add.html?_' + $.now(),
                width : '500px',
                height : '456px',
				scroll : true,
				yes : function(iframeId) {
					top.frames[iframeId].vm.acceptClick();
				},
			});
		},
        edit : function(userId) {
            dialogOpen({
                title : '编辑用户',
                url : 'base/user/edit.html?_' + $.now(),
                width : '500px',
                height : '456px',
                scroll : true,
                success : function(iframeId) {
                    top.frames[iframeId].vm.user.userId = userId;
                    top.frames[iframeId].vm.setForm();
                },
                yes : function(iframeId) {
                    top.frames[iframeId].vm.acceptClick();
                },
            });
        },
        remove : function(batch, userId) {
            var ids = [];
            if (batch) {
                var ck = $('#dataGrid').bootstrapTable('getSelections');
                if (!checkedArray(ck)) {
                    return false;
                }
                $.each(ck, function(idx, item) {
                    ids[idx] = item.userId;
                });
            } else {
                ids.push(userId);
            }
            $.RemoveForm({
                url : '../../sys/user/remove?_' + $.now(),
                param : ids,
                success : function(data) {
                    vm.load();
                }
            });
        },
        reset : function(userId) {
            dialogOpen({
                title : '重置密码',
                url : 'base/user/reset.html?_' + $.now(),
                width : '400px',
                height : '220px',
                success : function(iframeId) {
                    top.frames[iframeId].vm.user.userId = userId;
                },
                yes : function(iframeId) {
                    top.frames[iframeId].vm.acceptClick();
                },
            });
        }
	}
})