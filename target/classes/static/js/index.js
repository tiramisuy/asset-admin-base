//生成菜单
var menuItem = Vue
		.extend({
			name : 'menu-item',
			props : {
				item : {}
			},
			template : [
					'<li>',
					'<a v-if="item.type === 0" href="javascript:;">',
					'<i v-if="item.icon != null" :class="item.icon"></i>',
					'<span>{{item.name}}</span>',
					'<i class="fa fa-angle-left pull-right"></i>',
					'</a>',
					'<ul v-if="item.type === 0" class="treeview-menu">',
					'<menu-item :item="item" v-for="item in item.list"></menu-item>',
					'</ul>',
					'<a v-if="item.type === 1" :href="\'#\'+item.url"><i v-if="item.icon != null" :class="item.icon"></i><i v-else class="fa fa-circle-o"></i> {{item.name}}</a>',
					'</li>' ].join('')
		});

// iframe自适应
$(window).on('resize', function() {
	var $content = $('.content');
	$content.height($(this).height() - 146);
	$content.find('iframe').each(function() {
		$(this).height($content.height());
	});
}).resize();

// 注册菜单组件
Vue.component('menuItem', menuItem);

var vm = new Vue({
	el : '#dpLTE',
	data : {
		user : {},
		menuList : {},
		main : "dashboard",
		pswd : null,
		newPswd : null,
		navTitle : "控制台"
	},
	methods : {
		hideMenu : function() {
			if (!$("body").hasClass("sidebar-collapse")) {
				$("body").addClass("sidebar-collapse");
				removeScroll();
			} else {
				$("body").removeClass("sidebar-collapse");
				setScroll();
			}
		},
		getMenuList : function(event) {
			$.getJSON("sys/menu/user?_" + $.now(), function(r) {
				vm.menuList = r.menuList;
			});
		},
		getPermList : function(event) {
			$.getJSON("sys/user/perms?_" + $.now(), function(r) {
				window.perms = r.rows;
			});
		},
		getUser : function() {
			$.getJSON("sys/user/info?_" + $.now(), function(r) {
				vm.user = r.user;
			});
		},
		updatePassword : function() {
			dialogContent({
				title : "修改密码",
				width : '420px',
				height : '250px',
				content : $("#passwordLayer"),
				btn : [ '确定', '取消' ],
				yes : function(index) {
					if(isNullOrEmpty(vm.pswd)) {
                        dialogTip('原密码为空！', '#oldPswd');
						return false;
					}
					if(isNullOrEmpty(vm.newPswd)) {
                        dialogTip('新密码为空！', '#newPswd');
						return false;
					}
					var data = "pswd=" + vm.pswd + "&newPswd="
							+ vm.newPswd;
					$.ajax({
						type : "POST",
						url : "sys/user/updatePswd?_" + $.now(),
						data : data,
						dataType : "json",
						success : function(result) {
							if (result.code == 0) {
								layer.close(index);
								dialogMsg(result.msg, 'success');
								location.reload();
							} else {
								dialogAlert(result.msg, 'error');
							}
						}
					});
				}
			});
		},
		logout : function() {
			layer.open({
				title : '系统提示',
				area : '338px',
				icon : 3,
				anim : -1,
				isOutAnim : false,
				move : false,
				content : '注：您确定要安全退出本次登录吗？',
				btn : [ '确定', '取消' ],
				btnAlign : 'c',
				yes : function() {
					toUrl('logout');
				}
			});
		}
	},
	created : function() {
		this.getMenuList();
		this.getPermList();
		this.getUser();
		// 菜单点击事件
		$(document).off('click', ".sidebar-menu li a").on('click', ".sidebar-menu li a", function(e) {
			var $this = $(this);
			var checkElement = $this.next();
			if ((checkElement.is('.treeview-menu'))
					&& (checkElement.is(':visible'))) {
				checkElement.slideUp('fast', function() {
					checkElement.removeClass('menu-open');
				});
				checkElement.parent("li").removeClass("active");
			} else if ((checkElement.is('.treeview-menu'))
					&& (!checkElement.is(':visible'))) {
				var parent = $this.parents('ul').first();
				var ul = parent.find('ul:visible').slideUp('fast');
				ul.removeClass('menu-open');
				var parent_li = $this.parent("li");

				checkElement.slideDown('fast', function() {
					checkElement.addClass('menu-open');
					parent.find('li.active').removeClass('active');
					parent_li.addClass('active');
					
					var _height1 = $(window).height() - $("#sidebar-menu >li.active").position().top - 41;
                    var _height2 = $("#sidebar-menu li > ul.menu-open").height() + 10
                    if (_height2 > _height1) {
                        $("#sidebar-menu >li > ul.menu-open").css({
                            overflow: "auto",
                            height: _height1
                        })
                    }
					
				});
			}
			if (checkElement.is('.treeview-menu')) {
				e.preventDefault();
			}
		});
	},
	updated : function() {
		// 路由
		var router = new Router();
		routerList(router, vm.menuList);
		router.start();
		//默认展开第一个菜单
		if(!$('.sidebar-menu li').hasClass('active')) {
			$('.sidebar-menu li:eq(1)').addClass('active');
		}
		//自适应滚动条
		setScroll();
	}
});

function routerList(router, menuList) {
	for ( var key in menuList) {
		var menu = menuList[key];
		if (menu.type == 0) {
			routerList(router, menu.list);
		} else if (menu.type == 1) {
			router.add('#' + menu.url, function() {
				var url = window.location.hash;

				// 替换iframe的url
				vm.main = url.replace('#', '');

				// 导航菜单展开
				$(".treeview-menu li").removeClass("active");
				$("a[href='" + url + "']").parents("li").addClass("active");

				vm.navTitle = $("a[href='" + url + "']").text();
			});
		}
	}
}

//菜单滚动条自适应
function setScroll(){
    if (!$("body").hasClass("sidebar-collapse")) {
        $("#sidebar-menu").slimScroll({
            height: $(this).height() - 50,
            alwaysVisible: false,
        });
    }
    $(window).on("resize", function() {
        if (!$("body").hasClass("sidebar-collapse")) {
            $("#sidebar-menu").slimScroll({
                height: $(this).height() - 50,
                alwaysVisible: false,
            });
        }
    });
}

function removeScroll() {
	$('.sidebar').append($('#sidebar-menu'));
	$('#sidebar-menu').removeAttr('style');
	$('.slimScrollDiv').remove();
}