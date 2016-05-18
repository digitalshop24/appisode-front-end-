(function () {
    'use strict';

    angular
        .module('app')
        .controller('headerController', headerController);

    headerController.$inject = [
        '$scope', '$rootScope', '$state', 'localStorageService', 'authService', 'ngLocalStorageKeys'];

    function headerController($scope, $rootScope, $state, localStorageService, authService, ngLocalStorageKeys) {
        var vm = this;

        $scope.closeSearch = function() {
            history.go(-1);
        };

        $scope.authorize = function(element) {
            $(element.currentTarget).toggleClass("active");
            authService.checkAuth().then(function() {
                $state.go('subscriptions');
            }, function () {
                $state.go('auth-step1');
            });
        };
    };

})();