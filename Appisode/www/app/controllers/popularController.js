(function () {
    'use strict';

    angular
        .module('app')
        .controller('popularController', popularController);

    popularController.$inject = [
        '$scope', '$rootScope', '$state', 'localStorageService', 'showsService', 'authService', 'ngLocalStorageKeys'];

    function popularController($scope, $rootScope, $state, localStorageService, showsService, authService, ngLocalStorageKeys) {
        var vm = this;

        vm.page = 1;
        vm.take = 10;

        $scope.shows = [];
        $scope.count = null;
        $scope.busy = true;

        $scope.init = function() {
            $scope.busy = true;
            $scope.shows = [];
            $scope.count = null;
            $scope.getShows();
        };

        $scope.getShows = function () {

            var cached = localStorageService.get(ngLocalStorageKeys.popular_shows);

            if (cached) {
                $.each(cached, function() {
                    $scope.shows.push(this);
                });
            }

            $scope.busy = true;

            showsService.popularList().then(function (response) {
                $.each(response, function () {
                    $scope.shows.push(this);
                });

                localStorageService.remove(ngLocalStorageKeys.popular_shows);
                localStorageService.set(ngLocalStorageKeys.popular_shows, response);

                $scope.busy = false;
            });
        };

        $scope.gotoShow = function(id) {
            $state.go('show', {showId: id});
        };

        $scope.like = function (event) {
            event.stopPropagation();
            $(event.currentTarget).toggleClass("active");
        };

        $scope.subscribe = function(event) {
            event.stopPropagation();
            var authorized = authService.isAuthorized();
            if (!authorized) {
                $state.go($state.$current.parent.name + '.auth-step1');
            }
        };

        $scope.changePeriod = function (event) {
            event.stopPropagation();
        };

        $scope.init();
    };

})();