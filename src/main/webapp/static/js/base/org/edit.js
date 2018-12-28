/**
 * 编辑-机构管理js
 */
var vm = new Vue({
	el:'#dpLTE',
	data: {
		org: {
			parentId: 0,
			parentName: null,
			orderNum: 0,
			status: 1,
			layer: 1
		}
	},
	methods : {
		orgTree: function() {
			dialogOpen({
				id: 'layerOrgTree',
				title: '选择机构',
		        url: 'base/org/tree.html?_' + $.now(),
		        scroll : true,
		        width: "300px",
		        height: "450px",
		        yes : function(iframeId) {
		        	top.frames[iframeId].vm.acceptClick();
				}
		    })
		},
		setForm: function() {
			$.SetForm({
				url: '../../sys/org/info?_' + $.now(),
		    	param: vm.org.orgId,
		    	success: function(data) {
		    		vm.org = data;
		    	}
			});
		},
		acceptClick: function() {
			if (!$('#form').Validform()) {
		        return false;
		    }
		    $.ConfirmForm({
		    	url: '../../sys/org/update?_' + $.now(),
		    	param: vm.org,
		    	success: function(data) {
		    		$.currentIframe().vm.load();
		    	}
		    });
		}
	}
})
