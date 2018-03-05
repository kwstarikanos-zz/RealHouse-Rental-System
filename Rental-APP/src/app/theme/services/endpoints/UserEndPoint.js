(function () {
    'use strict';
    angular.module('BlurAdmin.theme')
        .factory('UserEndPoint', UserEndPoint);

    /** @ngInject */
    function UserEndPoint($http) {
        return {
            getUsers: function () {
                return $http.get(rootUrl + '/users/')
            },
            getUsersByRole: function (role) {
                return $http.get(rootUrl + '/' + role + '/show/')
            },
            getUser: function (username) {
                return $http.get(rootUrl + '/users/' + username)
            },
            getProfile: function (username) {
                return $http.get(rootUrl + '/users/' + username + '/profile/')
            },
            getProfilePicture: function (username) {
                return $http.get(rootUrl + '/users/' + username + '/profile/picture')
            },
            createUser: function (user) {
                return $http({
                    url: rootUrl + '/users/',
                    method: "POST",
                    data: user
                })
            },
            updateUser: function (user) {
                return $http({
                    url: rootUrl + '/users/' + user.username + '/',
                    method: "PUT",
                    data: user
                })
            },
            insertProfilePicture: function (username) {
                return $http({
                    url: rootUrl + '/users/' + username + '/picture',
                    method: "PUT",
                    data: user
                })
            }, //TODO: <---- METAMORFOSI FOTOGRAFIAS ANTI GIA AYTO
            confirmUser: function (username) {
                return $http({
                    url: rootUrl + '/users/' + username + '/confirm/',
                    method: "PUT"
                })
            },
            deleteUser: function (username) {
                return $http({
                    url: rootUrl + '/users/' + username + '/',
                    method: "DELETE"
                })
            }
        }
    }
})();
