/**
 * 系统菜单js
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
        url: '../../sys/menu/list?_' + $.now(),
        height: $(window).height() - 56,
        idField: 'menuId',
        treeShowField: 'name',
        parentIdField: 'parentId',
        columns: [
            {checkbox: true},
            {title: '编号', field: 'menuId', visible: false, width: '50px'},
            {title: '名称', field: 'name', width: '180px'},
            {title: '上级菜单', field: 'parentName', width: '100px'},
            {
                title: '图标',
                field: 'icon',
                width: '50px',
                formatter: function (value, row, index) {
                    return row.icon == null ? '' : '<i class="' + row.icon + ' fa-lg"></i>';
                }
            },
            {
                title: '类型',
                field: 'type',
                width: '60px',
                formatter: function (value, row, index) {
                    if (row.type === 0) {
                        return '<span class="label label-primary">目录</span>';
                    }
                    if (row.type === 1) {
                        return '<span class="label label-success">菜单</span>';
                    }
                    if (row.type === 2) {
                        return '<span class="label label-warning">按钮</span>';
                    }
                }
            },
            {title: '排序', field: 'orderNum', width: '50px'},
            {title: '菜单URL', field: 'url', width: '200px'},
            {title: '授权标识', field: 'perms'},
            {title: '操作', formatter: function(value, row, index) {
                    var _html = '';
                    if (hasPermission('sys:menu:edit')) {
                        _html += '<a href="javascript:;" onclick="vm.edit(\''+row.menuId+'\')" title="编辑"><i class="fa fa-pencil"></i></a>';
                    }
                    if (hasPermission('sys:area:remove')) {
                        _html += '<a href="javascript:;" onclick="vm.remove(false,\''+row.menuId+'\')" title="删除"><i class="fa fa-trash-o"></i></a>';
                    }
                    if (hasPermission('sys:menu:save') && row.type !== 2) {
                        _html += '<a href="javascript:;" onclick="vm.saveChild(\''+row.menuId+'\',\''+row.name+'\')" title="新增下级菜单"><i class="fa fa-plus-square"></i></a>';
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
				title: '新增菜单',
				url: 'base/menu/add.html?_' + $.now(),
				width: '600px',
				height: '420px',
				scroll : true,
				yes : function(iframeId) {
					top.frames[iframeId].vm.acceptClick();
				},
			});
		},
        saveChild: function(menuId, name) {
            dialogOpen({
                title: '新增菜单',
                url: 'base/menu/add.html?_' + $.now(),
                width: '600px',
                height: '420px',
                scroll : true,
                success: function(iframeId){
                    top.frames[iframeId].vm.menu.parentId = menuId;
                    top.frames[iframeId].vm.menu.parentName = name;
                },
                yes : function(iframeId) {
                    top.frames[iframeId].vm.acceptClick();
                },
            });
        },
        edit: function(menuId) {
            dialogOpen({
                title: '编辑菜单',
                url: 'base/menu/edit.html?_' + $.now(),
                width: '600px',
                height: '420px',
                scroll : true,
                success: function(iframeId){
                    top.frames[iframeId].vm.menu.menuId = menuId;
                    top.frames[iframeId].vm.setForm();
                },
                yes : function(iframeId) {
                    top.frames[iframeId].vm.acceptClick();
                },
            });
        },
        remove: function(batch, menuId) {
            var ids = [];
            if (batch) {
                var ck = $('#dataGrid').bootstrapTable('getSelections');
                if(!checkedArray(ck)){
                    return false;
                }
                $.each(ck, function(idx, item){
                    ids[idx] = item.menuId;
                });
            } else {
                ids.push(menuId);
            }
            $.RemoveForm({
                url: '../../sys/menu/remove?_' + $.now(),
                param: ids,
                success: function(data) {
                    vm.load();
                }
            });

        }
	}
})
