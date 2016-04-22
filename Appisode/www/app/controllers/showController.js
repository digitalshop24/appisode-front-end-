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

        $scope.arrows = false;

        $scope.getShow = function() {
            showsService.getShow($scope.showId).then(function(response) {
                $scope.show = response;
                $scope.show.poster = response.poster;
                $scope.episodes = response.episodes;
            });
        };

        $scope.slickConfig = {
            infinite: true,
            centerMode: true,
            centerPadding: '120px',
            arrows: false,
            slidesToShow: 1
        };

        $scope.breakpoints = [
            {
                breakpoint: 690,
                settings: {
                    arrows: false,
                    centerMode: true,
                    centerPadding: '40px',
                    slidesToShow: 3
                }
            },
            {
                breakpoint: 480,
                settings: {
                    arrows: false,
                    centerMode: true,
                    centerPadding: '40px',
                    slidesToShow: 1
                }
            }
        ];

        $scope.onAfterChange = function (index) {
            var current = $scope.episodes[index];
        };

        $scope.getShow();
    };
})();