define("liferay-image-editor-web@1.0.0/ImageEditor.soy", ['exports', 'metal-component/src/Component', 'metal-soy/src/Soy'], function (exports, _Component2, _Soy) {
  'use strict';

  Object.defineProperty(exports, "__esModule", {
    value: true
  });
  exports.templates = exports.ImageEditor = undefined;

  var _Component3 = _interopRequireDefault(_Component2);

  var _Soy2 = _interopRequireDefault(_Soy);

  function _interopRequireDefault(obj) {
    return obj && obj.__esModule ? obj : {
      default: obj
    };
  }

  function _classCallCheck(instance, Constructor) {
    if (!(instance instanceof Constructor)) {
      throw new TypeError("Cannot call a class as a function");
    }
  }

  function _possibleConstructorReturn(self, call) {
    if (!self) {
      throw new ReferenceError("this hasn't been initialised - super() hasn't been called");
    }

    return call && (typeof call === "object" || typeof call === "function") ? call : self;
  }

  function _inherits(subClass, superClass) {
    if (typeof superClass !== "function" && superClass !== null) {
      throw new TypeError("Super expression must either be null or a function, not " + typeof superClass);
    }

    subClass.prototype = Object.create(superClass && superClass.prototype, {
      constructor: {
        value: subClass,
        enumerable: false,
        writable: true,
        configurable: true
      }
    });
    if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass;
  }

  var templates;
  goog.loadModule(function (exports) {

    // This file was automatically generated from ImageEditor.soy.
    // Please don't edit this file by hand.

    /**
     * @fileoverview Templates in namespace ImageEditor.
     * @hassoydeltemplate {ImageEditor.Controls.idom}
     * @hassoydelcall {ImageEditor.Controls.idom}
     * @public
     */

    goog.module('ImageEditor.incrementaldom');

    /** @suppress {extraRequire} */
    var soy = goog.require('soy');
    /** @suppress {extraRequire} */
    var soydata = goog.require('soydata');
    /** @suppress {extraRequire} */
    goog.require('goog.i18n.bidi');
    /** @suppress {extraRequire} */
    goog.require('goog.asserts');
    var IncrementalDom = goog.require('incrementaldom');
    var ie_open = IncrementalDom.elementOpen;
    var ie_close = IncrementalDom.elementClose;
    var ie_void = IncrementalDom.elementVoid;
    var ie_open_start = IncrementalDom.elementOpenStart;
    var ie_open_end = IncrementalDom.elementOpenEnd;
    var itext = IncrementalDom.text;
    var iattr = IncrementalDom.attr;

    /**
     * @param {Object<string, *>=} opt_data
     * @param {(null|undefined)=} opt_ignored
     * @param {Object<string, *>=} opt_ijData
     * @return {void}
     * @suppress {checkTypes}
     */
    function $render(opt_data, opt_ignored, opt_ijData) {
      ie_open('div', null, null, 'id', opt_data.id);
      ie_open('div', null, null, 'class', 'lfr-image-editor-image-container', 'id', opt_data.id + 'ImageContainer');
      if (('' + opt_data.image).indexOf('data:image') != -1) {
        ie_open('img', null, null, 'class', 'img-responsive', 'src', soy.$$filterImageDataUri(opt_data.image));
        ie_close('img');
      } else {
        ie_open('img', null, null, 'class', 'img-responsive', 'src', opt_data.image);
        ie_close('img');
      }
      ie_void('canvas', null, null, 'class', 'image-preview ' + (opt_data.imagePreview ? '' : 'hide'));
      ie_close('div');
      ie_open('div', null, null, 'class', 'lfr-image-editor-tools-container');
      $tools_controls(opt_data, null, opt_ijData);
      ie_close('div');
      ie_open('div', null, null, 'class', 'lfr-image-editor-history-container');
      if (!opt_data.selectedTool) {
        ie_open('div', null, null, 'class', 'history-controls history-left btn-group', 'role', 'group');
        ie_void('a', null, null, 'class', 'btn btn-link ' + (opt_data.history && opt_data.history.canUndo ? '' : 'disabled') + ' icon-reply icon-monospaced', 'data-onclick', 'undo', 'href', 'javascript:;');
        ie_void('a', null, null, 'class', 'btn btn-link ' + (opt_data.history && opt_data.history.canRedo ? '' : 'disabled') + ' icon-share-alt icon-monospaced', 'data-onclick', 'redo', 'href', 'javascript:;');
        ie_close('div');
        ie_open('div', null, null, 'class', 'history-controls history-right btn-group', 'role', 'group');
        ie_void('a', null, null, 'class', 'btn btn-link icon-undo ' + (opt_data.history && opt_data.history.canReset ? '' : 'disabled') + ' icon-monospaced', 'data-onclick', 'reset', 'href', 'javascript:;');
        ie_close('div');
      }
      ie_close('div');
      ie_close('div');
    }
    exports.render = $render;
    if (goog.DEBUG) {
      $render.soyTemplateName = 'ImageEditor.render';
    }

    /**
     * @param {Object<string, *>=} opt_data
     * @param {(null|undefined)=} opt_ignored
     * @param {Object<string, *>=} opt_ijData
     * @return {void}
     * @suppress {checkTypes}
     */
    function $tools_controls(opt_data, opt_ignored, opt_ijData) {
      ie_open('div', null, null, 'class', 'controls text-center');
      ie_open('ul', null, null, 'class', 'list-inline');
      var toolList54 = opt_data.tools;
      var toolListLen54 = toolList54.length;
      for (var toolIndex54 = 0; toolIndex54 < toolListLen54; toolIndex54++) {
        var toolData54 = toolList54[toolIndex54];
        ie_open('li', null, null, 'class', opt_data.selectedTool && opt_data.selectedTool.name == toolData54.name ? 'open' : '', 'id', opt_data.id + '-' + toolData54.name, 'style', 'display: inline;');
        ie_open('a', null, null, 'class', 'btn', 'data-tool', toolData54.name, 'data-onclick', 'edit', 'href', 'javascript:;');
        ie_void('span', null, null, 'class', 'icon-' + toolData54.icon + ' icon-monospaced');
        ie_close('a');
        ie_open('div', null, null, 'class', 'filters');
        ie_open('div', null, null, 'class', 'col-md-2 col-sm-2 col-xs-2 btn-space');
        ie_open('a', null, null, 'class', 'btn btn-link btn-primary modal-btn-icon icon-ok', 'data-onclick', 'accept', 'href', 'javascript:;');
        itext('Apply');
        ie_close('a');
        ie_close('div');
        ie_open('div', null, null, 'class', 'col-md-8 col-sm-8 col-xs-8 filters-list');
        if (opt_data.selectedTool && opt_data.selectedTool.name == toolData54.name) {
          $active_tool_controls(soy.$$augmentMap(opt_data, { controlsId: toolData54.controls }), null, opt_ijData);
        }
        ie_close('div');
        ie_open('div', null, null, 'class', 'col-md-2 col-sm-2 col-xs-2 btn-space');
        ie_open('a', null, null, 'class', 'btn btn-link close-modal modal-btn-icon icon-remove', 'data-onclick', 'discard', 'href', 'javascript:;');
        itext('Cancel');
        ie_close('a');
        ie_close('div');
        ie_close('div');
        ie_close('li');
      }
      ie_close('ul');
      ie_close('div');
    }
    exports.tools_controls = $tools_controls;
    if (goog.DEBUG) {
      $tools_controls.soyTemplateName = 'ImageEditor.tools_controls';
    }

    /**
     * @param {Object<string, *>=} opt_data
     * @param {(null|undefined)=} opt_ignored
     * @param {Object<string, *>=} opt_ijData
     * @return {void}
     * @suppress {checkTypes}
     */
    function __deltemplate_s57_266044fb(opt_data, opt_ignored, opt_ijData) {}
    exports.__deltemplate_s57_266044fb = __deltemplate_s57_266044fb;
    if (goog.DEBUG) {
      __deltemplate_s57_266044fb.soyTemplateName = 'ImageEditor.__deltemplate_s57_266044fb';
    }
    soy.$$registerDelegateFn(soy.$$getDelTemplateId('ImageEditor.Controls.idom'), '', 0, __deltemplate_s57_266044fb);

    /**
     * @param {Object<string, *>=} opt_data
     * @param {(null|undefined)=} opt_ignored
     * @param {Object<string, *>=} opt_ijData
     * @return {void}
     * @suppress {checkTypes}
     */
    function $active_tool_controls(opt_data, opt_ignored, opt_ijData) {
      soy.$$getDelegateFn(soy.$$getDelTemplateId('ImageEditor.Controls.idom'), opt_data.controlsId, true)(soy.$$augmentMap(opt_data, { key: opt_data.id + '_selected_tool_' + opt_data.controlsId }), null, opt_ijData);
    }
    exports.active_tool_controls = $active_tool_controls;
    if (goog.DEBUG) {
      $active_tool_controls.soyTemplateName = 'ImageEditor.active_tool_controls';
    }

    exports.render.params = ["getEditorImage", "getEditorImageContainer", "history", "id", "image", "imagePreview", "requestEditorPreview", "selectedTool", "tools"];
    exports.render.types = { "getEditorImage": "any", "getEditorImageContainer": "any", "history": "any", "id": "any", "image": "any", "imagePreview": "any", "requestEditorPreview": "any", "selectedTool": "any", "tools": "any" };
    exports.tools_controls.params = ["id", "selectedTool", "tools"];
    exports.tools_controls.types = { "id": "any", "selectedTool": "any", "tools": "any" };
    exports.active_tool_controls.params = ["controlsId", "id"];
    exports.active_tool_controls.types = { "controlsId": "any", "id": "any" };
    exports.templates = templates = exports;
    return exports;
  });

  var ImageEditor = function (_Component) {
    _inherits(ImageEditor, _Component);

    function ImageEditor() {
      _classCallCheck(this, ImageEditor);

      return _possibleConstructorReturn(this, _Component.apply(this, arguments));
    }

    return ImageEditor;
  }(_Component3.default);

  _Soy2.default.register(ImageEditor, templates);
  exports.default = templates;
  exports.ImageEditor = ImageEditor;
  exports.templates = templates;
});
//# sourceMappingURL=ImageEditor.soy.js.map