(function() {
    'use strict';

    angular
        .module('app')
        .controller('showController', showController);

    showController.$inject = [
        '$scope', '$rootScope', '$stateParams', '$state', '$timeout', '$cordovaToast', 'showsService', 'authService', 'subscriptionsService'
    ];

    function showController($scope, $rootScope, $stateParams, $state, $timeout, $cordovaToast, showsService, authService, subscriptionsService) {
        var vm = this;

        $scope.showId = $stateParams.showId;

        $scope.type = Subscriptions.episode;

        $scope.show = {};
        $scope.episodes = [];
        $scope.busy = true;
        $scope.currentIndex = 0;
    
        $scope.getShow = function() {
            showsService.getShow($scope.showId).then(function(response) {
                $scope.show = vm.newShow(response);

                $.each(response.episodes, function() {
                    $scope.episodes.push(this);
                });

                $scope.busy = false;
            });
        };

        $scope.subscribe = function (event) {
            event.stopPropagation();

            $scope.show.subscribeLoading = true;

            var episode = $scope.show.episodes[$scope.show.currentIndex];

            subscriptionsService.subscribe($scope.show.id, $scope.type === Subscriptions.episode ? episode.id : null, $scope.type).then(function (response) {
                $scope.show.subscription_id = response.id;
                $scope.show.subscribeLoading = false;
                $scope.show_details_popup = false;

                $rootScope.subscriptionsTotal += 1;
            }, function (code) {
                $scope.show.subscribeLoading = false;
                if (code === 401) {
                    $state.go($state.$current.parent.name + '.auth-step1');
                }
                if (code === 406) {
                    $cordovaToast.showLongTop('Такая подписка уже существует.');
                }
            });
        };

        $scope.select = function() {
            $scope.selected = $scope.show;

            $scope.show.show_loading = true;

            $scope.selected.episodes = $scope.episodes;
            $scope.selected.initialSlide = $scope.show.next_episode ? ($scope.show.next_episode.number - 1) : ($scope.show.episodes.length - 1);

            $scope.selected.currentIndex = $scope.selected.initialSlide;

            vm.initSlider();

            $scope.show_details_popup = true;
            $scope.show.show_loading = false;
        };

        $scope.closePopup = function () {
            $scope.sliderConfig = null;
            $scope.show_details_popup = false;
        };

        $scope.changePeriod = function () {
            $scope.type = $scope.type === Subscriptions.episode ? Subscriptions.season : Subscriptions.episode;
        };

        $scope.sliderChangePos = function (event, index) {
            $scope.selected.currentIndex = index;
        };

        vm.initSlider = function () {
            $scope.sliderConfig = {
                start: $scope.selected.initialSlide,
                method: {},
                event: { changePos: $scope.sliderChangePos }
            };
        };

        vm.newShow = function (show) {
            show.air_date_str = DateFactory.getDate(show.next_episode ? show.next_episode.air_date : null);
            show.air_date_detailed = DateFactory.getMonthDaysHours(show.next_episode ? show.next_episode.days_left : null);

            return show;
        };

        $scope.getShow();
    };
})();