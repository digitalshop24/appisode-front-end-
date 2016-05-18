﻿(function() {
    'use strict';

    angular
        .module('app')
        .controller('showController', showController);

    showController.$inject = [
        '$scope', '$rootScope', '$stateParams', '$state', '$timeout', 'showsService', 'authService', 'subscriptionsService'
    ];

    function showController($scope, $rootScope, $stateParams, $state, $timeout, showsService, authService, subscriptionsService) {
        var vm = this;

        $scope.showId = $stateParams.showId;
        $scope.show = {};
        $scope.episodes = [];
        $scope.episode = {};
        $scope.totalEpisodes = 0;
        $scope.busy = true;
        $scope.currentIndex = 0;
    
        $scope.getShow = function() {
            showsService.getShow($scope.showId).then(function(response) {
                $scope.show = vm.newShow(response);
                $scope.poster = response.poster;    

                $.each(response.episodes, function() {
                    $scope.episodes.push(vm.newEpisode(this));
                });

                $scope.totalEpisodes = response.episodes.length;
                $scope.current = $scope.episodes[$scope.show.next_episode ? ($scope.show.next_episode.number - 1) : ($scope.totalEpisodes - 1)];
                $scope.currentIndex = $scope.show.next_episode ? ($scope.show.next_episode.number - 1) : ($scope.totalEpisodes - 1);

                vm.initSlider();

                $scope.busy = false;
            });
        };

        $scope.like = function (event) {
            event.stopPropagation();

            authService.checkAuth().then(function() {
                subscriptionsService.subscribe($scope.show.id, null, vm.type).then(function() {
                    $(event.currentTarget).toggleClass("active");
                }, function() {});
            }, function() {
                $state.go($state.$current.parent.name + '.auth-step1');
            });
        };

        $scope.subscribe = function (event) {
            event.stopPropagation();

            authService.checkAuth().then(function () {
                subscriptionsService.subscribe($scope.show.id, null, Subscriptions.season).then(function () {
                    $(event.currentTarget).toggleClass("active");
                }, function () { });
            }, function () {
                $state.go($state.$current.parent.name + '.auth-step1');
            });
        };

        $scope.onAfterChange = function (index) {
            $scope.current = $scope.episodes[index];
            $scope.$apply();
        };

        vm.initSlider = function () {
            $scope.slickConfig = {
                infinite: true,
                centerMode: true,
                centerPadding: '120px',
                arrows: false,
                slidesToShow: 1,
                initialSlide: $scope.currentIndex
            };

            $scope.breakpoints = [
                {
                    breakpoint: 690,
                    settings: {
                        arrows: false,
                        centerMode: true,
                        centerPadding: '40px',
                        slidesToShow: 3,
                        initialSlide: $scope.currentIndex
                    }
                },
                {
                    breakpoint: 480,
                    settings: {
                        arrows: false,
                        centerMode: true,
                        centerPadding: '20px',
                        slidesToShow: 1,
                        initialSlide: $scope.currentIndex
                    }
                }
            ];
        };

        vm.newShow = function(show) {
            return {
                id: show.id,
                in_production: show.in_production,
                name: show.name,
                russian_name: show.russian_name,
                season_number: show.season_number,
                poster: show.poster,
                next_episode: show.next_episode
            };
        };

        vm.newEpisode = function(episode) {
            return {
                air_date: DateFactory.getDate(episode.air_date),
                aired: episode.aired,
                days_left: DateFactory.getMonthDaysHours(episode.days_left),
                id: episode.id,
                number: episode.number
            };
        };

        $scope.getShow();
    };
})();