(function() {
    'use strict';

    angular
        .module('app')
        .controller('showController', showController);

    showController.$inject = [
        '$scope', '$rootScope', '$routeParams', '$timeout', 'showsService'
    ];

    function showController($scope, $rootScope, $routeParams, $timeout, showsService) {
        var vm = this;

        $scope.showId = $routeParams.showId;
        $scope.show = {};
        $scope.episodes = [];
        $scope.episode = {};
        $scope.totalEpisodes = 0;
        $scope.busy = true;
    
        $scope.getShow = function() {
            showsService.getShow($scope.showId).then(function(response) {
                $scope.show = vm.newShow(response);
                $scope.poster = response.poster;    

                $.each(response.episodes, function() {
                    $scope.episodes.push(vm.newEpisode(this));
                });

                $scope.totalEpisodes = response.episodes.length;
                $scope.current = $scope.episodes[$scope.totalEpisodes - 1];

                $scope.busy = false;
            });
        };

        $scope.slickConfig = {
            infinite: true,
            centerMode: true,
            centerPadding: '120px',
            arrows: false,
            slidesToShow: 1,
            initialSlide: $scope.totalEpisodes - 1
        };

        $scope.breakpoints = [
            {
                breakpoint: 690,
                settings: {
                    arrows: false,
                    centerMode: true,
                    centerPadding: '40px',
                    slidesToShow: 3,
                    initialSlide: $scope.totalEpisodes - 1
                }
            },
            {
                breakpoint: 480,
                settings: {
                    arrows: false,
                    centerMode: true,
                    centerPadding: '20px',
                    slidesToShow: 1,
                    initialSlide: $scope.totalEpisodes - 1
                }
            }
        ];

        $scope.onAfterChange = function (index) {
            $scope.current = $scope.episodes[index];
            $scope.$apply();
        };

        vm.newShow = function(show) {
            return {
                id: show.id,
                in_production: show.in_production,
                name: show.name,
                russian_name: show.russian_name,
                season_number: show.season_number,
                poster: show.poster
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