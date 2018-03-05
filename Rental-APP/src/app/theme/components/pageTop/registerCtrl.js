(function () {
    'use strict';

    angular.module('BlurAdmin.theme.components')
        .controller('registerCtrl', registerCtrl);

    /** @ngInject */
    function registerCtrl($scope, $rootScope, alert, Session, focus, $mdDialog, geocoder, toastr) {
        $scope.original = {
            account: {
                username: null,
                password: null,
                password_confirm: null,
                email: null,
                host: false
            },
            profile: {
                firstname: null,
                lastname: null,
                phone: null,
                birthday: null
            },
            address: {
                latitude: null,
                longitude: null,
                route: null,
                street_number: null,
                locality: null,
                postal_code: null,
                country: null,
                administrative_area_level_5: null,
                administrative_area_level_4: null,
                administrative_area_level_3: null,
                administrative_area_level_2: null,
                administrative_area_level_1: null,
                formatted_address: null
            }
        };

        $scope.data = angular.copy($scope.original);

        var currentDate = new Date();

        $scope.maxDate = new Date(
            currentDate.getFullYear() - 18,
            currentDate.getMonth(),
            currentDate.getDate()
        );

        $scope.gPlace = null;

        $scope.map = null;

        $scope.marker = null;

        var setMarker = function (position) {
            if ($scope.marker) {
                $scope.marker.setPosition(position);
            } else {
                $scope.marker = new google.maps.Marker({
                    position: position,
                    animation: google.maps.Animation.DROP,
                    //draggable: true,
                    map: $scope.map
                });
            }
        };

        var removeMarker = function () {
            $scope.marker.setMap(null);
            $scope.marker = null;
        };

        var clickOnMap = function (event) {
            /*Find address from latLng*/
            geocoder.geocode(event.latLng, true, function (callbackResult) {
                if (callbackResult.success) {
                    setMarker(event.latLng);
                    getComponents(callbackResult.results[0].address_components);
                    $scope.data.address.latitude = event.latLng.lat();
                    $scope.data.address.longitude = event.latLng.lng();
                    $scope.data.address.formatted_address = callbackResult.results[0].formatted_address;
                    $scope.addressForm.$pristine = false;
                    $scope.addressForm.$dirty = true;
                    $scope.$apply();
                }
                else {
                    toastr.error(callbackResult.error.toString(), '<strong>Geocode ERROR!</strong>');
                }
            });
        };

        $scope.refresh = function () {
            var latlngbounds = new google.maps.LatLngBounds();
            var place = $scope.gPlace.getPlace();
            console.log(place.address_components);
            getComponents(place.address_components);
            $scope.data.address.latitude = place.geometry.location.lat();
            $scope.data.address.longitude = place.geometry.location.lng();
            setMarker(place.geometry.location);
            latlngbounds.extend(place.geometry.location);
            $scope.map.setCenter(latlngbounds.getCenter());
        };

        var getComponents = function (components) {
            $scope.data.address.route = null;
            $scope.data.address.street_number = null;
            $scope.data.address.locality = null;
            $scope.data.address.country = null;
            $scope.data.address.postal_code = null;
            $scope.data.address.administrative_area_level_5 = null;
            $scope.data.address.administrative_area_level_4 = null;
            $scope.data.address.administrative_area_level_3 = null;
            $scope.data.address.administrative_area_level_2 = null;
            $scope.data.address.administrative_area_level_1 = null;
            components.filter(function (component) {
                //console.log(component);
                switch (component.types[0]) {
                    case "street_number":
                        $scope.data.address.street_number = component.long_name;
                        break;
                    case "route":
                        $scope.data.address.route = component.long_name;
                        break;
                    case "locality":
                        $scope.data.address.locality = component.long_name;
                        break;
                    case "country":
                        $scope.data.address.country = component.long_name;
                        break;
                    case "postal_code":
                        $scope.data.address.postal_code = component.long_name;
                        break;
                    case "administrative_area_level_5":
                        $scope.data.address.administrative_area_level_5 = component.long_name;
                        break;
                    case "administrative_area_level_4":
                        $scope.data.address.administrative_area_level_4 = component.long_name;
                        break;
                    case "administrative_area_level_3":
                        $scope.data.address.administrative_area_level_3 = component.long_name;
                        break;
                    case "administrative_area_level_2":
                        $scope.data.address.administrative_area_level_2 = component.long_name;
                        break;
                    case "administrative_area_level_1":
                        $scope.data.address.administrative_area_level_1 = component.long_name;
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

        function getLocation() {
            /* From:
             * https://stackoverflow.com/questions/13780583/get-current-location-on-google-map
             */
            if (navigator.geolocation) {
                navigator.geolocation.getCurrentPosition(function (position) {
                    var lat = position.coords.latitude;
                    var lng = position.coords.longitude;
                    $scope.map.setCenter(new google.maps.LatLng(lat, lng));
                });
            } else {
                toastr.warning("Geolocation is not supported by this browser.", '<strong>Geolocation</strong>');
            }
        }

        var initializeMap = function (element) {
            var mapCanvas = document.getElementById(element);
            var mapOptions = {
                center: {lat: 37.975191578937206, lng: 23.715534210205078},
                zoom: 13,
                disableDefaultUI: true,
                styles: [
                    {
                        "elementType": "geometry",
                        "stylers": [
                            {
                                "color": "#ebe3cd"
                            }
                        ]
                    },
                    {
                        "elementType": "labels.text.fill",
                        "stylers": [
                            {
                                "color": "#523735"
                            }
                        ]
                    },
                    {
                        "elementType": "labels.text.stroke",
                        "stylers": [
                            {
                                "color": "#f5f1e6"
                            }
                        ]
                    },
                    {
                        "featureType": "administrative",
                        "elementType": "geometry.stroke",
                        "stylers": [
                            {
                                "color": "#c9b2a6"
                            }
                        ]
                    },
                    {
                        "featureType": "administrative.land_parcel",
                        "elementType": "geometry.stroke",
                        "stylers": [
                            {
                                "color": "#dcd2be"
                            }
                        ]
                    },
                    {
                        "featureType": "administrative.land_parcel",
                        "elementType": "labels.text.fill",
                        "stylers": [
                            {
                                "color": "#ae9e90"
                            }
                        ]
                    },
                    {
                        "featureType": "landscape.natural",
                        "elementType": "geometry",
                        "stylers": [
                            {
                                "color": "#dfd2ae"
                            }
                        ]
                    },
                    {
                        "featureType": "poi",
                        "elementType": "geometry",
                        "stylers": [
                            {
                                "color": "#dfd2ae"
                            }
                        ]
                    },
                    {
                        "featureType": "poi",
                        "elementType": "labels.text.fill",
                        "stylers": [
                            {
                                "color": "#93817c"
                            }
                        ]
                    },
                    {
                        "featureType": "poi.park",
                        "elementType": "geometry.fill",
                        "stylers": [
                            {
                                "color": "#a5b076"
                            }
                        ]
                    },
                    {
                        "featureType": "poi.park",
                        "elementType": "labels.text.fill",
                        "stylers": [
                            {
                                "color": "#447530"
                            }
                        ]
                    },
                    {
                        "featureType": "road",
                        "elementType": "geometry",
                        "stylers": [
                            {
                                "color": "#f5f1e6"
                            }
                        ]
                    },
                    {
                        "featureType": "road.arterial",
                        "elementType": "geometry",
                        "stylers": [
                            {
                                "color": "#fdfcf8"
                            }
                        ]
                    },
                    {
                        "featureType": "road.highway",
                        "elementType": "geometry",
                        "stylers": [
                            {
                                "color": "#f8c967"
                            }
                        ]
                    },
                    {
                        "featureType": "road.highway",
                        "elementType": "geometry.stroke",
                        "stylers": [
                            {
                                "color": "#e9bc62"
                            }
                        ]
                    },
                    {
                        "featureType": "road.highway.controlled_access",
                        "elementType": "geometry",
                        "stylers": [
                            {
                                "color": "#e98d58"
                            }
                        ]
                    },
                    {
                        "featureType": "road.highway.controlled_access",
                        "elementType": "geometry.stroke",
                        "stylers": [
                            {
                                "color": "#db8555"
                            }
                        ]
                    },
                    {
                        "featureType": "road.local",
                        "elementType": "labels.text.fill",
                        "stylers": [
                            {
                                "color": "#806b63"
                            }
                        ]
                    },
                    {
                        "featureType": "transit.line",
                        "elementType": "geometry",
                        "stylers": [
                            {
                                "color": "#dfd2ae"
                            }
                        ]
                    },
                    {
                        "featureType": "transit.line",
                        "elementType": "labels.text.fill",
                        "stylers": [
                            {
                                "color": "#8f7d77"
                            }
                        ]
                    },
                    {
                        "featureType": "transit.line",
                        "elementType": "labels.text.stroke",
                        "stylers": [
                            {
                                "color": "#ebe3cd"
                            }
                        ]
                    },
                    {
                        "featureType": "transit.station",
                        "elementType": "geometry",
                        "stylers": [
                            {
                                "color": "#dfd2ae"
                            }
                        ]
                    },
                    {
                        "featureType": "water",
                        "elementType": "geometry.fill",
                        "stylers": [
                            {
                                "color": "#b9d3c2"
                            }
                        ]
                    },
                    {
                        "featureType": "water",
                        "elementType": "labels.text.fill",
                        "stylers": [
                            {
                                "color": "#92998d"
                            }
                        ]
                    }
                ],
                mapTypeId: google.maps.MapTypeId.ROADMAP
            };
            $scope.map = new google.maps.Map(mapCanvas, mapOptions);

            getLocation();

            /*Click on map event*/
            google.maps.event.addListener($scope.map, 'click', clickOnMap);
        };

        setTimeout(function () {
            initializeMap('registerMap');
        }, 500);

        $scope.hide = function () {
            $mdDialog.hide();
        };

        $scope.cancel = function () {
            $mdDialog.cancel();
        };

        $scope.reset = function (form) {
            /* The code below was written according to the answers to our question in stackoverflow:
             * https://stackoverflow.com/questions/40253213/angularjs-v1-5-5-reset-form-not-working-for-invalid-input-fields*/
            switch (form.$name) {
                case 'accountForm':
                    angular.extend($scope.data.account, $scope.original.account);
                    break;
                case 'profileForm':
                    angular.extend($scope.data.profile, $scope.original.profile);
                    break;
                case 'addressForm':
                    angular.extend($scope.data.address, $scope.original.address);
                    removeMarker();
                    break;
            }
            form.$setPristine();
            form.$setUntouched();
        };

        $scope.signup = function () {
            $scope.dataLoading = true;

            var register = {
                username: $scope.data.account.username,
                password: $scope.data.account.password,
                email: $scope.data.account.email,
                host: $scope.data.account.host,
                profile: $scope.data.profile
            };

            if ($scope.data.address !== null)
                register.profile.location = $scope.data.address;

            console.log(JSON.stringify(register, null, 2));

            Session.register(register)
                .success(function (data, status, headers, config) {
                    $scope.dataLoading = false;
                    $mdDialog.hide();
                    toastr.success('Your registration to <strong>RealHouse</strong> was completed successfully, to sign in click at the top right corner...',
                        '<strong>Congratulations!</strong>');
                    if (register.host)
                        toastr.info('Before you are able to list a room, you have to be confirmed by admin!',
                            '<strong>Host confirmation</strong>');
                })
                .error(function () {
                    $scope.dataLoading = false;
                });
        };
    }
})();
