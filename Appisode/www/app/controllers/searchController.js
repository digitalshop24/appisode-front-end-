(function () {
    'use strict';

    angular
        .module('app')
        .controller('searchController', searchController);

    searchController.$inject = ['$scope', '$rootScope', '$location', 'showsService'];

    function searchController($scope, $rootScope, $location, showsService) {
        var vm = this;

        vm.page = 1;
        vm.take = 10;
            
        $rootScope.hide_main_layout = true;
        
        $scope.shows = [];
        $scope.query = null;
        $scope.count = null;
        $scope.busy = true;
        $scope.spinner = false;

        $scope.init = function () {
            vm.page = 1;

            $scope.busy = true;
            $scope.shows = [];
            $scope.count = null;
            $scope.search();
        };

        $scope.search = function () {
            $scope.spinner = true;

            showsService.searchList(vm.page, vm.take, $scope.query).then(function (response) {
                vm.page += 1;

                $.each(response, function () {
                    $scope.shows.push(this);
                });

                $scope.count = response.length;
                $scope.busy = false;
                $scope.spinner = false;
            });
        };

        $scope.toggleSearch = function(element) {
            $(element.currentTarget).toggleClass("active");
        };

        $scope.gotoShow = function (id) {
            $location.path('/show/' + id);
        };
    };
})();