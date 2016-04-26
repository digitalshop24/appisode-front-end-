(function () {
    'use strict';

    var app = angular.module('app', [
        // Angular modules 
        'ngAnimate',
        'ngRoute',
        'ngSanitize',
        'ngTouch',
        'LocalStorageModule',
        'infinite-scroll',
        'slick'
        //'ngCordova'
    ]);

    app.configuration = {
        useLocalData: true
    };

    app.config([
        '$routeProvider',
        '$compileProvider',
        'localStorageServiceProvider',
        function ($routeProvider, $compileProvider, localStorageServiceProvider) {
            $routeProvider
                .when("/shows", {
                    templateUrl: "app/views/shows.html"
                })
                .when("/popular", {
                    templateUrl: "app/views/popular.html"
                })
                .when("/search", {
                    templateUrl: "app/views/search.html"
                })
                .when("/subscriptions", {
                    templateUrl: "app/views/subscriptions.html"
                })
                .when("/show/:showId", {
                    templateUrl: "app/views/show.html"
                })
                .when("/auth/step1", {
                    templateUrl: "app/views/auth/step1.html"
                })
                .when("/auth/step2", {
                    templateUrl: "app/views/auth/step2.html"
                })
                .when("/auth/step3", {
                    templateUrl: "app/views/auth/step3.html"
                })
                .when("/auth/step4", {
                    templateUrl: "app/views/auth/step4.html"
                })
                .otherwise({
                    templateUrl: 'app/views/auth/step1.html'
                });

            // Fix bug for Windows Phone wanting to download files on urls with routed parameters
            $compileProvider.aHrefSanitizationWhitelist(/^\s*(https?|ftp|mailto|file|ghttps?|ms-appx|x-wmapp0):/);

            localStorageServiceProvider.setPrefix('Appisode');
        }
    ]).filter('linebreakFilter', function() {
        return function(text) {
            if (text !== undefined)
                return text.replace(/\n/g, '<br />');
            return text;
        };
    });

    app.constant('ngApiSettings', {
        apiUri: 'http://appisode.ru/api/v1'
    });
})();