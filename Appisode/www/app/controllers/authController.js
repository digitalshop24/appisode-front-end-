(function() {
    'use strict';

    angular
        .module('app')
        .controller('authController', authController);

    authController.$inject = [
        '$scope', '$rootScope', '$state', '$cordovaToast', 'localStorageService', 'authService', 'pushNotificationsService', 'ngLocalStorageKeys'
    ];

    function authController($scope, $rootScope, $state, $cordovaToast, localStorageService, authService, pushNotificationsService, ngLocalStorageKeys) {
        $rootScope.hide_header = true;

        $scope.number = null;
        $scope.code = null;

        $scope.loading = false;

        $scope.togglePhone = function(element) {
            $(element.currentTarget).toggleClass("active");
        };

        $scope.goto4 = function() {
            if (!$scope.number) {
                $cordovaToast.showLongTop('Пожалуйста, введите номер телефона');
                return false;
            } else {
                $scope.loading = true;

                authService.register($scope.number).then(function(response) {
                    localStorageService.set(ngLocalStorageKeys.phone, response.phone);
                    $scope.loading = false;
                    $state.go('auth-step4');
                }, function() {
                    $scope.loading = false;
                });

                return true;
            }
        };

        $scope.goto5 = function() {
            if (!$scope.code) {
                $cordovaToast.showLongTop('Пожалуйста, введите код подтверждения');
                return false;
            } else {
                $scope.loading = true;

                var phone = localStorageService.get(ngLocalStorageKeys.phone);

                authService.checkConfirmation(phone, $scope.code).then(function(response) {
                    localStorageService.set(ngLocalStorageKeys.key, response.auth_token);
                    $scope.loading = false;
                    $state.go('auth-step5');
                }, function() {
                    $cordovaToast.showLongTop('Неверный код подтверждения');
                    $scope.loading = false;
                });
            }

            return true;
        };

        $scope.finish = function() {
            pushNotificationsService.getList($rootScope.pushPage, $rootScope.pushPerPage).then(function (response) {
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
                    }
                }, function(code) {});

                $state.go('popular');
            });
        };
    }
})();