+function ($) {
    'use strict';

    // BUTTON PUBLIC CLASS DEFINITION
    // ==============================

    var Expand = function (element, options) {
        this.$element  = $(element)
        this.options   = $.extend({}, Expand.DEFAULTS, options)
        this.expanded = false
        this.$parent = this.$element.closest('[data-behavior~=expandable]')
        var self = this;
        this.$element.find("[data-behavior~=cancel]").click(function(e){
            self.collapse()
            e.preventDefault()
        })
        if(this.$element.find("[data-behavior~=expandable]").length > 0) {
            this.exclusive = false;
            return
        }
        this.exclusive = this.$element.find("[data-behavior~=expand_exclusively]").length > 0
        var $calendar = this.$parent.find("[data-behavior~=date_picker]")
        if($calendar.length && $calendar.closest('[data-behavior~=expandable]') != this.$element) {
            var altField = $calendar.prev("[data-behavior~=alt_date_field]");
            $calendar.calendar({
                altField: altField,
                target: self,
                onDatePick: function (date, r) {
                    self.processor(date)
                }
            });
        }
    }

    Expand.VERSION  = '1.0'

    Expand.DEFAULTS = {
        loadingText: 'loading...'
    }

    Expand.prototype.expand = function() {
        if(this.expanded)
            return
        this.expanded = true;
        if (this.$parent.length) {
            this.$parent.addClass("expanded");
            if(this.exclusive) {
                $('body').addClass("balloon-active");
                var self = this;
                $('.modal-backdrop').click(function() {self.collapse()});
            }
        }
    }

    Expand.prototype.collapse = function() {
        if(!this.expanded)
            return
        this.expanded = false;
        if (this.$parent.length) {
            this.$parent.removeClass("expanded");
            if(this.exclusive) {
                $('body').removeClass("balloon-active");
                $('.modal-backdrop').off("click");
            }
        }
    }

    Expand.prototype.processor = function(date) {
        var $form = this.$element.closest('form')
        if($form.length > 0 && $form.find("[type=submit]").length < 1) {
            $form.beginSubmit()
        }
        else {
            $form.find("[data-behavior=assignee_name]").text($form.find("[name=workerId]").find("option:selected").text())
            $form.find("[data-behavior=due_date]").text($.formatDate(date))
        }
        this.collapse()
    }

    Expand.prototype.toggle = function () {
        return this.expanded ? this.collapse() : this.expand()
    }


  // BUTTON PLUGIN DEFINITION
  // ========================

    function Plugin(option) {
        return this.each(function () {
            var $this   = $(this)
            var data    = $this.data('bs.expand')
            var options = typeof option == 'object' && option

            if (!data) $this.data('bs.expand', (data = new Expand(this, options)))

            if (option == 'toggle') data.toggle()
            else if (option) data.setState(option)
        })
    }

    var old = $.fn.expand

    $.fn.expand             = Plugin
    $.fn.expand.Constructor = Expand


  // BUTTON NO CONFLICT
  // ==================

    $.fn.expand.noConflict = function () {
        $.fn.expand = old
        return this
    }


  // BUTTON DATA-API
  // ===============

    $(document).on('click.bs.expand.data-api', '[data-behavior~=expandable] [data-behavior~=expand_on_click]', function (e) {
        var self = $(this).closest("[data-behavior~=expandable]")
        console.log(self)
        Plugin.call(self, 'toggle')
        e.preventDefault()
    })

}(jQuery);