/**
 * Created by k.danovsky on 13.05.2016.
 */

(function () {
    'use strict';

    var basic = {
        default: '#ffffff',
        defaultText: '#666666',
        border: '#dddddd',
        borderDark: '#aaaaaa',
    };

    // main functional color scheme
    var colorScheme = {
        primary: '#209e91',
        info: '#2dacd1',
        success: '#90b900',
        warning: '#dfb81c',
        danger: '#e85656',
    };


    // main functional color scheme
    var graphs = {

        totalTraffic: {
            lineColor1: '#d8dd56',
            fillColors1: '#b4b943',
            lineColor2: '#76e0d6',
            fillColors2: '#76e0d6',
        },

        users: {
            usersLineColor: '#aaaf49',
            userFillColor: '#d8dd56',
            usersLineColorAlpha: 1,
            usersFillColorAlpha: 0.9,
            connectionsLineColor: '#5aafa5',
            connectionsFillColor: '#76e0d6',
            connectionsLineColorAlpha: 1,
            connectionsFillColorAlpha: 0.7,
        },

        networkUsage: {
            uploadLineColor: '#d8dd56',
            uploadFillColors: '#b4b943',
            downloadLineColor: '#76e0d6',
            downloadFillColors: '#76e0d6',
            users: '#e85656',
            speed: '#dfb81c'
        },


        throughput: {
            lineColor: '#28b3a7',
            fillColors: '#209e91',
            fillAlphas: 0.3,
        },

        devices: [
            'rgba(232,86,86, 0.75)',
            'rgba(242,220,126, 0.85)',
            'rgba(142,242,125, 0.85)',
            'rgba(16,196,181, 0.75)',
            'rgba(146, 114, 255, 0.55)'
        ]
    };


    // dashboard colors for charts
    var dashboardColors = {
        blueStone: '#005562',
        surfieGreen: '#0e8174',
        silverTree: '#6eba8c',
        gossip: '#b9f2a1',
        white: '#10c4b5',
        wtest: '#e85656'
    };


    angular.module('BlurAdmin.theme')
        .provider('baConfig', configProvider);

    /** @ngInject */
    function configProvider(colorHelper) {
        var conf = {
            theme: {
                blur: false,
            },
            colors: {
                default: basic.default,
                defaultText: basic.defaultText,
                border: basic.border,
                borderDark: basic.borderDark,
                graphs: graphs,
                primary: colorScheme.primary,
                info: colorScheme.info,
                success: colorScheme.success,
                warning: colorScheme.warning,
                danger: colorScheme.danger,

                primaryLight: colorHelper.tint(colorScheme.primary, 30),
                infoLight: colorHelper.tint(colorScheme.info, 30),
                successLight: colorHelper.tint(colorScheme.success, 30),
                warningLight: colorHelper.tint(colorScheme.warning, 30),
                dangerLight: colorHelper.tint(colorScheme.danger, 30),

                primaryDark: colorHelper.shade(colorScheme.primary, 15),
                infoDark: colorHelper.shade(colorScheme.info, 15),
                successDark: colorHelper.shade(colorScheme.success, 15),
                warningDark: colorHelper.shade(colorScheme.warning, 15),
                dangerDark: colorHelper.shade(colorScheme.danger, 15),

                dashboard: {
                    blueStone: dashboardColors.blueStone,
                    surfieGreen: dashboardColors.surfieGreen,
                    silverTree: dashboardColors.silverTree,
                    gossip: dashboardColors.gossip,
                    white: dashboardColors.white,
                },
            }
        };

        conf.changeTheme = function (theme) {
            angular.merge(conf.theme, theme)
        };

        conf.changeColors = function (colors) {
            angular.merge(conf.colors, colors)
        };

        conf.$get = function () {
            delete conf.$get;
            return conf;
        };
        return conf;
    }
})();
