/**
 * From:
 * https://stackoverflow.com/a/18942309/4056565
 */
(function () {
    'use strict';

    angular.module('BlurAdmin.theme')
        .service('geocoder', geocoder);

    /** @ngInject */
    function geocoder() {
        return {
            geocode: function (point, fromLatLng, outerCallback) {
                var geocoder = new google.maps.Geocoder();
                geocoder.geocode(fromLatLng ? {latLng: point} : {'address': point}, function (results, status) {
                    if (status === google.maps.GeocoderStatus.OK) {
                        outerCallback({success: true, results: results});
                    } else {
                        outerCallback({
                            success: false,
                            error: new Error('Geocode was not successful for the following reason: ' + status)
                        });
                    }
                });
            }
        }
    }
})();
