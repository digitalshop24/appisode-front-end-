(function() {
    'use strict';

    angular.module('app').factory('deviceService', deviceService);

    deviceService.$inject = ['$http', '$q', 'localStorageService', 'ngApiSettings', 'ngLocalStorageKeys'];

    function deviceService($http, $q, localStorageService, ngApiSettings, ngLocalStorageKeys) {
        function saveDeviceToken(token) {
            var deferred = $q.defer();

            if (!token) {
                deferred.reject('token is undefined');
                return deferred.promise;
            }

            var url = Utils.buildApiUrl(ngApiSettings.apiUri, "/users/device.json?token={token}", { token: token });

            $http.post(url).success(function (response) {
                console.log('device token saved ' + token);

                localStorageService.set(ngLocalStorageKeys.deviceToken, token);

                deferred.resolve(response);
            }).error(function (err, status) {
                deferred.reject(status);
            });

            return deferred.promise;
        };

        return{
            saveDeviceToken: saveDeviceToken
        }
    };
})();