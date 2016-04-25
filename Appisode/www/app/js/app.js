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
        function($routeProvider, $compileProvider) {
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
                .otherwise({
                    templateUrl: 'app/views/popular.html'
                });

            // Fix bug for Windows Phone wanting to download files on urls with routed parameters
            $compileProvider.aHrefSanitizationWhitelist(/^\s*(https?|ftp|mailto|file|ghttps?|ms-appx|x-wmapp0):/);
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

    app.constant('ngAuthSettings', {
        phone: null,
        key: null
    });
})();