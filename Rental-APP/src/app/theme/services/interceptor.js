/**
 * @author n.poltoratsky
 * created on 27.06.2016
 */
(function () {
    'use strict';
    angular.module('BlurAdmin.theme')
        .factory('interceptor', interceptor);

    /** @ngInject */
    function interceptor($injector, $q, $rootScope, HTTP_STATUS) {

        function handleHttpError(rejection) {

            /*Toastr was injected at the runtime here to avoid the circular dependency error.*/
            var toastr = $injector.get('toastr');

            if (rejection.status > 0) {
                if (rejection.data.error !== undefined)
                    toastr.warning('<strong>Target: </strong> ...' + rejection.config.url.replace(rootUrl,'') + '</br><strong>Server says: </strong>' + rejection.data.error.reason.toString(), '<strong>' + rejection.status + ' ' + rejection.data.error.type + '</strong>');
                else
                    toastr.error('<strong>Target: </strong> ...' + rejection.config.url.replace(rootUrl,'') + '</br><strong>Reason: </strong>The server does not explain the reason but has returned back the HTTP status code: ' + rejection.status, '<strong>' + rejection.status + ' ' + rejection.statusText + '</strong>');
            } else
                toastr.error('<strong>Target: </strong> ...' + rejection.config.url.replace(rootUrl,'') + '</br><strong>Reason: </strong>Could not communicate with the remote server.', '<strong>Request failed to complete!</strong>');
        }

        return {
            request: function (config) {
                if (config.url.indexOf(rootUrl) !== -1) {
                    $rootScope.$broadcast('request-start', config);
                }
                //config.timeout = 10000;
                return config;
            },
            requestError: function (rejection) {
                if (rejection.config.url.indexOf(rootUrl) !== -1) {
                    $rootScope.$broadcast('request-error', rejection);
                }
                return $q.reject(rejection);
            },
            response: function (response) {
                if (response.config.url.indexOf(rootUrl) !== -1) {
                    $rootScope.$broadcast('request-end', response);
                }
                return response;
            },
            responseError: function (rejection) {
                if (rejection.config.url.indexOf(rootUrl) !== -1) {
                    handleHttpError(rejection);
                    $rootScope.$broadcast('response-end-with-error', rejection);
                    $rootScope.$broadcast({
                        400: HTTP_STATUS.BAD_REQUEST,
                        401: HTTP_STATUS.UNAUTHORIZED,
                        403: HTTP_STATUS.FORBIDDEN,
                        404: HTTP_STATUS.NOT_FOUND,
                        500: HTTP_STATUS.INTERNAL_SERVER_ERROR
                    }[rejection.status], rejection.data);
                }
                return $q.reject(rejection);
            }
        };
    }
})();


