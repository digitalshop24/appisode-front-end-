'use strict';

angular.module('app').factory('pushNotificationsService', [
    '$http', '$q', '$cordovaPush', '$cordovaDialogs', '$cordovaMedia', 'localStorageService', 'deviceService', 'ngApiSettings', 'ngLocalStorageKeys',
    function ($http, $q, $cordovaPush, $cordovaDialogs, $cordovaMedia, localStorageService, deviceService, ngApiSettings, ngLocalStorageKeys) {
        var pushNotificationsServiceFactory = {};

        var getList = function() {
            var deferred = $q.defer();

            var url = Utils.buildApiUrl(ngApiSettings.apiUri, "/notifications");

            $http.get(url).success(function (response) {
                deferred.resolve(response);
            }).error(function (err, status) {
                deferred.reject(status);
            });

            return deferred.promise;
        };

        var markAsRead = function (id) {
            var deferred = $q.defer();

            var url = Utils.buildApiUrl(ngApiSettings.apiUri, "/notifications");

            $http.post(url, {notification_id: id}).success(function (response) {
                deferred.resolve(response);
            }).error(function (err, status) {
                deferred.reject(status);
            });

            return deferred.promise;
        };

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

            var url = Utils.buildApiUrl(ngApiSettings.apiUri, "/users/test_push.json?token=APA91bEm9ufj14uLAT7z8ITW6qan3ibkUFBntO4-I8GRpLjzb8rNAl4xnCNcZk5TUa9PO-2PLj0AZhrY65LRWkXNJhgBeeABOW0ebvZRsPOyGAjlrmtWvg3Kd8j8hyE_ahucUrKtZdkN&message={message}",
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
        pushNotificationsServiceFactory.getList = getList;
        pushNotificationsServiceFactory.markAsRead = markAsRead;

        return pushNotificationsServiceFactory;
    }
]);