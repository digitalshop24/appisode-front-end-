(function () {
    'use strict';

    angular
        .module('app')
        .controller('searchController', searchController);

    searchController.$inject = ['$scope', '$rootScope', 'showsService'];

    function searchController($scope, $rootScope, showsService) {
        var vm = this;

        vm.page = 1;
        vm.take = 10;
            
        $rootScope.hide_main_layout = true;
        
        $scope.shows = [];
        $scope.query = null;
        $scope.count = null;
        $scope.loaded = false;

        $scope.search = function () {
            $scope.shows = [];
            $scope.count = null;
            $scope.loaded = false;

            showsService.searchList(vm.page, vm.take, $scope.query).then(function (response) {
                vm.page += 1;

                $.each(response, function () {
                    $scope.shows.push(this);
                });

                $scope.count = response.length;
                $scope.loaded = true;
            });
        };

        $scope.toggleSearch = function(element) {
            $(element.currentTarget).toggleClass("active");
        };

        $scope.closeSearch = function() {
            $rootScope.hide_main_layout = false;
            history.go(-1);
        };
    };
})();