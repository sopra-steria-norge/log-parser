app.MeasurementBuckets = Backbone.Collection.extend({
	model: app.MeasurementBucket,
	url: function (from, to) {
		var arr = _.uniq(_.flatten(app.graphOfs));
		console.log(arr.toString());
		console.log('rest/timeMeasurement/' + arr);
		return 'rest/timeMeasurement/' + arr;

	}

});