/**
 * @author v.lugovksy
 * created on 16.12.2015
 */
(function () {
    'use strict';

    angular.module('BlurAdmin.theme.components')
        .controller('BaSidebarCtrl', BaSidebarCtrl);

    /** @ngInject */
    function BaSidebarCtrl($scope,$state, baSidebarService, Session) {
        /*We fount the solution of this from:
         * https://github.com/akveo/blur-admin/issues/233
         * */
        $scope.menuItems = baSidebarService.getAuthorizedMenuItems(Session.get().role);
        $scope.$on('$stateChangeSuccess', eventListener);
        $scope.$on('user-is-not-authenticated', eventListener);
        $scope.defaultSidebarState = $scope.menuItems[0].stateRef;
        $scope.hoverItem = function ($event) {
            $scope.showHoverElem = true;
            $scope.hoverElemHeight = $event.currentTarget.clientHeight;
            var menuTopValue = 66;
            $scope.hoverElemTop = $event.currentTarget.getBoundingClientRect().top - menuTopValue;
        };

        $scope.menuItemClick = function (item) {
            console.log(item);
            switch (item.name) {
                case 'residences':
                    if($state.current.name !== 'residences')
                        $state.go('residences', {}, {reload: false, inherit: false});
                    break;
            }
        };

        $scope.$on('$stateChangeSuccess', function () {
            baSidebarService.setMenuCollapsed(true);
        });

        function eventListener() {
            $scope.menuItems = baSidebarService.getAuthorizedMenuItems(Session.get().role);
        }
    }
})();