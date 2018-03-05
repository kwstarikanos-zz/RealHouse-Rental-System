(function () {
    'use strict';

    /*From:
     https://gist.github.com/VictorBjelkholm/6687484*/
    angular.module('BlurAdmin.theme')
        .directive('googleplace', googleplace);

    /** @ngInject */
    function googleplace() {
        return {
            require: 'ngModel',
            scope: {
                ngModel: '=',
                details: '=?'
            },
            link: function (scope, element, attrs, model) {
                $(document).ready(function () {
                    try {
                        scope.$parent.gPlace = new google.maps.places.Autocomplete(element[0], {});

                        google.maps.event.addListener(scope.$parent.gPlace, 'place_changed', function () {
                            scope.$apply(function () {
                                scope.$parent.refresh();
                                model.$setViewValue(element.val());
                            });
                        });
                    }
                    catch(err) {
                        console.warn(err);
                    }
                });
            }
        };
    }
})();
