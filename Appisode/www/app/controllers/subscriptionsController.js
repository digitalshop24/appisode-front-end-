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

        $scope.subscriptions = [];
        $scope.total = null;

        $scope.init = function () {
            $scope.subscriptions = [];
            $scope.count = null;
            $scope.getSubscriptions();
        };

        $scope.getSubscriptions = function () {
            if ($scope.total != null && $scope.total <= vm.page * vm.take) {
                return;
            }

            if ($scope.busy) {
                return;
            }

            $scope.busy = true;

            subscriptionsService.getList().then(function (response) {
                $scope.total = response.total;
                $scope.totalDesc = NumbersFactory.declOfNum(response.total, ["сериал", "сериала", "сериалов"]);

                $.each(response.items, function () {
                    $scope.subscriptions.push(vm.extendSubscription(this));
                });

                vm.page += 1;
            }).finally(function () {
                $scope.busy = false;
            });
        };

        $scope.unsubscribe = function(subscription) {
            subscriptionsService.unsubscribe(subscription.id, subscription.show.id).then(function() {
                $scope.subscriptions.splice($scope.subscriptions.indexOf(subscription), 1);
                $scope.totalDesc = NumbersFactory.declOfNum($scope.total - 1, ["сериал", "сериала", "сериалов"]);
            }, function() {
                
            });
        };

        vm.extendSubscription = function (subscription) {
            subscription.show.air_date_str = DateFactory.getDate(subscription.show.next_episode ? subscription.show.next_episode.air_date : null);
            subscription.show.air_date_detailed = DateFactory.getMonthDaysHours(subscription.show.next_episode ? subscription.show.next_episode.days_left : null);
            subscription.show.progress_class = subscription.show.current_season_episodes_number ? (subscription.show.next_episode.number / subscription.show.current_season_episodes_number * 100 <= 33 ? 'mini' : 'normal') : (subscription.show.in_production ? '' : 'disibe');
            subscription.show.days_left_str = NumbersFactory.declOfNum(subscription.show.next_episode.days_left, ['день', 'дня', 'дней']);
            return subscription;
        };

        $scope.init();
    }
})();