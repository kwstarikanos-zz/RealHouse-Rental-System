/**
 * Change top "Daily Downloads", "Active Users" values with animation effect
 */
(function () {
  'use strict';

  angular.module('BlurAdmin.theme')
      .directive('alert', alert);

  /** @ngInject */
  function alert() {
      return {
          restrict: 'E',
          template: '<div ' +
          'style=\"margin: 0 auto;\" class=\"alert \" ' +
          'ng-class=\"{\'bg-info\': Alert.type === \'info\',\'bg-success\': Alert.type === \'success\',\'bg-warning\': Alert.type === \'warning\',\'bg-danger\': Alert.type === \'danger\'}\"ng-if=\"Alert\"> ' +
          '<button ng-if=\"Alert.closable\" type=\"button\" class=\"close\" aria-label=\"Close\"><span aria-hidden=\"true\">&times;</span></button>' +
          '<div ng-bind=\"Alert.message\"></div>' +
          '</div>'
      };
  }
})();
