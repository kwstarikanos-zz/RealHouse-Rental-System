(function () {
    'use strict';

    angular.module('BlurAdmin.pages.residences')
        .controller('residencesCtrl', residencesCtrl);

    /** @ngInject */
    function residencesCtrl($scope, $compile, $mdDialog, $rootScope, $stateParams, $state, promise, PagerService) {
        $scope.prev_infowindow = false;
        $scope.rooms = promise.data.rooms;
        $scope.count = promise.data.count;
        var minPrice = promise.data.minPrice;
        var maxPrice = promise.data.maxPrice;
        $scope.size = $rootScope.size = $stateParams.size || 8;
        $scope.types = ['All', 'Full house', 'Public room', 'Private room'];
        $scope.type = $rootScope.type = $stateParams.type || 0;
        $scope.beds = $rootScope.beds = $stateParams.beds || 1;

        //Slider config with custom display function
        $scope.slider_translate = {
            minValue: $rootScope.min_price = ($stateParams.min_price === null || $stateParams.min_price <= minPrice) ? minPrice : $stateParams.min_price,
            maxValue: $rootScope.max_price = ($stateParams.max_price === null || $stateParams.max_price >= maxPrice) ? maxPrice : $stateParams.max_price,
            options: {
                onEnd: function (id) {
                    $stateParams.min_price = $rootScope.min_price = $scope.slider_translate.minValue;
                    $stateParams.max_price = $rootScope.max_price = $scope.slider_translate.maxValue;
                    $stateParams.start = 0;
                    $state.go($state.current, $stateParams, {reload: true, inherit: false, notify: true});
                },
                ceil: maxPrice,
                floor: minPrice,
                hideLimitLabels: true,
                readOnly: false,
                disabled: false,

                translate: function (value) {
                    return '€' + value;
                }
            }
        };

        /*Pagination*/
        $scope.pager = {};
        $scope.setPage = setPage;
        $scope.pager = PagerService.GetPager($scope.count, ($stateParams.start / $scope.size) + 1, $scope.size);

        $scope.goToRoom = function (id) {
            $state.go('residence', {id: id}, {reload: true, inherit: false});
        };

        $scope.changeSize = function (size) {
            $stateParams.start = 0;
            $stateParams.size = size;
            $state.go($state.current, $stateParams, {reload: true, inherit: false, notify: true});
        };

        $scope.changeType = function (type) {
            $stateParams.start = 0;
            $stateParams.type = type;
            $state.go($state.current, $stateParams, {reload: true, inherit: false, notify: true});
        };

        $scope.changeBeds = function (beds) {
            $stateParams.start = 0;
            $stateParams.beds = beds;
            $state.go($state.current, $stateParams, {reload: true, inherit: false, notify: true});
        };

        $scope.changeMinPrice = function (min_price) {
            $stateParams.start = 0;
            $stateParams.min_price = min_price;
            $state.go($state.current, $stateParams, {reload: true, inherit: false, notify: true});
        };

        $scope.changeMaxPrice = function (max_price) {
            $stateParams.start = 0;
            $stateParams.max_price = max_price;
            $state.go($state.current, $stateParams, {reload: true, inherit: false, notify: true});
        };

        function setPage(page) {
            $stateParams.start = page * $scope.size - $scope.size;
            $state.go($state.current, $stateParams, {reload: true, inherit: false, notify: true});
            if (page < 1 || page > $scope.pager.totalPages)
                return;
        }

        $scope.openInfoWindow = function (room) {
            if (!$scope.prev_infowindow || $scope.prev_infowindow !== room.infowindow) {
                if ($scope.prev_infowindow)
                    $scope.prev_infowindow.close();
                $scope.prev_infowindow = room.infowindow;
                room.infowindow.open(map, room.marker);
            }
        };

        /*Google Maps*/
        try {
            var mapCanvas, mapOptions, map = false, marker;
            var bounds = new google.maps.LatLngBounds();
            $(document).ready(function () {
                $("#rooms-map").height($("#rooms-grid").height());
                /*map.event.trigger(map, 'resize');*/
                /* $(window).trigger('resize');*/

                mapCanvas = document.getElementById('rooms-map');
                mapOptions = {
                    //zoom: 14,
                    /*
                     * Google Maps Styles:
                     * https://mapstyle.withgoogle.com
                     */
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
                map = new google.maps.Map(mapCanvas, mapOptions);

                /*Create markers.*/
                var i = 0;
                $scope.rooms.forEach(function (room) {
                    room.marker = new google.maps.Marker({
                        position: new google.maps.LatLng(room.location.latitude, room.location.longitude),
                        title: room.title,
                        animation: google.maps.Animation.DROP,
                        icon: {
                            full_house: {
                                icon: iconBase + 'dog-house-32px.png'
                            },
                            private_room: {
                                icon: iconBase + 'rooms/room/key-32px.png'
                            },
                            public_room: {
                                icon: iconBase + 'rooms/public_room/sleepy-32px.png'
                            }
                        }[room.type].icon,
                        map: map,
                        zoom: 14
                    });

                    bounds.extend(room.marker.getPosition());

                    room.marker.setMap(map);

                    room.infowindow = new google.maps.InfoWindow({});

                    var x =
                        '<div>' +
                        '<h4>{{rooms[' + i + '].title}}</h4>' +
                        '<slider size="thumbnail" images="rooms[' + i + '].pictures" controls="false"></slider>' +
                        '</div>';

                    $scope.c_title = room.title;

                    $scope.c_pictures = room.pictures;

                    var el = $compile(x);

                    var content = el($scope);

                    //$scope.$evalAsync(function () {
                    $scope.$apply();
                    room.infowindow.setContent(content[0]);
                    //room.infowindow.open(map, room.marker);
                    //});

                    room.marker.addListener('click', function () {
                        $scope.openInfoWindow(room);
                    });

                    i++;
                });

                /*Fit map to show all markers*/
                map.fitBounds(bounds);
            });
        }
        catch (err) {
            console.warn(err);
        }
    }

})();
