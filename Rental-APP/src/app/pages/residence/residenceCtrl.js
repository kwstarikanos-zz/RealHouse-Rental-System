(function () {
    'use strict';

    angular.module('BlurAdmin.pages.residence')
        .controller('residenceCtrl', residenceCtrl);

    /** @ngInject */
    function residenceCtrl($scope, promise, $state, RoomEndPoint, Session, toastr, UserEndPoint) {
        console.log(promise.data);

        $scope.room = promise.data;

        UserEndPoint.getProfilePicture($scope.room.profile.owner)
            .success(function (data, status, headers, config) {
                $scope.ownerPicture = "data:" + data.filetype + ";base64," + data.base64;
            });

        var uploadedCount = 0;

        $scope.files = [];

        $scope.onChange = function (e, fileList) {
            //alert('this is on-change handler!');
            console.log(e);
            console.log(fileList);
            console.log($scope.files);
        };

        $scope.uploadImages = function () {
            console.log("uploadImages");

            RoomEndPoint.insertRoomPictures($scope.room.id, $scope.files)
                .success(function (data, status, headers, config) {
                    toastr.success('Your Image/s was uploaded successfully!', '<strong>Booking</strong>');
                })
        };

        $scope.totalCost = null;
        $scope.days = null;
        $scope.visitors = null;

        $scope.goToProfile = function (username) {
            $state.go('profile', {username: username}, {reload: true, inherit: false});
        };

        $scope.dateInterval = {startDate: null, endDate: null};

        $scope.datepickerOptions = {
            locale: {
                format: 'YYYY-MM-DD',
                applyLabel: "Apply",
                cancelLabel: 'Cancel',
                autoUpdateInput: true,
                customRangeLabel: 'Custom range'
            },
            autoUpdateInput: true,
            autoApply: true,
            minDate: moment(),
            eventHandlers: {
                'apply.daterangepicker': function (ev, picker) {
                    $scope.checkInDate = ev.model.startDate.format('YYYY-MM-DD');
                    $scope.checkOutDate = ev.model.endDate.format('YYYY-MM-DD');
                    $scope.days = ev.model.endDate.diff(ev.model.startDate, 'days') + 1;
                    $scope.totalCost = $scope.room.overnight_price * $scope.days;
                }
            }
        };

        $scope.reserveRoom = function () {
            if (($scope.visitors > $scope.room.max_people) || $scope.visitors === null || $scope.checkInDate === undefined || $scope.checkOutDate === undefined) {
                if ($scope.checkInDate === undefined || $scope.checkOutDate === undefined) {
                    toastr.warning('<strong>You should pick checkin and checkout dates!</strong>');
                }
                else if (($scope.visitors > $scope.room.max_people) || $scope.visitors === null) {
                    toastr.warning('<strong>The room/house can have minimum 1 guest and maximum ' + $scope.room.max_people + ' guests!</strong>');
                    $scope.visitors = 1;
                }

            } else {
                var reservation = {
                    checkInDate: $scope.checkInDate,
                    checkOutDate: $scope.checkOutDate,
                    guests: $scope.visitors,
                    total_cost: $scope.totalCost
                };
                console.log(reservation);
                RoomEndPoint.bookRoom($scope.room.id, Session.getUsername(), reservation)
                    .success(function (data, status, headers, config) {
                        toastr.success('Your booking was successful!', '<strong>Booking</strong>');
                    })
            }
        }
    }
})();
