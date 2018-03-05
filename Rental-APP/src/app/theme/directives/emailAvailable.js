(function () {
  'use strict';

  /*From:
  * https://codepen.io/thepio/pen/rLNBWr?editors=1010*/
  angular.module('BlurAdmin.theme')
      .directive('emailAvailable', emailAvailable);

  /** @ngInject */
  function emailAvailable($q, $timeout, AuthenticationEndpoint) {
      return {
          restrict: 'AE',
          require: 'ngModel',
          link: function(scope, elm, attr, model) {
              model.$asyncValidators.email_not_available = function() {
                  var defer = $q.defer();
                  AuthenticationEndpoint.checkEmail(model.$viewValue)
                      .success(function (data, status, headers, config) {
                          if (data.email.available) {
                              defer.resolve();
                          }
                          else {
                              scope.checkEmailResponse = "This email is already in use, try another!";
                              defer.reject('This email is already in use, try another!');
                          }
                      }).error(function () {
                      if (status > 0) {
                          scope.checkEmailResponse = data.error.reason.toString();
                          defer.reject(data.error.reason);
                      } else {
                          scope.checkEmailResponse = 'Could not communicate with the remote server, try again later!';
                          defer.reject('Could not communicate with the remote server, try again later!');
                      }
                      defer.reject();
                  });
                  return defer.promise;
              };
          }
      }
  }
})();
