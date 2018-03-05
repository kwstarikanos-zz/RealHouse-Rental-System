/**
 * @author v.lugovsky
 * created on 16.12.2015
 */
(function () {
    'use strict';
    angular.module('BlurAdmin.pages.hostPanel', [])
        .config( routeConfig);

    /** @ngInject */
    function routeConfig($stateProvider) {
        $stateProvider
            .state('hostPanel', {
                url: '/host-panel/',
                templateUrl: 'app/pages/hostPanel/hostPanel.html',
                controller: 'hostPanelCtrl',
                title: 'Host Panel',
                resolve: {
                    promise: function ($rootScope, $stateParams, RoomEndPoint, Session) {
                        $rootScope.$pageFinishedLoading = false;
                        return RoomEndPoint.getRoomsByOwner(Session.getUsername());
                    }
                },
                sidebarMeta: {
                    icon: 'ion-ios-body',
                    order: 30
                },
                data: {
                    access: routingConfig.accessLevels.host
                }
            });
    }
})();
