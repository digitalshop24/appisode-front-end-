'use strict';

angular.module('app').factory('pushNotificationsService', [
    '$http', '$q', '$cordovaPush', '$cordovaDialogs', '$cordovaMedia', 'localStorageService', 'deviceService', 'ngApiSettings', 'ngLocalStorageKeys',
    function ($http, $q, $cordovaPush, $cordovaDialogs, $cordovaMedia, localStorageService, deviceService, ngApiSettings, ngLocalStorageKeys) {
        var pushNotificationsServiceFactory = {};

        var register = function () {
            var config = null;

            if (ionic.Platform.isAndroid()) {
                config = {
                    //"senderID": "552787023225" //mine
                    "senderID": "890062303664" //appisode
                };
            } else if (ionic.Platform.isIOS()) {
                config = {
                    "badge": "true",
                    "sound": "true",
                    "alert": "true"
                }
            }

            $cordovaPush.register(config).then(function (result) {
                console.log("Register success " + result);
                if (ionic.Platform.isIOS()) {
                    deviceService.saveDeviceToken(result).then(function () {
                        console.log("Token stored, device is successfully subscribed to receive push notifications.");
                    }, function (data, status) {
                        console.log("Error storing device token.");
                    });
                }
            }, function (err) {
                console.log("Register error " + err);
            });
        };

        var testPush = function(message) {
            var deferred = $q.defer();

            var token = localStorageService.get(ngLocalStorageKeys.deviceToken);

            var url = Utils.buildApiUrl(ngApiSettings.apiUri, "/users/test_push.json?token={token}&message={message}",
                { token: token, message: message });

            $http.get(url).success(function (response) {
                deferred.resolve(response);
            }).error(function (err, status) {
                deferred.reject(status);
            });

            return deferred.promise;
        };

        var unregister = function () {
            console.log("Unregister called");
        };

        pushNotificationsServiceFactory.register = register;
        pushNotificationsServiceFactory.unregister = unregister;
        pushNotificationsServiceFactory.testPush = testPush;

        return pushNotificationsServiceFactory;
    }
]);