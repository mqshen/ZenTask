+function ($) {
    'use strict';

    $.fn.beginSubmit = function(buttonTrigger, callback) {
        var requestData = this.serialize()
        console.log(requestData)
        if(!buttonTrigger)
            this.find("[data-behavior~=load_assignee_options]").addClass("busy");

        $.ajax({url: this.attr("action"),
            headers: {
                Accept : "text/javascript; charset=utf-8"
            },
            data: requestData,
            dataType: 'script',
            type: 'POST',
            success: callback
        })
    }

}(jQuery);