/**
 * Created by Borys on 2017-05-22.
 */

'use strict';

var URL = '/warehouse';
var REST_SERVICE_URI_AVAILABILITY = URL + '/availability';
var REST_SERVICE_URI_PRODUCTS = URL + '/products';
var REST_SERVICE_URI_RELEASE = URL + '/release';
var REST_SERVICE_URI_DELIVERY = URL + '/delivery';
var REST_SERVICE_URI_DELIVERY_LINE = URL + '/delivery-line';
var REST_SERVICE_URI_EVENTS = URL + '/events';

var home = angular.module('home', ['ngRoute', 'dynamicNumber']);

(function (angular) {

    home.config(['$routeProvider', '$locationProvider', 'dynamicNumberStrategyProvider', function ($routeProvider, $locationProvider, dynamicNumberStrategyProvider) {

        dynamicNumberStrategyProvider.addStrategy('quantity', {
            numInt: 6,
            numFract: 0,
            numSep: '.',
            numPos: true,
            numNeg: false,
            numRound: 'round',
            numThousand: true,
            numThousandSep: ' '
        });

        dynamicNumberStrategyProvider.addStrategy('price', {
            numInt: 6,
            numFract: 2,
            numSep: '.',
            numPos: true,
            numNeg: false,
            numRound: 'round',
            numThousand: true,
            numThousandSep: ' '
        });

        $locationProvider.html5Mode({
            enabled: true
        });

        $routeProvider
            .when('/warehouse', {
                templateUrl: '/avaliability.html',
                controller: 'warehouseCtrl'
            })
            .when('/warehouse/delivery/create', {
                templateUrl: '/delivery-create.html',
                controller: 'deliveryCreateCtrl'
            })
            .when('/warehouse/delivery', {
                templateUrl: '/delivery-list.html',
                controller: 'deliveryListCtrl'
            })
            .when('/warehouse/release/create', {
                templateUrl: '/release-create.html',
                controller: 'releaseCreateCtrl'
            })
            .when('/warehouse/release', {
                templateUrl: '/release-list.html',
                controller: 'releaseListCtrl'
            })
            .when('/warehouse/products', {
                templateUrl: 'products.html',
                controller: 'productCtrl'
            })
            .when('/warehouse/events', {
                templateUrl: '/events.html',
                controller: 'eventListCtrl'
            })
            .otherwise({
                redirectTo: ''
            });
    }]);

})(window.angular);

