(function() {
    'use strict';

    angular
        .module('app')
        .controller('appController', appController);

    appController.$inject = [
        '$scope', '$cordovaPush', '$cordovaDialogs', '$cordovaMedia', 'Notification', 'deviceService', 'pushNotificationsService'];

    function appController($scope, $cordovaPush, $cordovaDialogs, $cordovaMedia, Notification, deviceService, pushNotificationsService) { 
        $scope.notifications = [];

        $scope.push_img_path = null;
        $scope.push_content = null;

        ionic.Platform.ready(function () {
            pushNotificationsService.register();
        });

        $scope.$on('$cordovaPush:notificationReceived', function (event, notification) {
            console.log(JSON.stringify([notification]));
            if (ionic.Platform.isAndroid()) {
                handleAndroid(notification);
            }
            else if (ionic.Platform.isIOS()) {
                handleIos(notification);
                $scope.$apply(function() {
                    $scope.notifications.push(JSON.stringify(notification.alert));
                });
            }
        });

        $scope.triggerPush = function () {
            Notification.success({
                message: 'Error notification (no timeout)',
                templateUrl: "notification_template.html",
                delay: 3000,
                scope: $scope
            });
        };

        function handleAndroid(notification) {
            console.log("In foreground " + notification.foreground + " Coldstart " + notification.coldstart);
            if (notification.event == "registered") {
                deviceService.saveDeviceToken(notification.regid).then(function () {
                    console.log("Token stored, device is successfully subscribed to receive push notifications.");
                }, function (data, status) {
                    console.log("Error storing device token.");
                });
            }
            else if (notification.event == "message") {
                $scope.$apply(function () {
                    $scope.push_content = notification.message;
                    $scope.push_img_path = notification.payload ? notification.payload.path : null;

                    $scope.triggerPush();

                    $scope.notifications.push(JSON.stringify(notification.message));
                });
            }
            else if (notification.event == "error")
                $cordovaDialogs.alert(notification.msg, "Push notification error event");
            else $cordovaDialogs.alert(notification.event, "Push notification handler - Unprocessed Event");
        };

        function handleIos(notification) {
            if (notification.foreground === "1") {
                if (notification.sound) {
                    var mediaSrc = $cordovaMedia.newMedia(notification.sound);
                    mediaSrc.promise.then($cordovaMedia.play(mediaSrc.media));
                }

                if (notification.body && notification.messageFrom) {
                    $cordovaDialogs.alert(notification.body, notification.messageFrom);
                }
                else $cordovaDialogs.alert(notification.alert, "Push Notification Received");

                if (notification.badge) {
                    $cordovaPush.setBadgeNumber(notification.badge).then(function (result) {
                        console.log("Set badge success " + result);
                    }, function (err) {
                        console.log("Set badge error " + err);
                    });
                }
            }
            else {
                if (notification.body && notification.messageFrom) {
                    $cordovaDialogs.alert(notification.body, "(RECEIVED WHEN APP IN BACKGROUND) " + notification.messageFrom);
                }
                else $cordovaDialogs.alert(notification.alert, "(RECEIVED WHEN APP IN BACKGROUND) Push Notification Received");
            }
        };
    }
})();