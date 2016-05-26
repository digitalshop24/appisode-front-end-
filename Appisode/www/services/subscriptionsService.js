(function() {
    'use strict';

    angular.module('app').factory('subscriptionsService', subscriptionsService);

    subscriptionsService.$inject = ['$http', '$q', 'localStorageService', 'ngApiSettings', 'ngLocalStorageKeys'];

    function subscriptionsService($http, $q, localStorageService, ngApiSettings, ngLocalStorageKeys) {

        function getList(page, perPage) {
            var deferred = $q.defer();

            var url = Utils.buildApiUrl(ngApiSettings.apiUri, "/subscriptions.json?page={page}&perPage={perPage}",
            { page: page, perPage: perPage });

            $http.get(url).success(function (response) {
                deferred.resolve(response);
            }).error(function (err, status) {
                deferred.reject(status);
            });

            return deferred.promise;
        }

        function subscribe(showId, episodeId, subtype) {
            var deferred = $q.defer();

            var url = !episodeId
                ? Utils.buildApiUrl(ngApiSettings.apiUri, "/subscriptions/subscribe.json?show_id={showId}&subtype={subtype}",
                { showId: showId, subtype: subtype })
                : Utils.buildApiUrl(ngApiSettings.apiUri, "/subscriptions/subscribe.json?show_id={showId}&episode_id={episodeId}&subtype={subtype}",
                { showId: showId, subtype: subtype, episodeId: episodeId });

            $http.post(url).success(function (response) {
                deferred.resolve(response);
            }).error(function (err, status) {
                deferred.reject(status);
            });

            return deferred.promise;
        };

        function unsubscribe(subscriptionId, showId) {
            var deferred = $q.defer();

            var url = Utils.buildApiUrl(ngApiSettings.apiUri, "/subscriptions/unsubscribe.json?phone={phone}&key={key}&subscription_id={subscriptionId}&show_id={showId}",
            { phone: localStorageService.get(ngLocalStorageKeys.phone), key: localStorageService.get(ngLocalStorageKeys.key), subscriptionId: subscriptionId, showId: showId });

            $http.delete(url).success(function (response) {
                if (response.error) {
                    deferred.reject(response);
                } else {
                    deferred.resolve(response);
                }
            }).error(function (err, status) {
                deferred.reject(status);
            });

            return deferred.promise;
        }

        var service = {
            getList: getList,
            subscribe: subscribe,
            unsubscribe: unsubscribe
        };

        return service;
    }
})();