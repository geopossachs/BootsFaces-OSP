/*! Bootstrap v3.3.4 (http://getbootstrap.com) | Copyright 2011-2015 Twitter, Inc. | Licensed under MIT */
+function(d){var b=function(f,e){this.options=e;this.$body=d(document.body);this.$element=d(f);this.$dialog=this.$element.find(".modal-dialog");this.$backdrop=null;this.isShown=null;this.originalBodyPad=null;this.scrollbarWidth=0;this.ignoreBackdropClick=false;if(this.options.remote){this.$element.find(".modal-content").load(this.options.remote,d.proxy(function(){this.$element.trigger("loaded.bs.modal")},this))}};b.VERSION="3.3.4";b.TRANSITION_DURATION=300;b.BACKDROP_TRANSITION_DURATION=150;b.DEFAULTS={backdrop:true,keyboard:true,show:true};b.prototype.toggle=function(e){return this.isShown?this.hide():this.show(e)};b.prototype.show=function(h){var f=this;var g=d.Event("show.bs.modal",{relatedTarget:h});this.$element.trigger(g);if(this.isShown||g.isDefaultPrevented()){return}this.isShown=true;this.checkScrollbar();this.setScrollbar();this.$body.addClass("modal-open");this.escape();this.resize();this.$element.on("click.dismiss.bs.modal",'[data-dismiss="modal"]',d.proxy(this.hide,this));this.$dialog.on("mousedown.dismiss.bs.modal",function(){f.$element.one("mouseup.dismiss.bs.modal",function(i){if(d(i.target).is(f.$element)){f.ignoreBackdropClick=true}})});this.backdrop(function(){var j=d.support.transition&&f.$element.hasClass("fade");if(!f.$element.parent().length){f.$element.appendTo(f.$body)}f.$element.show().scrollTop(0);f.adjustDialog();if(j){f.$element[0].offsetWidth}f.$element.addClass("in").attr("aria-hidden",false);f.enforceFocus();var i=d.Event("shown.bs.modal",{relatedTarget:h});j?f.$dialog.one("bsTransitionEnd",function(){f.$element.trigger("focus").trigger(i)}).emulateTransitionEnd(b.TRANSITION_DURATION):f.$element.trigger("focus").trigger(i)})};b.prototype.hide=function(f){if(f){f.preventDefault()}f=d.Event("hide.bs.modal");this.$element.trigger(f);if(!this.isShown||f.isDefaultPrevented()){return}this.isShown=false;this.escape();this.resize();d(document).off("focusin.bs.modal");this.$element.removeClass("in").attr("aria-hidden",true).off("click.dismiss.bs.modal").off("mouseup.dismiss.bs.modal");this.$dialog.off("mousedown.dismiss.bs.modal");d.support.transition&&this.$element.hasClass("fade")?this.$element.one("bsTransitionEnd",d.proxy(this.hideModal,this)).emulateTransitionEnd(b.TRANSITION_DURATION):this.hideModal()};b.prototype.enforceFocus=function(){d(document).off("focusin.bs.modal").on("focusin.bs.modal",d.proxy(function(f){if(this.$element[0]!==f.target&&!this.$element.has(f.target).length){this.$element.trigger("focus")}},this))};b.prototype.escape=function(){if(this.isShown&&this.options.keyboard){this.$element.on("keydown.dismiss.bs.modal",d.proxy(function(f){f.which==27&&this.hide()},this))}else{if(!this.isShown){this.$element.off("keydown.dismiss.bs.modal")}}};b.prototype.resize=function(){if(this.isShown){d(window).on("resize.bs.modal",d.proxy(this.handleUpdate,this))}else{d(window).off("resize.bs.modal")}};b.prototype.hideModal=function(){var e=this;this.$element.hide();this.backdrop(function(){e.$body.removeClass("modal-open");e.resetAdjustments();e.resetScrollbar();e.$element.trigger("hidden.bs.modal")})};b.prototype.removeBackdrop=function(){this.$backdrop&&this.$backdrop.remove();this.$backdrop=null};b.prototype.backdrop=function(i){var h=this;var f=this.$element.hasClass("fade")?"fade":"";if(this.isShown&&this.options.backdrop){var e=d.support.transition&&f;this.$backdrop=d('<div class="modal-backdrop '+f+'" />').appendTo(this.$body);this.$element.on("click.dismiss.bs.modal",d.proxy(function(j){if(this.ignoreBackdropClick){this.ignoreBackdropClick=false;return}if(j.target!==j.currentTarget){return}this.options.backdrop=="static"?this.$element[0].focus():this.hide()},this));if(e){this.$backdrop[0].offsetWidth}this.$backdrop.addClass("in");if(!i){return}e?this.$backdrop.one("bsTransitionEnd",i).emulateTransitionEnd(b.BACKDROP_TRANSITION_DURATION):i()}else{if(!this.isShown&&this.$backdrop){this.$backdrop.removeClass("in");var g=function(){h.removeBackdrop();i&&i()};d.support.transition&&this.$element.hasClass("fade")?this.$backdrop.one("bsTransitionEnd",g).emulateTransitionEnd(b.BACKDROP_TRANSITION_DURATION):g()}else{if(i){i()}}}};b.prototype.handleUpdate=function(){this.adjustDialog()};b.prototype.adjustDialog=function(){var e=this.$element[0].scrollHeight>document.documentElement.clientHeight;this.$element.css({paddingLeft:!this.bodyIsOverflowing&&e?this.scrollbarWidth:"",paddingRight:this.bodyIsOverflowing&&!e?this.scrollbarWidth:""})};b.prototype.resetAdjustments=function(){this.$element.css({paddingLeft:"",paddingRight:""})};b.prototype.checkScrollbar=function(){var f=window.innerWidth;if(!f){var e=document.documentElement.getBoundingClientRect();f=e.right-Math.abs(e.left)}this.bodyIsOverflowing=document.body.clientWidth<f;this.scrollbarWidth=this.measureScrollbar()};b.prototype.setScrollbar=function(){var e=parseInt((this.$body.css("padding-right")||0),10);this.originalBodyPad=document.body.style.paddingRight||"";if(this.bodyIsOverflowing){this.$body.css("padding-right",e+this.scrollbarWidth)}};b.prototype.resetScrollbar=function(){this.$body.css("padding-right",this.originalBodyPad)};b.prototype.measureScrollbar=function(){var f=document.createElement("div");f.className="modal-scrollbar-measure";this.$body.append(f);var e=f.offsetWidth-f.clientWidth;this.$body[0].removeChild(f);return e};function c(e,f){return this.each(function(){var i=d(this);var h=i.data("bs.modal");var g=d.extend({},b.DEFAULTS,i.data(),typeof e=="object"&&e);if(!h){i.data("bs.modal",(h=new b(this,g)))}if(typeof e=="string"){h[e](f)}else{if(g.show){h.show(f)}}})}var a=d.fn.modal;d.fn.modal=c;d.fn.modal.Constructor=b;d.fn.modal.noConflict=function(){d.fn.modal=a;return this};d(document).on("click.bs.modal.data-api",'[data-toggle="modal"]',function(j){var i=d(this);var g=i.attr("href");var f=d(i.attr("data-target")||(g&&g.replace(/.*(?=#[^\s]+$)/,"")));var h=f.data("bs.modal")?"toggle":d.extend({remote:!/#/.test(g)&&g},f.data(),i.data());if(i.is("a")){j.preventDefault()}f.one("show.bs.modal",function(e){if(e.isDefaultPrevented()){return}f.one("hidden.bs.modal",function(){i.is(":visible")&&i.trigger("focus")})});c.call(f,h,this)})}(jQuery);