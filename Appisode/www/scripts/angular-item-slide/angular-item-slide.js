'use strict';
angular.module('itemSlide', [])
    .constant('itemSlideConfig', {
        method: {},
        event: {}
    })
    .directive('itemSlide', [
        '$timeout', 'itemSlideConfig', function($timeout, itemSlideConfig) {
            var itemSlideMethodList = ['getActiveIndex', 'getActiveIndex', 'next', 'previous', 'gotoSlide', 'reload', 'addSlide', 'removeSlide'];
            var itemSlideEventList = ['changePos', 'pan', 'changeActiveIndex', 'swipeout', 'clickSlide'];

            return {
                restrict: 'AEC',
                scope: {
                    settings: '=',
                    duration: '@',
                    swipe_sensitivity: '@',
                    disable_slide: '@',
                    disable_clicktoslide: '@',
                    disable_autowidth: '@',
                    disable_scroll: '@',
                    start: '@',
                    pan_threshold: '@',
                    one_item: '@',
                    parent_width: '@',
                    swipe_out: '@',
                    left_sided: '@'
                },
                link: function (scope, element, attrs) {
                    var options, slider;

                    var initOptions = function() {
                        options = angular.extend(angular.copy(itemSlideConfig), {
                            duration: scope.duration || 350,
                            swipe_sensitivity: scope.swipe_sensitivity || 150,
                            disable_slide: scope.disable_slide === 'true',
                            disable_clicktoslide: scope.disable_slide === 'true',
                            disable_autowidth: scope.disable_autowidth === 'true',
                            disable_scroll: scope.disable_scroll === 'true',
                            start: scope.start || 0,
                            pan_threshold: scope.pan_threshold || 0.3,
                            one_item: scope.one_item === 'true',
                            parent_width: scope.parent_width === 'true',
                            swipe_out: scope.swipe_out === 'true',
                            left_sided: scope.left_sided === 'true'
                        }, scope.settings);
                    };

                    var destroy = function() {
                        var el = angular.element(element);

                        if (slider) {
                            slider.destroy();
                        }

                        return el;
                    };

                    var init = function() {
                        initOptions();

                        slider = angular.element(element);

                        $timeout(function () {
                            angular.element(element).css('display', 'block');
                            slider.itemslide(options);
                        });

                        scope.internalControl = options.method || {};

                        itemSlideMethodList.forEach(function (value) {
                            scope.internalControl[value] = function () {
                                var args = Array.prototype.slice.call(arguments);
                                args.unshift(value);
                                slider.itemSlide.apply(element, args);
                            };
                        });

                        slider.on('changePos', function (event, index) {
                            if (typeof options.event.changePos !== 'undefined') {
                                $timeout(function () {
                                    scope.$apply(function () {
                                        options.event.changePos(event);
                                    });
                                });
                            }
                        });
                    };

                    var destroyAndInit = function () {
                        destroy();
                        init();
                    };

                    element.one('$destroy', function () {
                        destroy();
                    });

                    return scope.$watch('settings', function (newVal, oldVal) {
                        if (newVal !== null) {
                            return destroyAndInit();
                        }
                    }, true);
                }
            };
        }
    ]);