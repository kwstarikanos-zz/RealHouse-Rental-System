(function () {
    'use strict';

    angular.module('BlurAdmin.theme')
        .factory('focus', focus);

    /** @ngInject */
    function focus($rootScope, $timeout, $window) {
        /*From:
        * https://stackoverflow.com/a/25597540/4056565*/
        return function(id) {
            // timeout makes sure that is invoked after any other event has been triggered.
            // e.g. click events that need to run before the focus or
            // inputs elements that are in a disabled state but are enabled when those events
            // are triggered.
            $timeout(function() {
                var element = $window.document.getElementById(id);
                if(element)
                    element.focus();
            });
        };
    }
})();
