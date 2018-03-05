(function () {
    'use strict';
    angular.module('BlurAdmin.theme')
        .service('blobToBase64', blobToBase64);

    /** @ngInject */
    function blobToBase64(toastr, $q) {
        return function (file) {
            var deferer = $q.defer();
            var reader = new FileReader();
            reader.onload = function (readerEvt) {
                deferer.resolve(btoa(readerEvt.target.result));
            };
            reader.onerror = function (error) {
                deferer.reject(error);
                toastr.error(JSON.stringify(error), '<strong>File Reader Error!</strong>');
            };
            reader.readAsBinaryString(file);
            return deferer.promise;
        }
    }
})();
