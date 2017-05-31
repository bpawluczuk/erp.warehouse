/**
 * Created by Borys on 2017-05-21.
 */

home.controller('warehouseCtrl', ['$scope', '$routeParams', '$location', 'WarehouseService', function ($scope, $routeParams, $location, WarehouseService) {

    $scope.name = 'warehouseCtrl';
    $scope.params = $routeParams;

    $scope.availabilityList = [];

    $scope.page = 1;

    $scope.details = function (index) {
        var line = $("[data-line=" + index + "]");
        if (!line.hasClass("hidden")) {
            $scope.index = 0;
        } else {
            $scope.index = index;
        }
    }

    $scope.paginate = function (page) {
        $scope.page = page;
        fetchAll();
    }

    fetchAll();

    function fetchAll() {
        WarehouseService.warehouseAvailability($scope.page).then(
            function (response) {
                $scope.availabilityList = response;
            },
            function (errResponse) {}
        )
    }

    $scope.gotoDelivery = function (id) {
        $location.path('/warehouse/delivery').search("search="+id);
    }

    $scope.gotoRelease = function (id) {
        $location.path('/warehouse/release').search("search="+id);
    }

}])

home.factory('WarehouseService', ['$http', '$q', function ($http, $q) {

    var factory = {
        warehouseAvailability: warehouseAvailability
    };

    return factory;

    function warehouseAvailability(page) {
        var deferred = $q.defer();
        $http.get(REST_SERVICE_URI_AVAILABILITY + "/list/" + page)
            .then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function (errResponse) {
                    console.error('[Warehouse] Error availability list');
                    deferred.reject(errResponse);
                }
            )
        return deferred.promise;
    }
}])

