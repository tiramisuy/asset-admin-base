/**
 * 代码生成器js
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
		url : '../../sys/generator/list?_' + $.now(),
		height : $(window).height() - 56,
		queryParams : function(params) {
			params.name = vm.keyword;
			return params;
		},
		columns : [ {
			checkbox : true
		}, {
			field : "tableName",
			title : "表格名称"
		}, {
			field : "objName",
			title : "数据库引擎类型"
		}, {
			field : "tableComment",
			title : "备注"
		}, {
			field : "createTime",
			title : "创建时间"
		}, {
            title : "操作",
            formatter : function(value, row, index) {
                var _html = '';
                if (hasPermission('sys:gen:code')) {
                    _html += '<a href="javascript:;" onclick="vm.generate(false,\''+row.tableName+'\')" title="生成代码"><i class="fa fa-file-archive-o"></i></a>';
                }
                return _html;
            }
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
        generate : function(batch, tableName) {
            var names = [];
            if (batch) {
                var ck = $('#dataGrid').bootstrapTable('getSelections');
                if (!checkedRow(ck)) {
                    return false;
                }
                $.each(ck, function(idx, item) {
                    names[idx] = item.tableName;
                });
            } else {
                names.push(tableName);
            }
            dialogOpen({
                title : '生成代码',
                url : 'base/generator/code.html?_' + $.now(),
                width : '530px',
                height : '504px',
                success : function(iframeId) {
                    top.frames[iframeId].vm.generator.tables = names;
                },
                yes : function(iframeId) {
                    top.frames[iframeId].vm.acceptClick();
                },
            });
        }
	}
})