(function () {
    'use strict';

    angular
        .module('app')
        .controller('headerController', headerController);

    headerController.$inject = [
        '$scope', '$rootScope', '$state'];

    function headerController($scope, $rootScope, $state) {
        $scope.closeSearch = function() {
            history.go(-1);
        };

        $scope.authorize = function(element) {
            $(element.currentTarget).toggleClass("active");
            $state.go('auth-step1');
        };
    };

})();