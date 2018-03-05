'use strict';

var PASSWORD_REGEXP = /^[1-9]{4,4}$/;
var rootUrl = 'http://example.com:8080/services';
var iconBase = '/assets/img/';

var app = angular.module('BlurAdmin', [
    'ngAnimate',
    'ui.bootstrap',
    'ui.sortable',
    'ui.router',
    'toastr',
    'smart-table',
    "xeditable",
    'ui.slimscroll',
    'ngMessages',
    'ngMaterial',
    'ngCookies',
    'daterangepicker',
    'angular-progress-button-styles',
    'BlurAdmin.theme',
    'BlurAdmin.pages',
    'ngAnimate',
    'angular-loading-bar',
    'lfNgMdFileInput',
    'rzModule'
]);

app.config(['$httpProvider', '$mdThemingProvider', function ($httpProvider, $mdThemingProvider) {
    $httpProvider.interceptors.push('interceptor');
    $httpProvider.defaults.timeout = 5000;
    //$mdThemingProvider.disableTheming();
    $mdThemingProvider.theme('default')
    // Available palettes: red, pink, purple, deep-purple, indigo, blue, light-blue, cyan, teal, green, light-green,
    // lime, yellow, amber, orange, deep-orange, brown, grey, blue-grey
    //.primaryPalette('brown')
        .accentPalette('amber')
        .dark()
    ;
}]);

app.constant('HTTP_STATUS', {
    BAD_REQUEST: 'BAD_REQUEST',
    UNAUTHORIZED: 'UNAUTHORIZED',
    FORBIDDEN: 'FORBIDDEN',
    NOT_FOUND: 'NOT_FOUND',
    INTERNAL_SERVER_ERROR: 'INTERNAL_SERVER_ERROR'
});

app.run(['$rootScope', '$state', 'Session', 'toastr', 'baSidebarService', function ($rootScope, $state, Session, toastr, baSidebarService) {
    Session.init();
    $(document).ready(function () {
        baSidebarService.setMenuCollapsed(true);

        $rootScope.$on("$stateChangeStart", function (event, toState, toParams, fromState, fromParams) {
            if (!('data' in toState) || !('access' in toState.data)) {
                toastr.warning("Access undefined for this state", '<strong>Undefined State Access</strong>');
                event.preventDefault();
            }
            else if (!Session.authorize(toState.data.access)) {
                toastr.error("Seems like you tried accessing a route you don't have access to!", '<strong>Forbidden!</strong>');
                event.preventDefault();
                if (fromState.url === '^') {
                    console.log("fromState.url === '^' = true");
                    if (Session.isActive()) {
                        console.log("Session.isActive() = true");
                        toastr.success("Session.isActive() = true" + $rootScope.error, '<strong>STATE CHANGE START</strong>');
                        //$state.go('news');
                    } else {
                        console.log("Session.isActive() = false");
                        $rootScope.error = null;
                        toastr.warning("Session.isActive() = false" + $rootScope.error, '<strong>STATE CHANGE START</strong>');
                        //$state.go('news');
                    }
                }
            }
        });

        $rootScope.$on('$stateChangeError', function (event, toState, toParams, fromState, fromParams, error) {
            event.preventDefault();
            $rootScope.$pageFinishedLoading = true;
        });

        $rootScope.$on('$stateChangeSuccess', function (event, toState, toParams, fromState, fromParams, error) {
            $rootScope.$pageFinishedLoading = true;
        });

        console.log("The Application Loading Was Completed!");
    });

}]);


/*
app.filter('bytes', function () {
    return function (bytes, speed, precision) {
        if (bytes == 0) return "0";
        if (isNaN(parseFloat(bytes)) || !isFinite(bytes)) {
            return '-';
        }
        var speedUnits = ['bps', 'kbps', 'Mbps', 'Gbps', 'Tbps', 'Pbps'];
        var capacityUnits = ['bytes', 'kB', 'MB', 'GB', 'TB', 'PB'];
        var multiplier = 1;
        if (typeof precision === 'undefined') {
            precision = 1;
        }
        if (speed)
            multiplier = 8;
        var number = Math.floor(Math.log(multiplier * bytes) / Math.log(1024));
        var unit = (speed === true) ? speedUnits[number] : capacityUnits[number];
        var out = ((multiplier * bytes) / Math.pow(1024, Math.floor(number))).toFixed(precision) + ' ' + unit;
        return out;
    }
});

*/
app.filter('ageFilter', function(){
    return function(birthday){
        var birthday = new Date(birthday);
        var today = new Date();
        var age = ((today - birthday) / (31557600000));
        var age = Math.floor( age );
        return age;
    }
});