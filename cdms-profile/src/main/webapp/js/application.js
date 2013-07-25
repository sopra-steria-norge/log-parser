var app = {
	collections: {},
	models : {},
	views : {},

	initialize : function() {
		this.collections.procedures = new app.Procedures();
		this.collections.procedures.fetch();
		console.log(this.collections);
		app.percentiles = [100, 95, 90, 80, 50, 0];
		app.router = new app.Router();
		Backbone.history.start();

	}
}

$(document).ready(function() {
	$.get('rest/procedure', function(resp) {
		app.procedureMapping = resp;
		app.initialize();
	});
});
