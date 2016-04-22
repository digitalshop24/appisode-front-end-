(function() {
    'use strict';

    angular.module('app').factory('authService', authService);

    authService.$inject = ['$http', '$q', 'ngApiSettings', 'ngAuthSettings'];

    function authService($http, $q, ngApiSettings) {
        function checkAuth() {
            
        };
    };
});