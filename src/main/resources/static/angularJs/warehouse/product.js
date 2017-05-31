/**
 * Created by Borys on 2017-05-21.
 */

home.controller('productCtrl', ['$scope', '$routeParams', 'ProductService', function ($scope, $routeParams, ProductService) {

    $scope.name = 'productCtrl';
    $scope.params = $routeParams;

    $scope.product = {entityId: null, name: '', type: 'EQUIPMENT'};
    $scope.productList = [];
    $scope.options = [
        {
            name: 'EQUIPMENT',
            value: 'EQUIPMENT'
        },
        {
            name: 'FOOD',
            value: 'FOOD'
        }
    ];
    $scope.submit = submit;
    $scope.edit = edit;
    $scope.remove = remove;
    $scope.reset = reset;

    $scope.page = 1;

    $scope.paginate = function (page) {
        $scope.page = page;
        fetchAll();
    }

    fetchAll();

    function fetchAll() {
        ProductService.listProducts($scope.page).then(
            function (response) {
                $scope.productList = response;
            },
            function (errResponse) {}
        )
    }

    function createProduct(product) {
        ProductService.createProduct(product).then(
            function (response) {
                fetchAll();
            },
            function (errResponse) {}
        );
    }

    function updateProduct(product, id) {
        ProductService.updateProduct(product, id).then(
            function (response) {
                fetchAll();
            },
            function (errResponse) {}
        );
    }

    function edit(id) {
        for (var i = 0; i < $scope.productList.items.length; i++) {
            if ($scope.productList.items[i].entityId === id) {
                $scope.product = angular.copy($scope.productList.items[i]);
                console.log('[Product] Edit: ', $scope.product);
                break;
            }
        }
    }

    function submit() {
        if ($scope.product.entityId === null) {
            console.log('[Product] Saving New: ', $scope.product);
            createProduct($scope.product);
        } else {
            console.log('[Product] Update: ', $scope.product);
            updateProduct($scope.product, $scope.product.entityId)
        }
        reset();
    }

    function deleteProduct(id) {
        ProductService.deleteProduct(id).then(
            function (response) {
                console.log('[Product] Deleted entityId: '+id);
                fetchAll();
            },
            function (errResponse) {}
        );
    }

    function remove(id) {
        if ($scope.product.id === id) {
            reset();
        }
        deleteProduct(id);
    }

    function reset() {
        $scope.product = {entityId: null, name: '', type: 'EQUIPMENT'};
        $scope.productEditForm.$setPristine();
    }
}])

home.factory('ProductService', ['$http', '$q', '$location', function ($http, $q, $location) {

    var factory = {
        listProducts: listProducts,
        createProduct: createProduct,
        updateProduct: updateProduct,
        deleteProduct: deleteProduct
    };
    return factory;

    function listProducts(page) {
        var deferred = $q.defer();
        $http.get(REST_SERVICE_URI_PRODUCTS + "/list/" + page)
            .then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function (errResponse) {
                    console.error('[Product] Error product list');
                    deferred.reject(errResponse);
                }
            )
        return deferred.promise;
    }

    function createProduct(product) {
        var deferred = $q.defer();
        $http.post(REST_SERVICE_URI_PRODUCTS + "/add", product)
            .then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function (errResponse) {
                    console.error('[Product] Error product add');
                    deferred.reject(errResponse);
                }
            )
        return deferred.promise;
    }

    function updateProduct(product, id) {
        var deferred = $q.defer();
        $http.put(REST_SERVICE_URI_PRODUCTS + "/edit/" + id, product)
            .then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function (errResponse) {
                    console.error('[Product] Error product edit');
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }

    function deleteProduct(id) {
        var deferred = $q.defer();
        $http.delete(REST_SERVICE_URI_PRODUCTS + "/delete/" + id)
            .then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function (errResponse) {
                    console.error('[Product] Error product delete');
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }

}])

home.directive('ngConfirmMessage', [function () {
    return {
        restrict: 'A',
        link: function (scope, element, attrs) {
            element.on('click', function (e) {
                var message = attrs.ngConfirmMessage || "Are you sure ?";
                if (!confirm(message)) {
                    e.stopImmediatePropagation();
                }
            });
        }
    }
}]);
