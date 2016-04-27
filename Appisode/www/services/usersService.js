(function () {
    'use strict';

    angular.module('app').factory('usersService', usersService);

    usersService.$inject = ['$http', '$q', 'ngApiSettings', 'ngLocalStorageKeys'];

    function usersService($http, $q, ngApiSettings) {

    };
})();