(function() {
    'use strict';

    angular.module('app').factory('subscriptionsService', subscriptionsService);

    subscriptionsService.$inject = ['$http', '$q', 'ngApiSettings', 'ngLocalStorageKeys'];

    function subscriptionsService($http, $q, ngApiSettings, ngLocalStorageKeys) {

        function getList() {
            var deferred = $q.defer();

            var url = Utils.buildApiUrl(ngApiSettings.apiUri, "/subscriptions.json?phone={phone}&key={key}",
                { phone: ngAuthSettings.phone, key: ngAuthSettings.key });

            $http.get(url).success(function (response) {
                deferred.resolve(response);
            }).error(function (err, status) {
                deferred.reject(status);
            });
        }

        function subscribe(showId, episodeId, subtype) {
            var deferred = $q.defer();

            var url = !episodeId
                ? Utils.buildApiUrl(ngApiSettings.apiUri, "/subscriptions/subscribe.json?phone={phone}&key={key}&show_id={showId}&subtype={subtype}",
                { phone: ngLocalStorageKeys.phone, key: ngLocalStorageKeys.key, showId: showId, subtype: subtype })
                : Utils.buildApiUrl(ngApiSettings.apiUri, "/subscriptions/subscribe.json?phone={phone}&key={key}&show_id={showId}&episode_id={episodeId}&subtype={subtype}",
                { phone: ngLocalStorageKeys.phone, key: ngLocalStorageKeys.key, showId: showId, subtype: subtype, episodeId: episodeId });

            $http.get(url).success(function (response) {
                deferred.resolve(response);
            }).error(function (err, status) {
                deferred.reject(status);
            });
        };

        function unsubscribe(subscriptionId, showId) {
            var deferred = $q.defer();

            var url = Utils.buildApiUrl(ngApiSettings.apiUri, "/subscriptions/unsubscribe.json?phone={phone}&key={key}&subscription_id={subscriptionId}&show_id={showId}",
            { phone: ngLocalStorageKeys.phone, key: ngLocalStorageKeys.key, subscriptionId: subscriptionId, showId: showId });

            $http.get(url).success(function (response) {
                if (response.error) {
                    deferred.reject(response);
                } else {
                    deferred.resolve(response);
                }
            }).error(function (err, status) {
                deferred.reject(status);
            });
        }

        var service = {
            getList: getList,
            subscribe: subscribe,
            unsubscribe: unsubscribe
        };

        return service;
    }
})();