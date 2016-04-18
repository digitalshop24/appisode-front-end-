(function () {
    'use strict';

    var app = angular.module('app', [
        // Angular modules 
        'ngAnimate',
        'ngRoute',
        'ngSanitize',
        'ngTouch',
        'LocalStorageModule'
        //'ngCordova'
    ]);

    app.configuration = {
        useLocalData: true
    };

    app.config([
        '$routeProvider',
        '$compileProvider',
            function ($routeProvider, $compileProvider) {
                $routeProvider
                    .when("/shows", {
                        templateUrl: "app/views/shows.html"
                    })
                    .when("/search", {
                        templateUrl: "app/views/search.html"
                    })
                    .otherwise({
                        redirectTo: '/login'
                    });

                // Fix bug for Windows Phone wanting to download files on urls with routed parameters
                $compileProvider.aHrefSanitizationWhitelist(/^\s*(https?|ftp|mailto|file|ghttps?|ms-appx|x-wmapp0):/);
            }
    ]).filter('linebreakFilter', function () {
        return function (text) {
            if (text !== undefined)
                return text.replace(/\n/g, '<br />');
            return text;
        };
    });

    app.constant('ngAuthSettings', {
        apiServiceBaseUri: 'http://appisode.ru/api/v1'
    });
})();