+function ($) {
    'use strict';

    $(document).on('click.lily.newtodolist.data-api', '[data-behavior~=new_todolist]', function (e) {
        return $("article.todolist.new").expand("toggle"), e.preventDefault()
    })

    $(document).on('click.lily.remote.data-api', 'a[data-remote=true]', function (e) {
        var obj = $(this)
        $.ajax({url: obj.attr("data-url"),
            headers: {
                Accept : "text/javascript; charset=utf-8"
            },
            dataType: 'script',
            type: 'get'
        })
        e.preventDefault()
    })

    $(document).on('click.lily.remote.data-api', '[data-behavior=toggle]', function (e) {
        var obj = $(this)
        var flag = obj.is(":checked")
        $.ajax({url: obj.attr("data-url"),
            headers: {
                Accept : "text/javascript; charset=utf-8"
            },
            data: {flag: flag},
            dataType: 'script',
            type: 'get'
        })
    })

    $(document).on('submit.lily.form.data-api', 'form', function (e) {
        e.preventDefault()
        var self = $(this)
        var submitContainer = self.find('.submit')
        submitContainer.addClass("busy")
        self.beginSubmit(true, function(){
            submitContainer.removeClass("busy")
            self[0].reset()
        })
    })


    $.fn.fadeOutAndRemove = function (t) {
        var self = $(this)
        self.animate({ opacity: 0 }, 200, "linear", function () {
                return self.remove()
        })
    }

}(jQuery);