/*
 .success(function (data, UserEndPoint, status, headers, config) {

 changeUser(user);

 $rootScope.$broadcast('user-is-authenticated', data.access.role);
 window.localStorage.setItem("accessToken", data.access.token);
 })*/

(function () {
    'use strict';

    angular.module('BlurAdmin.theme')
        .factory('Session', Session);

    /** @ngInject */
    function Session($rootScope, $http, $q, $state, $cookies, AuthenticationEndpoint) {
        var accessLevels = routingConfig.accessLevels;
        var userRoles = routingConfig.userRoles;
        var defaultCookie = {username: '', role: userRoles.public, confirmed: false, token: null};
        var currentSession = null;
        var cookie = null;
        var tokenPayload = null;

        return {
            init: function () {
                cookie = $cookies.get('doghouse');
                currentSession = cookie ? JSON.parse(cookie) : defaultCookie;
                $http.defaults.headers.common['authorization'] = 'Bearer ' + currentSession.token;
                this.isActive();
            },

            isConfirmed: function () {
                return currentSession.confirmed;
            },

            getUsername: function () {
                return currentSession.username;
            },

            isAdmin: function () {
                return currentSession.role === userRoles.admin;
            },

            isRenter: function () {
                return currentSession.role === userRoles.renter;
            },

            isHost: function () {
                return currentSession.role === userRoles.host;
            },

            get: function () {
                return currentSession;
            },

            accessLevels: accessLevels,

            userRoles: userRoles,

            isActive: function () {
                var deferer = $q.defer();
                if (currentSession.token !== null) {
                    AuthenticationEndpoint.authenticated()
                        .success(function (data, status, headers, config) {
                            $rootScope.$broadcast('user-is-authenticated');
                            deferer.resolve();
                        })
                        .error(function (data, status, headers, config) {
                            $rootScope.$broadcast('user-is-not-authenticated');
                            clearSessionData();
                            deferer.reject();
                        });
                } else
                    deferer.reject();
                return deferer.promise;
            },

            register: function (register) {
                return AuthenticationEndpoint.registerUser(register);
            },

            login: function (credentials) {
                return AuthenticationEndpoint.authenticateUser(credentials)
                    .success(function (data, status, headers, config) {
                        setSessionData(data.access.token);
                        $rootScope.$broadcast('user-is-authenticated');
                        switch (currentSession.role) {
                            case userRoles.admin:
                                $state.go('adminPanel');
                                break;
                            case userRoles.host:
                                $state.go('hostPanel');
                                break;
                            default:
                                $state.go('residences');
                                break;
                        }
                    })
            },

            logout: function () {
                var deferer = $q.defer();
                if (currentSession.token !== null) {
                    return AuthenticationEndpoint.logout()
                        .success(function (data, status, headers, config) {
                            clearSessionData();
                            $rootScope.$broadcast('user-is-not-authenticated');
                        })
                } else
                    deferer.reject();
                return deferer.promise;
            },

            authorize: authorize
        };

        /* From:
         * https://stackoverflow.com/a/38552302/4056565
         * */
        function parseJwt(token) {
            var base64Url = token.split('.')[1];
            var base64 = base64Url.replace('-', '+').replace('_', '/');
            return JSON.parse(window.atob(base64));
        }

        function setSessionData(token) {
            var tokenPayload = parseJwt(token), role;
            var confirmed = true;
            $http.defaults.headers.common['authorization'] = 'Bearer ' + token;

            switch (tokenPayload.role) {
                case 'admin':
                    role = userRoles.admin;
                    break;
                case 'host':
                    role = tokenPayload.confirmed ? userRoles.host : userRoles.renter;
                    confirmed = tokenPayload.confirmed;
                    break;
                case 'renter':
                    role = userRoles.renter;
                    break;
                default:
                    role = userRoles.public;
                    break;
            }

            var session = {username: tokenPayload.sub, role: role, confirmed: confirmed, token: token};
            angular.extend(currentSession, session);
            var exp = new Date(null);
            exp.setSeconds(tokenPayload.exp);
            exp.setHours(exp.getHours() + 4);
            $cookies.put('doghouse', JSON.stringify(session), {expires: exp});
        }

        function clearSessionData() {
            defaultCookie = {username: '', role: userRoles.public, confirmed: false, token: null};
            angular.extend(currentSession, defaultCookie);
            $http.defaults.headers.common['authorization'] = 'Bearer ';
            $cookies.remove('doghouse');
        }

        function authorize(accessLevel, role) {
            if (role === undefined) {
                role = currentSession.role;
            }
            /*
             console.log("\nSESSION::AUTHORIZE() : role: " + JSON.stringify(role));
             console.log("\naccessLevel.bitMask");
             console.log(accessLevel.bitMask);
             console.log("\nrole.bitMask");
             console.log(role.bitMask);
             console.log("\naccessLevel.bitMask & role.bitMask");
             console.log(accessLevel.bitMask & role.bitMask);
             */
            return accessLevel.bitMask & role.bitMask;
        }
    }
})();
