/**
 * 组织机构js
 */

$(function () {
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
    $('#dataGrid').bootstrapTreeTableEx({
        url: '../../sys/org/list?_' + $.now(),
        height: $(window).height() - 56,
        idField: 'orgId',
        treeShowField: 'name',
        parentIdField: 'parentId',
        columns: [
            {checkbox: true},
            {title: '编号', field: 'orgId', visible: false, width: '80px'},
            {title: '名称', field: 'name'},
            {title: '机构编码', field: 'code', width: '200px'},
            {title: '上级机构', field: 'parentName', width: '300px'},
            {title: '可用', field: 'status', width: '60px', formatter: function(value, row, index){
                    if(row.status === 0){
                        return '<i class="fa fa-toggle-off"></i>';
                    }
                    if(row.status === 1){
                        return '<i class="fa fa-toggle-on"></i>';
                    }
                }},
            {title: '排序', field: 'orderNum', width: '80px'},
            {
                title: '操作', formatter: function (value, row, index) {
                    var _html = '';
                    if (hasPermission('sys:org:edit')) {
                        _html += '<a href="javascript:;" onclick="vm.edit(\'' + row.orgId + '\')" title="编辑"><i class="fa fa-pencil"></i></a>';
                    }
                    if (hasPermission('sys:org:remove')) {
                        _html += '<a href="javascript:;" onclick="vm.remove(false,\'' + row.orgId + '\')" title="删除"><i class="fa fa-trash-o"></i></a>';
                    }
                    if (hasPermission('sys:org:save')) {
                        _html += '<a href="javascript:;" onclick="vm.saveChild(\'' + row.orgId + '\',\'' + row.name + '\')" title="新增下级机构"><i class="fa fa-plus-square"></i></a>';
                    }
                    return _html;
                }
            }
        ]
    });
}

var vm = new Vue({
	el:'#dpLTE',
	methods : {
		load: function() {
            $('#dataGrid').bootstrapTable('refresh');
		},
		save: function() {
			dialogOpen({
				title: '新增机构',
				url: 'base/org/add.html?_' + $.now(),
				width: '500px',
				height: '320px',
				yes : function(iframeId) {
					top.frames[iframeId].vm.acceptClick();
				},
			});
		},
        saveChild: function (orgId, name) {
            dialogOpen({
                title: '新增机构',
                url: 'base/org/add.html?_' + $.now(),
                width: '500px',
                height: '320px',
                success: function (iframeId) {
                    top.frames[iframeId].vm.org.parentId = orgId;
                    top.frames[iframeId].vm.org.parentName = name;
                },
                yes: function (iframeId) {
                    top.frames[iframeId].vm.acceptClick();
                },
            });
        },
        edit: function (orgId) {
            dialogOpen({
                title: '编辑机构',
                url: 'base/org/edit.html?_' + $.now(),
                width: '500px',
                height: '320px',
                success: function (iframeId) {
                    top.frames[iframeId].vm.org.orgId = orgId;
                    top.frames[iframeId].vm.setForm();
                },
                yes: function (iframeId) {
                    top.frames[iframeId].vm.acceptClick();
                },
            });
        },
        remove: function (batch, orgId) {
            var ids = [];
            if (batch) {
                var ck = $('#dataGrid').bootstrapTable('getSelections');
                if (!checkedArray(ck)) {
                    return false;
                }
                $.each(ck, function (idx, item) {
                    ids[idx] = item.orgId;
                });
            } else {
                ids.push(orgId);
            }
            $.RemoveForm({
                url: '../../sys/org/remove?_' + $.now(),
                param: ids,
                success: function (data) {
                    vm.load();
                }
            });
        }
	}
})
