define("frontend-js-metal-web@1.0.7/metal-alert/src/Alert-min", ["exports","metal/src/metal","metal-dom/src/all/dom","metal-anim/src/Anim","metal-component/src/all/component","metal-events/src/events","metal-soy/src/Soy","./Alert.soy","metal-jquery-adapter/src/JQueryAdapter"], function(e,t,i,s,n,o,l,a,r){"use strict";function c(e){return e&&e.__esModule?e:{"default":e}}function d(e,t){if(!(e instanceof t))throw new TypeError("Cannot call a class as a function")}function u(e,t){if(!e)throw new ReferenceError("this hasn't been initialised - super() hasn't been called");return!t||"object"!=typeof t&&"function"!=typeof t?e:t}function h(e,t){if("function"!=typeof t&&null!==t)throw new TypeError("Super expression must either be null or a function, not "+typeof t);e.prototype=Object.create(t&&t.prototype,{constructor:{value:e,enumerable:!1,writable:!0,configurable:!0}}),t&&(Object.setPrototypeOf?Object.setPrototypeOf(e,t):e.__proto__=t)}Object.defineProperty(e,"__esModule",{value:!0});var p=c(i),f=c(s),y=c(n),m=c(l),b=c(a),v=c(r),_=function(e){function i(){return (d(this,i), u(this,e.apply(this,arguments)))}return (h(i,e), i.prototype.created=function(){this.eventHandler_=new o.EventHandler}, i.prototype.detached=function(){e.prototype.detached.call(this),this.eventHandler_.removeAllListeners(),clearTimeout(this.delay_)}, i.prototype.close=function(){p["default"].once(this.element,"animationend",this.dispose.bind(this)),p["default"].once(this.element,"transitionend",this.dispose.bind(this)),this.eventHandler_.removeAllListeners(),this.syncVisible(!1)}, i.prototype.handleDocClick_=function(e){this.element.contains(e.target)||this.hide()}, i.prototype.hide=function(){this.visible=!1}, i.prototype.hideCompletely_=function(){this.isDisposed()||this.visible||e.prototype.syncVisible.call(this,!1)}, i.prototype.toggle=function(){this.visible=!this.visible}, i.prototype.show=function(){this.visible=!0}, i.prototype.syncDismissible=function(e){e?this.eventHandler_.add(p["default"].on(document,"click",this.handleDocClick_.bind(this))):this.eventHandler_.removeAllListeners()}, i.prototype.syncHideDelay=function(e){t.core.isNumber(e)&&this.visible&&(clearTimeout(this.delay_),this.delay_=setTimeout(this.hide.bind(this),e))}, i.prototype.syncVisible=function(i,s){var n=this,o=!1;i?t.core.isDef(s)&&(o=!0,e.prototype.syncVisible.call(this,!0)):(p["default"].once(this.element,"animationend",this.hideCompletely_.bind(this)),p["default"].once(this.element,"transitionend",this.hideCompletely_.bind(this)));var l=function(){n.isDisposed()||(p["default"].removeClasses(n.element,n.animClasses[i?"hide":"show"]),p["default"].addClasses(n.element,n.animClasses[i?"show":"hide"]),f["default"].emulateEnd(n.element),i&&t.core.isNumber(n.hideDelay)&&n.syncHideDelay(n.hideDelay))};o?setTimeout(l,0):l()}, i)}(y["default"]);m["default"].register(_,b["default"]),_.STATE={animClasses:{validator:t.core.isObject,value:{show:"fade in",hide:"fade"}},body:{isHtml:!0},dismissible:{validator:t.core.isBoolean,value:!0},elementClasses:{value:"alert-success"},hideDelay:{},spinner:{value:!1},spinnerClasses:{},spinnerDone:{value:!1},visible:{value:!1}},e["default"]=_,v["default"].register("alert",_)});