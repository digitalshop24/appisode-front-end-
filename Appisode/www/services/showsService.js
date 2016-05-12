(function () {
    'use strict';

    angular.module('app').factory('showsService', showsService);

    showsService.$inject = ['$http', '$q', 'ngApiSettings'];

    function showsService($http, $q, ngApiSettings) {

        function getList(page, perPage) {
            var deferred = $q.defer();

            var url = page && perPage 
                ? Utils.buildApiUrl(ngApiSettings.apiUri, "/shows.json?page={page}&per_page={per_page}", { page: page, per_page: perPage })
                : Utils.buildApiUrl(ngApiSettings.apiUri, "/shows.json", { page: page, per_page: perPage });

            $http.get(url).success(function (response) {
                deferred.resolve(response);
            }).error(function (err, status) {
                deferred.reject(status);
            });

            return deferred.promise;
        };

        function searchList(page, perPage, query) {
            var deferred = $q.defer();

            var url = page && perPage
                ? Utils.buildApiUrl(ngApiSettings.apiUri, "/shows/search.json?page={page}&per_page={per_page}&query={query}", { page: page, per_page: perPage, query: query })
                : Utils.buildApiUrl(ngApiSettings.apiUri, "/shows/search.json?query={query}", { query: query });

            $http.get(url).success(function (response) {
                deferred.resolve(response);
            }).error(function (err, status) {
                deferred.reject(status);
            });

            return deferred.promise;
        };

        function popularList(page, perPage) {
            var deferred = $q.defer();

            var url = page && perPage
                ? Utils.buildApiUrl(ngApiSettings.apiUri, "/shows/popular.json?page={page}&perPage={perPage}", { page: page, perPage: perPage })
                : Utils.buildApiUrl(ngApiSettings.apiUri, "/shows/popular.json");

            $http.get(url).success(function (response) {
                deferred.resolve(response);
            }).error(function (err, status) {
                deferred.reject(status);
            });

            return deferred.promise;
        };

        function newestList(page, perPage) {
            var deferred = $q.defer();

            var url = page && perPage
                ? Utils.buildApiUrl(ngApiSettings.apiUri, "/shows/new.json?page={page}&perPage={perPage}", { page: page, perPage: perPage })
                : Utils.buildApiUrl(ngApiSettings.apiUri, "/shows/new.json");

            $http.get(url).success(function (response) {
                deferred.resolve(response);
            }).error(function (err, status) {
                deferred.reject(status);
            });

            return deferred.promise;
        };

        function getShow(id) {
            var deferred = $q.defer();

            var url = Utils.buildApiUrl(ngApiSettings.apiUri, "/shows/{id}.json", {id: id});

            $http.get(url).success(function (response) {
                deferred.resolve(response);
            }).error(function (err, status) {
                deferred.reject(status);
            });

            return deferred.promise;
        };

        var service = {
            getList: getList,
            searchList: searchList,
            popularList: popularList,
            newestList: newestList,
            getShow: getShow
        };

        return service;
    }
})();