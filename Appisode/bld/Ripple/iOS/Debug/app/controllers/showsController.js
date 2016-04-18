(function() {
    'use strict';

    angular
        .module('app')
        .controller('showsController', showsController);

    showsController.$inject = [
        '$scope', 'showsService'];

    function showsController($scope, showsService) {
        var vm = this;

        vm.page = 1;
        vm.take = 10;

        $scope.shows = [];

        $scope.getShows = function() {
            showsService.getList(vm.page, vm.take).then(function (response) {
                $.each(response, function () {
                    vm.page += 1;
                    $scope.shows.push(vm.newShow(this));
                });
            });
        };

        vm.newShow = function (show) {
            return {
                name: show.name,
                russian_name: show.russian_name
            };
        };

        $scope.getShows();
    };

})();