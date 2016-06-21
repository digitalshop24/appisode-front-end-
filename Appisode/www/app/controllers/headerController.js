(function () {
    'use strict';

    angular
        .module('app')
        .controller('headerController', headerController);

    headerController.$inject = [
        '$rootScope', '$scope', 'subscriptionsService'];

    function headerController($rootScope, $scope, subscriptionsService) {
    };

})();