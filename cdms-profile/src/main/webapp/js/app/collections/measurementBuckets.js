app.MeasurementBuckets = Backbone.Collection.extend({
	model: app.MeasurementBucket,
	arr: _.uniq(_.flatten(app.graphOfs)),
	url: function (from, to) {
		return 'rest/timeMeasurement/' + this.arr;
	},

	parse: function (resp) {
		// Deletes null objects from the resp
		var mod = [];
		_.each(resp, function(element){
			if (element !== null){
				mod.push(element);
			}
		});
		return mod;
	},
});