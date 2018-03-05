(function () {
    'use strict';

    angular.module('BlurAdmin.theme')
        .directive('slider', slider);

    /** @ngInject */
    function slider($parse, FileEndPoint) {
        return {
            restrict: 'E',
            replace: true,
            transclude: true,
            scope: {
                images: '=',
                size: '=',
                controls: '='
            },
            link: function (scope, elem, attrs) {
                if (scope.images === undefined) {
                    console.warn("The slider will not work without a array of pictures!");
                    return;
                }

                scope.fetching = false;

                scope.currentIndex = 0;

                scope.fetchImage = function (index) {
                    scope.fetching = true;
                    return FileEndPoint.getImageByIdAndSize(scope.images[index].picture_id, attrs.size)
                        .success(function (data, status, headers, config) {
                            scope.fetching = false;
                            scope.images[index].file = data;
                        })
                        .error(function (data, status, headers, config) {
                            scope.fetching = false;
                        });
                };

                scope.next = function () {
                    var next = scope.currentIndex < scope.images.length - 1 ? scope.currentIndex + 1 : scope.currentIndex;
                    if (scope.images[next].file === undefined) {
                        scope.fetchImage(next)
                            .success(function (data, status, headers, config) {
                                scope.currentIndex = next;
                            })
                    } else
                        scope.currentIndex = next;
                };

                scope.prev = function () {
                    var prev = scope.currentIndex > 0 ? scope.currentIndex - 1 : scope.currentIndex;
                    if (scope.images[prev].file === undefined) {
                        scope.fetchImage(prev)
                            .success(function (data, status, headers, config) {
                                scope.currentIndex = prev;
                            })
                    } else
                        scope.currentIndex = prev;
                };


                if (scope.images.length)
                    scope.fetchImage(0);

                scope.images.forEach(function (image) {
                    image.visible = false;
                });

                scope.$watch('currentIndex', function (newValue, oldValue) {
                    if (scope.images.length !== 0) {
                        scope.images[oldValue].visible = false;
                        scope.images[scope.currentIndex].visible = true;
                    }
                });

            },
            template:
            '<div class="slider"> ' +
            '<div ng-if="!images.length" class="slide" style="background-color: #8bc34a;"></div>' +
            '<div class="slide" ng-repeat="image in images" ng-show="image.visible" style="background-image: url(\'data:{{image.file.filetype}};base64,{{image.file.base64}}\');"></div>' +
            /*   '<i ng-if="fetching" class="fa fa-spinner fa-pulse fa-3x fa-fw slider-spinner"></i>' +*/
            '<div ng-if="fetching" class="spinner"></div>' + /*This is from: http://tobiasahlin.com/spinkit/*/
            '<md-button ng-if="controls && currentIndex > 0" class="md-warn arrows arrow-left" style="min-width: 30px;" ng-click="prev()" aria-label="Next"><md-icon  md-svg-src="/assets/img/left-arrow.svg"></md-icon></md-button>' +
            '<md-button ng-if="controls && currentIndex < images.length - 1" style="min-width: 30px;" class="md-warn arrows arrow-right"  ng-click="next()" aria-label="Previous"><md-icon  md-svg-src="/assets/img/right-arrow.svg"></md-icon></md-button>' +
            '</div>'
        };
    }
})();
