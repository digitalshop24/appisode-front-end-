(function () {
    'use strict';

    angular
        .module('app')
        .controller('headerController', headerController);

    headerController.$inject = [
        '$rootScope', '$scope', 'subscriptionsService'];

    function headerController($rootScope, $scope, subscriptionsService) {
        $scope.subscriptionsVisible = false;
        $rootScope.subscriptionsTotal = 0;

        $scope.getSubscriptions = function() {
            subscriptionsService.getList(1, 1).then(function(response) {
                $scope.subscriptionsVisible = true;
                $rootScope.subscriptionsTotal = response.total;
            }, function(code) {
                $scope.subscriptionsVisible = false;
            });
        };

        $scope.getSubscriptions();
    };

})();