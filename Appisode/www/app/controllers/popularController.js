(function () {
    'use strict';

    angular
        .module('app')
        .controller('popularController', popularController);

    popularController.$inject = [
        '$scope', '$rootScope', '$state', 'localStorageService', 'showsService', 'authService', 'ngLocalStorageKeys'];

    function popularController($scope, $rootScope, $state, localStorageService, showsService, authService, ngLocalStorageKeys) {
        var vm = this;

        vm.page = 1;
        vm.take = 10;
        vm.total = null;

        vm.authPhone = localStorageService.get(ngLocalStorageKeys.phone);
        vm.authKey = localStorageService.get(ngLocalStorageKeys.key);

        $scope.shows = [];
        $scope.count = null;

        $scope.init = function() {
            $scope.shows = [];
            $scope.count = null;
            $scope.getShows();
        };

        $scope.getShows = function () {
            if (vm.total != null && vm.total <= vm.page * vm.take) {
                return;
            }

            if ($scope.busy) {
                return;
            }

            $scope.busy = true;

            showsService.popularList(vm.page, vm.take).then(function (response) {
                vm.total = response.total;

                $.each(response.shows, function () {
                    $scope.shows.push(vm.extendShow(this));
                });

                vm.page += 1;
            }).finally(function () {
                $scope.busy = false;
            });;
        };

        $scope.gotoShow = function(id) {
            $state.go('show', {showId: id});
        };

        $scope.like = function (event) {
            event.stopPropagation();
            $(event.currentTarget).toggleClass("active");
        };

        $scope.subscribe = function(event) {
            event.stopPropagation();

            authService.checkAuth(vm.authPhone, vm.authKey).then(function(response) {
                $state.go();
            }, function(code) {
                $state.go($state.$current.parent.name + '.auth-step1');
            });
        };

        $scope.changePeriod = function (event) {
            event.stopPropagation();
        };

        vm.extendShow = function(show) {
            show.air_date_str = DateFactory.getDate(show.next_episode ? show.next_episode.air_date : null);
            show.air_date_detailed = DateFactory.getMonthDaysHours(show.next_episode ? show.next_episode.days_left : null);
            return show;
        };

        $scope.init();
    };

})();