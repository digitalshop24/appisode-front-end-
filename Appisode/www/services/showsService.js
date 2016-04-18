(function () {
    'use strict';

    angular.module('app').factory('showsService', showsService);

    showsService.$inject = ['$http', '$q', 'ngAuthSettings'];

    function showsService($http, $q, ngAuthSettings) {
        function getList(page, perPage) {
            var deferred = $q.defer();

            var url = page && perPage 
                ? Utils.buildApiUrl(ngAuthSettings.apiServiceBaseUri, "/shows.json?page={page}&per_page={per_page}", { page: page, per_page: perPage })
                : Utils.buildApiUrl(ngAuthSettings.apiServiceBaseUri, "/shows.json", { page: page, per_page: perPage });

            $http.get(url).success(function (response) {
                deferred.resolve(response);
            }).error(function (err, status) {
                deferred.reject(status);
            });

            return deferred.promise;
        };

        var service = {
            getList: getList
        };

        return service;
    }
})();