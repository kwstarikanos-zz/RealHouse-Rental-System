(function () {
    'use strict';
    angular.module('BlurAdmin.pages.residence', [])
        .config( routeConfig);

    /** @ngInject */
    function routeConfig($stateProvider) {
        $stateProvider
            .state('residence', {
                url: '/residences/:id',
                params: {
                    id: "0"
                },
                templateUrl: 'app/pages/residence/residence.html',
                controller: 'residenceCtrl',
                title: 'Residence',
                resolve: {
                    promise: function ($rootScope, $stateParams, RoomEndPoint) {
                        $rootScope.$pageFinishedLoading = false;
                        return RoomEndPoint.getRoom($stateParams.id);
                    }
                },
                data: {
                    access: routingConfig.accessLevels.public
                }
            });
    }
})();
