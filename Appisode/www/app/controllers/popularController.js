(function () {
    'use strict';

    angular
        .module('app')
        .controller('popularController', popularController);

    popularController.$inject = [
        '$scope', '$rootScope', '$location', 'showsService'];

    function popularController($scope, $rootScope, $location, showsService) {
        var vm = this;

        vm.page = 1;
        vm.take = 10;

        $scope.shows = [];
        $scope.count = null;
        $scope.busy = true;

        $scope.init = function() {
            vm.page = 1;

            $scope.busy = true;
            $scope.shows = [];
            $scope.count = null;
            $scope.getShows();
        };

        $scope.getShows = function () {
            $scope.busy = true;

            showsService.popularList().then(function (response) {
                $.each(response, function () {
                    $scope.shows.push(this);
                });

                $scope.busy = false;
            });
        };

        $scope.gotoShow = function(id) {
            $location.path('/show/' + id);
        };

        $scope.init();
    };

})();