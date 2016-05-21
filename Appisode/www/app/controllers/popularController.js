(function () {
    'use strict';

    angular
        .module('app')
        .controller('popularController', popularController);

    popularController.$inject = [
        '$scope', '$rootScope', '$state', '$timeout', 'localStorageService', 'showsService', 'authService', 'subscriptionsService', 'pushNotificationsService'];

    function popularController($scope, $rootScope, $state, $timeout, localStorageService, showsService, authService, subscriptionsService, pushNotificationsService) {
        var vm = this;

        vm.page = 1;
        vm.take = 10;
        vm.total = null;
        vm.type = Subscriptions.episode;

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
            });
        };

        $scope.gotoShow = function(id) {
            $state.go('show', {showId: id});
        };

        $scope.like = function (event, show) {
            event.stopPropagation();

            show.likeLoading = true;

            subscriptionsService.subscribe(show.id, null, vm.type).then(function() {
                show.likeLoading = false;
                $(event.currentTarget).toggleClass("active");
            }, function(code) {
                show.likeLoading = false;
                if (code === 401) {
                    $state.go($state.$current.parent.name + '.auth-step1');
                }
            });
        };

        $scope.changePeriod = function (event) {
            event.stopPropagation();

            vm.type = vm.type === Subscriptions.episode ? Subscriptions.season : Subscriptions.episode;
        };

        $scope.testPush = function() {
            pushNotificationsService.testPush('message');
        };

        vm.extendShow = function(show) {
            show.air_date_str = DateFactory.getDate(show.next_episode ? show.next_episode.air_date : null);
            show.air_date_detailed = DateFactory.getMonthDaysHours(show.next_episode ? show.next_episode.days_left : null);
            show.likeLoading = false;
            return show;
        };

        $scope.init();
    };

})();