(function () {
  'use strict';

  angular.module('BlurAdmin.theme')
      .directive('upperFirstCase', upperFirstCase);

  /** @ngInject */
  function upperFirstCase($timeout, $parse) {
      return {
          require: 'ngModel',
          link: function (scope, elem, attrs, ctrl) {
              elem.on("blur propertychange keyup paste", function () {
                  var value = elem.val();
                  elem.val(value.substring(0, 1).toUpperCase() + value.substring(1));
              });
          }
      };
  }
})();
