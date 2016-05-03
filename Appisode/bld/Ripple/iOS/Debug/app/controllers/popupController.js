(function () {
    'use strict';

    angular
        .module('app')
        .controller('popupController', popupController);

    popupController.$inject = [
        '$scope', '$rootScope'];

    function popupController($scope, $rootScope) {
        //$scope.closeAuthorizationPopup = function () {
        //    $rootScope.authorization_popup_show = false;
        //};
    };

})();