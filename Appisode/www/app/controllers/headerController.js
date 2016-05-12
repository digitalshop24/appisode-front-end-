(function () {
    'use strict';

    angular
        .module('app')
        .controller('headerController', headerController);

    headerController.$inject = [
        '$scope', '$rootScope', '$state', 'localStorageService', 'authService', 'ngLocalStorageKeys'];

    function headerController($scope, $rootScope, $state, localStorageService, authService, ngLocalStorageKeys) {
        var vm = this;

        vm.authPhone = localStorageService.get(ngLocalStorageKeys.phone);
        vm.authKey = localStorageService.get(ngLocalStorageKeys.key);

        $scope.closeSearch = function() {
            history.go(-1);
        };

        $scope.authorize = function(element) {
            $(element.currentTarget).toggleClass("active");
            authService.checkAuth(vm.authPhone, vm.authKey).then(function(response) {
                $state.go();
            }, function (code) {
                $state.go('auth-step1');
            });
        };
    };

})();