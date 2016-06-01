define("frontend-js-metal-web@1.0.7/metal-select/src/Select-min", ["exports","metal/src/metal","metal-dom/src/all/dom","metal-component/src/all/component","metal-soy/src/Soy","./Select.soy","metal-jquery-adapter/src/JQueryAdapter","metal-dropdown/src/Dropdown"], function(e,t,n,o,r,s,i){"use strict";function d(e){return e&&e.__esModule?e:{"default":e}}function u(e,t){if(!(e instanceof t))throw new TypeError("Cannot call a class as a function")}function a(e,t){if(!e)throw new ReferenceError("this hasn't been initialised - super() hasn't been called");return!t||"object"!=typeof t&&"function"!=typeof t?e:t}function l(e,t){if("function"!=typeof t&&null!==t)throw new TypeError("Super expression must either be null or a function, not "+typeof t);e.prototype=Object.create(t&&t.prototype,{constructor:{value:e,enumerable:!1,writable:!0,configurable:!0}}),t&&(Object.setPrototypeOf?Object.setPrototypeOf(e,t):e.__proto__=t)}Object.defineProperty(e,"__esModule",{value:!0});var c=d(t),f=d(n),p=d(o),h=d(r),y=d(s),_=d(i),m=function(e){function t(){return (u(this,t), a(this,e.apply(this,arguments)))}return (l(t,e), t.prototype.findItemIndex_=function(e){for(var t=this.element.querySelectorAll("li"),n=0;n<t.length;n++)if(t.item(n)===e)return n}, t.prototype.focusIndex_=function(e){var t=this.element.querySelector(".select-option:nth-child("+(e+1)+") a");t&&(this.focusedIndex_=e,t.focus())}, t.prototype.getDropdown=function(){return this.components.dropdown}, t.prototype.handleDropdownStateSynced_=function(e){this.openedWithKeyboard_?(this.focusIndex_(0),this.openedWithKeyboard_=!1):e.changes.expanded&&(this.focusedIndex_=null)}, t.prototype.handleItemClick_=function(e){this.selectedIndex=this.findItemIndex_(e.currentTarget),this.getDropdown().close(),e.preventDefault()}, t.prototype.handleKeyDown_=function(e){if(this.getDropdown().expanded)switch(e.keyCode){case 27:this.getDropdown().close();break;case 38:this.focusedIndex_=c["default"].isDefAndNotNull(this.focusedIndex_)?this.focusedIndex_:1,this.focusIndex_(0===this.focusedIndex_?this.items.length-1:this.focusedIndex_-1),e.preventDefault();break;case 40:this.focusedIndex_=c["default"].isDefAndNotNull(this.focusedIndex_)?this.focusedIndex_:-1,this.focusIndex_(this.focusedIndex_===this.items.length-1?0:this.focusedIndex_+1),e.preventDefault()}else if((13===e.keyCode||32===e.keyCode)&&f["default"].hasClass(e.target,"dropdown-select"))return (this.openedWithKeyboard_=!0, this.getDropdown().open(), void e.preventDefault())}, t.prototype.setItems_=function(e){return e.map(function(e){return h["default"].toIncDom(e)})}, t)}(p["default"]);h["default"].register(m,y["default"]),m.STATE={arrowClass:{value:"caret"},buttonClass:{validator:c["default"].isString,value:"btn btn-default"},hiddenInputName:{validator:c["default"].isString},items:{setter:"setItems_",validator:function(e){return e instanceof Array},valueFn:function(){return[]}},label:{setter:function(e){return h["default"].toIncDom(e)},validator:c["default"].isString},selectedIndex:{validator:c["default"].isNumber,valueFn:function(){return this.label||!this.items.length?-1:0}},values:{validator:function(e){return e instanceof Array}}},m.ELEMENT_CLASSES="select",e["default"]=m,_["default"].register("select",m)});