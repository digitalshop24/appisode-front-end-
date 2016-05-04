(function () {
    'use strict';

    angular
        .module('app')
        .controller('popupAuthController', popupAuthController);

    popupAuthController.$inject = [
        '$scope', '$rootScope', '$state', 'localStorageService', 'authService', 'ngLocalStorageKeys'
    ];

    function popupAuthController($scope, $rootScope, $state, localStorageService, authService, ngLocalStorageKeys) {
        $scope.number = null;

        $scope.togglePhone = function (element) {
            $(element.currentTarget).toggleClass("active");
        };

        $scope.goto2 = function () {
            if (!$scope.number) {
                return false;
            } else {
                authService.register($scope.number).then(function (response) {
                    localStorageService.set(ngLocalStorageKeys.phone, $scope.number);
                    $state.go($state.$current.parent.name + '.auth-step2');
                });

                return true;
            }
        };

        $scope.goto3 = function () {
            $state.go($state.$current.parent.name + '.auth-step1');
        };

        $scope.back = function() {
            $state.go($state.$current.parent.name);
        };
    };
})();