var app = {
	collections: {},
	models : {},
	views : {},

	initialize : function() {
		app.collections.procedures = new app.Procedures();
		app.collections.procedures.fetch({success: function(){
			app.router = new app.Router();
			Backbone.history.start();
		}});

	}
}

app.percentiles = [100, 95, 90, 80, 50, 0];
app.graphOfs= [[1],[18],[1,18]];
app.nrOfBuckets = 200;

$(document).ready(function() {
	app.initialize();
});
