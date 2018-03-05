(function () {
    'use strict';

    /*From:
     *http://justinwatson.name/2013/11/07/AngularJS_with_Geocode_Validation.html*/
    angular.module('BlurAdmin.theme')
        .directive('validLocation', validLocation);

    /** @ngInject */
    function validLocation($q) {
        return {
            require: 'ngModel',
            link: function (scope, elm, attrs, ctrl) {
                ctrl.$asyncValidators.location_invalid = function () {
                    var defer = $q.defer();

                    if (ctrl.$viewValue.length < 5) {
                        defer.reject(undefined);
                    } else {
                        var geocoder = new google.maps.Geocoder();
                        var myLatLng = new google.maps.LatLng(36.05, -118.25);
                        geocoder.geocode({'address': ctrl.$viewValue, latLng: myLatLng}, function (results, status) {
                            if (status === google.maps.GeocoderStatus.OK) {
                                if (1 === results.length) {
                                    defer.resolve(ctrl.$viewValue);
                                } else {
                                    defer.reject(undefined);
                                }
                            }
                        });
                    }
                    return defer.promise;
                };
            }
        };
    }
})();
