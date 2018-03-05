/**
 * @author v.lugovsky
 * created on 16.12.2015
 */
(function () {
    'use strict';
    angular.module('BlurAdmin.pages.residences', [])
        .config(routeConfig);

    /** @ngInject */
    function routeConfig($stateProvider) {
        $stateProvider
            .state('residences', {
                url: '/residences/?start&size&type&beds&min_price&max_price&checkin&checkout&locality&country&guests',
                templateUrl: 'app/pages/residences/residences.html',
                data: {
                    access: routingConfig.accessLevels.public
                },
                cache: false,
                resolve: {
                    promise: function ($rootScope, $stateParams, RoomEndPoint) {
                        $rootScope.$pageFinishedLoading = false;
                        return RoomEndPoint.getRooms($stateParams);
                    }
                },
                params: {
                    start: {
                        value: '0',
                        squash: true
                    },
                    size: {
                        value: '8',
                        squash: true
                    },
                    type: {
                        value: '0',
                        squash: true
                    },
                    beds: {
                        value: '0',
                        squash: true
                    },
                    min_price: {
                        value: null,
                        squash: true
                    },
                    max_price: {
                        value: null,
                        squash: true
                    },
                    checkin: {
                        value:  null,
                        squash: true
                    },
                    checkout: {
                        value: null,
                        squash: true
                    },
                    locality: {
                        value: null,
                        squash: true
                    },
                    country: {
                        value: null,
                        squash: true
                    },
                    guests: {
                        value: null,
                        squash: true
                    }
                },
                controller: 'residencesCtrl',
                title: 'Residences',
                sidebarMeta: {
                    icon: 'ion-ios-home',
                    order: 20
                }
            });
    }
})();
