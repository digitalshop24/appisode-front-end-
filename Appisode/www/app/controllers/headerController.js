(function () {
    'use strict';

    angular
        .module('app')
        .controller('headerController', headerController);

    headerController.$inject = [
        '$scope', 'subscriptionsService'];

    function headerController($scope, subscriptionsService) {
        $scope.subscriptionsVisible = false;
        $scope.subscriptionsTotal = 0;

        $scope.getSubscriptions = function() {
            subscriptionsService.getList(1, 1).then(function(response) {
                $scope.subscriptionsVisible = true;
                $scope.subscriptionsTotal = response.total;
            }, function(code) {
                $scope.subscriptionsVisible = false;
            });
        };

        $scope.getSubscriptions();
    };

})();