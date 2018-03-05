/**
 * @author v.lugovsky
 * created on 16.12.2015
 */
(function () {
  'use strict';

  angular.module('BlurAdmin.pages.profile')
    .controller('ProfilePageCtrl', ProfilePageCtrl);

  /** @ngInject */
  function ProfilePageCtrl($scope, promise, UserEndPoint) {
      console.log("profile CTRL");

      $scope.profile = promise.data;
      console.log(promise.data);

      UserEndPoint.getUser($scope.profile.owner)
          .success(function (data, status, headers, config) {
              $scope.user = data;
      });

      UserEndPoint.getProfilePicture($scope.profile.owner)
          .success(function (data, status, headers, config) {
              $scope.picture = "data:" + data.filetype + ";base64," + data.base64;
          });
  }
})();
