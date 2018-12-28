/**
 * 编辑-行政区域js
 */
var vm = new Vue({
	el:'#dpLTE',
	data: {
		area: {
			parentCode: 0,
			parentName: null,
			status: 1,
			orderNum: 0
		}
	},
	methods : {
		setForm: function() {
			$.SetForm({
				url: '../../sys/area/info?_' + $.now(),
		    	param: vm.area.areaId,
		    	success: function(data) {
		    		vm.area = data;
		    	}
			});
		},
		acceptClick: function() {
			if (!$('#form').Validform()) {
		        return false;
		    }
		    $.ConfirmForm({
		    	url: '../../sys/area/update?_' + $.now(),
		    	param: vm.area,
		    	success: function(data) {
		    		$.currentIframe().vm.load();
		    	}
		    });
		}
	}
})