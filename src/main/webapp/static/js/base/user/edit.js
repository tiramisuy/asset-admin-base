/**
 * 编辑-用户管理js
 */
var vm = new Vue({
	el:'#dpLTE',
	data: {
		user:{
			orgId: 0,
			orgName: null,
			status: 1,
			roleIdList:[]
		}
	},
	methods : {
        getRoleList: function(){
            $('.roleSelect').selectBindEx({
                url: '../../sys/role/select?_' + $.now(),
                placeholder: '请选择角色',
                value: 'roleId',
                text: 'roleName',
                selected: vm.user.roleIdList
            });
        },
		orgTree: function() {
			dialogOpen({
				id: 'layerOrgTree',
				title: '选择机构',
		        url: 'base/user/org.html?_' + $.now(),
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
				url: '../../sys/user/infoUser?_' + $.now(),
		    	param: vm.user.userId,
		    	success: function(data) {
                    vm.user = data;
                    vm.getRoleList();
		    	}
			});
		},
		acceptClick: function() {
            if (!$('#form').Validform()) {
                return false;
            }
            vm.user.roleIdList = $('.roleSelect').val();
		    $.ConfirmForm({
		    	url: '../../sys/user/update?_' + $.now(),
		    	param: vm.user,
		    	success: function(data) {
		    		$.currentIframe().vm.load();
		    	}
		    });
		}
	}
})
