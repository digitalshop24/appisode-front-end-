var Utils = {
    redirectToLogin: function() {
        document.location = AppPath + "account/login?returnUrl=" + encodeURIComponent(window.location.pathname);
    },
    getParameterByName: function(name) {
        name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
        var regex = new RegExp("[\\?&]" + name + "=([^&#]*)", "i"),
            results = regex.exec(location.search);
        return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
    },
    buildApiUrl: function(url, template, params) {
        template = this.buildUrl(template, params);
        return url + template;
    },
    buildAppUrl: function(template, params) {
        template = this.buildUrl(template, params);
        return AppPath + template;
    },
    buildUrl: function(template, params) {
        var self = this;
        for (var name in params) {
            var key = '{' + name + '}';
            template = self.replaceAll(key, encodeURIComponent(params[name]), template);
        }
        return template;
    },
    replaceAll: function(find, replace, str) {
        return str.replace(new RegExp(find, 'g'), replace);
    },
    getQueryString: function() {
        var result = {}, queryString = location.search.slice(1),
            re = /([^&=]+)=([^&]*)/g, m;

        while (m = re.exec(queryString)) {
            result[decodeURIComponent(m[1])] = decodeURIComponent(m[2]);
        }

        return result;
    },
    getRouteByName: function (name, caseSensitive) {
        var value;
        $.each(RouteData, function () {
            if ((!caseSensitive && this.key.toUpperCase() == name.toUpperCase()) ||
                caseSensitive && this.key == name) {
                value = this.value;
            }
        });
        return value;
    },
    getImplicit: function(args) {
        return Array.prototype.slice.call(args, arguments.callee.caller.length);
    }
};