+function ($) {
    'use strict';

    var excludeSelector = "[data-behavior~=hover_content]:not(.expanded, .ignore_hover)";

    var hide = function (object) {
       var self = this;
       return object.data("hovercontent-strategy") === "visibility" ? (e.css("visibility", "hidden"), object.removeClass("showing")) : object.hide(), object.trigger("hovercontent:hide")
    }

    $(document).on('mouseenter.lily.nubbin.data-api', '[data-behavior~="has_hover_content"]', function (e) {
        $(this).find("" + excludeSelector + ":not([data-hovercontent-strategy])").each(function () {
            if ($(this).css("visibility") === "hidden")
            return $(this).attr("data-hovercontent-strategy", "visibility")
        });
        $(this).find(excludeSelector).each(function () {
            $(this).data("hovercontent-strategy") === "visibility" ?
                ($(this).css("visibility", "visible"), $(this).addClass("showing")) :
                ($(this).hide(), $(this).show()), $(this).trigger("hovercontent:show");
        })
    })

    $(document).on('mouseleave.lily.nubbin.data-api', '[data-behavior~="has_hover_content"]', function (e) {
        $(this).find("" + excludeSelector + ":visible").each(function () {
            var self = $(this);
            if (self.css("visibility") === "hidden")
                return;
            return hide(self)
        })
    })

}(jQuery);