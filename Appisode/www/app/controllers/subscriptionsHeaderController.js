(function() {
    'use strict';

    angular
        .module('app')
        .controller('subscriptionsHeaderController', subscriptionsHeaderController);

    subscriptionsHeaderController.$inject = [
        '$scope', '$rootScope', '$state', '$window', 'localStorageService', 'authService', 'ngLocalStorageKeys'
    ];

    function subscriptionsHeaderController($scope, $rootScope, $state, $window, localStorageService, authService, ngLocalStorageKeys) {
        $scope.back = function() {
            $window.history.back();
        };
    }
})();