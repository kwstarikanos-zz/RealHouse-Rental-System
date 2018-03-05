/**
 * @author v.lugovksy
 * created on 15.12.2015
 */
(function () {
    'use strict';

    angular.module('BlurAdmin.theme.components')
        .config(toastrLibConfig);

    /** @ngInject */
    function toastrLibConfig(toastrConfig) {
        angular.extend(toastrConfig, {
            closeButton: true,
            closeHtml: '<button>&times;</button>',
            timeOut: 5000,
            allowHtml: true,
            autoDismiss: false,
            containerId: 'toast-container',
            maxOpened: 0,
            newestOnTop: true,
            positionClass: 'toast-bottom-right',
            preventDuplicates: false,
            preventOpenDuplicates: true,
            target: 'body'
        });
    }
})();