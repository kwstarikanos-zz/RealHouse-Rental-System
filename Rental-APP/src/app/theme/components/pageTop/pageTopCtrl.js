(function () {
    'use strict';

    angular.module('BlurAdmin.theme.components')
        .controller('pageTopCtrl', pageTopCtrl);

    /** @ngInject */
    function pageTopCtrl($scope, $rootScope, $state, $stateParams, Session, alert, focus, $mdDialog, toastr, HTTP_STATUS) {
        $scope.maxPeople = 16;
        $scope.admin = Session.get().role === routingConfig.userRoles.admin;
        $scope.searchData = {
            location: {
                latitude: null,
                longitude: null,
                route: null,
                street_number: null,
                locality: null,
                country: null,
                postal_code: null,
                administrative_area_level_5: null,
                administrative_area_level_4: null,
                administrative_area_level_3: null,
                administrative_area_level_2: null,
                administrative_area_level_1: null
            },
            checkInDate: null,
            checkOutDate: null,
            visitors: null
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
                    $scope.searchData.checkInDate = ev.model.startDate !== null ? ev.model.startDate.format('YYYY-MM-DD') : $scope.searchData.checkInDate;
                    $scope.searchData.checkOutDate = ev.model.endDate !== null ? ev.model.endDate.format('YYYY-MM-DD') : $scope.searchData.checkOutDate;
                    applySearch();
                }
            }
        };

        $scope.clear = function (element) {
            switch (element) {
                case 0:
                    $scope.data.address.formatted_address = null;
                    $scope.searchData.location.locality = null;
                    $scope.searchData.location.country = null;
                    applySearch();
                    break;
                case 1:
                    console.log(element);
                    $scope.dateInterval = {startDate: null, endDate: null};
                    $scope.searchData.checkInDate = null;
                    $scope.searchData.checkOutDate = null;
                    applySearch();
                    break;
                case 2:
                    $scope.searchData.visitors = null;
                    applySearch();
                    break;
            }


        };

        /*Navigation Search*/
        var formattedAddressElem = $('#searchBarFormattedAddress');

        var dateRangePickerElem = $('#searchBarDateRangePicker');

        var visitorsElem = $('#searchBarVisitors');

        formattedAddressElem.on('focus', function () {
            formattedAddressElem.width(310);
            dateRangePickerElem.width(129);
            $('#searchBarFormattedAddress + .bottomInputLine').css('display', 'block')
        });

        dateRangePickerElem.on('focus', function () {
            $('#searchBarDateRangePicker + .bottomInputLine').css('display', 'block')
        });

        visitorsElem.on('focus', function () {
            $('#searchBarVisitors + .bottomInputLine').css('display', 'block')
        });

        formattedAddressElem.on('focusout', function () {
            formattedAddressElem.width(190);
            dateRangePickerElem.width(188);
            $('#searchBarFormattedAddress + .bottomInputLine').css('display', 'none');
        });

        dateRangePickerElem.on('focusout', function () {
            formattedAddressElem.width(190);
            dateRangePickerElem.width(188);
            $('#searchBarDateRangePicker + .bottomInputLine').css('display', 'none');
        });

        visitorsElem.on('focusout', function () {
            $('#searchBarVisitors + .bottomInputLine').css('display', 'none');
            if ($scope.searchData.visitors)
                applySearch();
        });

        $scope.gPlace = null;

        /*TODO: Na allaxei ayto giati ayth h refresh kaleitai apo to directive Autocomplete tis google kai den einai xekatharo to onoma tis*/
        $scope.refresh = function () {
            var latlngbounds = new google.maps.LatLngBounds();
            var place = $scope.gPlace.getPlace();
            console.log(place.address_components);
            getComponents(place.address_components);
            $scope.searchData.location.latitude = place.geometry.location.lat();
            $scope.searchData.location.longitude = place.geometry.location.lng();
            applySearch();
        };

        var getComponents = function (components) {
            $scope.searchData.location = {
                latitude: null,
                longitude: null,
                route: null,
                street_number: null,
                locality: null,
                country: null,
                postal_code: null,
                administrative_area_level_5: null,
                administrative_area_level_4: null,
                administrative_area_level_3: null,
                administrative_area_level_2: null,
                administrative_area_level_1: null
            };
            components.filter(function (component) {
                switch (component.types[0]) {
                    case "street_number":
                        $scope.searchData.location.street_number = component.long_name;
                        break;
                    case "route":
                        $scope.searchData.location.route = component.long_name;
                        break;
                    case "locality":
                        $scope.searchData.location.locality = component.long_name;
                        break;
                    case "country":
                        $scope.searchData.location.country = component.long_name;
                        break;
                    case "postal_code":
                        $scope.searchData.location.postal_code = component.long_name;
                        break;
                    case "administrative_area_level_5":
                        $scope.searchData.location.administrative_area_level_5 = component.long_name;
                        break;
                    case "administrative_area_level_4":
                        $scope.searchData.location.administrative_area_level_4 = component.long_name;
                        break;
                    case "administrative_area_level_3":
                        $scope.searchData.location.administrative_area_level_3 = component.long_name;
                        break;
                    case "administrative_area_level_2":
                        $scope.searchData.location.administrative_area_level_2 = component.long_name;
                        break;
                    case "administrative_area_level_1":
                        $scope.searchData.location.administrative_area_level_1 = component.long_name;
                        break;
                    default:
                        //console.log(component);
                        //console.log(component.types[0]);
                        break;
                }
            }).map(function (obj) {
                console.log(".map");
                console.log(obj.long_name);
                // return obj.long_name;
            });
        };

        $scope.loggedIn = false;

        $scope.credentials = {};

        $scope.credentials = {username: 'admin', password: 'fdsA42Fcdsfg23'};
        /*TODO: <---- REMOVE THIS*/

        $scope.$on('user-is-authenticated', function () {
            $scope.loggedIn = true;
            $scope.admin = Session.get().role === routingConfig.userRoles.admin;
            $scope.username = Session.get().username;
        });

        $scope.$on('user-is-not-authenticated', function () {
            $scope.admin = Session.get().role === routingConfig.userRoles.admin;
            $scope.loggedIn = false;
        });

        $scope.$on('UNAUTHORIZED', function () {
            $scope.admin = Session.get().role === routingConfig.userRoles.admin;
            $scope.loggedIn = false;
        });


        /*Sing out action button*/
        $scope.logout = function logout() {
            Session.logout().then(function () {
                $state.go('residences', {}, {reload: true, inherit: false, notify: true});
            })
        };

        $scope.goToProfile = function () {
            $state.go('profile', {username: Session.getUsername()}, {reload: true, inherit: false});
        };

        /*Sign in action button*/
        $scope.login = function login() {
            $scope.dataLoading = true;
            Session.login($scope.credentials).then(function (data, status, headers, config) {
                $scope.dataLoading = false;
                if (!Session.isConfirmed())
                    toastr.info("Before you are able to list a room, you have to be confirmed by admin!", '<strong>Welcome ' + Session.getUsername() + '!</strong>');
                else {
                    if (Session.get().role === routingConfig.userRoles.admin) {
                        toastr.success('Do not forget that you have the power to delete and accept new requests from users.', '<strong>Welcome ' + Session.getUsername() + '!</strong>');
                    } else {
                        toastr.success('<strong>Welcome ' + Session.getUsername() + '!</strong>');
                    }
                }
            }, function (data, status, headers, config) {
                focus('username');
                $scope.dataLoading = false;
            });
            $scope.credentials = {};
            $scope.credentials = {username: 'admin', password: 'fdsA42Fcdsfg23'};
            /*TODO <------- REMOVE THIS!*/
        };

        /*Sing up action button*/
        $scope.signUpDialog = function ($event) {
            $mdDialog.show({
                controller: 'registerCtrl',
                templateUrl: 'app/theme/components/pageTop/signUpDialog.tmpl.html',
                //parent: angular.element(document.body),
                targetEvent: $event,
                clickOutsideToClose: false
            });
        };

        function applySearch() {
            var params = {};
            if ($scope.searchData !== undefined) {
                if ($scope.searchData.location.locality !== undefined) params.locality = $scope.searchData.location.locality;
                if ($scope.searchData.location.country !== undefined) params.country = $scope.searchData.location.country;
                if ($scope.searchData.checkInDate !== undefined) params.checkin = $scope.searchData.checkInDate;
                if ($scope.searchData.checkOutDate !== undefined) params.checkout = $scope.searchData.checkOutDate;
                if ($scope.searchData.visitors !== undefined) params.guests = $scope.searchData.visitors;
                params.start = 0;
                params.size = $rootScope.size;
                params.type = $rootScope.type;
                params.beds = $rootScope.beds;
                params.min_price = $rootScope.min_price;
                params.max_price = $rootScope.max_price;
            }
            $state.go('residences', params, {reload: true, inherit: false, notify: true});
        }
    }
})();
