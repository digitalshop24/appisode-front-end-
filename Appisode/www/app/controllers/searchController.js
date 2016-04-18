(function () {
    'use strict';

    angular
        .module('app')
        .controller('searchController', searchController);

    searchController.$inject = ['$scope', 'showsService'];

    function searchController($scope, showsService) {
        var vm = this;

        vm.page = 1;
        vm.take = 10;

        $scope.shows = [];

        $scope.search = function () {
            showsService.getList(vm.page, vm.take).then(function (response) {
                $.each(response, function () {
                    vm.page += 1;
                    $scope.shows.push(vm.newShow(this));
                });
            });
        };

        $scope.toggleSearch = function(element) {
            $(element.currentTarget).toggleClass("active");
        };

        vm.newShow = function (show) {
            return {
                name: show.name,
                russian_name: show.russian_name
            };
        };
    };
})();