(function () {
    'use strict';

    angular
        .module('app')
        .controller('searchHeaderController', searchHeaderController);

    searchHeaderController.$inject = [
        '$scope', '$rootScope', '$state'];

    function searchHeaderController($scope, $rootScope, $state) {
        $scope.back = function () {
            $state.go('popular');
        };
    };

})();