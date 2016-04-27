(function() {
    'use strict';

    angular
        .module('app')
        .controller('authController', authController);

    authController.$inject = [
        '$scope', '$rootScope', '$state', 'localStorageService', 'authService', 'ngLocalStorageKeys'
    ];

    function authController($scope, $rootScope, $state, localStorageService, authService, ngLocalStorageKeys) {
        $scope.number = null;
        
        $scope.togglePhone = function(element) {
            $(element.currentTarget).toggleClass("active");
        };

        $scope.goto3 = function() {
            if (!$scope.number) {
                return false;
            } else {
                authService.register($scope.number).then(function (response) {
                    localStorageService.set(ngLocalStorageKeys.phone, $scope.number);
                    $state.go('auth-step3');
                });

                return true;
            }
        };

        $scope.goto4 = function () {
            $state.go('auth-step4');
        };
    };
})();