(function () {
    'use strict';

    angular
        .module('app')
        .controller('newestController', newestController);

    newestController.$inject = [
        '$scope', '$rootScope', '$state', '$timeout', '$cordovaToast', '$cordovaNetwork', 'localStorageService', 'showsService', 'authService', 'subscriptionsService'];

    function newestController($scope, $rootScope, $state, $timeout, $cordovaToast, $cordovaNetwork, localStorageService, showsService, authService, subscriptionsService) {
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

        $scope.init = function () {
            $scope.busy = true;

            $scope.shows = [];
            $scope.count = null;
            $scope.getShows();
        };

        $scope.getShows = function () {
            $scope.networkOff = false;

            if ($cordovaNetwork.isOffline()) {
                $scope.busy = false;
                $scope.networkOff = true;
                return;
            }

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
            $scope.selected.isSeason = false;
            $scope.type = Subscriptions.episode;

            showsService.getShow(show.id).then(function (response) {
                $scope.selected.episodes = response.episodes;

                if (!show.subscription || show.subscription.subtype === Subscriptions.season) {
                    $scope.selected.initialSlide = response.next_episode
                        ? (response.next_episode.number - 1)
                        : (response.episodes.length - 1);
                } else {
                    $scope.selected.initialSlide = show.subscription.next_notification_episode
                        ? show.subscription.next_notification_episode.number - 1
                        : (response.next_episode
                            ? (response.next_episode.number - 1)
                            : (response.episodes.length - 1));
                }

                if (show.subscription && show.subscription.subtype === Subscriptions.season) {
                    $scope.type = Subscriptions.season;
                    $scope.selected.isSeason = true;
                }

                $scope.selected.currentIndex = $scope.selected.initialSlide;

                if ($scope.type === Subscriptions.episode) {
                    vm.initSlider();
                }

                var lastEpisode = $scope.selected.episodes[response.episodes.length - 1];
                $scope.selected.season_air_date_detailed = DateFactory.getMonthDaysHours(lastEpisode ? lastEpisode.days_left : null);

                $scope.show_details_popup = true;
                show.show_loading = false;
            }, function () {
                show.show_loading = false;
                $cordovaToast.showLongTop('Возникла непредвиденная ошибка.');
            });
        };

        $scope.closePopup = function () {
            $scope.sliderConfig = null;
            $scope.show_details_popup = false;
        };

        $scope.subscribe = function (event) {
            event.stopPropagation();

            $scope.selected.subscribeLoading = true;

            var episode = $scope.selected.episodes[$scope.selected.currentIndex];

            subscriptionsService.subscribe($scope.selected.id, $scope.type === Subscriptions.episode ? episode.id : null, $scope.type).then(function (response) {
                if (!$scope.selected.subscription) {
                    $rootScope.subscriptionsTotal += 1;
                }

                $scope.selected.subscription = response;
                $scope.selected.subscribeLoading = false;

                $scope.closePopup();
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

        $scope.changePeriod = function() {
            $scope.type = $scope.type === Subscriptions.episode ? Subscriptions.season : Subscriptions.episode;

            if ($scope.type === Subscriptions.episode) {
                $scope.selected.isSeason = false;
                $scope.sliderConfig = null;
                $timeout(vm.initSlider, 1);
            }
        };

        $scope.inView = function (index, inview, inviewpart, event, show) {
            var item = vm.getShow(show);

            item.animate_bell = inview;
        };

        vm.getShow = function (id) {
            var show;

            $.each($scope.shows, function () {
                if (this.id === id) {
                    show = this;
                }
            });

            return show;
        };

        vm.extendShow = function (show) {
            show.air_date_str = DateFactory.getDate(show.next_episode ? show.next_episode.air_date : null);
            show.air_date_detailed = DateFactory.getMonthDaysHours(show.next_episode ? show.next_episode.days_left : null);
            show.animate_bell = false;
            return show;
        };

        $scope.sliderChangePos = function (event, index) {
            $scope.selected.currentIndex = index;

            var episode = $scope.selected.episodes[index];

            $scope.selected.subscribe_btn_disable = episode.aired;

            $scope.selected.episode_date = episode ? DateFactory.getDate(episode.air_date) : ('дата неизвестна');
        };

        vm.initSlider = function () {
            $scope.sliderConfig = {
                start: $scope.selected.initialSlide,
                method: {},
                event: { changePos: $scope.sliderChangePos }
            };
        };

        $scope.init();

        $scope.refresh = $scope.getShows;
    };

})();