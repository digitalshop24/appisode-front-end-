(function () {
    'use strict';

    angular
        .module('app')
        .controller('headerController', headerController);

    headerController.$inject = [
        '$scope', '$rootScope', '$location'];

    function headerController($scope, $rootScope, $location) {
        $scope.closeSearch = function() {
            $rootScope.hide_main_layout = false;
            history.go(-1);
        };

        $scope.goto = function(url) {
            $location.path(url);
        };

        //$scope.showAuthorizationPopup = function() {
        //    $rootScope.authorization_popup_show = true;
        //};
    };

})();