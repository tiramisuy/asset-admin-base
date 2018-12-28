/**
 * 行政区域js
 */

$(function() {
	initialPage();
	getGrid();
});

function initialPage() {
	$("#treePanel").css('height', $(window).height()-54);
	$(window).resize(function() {
		$("#treePanel").css('height', $(window).height()-54);
		$('#dataGrid').bootstrapTable('resetView', {
			height : $(window).height() - 108
		});
	});
}

function getGrid() {
	$('#dataGrid').bootstrapTableEx({
		url : '../../sys/area/list?_' + $.now(),
		height : $(window).height() - 108,
		queryParams : function(params) {
			params.name = vm.keyword;
			params.parentCode = vm.parentCode;
			return params;
		},
		pagination : false,
		columns : [ {
			checkbox : true
		}, {
			field : "areaId",
			title : "编号",
			width : "50px"
		}, {
			field : "areaCode",
			title : "区域代码",
			width : "100px"
		}, {
			field : "name",
			title : "区域名称",
			width : "200px"
		}, {
			field : "layer",
			title : "层级",
			width : "60px",
			formatter : function(value, row, index) {
				if (value == 1) {
					return '<span class="label label-primary">省级</span>';
				}
				if (value == 2) {
					return '<span class="label label-success">地市</span>';
				}
				if (value == 3) {
					return '<span class="label label-warning">区县</span>';
				}
			}
		}, {
			field : "orderNum",
			title : "排序",
			width : "60px",
			align : "center",
		}, {
			field : "status",
			title : "可用",
			width : "60px",
			align : "center",
			formatter : function(value, row, index) {
				if (value == 0) {
					return '<i class="fa fa-toggle-off"></i>';
				}
				if (value == 1) {
					return '<i class="fa fa-toggle-on"></i>';
				}
			}
		}, {
			field : "remark",
			title : "备注"
		}, {
            title : "操作",
            formatter : function (value, row, index) {
                var _html = '';
                if (hasPermission('sys:area:edit')) {
                    _html += '<a href="javascript:;" onclick="vm.edit(\''+row.areaId+'\')" title="编辑"><i class="fa fa-pencil"></i></a>';
                }
                if (hasPermission('sys:area:remove')) {
                    _html += '<a href="javascript:;" onclick="vm.remove(false,\''+row.areaId+'\')" title="删除"><i class="fa fa-trash-o"></i></a>';
                }
                if (hasPermission('sys:area:save')) {
                    _html += '<a href="javascript:;" onclick="vm.saveChild(\''+row.areaCode+'\',\''+row.name+'\')" title="新增下级区域"><i class="fa fa-plus-square"></i></a>';
                }
                return _html;
            }
        }  ]
	})
}

var setting = {
	async : {
		enable: true,
		type: "get",
		url: "../../sys/area/select",
		autoParam: ["areaCode"]
	},
	data : {
		simpleData : {
			enable : true,
			idKey : "areaCode",
			pIdKey : "parentCode",
			rootPId : "0"
		},
		key : {
			url : "nourl"
		}
	},
	callback : {
		onClick : function(event, treeId, treeNode) {
			vm.parentCode = treeNode.areaCode;
			vm.parentName = treeNode.name;
			vm.load();
		}
	}
};
var ztree;

var vm = new Vue({
	el : '#dpLTE',
	data : {
		keyword : null,
		parentCode : '0',
		parentName : null
	},
	methods : {
		load : function() {
			$('#dataGrid').bootstrapTable('refresh');
		},
		getArea : function(parentCode) {
			$.get('../../sys/area/select', {areaCode: parentCode},function(r) {
				ztree = $.fn.zTree.init($("#areaTree"), setting, r);
			})
		},
		save : function() {
			dialogOpen({
				title : '新增区域',
				url : 'base/area/add.html?_' + $.now(),
				width : '500px',
				height : '456px',
				success : function(iframeId) {
					top.frames[iframeId].vm.area.parentCode = vm.parentCode;
					top.frames[iframeId].vm.area.parentName = vm.parentName;
				},
				yes : function(iframeId) {
					top.frames[iframeId].vm.acceptClick();
				},
			});
		},
        saveChild : function(areaCode, name) {
            dialogOpen({
                title : '新增区域',
                url : 'base/area/add.html?_' + $.now(),
                width : '500px',
                height : '456px',
                success : function(iframeId) {
                    top.frames[iframeId].vm.area.parentCode = areaCode;
                    top.frames[iframeId].vm.area.parentName = name;
                },
                yes : function(iframeId) {
                    top.frames[iframeId].vm.acceptClick();
                }
            });
        },
        edit : function(areaId) {
            dialogOpen({
                title : '编辑区域',
                url : 'base/area/edit.html?_' + $.now(),
                width : '500px',
                height : '456px',
                success : function(iframeId) {
                    top.frames[iframeId].vm.area.areaId = areaId;
                    top.frames[iframeId].vm.setForm();
                },
                yes : function(iframeId) {
                    top.frames[iframeId].vm.acceptClick();
                }
            });
        },
        remove : function(batch, areaId) {
            var ids = [];
            if (batch) {
                var ck = $('#dataGrid').bootstrapTable('getSelections');
                if (!checkedArray(ck)) {
                    return false;
                }
                $.each(ck, function(idx, item) {
                    ids[idx] = item.areaId;
                });
            } else {
                ids.push(areaId);
            }
            $.RemoveForm({
                url: '../../sys/area/remove?_' + $.now(),
                param: ids,
                success: function(data) {
                    vm.load();
                }
            });
        }
	},
	created : function() {
		this.getArea(this.parentCode);
	}
})