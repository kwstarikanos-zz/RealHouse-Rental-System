/**
 * @author v.lugovsky
 * created on 16.12.2015
 */
(function () {
    'use strict';
    angular.module('BlurAdmin.pages.adminPanel', [])
        .config(routeConfig);

    /** @ngInject */
    function routeConfig($stateProvider) {
        $stateProvider
            .state('adminPanel', {
                url: '/admin-panel/',
                templateUrl: 'app/pages/adminPanel/adminPanel.html',
                data: {
                    access: routingConfig.accessLevels.admin
                },
                resolve: {
                    users_promise: function ($rootScope, UserEndPoint) {
                        $rootScope.$pageFinishedLoading = false;
                        return UserEndPoint.getUsers();
                    }
                },
                controller: 'adminPanelCtrl',
                title: 'Admin Panel',
                sidebarMeta: {
                    icon: 'ion-gear-a',
                    order: 40,
                }
            });
    }
})();
