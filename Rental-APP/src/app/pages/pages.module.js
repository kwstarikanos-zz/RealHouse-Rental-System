/**
 * @author v.lugovsky
 * created on 16.12.2015
 */
(function () {
    'use strict';

    angular.module('BlurAdmin.pages', [
        'ui.router',
        'BlurAdmin.pages.residences',
        'BlurAdmin.pages.hostPanel',
        'BlurAdmin.pages.adminPanel',
        'BlurAdmin.pages.residence',
        'BlurAdmin.pages.profile'
    ])
        .config(routeConfig);

    /** @ngInject */
    function routeConfig($urlRouterProvider, baSidebarServiceProvider, $stateProvider) {
        $urlRouterProvider.otherwise('/residences/');

 /*       $stateProvider
            .state('residences', {
                url: '/residence',
                abstract: true,
                template: '<div ui-view  autoscroll="true" autoscroll-body-top></div>',
                title: "Residences",
                sidebarMeta: {
                    icon: 'ion-network',
                    order: 50,
                },
            });

        $stateProvider
            .state('residences.' + "test", {
                url: '/' + "test",
                templateUrl: 'app/pages/dashboard/dashboard.html',
                controller: 'dashboardCtrl',
                title: "Test",
                groupId: "test",
                sidebarMeta: {
                    order: 1,
                },
                class: "active",
            });
*/



    }

})();
