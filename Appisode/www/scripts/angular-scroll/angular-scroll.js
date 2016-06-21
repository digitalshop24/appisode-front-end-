'use strict';
angular.module('windowScroll', []).directive("windowScroll", function ($window) {
    return function (scope, element, attrs) {
        var _prevScrollPos = 0;
        angular.element($window).bind("scroll", function () {
            var scrollPos = $(this).scrollTop();
            var $n = $('.menu-block');

            //SCROLLING DOWN
            if (scrollPos > _prevScrollPos) {
                //remove fixed position so nav rolls off the page top with the content
                if ($n.css('position') !== 'absolute') {
                    $n.css({ position: 'absolute', top: _prevScrollPos });
                }
            }
                //SCROLLING UP
            else if (scrollPos < _prevScrollPos) {
                //once we set it fixed, no need to run this anymore
                if ($n.css('position') !== 'fixed') {
                    //if we are down the page a bit, but the nav is still at the top, we need to move it down closer (but not all the way, so there's a little delay) to where we currently are
                    if (scrollPos > 200 && $n.offset().top < scrollPos - 200) {
                        $n.offset({ top: scrollPos - 200 });
                    }
                        //once the nav is in place, set it to fixed so it stays there
                    else if ($n.offset().top >= scrollPos) {
                        $n.css({ position: 'fixed', top: '0' });
                    }
                }
            }
            _prevScrollPos = scrollPos;
            //alert();
            //scope.visible = false;
            //scope.$apply();
        });
    };
});