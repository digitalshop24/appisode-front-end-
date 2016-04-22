(function () {
    'use strict';

    angular
        .module('app')
        .controller('subscriptionsController', subscriptionsController);

    subscriptionsController.$inject = [
        '$scope', '$rootScope', 'subscriptionsService'];

    function subscriptionsController($scope, $rootScope, subscriptionsService) {
        var vm = this;

        vm.page = 1;
        vm.take = 10;

        $scope.shows = [];
        $scope.count = null;
        $scope.busy = true;

        $scope.init = function () {
            vm.page = 1;

            $scope.busy = true;
            $scope.shows = [];
            $scope.count = null;
            $scope.getSubscriptions();
        };

        $scope.getSubscriptions = function () {
            $scope.busy = true;

            subscriptionsService.getList().then(function(response) {
                $.each(response, function() {
                    $scope.shows.push(this);
                });

                $scope.busy = false;
            });
        };

        $scope.init();
    };

})();