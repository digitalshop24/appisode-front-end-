(function () {
    'use strict';

    angular
        .module('app')
        .controller('newestController', newestController);

    newestController.$inject = [
        '$scope', '$rootScope', '$state', '$timeout', '$cordovaToast', 'localStorageService', 'showsService', 'authService', 'subscriptionsService'];

    function newestController($scope, $rootScope, $state, $timeout, $cordovaToast, localStorageService, showsService, authService, subscriptionsService) {
        var vm = this;

        vm.page = 1;
        vm.take = 10;
        vm.total = null;

        $scope.type = Subscriptions.episode;

        $scope.shows = [];
        $scope.count = null;
        $scope.busy = true;

        $scope.selected = {};

        $scope.show_details_popup = false;
        $scope.show_episodes_loaded = false;

        $scope.slickControl = {};

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

        $scope.select = function (show) {
            $scope.selected = show;

            show.show_loading = true;

            showsService.getShow(show.id).then(function (response) {
                $scope.selected.episodes = response.episodes;
                $scope.show_episodes_loaded = true;
                $scope.selected.initialSlide = response.next_episode ? (response.next_episode.number - 1) : (response.episodes.length - 1);

                $scope.selected.currentIndex = $scope.selected.initialSlide;

                vm.initSlider();

                $scope.show_details_popup = true;
                show.show_loading = false;
            }, function () {
                show.show_loading = false;
                $cordovaToast.showLongTop('Возникла непредвиденная ошибка.');
            });
        };

        $scope.closePopup = function () {
            $scope.show_details_popup = false;
            $scope.show_episodes_loaded = false;
        };

        $scope.subscribe = function (event) {
            event.stopPropagation();

            $scope.selected.subscribeLoading = true;

            var episode = $scope.selected.episodes[$scope.selected.currentIndex];

            subscriptionsService.subscribe($scope.selected.id, $scope.type === Subscriptions.episode ? episode.id : null, $scope.type).then(function (response) {
                $scope.selected.subscription_id = response.id;
                $scope.selected.subscribeLoading = false;
                $scope.show_details_popup = false;

                $rootScope.subscriptionsTotal += 1;
            }, function (code) {
                $scope.selected.subscribeLoading = false;
                if (code === 401) {
                    $state.go($state.$current.parent.name + '.auth-step1');
                }
                if (code === 406) {
                    $cordovaToast.showLongTop('Такая подписка уже существует.');
                }
            });
        };

        $scope.changePeriod = function () {
            $scope.type = $scope.type === Subscriptions.episode ? Subscriptions.season : Subscriptions.episode;

            if ($scope.type === Subscriptions.episode) {
                $scope.show_episodes_loaded = true;

                vm.initSlider();

                $timeout($scope.slickControl.setPosition, 5);
            }
        };

        vm.extendShow = function (show) {
            show.air_date_str = DateFactory.getDate(show.next_episode ? show.next_episode.air_date : null);
            show.air_date_detailed = DateFactory.getMonthDaysHours(show.next_episode ? show.next_episode.days_left : null);
            return show;
        };

        vm.initSlider = function () {
            $scope.slickConfig = {
                infinite: true,
                centerMode: true,
                centerPadding: '120px',
                arrows: false,
                slidesToShow: 5
            };

            $scope.breakpoints = [
                {
                    breakpoint: 690,
                    settings: {
                        arrows: false,
                        centerMode: true,
                        centerPadding: '40px',
                        slidesToShow: 5
                    }
                },
                {
                    breakpoint: 480,
                    settings: {
                        arrows: false,
                        centerMode: true,
                        centerPadding: '20px',
                        slidesToShow: 5
                    }
                }
            ];
        };

        $scope.init();
    };

})();