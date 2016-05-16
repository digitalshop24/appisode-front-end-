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
        'slick',
        'ui.router',
        'ngCordova'
    ]);

    app.configuration = {
        useLocalData: true
    };

    app.config([
        '$routeProvider',
        '$stateProvider',
        '$compileProvider',
        'localStorageServiceProvider',
        function ($routeProvider, $stateProvider, $compileProvider, localStorageServiceProvider) {
            $routeProvider.otherwise("/");

            $stateProvider.state('popular', {
                url: '/',
                views: {
                    'main': { templateUrl: "app/views/popular.html" },
                    'header@popular': { templateUrl: "app/views/partials/header.html" }
                }
            }).state('subscriptions', {
                url: '/subscriptions',
                views: {
                    'main': { templateUrl: "app/views/subscriptions.html" },
                    'header@subscriptions': { templateUrl: "app/views/partials/subscriptions-header.html" }
                }
            }).state('newest', {
                url: '/newest',
                views: {
                    'main': { templateUrl: "app/views/newest.html" },
                    'header@newest': { templateUrl: "app/views/partials/header.html" }
                }
            }).state('search', {
                url: '/search',
                views: {
                    'main': { templateUrl: "app/views/search.html" },
                    'header@search': { templateUrl: "app/views/partials/search-header.html" }
                }
            }).state('show', {
                url: '/show/:showId',
                views: {
                    'main': { templateUrl: "app/views/show.html" },
                    'header@show': { templateUrl: "app/views/partials/header.html" }
                }
            }).state('auth-step1', {
                url: '/auth/step1',
                views: {
                    'main': { templateUrl: "app/views/auth/step1.html" }
                }
            }).state('auth-step2', {
                url: '/auth/step2',
                views: {
                    'main': { templateUrl: "app/views/auth/step2.html" }
                }
            }).state('auth-step3', {
                url: '/auth/step3',
                views: {
                    'main': { templateUrl: "app/views/auth/step3.html" }
                }
            }).state('auth-step4', {
                url: '/auth/step4',
                views: {
                    'main': { templateUrl: "app/views/auth/step4.html" }
                }
            })

                .state('popular.auth-step1', {
                url: '/auth/alt/step1',
                templateUrl: "app/views/auth/alt/step1.html"
            }).state('popular.auth-step2', {
                url: '/auth/alt/step2',
                templateUrl: "app/views/auth/alt/step2.html"
            }).state('popular.auth-step3', {
                url: '/auth/alt/step3',
                templateUrl: "app/views/auth/alt/step3.html"
            })

                .state('show.auth-step1', {
                url: '/auth/alt/step1',
                templateUrl: "app/views/auth/alt/step1.html"
                }).state('show.auth-step2', {
                url: '/auth/alt/step2',
                templateUrl: "app/views/auth/alt/step2.html"
                }).state('show.auth-step3', {
                url: '/auth/alt/step3',
                templateUrl: "app/views/auth/alt/step3.html"
            });

            // Fix bug for Windows Phone wanting to download files on urls with routed parameters
            $compileProvider.aHrefSanitizationWhitelist(/^\s*(https?|ftp|mailto|file|ghttps?|ms-appx|x-wmapp0):/);

            localStorageServiceProvider.setPrefix('appisode');
        }
    ]).filter('linebreakFilter', function() {
        return function(text) {
            if (text !== undefined)
                return text.replace(/\n/g, '<br />');
            return text;
        };
    });

    app.run([
        '$state',
        '$window',
        function ($state, $window, localStorageService) {
            var key = 'appisode.started';
            var started = $window.localStorage.getItem(key);
            if (started) {
                $state.transitionTo('popular');
            } else {
                $state.transitionTo('auth-step1');
                $window.localStorage.setItem(key, true);
            }
        }
    ]);

    app.run([
        '$rootScope', '$state',
        function($rootScope, $state) {
            $rootScope.$on('$stateChangeSuccess', function(event, toState, toParams, fromState, fromParams) {
                $rootScope.activetab = toState.name;
            });
        }
    ]);

    app.run([
        '$rootScope', '$http', '$cordovaPush',
        function($rootScope, $http, $cordovaPush) {
            var iosConfig = {
                "badge": true,
                "sound": true,
                "alert": true
            };

            document.addEventListener("deviceready", function() {
                $cordovaPush.register(iosConfig).then(function(deviceToken) {
                    // Success -- send deviceToken to server, and store for future use
                    //console.log("deviceToken: " + deviceToken);
                    alert(deviceToken);
                }, function(err) {
                    alert("Registration error: " + err);
                });


                $rootScope.$on('$cordovaPush:notificationReceived', function(event, notification) {
                    if (notification.alert) {
                        navigator.notification.alert(notification.alert);
                    }

                    //if (notification.sound) {
                    //    var snd = new Media(event.sound);
                    //    snd.play();
                    //}

                    //if (notification.badge) {
                    //    $cordovaPush.setBadgeNumber(notification.badge).then(function(result) {
                    //        // Success!
                    //    }, function(err) {
                    //        // An error occurred. Show a message to the user
                    //    });
                    //}
                });

                //$cordovaPush.unregister(options).then(function(result) {
                //}, function(err) {
                //});

            });
        }
    ]);

    app.constant('ngApiSettings', {
        apiUri: 'http://api.appisode.ru/api/v1'
    });

    app.constant('ngLocalStorageKeys', {
        phone: 'phone',
        key: 'key',
        auth_step: 'authstep',
        started: 'started',
        authorized: 'authorized',
        popular_shows: 'popular_shows',
        ios_token: 'ios_token'
    });

    
})();