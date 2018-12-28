/**
 * 编辑-定时任务js
 */
var vm = new Vue({
	el:'#dpLTE',
	data: {
		job: {
			jobId: 0
		}
	},
	methods : {
		setForm: function() {
			$.SetForm({
				url: '../../quartz/job/info?_' + $.now(),
		    	param: vm.job.jobId,
		    	success: function(data) {
		    		vm.job = data;
		    	}
			});
		},
		acceptClick: function() {
			if (!$('#form').Validform()) {
		        return false;
		    }
		    $.ConfirmForm({
		    	url: '../../quartz/job/update?_' + $.now(),
		    	param: vm.job,
		    	success: function(data) {
		    		$.currentIframe().vm.load();
		    	}
		    });
		}
	}
})