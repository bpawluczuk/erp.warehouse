/**
 * Created by Borys on 2017-05-21.
 */

home.controller('eventListCtrl', ['$scope', '$routeParams', 'EventService', function ($scope, $routeParams, EventService) {

    $scope.name = 'eventListCtrl';
    $scope.params = $routeParams;

    $scope.eventList = [];
    $scope.page = 1;

    $scope.paginate = function (page) {
        $scope.page = page;
        fetchAll();
    }

    fetchAll();

    function fetchAll() {
        EventService.listEvents($scope.page).then(
            function (response) {
                $scope.eventList = response;
            },
            function (errResponse) {
            }
        )
    }
}])

home.factory('EventService', ['$http', '$q', function ($http, $q) {

    var factory = {
        listEvents: listEvents
    };

    return factory;

    function listEvents(page) {
        var deferred = $q.defer();
        $http.get(REST_SERVICE_URI_EVENTS + "/list/" + page)
            .then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function (errResponse) {
                    console.error('[Events] Error event list');
                    deferred.reject(errResponse);
                }
            )
        return deferred.promise;
    }

}])