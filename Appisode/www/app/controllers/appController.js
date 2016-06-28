(function() {
    'use strict';

    angular
        .module('app')
        .controller('appController', appController);

    appController.$inject = [
        '$rootScope', '$scope', '$state', '$timeout', '$cordovaPush', '$cordovaDialogs', '$cordovaMedia', 'Notification', 'deviceService', 'pushNotificationsService'];

    function appController($rootScope, $scope, $state, $timeout, $cordovaPush, $cordovaDialogs, $cordovaMedia, Notification, deviceService, pushNotificationsService) { 
        $rootScope.notifications = [];
        $rootScope.pushSkip = 0;
        $rootScope.pushTake = 6;
        $rootScope.pushTotal = 0;
        $rootScope.pushLoaded = 0;

        $rootScope.navSlickControl = {};
        $rootScope.current_action = 'popular';

        $rootScope.initialized = true;
        $rootScope.index = 0;

        ionic.Platform.ready(function () {
            pushNotificationsService.register();

            $scope.onAfterChangeNavSlick = function (index) {
                if (index == 0 || index == 3 || index == 6) {
                    $state.go('popular');
                    $rootScope.current_action = 'popular';
                }
                if (index == 1 || index == 4) {
                    $state.go('subscriptions');
                    $rootScope.current_action = 'subscriptions';
                }
                if (index == 2 || index === 5) {
                    $state.go('newest');
                    $rootScope.current_action = 'newest';
                }
            };
        });

        $scope.$on('$cordovaPush:notificationReceived', function (event, notification) {
            console.log(JSON.stringify([notification]));
            if (ionic.Platform.isAndroid()) {
                handleAndroid(notification);
            }
            else if (ionic.Platform.isIOS()) {
                handleIos(notification);
                $scope.$apply(function() {
                    $rootScope.notifications.push(JSON.stringify(notification.alert));
                });
            }
        });

        $rootScope.hideOnDelay = function (notification) {
            $timeout(function() { $scope.closePush(notification) }, 5000);
        };

        $scope.closePush = function (notification) {
            var index = $rootScope.notifications.indexOf(notification);

            if (notification.notification_id) {
                pushNotificationsService.markAsRead(notification.notification_id);

                $scope.loadPush();
            }

            if (index >= 0) {
                $timeout(function() { $rootScope.notifications.splice(notification, 1) }, 100);
            }
        };

        $scope.loadPush = function() {
            pushNotificationsService.getList($rootScope.pushSkip, $rootScope.pushTake).then(function(response) {
                $.each(response.shows, function() {
                    var notification = {
                        id: $rootScope.index++,
                        content: this.message,
                        image: this.image,
                        notification_id: this.id
                    };

                    var index = $rootScope.notifications.indexOf(notification);

                    if (index < 0) {
                        $rootScope.notifications.push(notification);
                        $rootScope.hideOnDelay(notification);
                    }
                });

                $rootScope.pushTotal = response.total;
                $rootScope.pushSkip += $rootScope.pushTake;
                $rootScope.pushTake = 1;
            }, function(code) {});
        };

        function handleAndroid(notification) {
            console.log("In foreground " + notification.foreground + " Coldstart " + notification.coldstart);
            if (notification.event === "registered") {
                deviceService.saveDeviceToken(notification.regid).then(function () {
                    console.log("Token stored, device is successfully subscribed to receive push notifications.");
                }, function (data, status) {
                    console.log("Error storing device token.");
                });
            }
            else if (notification.event === "message" && notification.foreground) {
                $scope.$apply(function () {
                    var n = {
                        id: $rootScope.index++,
                        content: notification.message,
                        image: notification.payload ? notification.payload.path : null,
                        notification_id: notification.payload.notification_id
                    };

                    $rootScope.hideOnDelay(n);

                    $rootScope.notifications.push(n);
                });
            }
            else if (notification.event === "error")
                $cordovaDialogs.alert(notification.msg, "Push notification error event");
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