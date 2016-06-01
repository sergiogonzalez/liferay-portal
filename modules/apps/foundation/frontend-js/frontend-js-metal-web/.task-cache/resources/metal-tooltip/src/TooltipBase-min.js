define("frontend-js-metal-web@1.0.7/metal-tooltip/src/TooltipBase-min", ["exports","metal/src/metal","metal-dom/src/all/dom","metal-position/src/all/position","metal-component/src/all/component","metal-events/src/events","metal-jquery-adapter/src/JQueryAdapter"], function(t,e,i,n,o,l,s){"use strict";function r(t){return t&&t.__esModule?t:{"default":t}}function a(t,e){if(!(t instanceof e))throw new TypeError("Cannot call a class as a function")}function c(t,e){if(!t)throw new ReferenceError("this hasn't been initialised - super() hasn't been called");return!e||"object"!=typeof e&&"function"!=typeof e?t:e}function d(t,e){if("function"!=typeof e&&null!==e)throw new TypeError("Super expression must either be null or a function, not "+typeof e);t.prototype=Object.create(e&&e.prototype,{constructor:{value:t,enumerable:!1,writable:!0,configurable:!0}}),e&&(Object.setPrototypeOf?Object.setPrototypeOf(t,e):t.__proto__=e)}Object.defineProperty(t,"__esModule",{value:!0});var h=r(e),u=r(i),p=r(o),y=r(s),f=function(t){function e(){return (a(this,e), c(this,t.apply(this,arguments)))}return (d(e,t), e.prototype.attached=function(){this.align(),this.syncTriggerEvents(this.triggerEvents)}, e.prototype.created=function(){this.eventHandler_=new l.EventHandler}, e.prototype.detached=function(){this.eventHandler_.removeAllListeners()}, e.prototype.disposeInternal=function(){t.prototype.disposeInternal.call(this),clearTimeout(this.delay_)}, e.prototype.align=function(t){this.syncAlignElement(t||this.alignElement)}, e.prototype.callAsync_=function(t,e){clearTimeout(this.delay_),this.delay_=setTimeout(t.bind(this),e)}, e.prototype.handleHide=function(t){var e=t.delegateTarget,i=e&&e!==this.alignElement;this.callAsync_(function(){this.locked_||(i?this.alignElement=e:(this.visible=!1,this.syncVisible(!1)))},this.delay[1])}, e.prototype.handleShow=function(e){var i=e.delegateTarget;t.prototype.syncVisible.call(this,!0),this.callAsync_(function(){this.alignElement=i,this.visible=!0},this.delay[0])}, e.prototype.handleToggle=function(t){this.visible?this.handleHide(t):this.handleShow(t)}, e.prototype.lock=function(){this.locked_=!0}, e.prototype.unlock=function(t){this.locked_=!1,this.handleHide(t)}, e.prototype.syncAlignElement=function(t,i){if(i&&t.removeAttribute("aria-describedby"),t){var n=t.getAttribute("data-title");n&&(this.title=n),this.inDocument&&(this.alignedPosition=e.Align.align(this.element,t,this.position))}}, e.prototype.syncPosition=function(){this.syncAlignElement(this.alignElement)}, e.prototype.syncSelector=function(){this.syncTriggerEvents(this.triggerEvents)}, e.prototype.syncTriggerEvents=function(t){if(this.inDocument){this.eventHandler_.removeAllListeners();var e=this.selector;e&&(this.eventHandler_.add(this.on("mouseenter",this.lock),this.on("mouseleave",this.unlock)),t[0]===t[1]?this.eventHandler_.add(u["default"].delegate(document,t[0],e,this.handleToggle.bind(this))):this.eventHandler_.add(u["default"].delegate(document,t[0],e,this.handleShow.bind(this)),u["default"].delegate(document,t[1],e,this.handleHide.bind(this))))}}, e.prototype.syncVisible=function(){this.align()}, e)}(p["default"]);f.Align=n.Align,f.STATE={alignedPosition:{validator:f.Align.isValidPosition},alignElement:{setter:u["default"].toElement},delay:{validator:Array.isArray,value:[500,250]},triggerEvents:{validator:Array.isArray,value:["mouseenter","mouseleave"]},selector:{validator:h["default"].isString},position:{validator:f.Align.isValidPosition,value:f.Align.Bottom},title:{isHtml:!0}},f.PositionClasses=["top","right","bottom","left"],t["default"]=f,y["default"].register("tooltipBase",f)});