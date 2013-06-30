app.MeasurementCollection = Backbone.Collection.extend({
	model: app.Measurement,
	url: function () {
		var ids = [];
		_.each($.find('.graph'), function	(graph) {
			var graphOf = [$(graph).data('graphof')];
			if (typeof graphOf[0] === 'string') {
            	graphOf = graphOf[0].split(',')
            	for (var i = 0; i < graphOf.length; i++) {
                	graphOf[i] = parseInt(graphOf[i]);
            	};
        	};
        	ids.push(graphOf);
		})
		ids = _.uniq(_.flatten(ids));
		return 'rest/timeMeasurement/' + ids;
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