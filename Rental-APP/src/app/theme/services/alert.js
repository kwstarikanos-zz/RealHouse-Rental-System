(function () {
    'use strict';

    angular.module('BlurAdmin.theme')
        .factory('alert', alert);

    /** @ngInject */
    function alert($rootScope) {

        function initService() {
            $rootScope.$on('$locationChangeStart', function () {
                clearAlertMessage();
            });
            function clearAlertMessage() {
                var Alert = $rootScope.Alert;
                if (Alert) {
                    if (!Alert.keepAfterLocationChange) {
                        delete $rootScope.Alert;
                    } else {
                        // only keep for a single location change
                        Alert.keepAfterLocationChange = false;
                    }
                }
            }
        }

        function info(message, keepAfterLocationChange, closable) {
            $rootScope.Alert = {
                closable: closable,
                message: message,
                type: 'info',
                keepAfterLocationChange: keepAfterLocationChange
            };
        }

        function success(message, keepAfterLocationChange, closable) {
            $rootScope.Alert = {
                closable: closable,
                message: message,
                type: 'success',
                keepAfterLocationChange: keepAfterLocationChange
            };
        }

        function warning(message, keepAfterLocationChange, closable) {
            $rootScope.Alert = {
                closable: closable,
                message: message,
                type: 'warning',
                keepAfterLocationChange: keepAfterLocationChange
            };
        }

        function danger(message, keepAfterLocationChange, closable) {
            $rootScope.Alert = {
                closable: closable,
                message: message,
                type: 'danger',
                keepAfterLocationChange: keepAfterLocationChange
            };
        }

        initService();

        return {
            info: info,
            success: success,
            warning: warning,
            danger: danger
        };
    }

})();
