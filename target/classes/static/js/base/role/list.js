/**
 * 角色管理js
 */

$(function () {
	initialPage();
	getGrid();
});

function initialPage() {
	$(window).resize(function() {
		$('#dataGrid').bootstrapTable('resetView', {height: $(window).height()-56});
	});
}

function getGrid() {
	$('#dataGrid').bootstrapTableEx({
		url: '../../sys/role/list?_' + $.now(),
		height: $(window).height()-56,
		queryParams: function(params){
			params.roleName = vm.keyword;
			return params;
		},
		columns: [{
			checkbox: true
		}, {
			field : "roleId",
			title : "编号",
			width : "50px"
		}, {
			field : "roleName",
			title : "角色名称",
			width : "200px"
		}, {
			field : "roleSign",
			title : "角色标识",
			width : "200px"
		}, {
			field : "orgName",
			title : "所属机构",
			width : "200px"
		}, {
			field : "remark",
			title : "备注",
			width : "250px"
		}, {
			field : "gmtCreate",
			title : "创建时间"
		}, {
            title: "操作",
            formatter: function (value, row, index) {
                var _html = '';
                if (hasPermission('sys:role:edit')) {
                    _html += '<a href="javascript:;" onclick="vm.edit(\'' + row.roleId + '\')" title="编辑"><i class="fa fa-pencil"></i></a>';
                }
                if (hasPermission('sys:role:remove')) {
                    _html += '<a href="javascript:;" onclick="vm.remove(false,\'' + row.roleId + '\')" title="删除"><i class="fa fa-trash-o"></i></a>';
                }
                var perms = cntPermission(['sys:role:authorizeOpt', 'sys:role:authorizeData']);
                if (perms > 0) {
                    var opts = [];
                    if (hasPermission('sys:role:authorizeOpt')) {
                        opts.push('<button class="btn btn-default btn-xs" onclick="vm.authorizeOpt(\'' + row.roleId + '\')"><i class="fa fa-gavel"></i>操作权限</button>');
                    }
                    if (hasPermission('sys:role:authorizeData')) {
                        opts.push('<button class="btn btn-default btn-xs" onclick="vm.authorizeData(\'' + row.roleId + '\')"><i class="fa fa-eye"></i>数据权限</button>');
                    }
                    _html += morePermissionOpt(perms, opts);
                }
                return _html;
            }
        }]
	})
}

var vm = new Vue({
	el:'#dpLTE',
	data: {
		keyword: null
	},
	methods : {
		load: function() {
			$('#dataGrid').bootstrapTable('refresh');
		},
		save: function() {
			dialogOpen({
				title: '新增角色',
				url: 'base/role/add.html?_' + $.now(),
				width: '420px',
				height: '350px',
				yes : function(iframeId) {
					top.frames[iframeId].vm.acceptClick();
				},
			});
		},
        edit: function (roleId) {
            dialogOpen({
                title: '编辑角色',
                url: 'base/role/edit.html?_' + $.now(),
                width: '420px',
                height: '350px',
                success: function (iframeId) {
                    top.frames[iframeId].vm.role.roleId = roleId;
                    top.frames[iframeId].vm.setForm();
                },
                yes: function (iframeId) {
                    top.frames[iframeId].vm.acceptClick();
                }
            });
        },
        remove: function (batch, roleId) {
            var ids = [];
            if (batch) {
                var ck = $('#dataGrid').bootstrapTable('getSelections');
                if (!checkedArray(ck)) {
                    return false;
                }
                $.each(ck, function (idx, item) {
                    ids[idx] = item.roleId;
                });
            } else {
                ids.push(roleId);
            }
            $.RemoveForm({
                url: '../../sys/role/remove?_' + $.now(),
                param: ids,
                success: function (data) {
                    vm.load();
                }
            });
        },
        authorizeOpt: function (roleId) {
            dialogOpen({
                title: '操作权限',
                url: 'base/role/opt.html?_' + $.now(),
                scroll: true,
                width: "300px",
                height: "450px",
                success: function (iframeId) {
                    top.frames[iframeId].vm.role.roleId = roleId;
                    top.frames[iframeId].vm.setForm();
                },
                yes: function (iframeId) {
                    top.frames[iframeId].vm.acceptClick();
                }
            })
        },
        authorizeData: function (roleId) {
            dialogOpen({
                title: '数据权限',
                url: 'base/role/data.html?_' + $.now(),
                scroll: true,
                width: "300px",
                height: "450px",
                success: function (iframeId) {
                    top.frames[iframeId].vm.role.roleId = roleId;
                    top.frames[iframeId].vm.setForm();
                },
                yes: function (iframeId) {
                    top.frames[iframeId].vm.acceptClick();
                }
            })
        }
	}
})