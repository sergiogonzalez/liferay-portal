// This file was automatically generated from select.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace ddm.
 * @hassoydeltemplate {ddm.field}
 * @public
 */

if (typeof ddm == 'undefined') { var ddm = {}; }


ddm.__deltemplate_s2_2dbfb377 = function(opt_data, opt_ignored) {
  return '' + ddm.select(opt_data);
};
if (goog.DEBUG) {
  ddm.__deltemplate_s2_2dbfb377.soyTemplateName = 'ddm.__deltemplate_s2_2dbfb377';
}
soy.$$registerDelegateFn(soy.$$getDelTemplateId('ddm.field'), 'select', 0, ddm.__deltemplate_s2_2dbfb377);


ddm.select = function(opt_data, opt_ignored) {
  var output = '<div class="form-group' + soy.$$escapeHtmlAttribute(opt_data.visible ? '' : ' hide') + '" data-fieldname="' + soy.$$escapeHtmlAttribute(opt_data.name) + '"><div class="input-select-wrapper">' + ((opt_data.showLabel) ? ddm.select_label(opt_data) : '') + '<div class="form-builder-select-field input-group-container">' + ((! opt_data.readOnly) ? ddm.hidden_select(opt_data) : '') + '<div class="form-control select-field-trigger" dir="' + soy.$$escapeHtmlAttribute(opt_data.dir) + '" id="' + soy.$$escapeHtmlAttribute(opt_data.name) + '" name="' + soy.$$escapeHtmlAttribute(opt_data.name) + '"><a class="select-arrow-down-container" href="javascript:;">' + ((opt_data.selectCaretDoubleIcon) ? soy.$$filterNoAutoescape(opt_data.selectCaretDoubleIcon) : '') + '</a>';
  if (opt_data.multiple) {
    if (opt_data.value.length == 0) {
      output += '<span class="option-selected option-selected-placeholder">' + soy.$$escapeHtml(opt_data.strings.chooseOptions) + '</span>';
    } else {
      output += '<ul class="multiple-badge-list">';
      var currentValueList42 = opt_data.value;
      var currentValueListLen42 = currentValueList42.length;
      for (var currentValueIndex42 = 0; currentValueIndex42 < currentValueListLen42; currentValueIndex42++) {
        var currentValueData42 = currentValueList42[currentValueIndex42];
        output += ddm.badge_item({badgeCloseIcon: opt_data.badgeCloseIcon, value: currentValueData42.value, label: currentValueData42.label});
      }
      output += '</ul>';
    }
  } else {
    if (opt_data.value && opt_data.value.length > 0) {
      var currentValueList53 = opt_data.value;
      var currentValueListLen53 = currentValueList53.length;
      for (var currentValueIndex53 = 0; currentValueIndex53 < currentValueListLen53; currentValueIndex53++) {
        var currentValueData53 = currentValueList53[currentValueIndex53];
        output += (currentValueData53 && currentValueData53.label) ? '<span class="option-selected">' + soy.$$escapeHtml(currentValueData53.label) + '</span>' : '';
      }
    } else {
      output += '<span class="option-selected option-selected-placeholder">' + soy.$$escapeHtml(opt_data.strings.chooseAnOption) + '</span>';
    }
  }
  output += '</div>' + ((! opt_data.readOnly) ? '<div class="drop-chosen ' + soy.$$escapeHtmlAttribute(opt_data.open ? '' : 'hide') + '"><div class="search-chosen"><div class="select-search-container">' + ((opt_data.selectSearchIcon) ? '<a class="" href="javascript:;">' + soy.$$filterNoAutoescape(opt_data.selectSearchIcon) + '</a>' : '') + '</div><input autocomplete="off" class="drop-chosen-search" placeholder="Search" type="text"></div><ul class="results-chosen">' + ddm.select_options(opt_data) + '</ul></div>' : '') + '</div>' + ((opt_data.childElementsHTML) ? soy.$$filterNoAutoescape(opt_data.childElementsHTML) : '') + '</div></div>';
  return output;
};
if (goog.DEBUG) {
  ddm.select.soyTemplateName = 'ddm.select';
}


ddm.badge_item = function(opt_data, opt_ignored) {
  return '<li><span class="badge badge-default badge-sm multiple-badge" data-original-title="' + soy.$$escapeHtmlAttribute(opt_data.label) + '" title="' + soy.$$escapeHtmlAttribute(opt_data.label) + '">' + soy.$$escapeHtml(opt_data.label) + '<a class="trigger-badge-item-close" data-badge-value="' + soy.$$escapeHtmlAttribute(opt_data.value) + '" href="javascript:void(0)">' + soy.$$filterNoAutoescape(opt_data.badgeCloseIcon) + '</a></span></li>';
};
if (goog.DEBUG) {
  ddm.badge_item.soyTemplateName = 'ddm.badge_item';
}


