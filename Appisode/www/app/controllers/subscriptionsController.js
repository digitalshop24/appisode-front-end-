(function() {
    'use strict';

    angular
        .module('app')
        .controller('subscriptionsController', subscriptionsController);

    subscriptionsController.$inject = [
        '$scope', '$rootScope', '$state', 'localStorageService', 'subscriptionsService', 'authService', 'ngLocalStorageKeys'
    ];

    function subscriptionsController($scope, $rootScope, $state, localStorageService, subscriptionsService, authService, ngLocalStorageKeys) {
        var vm = this;

        vm.page = 1;
        vm.take = 10;

        vm.authPhone = localStorageService.get(ngLocalStorageKeys.phone);
        vm.authKey = localStorageService.get(ngLocalStorageKeys.key);

        $scope.shows = [];
        $scope.total = null;

        $scope.init = function () {
            $scope.shows = [];
            $scope.count = null;
            $scope.getShows();
        };

        $scope.getShows = function () {
            if ($scope.total != null && $scope.total <= vm.page * vm.take) {
                return;
            }

            if ($scope.busy) {
                return;
            }

            $scope.busy = true;

            subscriptionsService.getList().then(function (response) {
                $scope.total = response.length;

                $.each(response, function () {
                    $scope.shows.push(vm.extendShow(this.show));
                });

                vm.page += 1;
            }).finally(function () {
                $scope.busy = false;
            });
        };

        vm.extendShow = function (show) {
            show.air_date_str = DateFactory.getDate(show.next_episode ? show.next_episode.air_date : null);
            show.air_date_detailed = DateFactory.getMonthDaysHours(show.next_episode ? show.next_episode.days_left : null);
            return show;
        };

        $scope.init();
    }
})();