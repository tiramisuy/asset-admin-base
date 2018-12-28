/**
 * 新增-行政区域js
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
		acceptClick: function() {
			if (!$('#form').Validform()) {
		        return false;
		    }
		    $.SaveForm({
		    	url: '../../sys/area/save?_' + $.now(),
		    	param: vm.area,
		    	success: function(data) {
		    		$.currentIframe().vm.load();
		    	}
		    });
		}
	}
})
