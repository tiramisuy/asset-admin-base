/**
 * 新增-定时任务js
 */
var vm = new Vue({
	el:'#dpLTE',
	data: {
		job: {
			
		}
	},
	methods : {
		acceptClick: function() {
			if (!$('#form').Validform()) {
		        return false;
		    }
		    $.SaveForm({
		    	url: '../../quartz/job/save?_' + $.now(),
		    	param: vm.job,
		    	success: function(data) {
		    		$.currentIframe().vm.load();
		    	}
		    });
		}
	}
})
