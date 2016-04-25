var DateFactory = {
    months: [
        "января", "февраля", "марта", "апреля", "мая", "июня",
        "июля", "августа", "сентября", "октября", "ноября", "декабря"
    ],
    getDate: function(d) {
        var date = new Date(d);

        var month = this.months[date.getMonth()];
        var day = date.getDate();
        var year = date.getFullYear();

        return day + " " + month + " " + year;
    },
    getMonthDaysHours: function (days) {
        if (days < 0) {
            return {months: 0, days: 0, hours: 0}
        }

        var m = Math.floor(days / 30);
        var d = days - (m * 30);

        if (d < 0) {
            return { months: m, days: 0, hours: 0 };
        } else {
            return {months: m, days: d, hours: 0}
        }
    }
};