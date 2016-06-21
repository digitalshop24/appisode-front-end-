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
        'ngCordova',
        'ionic',
        'angular-ladda',
        'ui-notification',
        'angular-google-analytics',
        'itemSlide',
        'clickForOptions',
        'duScroll',
        'angular-inview',
        'focusMe',
        'windowScroll'
    ]).value('duScrollOffset', 300);

    angular.module('app').config(function (laddaProvider) {
        laddaProvider.setOption({
            style: 'zoom-in',
            spinnerSize: 35,
            spinnerColor: '#ffffff'
        });
    });

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
                    'main': { templateUrl: "app/views/popular.html" }
                }
            }).state('subscriptions', {
                url: '/subscriptions',
                views: {
                    'main': { templateUrl: "app/views/subscriptions.html" }
                }
            }).state('newest', {
                url: '/newest',
                views: {
                    'main': { templateUrl: "app/views/newest.html" }
                }
            }).state('search', {
                url: '/search',
                views: {
                    'main': { templateUrl: "app/views/search.html" },
                    'header@search': { templateUrl: "app/views/partials/search-header.html" }
                }
            }).state('search-results', {
                url: '/search-results/:showId',
                views: {
                    'main': { templateUrl: "app/views/search-results.html" }
                    //'header@search-results': { templateUrl: "app/views/partials/search-header.html" }
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
            }).state('auth-step5', {
                url: '/auth/step5',
                views: {
                    'main': { templateUrl: "app/views/auth/step5.html" }
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
        'localStorageService',
        'ngLocalStorageKeys',
        function ($state, $window, localStorageService, ngLocalStorageKeys) {
            var key = ngLocalStorageKeys.key;

            var authorized = localStorageService.get(key);

            if (authorized) {
                $state.transitionTo('popular');
            } else {
                $state.transitionTo('auth-step1');
            }
        }
    ]);

    app.run([
        '$rootScope', '$state',
        function($rootScope) {
            $rootScope.$on('$stateChangeSuccess', function (event, toState, toParams, fromState, fromParams) {
                $rootScope.slick_pages = true;
                $rootScope.activetab = toState.name;
            });
        }
    ]);

    app.run(['$rootScope', '$ionicPlatform', 'pushNotificationsService',
        function ($rootScope, $ionicPlatform, pushNotificationsService) {
            $ionicPlatform.registerBackButtonAction(function () {
                navigator.app.backHistory();
            }, 100);

            var fn = function() {
                pushNotificationsService.getList($rootScope.pushPage, $rootScope.pushPerPage).then(function(response) {
                    $.each(response.shows, function () {
                        var notification = {
                            id: $rootScope.index++,
                            content: this.message,
                            image: this.image,
                            notification_id: this.id
                        };

                        var index = $rootScope.notifications.indexOf(notification);

                        if (index < 0) {
                            $rootScope.notifications.push(notification);
                        }
                    });
                }, function(code) {});
            };

            $ionicPlatform.on("resume", function (event) {
                fn();
            });

            ionic.Platform.ready(function() {
                fn();
            });
        }
    ]);

    app.config(function ($httpProvider) {
        $httpProvider.interceptors.push('authInterceptorService');
    });

    app.config(function () {
        var tagManager = cordova.require('com.jareddickson.cordova.tag-manager.TagManager');
        tagManager.init(null, null, 'GTM-5CRB2N', 30);
    });

    app.constant('ngApiSettings', {
        apiUri: 'http://api.appisode.ru/api/v1'
    });

    app.constant('ngLocalStorageKeys', {
        phone: 'phone',
        key: 'key',
        started: 'started',
        deviceToken: 'deviceToken'
    });
    
})();