ddm.select_label = function(opt_data, opt_ignored) {
  return '<label class="control-label" for="' + soy.$$escapeHtmlAttribute(opt_data.name) + '">' + soy.$$escapeHtml(opt_data.label) + ((opt_data.required) ? '<span class="icon-asterisk text-warning"></span>' : '') + '</label>' + ((opt_data.tip) ? '<p class="liferay-ddm-form-field-tip">' + soy.$$escapeHtml(opt_data.tip) + '</p>' : '');
};
if (goog.DEBUG) {
  ddm.select_label.soyTemplateName = 'ddm.select_label';
}


ddm.hidden_select = function(opt_data, opt_ignored) {
  var output = '<select class="form-control hide" dir="' + soy.$$escapeHtmlAttribute(opt_data.dir) + '" id="' + soy.$$escapeHtmlAttribute(opt_data.name) + '" name="' + soy.$$escapeHtmlAttribute(opt_data.name) + '" ' + ((opt_data.multiple) ? 'multiple size="' + soy.$$escapeHtmlAttribute(opt_data.options.length) + '"' : '') + '>' + ((! opt_data.readOnly) ? '<option dir="' + soy.$$escapeHtmlAttribute(opt_data.dir) + '" disabled ' + ((opt_data.value.length == 0) ? 'selected' : '') + ' value="">' + soy.$$escapeHtml(opt_data.strings.chooseAnOption) + '</option>' : '');
  var optionList136 = opt_data.options;
  var optionListLen136 = optionList136.length;
  for (var optionIndex136 = 0; optionIndex136 < optionListLen136; optionIndex136++) {
    var optionData136 = optionList136[optionIndex136];
    output += ddm.select_hidden_options({dir: opt_data.dir, option: optionData136, values: opt_data.value});
  }
  output += '</select>';
  return output;
};
if (goog.DEBUG) {
  ddm.hidden_select.soyTemplateName = 'ddm.hidden_select';
}


ddm.select_hidden_options = function(opt_data, opt_ignored) {
  var output = '';
  var selected__soy140 = '';
  if (opt_data.values) {
    var currentValueList146 = opt_data.values;
    var currentValueListLen146 = currentValueList146.length;
    for (var currentValueIndex146 = 0; currentValueIndex146 < currentValueListLen146; currentValueIndex146++) {
      var currentValueData146 = currentValueList146[currentValueIndex146];
      selected__soy140 += (currentValueData146.value == opt_data.option.value) ? 'selected' : '';
    }
  }
  output += '<option dir="' + soy.$$escapeHtmlAttribute(opt_data.dir) + '" ' + soy.$$filterHtmlAttributes(selected__soy140) + ' value="' + soy.$$escapeHtmlAttribute(opt_data.option.value) + '">' + soy.$$escapeHtml(opt_data.option.label) + '</option>';
  return output;
};
if (goog.DEBUG) {
  ddm.select_hidden_options.soyTemplateName = 'ddm.select_hidden_options';
}


ddm.select_options = function(opt_data, opt_ignored) {
  var output = '';
  if (opt_data.options.length > 0) {
    var optionList191 = opt_data.options;
    var optionListLen191 = optionList191.length;
    for (var optionIndex191 = 0; optionIndex191 < optionListLen191; optionIndex191++) {
      var optionData191 = optionList191[optionIndex191];
      var selected__soy160 = '';
      if (opt_data.value) {
        var currentValueList166 = opt_data.value;
        var currentValueListLen166 = currentValueList166.length;
        for (var currentValueIndex166 = 0; currentValueIndex166 < currentValueListLen166; currentValueIndex166++) {
          var currentValueData166 = currentValueList166[currentValueIndex166];
          selected__soy160 += (currentValueData166.value == optionData191.value) ? 'selected' : '';
        }
      }
      output += '<li class="select-option-item ' + ((selected__soy160) ? 'option-selected' : '') + '" data-option-index="' + soy.$$escapeHtmlAttribute(optionIndex191) + '" data-option-selected="' + ((selected__soy160) ? 'true' : '') + '" data-option-value="' + soy.$$escapeHtmlAttribute(optionData191.value) + '">' + ((opt_data.multiple) ? '<input type="checkbox" value="" ' + ((selected__soy160) ? 'checked' : '') + '>' : '') + '<span>' + soy.$$escapeHtml(optionData191.label) + '</span></li>';
    }
  } else {
    output += '<li class="no-results-list"><span>' + soy.$$escapeHtml(opt_data.strings.emptyList) + '</span></li>';
  }
  return output;
};
if (goog.DEBUG) {
  ddm.select_options.soyTemplateName = 'ddm.select_options';
}
