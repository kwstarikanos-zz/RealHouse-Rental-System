(function () {
  'use strict';

  angular.module('BlurAdmin.theme')
      .directive('starRating', starRating);

  /** @ngInject */
  function starRating() {
      return {
          restrict: 'A',
          template: '<ul class="rating">' +
          '<li ng-repeat="star in stars" ng-class="star">' +
          '\u2605' +
          '</li>' +
          '</ul>',
          scope: {
              ratingValue: '=',
              max: '='
          },
          link: function (scope, elem, attrs) {
              scope.stars = [];
              for (var i = 0; i < scope.max; i++) {
                  scope.stars.push({
                      filled: i < scope.ratingValue
                  });
              }
          }
      };
  }
})();
