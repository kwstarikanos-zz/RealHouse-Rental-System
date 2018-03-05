/**
 * @author v.lugovsky
 * created on 16.12.2015
 */
(function () {
    'use strict';

    angular.module('BlurAdmin.pages.profile', [])
        .config(routeConfig);

    /** @ngInject */
    function routeConfig($stateProvider) {
        $stateProvider
            .state('profile', {
                params: {
                    username: null
                },
                url: '/profile/:username',
                title: 'Profile',
                templateUrl: 'app/pages/profile/profile.html',
                controller: 'ProfilePageCtrl',
                resolve: {
                    promise: function ($rootScope, $stateParams, UserEndPoint) {
                        $rootScope.$pageFinishedLoading = false;
                        console.log($stateParams.username);
                        return UserEndPoint.getProfile($stateParams.username);
                    }
                },
                data: {
                    access: routingConfig.accessLevels.public
                }
            });
    }
})();
