(function () {
    'use strict';
    angular.module('BlurAdmin.theme')
        .factory('AuthenticationEndpoint', AuthenticationEndpoint);

    /** @ngInject */
    function AuthenticationEndpoint($http) {
        return {
            authenticateUser: function (credentials) {
                return $http({
                    url: rootUrl + '/authentication/login/',
                    method: "POST",
                    headers: {'Content-Type': 'application/json'},
                    data: credentials
                });
            },
            logout: function () {
                return $http.get(rootUrl + '/authentication/logout/');
            },
            authenticated: function () {
                return $http.get(rootUrl + '/authentication/check/');
            },
            registerUser: function (register) {
                return $http({
                    url: rootUrl + '/authentication/register/',
                    method: "POST",
                    headers: {'Content-Type': 'application/json'},
                    data: register
                });
            },
            checkUsername: function (username) {
                return $http.get(rootUrl + '/authentication/check/username/' + username + '/');
            },
            checkPhone: function (phone) {
                return $http.get(rootUrl + '/authentication/check/phone/' + phone + '/');
            },
            checkEmail: function (email) {
                return $http.get(rootUrl + '/authentication/check/email/' + email + '/');
            }
        }
    }
})();
