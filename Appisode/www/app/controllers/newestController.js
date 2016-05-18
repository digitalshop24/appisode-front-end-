(function () {
    'use strict';

    angular
        .module('app')
        .controller('newestController', newestController);

    newestController.$inject = [
        '$scope', '$rootScope', '$state', 'localStorageService', 'showsService', 'authService', 'subscriptionsService'];

    function newestController($scope, $rootScope, $state, localStorageService, showsService, authService, subscriptionsService) {
        var vm = this;

        vm.page = 1;
        vm.take = 10;
        vm.total = null;
        vm.type = Subscriptions.episode;

        $scope.shows = [];
        $scope.count = null;
        $scope.busy = true;

        $scope.init = function () {
            $scope.busy = true;

            $scope.shows = [];
            $scope.count = null;
            $scope.getShows();
        };

        $scope.getShows = function () {
            if (vm.total != null && vm.total <= vm.page * vm.take) {
                return;
            }

            $scope.busy = true;

            showsService.newestList(vm.page, vm.take).then(function (response) {
                vm.total = response.total;

                $.each(response.shows, function () {
                    $scope.shows.push(vm.extendShow(this));
                });

                vm.page += 1;
            }).finally(function () {
                $scope.busy = false;
            });
        };

        $scope.gotoShow = function (id) {
            $state.go('show', { showId: id });
        };

        $scope.like = function (event, show) {
            event.stopPropagation();

            authService.checkAuth().then(function () {
                subscriptionsService.subscribe(show.id, null, vm.type).then(function () {
                    $(event.currentTarget).toggleClass("active");
                }, function () { });
            }, function () {
                $state.go($state.$current.parent.name + '.auth-step1');
            });
        };

        $scope.changePeriod = function (event) {
            event.stopPropagation();

            vm.type = vm.type === Subscriptions.episode ? Subscriptions.season : Subscriptions.episode;
        };

        vm.extendShow = function (show) {
            show.air_date_str = DateFactory.getDate(show.next_episode ? show.next_episode.air_date : null);
            show.air_date_detailed = DateFactory.getMonthDaysHours(show.next_episode ? show.next_episode.days_left : null);
            return show;
        };

        $scope.init();
    };

})();