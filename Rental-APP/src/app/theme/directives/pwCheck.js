(function () {
  'use strict';

  /*From:
  * https://stackoverflow.com/questions/31039034/custom-angularjs-confirm-password-directive-not-working*/
  angular.module('BlurAdmin.theme')
      .directive('pwCheck', pwCheck);

  /** @ngInject */
  function pwCheck() {
      return {
          require: 'ngModel',
          scope: {
              otherModelValue: '=pwCheck'
          },
          link: function(scope, element, attributes, ngModel) {

              ngModel.$validators.pwCheck = function(modelValue) {
                  return modelValue === scope.otherModelValue;
              };

              scope.$watch('otherModelValue', function() {
                  ngModel.$validate();
              });
          }
      };
  }
})();


