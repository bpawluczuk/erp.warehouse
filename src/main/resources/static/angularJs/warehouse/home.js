/**
 * Created by Borys on 2017-05-21.
 */

home.controller('homeCtrl', ['$scope', '$route', '$routeParams', '$location', function ($scope, $route, $routeParams, $location) {

    $scope.$route = $route;
    $scope.$location = $location;
    $scope.$routeParams = $routeParams;

    $scope.isCurrentPath = function (path) {
        return $location.path() == path;
    };
}])

