(function () {
    'use strict';
    angular.module('BlurAdmin.theme')
        .factory('RoomEndPoint', RoomEndPoint);

    /** @ngInject */
    function RoomEndPoint($rootScope, $http) {
        return {
            getRooms: function (params) {
                return $http({url: rootUrl + '/rooms/', method: "GET", params: params});
            },
            getRoom: function (id) {
                return $http({url: rootUrl + '/rooms/' + id + '/', method: "GET"});
            },
            getRoomsByOwner: function (owner) {
                return this.getRooms({owner: owner});
            },
            getRoomPicture: function (id, picture, size) {
                return $http({
                    cache: true,
                    url: rootUrl + '/rooms/' + id + '/picture/' + picture + '/',
                    method: "GET",
                    params: {size: size ? size : "picture"}
                });
            },
            insertRoomPictures: function (id, pictures) {
                return $http({
                    url: rootUrl + '/rooms/' + id + '/picture/',
                    method: "POST",
                    data: pictures
                });
            },
            createRoom: function (room) {
                return $http({
                    url: rootUrl + '/rooms/',
                    method: "POST",
                    data: room
                });
            },
            bookRoom: function (id, username, reservation) {
                return $http({
                    url: rootUrl + '/rooms/' + id + '/book/' + username,
                    method: "POST",
                    data: reservation
                });
            },
            updateRoom: function (owner, room) {
                return $http({
                    url: rootUrl + '/rooms/' + owner,
                    method: "PUT",
                    data: room
                });
            },
            deleteRoom: function (owner) {
                return $http({
                    url: rootUrl + '/rooms/' + owner,
                    method: "DELETE"
                });
            }
        }
    }
})();
