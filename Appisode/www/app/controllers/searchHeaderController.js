(function () {
    'use strict';

    angular
        .module('app')
        .controller('searchHeaderController', searchHeaderController);

    searchHeaderController.$inject = [
        '$scope', '$rootScope', '$state', '$timeout'];

    function searchHeaderController($scope, $rootScope, $state, $timeout) {
        var vm = this;

        $scope.back = function () {
            $rootScope.hide_header = false;
            $timeout(vm.back, 5);
        };

        $scope.focus = function() {
            $rootScope.search_focus = true;
        };

        vm.back = function() {
            $state.go($rootScope.current_action);
        };
    };

})();