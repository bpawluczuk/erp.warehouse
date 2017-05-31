/**
 * Created by Borys on 2017-05-21.
 */

home.controller('releaseListCtrl', ['$scope', '$routeParams', 'ReleaseService', function ($scope, $routeParams, ReleaseService) {

    $scope.name = 'releaseListCtrl';
    $scope.params = $routeParams;

    $scope.releaseList = [];
    $scope.page = 1;

    if ($routeParams.search) {
        $scope.index = $routeParams.search;
        ReleaseService.pageFinder($scope.index).then(
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
        fetchAll();
    }

    fetchAll();

    function fetchAll() {
        ReleaseService.listRelease($scope.page).then(
            function (response) {
                $scope.releaseList = response;
            },
            function (errResponse) {}
        )
    }

}])

home.controller('releaseCreateCtrl', ['$scope', '$filter', '$routeParams', 'ReleaseService', function ($scope, $filter, $routeParams, ReleaseService) {

    $scope.name = 'releaseCreateCtrl';
    $scope.params = $routeParams;

    $scope.releaseLines = [];
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
        ReleaseService.listProducts($scope.page).then(
            function (response) {
                $scope.productList = response;
            },
            function (errResponse) {
            }
        )
    }

    function submit() {
        ReleaseService.createRelease($scope.releaseLines);
    }

    $scope.checkAtLeast = function () {
        return !$(":checkbox").is(':checked');
    }

    $scope.$watch('bid', function (val) {
        $scope.result = $filter('currency')(val);
    }, true);

}])


home.factory('ReleaseService', ['$http', '$q', '$location', function ($http, $q, $location) {

    var factory = {
        listRelease: listRelease,
        listProducts: listProducts,
        createRelease: createRelease,
        pageFinder: pageFinder
    };

    return factory;

    function listRelease(page) {
        var deferred = $q.defer();
        $http.get(REST_SERVICE_URI_RELEASE + "/list/" + page)
            .then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function (errResponse) {
                    console.error('[Release] Error release list');
                    deferred.reject(errResponse);
                }
            )
        return deferred.promise;
    }

    function listProducts(page) {
        var deferred = $q.defer();
        $http.get(REST_SERVICE_URI_DELIVERY_LINE + "/list/" + page)
            .then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function (errResponse) {
                    console.error('[Delivery Line] Error release list');
                    deferred.reject(errResponse);
                }
            )
        return deferred.promise;
    }

    function convertReleaseLinesItemsToReleaseDto(releaseLines) {

        var ReleaseDto = new Object();
        ReleaseDto.releaseDescription = releaseLines.releaseDescription;
        ReleaseDto.releaseLineDtoList = new Array();
        releaseLines.forEach(function (item) {
            if (item.checked) {
                ReleaseDto.releaseLineDtoList.push(item);
            }
        });
        console.log('[Release] Lines, convert to Json: ', JSON.stringify(ReleaseDto));
        return JSON.stringify(ReleaseDto);
    }

    function createRelease(releaseLines) {
        var deferred = $q.defer();
        $http.post(REST_SERVICE_URI_RELEASE + "/add", convertReleaseLinesItemsToReleaseDto(releaseLines))
            .then(
                function (response) {
                    deferred.resolve(response.data);
                    console.log('[Release] Created release');
                    $location.path("/warehouse/release");
                },
                function (errResponse) {
                    console.error('[Release] Error while creating Release');
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }


    function pageFinder(entityId) {
        var deferred = $q.defer();
        $http.get(REST_SERVICE_URI_RELEASE + "/page/" + entityId)
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

