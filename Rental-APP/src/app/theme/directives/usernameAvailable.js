(function () {
    'use strict';

    /*From:
     * https://codepen.io/thepio/pen/rLNBWr?editors=1010*/
    angular.module('BlurAdmin.theme')
        .directive('usernameAvailable', usernameAvailable);

    /** @ngInject */
    function usernameAvailable($q, $timeout, AuthenticationEndpoint) {
        return {
            restrict: 'AE',
            require: 'ngModel',
            link: function (scope, elm, attr, model) {
                model.$asyncValidators.username_not_available = function () {
                    var defer = $q.defer();
                    AuthenticationEndpoint.checkUsername(model.$viewValue)
                        .success(function (data, status, headers, config) {
                            if (data.username.available) {
                                defer.resolve();
                            }
                            else {
                                scope.checkUsernameResponse = "This username is already in use, try another!";
                                defer.reject("This username is already in use, try another!");
                            }
                        }).error(function (data, status, headers, config) {
                        if (status > 0) {
                            scope.checkUsernameResponse = data.error.reason.toString();
                            defer.reject(data.error.reason);
                        } else {
                            scope.checkUsernameResponse = 'Could not communicate with the remote server, try again later!';
                            defer.reject('Could not communicate with the remote server, try again later!');
                        }
                    });
                    return defer.promise;
                };
            }
        }
    }
})();
