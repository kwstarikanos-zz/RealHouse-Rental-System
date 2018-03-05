(function () {
    'use strict';
    angular.module('BlurAdmin.theme')
        .factory('FileEndPoint', FileEndPoint);

    /** @ngInject */
    function FileEndPoint($rootScope, $http) {
        return {
            getImageByIdAndSize: function (picture, size) {
                return $http({
                    cache: true,
                    url: rootUrl + '/files/picture/' + picture + '/',
                    method: "GET",
                    params: {size: size ? size : "picture"}
                });
            }
        }
    }
})();
