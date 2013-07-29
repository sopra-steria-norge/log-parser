app.PercentileCollection = Backbone.Collection.extend({
	model: app.Percentile,

	url: function () {
		return 'rest/percentile'
	}
});