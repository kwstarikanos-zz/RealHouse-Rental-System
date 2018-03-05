/**
 * Change top "Daily Downloads", "Active Users" values with animation effect
 */
(function () {
  'use strict';

  angular.module('BlurAdmin.theme')
      .directive('eventFocus', eventFocus);

  /** @ngInject */
  function eventFocus(focus) {
      return function(scope, elem, attr) {
          elem.on(attr.eventFocus, function() {
              focus(attr.eventFocusId);
          });

          // Removes bound events in the element itself
          // when the scope is destroyed
          scope.$on('$destroy', function() {
              element.off(attr.eventFocus);
          });
      };
  }

})();
