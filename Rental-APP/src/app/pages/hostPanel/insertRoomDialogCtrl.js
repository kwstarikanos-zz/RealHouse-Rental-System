(function () {
    'use strict';

    angular.module('BlurAdmin.theme.components')
        .controller('insertRoomDialogCtrl', insertRoomDialogCtrl);

    /** @ngInject */
    function insertRoomDialogCtrl($scope, $q, $rootScope, $http, Session, focus, $mdDialog, geocoder, toastr, RoomEndPoint, blobToBase64) {

        $scope.files = [];

        $scope.room = {
            owner: Session.getUsername(),
            beds: null,
            bedrooms: null,
            bathrooms: null,
            max_people: null,
            overnight_price: null,
            extra_person_price: null,
            min_overnights: null,
            square_meters: null,
            title: null,
            description: null,
            transport: null,
            neighborhood: null,
            house_rules: null,
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
            },
            type: {
                id: null,
                type: null
            },
            transports: [],
            rules: [],
            amenities: [],
            pictures: []
        };

        $scope.data = angular.copy($scope.room);

        $scope.roomTypes = [
            {
                id: 1,
                type: "full_house",
                name: "Full House"
            },
            {
                id: 2,
                type: "public_room",
                name: "Public Room"
            },
            {
                id: 3,
                type: "private_room",
                name: "Private Room"
            }
        ];

        $scope.setTransports = [
            {
                id: 1,
                transport: "Taxi"
            },
            {
                id: 2,
                transport: "Metro"
            },
            {
                id: 3,
                transport: "Bus"
            },
            {
                id: 4,
                transport: "Boat"
            },
            {
                id: 5,
                transport: "Airplane"
            }
        ];

        $scope.setRules = [
            {
                id: 1,
                rule: "No pets"
            },
            {
                id: 2,
                rule: "No party"
            },
            {
                id: 3,
                rule: "No smoking"
            }

        ];

        $scope.setAmenities = [
            {
                "id": 2,
                "amenity": "heating"
            },
            {
                "id": 3,
                "amenity": "pool"
            },
            {
                "id": 1,
                "amenity": "wifi"
            }
        ];

        $scope.selectedTransport = [];

        $scope.selectedRule = [];

        $scope.selectedAmenity = [];


        $scope.toggleTransport = function (item1, list1) {
            var idx = list1.indexOf(item1);
            if (idx > -1) {
                //$scope.data.transport.splice(idx);
                list1.splice(idx, 1);
                $scope.data.transports.splice(idx, 1);
            }
            else {
                var transport = {
                    id: item1.id,
                    transport: item1.transport
                };
                $scope.data.transports.push(transport);
                list1.push(item1);
            }
        };

        $scope.existsTransport = function (item1, list1) {
            return list1.indexOf(item1) > -1;
        };


        $scope.toggleRule = function (item1, list1) {
            var idx = list1.indexOf(item1);
            if (idx > -1) {
                //$scope.data.rules.splice(idx);
                list1.splice(idx, 1);
                $scope.data.rules.splice(idx, 1);
            }
            else {
                var rule = {
                    id: item1.id,
                    rule: item1.rule
                };
                $scope.data.rules.push(rule);
                list1.push(item1);
            }
        };

        $scope.existsRule = function (item1, list1) {
            return list1.indexOf(item1) > -1;
        };

        $scope.toggleAmenity = function (item, list2) {
            var idx = list2.indexOf(item);
            if (idx > -1) {
                list2.splice(idx, 1);
                $scope.data.amenities.splice(idx, 1);
            }
            else {
                var amenity = {
                    id: item.id,
                    amenity: item.amenity
                };
                $scope.data.amenities.push(amenity);
                list2.push(item);
            }
        };

        $scope.existsAmenity = function (item, list2) {
            return list2.indexOf(item) > -1;
        };

        $scope.$watch("selectedType", function (newValue) {
            if (angular.isDefined(newValue)) {
                $scope.data.type.id = newValue.id;
                $scope.data.type.type = newValue.type;
            }
        });

        $scope.$watch("data.square_meters", function (newValue) {
            if (angular.isDefined(newValue)) {

            }
        });

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
                    $scope.roomLocationForm.$pristine = false;
                    $scope.roomLocationForm.$dirty = true;
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
                case 'basicRoomForm':
                    angular.extend($scope.data, $scope.room);
                    break;
                case 'profileForm':
                    angular.extend($scope.data.profile, $scope.original.profile);
                    break;
                case 'addressForm':
                    angular.extend($scope.data.address, $scope.original.address);
                    removeMarker();
                    break;
                case 'extraRoomForm':
                    $scope.selectedRule = [];
                    $scope.selectedAmenity = [];
                    $scope.data.rules = [];
                    $scope.data.amenities = [];
                    break;
            }
            form.$setPristine();
            form.$setUntouched();
        };

        $scope.hostRoom = function () {
            $scope.dataLoading = true;

            // list of all promises
            var promises = [];

            angular.forEach($scope.pictures, function (value, key) {
                var deferred = $q.defer();

                blobToBase64(value.lfFile)
                    .then(function (base64) {
                        /*$scope.files.push();*/
                        deferred.resolve({
                            filename: value.lfFile.name,
                            filetype: value.lfFile.type,
                            filesize: value.lfFile.size,
                            base64: base64
                        });
                    })

                // add to the list of promises
                promises.push(deferred.promise);
            });

            // execute all the promises and do something with the results
            $q.all(promises).then(
                function (pictures) {
                    var roomInsert = {
                        owner: Session.getUsername(),
                        beds: $scope.data.beds,
                        bedrooms: $scope.data.bedrooms,
                        bathrooms: $scope.data.bathrooms,
                        max_people: $scope.data.max_people,
                        overnight_price: $scope.data.overnight_price,
                        exta_person_price: $scope.data.extra_person_price,
                        min_overnights: $scope.data.min_overnights,
                        square_meters: $scope.data.square_meters,
                        title: $scope.data.title,
                        description: $scope.data.description,
                        transport: $scope.data.transport,
                        neightborhood: $scope.data.neighborhood,
                        house_rules: $scope.data.house_rules,
                        type: {
                            id: $scope.data.type.id,
                            type: $scope.data.type.type
                        },
                        transports: $scope.data.transports,
                        rules: $scope.data.rules,
                        amenities: $scope.data.amenities,
                        pictures: pictures
                    };

                    if ($scope.data.address !== null)
                        roomInsert.location = $scope.data.address;

                    console.log(JSON.stringify(roomInsert));

                    RoomEndPoint.createRoom(roomInsert)
                        .success(function (data, status, headers, config) {
                            $scope.dataLoading = false;
                            $mdDialog.hide();
                            toastr.success('Your room insert was successful!');
                        })
                        .error(function () {
                            $scope.dataLoading = false;
                        });
                },
                function (rejection) {
                    $scope.dataLoading = false;
                    toastr.error(JSON.stringify(rejection), '<strong>Convert Pictures Error</strong>');
                }
            );
        };
    }
})();
