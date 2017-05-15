// This file was automatically generated from validation.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace ddm.
 * @hassoydeltemplate {ddm.field}
 * @public
 */

if (typeof ddm == 'undefined') { var ddm = {}; }


ddm.__deltemplate_s2_49e0bcef = function(opt_data, opt_ignored) {
  return '' + ddm.validation(opt_data);
};
if (goog.DEBUG) {
  ddm.__deltemplate_s2_49e0bcef.soyTemplateName = 'ddm.__deltemplate_s2_49e0bcef';
}
soy.$$registerDelegateFn(soy.$$getDelTemplateId('ddm.field'), 'validation', 0, ddm.__deltemplate_s2_49e0bcef);


ddm.validationOption = function(opt_data, opt_ignored) {
  return soydata.VERY_UNSAFE.ordainSanitizedHtml('<option ' + soy.$$filterHtmlAttributes(opt_data.option.status) + ' value="' + soy.$$escapeHtmlAttribute(opt_data.option.value) + '">' + soy.$$escapeHtml(opt_data.option.label) + '</option>');
};
if (goog.DEBUG) {
  ddm.validationOption.soyTemplateName = 'ddm.validationOption';
}


ddm.validation = function(opt_data, opt_ignored) {
  var output = '<div class="form-group lfr-ddm-form-field-validation" data-fieldname="' + soy.$$escapeHtmlAttribute(opt_data.name) + '"><div class="form-group"><label class="control-label" for="' + soy.$$escapeHtmlAttribute(opt_data.name) + 'EnableValidation"><input class="enable-validation toggle-switch" ' + soy.$$filterHtmlAttributes(opt_data.enableValidationValue ? 'checked' : '') + ' id="' + soy.$$escapeHtmlAttribute(opt_data.name) + 'EnableValidation" type="checkbox" value="true" /><span aria-hidden="true" class="toggle-switch-bar"><span class="toggle-switch-handle"></span></span><span class="toggle-switch-text toggle-switch-text-right">' + soy.$$escapeHtml(opt_data.validationMessage) + '</span></label></div><div class="' + soy.$$escapeHtmlAttribute(opt_data.enableValidationValue ? '' : 'hide') + ' row"><div class="col-md-6"><select class="form-control validations-select">';
  if (opt_data.validationsOptions) {
    var optionList30 = opt_data.validationsOptions;
    var optionListLen30 = optionList30.length;
    for (var optionIndex30 = 0; optionIndex30 < optionListLen30; optionIndex30++) {
      var optionData30 = optionList30[optionIndex30];
      output += ddm.validationOption(soy.$$augmentMap(opt_data, {option: optionData30}));
    }
  }
  output += '</select></div><div class="col-md-6"><input class="field form-control ' + soy.$$escapeHtmlAttribute(opt_data.parameterMessagePlaceholder ? '' : ' hide') + ' parameter-input" placeholder="' + soy.$$escapeHtmlAttribute(opt_data.parameterMessagePlaceholder) + '" type="text" value="' + soy.$$escapeHtmlAttribute(opt_data.parameterValue) + '" /></div></div><div class="' + soy.$$escapeHtmlAttribute(opt_data.enableValidationValue ? '' : 'hide') + ' row"><div class="col-md-12"><input class="field form-control message-input" placeholder="' + soy.$$escapeHtmlAttribute(opt_data.errorMessagePlaceholder) + '" type="text" value="' + soy.$$escapeHtmlAttribute(opt_data.errorMessageValue) + '" /></div></div><input name="' + soy.$$escapeHtmlAttribute(opt_data.name) + '" type="hidden" value="' + soy.$$escapeHtmlAttribute(opt_data.value) + '" /></div>';
  return soydata.VERY_UNSAFE.ordainSanitizedHtml(output);
};
if (goog.DEBUG) {
  ddm.validation.soyTemplateName = 'ddm.validation';
}
