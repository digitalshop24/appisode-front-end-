﻿(function () {
    'use strict';

    angular
        .module('app')
        .controller('searchResultsController', searchResultsController);

    searchResultsController.$inject = [
        '$scope', '$rootScope', '$state', '$stateParams', '$timeout', '$cordovaToast', 'localStorageService', 'showsService', 'authService', 'subscriptionsService', 'pushNotificationsService'];

    function searchResultsController($scope, $rootScope, $state, $stateParams, $timeout, $cordovaToast, localStorageService, showsService, authService, subscriptionsService, pushNotificationsService) {
        var vm = this;

        $scope.showId = $stateParams.showId;

        vm.page = 1;
        vm.take = 10;
        vm.total = null;

        $rootScope.hide_header = true;

        $scope.type = Subscriptions.episode;

        $scope.shows = [];
        $scope.count = null;

        $scope.selected = {};

        $scope.show_details_popup = false;

        $scope.init = function () {
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

            showsService.searchListShow($scope.showId, vm.page, vm.take).then(function (response) {
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
                $scope.selected.initialSlide = response.next_episode ? (response.next_episode.number - 1) : (response.episodes.length - 1);

                $scope.selected.currentIndex = $scope.selected.initialSlide;

                if ($scope.type === Subscriptions.episode) {
                    vm.initSlider();
                }

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
                $scope.sliderConfig = null;
                $timeout(vm.initSlider, 0.1);
            }
        };

        $scope.getSubscriptions = function () {
            if (!$rootScope.subscriptionsVisible) {
                subscriptionsService.getList(1, 1).then(function (response) {
                    $rootScope.subscriptionsVisible = true;
                    $rootScope.subscriptionsTotal = response.total;
                }, function (code) {
                    $rootScope.subscriptionsVisible = false;
                });
            }
        };

        $scope.sliderChangePos = function (event, index) {
            $scope.selected.currentIndex = index;

            var episode = $scope.selected.episodes[index];

            $scope.selected.episode_date = episode ? DateFactory.getDate(episode.air_date) : ('дата неизвестна');
        };

        vm.initSlider = function () {
            $scope.sliderConfig = {
                start: $scope.selected.initialSlide,
                method: {},
                event: { changePos: $scope.sliderChangePos }
            };
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

        $scope.init();
        $scope.getSubscriptions();
    };

})();