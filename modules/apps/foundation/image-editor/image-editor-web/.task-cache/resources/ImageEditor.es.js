define("liferay-image-editor-web@1.0.0/ImageEditor.es", ['exports', 'metal-component/src/Component', 'metal-soy/src/Soy', 'metal-promise/src/promise/Promise', './ImageEditorUtils.es', './ImageEditor.soy'], function (exports, _Component2, _Soy, _Promise, _ImageEditorUtils, _ImageEditor) {
	'use strict';

	Object.defineProperty(exports, "__esModule", {
		value: true
	});

	var _Component3 = _interopRequireDefault(_Component2);

	var _Soy2 = _interopRequireDefault(_Soy);

	var _ImageEditorUtils2 = _interopRequireDefault(_ImageEditorUtils);

	var _ImageEditor2 = _interopRequireDefault(_ImageEditor);

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

	var ImageEditor = function (_Component) {
		_inherits(ImageEditor, _Component);

		/**
   * @inheritDoc
   */

		function ImageEditor(opt_config) {
			_classCallCheck(this, ImageEditor);

			var _this = _possibleConstructorReturn(this, _Component.call(this, opt_config));

			/**
      	 * This index points to the current state in the history.
      	 *
    * @type {Number}
    * @protected
    */
			_this.historyIndex_ = 0;

			/**
    * History of the different image states during edition. Every
    * state entry represents a change to the image on top of the
    * previous image state.
    * - Images are represented as URIs or base64 encoded urls.
    * - State entries are objects with
    *     - url: the url representing the image
    *     - utils: image utils for this image
      	 *
    * @type {Array.<Object>}
    * @protected
    */
			_this.history_ = [{
				url: _this.image,
				utils: new _ImageEditorUtils2.default(_this.image, _this.element)
			}];
			return _this;
		}

		/**
   * Accepts the current changes applied by the active tool and creates
   * a new entry in the history stack. Doing this will wipe out any
   * stale redo states.
   */


		ImageEditor.prototype.accept = function accept() {
			var _this2 = this;

			var selectedTool = this.components[this.id + '_selected_tool_' + this.selectedTool.controls];
			var utils = this.history_[this.historyIndex_].utils;

			utils.getImageData().then(function (imageData) {
				return selectedTool.process(imageData);
			}).then(function (imageURL) {
				return _this2.createHistoryEntry_(imageURL);
			}).then(function () {
				_this2.selectedTool = null;
				_this2.imagePreview = false;
				_this2.syncHistory_();
			});
		};

		ImageEditor.prototype.createHistoryEntry_ = function createHistoryEntry_(imageURL) {
			// Push new state and discard stale redo states
			this.historyIndex_++;
			this.history_.length = this.historyIndex_ + 1;
			this.history_[this.historyIndex_] = {
				url: imageURL,
				utils: new _ImageEditorUtils2.default(imageURL, this.element)
			};

			return _Promise.CancellablePromise.resolve();
		};

		ImageEditor.prototype.discard = function discard() {
			this.selectedTool = null;
			this.syncHistory_();
		};

		ImageEditor.prototype.edit = function edit(event) {
			this.selectedTool = this.tools.filter(function (tool) {
				return tool.name === event.delegateTarget.getAttribute('data-tool');
			})[0];

			this.syncHistory_();

			this.imagePreview = true;
			this.history_[this.historyIndex_].utils.enablePreview();
		};

		ImageEditor.prototype.getEditorImage = function getEditorImage() {
			return this.element.querySelector('.lfr-image-editor-image-container img');
		};

		ImageEditor.prototype.getEditorImageContainer = function getEditorImageContainer() {
			return this.element.querySelector('.lfr-image-editor-image-container');
		};

		ImageEditor.prototype.redo = function redo() {
			this.historyIndex_++;
			this.syncHistory_();
		};

		ImageEditor.prototype.requestEditorPreview = function requestEditorPreview() {
			var selectedTool = this.components[this.id + '_selected_tool_' + this.selectedTool.controls];
			var utils = this.history_[this.historyIndex_].utils;

			utils.getImageData().then(function (imageData) {
				return selectedTool.preview(imageData);
			}).then(function (imageData) {
				return utils.preview(imageData);
			});
		};

		ImageEditor.prototype.reset = function reset() {
			this.historyIndex_ = 0;
			this.history_.length = 1;
			this.syncHistory_();
		};

		ImageEditor.prototype.syncHistory_ = function syncHistory_() {
			this.image = this.history_[this.historyIndex_].url;

			this.history = {
				canRedo: this.historyIndex_ < this.history_.length - 1,
				canReset: this.history_.length > 1,
				canUndo: this.historyIndex_ > 0
			};
		};

		ImageEditor.prototype.undo = function undo() {
			this.historyIndex_--;
			this.syncHistory_();
		};

		return ImageEditor;
	}(_Component3.default);

	// Register component
	_Soy2.default.register(ImageEditor, _ImageEditor2.default);

	exports.default = ImageEditor;
});
//# sourceMappingURL=ImageEditor.es.js.map