(function () {
    'use strict';

    angular
        .module('app')
        .controller('popularController', popularController);

    popularController.$inject = [
        '$scope', '$rootScope', '$state', '$cordovaToast', 'localStorageService', 'showsService', 'authService', 'subscriptionsService', 'pushNotificationsService'];

    function popularController($scope, $rootScope, $state, $cordovaToast, localStorageService, showsService, authService, subscriptionsService, pushNotificationsService) {
        var vm = this;

        vm.page = 1;
        vm.take = 10;
        vm.total = null;

        $scope.type = Subscriptions.episode;

        $scope.shows = [];
        $scope.count = null;

        $scope.selected = {};

        $scope.show_details_popup = false;
        $scope.show_episodes_loaded = false;

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

        $scope.select = function(show) {
            $scope.selected = show;

            show.show_loading = true;

            showsService.getShow(show.id).then(function(response) {
                $scope.selected.episodes = response.episodes;
                $scope.show_episodes_loaded = true;
                $scope.selected.currentIndex = response.next_episode ? (response.next_episode.number - 1) : (response.episodes.length - 1);

                vm.initSlider();

                $scope.show_details_popup = true;
                show.show_loading = false;
            }, function() {
                show.show_loading = false;
                $cordovaToast.showLongTop('Возникла непредвиденная ошибка.');
            });
        };

        $scope.closePopup = function () {
            $scope.show_details_popup = false;
            $scope.show_episodes_loaded = false;
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
            $scope.type = $scope.type === Subscriptions.episode ? Subscriptions.season : Subscriptions.episode;
        };

        $scope.testPush = function() {
            pushNotificationsService.testPush('message');
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
                        slidesToShow: 5,

                    }
                }
            ];
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