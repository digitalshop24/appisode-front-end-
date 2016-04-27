(function() {
    'use strict';

    angular.module('app').factory('authService', authService);

    authService.$inject = ['$http', '$q', 'localStorageService', 'ngApiSettings', 'ngLocalStorageKeys'];

    function authService($http, $q, localStorageService, ngApiSettings, ngLocalStorageKeys) {
        function checkAuth(phone, key) {
            var deferred = $q.defer();

            var url = Utils.buildApiUrl(ngApiSettings.apiUri, "/users/check_auth.json?phone={phone}&key={key}", { phone: phone, key: key });

            $http.get(url).success(function (response) {
                deferred.resolve(response);
            }).error(function (err, status) {
                deferred.reject(status);
            });

            return deferred.promise;
        };

        function isAuthorized() {
            return localStorageService.get(ngLocalStorageKeys.authorized);
        };

        function register(phone) {
            var deferred = $q.defer();

            var url = Utils.buildApiUrl(ngApiSettings.apiUri, "/users/register.json?phone={phone}", { phone: phone });

            $http.get(url).success(function (response) {
                deferred.resolve(response);
            }).error(function (err, status) {
                deferred.reject(status);
            });

            return deferred.promise;
        };

        function checkConfirmation(phone, confirmation) {
            var deferred = $q.defer();

            var url = Utils.buildApiUrl(ngApiSettings.apiUri, "/users/check_confirmation.json?phone={phone}&confirmation={confirmation}", { phone: phone, confirmation: confirmation });

            $http.get(url).success(function (response) {
                deferred.resolve(response);
            }).error(function (err, status) {
                deferred.reject(status);
            });

            return deferred.promise;
        };

        return {
            checkAuth: checkAuth,
            isAuthorized: isAuthorized,
            register: register,
            checkConfirmation: checkConfirmation
        };
    };
})();