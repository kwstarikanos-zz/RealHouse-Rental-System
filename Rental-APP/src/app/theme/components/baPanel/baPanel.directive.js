/**
 * @author v.lugovsky
 * created on 23.12.2015
 */
(function () {
  'use strict';

  /**
   * Includes basic panel layout inside of current element.
   */
  angular.module('BlurAdmin.theme')
      .directive('baPanel', baPanel);

  /** @ngInject */
  function baPanel(baPanel, baConfig) {
    return angular.extend({}, baPanel, {

      template: function(el, attrs) {
        var res = '<div  class="panel ' + (baConfig.theme.blur ? 'panel-blur' : '') + ' full-invisible ' + (attrs.baPanelClass || '');
        res += '" zoom-in ' + (baConfig.theme.blur ? 'ba-panel-blur' : '') + '>';

        if(attrs.baPanelInfoTitle) {
            res += '<button type="button" ' +
                'class="btn  btn-xs btn-default btn-icon" ' +
                'style="font-size: 20px; float: right; border-width: 1px; color: #ffffff; background: rgba(0, 0, 0, 0); border-color: transparent; ' +
                'data-toggle="modal" ' +
                'ng-click="open(\'app/pages/dashboard/infoModal.html\')"' +
                'border-color: rgba(255, 255, 255, 0);">' +
                '<i class="ion-information"></i>' +
                '</button>';
        }

        res += baPanel.template(el, attrs);

        res += '</div>';
        return res;
      }
    });
  }
})();
