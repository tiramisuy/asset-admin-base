/**
 * 通用字典js
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
        url: '../../sys/macro/list?_' + $.now(),
        height: $(window).height() - 56,
        idField: 'macroId',
        treeShowField: 'name',
        parentIdField: 'typeId',
        columns: [
            {checkbox: true},
            {title: '编号', field: 'macroId', visible: false, width: '50px'},
            {title: '参数名', field: 'name', width: '180px'},
            {title: '参数值', field: 'value', width: '180px'},
            {title: '所属类别', field: 'typeName', width: '100px'},
            {
                title: '类型',
                field: 'type',
                width: '60px',
                formatter: function (value, row, index) {
                    if (row.type === 0) {
                        return '<span class="label label-primary">目录</span>';
                    }
                    if (row.type === 1) {
                        return '<span class="label label-warning">参数</span>';
                    }
                }
            },
            {title: '排序', field: 'orderNum', align: 'center', valign: 'middle', width: '50px'},
            {
                title: '可用',
                field: 'status',
                width: '60px',
                formatter: function (value, row, index) {
                    if(value === 0){
                        return '<input type="checkbox" class="js-switch" data-id="'+row.macroId+'">';
                    }else if(value === 1){
                        return '<input type="checkbox" class="js-switch" data-id="'+row.macroId+'" checked>';
                    }
                }
            },
            {title: '备注', field: 'remark'},
            {title: '操作', formatter: function(value, row, index) {
                    var _html = '';
                    if (hasPermission('sys:macro:edit')) {
                        _html += '<a href="javascript:;" onclick="vm.edit(\''+row.macroId+'\')" title="编辑"><i class="fa fa-pencil"></i></a>';
                    }
                    if (hasPermission('sys:macro:remove')) {
                        _html += '<a href="javascript:;" onclick="vm.remove(false,\''+row.macroId+'\')" title="删除"><i class="fa fa-trash-o"></i></a>';
                    }
                    if (hasPermission('sys:macro:save') && row.type === 0) {
                        _html += '<a href="javascript:;" onclick="vm.saveChild(\''+row.macroId+'\',\''+row.name+'\')" title="新增字典数据"><i class="fa fa-navicon"></i></a>';
                    }
                    return _html;
                }
            }
        ],
        onPostBody: function() {
            switchUtils.init({
                selector: '.js-switch',
                single: false,
                change: function(switchContainer) {
                    var url = '../../sys/macro/disable?_' + $.now();
                    if (switchUtils.checked(switchContainer)) {
                        url = '../../sys/macro/enable?_' + $.now();
                    }
                    $.AjaxForm({
                        url: url,
                        param: switchUtils.data(switchContainer, "id"),
                        success: function(data) {
                            vm.load();
                        }
                    });
                }
            });
        }
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
				title: '新增字典',
				url: 'base/macro/add.html?_' + $.now(),
				width: '600px',
				height: '420px',
				scroll : true,
				yes : function(iframeId) {
					top.frames[iframeId].vm.acceptClick();
				},
			});
		},
        saveChild: function(macroId, name) {
            dialogOpen({
                title: '新增字典',
                url: 'base/macro/add.html?_' + $.now(),
                width: '600px',
                height: '420px',
                scroll : true,success: function(iframeId){
                    top.frames[iframeId].vm.macro.typeId = macroId;
                    top.frames[iframeId].vm.macro.typeName = name;
                },
                yes : function(iframeId) {
                    top.frames[iframeId].vm.acceptClick();
                },
            });
        },
		edit: function(macroId) {
            dialogOpen({
                title: '编辑字典',
                url: 'base/macro/edit.html?_' + $.now(),
                width: '600px',
                height: '420px',
                scroll : true,
                success: function(iframeId){
                    top.frames[iframeId].vm.macro.macroId = macroId;
                    top.frames[iframeId].vm.setForm();
                },
                yes : function(iframeId) {
                    top.frames[iframeId].vm.acceptClick();
                },
            });
		},
        remove: function(batch, macroId) {
            var ids = [];
            if (batch) {
                var ck = $('#dataGrid').bootstrapTable('getSelections');
                if(!checkedArray(ck)){
                    return false;
                }
                $.each(ck, function(idx, item){
                    ids[idx] = item.macroId;
                });
            } else {
                ids.push(macroId);
            }
            $.RemoveForm({
                url: '../../sys/macro/remove?_' + $.now(),
                param: ids,
                success: function(data) {
                    vm.load();
                }
            });
        }
	}
})