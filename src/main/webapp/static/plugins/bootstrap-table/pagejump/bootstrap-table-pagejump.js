(function ($) {
    'use strict';
    $.extend($.fn.bootstrapTable.defaults, {
        // 默认不显示
        paginationShowPageGo: false
    });

    $.extend($.fn.bootstrapTable.locales, {
        pageGo: function () {
            return '跳转到';
        }
    });
    $.extend($.fn.bootstrapTable.defaults, $.fn.bootstrapTable.locales);

    var BootstrapTable = $.fn.bootstrapTable.Constructor,
        _initPagination = BootstrapTable.prototype.initPagination;

    BootstrapTable.prototype.initPagination = function() {
        _initPagination.apply(this, Array.prototype.slice.apply(arguments));
        if(this.options.paginationShowPageGo){
            var $paginationContainer = this.$pagination;
            var html = [];

            html.push(
                '<ul class="pagination-jump">',
                '<li class=""><span>' + this.options.pageGo() + '</span></li>',
                '<li class=""><input type="text" class="page-input form-control" value="' + this.options.pageNumber + '"   /></li>',
                '<li class="page-go"><a class="jump-go" href="javascript:;">确定</a></li>',
                '</ul>');
            $paginationContainer.find('ul.pagination').after(html.join(''));

            $paginationContainer.find('.page-go').off('click').on('click', $.proxy(this.onPageGo, this));
            $paginationContainer.find('.page-input').off('keyup').on('keyup', function(event){
                this.value = this.value.length == 1 ? this.value.replace(/[^1-9]/g,'') : this.value.replace(/\D/g,'');
                if (event.which == 13) {
                    $paginationContainer.find('.page-go').trigger('click');
                }
            });
        }
    };

    BootstrapTable.prototype.onPageGo = function (event) {
        var $toPage=this.$pagination.find('.page-input');
        if (this.options.pageNumber === +$toPage.val()) {
            return false;
        }
        this.selectPage(+$toPage.val());
        return false;
    };
})(jQuery);