(function () {
    'use strict';

    angular
        .module('app')
        .controller('searchController', searchController);
       
    searchController.$inject = ['$scope', '$rootScope', '$state', 'showsService', 'authService', 'subscriptionsService'];

    function searchController($scope, $rootScope, $state, showsService, authService, subscriptionsService) {
        var vm = this;

        vm.page = 1;
        vm.take = 10;
        
        $scope.shows = [];
        $scope.query = null;
        $scope.total = null;
        $scope.busy = true;
        $scope.spinner = false;

        $scope.init = function () {
            vm.page = 1;

            $scope.busy = true;
            $scope.shows = [];
            $scope.total = null;
            $scope.search();
        };

        $scope.search = function () {
            if ($scope.total != null && $scope.total <= vm.page * vm.take) {
                return;
            }

            if ($scope.spinner) {
                return;
            }

            $scope.spinner = true;

            showsService.searchList(vm.page, vm.take, $scope.query).then(function (response) {
                vm.page += 1;

                $.each(response.shows, function () {
                    $scope.shows.push(vm.extendShow(this));
                });

                $scope.total = response.total;
            }).finally(function() {
                $scope.busy = false;
                $scope.spinner = false;
            });
        };

        $scope.toggleSearch = function (event) {
            $(event.currentTarget).toggleClass("active");
        };

        $scope.like = function(event, show) {
            event.stopPropagation();

            show.likeLoading = true;

            subscriptionsService.subscribe(show.id, show.next_episode ? show.next_episode.id : null, Subscriptions.episode).then(function() {
                show.likeLoading = false;
                $(event.currentTarget).toggleClass("active");
            }, function(code) {
                show.likeLoading = false;
                if (code === 401) {
                    $state.go($state.$current.parent.name + '.auth-step1');
                }
            });
        };

        $scope.gotoShow = function (id) {
            $state.go('show', {showId: id});
        };

        vm.extendShow = function(show) {
            show.likeLoading = false;
            return show;
        };
    };
})();