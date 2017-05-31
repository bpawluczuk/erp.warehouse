/**
 * Created by Borys on 2017-05-21.
 */

home.controller('deliveryListCtrl', ['$scope', '$routeParams', 'DeliveryService', function ($scope, $routeParams, DeliveryService) {

    $scope.name = 'deliveryListCtrl';
    $scope.params = $routeParams;

    $scope.deliveryList = [];
    $scope.page = 1;

    if ($routeParams.search) {
        $scope.index = $routeParams.search;
        DeliveryService.pageFinder($scope.index).then(
            function (response) {
                $scope.page = response.page;
            }
        )
    }

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
        fetchAllDelivery();
    }

    fetchAllDelivery();

    function fetchAllDelivery() {
        DeliveryService.listDelivery($scope.page).then(
            function (response) {
                $scope.deliveryList = response;
            },
            function (errResponse) {
            }
        )
    }

}])

home.controller('deliveryCreateCtrl', ['$scope', '$filter', '$routeParams', 'DeliveryService', function ($scope, $filter, $routeParams, DeliveryService) {

    $scope.name = 'deliveryCreateCtrl';
    $scope.params = $routeParams;

    $scope.deliveryLines = [];
    $scope.productList = [];
    $scope.options = [
        {
            name: 'EUR',
            value: 'EUR'
        },
        {
            name: 'PLN',
            value: 'PLN'
        }
    ];

    $scope.submit = submit;

    $scope.page = 1;

    $scope.paginate = function (page) {
        $scope.page = page;
        fetchAllProducts();
    }

    fetchAllProducts();

    function fetchAllProducts() {
        DeliveryService.listProducts($scope.page).then(
            function (response) {
                $scope.productList = response;
            },
            function (errResponse) {
            }
        )
    }

    function submit() {
        DeliveryService.createDelivery($scope.deliveryLines);
    }

    $scope.checkAtLeast = function () {
        return !$(":checkbox").is(':checked');
    }

    $scope.$watch('bid', function (val) {
        $scope.result = $filter('currency')(val);
    }, true);

}])

home.factory('DeliveryService', ['$http', '$q', '$location', function ($http, $q, $location) {

    var factory = {
        listDelivery: listDelivery,
        listProducts: listProducts,
        createDelivery: createDelivery,
        pageFinder: pageFinder
    };

    return factory;

    function listDelivery(page) {
        var deferred = $q.defer();
        $http.get(REST_SERVICE_URI_DELIVERY + "/list/" + page)
            .then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function (errResponse) {
                    console.error('[Delivery] Error delivery list');
                    deferred.reject(errResponse);
                }
            )
        return deferred.promise;
    }

    function listProducts(page) {
        var deferred = $q.defer();
        $http.get(REST_SERVICE_URI_PRODUCTS + "/list/" + page)
            .then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function (errResponse) {
                    console.error('[Delivery] Error delivery list');
                    deferred.reject(errResponse);
                }
            )
        return deferred.promise;
    }

    function convertDeliveryLinesItemsToDeliveryDto(deliveryLines) {

        var DeliveryDto = new Object();
        DeliveryDto.deliveryDescription = deliveryLines.deliveryDescription;
        DeliveryDto.deliveryLineDtoList = new Array();
        deliveryLines.forEach(function (item) {
            if (item.checked) {
                DeliveryDto.deliveryLineDtoList.push(item);
            }
        });
        console.log('[Delivery] Lines, convert to Json: ', JSON.stringify(DeliveryDto));
        return JSON.stringify(DeliveryDto);
    }

    function createDelivery(deliveryLines) {
        var deferred = $q.defer();
        $http.post(REST_SERVICE_URI_DELIVERY + "/add", convertDeliveryLinesItemsToDeliveryDto(deliveryLines))
            .then(
                function (response) {
                    deferred.resolve(response.data);
                    console.log('[Delivery] Created delivery');
                    $location.path("/warehouse/delivery");
                },
                function (errResponse) {
                    console.error('[Delivery] Error while creating Delivery');
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }

    function pageFinder(entityId) {
        var deferred = $q.defer();
        $http.get(REST_SERVICE_URI_DELIVERY + "/page/" + entityId)
            .then(
                function (response) {
                    console.log(response.data)
                    deferred.resolve(response.data);
                },
                function (errResponse) {
                    console.error('[Delivery] Error get page number');
                    deferred.reject(errResponse);
                }
            )
        return deferred.promise;
    }
}])
