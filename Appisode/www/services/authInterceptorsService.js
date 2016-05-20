'use strict';

angular.module('app').factory('authInterceptorService', ['$q', '$injector', 'localStorageService', 'ngLocalStorageKeys',
 function ($q, $injector, localStorageService, ngLocalStorageKeys) {

     var authInterceptorServiceFactory = {};

     var request = function (config) {

         config.headers = config.headers || {};

         var authToken = localStorageService.get(ngLocalStorageKeys.key);

         if (authToken) {
             config.headers['Auth-Token'] = authToken;
         }

         return config;
     }

     var responseError = function (rejection) {
         if (rejection.status === 401) {
         }
         return $q.reject(rejection);
     }

     authInterceptorServiceFactory.request = request;
     authInterceptorServiceFactory.responseError = responseError;

     return authInterceptorServiceFactory;
 }]);