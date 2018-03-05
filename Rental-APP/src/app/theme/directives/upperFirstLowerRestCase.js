(function () {
  'use strict';

  angular.module('BlurAdmin.theme')
      .directive('upperFirstLowerRestCase', upperFirstLowerRestCase);

  /** @ngInject */
  function upperFirstLowerRestCase($timeout, $parse) {
      return {
          require: 'ngModel',
          link: function (scope, elem, attrs, ctrl) {
              elem.on("blur propertychange keyup paste", function () {
                  var value = elem.val().toLowerCase();
                  elem.val(value.substring(0, 1).toUpperCase() + value.substring(1));
              });
          }
      };
  }
})();
