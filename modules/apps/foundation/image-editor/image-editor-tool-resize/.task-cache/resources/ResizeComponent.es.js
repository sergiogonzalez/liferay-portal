define("liferay-image-editor-tool-resize@1.0.0/ResizeComponent.es", ['exports', 'metal-component/src/Component', 'metal-soy/src/Soy', 'metal/src/async/async', 'metal/src/core', 'metal-promise/src/promise/Promise', './ResizeComponent.soy', './ResizeControls.soy'], function (exports, _Component2, _Soy, _async, _core, _Promise, _ResizeComponent, _ResizeControls) {
    'use strict';

    Object.defineProperty(exports, "__esModule", {
        value: true
    });

    var _Component3 = _interopRequireDefault(_Component2);

    var _Soy2 = _interopRequireDefault(_Soy);

    var _async2 = _interopRequireDefault(_async);

    var _core2 = _interopRequireDefault(_core);

    var _ResizeComponent2 = _interopRequireDefault(_ResizeComponent);

    var _ResizeControls2 = _interopRequireDefault(_ResizeControls);

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

    var ResizeComponent = function (_Component) {
        _inherits(ResizeComponent, _Component);

        function ResizeComponent() {
            _classCallCheck(this, ResizeComponent);

            return _possibleConstructorReturn(this, _Component.apply(this, arguments));
        }

        ResizeComponent.prototype.attached = function attached() {
            var _this2 = this;

            _async2.default.nextTick(function () {
                _this2.lockProportions = true;

                _this2.imageHeight = _this2.getEditorImage().naturalHeight;
                _this2.imageWidth = _this2.getEditorImage().naturalWidth;

                _this2.imageRatio_ = _this2.imageWidth / _this2.imageHeight;

                _this2.imageHeightInput_ = _this2.element.querySelector('#' + _this2.key + 'Height');
                _this2.imageWidthInput_ = _this2.element.querySelector('#' + _this2.key + 'Width');
            });
        };

        ResizeComponent.prototype.process = function process(imageData) {
            var rawCanvas = document.createElement('canvas');
            rawCanvas.width = imageData.raw.width;
            rawCanvas.height = imageData.raw.height;

            rawCanvas.getContext('2d').putImageData(imageData.raw, 0, 0);

            var canvas = document.createElement('canvas');
            canvas.width = this.imageWidth;
            canvas.height = this.imageHeight;

            canvas.getContext('2d').drawImage(rawCanvas, 0, 0, this.imageWidth, this.imageHeight);

            return _Promise.CancellablePromise.resolve(canvas.toDataURL());
        };

        ResizeComponent.prototype.syncDimensions = function syncDimensions(event) {
            if (this.lockProportions) {
                var newValue = event.delegateTarget.value;

                if (event.delegateTarget === this.imageWidthInput_) {
                    this.imageHeight = newValue / this.imageRatio_;
                    this.imageWidth = newValue;

                    this.imageHeightInput_.value = this.imageHeight;
                } else {
                    this.imageHeight = newValue;
                    this.imageWidth = newValue * this.imageRatio_;

                    this.imageWidthInput_.value = this.imageWidth;
                }
            }
        };

        ResizeComponent.prototype.toggleLockProportions = function toggleLockProportions(event) {
            this.lockProportions = !this.lockProportions;
        };

        return ResizeComponent;
    }(_Component3.default);

    /**
     * State definition.
     * @type {!Object}
     * @static
     */
    ResizeComponent.STATE = {
        /**
         * Injected helper to get the editor image
         * @type {Function}
         */
        getEditorImage: {
            validator: _core2.default.isFunction
        }
    };

    // Register component
    _Soy2.default.register(ResizeComponent, _ResizeComponent2.default);

    exports.default = ResizeComponent;
});
//# sourceMappingURL=ResizeComponent.es.js.map