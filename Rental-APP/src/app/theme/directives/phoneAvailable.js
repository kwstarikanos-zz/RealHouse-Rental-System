(function () {
    'use strict';

    /*From:
     * https://codepen.io/thepio/pen/rLNBWr?editors=1010*/
    angular.module('BlurAdmin.theme')
        .directive('phoneAvailable', phoneAvailable);

    /** @ngInject */
    function phoneAvailable($q, $timeout, AuthenticationEndpoint) {
        return {
            restrict: 'AE',
            require: 'ngModel',
            link: function (scope, elm, attr, model) {
                model.$asyncValidators.phone_not_available = function () {
                    var defer = $q.defer();
                    AuthenticationEndpoint.checkPhone(model.$viewValue)
                        .success(function (data, status, headers, config) {
                            if (data.phone.available) {
                                defer.resolve();
                            }
                            else {
                                scope.checkPhoneResponse = "This phone is already in use, try another!";
                                defer.reject("This phone is already in use, try another!");
                            }
                        }).error(function () {
                        if (status > 0) {
                            scope.checkPhoneResponse = data.error.reason.toString();
                            defer.reject(data.error.reason);
                        } else {
                            scope.checkPhoneResponse = 'Could not communicate with the remote server, try again later!';
                            defer.reject('Could not communicate with the remote server, try again later!');
                        }
                    });
                    return defer.promise;
                };
            }
        }
    }
})();
