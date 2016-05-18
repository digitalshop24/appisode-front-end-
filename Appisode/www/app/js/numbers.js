var NumbersFactory = {
    cases: [2, 0, 1, 1, 1, 2],
    declOfNum: function (number, titles) {
        return number + " " + titles[(number % 100 > 4 && number % 100 < 20) ? 2 : this.cases[(number % 10 < 5) ? number % 10 : 5]];
    }
};