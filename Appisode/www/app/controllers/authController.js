(function() {
    'use strict';

    angular
        .module('app')
        .controller('authController', authController);

    authController.$inject = [
        '$scope', '$rootScope', '$location', 'localStorageService', 'authService', 'ngAuthSettings'
    ];

    function authController($scope, $rootScope, $location, localStorageService, authService, ngAuthSettings) {
        $scope.number = null;
        
        $scope.togglePhone = function(element) {
            $(element.currentTarget).toggleClass("active");
        };

        $scope.goto3 = function() {
            if (!$scope.number) {
                return false;
            } else {
                authService.register($scope.number).then(function (response) {
                    localStorageService.set(ngAuthSettings.phone, $scope.number);
                    $location.path('/auth/step3');
                });

                return true;
            }
        };
    };
})();